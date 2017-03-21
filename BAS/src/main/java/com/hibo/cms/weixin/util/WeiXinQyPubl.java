package com.hibo.cms.weixin.util;

import java.util.Map;

import com.hibo.bas.util.DateUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.bas.encrypt.*;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月12日 下午6:07:58</p>
 * <p>类全名：com.hibo.weixin.util.WeiXinQyPubl</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 */
public class WeiXinQyPubl
{
	private static Map<String,Object> accessMap = new java.util.HashMap<String,Object>();

	/**
	 * 被动响应回复信息
	 * @param to
	 * @param from
	 * @param msg
	 * @return
	 */
	public static String getSendTextXml(String appKey, String userId, String from, String nonce,String msg) {
		long timeLong = DateUtil.getDateTime(new java.util.Date());
		String xml = "<xml>";
		xml += "<ToUserName><![CDATA["+userId+"]]></ToUserName>";
		xml += "<FromUserName><![CDATA["+from+"]]></FromUserName>";
		xml += "<CreateTime>"+timeLong+"</CreateTime>";
		xml += "<MsgType><![CDATA[text]]></MsgType>";
		xml += "<Content><![CDATA["+msg+"]]></Content>";
		xml += "</xml>";
		WXBizMsgCrypt wxcpt = getWXBizMsgCrypt(appKey);
		try{
			String sEncryptMsg = wxcpt.EncryptMsg(xml, ""+timeLong, nonce);
			return sEncryptMsg;
		}catch (Exception ex){
			ex.printStackTrace();
			throw new java.lang.RuntimeException(ex.toString());
		}
		
	}
	
	/**
	 * 客服文本消息JSON
	 * @param openid
	 * @param content
	 * @return
	 */
	public static String getSendTextJson(String userId, String agentId, String content){
		return getSendTextJson(userId, null, null, agentId, content, "0");
	}
	
	public static String getSendTextJson(String userId, String toparty, String totag, String agentId, String content, String safe){
		userId = StrUtil.replace(userId, ",", "|");
		toparty = StrUtil.replace(toparty, ",", "|");
		totag = StrUtil.replace(totag, ",", "|");
		String json = "{";
		if (userId != null && userId.trim().length() > 0)
			json += "\"touser\":\""+userId+"\",";    //UserID1|UserID2|UserID3
		if (toparty != null && toparty.trim().length() > 0)
			json += "\"toparty\":\""+toparty+"\",";	//"toparty": " PartyID1 | PartyID2 ",
		if (totag != null && totag.trim().length() > 0)
			json += "\"totag\":\""+totag+"\",";		//"totag": " TagID1 | TagID2 ",
		json += "\"msgtype\":\"text\",";
		json += "\"agentid\":\""+agentId+"\",";
		json += "\"text\":";
		json += "{";
		json += "\"content\":\""+content+"\"";
		json += "},";
		json += "\"safe\":\""+safe+"\"";
		json += "}";
		return json;
	}
	
	/**
	 * 客服图文消息JSON
	 * @param openid
	 * @param mapArr
	 * @return
	 */
	public static String getSendImageJson(String userId, String agentId, Map[] mapArr){
		return getSendImageJson(userId,null, null, agentId, mapArr);
	}
	
	public static String getSendImageJson(String userId, String toparty, String totag, String agentId, Map[] mapArr){
		userId = StrUtil.replace(userId, ",", "|");
		toparty = StrUtil.replace(toparty, ",", "|");
		totag = StrUtil.replace(totag, ",", "|");
		String json = "{";
		if (userId != null)
			json += "\"touser\":\""+userId+"\",";    //UserID1|UserID2|UserID3
		if (toparty != null)
			json += "\"toparty\":\""+toparty+"\",";	//"toparty": " PartyID1 | PartyID2 ",
		if (totag != null)
			json += "\"totag\":\""+totag+"\",";		//"totag": " TagID1 | TagID2 ",
		json += "\"msgtype\":\"news\",";
		json += "\"agentid\":\""+agentId+"\",";
		json += "\"news\":{";
		json += "\"articles\": [";
		for(int i=0;i<mapArr.length;i++){
			if (i >= 10)
				break;
			
			if (mapArr[i] == null)
				continue;
			
			if (i > 0)
				json += ",";
			json += "{";
			json += "\"title\":\""+mapArr[i].get("title")+"\",";
			json += "\"description\":\""+mapArr[i].get("desc")+"\",";
			String url = ""+mapArr[i].get("url");
			json += "\"url\":\""+url+"\",";
			url = ""+mapArr[i].get("picurl");
			json += "\"picurl\":\""+url+"\"";
		    json += "}";
		}
		 json += "]}}";
		return json;
	}
	
