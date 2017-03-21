package com.hibo.bas.sms.phonecheck;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.hibo.bas.http.HttpRequestManager;
import com.hibo.bas.xml.XmlElement;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月9日 下午7:01:40</p>
 * <p>类全名：com.hibo.bas.sms.phonecheck.PhoneSendUtil</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class PhoneSendUtil {
	
	/**
	 * 
	 * @param user 短信网站用户名
	 * @param password 短信网站密码
	 * @param phoneNumber 手机号码
	 * @param templetNumber 短信模板号
	 * @throws num 验证码位数
	 * @param replece 验证码替换手机模板内的匹配字符
	 * @param url 发送的URL
	 * @return 验证码
	 * @throws Exception
	 */
	public static String sendSms(String user,String password,String url, String phoneNumber, String templet, String replece,int num) throws Exception{
		String code = getRandomCode(num);
		Map<String,Object> sParaTemp = new HashMap<String,Object>();
		templet = templet.replaceAll(replece,code);
		sParaTemp.put("user", user);
		sParaTemp.put("pwd", password);
		sParaTemp.put("mobiles", phoneNumber);
		sParaTemp.put("contents", templet);
		String rs = HttpRequestManager.sendPostRequest(sParaTemp,url);
		try{
			XmlElement rootE = com.hibo.bas.xml.XmlHandler.parseXML(rs.getBytes());
			String rsCode = rootE.getSubElement("Code").value;
			int rsNum = Integer.parseInt(rsCode);
			String rsResult = rootE.getSubElement("Result").value;
			if(rsNum<=0){
				throw new RuntimeException(rsResult);
			}
		}catch(Exception e){
			throw new RuntimeException("短信验证码请求错误,错误信息:"+e.getMessage());
		}
		return code;
	}
	

	/**
	 * 获取N位随机码 N>=1
	 * @param num 随机码位数
	 * @return
	 */
	public static String getRandomCode(int num){
		Random random = new Random();
		String checkCode = "";
		num=num<1?1:num;
		for (int i = 0; i < num; i++) {
			checkCode += random.nextInt(10)+"";
		}
		return checkCode;
	}
	
	public class SmsCodeTime{
		private String code;
		private int minitue;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public int getMinitue() {
			return minitue;
		}
		public void setMinitue(int minitue) {
			this.minitue = minitue;
		}
		
	}
}
