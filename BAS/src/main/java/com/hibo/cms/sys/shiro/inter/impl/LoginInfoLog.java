package com.hibo.cms.sys.shiro.inter.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hibo.bas.util.WorkSpace;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.shiro.inter.SysAfterLogin;
import com.hibo.cms.sys.utils.login.LoginUtil;
import com.hibo.cms.table.dao.TableMapper;
import com.hibo.cms.util.IPUtil;
import com.hibo.mem.member.model.LoginInfoMem;
import com.hibo.mem.member.service.ILoginInfoMemService;

/** 
 * <p>标题：登录日志</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 下午3:54:26</p>
 * <p>类全名：com.hibo.cms.sys.shiro.inter.impl.LoginInfoLog</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Component
public class LoginInfoLog implements SysAfterLogin {

	private static final Logger log = LoggerFactory.getLogger(LoginInfoLog.class);
	@Resource
	private ILoginInfoMemService loginInfoMemService;
	@Resource
	private TableMapper tableMapper;
	
	@Override
	public void afterLoginDo(HttpServletRequest request,HttpServletResponse response, LoginInfo loginInfo) {
		try {
			LoginInfoMem infoMem = new LoginInfoMem();
			String loginIp = IPUtil.requestIp(request);
			String loginArea = IPUtil.findAddr(loginIp);
			infoMem.setUserid(loginInfo.getUser().getUserid());
			infoMem.setLoginIp(loginIp);
			infoMem.setLoginArea(loginArea);
			boolean isLog = true;
			if (LoginUtil.ADMSTR.equals(loginInfo.getType())) {
				String workId = loginInfo.getWork();
				if (workId == null)
					throw new com.hibo.bas.exception.HookedException("套帐获取失败！");
				String database = WorkSpace.getWorkspace(workId).database;
				isLog = null != tableMapper.selectTable(database, "mem_login_info");

			}
			if (isLog) {
				loginInfoMemService.insert(infoMem);
			}
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
		}
	}

}





