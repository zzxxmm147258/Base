package com.hibo.cms.sys.shiro.chain;

import java.text.MessageFormat;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月13日 上午9:01:26</p>
 * <p>类全名：com.hibo.cms.sys.shiro.chain.ChainDefinitionsStringUtil</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public class ChainDefinitionsStringUtil {
	/**
	 * anon,authc,authcBasic,user是第一组认证过滤器
	 */
	//忽略
	public static final String ANON_STRING="anon";
	//认证
	public static final String AUTHC_STRING="authc";
	//证书认证
	public static final String AUTHCBASIC_STRING="authcBasic";
	//用户认证
	public static final String USER_STRING="user";
	//自定义拦截器
	public static final String SYSFILTER_STRING="sysfilter";
	
	/**
	 * perms,port,rest,roles,ssl是第二组授权过滤器
	 */
	public static final String PREMS_STRING="perms[\"{0}\"]";
	public static final String PORT_STRING="port[\"{0}\"]";
	public static final String REST_STRING="rest[\"{0}\"]";
	public static final String ROLES_STRING="roles[\"{0}\"]";
	public static final String OROLES_STRING="oroles[\"{0}\"]";
	public static final String SSL_STRING="ssl[\"{0}\"]";
	
	
	public static String format(String pattern, Object ... arguments) {
		return MessageFormat.format(pattern,arguments);
	}

}
