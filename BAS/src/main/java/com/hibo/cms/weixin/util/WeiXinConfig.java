package com.hibo.cms.weixin.util;

import com.hibo.cms.config.model.Sysoptions;
import com.hibo.cms.config.util.SysConfigUtil;

/** <p>标题：</p>
 * <p>功能：微信匹配文件 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月23日 下午8:42:59</p>
 * <p>类全名：com.hibo.weixin.util.WeiXinConfig</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public class WeiXinConfig {
	
	//微信 APPID
	public String appId;
	
	//微信用户密钥
	public String appSecret;
	
	//企业号Corpid
	public String corpId;
	
	//Token
	public String token;
	
	//关注后的欢迎词
	public String welcome;
	
	//加密使用的密钥
	public String AESKey;
	
	//企业号应用ID 从 0 开始的整数
	public String agentId;
	
	//微信类型：  1：订阅号，2：服务号，3：企业号
	public String wxType;
	
	//URL跟目录
	public String domain;
	

	static public String getAppId(String appKey){ 
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.appId;
	}
	
	static public String getAppSecret(String appKey){
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.appSecret;
	}
	
	static public String getAppCorpId(String appKey){
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.corpId;
	}
	
	static public String getAppToken(String appKey){
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.token;
	}
	
	static public String getWelcome(String appKey){
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.welcome;
	}
	
	static public String getAESKey(String appKey){
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.AESKey;
	}
	
	static public String getAgentId(String appKey){
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.agentId;
	}
	
	static public String getWxType(String appKey){
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.wxType;
	}
	
	static public String getDomain(String appKey){
		WeiXinConfig config = getAlipayConfig(appKey);
		return config.domain;
	}
	
	public static java.util.Hashtable<String,WeiXinConfig> conMap = new java.util.Hashtable<String,WeiXinConfig>();
	
	public static WeiXinConfig getAlipayConfig(String appKey){
		WeiXinConfig config = conMap.get(appKey);
		if (config == null){
			config = new WeiXinConfig();
			Sysoptions sysmodel = SysConfigUtil.getSysOtions(appKey);
			config.wxType = sysmodel.getOption1();
			config.appId = sysmodel.getOption2();
			config.corpId = sysmodel.getOption2();
			config.agentId = sysmodel.getOption3();
			config.token = sysmodel.getOption4();
			config.appSecret = sysmodel.getOption5();
			config.AESKey = sysmodel.getOption6();
			config.welcome = sysmodel.getOption7();
			config.domain = sysmodel.getOption8();
		}
		return config;
	}

}
