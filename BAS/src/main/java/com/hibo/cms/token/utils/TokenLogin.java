package com.hibo.cms.token.utils;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hibo.bas.constant.Message;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.ObjectId;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.cache.Utils.SysCacheManager;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.shiro.inter.SysAfterLogin;
import com.hibo.cms.sys.utils.cookie.CookieUtils;
import com.hibo.cms.token.dao.TokenBasMapper;
import com.hibo.cms.token.model.TokenBas;
import com.hibo.cms.util.Envparam;

/**
 * <p>标题：令牌登录</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月6日 下午2:33:13</p>
 * <p>类全名：com.hibo.cms.token.utils.TokenLogin</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Component
public class TokenLogin implements SysAfterLogin {

	public static final String TOKEN_KEY = "token_login_cookie_key";

	public static final String TOKEN_PARAM = "token";

	public static final String TOKEN_REDIS_NAME = "redis_token";

	private static TokenBasMapper tokenBasMapper;

	@Resource
	public void setTokenBasMapper(TokenBasMapper tokenBasMapper) {
		TokenLogin.tokenBasMapper = tokenBasMapper;
	}

	@Override
	public void afterLoginDo(HttpServletRequest request, HttpServletResponse response, LoginInfo loginInfo) {
		/*if (TokenLogin.isUsed(request, loginInfo.getType())) {
			try {
				TokenBas token = TokenLogin.selectToken(loginInfo.getUsername(), loginInfo.getType());
				boolean isSet = null == token ? true : token.getAccessTime().getTime() > System.currentTimeMillis();
				if (isSet) {
					String tokenKey = TokenLogin.setToken(request, response, loginInfo);
					request.setAttribute(TokenLogin.TOKEN_KEY, tokenKey);
				}else{
					request.setAttribute(TokenLogin.TOKEN_KEY, token.getTokenNo());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}
	
	public static String LoginDo(HttpServletRequest request, HttpServletResponse response, LoginInfo loginInfo) {
		if (TokenLogin.isUsed(request, loginInfo.getType())) {
			try {
				TokenBas token = TokenLogin.selectToken(loginInfo.getUsername(), loginInfo.getType());
				boolean isSet = null == token ? true : token.getAccessTime().getTime() > System.currentTimeMillis();
				if (isSet) {
					return TokenLogin.setToken(request, response, loginInfo);
				}else{
					return token.getTokenNo();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean tokenAccess(ServletRequest request, ServletResponse response) {
		if (Envparam.isLogin()) {
			return true;
		}
		String utype = request.getParameter("utype");
		if (TokenLogin.isUsed(request, utype)) {
			String tokenKey = request.getParameter(TokenLogin.TOKEN_PARAM);
			if (StrUtil.isnull(tokenKey)) {
				HttpServletRequest req = WebUtils.toHttp(request);
				HttpServletResponse res = WebUtils.toHttp(response);
				tokenKey = CookieUtils.getCookie(req, res, TokenLogin.TOKEN_KEY);
			}
			return !tokenEffective(tokenKey, utype).isNoLogin();
		}
		return false;
	}
	public static Message tokenEffective(String tokenKey,String utype){
		Message message = new Message(true);
		message.setNoLogin(true);
		if (Envparam.isLogin()) {
			message.setNoLogin(false);
			return message;
		}
		try {
			if (!StrUtil.isnull(tokenKey)) {
				TokenBas token = null;
				if (SysCacheManager.isExistsGlobalCache(TokenLogin.TOKEN_REDIS_NAME, tokenKey)) {
					token = (TokenBas) SysCacheManager.getFromGlobalCache(TokenLogin.TOKEN_REDIS_NAME, tokenKey);
				}
				if (null == token) {
					token = tokenBasMapper.selectByPrimaryKey(tokenKey);
				}
				if (null != token && !StrUtil.isnull(token.getUsername())) {
					long lTime = token.getAccessTime().getTime();
					long sTime = System.currentTimeMillis();
					if (lTime > sTime) {
						boolean isLogin = Envparam.login(token.getUsername(), null, StrUtil.isnull(utype)?"10":utype, null, token.getAttr2());
						message.setNoLogin(!isLogin);
						message.setMessage(isLogin?null:"登录失败");
						return message;
					} else {
						TokenLogin.deleTokenByTokenNo(tokenKey);
						SysCacheManager.removeCache(TokenLogin.TOKEN_REDIS_NAME, tokenKey);
						message.setMessage("自动登录已过期,请重新登录");
						return message;
					}
				}else{
					message.setMessage("在其他终端登录过,请重新登录");
					return message;
				}
			}else{
				message.setMessage("未登录,请登录");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
			return message;
		}
		return message;
	}
	private static String setToken(HttpServletRequest request, HttpServletResponse response, LoginInfo loginInfo) {
		int dbTimelong = 3600 * DataConfig.getIntConfig("BAS.TOKEN.HOUR_DB", 168);
		String tokenKey = ObjectId.getUUId();
		//CookieUtils.addCookie(request, response, TokenLogin.TOKEN_KEY, tokenKey, dbTimelong);
		TokenBas tokenBas = new TokenBas();
		tokenBas.setTokenNo(tokenKey);
		tokenBas.setUserid(loginInfo.getUser().getUserid());
		tokenBas.setUsername(loginInfo.getUsername());
		long DateTime = dbTimelong * 1000 + System.currentTimeMillis();
		Date date = new Date(DateTime);
		tokenBas.setAccessTime(date);
		tokenBas.setAttr1(loginInfo.getType());
		tokenBas.setAttr2(loginInfo.getSysid());
		TokenLogin.insertToken(tokenBas);
		int cacheTimelong = DataConfig.getIntConfig("BAS.TOKEN.HOUR_CACHE", 168);
		SysCacheManager.putToGlobalCache(TokenLogin.TOKEN_REDIS_NAME, tokenKey, tokenBas, 3600 * cacheTimelong);
		return tokenKey;
	}

	public static int deleTokenByUsername(String username, String utype) {
		return tokenBasMapper.deleteByPrimaryUserName(username, utype);
	}

	public static int deleTokenByTokenNo(String tokenNo) {
		return tokenBasMapper.deleteByPrimaryKey(tokenNo);
	}

	public static TokenBas selectToken(String username, String utype) {
		TokenBas tokenBas = null;
		if (!StrUtil.isnull(username)) {
			tokenBas = tokenBasMapper.selectByPrimaryUserName(username, utype);
		}
		return tokenBas;
	}

	@Transactional
	public static int insertToken(TokenBas tokenBas) {
		tokenBasMapper.deleteByPrimaryUserName(tokenBas.getUsername(), tokenBas.getAttr1());
		return tokenBasMapper.insert(tokenBas);
	}

	public static boolean isUsed(ServletRequest request, String utype) {
		// 访问的URI
		//String requestURI = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
		//utype = (StrUtil.isnull(utype) && (requestURI.startsWith("/admin") || requestURI.startsWith("/main"))) ? "20" : utype;
		boolean isused = "true".equals(DataConfig.getConfig("BAS.TOKEN.ISUSED", "false"));
		//return isused && !"20".equals(utype);
		return isused;
	}
}
