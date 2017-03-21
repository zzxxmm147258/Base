package com.hibo.cms.user.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hibo.bas.model.ModelBas;

public class Menu extends ModelBas{
	@NotEmpty(message="菜单ID不能为空")
    private String menuid;

    private String sysid;

    @NotEmpty(message="菜单名称不能为空")
    @Length(min=1,max=64,message="长度不能超过64")
    private String menuname;

    @NotNull(message="级别不能为空")
    private Integer level;

    @NotEmpty(message="链接不能为空")
    private String url;

    private Boolean availabe;
    
    //显示方式
    private String showType;
    
    /**
     * 操作人
     */
    private String operator;
    
	private List<Menu> children;

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

    public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid == null ? null : menuid.trim();
    }

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid == null ? null : sysid.trim();
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getAvailabe() {
        return availabe;
    }

    public void setAvailabe(Boolean availabe) {
        this.availabe = availabe;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
    
}