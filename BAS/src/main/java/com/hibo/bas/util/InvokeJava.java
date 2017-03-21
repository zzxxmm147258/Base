package com.hibo.bas.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午5:40:52</p>
 * <p>类全名：com.hibo.bas.util.InvokeJava</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public final class InvokeJava {
	private InvokeJava() {
	}

	/*
	 * matchMode == 1: 类型必须严格相等 2: isAssignableFrom, 3：可以 数值转换
	 */
	static final Class PrimitiveClass[] = {
			Void.TYPE,// null,null,null,
			Boolean.TYPE, Character.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE,
			Short.TYPE, Integer.TYPE, Long.TYPE };
	static final Class PrimitiveClassX[] = {
			Void.class,// null,null,null,
			Boolean.class, Character.class, Float.class, Double.class,
			Byte.class, Short.class, Integer.class, Long.class };

	private static Object convertToAssignable(Class toType, Object fromObject,
			int matchMode) {
		if (fromObject == null)
			return null;
		final Class fromType = fromObject.getClass();
		if (toType.isPrimitive())
			for (int j = 0; j < PrimitiveClass.length; j++) {
				if (PrimitiveClass[j] == toType)
					toType = PrimitiveClassX[j];
				// toType.get
			}
		if (matchMode == 1) {
			return toType == fromType ? fromObject : null;
		}
		if (toType.isAssignableFrom(fromType))
			return fromObject;
		if (matchMode == 3 && fromObject instanceof Number
				&& Number.class.isAssignableFrom(toType)) //
		{
			return Data.toNumber((Number) fromObject, toType);
		}
		// isPrimitive()
		return null;
	}

	final static class MethodArgs {
		public final Member method;
		public final Class[] parmTypes;
		public final boolean isVarArgs;

		public MethodArgs(Member method, Class[] parmTypes, boolean isVarArgs) {
			this.method = method;
			this.parmTypes = parmTypes;
			this.isVarArgs = isVarArgs;
		}

		// isVarArgs
		Object args[];

		boolean setArgs(final Object args[], final int matchMode) {
			final int nParamTypes = parmTypes.length;
			final int n = isVarArgs ? nParamTypes - 1 : nParamTypes;
			Object paramArgs[] = new Object[nParamTypes];
			for (int i = 0; i < n; i++) {
				if (args[i] == null) {
					if (matchMode == 1 || parmTypes[i].isPrimitive())
						// matchMode==1:必须严格匹配; parmTypes[i].isPrimitive()
						// :空只不能赋值给
						return false;
					continue;
				}
				paramArgs[i] = convertToAssignable(parmTypes[i], args[i],
						matchMode);
				if (paramArgs[i] == null)
					return false;
				// convertToAssignable
			}
			checkVarArgs: if (isVarArgs) {
				final int lastParamIdx = nParamTypes - 1;
				final Class varArgType = parmTypes[lastParamIdx]
						.getComponentType();
				if (args.length == nParamTypes - 1) // 缺省最后一个参数：
				{
					paramArgs[lastParamIdx] = java.lang.reflect.Array
							.newInstance(varArgType, 0);
					break checkVarArgs;
				}
				if (args.length == nParamTypes) {
					final Class lastArgType = args[lastParamIdx] == null ? null
							: args[lastParamIdx].getClass();
					if (matchMode == 1) // 必须严格匹配：
					{
						if (lastArgType == null)
							return false;
						if (lastArgType == parmTypes[lastParamIdx]) {
							paramArgs[lastParamIdx] = args[lastParamIdx];
							break checkVarArgs;
						}
					} else {
						if (args[lastParamIdx] == null
								|| parmTypes[lastParamIdx]
										.isAssignableFrom(lastArgType)) {
							paramArgs[lastParamIdx] = args[lastParamIdx];
							break checkVarArgs;
						}
					}
				}
				// 检查元素：
				final int nVarArgs = args.length - (nParamTypes - 1);
				final Object varArgsA = paramArgs[lastParamIdx] = java.lang.reflect.Array
						.newInstance(varArgType, nVarArgs);
				// paramArgs[lastParamIdx] =
				for (int i = 0; i < nVarArgs; i++) {
					Object v = args[nParamTypes - 1 + i];
					if (v == null) {
						if (matchMode == 1 || varArgType.isPrimitive()) // 必须严格匹配：
							return false;
						continue;
					}
					v = convertToAssignable(varArgType, v, matchMode);
					if (v == null)
						return false;
					java.lang.reflect.Array.set(varArgsA, i, v);
					// varArgType
				}
			}
			this.args = paramArgs;
			return true;
		}
	}

	// private boolean
	/*
	 * methodName==null : 构造函数：
	 */
	static MethodArgs[] getMatchedMethods(final Member[] members,
			final String methodName, final Object... args) {
		final int nMethod;
		final MethodArgs methods[];
		final java.util.List<MethodArgs> v = new java.util.ArrayList<MethodArgs>();
		{
			final int nArgs = args.length;
			for (final Member m : members) {
				boolean isVarArgs;
				Class[] parmTypes;
				if (methodName != null) {
					if (!m.getName().equals(methodName))
						continue;
					isVarArgs = ((Method) m).isVarArgs();
					parmTypes = ((Method) m).getParameterTypes();

				} else // 构造函数
				{
					isVarArgs = ((Constructor) m).isVarArgs();
					parmTypes = ((Constructor) m).getParameterTypes();
				}
				// 参数匹配：
				if (isVarArgs ? nArgs >= parmTypes.length - 1
						: nArgs == parmTypes.length) {
					v.add(new MethodArgs(m, parmTypes, isVarArgs));
				}
				// Method
				// v.add(m);
			}
			nMethod = v.size();
			if (nMethod == 0)
				return null;
			methods = v.toArray(new MethodArgs[nMethod]);// return
															// getMatchedMethod(v.toArray(a))
		}
		// matchMode == 1: 类型必须严格相等
		// 2: isAssignableFrom,
		// 3：可以 数值转换
		for (int matchMode = methods.length == 1 ? 3 : 1; matchMode <= 3; matchMode++) {
			// MethodArgs matchedMthod = null;
			v.clear();
			for (MethodArgs m : methods) {
				if (m.setArgs(args, matchMode)) {
					v.add(m);
				}
			}
			if (v.size() > 0)
				return v.toArray(new MethodArgs[v.size()]);
		}
		return null;
	}

	static public Object invokeJava(Class cls, String methodName, int options,
			Object obj, Object... args) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException,
			ClassNotFoundException {
		if (args == null)
			args = Const.ZObjectArray1;// new Object[0];
		MethodArgs matchedMthods[] = getMatchedMethods(cls.getMethods(),
				methodName, args);
		if (matchedMthods != null && matchedMthods.length >= 1) // ???
		{
			final MethodArgs m = matchedMthods[0];
			if (obj == null && !Modifier.isStatic(m.method.getModifiers()))
				return null;
			return ((Method) m.method).invoke(obj, m.args);
		}
		{
			String argsDecl = "(";
			if (args != null)
				for (int j = 0; j < args.length; j++) {
					if (j > 0)
						argsDecl += ",";
					argsDecl += args[j] == null ? "null" : args[j].getClass()
							.getName();
				}
			argsDecl += ")";
			throw new java.lang.NoSuchMethodException(cls.getName() + "中未找到方法:"
					+ methodName + argsDecl);
		}
	}

	static public Object invokeJava(String methodName, int options, Object obj,
			Object... args) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException,
			ClassNotFoundException {
		int p = methodName.lastIndexOf('.');
		if (p < 0)
			throw new IllegalArgumentException(methodName);
		return invokeJava(Class.forName(methodName.substring(0, p)),
				methodName.substring(p + 1), options, obj, args);
	}

	static public <T> T newJavaObject(Class<T> cls, Class[] argsClass,
			Object... args) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException,
			InstantiationException {
		if (argsClass.length != args.length)
			throw new java.lang.IllegalArgumentException();
		if (args.length == 0)
			return cls.newInstance();
		Constructor method = cls.getConstructor(argsClass);
		return (T) method.newInstance(args);
	}

	static public Object newJavaObject(Class cls, Object... args)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException, InstantiationException {
		if (args == null || args.length == 0)
			return cls.newInstance();
		MethodArgs matchedMthods[] = getMatchedMethods(cls.getConstructors(),
				null, args);
		if (matchedMthods != null && matchedMthods.length >= 1) {
			if (matchedMthods.length > 1) {
				System.err.println(cls.getName() + ":构造函数二义性");// ERROR
			}
			final MethodArgs m = matchedMthods[0];
			return ((Constructor) m.method).newInstance(m.args);
		}

		throw new java.lang.NoSuchMethodException(cls.getName() + "中未找到对应的构造函数");
	}

	// f = xxx.xxx.xxx.Mtehod(argument,....)
	static public Object invokeJavaX(String f, Object thisObject)
			throws InvocationTargetException, IllegalAccessException,
			ClassNotFoundException, NoSuchMethodException,
			InstantiationException {
		return invokeJavaX(f, thisObject, null);
	}

	static public Object invokeJavaX(String f, Object thisObject,
			java.util.Map paramVar) throws InvocationTargetException,
			IllegalAccessException, ClassNotFoundException,
			NoSuchMethodException, InstantiationException {
		f = f.trim();
		int p = f.indexOf('(');
		if (p < 0 || f.charAt(f.length() - 1) != ')')
			throw new IllegalArgumentException();
		String paramList[] = new String[32];
		final int nParam;// = 0;
		{
			int pCountParams[] = new int[1];
			StrUtil.parseParameterList(f, p, paramList, null, pCountParams);
			nParam = pCountParams[0];
		}
		String methodName = f.substring(0, p);
		Object param[] = new Object[nParam];
		for (int i = 0; i < nParam; i++) {

			param[i] = StrUtil.parseValue(paramList[i], paramVar);
		}
		Class cls;
		{
			p = methodName.lastIndexOf('.');
			if (p < 0)
				cls = thisObject != null ? thisObject.getClass() : null;
			else {
				String className = methodName.substring(0, p);
				methodName = methodName.substring(p + 1);
				cls = Class.forName(className);
			}
		}
		if ("new".equals(methodName))
			return newJavaObject(cls, param);
		return invokeJava(cls, methodName, 0, thisObject, param);
	}

	final private static Class getClassIf(String className) {
		try {
			int p = className.lastIndexOf('.');
			char c = p > 0 ? className.charAt(p + 1) : className.charAt(0);
			if (c < 'A' || c > 'Z')
				return null;
			return Class.forName(className);
		} catch (Throwable ex) {
		}
		return null;
	}

	final static public Class Class0[] = new Class[0];
	final static public Class MapClass1[] = new Class[] { java.util.Map.class };
	final static public Class MapClass2[] = new Class[] { java.util.Map.class,
			java.util.Map.class };

	public static Object invoke(String method, Object obj, Class clazz[],
			Object args[]) throws ClassNotFoundException,
			NoSuchMethodException, InvocationTargetException,
			IllegalAccessException// ,InstantiationException
	{
		int p = method.lastIndexOf('.');
		if (p < 0)
			throw new java.lang.IllegalArgumentException(method);
		Class c = Class.forName(method.substring(0, p));
		return c.getMethod(method.substring(p + 1), clazz).invoke(obj, args);
	}

	static private void addListenersTo(Object o, java.util.List<Object> to) {
		if (o == null || to == null)
			return;
		if (o instanceof Object[]) {
			for (final Object x : (Object[]) o)
				addListenersTo(x, to);
		} else {
			to.add(o);
		}
	}

	static public void newListenersTo(String listeners,
			java.util.Map envParams, java.util.Map parameter,
			java.util.List<Object> v, java.util.Map mapNameListener)
			throws Throwable {
		if (listeners == null)
			return;
		listeners = StrUtil.replaceMacro(listeners, parameter, '{', '}', 2);
		String alisteners[] = StrUtil.splitString(listeners, ';');
		for (int j = 0; j < alisteners.length; j++) {
			final String ls = alisteners[j].trim();

			if (ls.startsWith("//") || ls.length() == 0)
				continue;
			Object o = mapNameListener == null ? null : mapNameListener.get(ls);
			if (o == null) {
				o = InvokeJava.invokeJava(ls, 4, envParams);
				if (o != null && mapNameListener != null)
					mapNameListener.put(ls, o);
			}
			addListenersTo(o, v);
		}
	}

	static public Object[] newListeners(String listeners,
			java.util.Map envParams, java.util.Map parameter) throws Throwable
	// ,
	// java.util.Map mapNameListener)
	{
		if (listeners == null)
			return null;
		java.util.List<Object> v = new java.util.ArrayList<>();
		newListenersTo(listeners, envParams, parameter, v,
				new java.util.HashMap());
		return v.size() == 0 ? null : v.toArray(new Object[v.size()]);
	}

	static public <T> T[] newListeners(Class<T> clazz, String listeners,
			java.util.Map envParams, java.util.Map parameter) throws Throwable {
		if (listeners == null)
			return null;
		java.util.List<T> v = new java.util.ArrayList<>();
		newListenersTo(listeners, envParams, parameter,
				(java.util.List<Object>) v, new java.util.HashMap());
		return v.size() == 0 ? null : ArrayUtil.toArray(v, clazz);
	}
}
