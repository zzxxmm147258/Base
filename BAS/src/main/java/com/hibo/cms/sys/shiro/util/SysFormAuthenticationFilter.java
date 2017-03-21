package com.hibo.cms.sys.shiro.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.bas.exception.shiro.RunAuthenticationException;
import com.hibo.bas.spring.SpringContextUtil;
import com.hibo.bas.spring.SpringWebUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.shiro.inter.SysAfterLogin;
import com.hibo.cms.sys.utils.captcha.CaptchaSessionUtil;
import com.hibo.cms.sys.utils.login.LoginUtil;
import com.hibo.cms.util.Envparam;

/**
 * <p>
 * 标题：登录
 * </p>
 * <p>
 * 功能：登录表单的数据及登录
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年7月22日 上午11:47:11
 * </p>
 * <p>
 * 类全名：com.hibo.cms.sys.shiro.util.SysFormAuthenticationFilter
 * </p>
 * 作者：Victor 初审： 复审：
 */
public class SysFormAuthenticationFilter extends FormAuthenticationFilter {

	// 账套号
	public static final String DEFAULT_BD_PARAM = "db";
	// 系统号
	public static final String DEFAULT_SYS_PARAM = "sysid";
	// 验证码
	public static final String DEFAULT_SYS_CAPTCHA = "captcha";
	// 短信验证码
	public static final String DEFAULT_SYS_SMSCAPTCHA = "smscaptcha";
	// 登录路径类型
	public static final String DEFAULT_SYS_UTYPE = "sys_utype";
	private static final Logger log = LoggerFactory.getLogger(SysFormAuthenticationFilter.class);
	private String dbsource = DEFAULT_BD_PARAM;
	private String sysid = DEFAULT_SYS_PARAM;
	private String captcha = DEFAULT_SYS_CAPTCHA;
	private String smscaptcha = DEFAULT_SYS_SMSCAPTCHA;
	private String sys_utype = DEFAULT_SYS_UTYPE;

	@Override
	protected LoginfoToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String dbsource = getDbsource(request);
		String sysid = getSysid(request);
		String captcha = getCaptcha(request);
		String smscaptcha = getSmscaptcha(request);
		String sys_utype = getSysUtype(request);
		return new LoginfoToken(username, password, rememberMe, host, dbsource, sysid, captcha, smscaptcha, sys_utype,
				null);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		String requestURI = getPathWithinApplication(request);
		if (requestURI.equals(getLoginUrl())) {
			request.setAttribute(DEFAULT_SYS_UTYPE, "20");
		}
		return super.isAccessAllowed(request, response, mappedValue) && !isLoginRequest(request, response);
	}

	@Override
	protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
		String requestURI = getPathWithinApplication(request);
		return LoginFilterUtil.isLogingUrl(requestURI, getLoginUrl());
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		LoginfoToken token = createToken(request, response);
		if (token == null) {
			String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
					+ "must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}
		try {
			String username = token.getUsername();
			if (StringUtils.isEmpty(username)) {
				throw new RunAuthenticationException("用户名或密码不能为空!");
			}
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setUsername(username);
			loginInfo.setWork(token.getDbsource());
			loginInfo.setSysid(token.getSysid());
			Envparam.setVFormSession("loginInfo", loginInfo);
			boolean isPass = doCheckCptcha(token.getCaptcha());
			if (!isPass) {
				throw new RunAuthenticationException("验证码错误");
			}
			Subject subject = getSubject(request, response);
			if (log.isInfoEnabled()) {
				log.info("Login authentication:准备登录认证。。。。。。。。。。。");
			}
			subject.login(token);
			if (log.isInfoEnabled()) {
				log.info("Login authentication:登录认证成功结束。。。。。。。。。。。");
			}
			String isToMain = WebUtils.getCleanParam(request, "isToMain");
			if ("true".equals(isToMain)) {
				WebUtils.getAndClearSavedRequest(request);
			}
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			if (log.isErrorEnabled()) {
				log.error("验证失败:" + e.getMessage());
			}
			return onLoginFailure(token, e, request, response);
		}
	}

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		super.setFailureAttribute(request, ae);
		request.setAttribute(getFailureKeyAttribute() + "_message", ae.getMessage());
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		if (log.isInfoEnabled()) {
			log.info("Login authentication:登录成功路径开始跳转。。。。。。。。。。。");
		}
		String successUrl = null;
		boolean isContext = true;
		SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
		if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
			successUrl = savedRequest.getRequestUrl();
			isContext = false;
		}

		if (successUrl == null) {
			successUrl = getSuccessUrl();
		}
		String loginUri = (String) Envparam.getVFormSession(LoginFilterUtil.LOGIN_URL_KEY);
		String controllerUrl = LoginUtil.getLoginControllerUrl(loginUri);
		String path = SpringWebUtil.getContextPath();
		String requestURI = getPathWithinApplication(request);
		if (null != controllerUrl && isContext
				&& (pathsMatch(requestURI, controllerUrl) || pathsMatch(requestURI, controllerUrl))) {
			successUrl = LoginUtil.getLoginSuccessUrl(loginUri);
		}
		if (log.isInfoEnabled()) {
			log.info("successUrl:登录成功路径开始跳转=" + successUrl);
		}
		if (log.isInfoEnabled()) {
			log.info("path:登录成功路径开始跳转=" + path);
		}
		// 保存地址用
		if (successUrl.startsWith(path)) {
			path = successUrl.substring(path.length());
			if (log.isInfoEnabled()) {
				log.info("successUrl--path:登录成功路径开始跳转=" + path);
			}
		} else {
			path = successUrl;
		}
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
		String[] sysAfterLogin = SpringContextUtil.getBeanNameByType(SysAfterLogin.class);
		if (null != sysAfterLogin && sysAfterLogin.length > 0) {
			for (String name : sysAfterLogin) {
				SysAfterLogin iSysAfterLogin = SpringContextUtil.getBean(name);
				iSysAfterLogin.afterLoginDo(httpServletRequest, httpServletResponse, Envparam.getLoginInfo());
			}
		}
		Envparam.setVFormSession(LoginFilterUtil.SUCCESS_URL_KEY, requestURI);
		String type = WebUtils.toHttp(request).getParameter("dataType");
		if ("json".equalsIgnoreCase(type)) {
			path = "/mainsuccess";
		}
		if (log.isInfoEnabled()) {
			log.info("Login authentication:登录成功路径开始跳转=" + path);
		}
		WebUtils.issueRedirect(request, response, path, null, true);
	}

	/**
	 * 验证验证码
	 * 
	 * @param captcha
	 * @return
	 */
	private boolean doCheckCptcha(String captcha) {
		String exitCode = (String) Envparam.getSession().getAttribute(CaptchaSessionUtil.KEY_CAPTCHA);
		// 给APP登录使用
		if (exitCode == null && captcha == null)
			return true;
		return (null != exitCode && exitCode.equalsIgnoreCase(captcha)) || !LoginUtil.isCaptcha();
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		return super.preHandle(request, response);
	}

	private String getSysid(ServletRequest request) {
		return WebUtils.getCleanParam(request, getSysid());
	}

	protected String getDbsource(ServletRequest request) {
		return WebUtils.getCleanParam(request, getDbsource());
	}

	protected String getSysUtype(ServletRequest request) {
		String utype = WebUtils.getCleanParam(request, DEFAULT_SYS_UTYPE);
		if (StrUtil.isnull(utype)) {
			utype = StrUtil.obj2str(request.getAttribute(DEFAULT_SYS_UTYPE));
		}
		;
		return utype;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptcha());
	}

	public String getSmscaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getSmscaptcha());
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

}
