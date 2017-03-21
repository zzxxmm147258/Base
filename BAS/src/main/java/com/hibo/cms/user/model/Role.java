package com.hibo.cms.user.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hibo.bas.model.ModelBas;

public class Role extends ModelBas {

	private static final long serialVersionUID = -3986984294040521017L;

	@NotEmpty(message="角色ID不能为空")
    private String roleid;

	@NotEmpty(message="角色类型不能为空")
    private String rotype;

	@NotEmpty(message="角色名称不能为空")
	@Length(min=1,max=64,message="长度不能超过64")
    private String rolename;

    private String description;

    private Boolean availabe;
    
    /**
     * 操作人
     */
    private String operator;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getRotype() {
        return rotype;
    }

    public void setRotype(String rotype) {
        this.rotype = rotype == null ? null : rotype.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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