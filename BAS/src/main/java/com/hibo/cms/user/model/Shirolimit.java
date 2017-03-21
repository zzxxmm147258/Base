package com.hibo.cms.user.model;

import javax.validation.constraints.DecimalMax;

import org.hibernate.validator.constraints.NotEmpty;

import com.hibo.bas.model.ModelBas;

public class Shirolimit extends ModelBas{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="系统id不能为空")
    private String sysid;

	@NotEmpty(message="名称不能为空")
    private String name;

    private String url;

    private String limits;
    
    @DecimalMax(value="99",message="必须两位数的数字")
    private Integer idx;

    private String remark;

    private Boolean availabe;

    private String operator;

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid == null ? null : sysid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits == null ? null : limits.trim();
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        this.operator = operator == null ? null : operator.trim();
    }
    
}