package com.hibo.cms.sys.shiro.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.utils.login.LoginUtil;
import com.hibo.cms.token.utils.TokenLogin;
import com.hibo.cms.util.Envparam;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 下午8:28:20</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.SysUserFilter</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class SysUserFilter extends UserFilter {

	private static final Logger log = LoggerFactory.getLogger(SysUserFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		request.setAttribute("sys_isAccessAllowed_isStop", null);
		if (isLoginRequest(request, response)) {
			return true;
		} else {
			// 是否已经登录
			boolean islogin = TokenLogin.tokenAccess(request, response);
			// 访问的URI
			String requestURI = getPathWithinApplication(request);
			boolean isAdmin_Main = LoginUtil.isMainPath(requestURI);
			LoginInfo loginInfo = Envparam.getLoginInfo();
			if (log.isInfoEnabled()) {
				log.info("当前访问的URL为:" + requestURI + "----用户类型为:" + (islogin ? loginInfo.getType() : "来宾"));
			}
			if (islogin) {
				// 超级管理员不过滤
				if (loginInfo.isSuperAdmin()) {
					return true;
				}
				// 后台
				if (isAdmin_Main) {
					if (!loginInfo.isMain()) {
						request.setAttribute("sys_isAccessAllowed_isStop", requestURI);
						return !islogin;
					}
				} else {
					if (loginInfo.isMain()) {
						request.setAttribute("sys_isAccessAllowed_isStop", requestURI);
						return !islogin;
					}
				}
			} else {
				if (!isAdmin_Main) {
					// 获取自定义的拦截登陆
					if (!LoginUtil.isLoginEmpty()) {
						boolean ispass = true;
						for (String loginUri : LoginUtil.loginUriSet()) {
							if (LoginFilterUtil.pathsMatch(LoginUtil.getLoginFilterUrl(loginUri), requestURI)) {
								ispass = false;
								break;
							}
						}
						if (ispass) {
							return !islogin;
						}
					} else {
						return !islogin;
					}
				}
			}
			return islogin;
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return super.onAccessDenied(request, response);
	}

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		String loginUrl = null;
		String requestURI = getPathWithinApplication(request);
		loginUrl = getLoginUrl();
		loginUrl = LoginFilterUtil.getLoginUrl(requestURI, getLoginUrl());
		String URI = StrUtil.obj2str(request.getAttribute("sys_isAccessAllowed_isStop"));
		if (!StrUtil.isnull(URI)) {
			Envparam.logout();
			WebUtils.issueRedirect(request, response, (String) URI);
		} else {
			String type = null;
			HttpServletRequest servletRequest = WebUtils.toHttp(request);
			boolean isMultipart = ServletFileUpload.isMultipartContent(servletRequest);
			if (isMultipart) {
				try {
					List<FileItem> formlists = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(servletRequest);
					for (FileItem fileItem : formlists) {
						if("dataType".equals(fileItem.getFieldName())){
							type = fileItem.getString("UTF-8");
							break;
						}
					}
				} catch (FileUploadException e) {
				}
			}else{
				type = request.getParameter("dataType");
			}
			if ("json".equalsIgnoreCase(type)) {
				WebUtils.issueRedirect(request, response, "/nologin");
			} else {
				Envparam.setVFormSession("loginToUri", requestURI);
				WebUtils.issueRedirect(request, response, loginUrl);
			}
		}
	}

	@Override
	protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
		String requestURI = getPathWithinApplication(request);
		return LoginFilterUtil.isLogingUrl(requestURI, getLoginUrl());
	}
}
