package com.hibo.cms.weixin.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hibo.bas.http.HttpRequester;
import com.hibo.bas.util.DateUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.utils.json.JsonUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年1月14日 下午8:19:54</p>
 * <p>类全名：com.hibo.cms.weixin.util.WeiXinPubl</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 */
public class WeiXinPubl
{
	private static Map<String,String> wxMap = new java.util.HashMap<String,String>();
	private static Map<String,Object> accessMap = new java.util.HashMap<String,Object>();
	
	
	/**
	 * 通过URL获取返回的 json 转  map
	 * @param url
	 * @return
	 */
	static public Map getUrlJson(String url){
		try{
			//String pageText = com.hibo.bas.http.HttpRequestManager.sendGetRequest((Map<String,Object>)null, url);
			HttpRequester httpReq = new HttpRequester();
			HttpRequester.HttpRespons respons = httpReq.sendGet(url);
			String pageText = respons.getWebPagesText();
			System.err.println("pageText="+pageText);
			Map map = JsonUtil.toJavaBean(pageText, Map.class);
			return map;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return new java.util.HashMap();
	}
	
	
	

	/**
	 * 向用户发送信息
	 * @param json
	 */
	static public void sendJson(String appKey, String json){
		String access_token = getAccessToken(appKey);
		if (access_token == null || json == null)
			return;
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;
		
		postUrlJson(appKey, url, json);
	}
	
	
	/**
	 * 创建菜单
	 * @param json
	 */
	static public void createMenu(String appKey, String json){
		String access_token = getAccessToken(appKey);
		if (access_token == null)
			return;
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
		postUrlJson(appKey, url, json);
	}
	
	/**
	 * 客服消息POST发送
	 * @param appKey  微信ID
	 * @param agentid   应用ID，服务器为 0
	 * @param json
	 */
	
	static public Map postUrlJson(String appKey, String url,String json){
		return postUrlJson(appKey, "0", url, json, false);
	}
	
	static public Map postUrlJson(String appKey, String agentid, String url,String json){
		return postUrlJson(appKey, agentid, url, json, false);
	}
	
	static public Map postUrlJson(String appKey, String agentid, String url,String json, boolean reJson){
		if (url == null || json == null)
			return new java.util.HashMap();
		json = replaceDomain(appKey, agentid, json);
		PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        post.setRequestBody(json);
        HttpClient httpClient = new HttpClient();
        int reCode = -1;
        try {
            reCode = httpClient.executeMethod(post);
            if (reJson && reCode == org.apache.commons.httpclient.HttpStatus.SC_OK){
            	String json1 = post.getResponseBodyAsString();
            	Map map = JsonUtil.toJavaBean(json1, Map.class);
    			return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            post.releaseConnection();
        }
        return null;
	}
	
	/**
	 * 通过人员码发送文本信息
	 * @param envParams
	 * @param wcode
	 * @param text
	 * @return
	 */
	
	static public boolean sendText(String appKey, String openid,String text){
		if (openid == null)
			return false;
		sendJson(appKey, getSendTextJson(openid,text));
		return true;
	}
	
	/**
	 * 发送图文信息
	 * @param envParams
	 * @param wcode
	 * @param map
	 * @return
	 */
	
	static public boolean sendImages(String appKey, String openid,Map[] map){
		if (openid == null)
			return false;
		sendJson(appKey, getSendImageJson(appKey, openid, map));
		return true;
	}
	
	
	/**
	 * 通过重定向获得中的 code，获取 openid
	 * @param code
	 * @return
	 */
	static public String getOpenId(String appKey, String code){
		String appId = WeiXinConfig.getAppId(appKey);
		String appSecret = WeiXinConfig.getAppSecret(appKey);
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
		url += "appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
		Map map = getUrlJson(url);
		return StrUtil.obj2str(map.get("openid"));
	}

	/**
	 * 获取access_token
	 * @return
	 */
	static public String getAccessToken(String appKey){
		long newtime = DateUtil.getDateTime(new java.util.Date());
		String access_token = StrUtil.obj2str(accessMap.get(appKey));
		long access_token_time = StrUtil.obj2long(accessMap.get(appKey+"-T"),0);
		if (access_token == null || newtime >= (access_token_time-10000)){
			String appId = WeiXinConfig.getAppId(appKey);
			String appSecret = WeiXinConfig.getAppSecret(appKey);
			String url = "https://api.weixin.qq.com/cgi-bin/token?";
			url += "grant_type=client_credential&appid="+appId+"&secret="+appSecret;
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
	 * 用户合法性判断
	 * @return true 通过
	 */
	static public boolean userCheck(String appKey, String signature,String timestamp,String nonce,boolean defRet){
		if (signature == null)
			return defRet;
		String token = WeiXinConfig.getAppToken(appKey);
		String[] str = new String[]{token,timestamp,nonce};
		java.util.Arrays.sort(str);
		String impStr = str[0]+str[1]+str[2];
		java.security.MessageDigest messageDigest = null;
		try{
			messageDigest = java.security.MessageDigest.getInstance("SHA-1");
			messageDigest.update(impStr.getBytes());
			byte[] b = messageDigest.digest();
			StringBuilder sbDes = new StringBuilder();  
	        String tmp = null;  
	        for (int i = 0; i < b.length; i++) {  
	            tmp = (Integer.toHexString(b[i] & 0xFF));  
	            if (tmp.length() == 1) {  
	                sbDes.append("0");  
	            }  
	            sbDes.append(tmp);  
	        }
	        String signature1 =  sbDes.toString(); 
			//System.err.println("token="+token+";timestamp="+timestamp+";nonce="+nonce+";signature="+signature+";signature1="+signature1);
			if (signature.equals(signature1))
				return true;
		}catch(Exception ex){
		}
		return false;
	}
	

	
	/**
	 * 被动响应回复信息
	 * @param to
	 * @param from
	 * @param msg
	 * @return
	 */
	public static String getSendTextXml(String openid, String from, String msg){
		String xml = "<xml>";
		xml += "<ToUserName><![CDATA["+openid+"]]></ToUserName>";
		xml += "<FromUserName><![CDATA["+from+"]]></FromUserName>";
		xml += "<CreateTime>"+DateUtil.getDateTime(new java.util.Date())+"</CreateTime>";
		xml += "<MsgType><![CDATA[text]]></MsgType>";
		xml += "<Content><![CDATA["+msg+"]]></Content>";
		xml += "</xml>";
		return xml;
	}
	
	/**
	 * 客服文本消息JSON
	 * @param openid
	 * @param content
	 * @return
	 */
	public static String getSendTextJson(String openid,String content){
		String json = "{";
		json += "\"touser\":\""+openid+"\",";
		json += "\"msgtype\":\"text\",";
		json += "\"text\":";
		json += "{";
		json += "\"content\":\""+content+"\"";
		json += "}";
		json += "}";
		return json;
	}
	
	/**
	 * 客服图文消息JSON
	 * @param openid
	 * @param mapArr
	 * @return
	 */
	public static String getSendImageJson(String appKey, String openid,Map[] mapArr){
		String appId = WeiXinConfig.getAppId(appKey);
		String appSecret = WeiXinConfig.getAppSecret(appKey);
		String json = "{";
		json += "\"touser\":\""+openid+"\",";
		json += "\"msgtype\":\"news\",";
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
			url = StrUtil.replace(url, "APPID", appId);
			url = StrUtil.replace(url, "SECRET", appSecret);
			json += "\"url\":\""+url+"\",";
			url = ""+mapArr[i].get("picurl");
			url = StrUtil.replace(url, "APPID", appId);
			url = StrUtil.replace(url, "SECRET", appSecret);
			json += "\"picurl\":\""+url+"\"";
		    json += "}";
		}
		 json += "]}}";
		return json;
	}
	
	/**
	 * 获取菜单数据
	 * @param but
	 * @param obj
	 * @return
	 */
	public static String getMenuJson(String appKey, String[] but,Object[] obj){
		String json = "{\"button\":[";
		int k = (but.length > 3)?3:but.length;
		for(int i=0;i < k; i++){
			if (i > 0)
				json += ",";
			String[][] subbut = (String[][])obj[i];
			if (but[i] == null){
				String type = subbut[0][0];
				if ("1".equals(type))
					type = "click";
				if ("2".equals(type))
					type = "view";
				boolean bKey = "click".equals(type);
				String key = subbut[0][2];
				if (!bKey){
					if (subbut[0].length > 3){
						int oauth = StrUtil.obj2int(subbut[0][3],0);
						if ((oauth&1)!=0)
							key =  getMenuUrl(appKey, key);
					}
				}
				json += "{";
				json += "\"type\":\""+type+"\",";
				json += "\"name\":\""+subbut[0][1]+"\",";
				json += "\""+(bKey?"key":"url")+"\":\""+key+"\"";
				json += "}";
			}else{
				json += "{";
				json += "\"name\":\""+but[i]+"\",";
				json += "\"sub_button\":[";
				int j = (subbut.length > 5)?5:subbut.length;
				for(int l=0;l<j;l++){
					String type = subbut[l][0];
					if ("1".equals(type))
						type = "click";
					if ("2".equals(type))
						type = "view";
					if (l > 0)
						json += ",";
					
					boolean bKey = "click".equals(type);
					String key = subbut[l][2];
					if (!bKey){
						if (subbut[l].length > 3){
							int oauth = StrUtil.obj2int(subbut[l][3],0);
							if ((oauth&1)!=0)
								key =  getMenuUrl(appKey, key);
						}
					}
					json += "{";
					json += "\"type\":\""+type+"\",";
					json += "\"name\":\""+subbut[l][1]+"\",";
					json += "\""+(bKey?"key":"url")+"\":\""+key+"\"";
					json += "}";
				}
				json += "]}";
			}
		}
		json += "]}";
		return json;
	}
	
	/**
	 * 获取反向调用URL
	 * @param url
	 * @return
	 */
	public static String getMenuUrl(String appKey, String url){
		url = StrUtil.replace(url, "#", "__");
		url = replaceDomain(appKey, "0", url);
		url = getUrlEncoder(url);
		String str = "https://open.weixin.qq.com/connect/oauth2/authorize?";
		str += "appid="+WeiXinConfig.getAppId(appKey)+"&redirect_uri="+url;
		str += "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
		return str;
	}
	
	
	/**
	 * URL做 urlencode
	 * @param url
	 * @return
	 */
	public static String getUrlEncoder(String url){
		try{
			url = java.net.URLEncoder.encode(url,"UTF-8");
		}catch (Exception ex){
		}
		return url;
	}
	
	/**
	 * 返回图文使用的Map
	 * @param title
	 * @param desc
	 * @param url
	 * @param picurl
	 * @return
	 */
	public static Map[] strToMap(String title,String desc,String url,String picurl){
		return strToMap(new String[][]{{title,desc,url,picurl}});
	}
	
	public static Map[] strToMap(String[][] strArr){
		Map[] map = new Map[strArr.length];
		for(int i=0;i<strArr.length;i++){
			map[i] = new java.util.HashMap();
			map[i].put("title", strArr[i][0]);
			map[i].put("desc", strArr[i][1]);
			map[i].put("url", strArr[i][2]);
			if (strArr[i].length > 3)
				map[i].put("picurl", strArr[i][3]);
		}
		return map;
	}
	
	/**
	 * 接收到的信息转换成Map
	 * @param xmlStr
	 * @return
	 */
	public static Map xmlToMap(String xmlStr){
		java.util.Map map = new java.util.HashMap();
		if (xmlStr==null || "".equals(xmlStr))
			return map;
		
		Document doc = null;
		try
		{
			
			doc = DocumentHelper.parseText(xmlStr);
			Element root = doc.getRootElement();
			List list = root.elements();
			if (list != null){
				Iterator iter = list.iterator();
				while (iter.hasNext())
				{
					Element element = (Element) iter.next();
					map.put(element.getName(),element.getText());
				}
			}
		}catch (Exception e){
		}
		return map;
	}
	
	private static String replaceDomain(String appKey, String agentid, String src){
		String domain = getDomain(appKey, agentid);
		if(domain != null && domain.length() > 0){
			src = src.replace("{%Domain%}", domain);
			src = src.replace("%7B%25Domain%25%7D", domain);
		}
		return src;
	}
	
	private static String getDomain(String appKey, String agentid){
		String domain = WeiXinConfig.getDomain(appKey);
		if (domain == null)
			domain = "";
		return domain;
	}
	/**
	 * 是否关注URL
	 */
	public static String getSubscribeUrl(String appKey,String openId){
		return "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+getAccessToken(appKey)+"&openid="+openId;
	}
	
    public static void main(String[] args) throws Exception
    {
    	/*
    	Map map1 = new java.util.HashMap<String,Object>();
    	map1.put("aa", "aa");
    	map1.put("bb", "bb");
    	Map map_1 = new java.util.HashMap<String,Object>();
    	map_1.put("aa1", "aa1");
    	map_1.put("aa2", "aa2");
    	Map map_2 = new java.util.HashMap<String,Object>();
    	map_2.put("bb1", "bb1");
    	map_2.put("bb2", "bb2");
    	map_2.put("a1", map_1);
    	map1.put("b2", map_2);
    	String[] str = new String[2];
    	str[0]="str1";
    	str[1]="str2";
    	map1.put("cc", str);
    	String pageText  = org.eclipse.jetty.util.ajax.JSON.toString(map1);
    	System.err.println("pageText="+pageText);
   	
    	Map map3 = JsonUtil.toJavaBean(pageText, Map.class);
    	System.err.println("map3="+map3);
    	Map b2 = (Map)map3.get("b2");
    	System.err.println("bb2="+b2.getClass());
    	System.err.println("bb2="+b2.get("bb2"));
    	
    	Map a1 = (Map)b2.get("a1");
    	System.err.println("aa2="+a1.get("aa2"));
    	System.err.println(map3.get("cc").getClass());
    	*/
    	
    	/*
    	String replyMsg = "aaaafdsadsa";
    	String ss = com.hibo.bas.encrypt.EncryptPubl.EncryptMsg("AUTHEN01", replyMsg);
    	System.err.println("aaa="+com.hibo.bas.encrypt.EncryptPubl.DecryptMsg(ss));
    	
    	String[] re = com.hibo.bas.encrypt.EncryptPubl.getSHA1("AUTHEN01");
    	System.err.println("aaa="+com.hibo.bas.encrypt.EncryptPubl.getSignature("AUTHEN01", re[0], re[1], re[2]));
    	*/
    	
    	//System.err.println(com.hibo.bas.util.ObjectId.getOrderNum("1", "1"));
    	/*
    	String dates = "12345";
    	String dates1 = dates.substring(0, 2);
		String dates2 = dates.substring(2);
    	System.err.println(dates1);
    	System.err.println(dates2);
    	
    	System.err.println(org.eclipse.jetty.http.security.Credential.MD5.digest("aaaa"+998));
    	System.err.println(com.hibo.bas.encrypt.SHA1.getMD5("aaaa"+998));
    	*/
    	/*
		String projectname = System.getProperty("user.dir"); 
		String pn = projectname.substring(projectname.lastIndexOf(File.separator),projectname.length()); 
		System.err.println(pn);
		
		String datestr = DateUtil.dateToString(new java.util.Date(),"YYYYMMDDHHMMSS");
		System.err.println(datestr);
		*/
    	
    	
    	
    }
}
