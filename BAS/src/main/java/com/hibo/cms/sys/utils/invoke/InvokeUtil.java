package com.hibo.cms.sys.utils.invoke;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.hibo.bas.spring.SpringContextUtil;

/**
 * <p>标题：java反射调用类</p>
 * <p>功能：通过spring的bean或java全路径来执行java类 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月8日 下午3:34:57</p>
 * <p>类全名：com.hibo.cms.sys.utils.invoke.InvokeUtil</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public final class InvokeUtil {
	
    /**
     * @功能:获取包装类
     * @作者:周雷
     * @时间:2015年12月12日 下午12:13:12
     * @param type
     * @return
     */
    public static  Class<?> wrapper(Class<?> type){
        if(type == null){
            return null;
        }else if(type.isPrimitive()){
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }
        return type;
    }
	
    /**
     * @功能:根据路径加载类
     * @作者:周雷
     * @时间:2015年12月14日 上午10:31:00
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
	public static Class<?> ClassForName(String className) throws ClassNotFoundException {
		return Class.forName(className.trim());
	}

	/**
	 * @功能:new无参数对象实例
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:30:32
	 * @param className
	 * @return
	 */
	public static  Object newInstance(String className) {
		return newInstance(className, new Object[0]);
	}
	
	/**
	 * @功能:new有参数对象实例
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:30:03
	 * @param className
	 * @param args
	 * @return
	 */
	public static Object newInstance(String className,Object[] args) {
		try {
			Class<?> clazz = ClassForName(className);
			Constructor<?> constructor = findConstructors(clazz,args);
			if(null==constructor){
				return null;
			}
			return constructor.newInstance(args);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @功能:查找构造器
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:29:46
	 * @param clazz
	 * @param argsClazzes
	 * @return
	 */
	private static  Constructor<?> findConstructors(Class<?> clazz, Object[] args) {
		if(null==args){
			args = new Object[0];
		}
		return findConstructors(clazz, argsToClasses(args));
	}
	
	/**
	 * @功能:查找构造器
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:29:46
	 * @param clazz
	 * @param argsClazzes
	 * @return
	 */
	private static  Constructor<?> findConstructors(Class<?> clazz, Class<?>[] argsClazzes) {
		Constructor<?>[] constructors = clazz.getConstructors();
		for (Constructor<?> constructor : constructors) {
			if(methodMatch(constructor.getParameterTypes(), argsClazzes)){
				return constructor;
			}
		}
		return null;
	}

	/**
	 * @功能:调用bean_method对象方法
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:43:00
	 * @param bean_method 类全路径名.方法名
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeFromBean(String bean_method, Object[] args) throws Exception{
		int p = bean_method.trim().lastIndexOf('.');
		if(p<0){
			throw new RuntimeException("bean_method参数有误:"+bean_method);
		}
		String beanName = bean_method.substring(0, p);
		String methodName = bean_method.substring(p+1);
		Object obj = SpringContextUtil.getBean(beanName);
		return invokeFromBean(obj, methodName, args);
	}
	
	/**
	 * @功能:调用spring的bean名称对象的methodName方法
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:43:14
	 * @param beanName
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeFromBean(String beanName,String methodName, Object[] args) throws Exception{
		Object obj = SpringContextUtil.getBean(beanName.trim());
		return invokeFromBean(obj, methodName.trim(), args);
	}
	
	/**
	 * @功能:调用java的ClassName名称对象的methodName方法
	 * @作者:周雷
	 * @时间:2015年12月12日 下午3:51:46
	 * @param bean_method 类全路径名.方法名
	 * @param constructorArgs 构造器参数数组
	 * @param methodArgs 方法参数数组
	 * @return
	 * @throws Exception
	 */
	public static Object invokeNewInstance(String class_method, Object[] constructorArgs,Object[] methodArgs) throws Exception{
		int p = class_method.trim().lastIndexOf('.');
		if(p<0){
			throw new RuntimeException("bean_method参数有误:"+class_method);
		}
		String className = class_method.substring(0, p);
		String methodName = class_method.substring(p+1);
		Object newObj = newInstance(className, constructorArgs);
		return invokeFromBean(newObj, methodName, methodArgs);
	}
	
	/**
	 * @功能:调用java的ClassName名称对象的methodName方法
	 * @作者:周雷
	 * @时间:2015年12月12日 下午3:51:46
	 * @param bean_method 类全路径名.方法名
	 * @param methodArgs 方法参数数组
	 * @return
	 * @throws Exception
	 */
	public static Object invokeNewInstance(String class_method,Object[] methodArgs) throws Exception{
		int p = class_method.trim().lastIndexOf('.');
		if(p<0){
			throw new RuntimeException("bean_method参数有误:"+class_method);
		}
		String className = class_method.substring(0, p);
		String methodName = class_method.substring(p+1);
		Object newObj = newInstance(className);
		return invokeFromBean(newObj, methodName, methodArgs);
	}
	
	/**
	 * @功能:调用java的ClassName名称对象的methodName方法
	 * @作者:周雷
	 * @时间:2015年12月12日 下午3:51:46
	 * @param className 类全路径名
	 * @param methodName 方法名
	 * @param methodArgs 方法参数数组
	 * @return
	 * @throws Exception
	 */
	public static  Object invokeNewInstance(String className,String methodName,Object[] methodArgs) throws Exception{
		Object newObj = newInstance(className);
		return invokeFromBean(newObj, methodName.trim(), methodArgs);
	}
	
	/**
	 * @功能:调用java的ClassName名称对象的methodName方法
	 * @作者:周雷
	 * @时间:2015年12月12日 下午3:51:46
	 * @param className 类全路径名
	 * @param methodName 方法名
	 * @param constructorArgs 构造器参数数组
	 * @param methodArgs 方法参数数组
	 * @return
	 * @throws Exception
	 */
	public static  Object invokeNewInstance(String className,String methodName, Object[] constructorArgs,Object[] methodArgs) throws Exception{
		Object newObj = newInstance(className, constructorArgs);
		return invokeFromBean(newObj, methodName.trim(), methodArgs);
	}
	
	/**
	 * @功能:调用java的ClassName名称的methodName静态方法
	 * @作者:周雷
	 * @时间:2015年12月12日 下午3:51:46
	 * @param class_method 类全路径名.方法名
	 * @param methodName 方法名
	 * @param methodArgs 方法参数数组
	 * @return
	 * @throws Exception
	 */
	public static Object invokeStaticMetod(String class_method,Object[] methodArgs) throws Exception{
		int p = class_method.trim().lastIndexOf('.');
		if(p<0){
			throw new RuntimeException("bean_method参数有误:"+class_method);
		}
		String className = class_method.substring(0, p);
		String methodName = class_method.substring(p+1);
		Class<?> clazz = ClassForName(className);
		return invokeFromBean(clazz, methodName, methodArgs);
	}
	
	/**
	 * @功能:调用java的ClassName名称的methodName静态方法
	 * @作者:周雷
	 * @时间:2015年12月12日 下午3:51:46
	 * @param className 类全路径名
	 * @param methodName 方法名
	 * @param constructorArgs 构造器参数数组
	 * @param methodArgs 方法参数数组
	 * @return
	 * @throws Exception
	 */
	public static  Object invokeStaticMetod(String className,String methodName,Object[] methodArgs) throws Exception{
		Class<?> clazz = ClassForName(className);
		return invokeFromBean(clazz, methodName.trim(), methodArgs);
	}
	
	/**
	 * @功能:调用静态的methodName方法
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:43:28
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeStaticMetod(Class<?> clazz, String methodName, Object[] args) throws Exception {
		if(null==clazz){
			throw new RuntimeException("The reflected Class is not available");
		}
		if(null==args){
			args = new Object[0];
		}
		Class<?>[] argsClazz = new Class<?>[args.length];
		for (int i = 0; i < argsClazz.length; i++) {
			Object o = args[i];
			if(null==o){
				argsClazz[i] = null;
			}else{
				argsClazz[i]=o.getClass();
			}
		}
		Method method = findMethod(clazz, methodName, argsClazz);
		if(null==method){
			throw new NoSuchMethodException();
		}
		return method.invoke(clazz, args);
	}
	
	/**
	 * @功能:调用Obj对象的methodName方法
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:43:28
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeFromBean(Object obj, String methodName, Object[] args) throws Exception {
		if(null==obj){
			throw new RuntimeException("The reflected object is not available");
		}
		if(null==args){
			args = new Object[0];
		}
		Class<?> clazz = obj.getClass();
		Class<?>[] argsClazz = new Class<?>[args.length];
		for (int i = 0; i < argsClazz.length; i++) {
			Object o = args[i];
			if(null==o){
				argsClazz[i] = null;
			}else{
				argsClazz[i]=o.getClass();
			}
		}
		Method method = findMethod(clazz, methodName, argsClazz);
		if(null==method){
			throw new NoSuchMethodException();
		}
		return method.invoke(obj, args);
	}
	
	/**
	 * @功能:调用静态的methodName方法
	 * @作者:周雷
	 * @时间:2015年12月14日 上午10:43:36
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeFromBean(Class<?> clazz, String methodName, Object[] args) throws Exception {
		if(null==clazz){
			throw new RuntimeException("The reflected Class is not available");
		}
		if(null==args){
			args = new Object[0];
		}
		Class<?>[] argsClazz = new Class<?>[args.length];
		for (int i = 0; i < argsClazz.length; i++) {
			Object o = args[i];
			if(null==o){
				argsClazz[i] = null;
			}else{
				argsClazz[i]=o.getClass();
			}
		}
		Method method = findMethod(clazz, methodName, argsClazz);
		if(null==method){
			throw new NoSuchMethodException();
		}
		return method.invoke(clazz, args);
	}
	
	/**
	 * @功能:查找匹配方法
	 * @作者:周雷
	 * @时间:2015年12月12日 下午12:19:41
	 * @param clazz
	 * @param name
	 * @param args
	 * @return
	 */
	public static Method findMethod(Class<?> clazz,String name,Object[] args){
		if(null==args){
			args = new Object[0];
		}
		return findMethod(clazz, name, argsToClasses(args));
	}
	
	/**
	 * @功能:查找匹配方法
	 * @作者:周雷
	 * @时间:2015年12月12日 下午12:19:41
	 * @param clazz
	 * @param name
	 * @param args
	 * @return
	 */
	public static Method findMethod(Class<?> clazz,String name,Class<?>[] argsClass){
		//获取公有方法
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if(method.getName().equals(name)){
				boolean isMethod = methodMatch(method.getParameterTypes(),argsClass);
				if(isMethod){
					return method;
				}
			}
		}
		return null;
	}
	
	/**
	 * @功能:方法参数类型匹配
	 * @作者:周雷
	 * @时间:2015年12月12日 下午12:19:01
	 * @param parameterTypes
	 * @param argsClass
	 * @return
	 */
	private static boolean methodMatch(Class<?>[] parameterTypes, Class<?>[] argsClass) {
		if(parameterTypes.length!=argsClass.length){
			return false;
		}
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> pc = wrapper(parameterTypes[i]);
			Class<?> ac = wrapper(argsClass[i]);
			if(null!=ac){
				if(!pc.isAssignableFrom(ac)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @功能:通过参数获取Classes
	 * @作者:周雷
	 * @时间:2015年12月12日 下午3:01:14
	 * @param args
	 * @return
	 */
	private static Class<?>[] argsToClasses(Object[] args){
		Class<?>[] argsClass = new Class<?>[args.length];
		for (int i = 0; i < argsClass.length; i++) {
			if(null==args[i]){
				argsClass[i]=null;
			}else{
				argsClass[i]=args[i].getClass();
			}
		}
		return argsClass;
	}
}
