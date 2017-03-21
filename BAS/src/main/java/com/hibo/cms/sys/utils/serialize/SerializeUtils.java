package com.hibo.cms.sys.utils.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.StringUtils;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月29日 下午5:36:15</p>
 * <p>类全名：com.hibo.cms.sys.cache.SerializeUtils</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public class SerializeUtils {
	
	public static <V> String serialize(V value){
		return SerializeUtils.serialize(value, "UTF-8");
	}
	/**
	 * 序列化传入的对象为字符串
	 * @param value
	 * @param chartSet
	 * @return
	 */
	public static <V> String serialize(V value,String chartSet){
		if(null==value){
			return null;
		}
		String serStr = null;
		ByteArrayOutputStream bm = null;
		ObjectOutputStream om = null;
		try{
			bm = new ByteArrayOutputStream();  
			om = new ObjectOutputStream(bm);  
			om.writeObject(value);    
			serStr = bm.toString("ISO-8859-1");  
	        serStr = java.net.URLEncoder.encode(serStr, chartSet); 
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			if(null!=om){
				try {
					om.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=bm){
				try {
					bm.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return serStr;
	}
	
	/**
	 * 从字符串反序列化成对象
	 * @param value
	 * @return
	 */
	public static <V> V deserialize(String value){
		return SerializeUtils.deserialize(value, "UTF-8");
	}
	
	@SuppressWarnings("unchecked")
	public static <V> V deserialize(String value,String chartSet){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		V reValue = null;
		ByteArrayInputStream bm = null;
		ObjectInputStream om = null;
		try{
			value = java.net.URLDecoder.decode(value, "UTF-8");  
	        bm = new ByteArrayInputStream(value.getBytes("ISO-8859-1"));  
	        om = new ObjectInputStream(bm);   
	        reValue = (V) om.readObject();    
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(null!=om){
				try {
					om.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=bm){
				try {
					bm.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return reValue;
	}
	
	/**
	 * 从byte数组反序列化成对象
	 * @param value
	 * @param chartSet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <V> V deserializeFromByte(byte[] value){
		if(null==value||value.length<=0){
			return null;
		}
		V reValue = null;
		ByteArrayInputStream bm = null;
		ObjectInputStream om = null;
		try{
	        bm = new ByteArrayInputStream(value);  
	        om = new ObjectInputStream(bm);   
	        reValue = (V) om.readObject();    
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=om){
				try {
					om.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=bm){
				try {
					bm.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return reValue;
	}
}
