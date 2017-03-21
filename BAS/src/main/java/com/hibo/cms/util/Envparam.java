package com.hibo.cms.util;

import java.io.Serializable;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.cache.Utils.SysCacheManager;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.shiro.util.LoginfoToken;
import com.hibo.cms.sys.utils.captcha.CaptchaSessionUtil;
import com.hibo.cms.token.model.TokenBas;
import com.hibo.cms.token.utils.TokenLogin;
import com.hibo.cms.user.model.User;

/**
 * <p>
 * 标题：用户访问环境变量
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年9月22日 下午1:45:11
 * </p>
 * <p>
 * 类全名：com.hibo.cms.util.Envparam
 * </p>
 * 作者：周雷 初审： 复审：
 */
public class Envparam {

	private static final Logger log = LoggerFactory.getLogger(Envparam.class);
	private static Session localSession = null;

	/**
	 * @功能:获取session
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月31日 下午1:14:39
	 * @return
	 */
	public static Session getSession() {
		Session session = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			if (null != subject) {
				session = subject.getSession();
			}
			if (log.isInfoEnabled()) {
				log.info("从Envparam获取Subject-sessionId=" + session.getId() + " loginInfo="
						+ (session != null && session.getAttribute("loginInfo") != null));
			}
		} catch (Exception e) {
			session = localSession;
			if (null == session) {
				String ip = IPUtil.LocalIp();
				session = new SimpleSession(ip);
				Serializable id = new JavaUuidSessionIdGenerator().generateId(session);
				((SimpleSession) session).setId(id);
				localSession = session;
			}
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("环境Envparam获取Subject失败-转获取服务器session-sessionId=" + session.getId());
			}
		}
		return session;
	}

	/**
	 * @功能:获取登录信息
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月31日 下午1:14:48
	 * @return
	 */
	public static LoginInfo getLoginInfo() {
		Session session = Envparam.getSession();
		if (null != session) {
			Object oo = session.getAttribute("loginInfo");
			if (null != oo && oo instanceof LoginInfo) {
				return (LoginInfo) oo;
			}
		}
		return null;
	}

	/**
	 * @功能:获取用户信息
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月31日 下午1:15:18
	 * @return
	 */
	public static User getUser() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		if (null != loginInfo) {
			return loginInfo.getUser();
		}
		return null;
	}
	
	/**
	 * @功能:获取角色信息
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月31日 下午1:15:18
	 * @return
	 */
	public static Set<String> getRoles() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		if (null != loginInfo) {
			return loginInfo.getRoleSet();
		}
		return null;
	}
	
	/**
	 * @功能:获取用户信息
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月31日 下午1:15:18
	 * @return
	 */
	public static String getSysId() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		if (null != loginInfo) {
			return loginInfo.getSysid();
		}
		return null;
	}
	
	/**
	 * @功能:获取用户类型
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月31日 下午1:15:18
	 * @return
	 */
	public static String getUtype() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		if (null != loginInfo) {
			return loginInfo.getType();
		}
		return null;
	}

	/**
	 * @功能:获取用户ID
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月31日 下午1:16:00
	 * @return
	 */
	public static String getUserId() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		if (null != loginInfo) {
			User user = loginInfo.getUser();
			if (user != null)
				return user.getUserid();
		}
		return null;
	}

	/**
	 * @功能:获取当前登录用户名
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月31日 下午1:16:17
	 * @return
	 */
	public static String getUserName() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		if (null != loginInfo) {
			User user = loginInfo.getUser();
			if (user != null)
				return user.getUsername();
		}
		return null;
	}

	public static boolean isAdmin() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		if (null != loginInfo) {
			return loginInfo.isAdmin();
		}
		return false;
	}

	public static boolean isMaintain() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		if (null != loginInfo) {
			return loginInfo.isMaintain();
		}
		return false;
	}

	public static Object getVFormSession(String key) {
		Session session = Envparam.getSession();
		if (null != session) {
			return session.getAttribute(key);
		}
		return null;
	}

	public static Object removeVFormSession(String key) {
		Session session = Envparam.getSession();
		if (null != session) {
			return session.removeAttribute(key);
		}
		return null;
	}

	public static void setVFormSession(String key, Object value) {
		Session session = Envparam.getSession();
		if (null != session) {
			session.setAttribute(key, value);
		}
	}

	public static void logout() {
		try {
			String username = Envparam.getUserName();
			if (!StrUtil.isnull(username)) {
				TokenBas token = TokenLogin.selectToken(username,Envparam.getUtype());
				if (null != token) {
					TokenLogin.deleTokenByTokenNo(token.getTokenNo());
					SysCacheManager.removeCache(TokenLogin.TOKEN_REDIS_NAME, token.getTokenNo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SecurityUtils.getSubject().logout();
	}

	public static boolean login(LoginfoToken token) {
		Subject Subject = SecurityUtils.getSubject();
		try {
			Subject.login(token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isLogin() {
		return Envparam.getUser() != null;
	}

	public static boolean login(String username, String password, String captcha, String smscaptcha) {
		AuthenticationToken token = new LoginfoToken(username, password, null, null, captcha, smscaptcha, null);
		Subject Subject = SecurityUtils.getSubject();
		try {
			Subject.login(token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @功能:快速登录
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月5日 下午4:57:48
	 * @param username
	 * @return
	 */
	public static boolean login(String userName,String utype) throws Exception{
		return login(userName, null, utype);
	}

	/**
	 * @功能:快速登录
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月5日 下午4:57:48
	 * @param username
	 * @return
	 */
	public static boolean login(String userName,String password,String utype) throws Exception{
		String code = StrUtil.randomStr(6);
		CaptchaSessionUtil.setSmsSession(Envparam.getSession(), code, 2);
		AuthenticationToken token = new LoginfoToken(userName, password, null, null, null, code, utype);
		Subject Subject = SecurityUtils.getSubject();
		Subject.login(token);
		return true;
	}
	/**
	 * @功能:后台快速登录
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月5日 下午4:57:48
	 * @param username
	 * @return
	 */
	public static boolean login(String userName,String password,String utype,String dbsource,String sysid) throws Exception{
		String code = StrUtil.randomStr(6);
		CaptchaSessionUtil.setSmsSession(Envparam.getSession(), code, 2);
		AuthenticationToken token = new LoginfoToken(userName, password, dbsource, sysid, null, code, utype);
		Subject Subject = SecurityUtils.getSubject();
		Subject.login(token);
		return true;
	}
}
