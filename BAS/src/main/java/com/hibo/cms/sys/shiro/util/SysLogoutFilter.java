package com.hibo.cms.sys.shiro.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.cms.util.Envparam;

/** <p>标题：拦截器</p>
 * <p>功能： 退出拦截器</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月7日 下午2:44:27</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.SysLogoutFilter</p>
 * 作者：Administrator
 * 初审：
 * 复审：
 */
public class SysLogoutFilter extends LogoutFilter{

	private static final Logger log = LoggerFactory.getLogger(SysLogoutFilter.class);
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		String redirectUrl = (String) Envparam.getVFormSession(LoginFilterUtil.SUCCESS_URL_KEY);
		if(StringUtils.isEmpty(redirectUrl)){
			redirectUrl = getRedirectUrl(request, response, subject);
		}
		try {
			Envparam.logout();
		} catch (SessionException ise) {
			log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
		}
		issueRedirect(request, response, redirectUrl);
		return false;
	}

}