	/**
	 * 用户合法性判断
	 * @return true 通过
	 */
	static public String userCheck(String appKey, String signature,String timestamp,String nonce,String echostr) throws AesException{
		if (signature == null)
			return null;
		WXBizMsgCrypt wxcpt = getWXBizMsgCrypt(appKey);
		String sEchoStr = wxcpt.VerifyURL(signature, timestamp, nonce, echostr);
		System.out.println("verifyurl echostr: " + sEchoStr);
		return sEchoStr;
	}
	
	public static WXBizMsgCrypt getWXBizMsgCrypt(String appKey) {
		String token = WeiXinConfig.getAppToken(appKey);
		String corpId = WeiXinConfig.getAppCorpId(appKey);
		String sEncodingAESKey = WeiXinConfig.getAESKey(appKey);
		try {
			return new WXBizMsgCrypt(token, sEncodingAESKey, corpId);
		}catch (Exception ex){
			ex.printStackTrace();
			throw new java.lang.RuntimeException(ex.toString());
		}
	}
	/**
	 * 创建菜单
	 * @param json
	 */
	static public Map createMenu(String appKey, String agentId,String json){
		String access_token = getAccessToken(appKey);
		if (access_token == null)
			return new java.util.HashMap();
		String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token+"&agentid="+agentId;
		return WeiXinPubl.postUrlJson(appKey, agentId, url,json,true);
	}
	
	/**
	 * 获取反向调用URL
	 * @param url
	 * @return
	 */
	public static String getMenuUrl(String appKey, String agentId, String url){
		url = StrUtil.replace(url, "#", "__");
		url = WeiXinPubl.getUrlEncoder(url);
		String str = "https://open.weixin.qq.com/connect/oauth2/authorize?";
		str += "appid="+WeiXinConfig.getAppCorpId(appKey)+"&redirect_uri="+url;
		str += "&response_type=code&scope=snsapi_base&state="+agentId+"#wechat_redirect";
		return str;
	}
	
	/**
	 * 通过重定向获得中的 code，获取 UserID
	 * @param code
	 * @return
	 */
	static public String getUserId(String appKey, String agentId, String code){
		String access_token = getAccessToken(appKey);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?";
		url += "access_token="+access_token+"&code="+code+"&agentid="+agentId;
		Map map = WeiXinPubl.getUrlJson(url);
		return StrUtil.obj2str(map.get("UserId"));
	}
	
	/**
	 * 向用户发送信息
	 * @param json
	 */
	static public void sendJson(String appKey, String agentid, String json){
		String access_token = getAccessToken(appKey);
		if (access_token == null || json == null)
			return;
		String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+access_token;
		WeiXinPubl.postUrlJson(appKey, agentid, url,json);
	}
	
	/**
	 * 发送文本信息
	 * @param appKey
	 * @param agentid
	 * @param openid
	 * @param text
	 * @return
	 */
	static public boolean sendText(String appKey, String agentid, String openid, String text){
		if (openid == null)
			return false;
		sendJson(appKey, agentid, getSendTextJson(openid,agentid, text));
		return true;
	}
	
	/**
	 * 发送图文信息
	 * @param appKey
	 * @param agentId
	 * @param openid
	 * @param map
	 * @return
	 */
	static public boolean sendImages(String appKey, String agentId, String openid, Map[] map){
		if (openid == null)
			return false;
		sendJson(appKey, agentId, getSendImageJson(openid,agentId,map));
		return true;
	}
	
	/**
	 * 获取 AccessToken
	 * @param appKey 企业号ID
	 * @param agentid  应用 ID
	 * @return
	 */
	static public String getAccessToken(String appKey){
		long newtime = DateUtil.getDateTime(new java.util.Date());
		String access_token = StrUtil.obj2str(accessMap.get(appKey));
		long access_token_time = StrUtil.obj2long(accessMap.get(appKey+"-T"),0);
		if (access_token == null || newtime >= (access_token_time-10000)){
			String corpId = WeiXinConfig.getAppCorpId(appKey);
			String appSecret = WeiXinConfig.getAppSecret(appKey);
			String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?";
			url += "corpid="+corpId+"&corpsecret="+appSecret;
			try{
				Map map = WeiXinPubl.getUrlJson(url);
				access_token = StrUtil.obj2str(map.get("access_token"));
				access_token_time = newtime + (7200*1000);
			}catch(Exception ex){
				access_token = null;
				access_token_time=0;
			}
			accessMap.put(appKey, access_token);
			accessMap.put(appKey+"-T", ""+access_token_time);
		}
		return access_token;
	}
	
