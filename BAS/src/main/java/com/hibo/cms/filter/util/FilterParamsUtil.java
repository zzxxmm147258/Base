package com.hibo.cms.filter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hibo.bas.dbutil.DataSource;
import com.hibo.bas.dbutil.DataSourceUtil;
import com.hibo.cms.filter.dao.IPFilterMapper;
import com.hibo.cms.filter.model.SysIPFilter;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月16日 下午2:11:07</p>
 * <p>类全名：com.hibo.cms.filter.util.FilterParamsUtil</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
@Component
public class FilterParamsUtil {

	private static final Logger log = LoggerFactory.getLogger(FilterParamsUtil.class);

	private static List<SysIPFilter> allowedIplist = new ArrayList<SysIPFilter>();
	
	private static List<SysIPFilter> notallowedIplist = new ArrayList<SysIPFilter>();

	private static IPFilterMapper iPFilterMapper = null;

	/** 是否初始化 */
	private static boolean isInit = true;

	public static boolean isInit() {
		return isInit;
	}

	public static void setInit(boolean isInit) {
		FilterParamsUtil.isInit = isInit;
	}

	@Resource
	private void setiPFilterMapper(IPFilterMapper iPFilterMapper) {
		FilterParamsUtil.iPFilterMapper = iPFilterMapper;
	}

	/**
	 * 获取IP列表
	 * 
	 * @return
	 */
	public static void init() {
			try {
				DataSource.setDataSource(DataSourceUtil.getDefaultDataSource());
				//允许列表
				allowedIplist = iPFilterMapper.getIPFilterList(true);
				//不允许列表
				notallowedIplist = iPFilterMapper.getIPFilterList(false);
				if (log.isInfoEnabled()) {
					log.info("初始化IP列表完成。。。。。。");
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (log.isErrorEnabled()) {
					log.error("初始化IP列表失败:" + e.getMessage());
				}
			}
	}
	
	/**
	 * 被允许的IP
	 * @param ip
	 * @return
	 */
	public static boolean allowedIp(String ip){
		if (!isboolIp(ip)) {
			return false;
		}
		if(isInit){
			//初始化
			init();
			isInit = false;
		}
		//如果无数据默认不过滤
		if((null==allowedIplist||allowedIplist.size()<=0)&&(null==notallowedIplist||notallowedIplist.size()<=0)){
			return true;
		}
		//判断是否在禁止列表
		for (SysIPFilter sysIPFilter : notallowedIplist) {
			if(IPMatch(ip, sysIPFilter.getIp())){
				return false;
			}
		}
		//判断是否在允许列表
		if(null==allowedIplist||allowedIplist.size()<=0){
			return true;
		}
		for (SysIPFilter sysIPFilter : allowedIplist) {
			if(IPMatch(ip, sysIPFilter.getIp())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param ip
	 * @param ipPattern
	 * @return
	 */
	public static boolean IPMatch(String ip, String ipPattern) {
		if (!isboolIp(ip)) {
			return false;
		}
		if ("*".equals(ipPattern)) {
			return true;
		}
		String[] ips = ip.split("\\.");
		String[] ps = ipPattern.split("\\.");
		if(ps.length!=4||ps.length!=4||ips.length!=ps.length){
			return false;
		}
		return match(ip, ipPattern);
	}
	
	
	private static boolean isboolIp(String ipAddress)  
	{  
	       String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."  
	                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
	                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
	                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";   
	       Pattern pattern = Pattern.compile(ip);   
	       Matcher matcher = pattern.matcher(ipAddress);   
	       return matcher.matches();   
	}
	
	public static boolean match(String str, String patten) {
		if ("*".equals(patten)) {
			return true;
		}
		if (patten.indexOf('*') < 0) {
			return str.equals(patten);
		} else {
			char lcharAt = '*';
			boolean isfirst = false;
			int fAt = -1;
			int lAt = -1;
			for (int i = 0; i < patten.length(); i++) {
				char chars = patten.charAt(i);
				if (chars != '*'&&!isfirst) {
					fAt = i;
					isfirst = true;
				}
				if (chars == '*' && isfirst) {
					lAt = i;
					lcharAt = patten.charAt(i - 1);
					break;
				}
			}
			if (lcharAt == '*') {
				return true;
			}
			if (lAt < 0) {
				lAt = patten.length();
			}
			// 比对的字符串
			String pstr = patten.substring(fAt, lAt);
			String newpatten = patten.substring(lAt);
			int bat = str.indexOf(pstr);
			if(fAt==0&&bat!=0){
				return false;
			}
			if(bat<0){
				return false;
			}else{
				String newbstr = str.substring(bat+pstr.length());
				return match(newbstr, newpatten);
			}
		}
	}
}
