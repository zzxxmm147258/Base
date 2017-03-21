package com.hibo.cms.sys.utils.cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.util.Assert;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月15日 下午3:11:35</p>
 * <p>类全名：com.hibo.ebusi.utils.WebUtils</p>
 * 作者：马骏达
 * 初审：
 * 复审：
 */
public final class CookieUtils {

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效期(单位: 秒)
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			Integer maxAge) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			SimpleCookie cookie = new SimpleCookie(name);
			cookie.setValue(value);
			if (maxAge != null) {
				cookie.setMaxAge(maxAge);
			}
			cookie.saveTo(request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie名称
	 * @return 若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Assert.notNull(request);
		Assert.hasText(name);
		SimpleCookie cookie = new SimpleCookie(name);
		String result = cookie.readValue(request, response);
		return result;
	}

	/**
	 * 移除cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param path
	 *            路径
	 * @param domain
	 *            域
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			SimpleCookie cookie = new SimpleCookie(name);
			cookie.setValue(null);
			cookie.setMaxAge(0);
			cookie.saveTo(request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
