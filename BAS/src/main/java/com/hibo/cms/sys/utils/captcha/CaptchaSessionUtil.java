package com.hibo.cms.sys.utils.captcha;

import org.apache.shiro.session.Session;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.util.Envparam;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月25日 下午3:36:21</p>
 * <p>类全名：com.hibo.cms.sys.utils.captcha.CaptchaSessionUtil</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class CaptchaSessionUtil {
	
	public static final String KEY_CAPTCHA = "SE_KEY_CAPTCHA_CODE";
	
	public static final String KEY_SMSCAPTCHA = "SE_KEY_SMSCAPTCHA_CODE";
	
	public static void setSessionCaptcha(String captcha){
		Session session = Envparam.getSession();
		session.removeAttribute(KEY_CAPTCHA);
		session.setAttribute(KEY_CAPTCHA, captcha);
	}
	
	
	/**
	 * @param code 短信码
	 * @param minitue 有效时间(分钟)
	 */
	public static void setSmsSession(Session session, String code,int minitue){
		session.setAttribute(KEY_SMSCAPTCHA, code);
		session.setAttribute(KEY_SMSCAPTCHA+"_TIME", System.currentTimeMillis());
		session.setAttribute(KEY_SMSCAPTCHA+"_MINITUE",minitue);
	}
	
	/**
	 * 检验手机验证码
	 * @param smscaptcha
	 * @return
	 */
	public static boolean checkSMS4Session(String smscaptcha,boolean isRemove){
		return checkSMS4Session(null, smscaptcha,isRemove);
	}
	
	public static boolean checkSMS4Session(String phoneNo, String smscaptcha,boolean isRemove){
		Session session = Envparam.getSession();
		Object obj = null;
		if(isRemove){
			obj = session.removeAttribute(KEY_SMSCAPTCHA);
		}else{
			obj = session.getAttribute(KEY_SMSCAPTCHA);
		}
		if(null!=obj&&!"".equals(obj)){
			long startime = 0;
			int minitue = 100;
			if(isRemove){
				startime = (long) session.removeAttribute(KEY_SMSCAPTCHA+"_TIME");
				minitue = (int) session.removeAttribute(KEY_SMSCAPTCHA+"_MINITUE");
			}else{
				startime = (long) session.getAttribute(KEY_SMSCAPTCHA+"_TIME");
				minitue = (int) session.getAttribute(KEY_SMSCAPTCHA+"_MINITUE");
			}
			long endtime = System.currentTimeMillis();
			long time = (endtime-startime)/60000;
			if(minitue>time){
				String[] objArr = StrUtil.splitString(obj.toString(), ':');
				boolean bOk = objArr[0].equals(smscaptcha);
				if (bOk && phoneNo != null && objArr.length > 1){
					bOk = phoneNo.equals(objArr[1]);
				}
				return bOk;
			}
		}
		return false;
	}
	
}