	public static Map getAccessMap(){
		return accessMap;
	}
	
	public static void clearAccessMap(){
		accessMap.clear();
	}
	
	/**
	 * 获取菜单数据
	 * @param but
	 * @param obj
	 * @return
	 */
	public static String getMenuJson(String appKey, String appid, Object[][] but,Object[][] subBut){
		String json = "{\"button\":[";
		for(int i=0;i < but.length; i++){
			String menuid = StrUtil.obj2str(but[i][0]);
			String name = StrUtil.obj2str(but[i][1]);
			String type = ""+StrUtil.obj2str(but[i][2]);
			String key = ""+StrUtil.obj2str(but[i][3]);
			if (!StrUtil.isnull2(key)){
				if (i > 0)json += ",";
				if ("view".equals(type)){
					boolean bOauth = (StrUtil.obj2int(but[i][4])&1)!=0;
					if (bOauth)
						key = getMenuUrl(appKey, appid, key);
				}
				json += "{";
				json += "\"type\":\""+type+"\",";
				json += "\"name\":\""+name+"\",";
				boolean bUrl = "view".equals(type);
				json += "\""+(bUrl?"url":"key")+"\":\""+key+"\"";
				json += "}";
			}else if (subBut != null && subBut.length > 0 && !StrUtil.isnull2(name)){
				if (i > 0)json += ",";
				int k = 0;
				json += "{";
				json += "\"name\":\""+name+"\",";
				json += "\"sub_button\":[";
				for(int l = 0;l<subBut.length;l++){
					String menuid2 = StrUtil.obj2str(subBut[l][0]);
					String key2 = StrUtil.obj2str(subBut[l][3]);
					String type2 = StrUtil.obj2str(subBut[l][2]);
					if (!menuid2.startsWith(menuid)|| "".equals(key2) || "".equals(type2))
						continue;
					String name2 = StrUtil.obj2str(subBut[l][1]);
					if ("view".equals(type2)){
						boolean bOauth = (StrUtil.obj2int(subBut[l][4])&1)!=0;
						if (bOauth)
							key2 = getMenuUrl(appKey, appid, key2);
					}
					if (k > 0)
						json += ",";
					k++;
					json += "{";
					json += "\"type\":\""+type2+"\",";
					json += "\"name\":\""+name2+"\",";
					json += "\""+("view".equals(type2)?"url":"key")+"\":\""+key2+"\"";
					json += "}";
				}
				json += "]}";
			}
		}
		json += "]}";
		System.err.println("menu json = "+json);
		return json;
	}
	
