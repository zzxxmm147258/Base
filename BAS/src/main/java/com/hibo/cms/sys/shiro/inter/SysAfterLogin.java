package com.hibo.cms.sys.shiro.inter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hibo.cms.sys.model.login.LoginInfo;

/** 
 * <p>标题：登录后调用类,实现类需要添加spring扫描注解</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月28日 下午3:00:09</p>
 * <p>类全名：com.hibo.cms.sys.shiro.inter.SysAfterLogin</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public interface SysAfterLogin {
	public void afterLoginDo(HttpServletRequest request,HttpServletResponse response, LoginInfo loginInfo);
}
