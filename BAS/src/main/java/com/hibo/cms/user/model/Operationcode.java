package com.hibo.cms.user.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.hibo.bas.model.ModelBas;

public class Operationcode extends ModelBas{
	
	@NotEmpty(message="操作码不能为空")
    private String operaid;

	@NotEmpty(message="操作名称不能为空")
    private String operaname;

    private String remark;

    private String operator;

    public String getOperaid() {
        return operaid;
    }

    public void setOperaid(String operaid) {
        this.operaid = operaid == null ? null : operaid.trim();
    }

    public String getOperaname() {
        return operaname;
    }

    public void setOperaname(String operaname) {
        this.operaname = operaname == null ? null : operaname.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }
}