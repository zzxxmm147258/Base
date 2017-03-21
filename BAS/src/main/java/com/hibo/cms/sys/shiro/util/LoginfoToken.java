package com.hibo.cms.sys.shiro.util;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.hibo.cms.sys.model.login.LoginInfo;

/**
 * <p>标题：信息</p>
 * <p>功能：用户提交登录的信息 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 下午8:31:36</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.LoginfoToken</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class LoginfoToken extends UsernamePasswordToken {
	private static final long serialVersionUID = -8925751149471263944L;
	private String dbsource;
	private String sysid;
	private String captcha;
	private String smscaptcha;
	private String sys_utype;
	private String origin;//weixin:微信登陆；pc:pc登录
	
	public LoginfoToken(final String username, final String password,String smscaptcha, String sys_utype,String origin) {
		this(username, password, false, null, null, null, null, smscaptcha, sys_utype,origin);
	}
	
	/**
	 * 登录参数
	 * @param username
	 * @param password
	 * @param dbsource
	 * @param sysid
	 * @param captcha
	 * @param smscaptcha
	 * @param sys_utype
	 */
	public LoginfoToken(final String username, final String password, String dbsource, String sysid, String captcha, String smscaptcha, String sys_utype) {
		this(username, password, false, null, dbsource, sysid, captcha, smscaptcha, sys_utype,null);
	}
	
	/**
	 * 登录参数
	 * @param username 用户名
	 * @param password 密码
	 * @param dbsource 账套
	 * @param sysid 系统号
	 * @param captcha 验证码
	 * @param smscaptcha 短信验证码
	 * @param sys_utype 用户类型
	 * @param origin //weixin:微信登陆;pc:pc登录
	 */
	public LoginfoToken(final String username, final String password, String dbsource, String sysid, String captcha, String smscaptcha, String sys_utype,String origin) {
		this(username, password, false, null, dbsource, sysid, captcha, smscaptcha, sys_utype,origin);
	}
	public LoginfoToken(final String username, final String password, final String host, String dbsource, String sysid, String captcha, String smscaptcha, String sys_utype,String origin) {
		this(username, password, false, host, dbsource, sysid, captcha, smscaptcha, sys_utype,origin);
	}

	public LoginfoToken(final String username, final String password, final boolean rememberMe, final String host,String dbsource, String sysid, String captcha, String smscaptcha,String sys_utype,String origin) {
		super(username, password, rememberMe, host);
		this.dbsource = dbsource;
		this.sysid = sysid;
		this.captcha = captcha;
		this.smscaptcha = smscaptcha;
		this.sys_utype = sys_utype;
		if(null==origin||origin.isEmpty()){
			origin = LoginInfo.ORIGIN_PC;
		}
		this.origin = origin.toUpperCase();
	}

	public String getDbsource() {
		return dbsource;
	}

	public void setDbsource(String dbsource) {
		this.dbsource = dbsource;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getSmscaptcha() {
		return smscaptcha;
	}

	public void setSmscaptcha(String smscaptcha) {
		this.smscaptcha = smscaptcha;
	}

	public String getSys_utype() {
		return sys_utype;
	}

	public void setSys_utype(String sys_utype) {
		this.sys_utype = sys_utype;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
