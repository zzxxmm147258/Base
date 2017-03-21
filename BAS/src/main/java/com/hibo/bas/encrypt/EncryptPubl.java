package com.hibo.bas.encrypt;

import java.util.Date;

import com.hibo.bas.util.DateUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.config.model.Sysoptions;
import com.hibo.cms.config.util.SysConfigUtil;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月29日 下午6:26:32</p>
 * <p>类全名：com.hibo.bas.encrypt.EncryptPubl</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public class EncryptPubl {
	
	/**
	 * 字符串加密
	 * @param conno   密钥组，在系统选项中定义
	 * @param replyMsg  待加密字符串
	 * @return
	 */
	public static String EncryptMsg(String keynum,String replyMsg){
		Sysoptions sysmodel = SysConfigUtil.getSysOtions(keynum);
		String token = sysmodel.getOption1();
		String sEncodingAESKey = sysmodel.getOption2();
		String corpId = sysmodel.getOption3();
		String timeStamp = Long.toString(System.currentTimeMillis());
		String nonce = ""+new java.util.Random().nextInt(1000000);
		try {
			WXBizMsgCrypt Crypt = new WXBizMsgCrypt(token, sEncodingAESKey, corpId);
			return Crypt.EncryptMsg(replyMsg, timeStamp, nonce, keynum);
		}catch (Exception ex){
			//ex.printStackTrace();
			throw new java.lang.RuntimeException(ex.toString());
		}
	}
	
	/**
	 * 解密，返回明文
	 * @param conno  密钥组，在系统选项中定义
	 * @param postData 待解密的XML格式字符串
	 * @return
	 */
	public static String DecryptMsg(String postData){
		try {
			Object[] result = XMLParse.extract2(postData);
			String msgSignature = StrUtil.obj2str(result[0]);
			String timeStamp = StrUtil.obj2str(result[1]);
			String nonce = StrUtil.obj2str(result[2]);
			String encrypt = StrUtil.obj2str(result[3]);
			String keynum = StrUtil.obj2str(result[4]);
			
			Sysoptions sysmodel = SysConfigUtil.getSysOtions(keynum);
			String token = sysmodel.getOption1();
			String sEncodingAESKey = sysmodel.getOption2();
			String corpId = sysmodel.getOption3();
			
			WXBizMsgCrypt Crypt = new WXBizMsgCrypt(token, sEncodingAESKey, corpId);			
			return Crypt.DecryptMsg2(msgSignature, timeStamp, nonce, encrypt);
		}catch (Exception ex){
			//ex.printStackTrace();
			throw new java.lang.RuntimeException(ex.toString());
		}
	}
	
	/**
	 * 获得安全签名
	 * @param conno 密钥组，在系统选项中定义
	 * @param timestamp  时间
	 * @param nonce   随机数
	 * @return
	 */
	public static String getSHA1(String conno, String timestamp, String nonce){
		Sysoptions sysmodel = SysConfigUtil.getSysOtions(conno);
		String token = sysmodel.getOption1();
		try {
			return SHA1.getSHA1(token, timestamp, nonce);
		}catch (Exception ex){
			throw new java.lang.RuntimeException(ex.toString());
		}
	}
	
	/**
	 * 获得安全签名
	 * @param conno 密钥组，在系统选项中定义
	 * @return 
	 */
	public static String[] getSHA1(String conno){
		Sysoptions sysmodel = SysConfigUtil.getSysOtions(conno);
		String token = sysmodel.getOption1();
		String[] result = new String[4];
		result[0] = Long.toString(System.currentTimeMillis());   //时间
		result[1] = ""+new java.util.Random().nextInt(1000000);      //随机数
		try {
			result[2] = SHA1.getSHA1(token, result[0], result[1]);   //安全签名
		}catch (Exception ex){
			throw new java.lang.RuntimeException(ex.toString());
		}
		result[3] = sysmodel.getOption2();
		return result;
	}
	
	/**
	 * 判断访问IP是否正确，判断安全签名是否正确
	 * @param conno 密钥组，在系统选项中定义
	 * @param msgSignature  安全签名值
	 * @param timestamp  时间
	 * @param nonce   随机数
	 * @return
	 */
	public static boolean getSignature(String conno, String msgSignature ,String timestamp, String nonce){
		return getSignature(conno, msgSignature, timestamp, nonce, null);
	}
	
	/**
	 * 判断访问IP是否正确，判断安全签名是否正确
	 * @param conno 密钥组，在系统选项中定义
	 * @param msgSignature  安全签名值
	 * @param timestamp  时间
	 * @param nonce   随机数
	 * @param ip
	 * @return
	 */
	public static boolean getSignature(String conno, String msgSignature ,String timestamp, String nonce, String ip){
		Sysoptions sysmodel = SysConfigUtil.getSysOtions(conno);
		String token = sysmodel.getOption1();
		String ipList = sysmodel.getOption3();
		try {
			if (ip != null && !"".equals(ip) && ipList != null && !"".equals(ipList)){
				if (!StrUtil.likeOneOf(ip, ipList))
					return false;
			}
			if (StrUtil.isnull(timestamp)){
				timestamp = "";
			}else{
				//超时判断
				Date date1 = DateUtil.toDate(StrUtil.obj2long(timestamp, 0));
				long seconds = DateUtil.diffSeconds(new Date(), date1);
				if (Math.abs(seconds) > 5*60)
					return false;
			}
			if (nonce==null)
				nonce = "";
			String signature = SHA1.getSHA1(token, timestamp, nonce);
			return signature.equals(msgSignature);
		}catch (Exception ex){
			throw new java.lang.RuntimeException(ex.toString());
		}
	}
	
	/**
	 * 判断安全签名是否正确,MD5
	 * @param conno 密钥组，在系统选项中定义
	 * @param msgSignature  安全签名值
	 * @param timestamp  时间
	 * @param nonce   随机数
	 * @return
	 */
	public static boolean getMd5Signature(String conno, String msgSignature ,String timestamp, String nonce){
		return getMd5Signature(conno, msgSignature ,timestamp, nonce, null);
	}
	
	/**
	 * 判断访问IP是否正确，判断安全签名是否正确, MD5
	 * @param conno
	 * @param msgSignature
	 * @param timestamp
	 * @param nonce
	 * @param ip
	 * @return
	 */
	public static boolean getMd5Signature(String conno, String msgSignature ,String timestamp, String nonce, String ip){
		Sysoptions sysmodel = SysConfigUtil.getSysOtions(conno);
		String token = sysmodel.getOption1();
		String ipList = sysmodel.getOption2();
		try {
			if (ip != null && !"".equals(ip) && ipList != null && !"".equals(ipList)){
				if (!StrUtil.likeOneOf(ip, ipList))
					return false;
			}
			if (timestamp==null){
				timestamp = "";
			}else{
				//超时判断
				Date date1 = DateUtil.toDate(StrUtil.obj2long(timestamp, 0));
				long seconds = DateUtil.diffSeconds(new Date(), date1);
				if (Math.abs(seconds) > 5*60)
					return false;
			}
			if (nonce==null)
				nonce = "";
			String signature = SHA1.getMD5(token+timestamp+nonce);
			return signature.equals(msgSignature);
		}catch (Exception ex){
			throw new java.lang.RuntimeException(ex.toString());
		}
	}
}
