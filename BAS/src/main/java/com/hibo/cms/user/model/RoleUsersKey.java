package com.hibo.cms.user.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.hibo.bas.model.ModelBas;

public class RoleUsersKey extends ModelBas {
	@NotEmpty(message="角色ID不能为空")
    private String roleid;

	@NotEmpty(message="用户ID不能为空")
    private String userid;
	
	/**
	 * 角色名称
	 */
	private String rolename;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 部门码
	 */
	private String bcode;
	
	/**
	 * 部门名
	 */
	private String bname;
	
	/**
	 * 是否启用
	 */
	private Boolean availabe;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	private User user;
	
	private Role role;

    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getBcode() {
		return bcode;
	}

	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	
	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public Boolean getAvailabe() {
		return availabe;
	}

	public void setAvailabe(Boolean availabe) {
		this.availabe = availabe;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
    
}