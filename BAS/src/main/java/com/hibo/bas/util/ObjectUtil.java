package com.hibo.bas.util;

import com.hibo.bas.exception.ExceptionUtil;
import com.hibo.bas.exception.HookedException;

/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午6:50:22</p>
 * <p>类全名：com.hibo.bas.util.ObjectUtil</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class ObjectUtil {
	public static void setObjectProperty(Object obj, String name, String value) {
		setObjectValue(obj, name, StrUtil.parseValue(value));
	}

	static java.lang.reflect.Method getMethod(Class clazz, String methodName, Class argType) {
		try {
			return clazz.getMethod(methodName, new Class[] { argType });
		} catch (Throwable ex) {
		}
		final java.lang.reflect.Method[] ms = clazz.getMethods();
		for (java.lang.reflect.Method m : ms) {
			if (!m.getName().equals(methodName))
				continue;
			// if( m.getModifiers()&Method.PUBLIC
			Class pramTypes[] = m.getParameterTypes();
			if (pramTypes.length == 1 && pramTypes[0].isAssignableFrom(argType))
				return m;
		}
		return null;
	}

	public static void setObjectValue(Object obj, String name, Object v) {
		final String setMethodName = "set" + name;
		if (v == null) {
			try {
				InvokeJava.invokeJava(obj.getClass(), setMethodName, 0, obj, new Object[] { v });
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			return;
		}
		Class type;
		if (v instanceof Integer)
			type = Integer.TYPE;
		else if (v instanceof Boolean)
			type = Boolean.TYPE;
		else if (v instanceof Double)
			type = Double.TYPE;
		else if (v instanceof Float)
			type = Float.TYPE;
		else
			type = v.getClass();
		try {
			java.lang.reflect.Method m = getMethod(obj.getClass(), setMethodName, type);
			if (m != null)
				m.invoke(obj, new Object[] { v });
		} catch (Throwable ex) {
			if (Diagnostic.isEnableOutput())
				ExceptionUtil.printStackTrace(ex, System.err, null, true);
		}
	}

	public static void setObjectValues(Object obj, java.util.Map values, String prefix, boolean parseValue) {
		if (values == null)
			return;
		for (final Object key : values.keySet()) {
			if (!(key instanceof String))
				continue;
			String name = (String) key;
			if (prefix != null) {
				if (!name.startsWith(prefix))
					continue;
				name = name.substring(prefix.length());
			}
			if (name.length() == 0)
				continue;
			Object v = values.get(key);
			if (parseValue && v instanceof String) {
				v = StrUtil.parseValue((String) v);
			}
			setObjectValue(obj, name, v);
		}
	}

	public static void setFieldValue(Object obj, String name, Object v) {
		try {
			java.lang.reflect.Field f = obj.getClass().getField(name);
			v = Data.toCompatibleObject(v, f.getType());
			f.set(obj, v);
		} catch (Throwable ex) {
			if (Diagnostic.isEnableOutput())
				ExceptionUtil.printStackTrace(ex, System.err, null, true);
		}
	}

	public static final <T> T getAs(Class<T> clazz, Object v) {
		return (v != null && clazz.isAssignableFrom(v.getClass())) ? (T) v : null;
	}

	private static void set(Object obj, Class clazz, String name, Object v, boolean ignoreCase, boolean throwErrIfNoMethod) {
		final java.lang.reflect.Method[] ms = clazz.getMethods();
		char c0 = name.charAt(0);
		if (c0 >= 'a' && c0 <= 'z')
			c0 = (char) (c0 - 'a' + 'A');
		final String methodName = "set" + c0 + name.substring(1);
		for (java.lang.reflect.Method m : ms) {
			if (!(ignoreCase ? m.getName().equalsIgnoreCase(methodName) : m.getName().equals(methodName)))
				continue;
			Class pramTypes[] = m.getParameterTypes();
			if (pramTypes.length != 1)
				continue;
			v = Data.toCompatibleObject(v, pramTypes[0]);
			try {
				m.invoke(obj, new Object[] { v });
			} catch (Throwable ex) {
				throw HookedException.toRuntimeException(ex);
			}
			return;
		}
		if (throwErrIfNoMethod)
			throw new java.lang.IllegalArgumentException("No method " + methodName);
	}

	/*
	 * ObjectUtil.set(Object obj,java.util.Map<String,Object> values,boolean
	 * ignoreCase)
	 */
	public static void set(Object obj, java.util.Map<String,Object> values, boolean ignoreCase, boolean throwErrIfNoMethod) {
		if (values == null)
			return;
		final Class clazz = obj.getClass();
		for (final String name : values.keySet()) {
			set(obj, clazz, name, values.get(name), ignoreCase, throwErrIfNoMethod);
		}
	}

	/*
	 * ObjectUtil.set(this,"Ftp.",xparams,true,true);
	 */
	public static void set(Object obj, String namePrefix, java.util.Map<String,Object> values, boolean ignoreCase, boolean throwErrIfNoMethod) {
		if (values == null)
			return;
		final Class clazz = obj.getClass();
		final int lP = namePrefix == null ? 0 : namePrefix.length();
		for (final String name : values.keySet()) {
			if (lP == 0 || name.startsWith(namePrefix))
				set(obj, clazz, name.substring(lP), values.get(name), ignoreCase, throwErrIfNoMethod);
		}
	}

	public static void set(Object obj, java.util.Map<String,Object> values, boolean ignoreCase) {
		set(obj, values, ignoreCase, true);
	}

	/*
	 * ObjectUtil.get(Object obj,boolean ignoreCase)
	 */
	public static java.util.Map get(Object obj, boolean ignoreCase) {
		final java.util.Map values = new java.util.HashMap();
		final Class clazz = obj.getClass();
		final java.lang.reflect.Method[] ms = clazz.getMethods();
		for (java.lang.reflect.Method m : ms) {
			Class pramTypes[] = m.getParameterTypes();
			if (pramTypes.length != 0)
				continue;
			String name = m.getName();
			if (name.equals("getClass"))
				continue;
			if (!name.startsWith("get"))
				continue;
			name = name.substring(3);
			if (ignoreCase) {
				name = name.toLowerCase();
			} else {
				char c0 = name.charAt(0);
				if (c0 >= 'A' && c0 <= 'Z')
					name = (char) (c0 + 'a' - 'A') + name.substring(1);
			}
			try {
				values.put(name, m.invoke(obj, Const.ZObjectArray1));
			} catch (Throwable ex) {
				throw HookedException.toRuntimeException(ex);
			}
		}
		return values;
	}

	/**
	 * 
	 * @param classAndField
	 * @return
	 * @since 2015-01-05
	 */
	static public Object getStaticFieldValue(String classAndField) {
		try {
			int p = classAndField.lastIndexOf('.');
			Class clazz = Class.forName(classAndField.substring(0, p));
			// System.out.println("clazz="+clazz);
			java.lang.reflect.Field field = clazz.getDeclaredField(classAndField.substring(p + 1));
			field.setAccessible(true);
			return field.get(null);
		} catch (Throwable ex) {
			throw HookedException.toRuntimeException(ex);
			// ex.printStackTrace();
		}
		// retur
	}

	/**
	 * 
	 * @param classAndField
	 * @param value
	 * @since 2015-01-05
	 */
	static public void setStaticFieldValue(String classAndField, Object value) {
		try {
			int p = classAndField.lastIndexOf('.');
			Class clazz = Class.forName(classAndField.substring(0, p));
			// System.out.println("clazz="+clazz);
			java.lang.reflect.Field field = clazz.getDeclaredField(classAndField.substring(p + 1));
			field.setAccessible(true);
			field.set(null, value);
			// return field.get(null);
		} catch (Throwable ex) {
			throw HookedException.toRuntimeException(ex);
			// ex.printStackTrace();
		}
		// retur
	}

	/**
	 * @功能:转换boolean
	 * @描述:
	 * @作者:吕亚南
	 * @时间:2016年9月30日 下午2:38:25
	 * @param o
	 * @return
	 */
	static public boolean objToBoolean(Object o) {
		if (o != null) {
			if (o instanceof Boolean)
				return ((Boolean) o).booleanValue();
			else if (o instanceof String) {
				String s = (String) o;
				if (s.equalsIgnoreCase("true"))
					return true;
				if (s.equalsIgnoreCase("false"))
					return false;
				if (s.equals("1"))
					return true;
				if (s.equals("0"))
					return false;
			} else if (o instanceof Integer) {
				Integer i = (Integer) o;
				if (1 == i)
					return true;
				if (0 == i)
					return false;
			}
		}
		return false;
	}
}
