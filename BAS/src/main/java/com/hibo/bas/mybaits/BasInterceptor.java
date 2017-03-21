package com.hibo.bas.mybaits;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * <p>标题：修改mybaits的sql或结果</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年6月23日 上午11:44:42</p>
 * <p>类全名：com.hibo.bas.mybaits.BasInterceptor</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Intercepts(@Signature(method="handleResultSets", type=ResultSetHandler.class, args={Statement.class}))  
public class BasInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//Object target = invocation.getTarget();
		/*if (target instanceof ResultSetHandler) {
			InvocationHandler handler = new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					System.out.println("proxy:" + proxy.getClass().getName());
					System.out.println("method:" + method.getName());
					System.out.println("args:" + args[0].getClass().getName());
					return method.invoke(proxy, args);
				}
			};
			Object o = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),handler);
			return o;
		}*/
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
