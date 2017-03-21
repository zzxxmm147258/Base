package com.hibo.cms.sys.shiro.util;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * <p>标题：角色或的关系</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月2日 下午8:48:33</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.SysRolesAuthorizationFilter</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class SysRolesAuthorizationFilter extends AuthorizationFilter{
	
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }else{
        	for (String role : rolesArray) {
        		boolean isRole = subject.hasRole(role);
        		if(isRole){
        			return true;
        		}
        	}
        	return false;
        }
    }
}
