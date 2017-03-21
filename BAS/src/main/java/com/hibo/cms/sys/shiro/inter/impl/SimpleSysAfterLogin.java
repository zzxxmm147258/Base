package com.hibo.cms.sys.shiro.inter.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.shiro.inter.SysAfterLogin;

/** 
 * <p>标题：登录后调用类,添加spring扫描注解可以用</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月28日 上午11:31:14</p>
 * <p>类全名：com.hibo.cms.sys.shiro.inter.impl.SimpleSysAfterLogin</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
@Component
public class SimpleSysAfterLogin implements SysAfterLogin{

	@Override
	public void afterLoginDo(HttpServletRequest request, HttpServletResponse response,LoginInfo loginInfo) {
		
	}


}
