package com.hibo.bas.sms.util;

import com.hibo.bas.util.DataConfig;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月9日 下午6:37:33</p>
 * <p>类全名：com.hibo.bas.sms.util.PhoneConfig</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class PhoneConfig {
	private static String SMS_PHONE_CHECK_CONNO = null;
	static{
		SMS_PHONE_CHECK_CONNO = DataConfig.getConfig("SMS.PHONE.CHECK.CONNO");
	}
	public static String getSMS_PHONE_CHECK_CONNO() {
		return SMS_PHONE_CHECK_CONNO;
	}
	
}