	/**
	 * 微信发送文本消息
	 * @param envParams
	 */
	/*
	public static void wxSendText(ValueMap envParams, Progress progress){
		String wcode = StrUtil.obj2str(envParams.getValue("wcode"));
		String bcode = StrUtil.obj2str(envParams.getValue("bcode"));
		String ccode = StrUtil.obj2str(envParams.getValue("ccode"));
		String touser = StrUtil.obj2str(envParams.getValue("touser"));
		String toparty = StrUtil.obj2str(envParams.getValue("toparty"));
		String totag = StrUtil.obj2str(envParams.getValue("totag"));
		String appid = StrUtil.obj2str(envParams.getValue("appid"));
		String text = StrUtil.obj2str(envParams.getValue("text"));
		String sql = "select apptype from weixinmenu where appid = '"+appid+"'";
		String apptype = StrUtil.obj2str(BusiBasPubl.sqlSelect1(envParams, sql));
		sql = "select openid,wname from weixinuser where apptype = '"+apptype+"' and (1<>1 ";
		if (wcode != null)
			sql += "or " + StrUtil.filterFormat("or", "wcode='%0'", null, wcode, false);
		if (bcode != null)
			sql += "or " + StrUtil.filterFormat("or", "bcode like '%0%'", null, bcode, false);
		if (ccode != null)
			sql += "or " + StrUtil.filterFormat("or", "ccode='%0'", null, ccode, false);
		if (touser != null)
			sql += "or " + StrUtil.filterFormat("or", "openid='%0'", null, touser, false);
		sql += ")";
		String touser1 = "";

		if (progress != null){
			progress.setProgressMessage("准备发送消息...", 30, 100);
		}
		
		if (wcode != null || bcode != null || ccode != null || touser != null){
			Object[][] oData = BusiBasPubl.sqlSelect3(envParams, sql);
			if (oData != null && oData.length > 0){
				if (progress != null){
					progress.setProgressMessage("成员明细：");
				}
				for(int i = 0; i < oData.length; i++){
					touser1 += "|"+oData[i][0];
					if (progress != null){
						progress.setProgressMessage(oData[i][0]+":"+oData[i][1]);
					}
				}
				touser1 = touser1.substring(1);
			}
		}
		//服务号
		if ("2".equals(apptype)){
			if (progress != null){
				progress.setProgressMessage("正在发送消息...", 60, 100);
				progress.setProgressMessage("服务号发送消息，接收者可能接收不到消息！");
			}
			String[] openidArr = StrUtil.splitString(touser1, '|');
			for(int i = 0; i < openidArr.length; i++){
				WeiXinPubl.sendText(openidArr[i], text);
			}
		}else if ("3".equals(apptype)){   //企业号
			String toparty1 = "";
			if (toparty != null){
				sql = "select id,name from weixinbcode where id is not null and (";
				sql +=  StrUtil.filterFormat("or", "code='%0'", null, toparty, false);
				sql += ")";
				Object[][] oData = BusiBasPubl.sqlSelect3(envParams, sql);
				if (oData != null && oData.length > 0){
					if (progress != null){
						progress.setProgressMessage("\n部门明细：");
					}
					for(int i = 0; i < oData.length; i++){
						toparty1 += "|"+oData[i][0];
						if (progress != null){
							progress.setProgressMessage(oData[i][0]+":"+oData[i][1]);
						}
					}
					toparty1 = toparty1.substring(1);
				}
			}
			String totag1 = "";
			if (totag != null){
				sql = "select tagid,tagname from weixintag where tagid is not null and (";
				sql +=  StrUtil.filterFormat("or", "code='%0'", null, totag, false);
				sql += ")";
				Object[][] oData = BusiBasPubl.sqlSelect3(envParams, sql);
				if (oData != null && oData.length > 0){
					if (progress != null){
						progress.setProgressMessage("\n标签明细：");
					}
					for(int i = 0; i < oData.length; i++){
						totag1 += "|"+oData[i][0];
						if (progress != null){
							progress.setProgressMessage(oData[i][0]+":"+oData[i][1]);
						}
					}
					totag1 = totag1.substring(1);
				}
			}
			if (!("".equals(touser1) && "".equals(toparty1) && "".equals(totag1))){
				if (progress != null){
					progress.setProgressMessage("正在发送消息...", 60, 100);
				}
				String json = getSendTextJson(touser1, toparty1, totag1, appid, text, "0");
				sendJson(appKey, appid, json);
			}else{
				if (progress != null){
					progress.setProgressMessage("消息发送失败，没有符合条件的接收人！消息内容：\n"+text);
				}
			}
		}
		if (progress != null){
			progress.setProgressMessage("消息发送成功...", 100, 100);
		}
	}
	
	*/
	
