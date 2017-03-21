package com.hibo.cms.sys.model.login;

import java.io.Serializable;
import java.util.Set;

import com.hibo.cms.sys.utils.login.LoginUtil;
import com.hibo.cms.user.model.User;
/**
 * <p>标题：登录信息</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月22日 下午4:00:07</p>
 * <p>类全名：com.hibo.cms.sys.model.login.LoginInfo</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public class LoginInfo implements Serializable{
	private static final long serialVersionUID = -6067740822761860467L;
	public static final String ORIGIN_WEIXIN = "weixinLogin";
	public static final String ORIGIN_PC = "PCLogin";
	public static final String ORIGIN_APP = "AppLogin";
	private String username;
	private User user;
	private String sysid;
	private String work;
	//角色列表
	private Set<String> roleSet;
	//权限列表
	private Set<String> premSet;
	//用户类型 
	private String type;
	//是否超级管理员
	private boolean isSuperAdmin = false;
	//账套列表
	private Set<String> worklist;
	//系统列表
	private Set<String> sysidlist;
	//是否后台进入系统
	private boolean isMain;
	
	private String origin;//weixin:微信登陆；pc:pc登录
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public Set<String> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<String> roleSet) {
		this.roleSet = roleSet;
	}
	public Set<String> getPremSet() {
		return premSet;
	}
	public void setPremSet(Set<String> premSet) {
		this.premSet = premSet;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		isSuperAdmin = LoginUtil.ADMSTR.equals(type)?true:false;
		this.type = type;
	}
	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}
	
	public boolean isAdmin() {
		return roleSet!=null && roleSet.contains("admin");
	}
	
	public boolean isMaintain() {
		return roleSet!=null && roleSet.contains("maintain");
	}
	
	public Set<String> getWorklist() {
		return worklist;
	}
	public void setWorklist(Set<String> worklist) {
		this.worklist = worklist;
	}
	public Set<String> getSysidlist() {
		return sysidlist;
	}
	public void setSysidlist(Set<String> sysidlist) {
		this.sysidlist = sysidlist;
	}
	public boolean isMain() {
		return isMain;
	}
	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
