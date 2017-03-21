package com.hibo.cms.sys.shiro.util;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import com.hibo.bas.exception.shiro.RunAuthenticationException;
import com.hibo.cms.sys.cache.Utils.SysCacheManager;
import com.hibo.cms.sys.utils.captcha.CaptchaSessionUtil;

/**
 * <p>标题：验证</p>
 * <p>功能：密码验证及录入次数 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 下午8:30:57</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.SysHashedCredentialsMatcher</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class SysHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private PasswordHelper passwordHelper;

	public PasswordHelper getPasswordHelper() {
		return passwordHelper;
	}

	public void setPasswordHelper(PasswordHelper passwordHelper) {
		this.passwordHelper = passwordHelper;
	}

	@Override
	public void setHashIterations(int hashIterations) {
		super.setHashIterations(hashIterations);
		passwordHelper.setHashIterations(hashIterations);
	}

	@Override
	public void setHashAlgorithmName(String hashAlgorithmName) {
		super.setHashAlgorithmName(hashAlgorithmName);
		passwordHelper.setAlgorithmName(hashAlgorithmName);
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		// 过滤手机验证码
		LoginfoToken logintoken = (LoginfoToken) token;
		String smscaptcha = logintoken.getSmscaptcha();
		char[] loginpPassword = logintoken.getPassword();
		if (null != smscaptcha && !smscaptcha.isEmpty()) {
			boolean ispass = CaptchaSessionUtil.checkSMS4Session(smscaptcha,true);
			if (!ispass) {
				throw new RunAuthenticationException("短息验证码错误!");
			}
			if (null == loginpPassword || loginpPassword.length <= 0) {
				return ispass;
			}
		}else{
			if(null==loginpPassword||loginpPassword.length<=0){
				throw new RunAuthenticationException("用户名或密码不能为空!");
			}
		}
		boolean isOk = super.doCredentialsMatch(logintoken, info);
		String principals = info.getPrincipals().toString();
		String CacheName = "PASSWORD_LOGIN_NUM";
		String CacheKey = "PASSWORD_LOGIN_" + principals;
		boolean isExist = SysCacheManager.isExistsGlobalCache(CacheName, CacheKey);
		int num = 0;
		if (isExist) {
			num = (int) SysCacheManager.getFromGlobalCache(CacheName, CacheKey);
		}
		int minitue = 30;
		int time = minitue*60;
		// 判断密码录入次数
		if (num >= 5) {
			Long minitue01 = SysCacheManager.getGlobalCacheRestTime(CacheName, CacheKey);
			minitue = (int) (minitue01/60);
			int second = (int) (minitue01%60);
			throw new RunAuthenticationException("此用户密码错误 "+num+" 次,将于"+minitue+"分"+second+"秒后解锁!");
		} else {
			if(!isOk){
				SysCacheManager.putToGlobalCache(CacheName, CacheKey, ++num, time);
			}else{
				SysCacheManager.removeToGlobalCache(CacheName, CacheKey);
			}
		}
		return isOk;
	}

}
