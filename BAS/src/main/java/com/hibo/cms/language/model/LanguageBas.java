package com.hibo.cms.language.model;

import com.hibo.bas.model.ModelBas;

public class LanguageBas extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4083092820886903345L;

	private String code;

    private String en;

    private String zh;

    private String operator;



    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en == null ? null : en.trim();
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh == null ? null : zh.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

}