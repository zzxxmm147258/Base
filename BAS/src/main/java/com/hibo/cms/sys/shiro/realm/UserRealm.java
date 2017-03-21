package com.hibo.cms.sys.shiro.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.bas.dbutil.DataSourceUtil;
import com.hibo.bas.exception.shiro.RunAuthenticationException;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.language.model.UserLanguageBas;
import com.hibo.cms.language.service.IUserLanguageBasService;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.shiro.util.LoginfoToken;
import com.hibo.cms.sys.shiro.util.PasswordHelper;
import com.hibo.cms.sys.utils.admin.AdminUtil;
import com.hibo.cms.sys.utils.login.LoginUtil;
import com.hibo.cms.user.model.Role;
import com.hibo.cms.user.model.User;
import com.hibo.cms.user.service.role.IRoleService;
import com.hibo.cms.user.service.user.IUserService;
import com.hibo.cms.util.Envparam;
import com.hibo.mem.member.service.IMemberMemBasService;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-10 下午3:15:19</p>
 * <p>类全名：com.hibo.cms.sys.shiro.realm.UserRealm</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class UserRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

	@Resource
	private IUserService userService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IMemberMemBasService memberMmService;
	@Resource
	private IUserLanguageBasService userLanguageBasService;

	/**
	 * 当用户进行访问链接时的授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = null;
		if (null == authorizationInfo) {
			authorizationInfo = new SimpleAuthorizationInfo();
			LoginInfo loginInfo = Envparam.getLoginInfo();
			// 添加角色
			authorizationInfo.setRoles(loginInfo.getRoleSet());
			// 添加权限
			// authorizationInfo.setStringPermissions(proms);
		}
		return authorizationInfo;
	}

	/**
	 * 用户登录的认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if (log.isInfoEnabled()) {
			log.info("Login authentication:登录认证开始。。。。。。。。。。");
		}
		LoginfoToken loginfoToken = (LoginfoToken) token;
		LoginInfo loginInfo = new LoginInfo();
		if(StrUtil.isnull(loginfoToken.getDbsource())){
			loginInfo.setWork(DataSourceUtil.getDefaultDataSource());
		}else{
			loginInfo.setWork(loginfoToken.getDbsource());
		}
		loginInfo.setSysid(loginfoToken.getSysid());
		// 添加登录信息
		loginInfo.setUsername(loginfoToken.getUsername());
		loginInfo.setOrigin(loginfoToken.getOrigin());
		String userName = loginfoToken.getUsername();
		if (userName == null) {
			Envparam.setVFormSession("loginInfo", loginInfo);
			throw new AccountException("用户名不能为空");
		}
		SimpleAuthenticationInfo info = null;
		User user = null;
		Exception ems = null;
		String sys_type = loginfoToken.getSys_utype();
		try {
			if (null != sys_type && "20".equals(sys_type)) {
				user = userService.selectUserByUserName(userName);
			} else {
				user = memberMmService.selectByUsername(userName, null);
			}
		} catch (Exception e) {
			ems = e;
		}
		if (user != null) {
			String uType = StrUtil.isnull(user.getUtype())?"10":user.getUtype();
			sys_type = StrUtil.isnull(sys_type)?"10":sys_type;
			loginInfo.setType(sys_type);
			if (!uType.equals(sys_type)) {
				throw new RunAuthenticationException("已注册其他系统,无法同时登录此系统!");
			}
			if (Boolean.TRUE.equals(user.getLocked())) {
				throw new LockedAccountException(); // 帐号锁定
			}
			try {
				// 加入角色做权限
				loginInfo.setRoleSet(getSetFromListRole(roleService.selectByUserId(user.getUserid())));
			} catch (Exception e) {
				ems = e;
			}
			// 20为后台用户
			if ("20".equals(uType)) {
				loginInfo.setWorklist(getSetFromStr(user.getWorklist(), ","));
				loginInfo.setSysidlist(getSetFromStr(user.getSysidlist(), ","));
				if (!isAcrossed(loginInfo)) {
					Envparam.setVFormSession("loginInfo", loginInfo);
					throw new RunAuthenticationException("无权登录此账套系统!");
				}
			} else {
				loginInfo.setMain(false);
				user.setUtype(uType);
			}
			loginInfo.setType(user.getUtype());
			info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
					ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
			user.setPassword(null);
			user.setSalt(null);
		} else if (AdminUtil.isAdmin(loginfoToken.getUsername())) {
			String[] adm = PasswordHelper.encryptPassword(AdminUtil.getAdmins()[1], AdminUtil.getAdmins()[0]);
			// 管理员会员类型
			loginInfo.setType(LoginUtil.ADMSTR);
			loginInfo.setSysidlist(getSetFromStr(loginInfo.getSysid(), null));
			loginInfo.setWorklist(getSetFromStr(loginInfo.getWork(), null));
			// 管理员加入所有角色做权限
			try {
				Set<String> roles = getSetFromListRole(roleService.findAllRoles());
				if (null == roles || roles.size() <= 0) {
					roles = getSetFromStr("admin", null);
				} else {
					roles.add("admin");
				}
				loginInfo.setRoleSet(roles);
			} catch (Exception e) {
				loginInfo.setRoleSet(getSetFromStr("admin", null));
				if (log.isErrorEnabled()) {
					log.error("Login authentication:登录认证查询角色程序异常:" + e.getMessage());
				}
			}
			// 管理员当前加入当前系统号
			loginInfo.setSysidlist(getSetFromStr(loginInfo.getSysid(), ","));
			// 管理员当前加入当前账套号
			loginInfo.setWorklist(getSetFromStr(loginInfo.getWork(), ","));
			user = new User();
			String userid = "";
			for (int i = 0; i < 32; i++) {
				userid = userid + "0";
			}
			user.setUserid(userid);
			user.setUsername(AdminUtil.getAdmins()[0]);
			info = new SimpleAuthenticationInfo(AdminUtil.getAdmins()[0], adm[1],
					ByteSource.Util.bytes(AdminUtil.getAdmins()[0] + adm[0]), getName());
		} else {
			if (null == ems) {
				Envparam.setVFormSession("loginInfo", loginInfo);
				throw new RunAuthenticationException("该用户名未注册");
			}
		}
		if (null != ems) {
			if (log.isErrorEnabled()) {
				log.error("Login authentication:登录认证查询程序异常:" + ems.getMessage());
			}
			ems.printStackTrace();
			if (!LoginUtil.ADMSTR.equals(loginInfo.getType())) {
				Envparam.setVFormSession("loginInfo", loginInfo);
				throw new RunAuthenticationException("登录异常，请重新登录！");
			}
		}
		if (log.isInfoEnabled()) {
			log.info("Login authentication:登录认证中。。。。。。。。。。");
		}
		loginInfo.setUser(user);
		Envparam.setVFormSession("loginInfo", loginInfo);
		//设置语言
		try{
			String language = (String) Envparam.getVFormSession("language");
			UserLanguageBas lang = userLanguageBasService.selectByPrimaryKey(user.getUserid());
			if(lang!=null){
				if(StrUtil.isnull2(language)){
					Envparam.setVFormSession("language", lang.getLanguage());
				}else{
					lang.setUserid(language);
					userLanguageBasService.updateByPrimaryKey(lang);
				}
			}else{
				if(!StrUtil.isnull2(language)){
					UserLanguageBas bean = new UserLanguageBas();
					bean.setUserid(user.getUserid());
					bean.setLanguage(language);
					userLanguageBasService.insertSelective(bean);
				}else{
					Envparam.setVFormSession("language", "zh");
				}
			}
		}catch(Exception ex){
			if (log.isInfoEnabled()) {
				log.info("Login Exception:登录语言设置异常:" + ex.getMessage());
			}
		}
		return info;
	}

	private Set<String> getSetFromStr(String str, String regex) {
		Set<String> set = new HashSet<String>();
		if (!StrUtil.isnull(str.trim())) {
			if (null == regex) {
				set.add(str);
			} else {
				String[] strs = str.split(regex);
				for (String key : strs) {
					set.add(key);
				}
			}
		}
		return set;
	}

	private Set<String> getSetFromListRole(List<Role> list) {
		Set<String> set = new HashSet<String>();
		if (null != list && list.size() > 0) {
			for (Role role : list) {
				set.add(role.getRotype());
			}
		}
		return set;
	}

	private boolean isAcrossed(LoginInfo loginInfo) {
		boolean isAcrossed = false;
		String work = loginInfo.getWork();
		String sysid = loginInfo.getSysid();
		Set<String> workList = loginInfo.getWorklist();
		Set<String> sysidList = loginInfo.getSysidlist();
		if (LoginInfo.ORIGIN_WEIXIN.equals(loginInfo.getOrigin())) {
			isAcrossed = true;
		} else {
			isAcrossed = sysidList.contains(sysid);
		}
		if (null != workList && workList.size() > 0) {
			isAcrossed = isAcrossed && workList.contains(work);
		}
		loginInfo.setMain(isAcrossed);
		return isAcrossed;
	}
}
