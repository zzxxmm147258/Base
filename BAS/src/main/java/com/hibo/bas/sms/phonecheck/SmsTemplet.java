package com.hibo.bas.sms.phonecheck;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hibo.bas.sms.model.SmsTempletBas;
import com.hibo.bas.sms.service.ISmsTempletBasService;

/** 
 * <p>标题：短信模板</p>
 * <p>功能：短信验证码模板 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月9日 下午7:00:45</p>
 * <p>类全名：com.hibo.bas.sms.phonecheck.SmsTemplet</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
@Component
public class SmsTemplet {

	private static ISmsTempletBasService smsTempletBasService;
	private static Map<String,SmsTemp> smsMap= new HashMap<String,SmsTemp>();
	
	@Resource
	public void setSmsTempletBasService(ISmsTempletBasService smsTempletBasService) {
		SmsTemplet.smsTempletBasService = smsTempletBasService;
	}

	public static SmsTemp getTemplet(String templetNumber) {
		if(smsMap.containsKey(templetNumber)){
			return smsMap.get(templetNumber);
		}else{
			SmsTemplet.SmsTemp smsTemp = new SmsTemplet().new SmsTemp();
			SmsTempletBas sms = smsTempletBasService.selectByPrimaryKey(templetNumber);
			int minitue = sms.getTimeLimit();
			String time = "";
			if(minitue>0){
				if(minitue<1){
					minitue = minitue*60;
					time = ",有效期"+minitue+"秒!";
				}else{
					time = ",有效期"+minitue+"分钟!";
					if(minitue>=60){
						String min = minitue%60==0?"":(minitue%60)+"分钟!";
						time = ",有效期"+minitue/60+"小时" + min;
					}
				}
			}
			String temp = sms.getTemp()+time;
			smsTemp.setMinitue(minitue);
			smsTemp.setTemp(temp);
			return smsTemp;
		}
		
		
/*		templetMap.put("1000", "【薇思丹】正在注册会员,手机动态码:#"+time);
		templetMap.put("1001", "【薇思丹】正在修改密码,手机动态码:#"+time);
		templetMap.put("1002", "【薇思丹】正在修改手机,手机动态码:#"+time);
		templetMap.put("1003", "【会员】正在注册手机,手机动态码:#"+time);*/
	}
	
	protected class SmsTemp{
		private int minitue;
		private String temp;
		public int getMinitue() {
			return minitue;
		}
		public void setMinitue(int minitue) {
			this.minitue = minitue;
		}
		public String getTemp() {
			return temp;
		}
		public void setTemp(String temp) {
			this.temp = temp;
		}
		
	}
	
}
