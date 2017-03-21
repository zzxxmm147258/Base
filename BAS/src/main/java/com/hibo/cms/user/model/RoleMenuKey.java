package com.hibo.cms.user.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.hibo.bas.model.ModelBas;

public class RoleMenuKey extends ModelBas{
	@NotEmpty(message="菜单ID不能为空")
    private String menuid;

	@NotEmpty(message="角色ID不能为空")
    private String roleid;
	
	/**
	 * 角色名称
	 */
	private String rolename;
	
	/**
	 * 菜单名称
	 */
	private String menuname;
	
	/**
	 * 是否启用
	 */
	private Boolean availabe;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	private Role role;
	
	private Menu menu;

    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid == null ? null : menuid.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
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