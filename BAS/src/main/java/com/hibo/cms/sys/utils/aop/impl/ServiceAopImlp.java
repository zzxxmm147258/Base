package com.hibo.cms.sys.utils.aop.impl;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.bas.dbutil.DataSource;
import com.hibo.bas.dbutil.DataSourceUtil;
import com.hibo.bas.dbutil.DynamicDataSource;
import com.hibo.cms.sys.utils.aop.IServiceAop;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月11日 下午4:47:31</p>
 * <p>类全名：com.hibo.cms.sys.utils.aop.impl.ServiceAopImlp</p>
 * 作者：Administrator
 * 初审：
 * 复审：
 */
public class ServiceAopImlp implements IServiceAop {
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
	
	@Override
	public void before(JoinPoint jp) {
		String dataSource = DataSourceUtil.getDataSource();
		if(StringUtils.isEmpty(dataSource)){
			throw new RuntimeException("无法获取用户选择的链接。请重新登陆。。。。。");
		}
		DataSource.setDataSource(dataSource);
		if(log.isDebugEnabled()){
			String msg = "DB "+jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName();
			log.debug(msg);
		}
	}
	
	@Override
	public Object around(ProceedingJoinPoint jp){
		String dataSource = DataSourceUtil.getDataSource();
		if(StringUtils.isEmpty(dataSource)){
			throw new RuntimeException("无法获取用户选择的链接。请重新登陆。。。。。");
		}
		DataSource.setDataSource(dataSource);
		if(log.isDebugEnabled()){
			String msg = "DB "+jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName();
			log.debug(msg);
		}
		Object r = null;
		try {
			r = jp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if(log.isDebugEnabled()){
			String msg = jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName()+"已执行完";
			log.debug(msg);
		}
		return r;
	}

}
