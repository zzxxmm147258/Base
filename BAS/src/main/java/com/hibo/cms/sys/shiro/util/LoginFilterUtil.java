package com.hibo.cms.sys.shiro.util;

import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;

import com.hibo.cms.sys.utils.login.LoginUtil;
import com.hibo.cms.util.Envparam;

/** <p>标题：登录拦截工具</p>
 * <p>功能：处理拦截登陆的URL </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月7日 下午2:00:20</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.LoginFilterUtil</p>
 * 作者：Administrator
 * 初审：
 * 复审：
 */
public class LoginFilterUtil{
	private static PatternMatcher pathMatcher = new AntPathMatcher();
	
	public static String SUCCESS_URL_KEY = "SuccessUrl";
	public static String LOGIN_URL_KEY = "LoginUrl";
	
	/**
	 * 返回拦截路径对应的登陆地址URI
	 * @param requestURI
	 * @param defaultLoginUrl
	 * @return
	 */
	public static String getLoginUrl(String requestURI,String defaultLoginUrl){
		String pattern = defaultLoginUrl;
		if(!LoginUtil.isLoginEmpty()){
			for (String loginUri:LoginUtil.loginUriSet()) {
				//判断是否是拦截的地址
				boolean isZ = pathsMatch(LoginUtil.getLoginFilterUrl(loginUri), requestURI)||pathsMatch(LoginUtil.getLoginControllerUrl(loginUri), requestURI);
				if (isZ) {
					//返回登录拦截的地址
					pattern  = LoginUtil.getLoginControllerUrl(loginUri);
					break;
				}
			}
		}
		return pattern;
	}

	/**
	 * URI路径校验
	 * @param pattern
	 * @param source
	 * @return
	 */
	public static boolean pathsMatch(String pattern, String source) {
		return pathMatcher.matches(pattern, source);
	}
	
	/**
	 * 是否登录地址
	 * @param requestURI
	 * @param defaultLoginUrl
	 * @return
	 */
	public static boolean isLogingUrl(String requestURI,String defaultLoginUrl){
		if(!LoginUtil.isLoginEmpty()){
			for (String loginUri:LoginUtil.loginUriSet()) {
				//判断是否是拦截的地址
				boolean isZ = pathsMatch(LoginUtil.getLoginControllerUrl(loginUri), requestURI);
				if (isZ) {
					Envparam.setVFormSession(LOGIN_URL_KEY, loginUri);
					return isZ;
				}
			}
		}		
		if (pathsMatch(defaultLoginUrl, requestURI)) {
			Envparam.setVFormSession(LOGIN_URL_KEY, defaultLoginUrl);
			return true;
		}
		return false;
	}
	
	
	
}