	/**
	 * 定时任务，发送短信消息
	 * @param envParams
	 */
	/*
    public static void sendWxText(ValueMap envParams) {
    	String msgTypes = StrUtil.obj2str(envParams.getValue("MsgTypes"));
    	if (msgTypes == null)
    		msgTypes = "2";
    	String url = StrUtil.obj2str(envParams.getValue("TaskURL"));
    	String picurl = StrUtil.obj2str(envParams.getValue("TaskPicUrl"));
    	if (picurl == null)
    		picurl = "";
        String filter = StrUtil.obj2str(envParams.getValue("expFilter"));
        String defname = StrUtil.obj2str(envParams.getValue("defname"));
        if (defname == null)
        	defname = "微信-消息提醒处理";
        int dbType = snsoft.dx.DxUtil.getDatabaseType(envParams);
        Date date = new Date();
        int maxExetimes = StrUtil.obj2int(envParams.getValue("maxExetimes"), 10);
        String sql = "update msgqueue set vlock = '1',lockdate = " + SqlUtil1.sqlDateConstant(dbType, date, true);
        sql += ",exetimes=" + SqlUtil1.sqlIsNullFunction(dbType, "exetimes", "0") + "+1";
        sql += " where msgtype = "+msgTypes+" and msgact = 2 and vlock is null ";
        sql += " and " + SqlUtil1.sqlIsNullFunction(dbType, "exetimes", "0") + "<" + maxExetimes;
        if (filter != null)
            sql += " and " + filter;
        boolean bSucceed = true;
        int iCount = 0;
        try {
            iCount = BusiBasPubl.executeUpdate(snsoft.dx.DxUtil.getDataSource(envParams), sql);
            if (iCount == 0)
                return;
            sql = "select m.msgid,m.title,m.msgtext,w.openid,w.appid,m.kword,w.apptype";
            sql += " from msgqueue m,weixinuser w";
            sql += " where m.msgto = w.wcode and m.msgtype = "+msgTypes+" and m.msgact = 2 ";
            sql += " and m.vlock='1' ";
            sql += " and m.lockdate = " + SqlUtil1.sqlDateConstant(snsoft.dx.DxUtil.getDatabaseType(envParams), date, true);
            sql += " order by w.openid";

            Object[][] oData = BusiBasPubl.sqlSelect3(envParams, sql);

            if (oData == null || oData.length == 0) {
                return;
            }
            
            sql = "select deftext from defs where sysid= 10 and deftype=4 and defname = '"+defname+"'";
            String tacText = StrUtil.longText(BusiBasPubl.sqlSelect1(envParams, sql));
            Tac tac = null;
            if (tacText != null && tacText.trim().length() > 0) {
                tac = new Tac();
                tac.setEnvParams(envParams);
                tac.addDefaultGlobalVar("envParams", envParams);
                if (!tac.yaccParse(tacText)) {
                    bSucceed = false;
                    throw new RuntimeException(defname+" TAC有语法错！");
                }
            }
            Map[] mapArr  = new java.util.HashMap[1];
            for (int i = 0; i < oData.length; i++) {
            	mapArr[0] = new java.util.HashMap();
            	mapArr[0].put("title", oData[i][1]);
            	mapArr[0].put("desc", oData[i][2]);
            	mapArr[0].put("url", "");
            	mapArr[0].put("picurl", picurl);
            	
            	String textMsg = null;
            	
                String openid = StrUtil.obj2str(oData[i][3]);
                String appid = StrUtil.obj2str(oData[i][4]);
                if (appid == null)
                	appid = "0";
            	String kword = StrUtil.obj2str(oData[i][5]);
            	String apptype = StrUtil.obj2str(oData[i][6]);
                String[] taskArr = StrUtil.splitString(kword, '#');
                if (kword != null && kword.indexOf("#") > 0 && !"*".equals(taskArr[2])) {
                    //taskId+"#"+idx+"#"+a[i][0];  //wcode
                    sql = "select taskid,wfcode,buidx,ver,idx,wcode,sheetcode,taskexpl,submitdate from tasklist ";
                    sql += " where taskid = '" + taskArr[0] + "' and idx = " + taskArr[1] + " and wcode = '" + taskArr[2] + "'";
                    sql += " and (performstat is null or performstat=0)";
                    Object[][] tData = BusiBasPubl.sqlSelect3(envParams, sql);
                    if (tData == null || tData.length == 0) {
                        continue;
                    }
                    if (tac != null){
                    	Object o = tac.callProc("setTaskMsg", new Object[]{appid,mapArr[0],oData[i],tData[0]});
                    	if (o != null)
                			textMsg = StrUtil.obj2str(o);
                    }
                }else{  //非审批消息
                	if (tac != null){
                		Object o = tac.callProc("setTextMsg", new Object[]{appid,mapArr[0],oData[i]});
                		if (o != null)
                			textMsg = StrUtil.obj2str(o);
                	}
                }
                
                if ("2".equals(apptype)){
                	if (textMsg != null){
                		WeiXinPubl.sendText(openid, textMsg);
                	}else{
                		WeiXinPubl.sendImages(openid, mapArr);
                	}
                }else if ("3".equals(apptype)){
                	if (textMsg != null){
                		sendText(appKey, appid, openid, textMsg);
                	}else{
                		sendImages(appKey, appid, openid, mapArr);
                	}
                }
                
                sql = "update msgqueue set vlock = '2'";
                sql += " where msgid = '" + oData[i][0] + "'";
                BusiBasPubl.executeUpdate(snsoft.dx.DxUtil.getDataSource(envParams), sql);
            }
        }catch (Exception ex){
        	bSucceed = false;
        }finally {
            if (iCount > 0 && !bSucceed) {
                sql = "update msgqueue set vlock = null";
                sql += " where msgtype = "+msgTypes+" and msgact = 2 and vlock = '1' and lockdate = " + SqlUtil1.sqlDateConstant(snsoft.dx.DxUtil.getDatabaseType(envParams), date, true);
                BusiBasPubl.executeUpdate(snsoft.dx.DxUtil.getDataSource(envParams), sql);
            }
        }
    }
	*/
}
