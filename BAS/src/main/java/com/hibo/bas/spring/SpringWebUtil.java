package com.hibo.bas.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import com.hibo.bas.util.StrUtil;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月28日 下午5:52:40</p>
 * <p>类全名：com.hibo.bas.spring.SpringWebUtil</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public class SpringWebUtil implements ServletContextAware{

	private static ServletContext servletContext;
	@Autowired
	@Override
	public void setServletContext(ServletContext servletContext) {
		SpringWebUtil.servletContext = servletContext;
	}
	/**
	 * 获取工程目录
	 * @return
	 */
	public static String getContextPath(){
		return null==servletContext?null:servletContext.getContextPath();
	}
	
	/**
	 * 获取工程根地址
	 * @return
	 */
	public static String getwebPath(HttpServletRequest request){
		String contextPath = getContextPath()+"/";
		if(StrUtil.isnull(contextPath)){
			return null;
		}
		String url = request.getRequestURL().toString();
		int u = url.indexOf(contextPath);
		if(u<0){
			return url;
		}
		int p = contextPath.length();
		return url.substring(0, u+p);
	}
	
	/**
	 * 获取真实地址目录
	 * @return
	 */
	public static String getRealPath(){
		return null==servletContext?null:servletContext.getRealPath("/");
	}
}
