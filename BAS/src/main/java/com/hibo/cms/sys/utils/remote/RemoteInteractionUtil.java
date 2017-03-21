package com.hibo.cms.sys.utils.remote;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;

import com.hibo.bas.encrypt.EncryptPubl;
import com.hibo.bas.http.HttpRequestManager;
import com.hibo.bas.util.DataConfig;
import com.hibo.cms.sys.utils.invoke.InvokeUtil;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.sys.utils.serialize.SerializeUtils;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月25日 下午2:21:18</p>
 * <p>类全名：com.hibo.cms.sys.utils.remote.RemoteInteractionUtil</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class RemoteInteractionUtil {
	public static final String REMOTE_SESSION_KEY = "RemoteAopImpl_remote_excuce";
	public static final String TYPE_GET = "remoteGet";
	public static final String TYPE_SET = "remoteSet";
	public static final String REMOTE_CONNO = "REMOTE.CONNO.";
	public static boolean ISREMOTE;
	static{
		Object obj = DataConfig.getConfig("REMOTE.ON");
		ISREMOTE = "true".equals(obj);
	}
	public static String getConnoType(String AnnoType){
		String value = DataConfig.getConfig(REMOTE_CONNO+AnnoType.toUpperCase());
		if("".equals(value)){
			value = null;
		}
		return value;
	}
	public static boolean isAcrossed(String conno,String time, String randomObj, String msgSignature,String ip) {
		return EncryptPubl.getSignature(conno, msgSignature, time, randomObj,ip);
	}
	
	/**
	 * type 配置文件中 REMOTE.CONNO.XXXX 的  XXXX 名称
	 * @param type
	 * @param beanName
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static <T> T getRemoteObjByConno(String type, String beanName, String methodName, Map<String,Object>sParaTemp) throws Exception{
		String conno = RemoteInteractionUtil.getConnoType(type);
		if (!RemoteInteractionUtil.ISREMOTE||null==conno) {
			return null;
		}
		String[] rs = null;
		T t = null;
		if(null!=conno){
			rs = EncryptPubl.getSHA1(conno.toUpperCase());
		}
		if (null != rs && rs.length > 0) {
			t = RemoteInteractionUtil.sendRemoteRquerst(rs[3],beanName, methodName, conno, rs[0], rs[1], rs[2], sParaTemp);
		} else {
			throw new RuntimeException("生成密钥失败!");
		}
		return t;
	}
	

	/**
	 * 运程调用
	 * @param url 地址到工程名
	 * @param beanName 被调用的类的bean的名字 
	 * @param remoteMethod 被调用的方法名
	 * @param conno 对应的编号
	 * @param remoteTime 时间戳
	 * @param remoterandom 随机数
	 * @param msgSignature 签名
	 * @param sParaTemp 参数
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T sendRemoteRquerst(String url, String beanName, String remoteMethod, String conno, String remoteTime, String remoterandom, String msgSignature, Map<String,Object>sParaTemp) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		sParaTemp.put("conno", conno);
		sParaTemp.put("remoteTime", remoteTime);
		sParaTemp.put("remoterandom",remoterandom);
		sParaTemp.put("msgSignature",msgSignature);
		//将传进来的参数序列化以便控制器jie
		map.put("sParaTemp", SerializeUtils.serialize(sParaTemp));
		url = url+"/remoteinteraction/"+beanName+"/"+remoteMethod;
		String response = HttpRequestManager.sendPostRequest(map, url);
		T t = null;
		if(null!=response){
			Map<String,Object> reMap = JsonUtil.toJavaBean(response, Map.class);
			if(null!=reMap&&(boolean) reMap.get("success")){
				t = (T) SerializeUtils.deserialize((String) reMap.get("result"));
			}else{
				throw new RuntimeException((String) reMap.get("result"));
			}
			
		}
		return t;
	}
	
	private static String getRandomStr(int n){
		String s = "";
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			s = s + random.nextInt(10);
		}
		return s;
	}
	
	/**
	 * type 配置文件中 REMOTE.CONNO.XXXX 的  XXXX 部分
	 * @param type
	 * @param beanName
	 * @param methodName
	 * @param ProceedingJoinPoint
	 * @return
	 * @throws Throwable 
	 */
	public static Object getRemoteObjByConno(String type, String beanName, String methodName, ProceedingJoinPoint pjp) throws Throwable{
		String conno = RemoteInteractionUtil.getConnoType(type);
		if (!RemoteInteractionUtil.ISREMOTE||null==conno||conno.isEmpty()) {
			return pjp.proceed();
		}
		String[] rs = null;
		Object t = null;
		if(null!=conno){
			rs = EncryptPubl.getSHA1(conno.toUpperCase());
		}
		if (null != rs && rs.length > 0) {
			t = RemoteInteractionUtil.sendRemoteRquerst(rs[3],beanName, methodName, conno, rs[0], rs[1], rs[2], pjp.getArgs());
		} else {
			throw new RuntimeException("生成密钥失败!");
		}
		return t;
	}
	
	public static <T> T getRemoteObjByConno(String type, String beanName, String methodName) throws Exception{
		return getRemoteObjByConno(type, beanName, methodName, new Object[0]);
	}
	/**
	 * type 配置文件中 REMOTE.CONNO.XXXX 的  XXXX 部分
	 * @param type
	 * @param beanName
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRemoteObjByConno(String type, String beanName, String methodName, Object[] args) throws Exception{
		String conno = RemoteInteractionUtil.getConnoType(type);
		if (!RemoteInteractionUtil.ISREMOTE||null==conno||conno.isEmpty()) {
			return (T) InvokeUtil.invokeFromBean(beanName, methodName, args);
		}
		String[] rs = null;
		T t = null;
		if(null!=conno){
			rs = EncryptPubl.getSHA1(conno.toUpperCase());
		}
		if (null != rs && rs.length > 0) {
			t = RemoteInteractionUtil.sendRemoteRquerst(rs[3],beanName, methodName, conno, rs[0], rs[1], rs[2], args);
		} else {
			throw new RuntimeException("生成密钥失败!");
		}
		return t;
	}
	
	
	/**
	 * 运程调用
	 * @param url 地址到工程名
	 * @param beanName 被调用的类的bean的名字 
	 * @param remoteMethod 被调用的方法名
	 * @param conno 对应的编号
	 * @param remoteTime 时间戳
	 * @param remoterandom 随机数
	 * @param msgSignature 签名
	 * @param args 参数
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T sendRemoteRquerst(String url, String beanName, String remoteMethod, String conno, String remoteTime, String remoterandom, String msgSignature, Object[] args) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> sParaTemp = new HashMap<String,Object>();
		sParaTemp.put("conno", conno);
		sParaTemp.put("remoteTime", remoteTime);
		sParaTemp.put("remoterandom",remoterandom);
		sParaTemp.put("msgSignature",msgSignature);
		sParaTemp.put("params", args);
		//将传进来的参数序列化以便控制器jie
		map.put("sParaTemp", SerializeUtils.serialize(sParaTemp));
		url = url+"/remoteinteraction/"+beanName+"/"+remoteMethod+"/"+getRandomStr(15);
		String response = HttpRequestManager.sendPostRequest(map, url);
		T t = null;
		if(null!=response){
			Map<String,Object> reMap = JsonUtil.toJavaBean(response, Map.class);
			if(null!=reMap&&(boolean) reMap.get("success")){
				t = (T) SerializeUtils.deserialize((String) reMap.get("result"));
			}else{
				throw new RuntimeException((String) reMap.get("result"));
			}
			
		}
		return t;
	}
}
