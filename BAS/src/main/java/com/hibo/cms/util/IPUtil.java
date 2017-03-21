package com.hibo.cms.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.utils.ipaddr.IpAddressUtils;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 下午2:02:33</p>
 * <p>类全名：com.hibo.cms.util.IPUtil</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class IPUtil {
	/**
	 * 获取本地IP
	 * @return
	 */
	public static String LocalIp(){
		String ip= null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
		} catch (UnknownHostException e1) {
			ip= "127.0.0.1";
		}
		return ip;
	}
	
	/**
	 * 获取用户IP
	 * @return
	 */
	public static String requestIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StrUtil.isnull(ip)|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StrUtil.isnull(ip)|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");

		}
		if (StrUtil.isnull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StrUtil.isnull(ip)) {
			ip = request.getRemoteAddr();// 返回发出请求的IP地址
		}
		ip = ip.split(",")[0];
		return ip;
	}
	
	/**
	 * 获取用户IP地址
	 * @param request
	 * @return
	 */
	public static String[] findReqIPAddrs(HttpServletRequest request){
		return IpAddressUtils.find(requestIp(request));
	}
	
	/**
	 * 获取用户IP地址
	 * @param request
	 * @return
	 */
	public static String findReqIPAddr(HttpServletRequest request){
		return IpAddressUtils.findAddr(requestIp(request));
	}
	
	/**
	 * 根据ip获取地址
	 * @param ip
	 * @return
	 */
	public static String[] findAddrs(String ip){
		return IpAddressUtils.find(ip);
	}
	
	/**
	 * 根据ip获取地址
	 * @param ip
	 * @return
	 */
	public static String findAddr(String ip){
		return IpAddressUtils.findAddr(ip);
	}
}
