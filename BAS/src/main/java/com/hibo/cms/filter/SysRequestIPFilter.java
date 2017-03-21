package com.hibo.cms.filter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hibo.bas.util.DataConfig;
import com.hibo.cms.filter.util.FilterParamsUtil;
import com.hibo.cms.sys.shiro.util.SysUserFilter;
import com.hibo.cms.util.IPUtil;

/**
 * <p>标题：过滤器</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月16日 下午1:45:33</p>
 * <p>类全名：com.hibo.cms.filter.SysRequestIPFilter</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class SysRequestIPFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(SysUserFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		String ip = IPUtil.requestIp(request);
		boolean isAllowedIp = FilterParamsUtil.allowedIp(ip);
		String requestURI = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
		boolean isAccesse = uriFilter(requestURI);
		if (log.isInfoEnabled()) {
			String date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.sss").format(new Date());
			String requertMethod = WebUtils.toHttp(request).getMethod();
			long endTime = System.currentTimeMillis();
			double time = (endTime - startTime) / 1000.000;
			log.info("--URL:" + requestURI + "[" + requertMethod + "]--IP:" + ip + "--耗时" + time + "s--地址:" + IPUtil.findAddr(ip) + "--时间:" + date);
		}
		if (isAllowedIp) {
			if (isAccesse) {
				WebUtils.issueRedirect(request, response, "/message/404");
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			response.sendError(403, "IP is not accepted, please use the accepted IP address!");
		}
	}

	private boolean uriFilter(String requestURI) {
		String INACCESSIBLEURI = DataConfig.getConfig("INACCESSIBLEURI");
		if (null != INACCESSIBLEURI && !INACCESSIBLEURI.isEmpty()) {
			// 访问的URI
			int p = INACCESSIBLEURI.indexOf(',');
			if (isAccessed(requestURI, INACCESSIBLEURI.substring(p + 1))) {
				return false;
			}
			boolean isMain = "back".equals(INACCESSIBLEURI.substring(0, p));
			boolean isAdmin_Main = isAsminMain(requestURI);
			if (isAdmin_Main) {
				return isMain;
			} else {
				return !isMain;
			}
		}
		return false;
	}

	private boolean isAccessed(String requestURI, String uri) {
		if (null != uri && !uri.isEmpty()) {
			uri = uri + ":logout";
			String[] uris = uri.split(":");
			for (String str : uris) {
				str = str.charAt(0) == '/' ? str : "/" + str;
				if (requestURI.startsWith(str)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isAsminMain(String requestURI){
		return requestURI.startsWith("/admin") || requestURI.startsWith("/main") || requestURI.startsWith("/login");
	}

}
