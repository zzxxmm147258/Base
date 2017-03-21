package com.hibo.cms.util.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月8日 下午5:30:20</p>
 * <p>类全名：com.hibo.cms.util.push.BasPushPayBuild</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public class BasPushPayBuild {

	
	public static final String masterSecret = "2dc936303c99bb7eb8a12d39";
	
	public static final String appKey = "4ca890400bd6478085b0f255";
	
	private static final Logger log = LoggerFactory.getLogger(BasPushPayBuild.class);
	
	/**
	 * <p>功能： 推送全部用户</p>
	 * <p>创建日期：2015年12月24日 下午5:01:35</p>
	 * 作者：崔志敏 
	 * @return
	 */
	public static PushResult pushAndroidAndIosAll(String alert) {
		
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		
		PushResult result = null;
		
		PushPayload pushPayload =  PushPayload.alertAll(alert);
		
		try {
			result = jpushClient.sendPush(pushPayload);
			log.info("Got result - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error, should retry later" + e);
		} catch (APIRequestException e) {
			log.error("Should review the error, and fix the request");
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}
		return result;
	}
	
	/**
	 * @功能:推送android指定用户
	 * @作者:崔志敏
	 * @时间:2016年4月26日 下午5:30:07
	 * @param alert
	 * @param title
	 * @param jsonObject
	 * @param registrationId
	 * @return
	 */
	public static PushResult pushAndroidUser(String alert, String title, String jsonObject, String... registrationId) {
		
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		
		PushResult result = null;
		
		PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification
                		.newBuilder()
                		.addPlatformNotification(
                				AndroidNotification.newBuilder()
                				.setAlert(alert)
                				.setTitle(title)
                				.addExtra("flag", jsonObject)
                				.build()).build()).build();
		
		try {
			result = jpushClient.sendPush(pushPayload);
			log.info("Got result - " + result);

		} catch (APIConnectionException e) {
			log.error("Connection error, should retry later" + e);
		} catch (APIRequestException e) {
			log.error("Should review the error, and fix the request");
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}
		return result;
	}
	
	/**
	 * @功能:推送IOS指定用户
	 * @作者:崔志敏
	 * @时间:2016年4月26日 下午5:07:00
	 * @param alert 弹出推送消息
	 * @param jsonObject 自定义字段内容
	 * @param registrationId 设备ID
	 * @return
	 */
	public static PushResult pushIosUser(String alert, String jsonObject, String... registrationId) {
		
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);

		PushResult result = null;
		
		PushPayload pushPayload = PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.registrationId(registrationId))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								IosNotification
								.newBuilder()
								.setAlert(alert)//.setBadge(1)
								.setSound("happy")
								.addExtra("flag", jsonObject)
								.build()).build())
				.setMessage(Message.content(""))
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();

		try {
			result = jpushClient.sendPush(pushPayload);
			log.info("Got result - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error, should retry later" + e);
		} catch (APIRequestException e) {
			log.error("Should review the error, and fix the request");
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}
		return result;
	}
	
	public static void main(String[] args) {
		
//		PushPayBuild.pushAndroidAndIosByUserDeviceId("androidAndIOS", "0115d8a8dbf");
//		PushPayBuild.pushAndroidAndIosAll("dddddd22222fffffffff");
//		PushPayBuild.pushAndroidAll("050349af048","");
//		PushPayBuild.pushIosUser("国林，。。。。。。。。是不是变成4了。。。。。。", "18171adc030e46c7608");
	}


}
