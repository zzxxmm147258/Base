package com.hibo.cms.readexcel.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Readexceldef implements Serializable{
	
	private static final long serialVersionUID = -5633394107963354363L;

	@NotEmpty(message="不能为空")
    private String rxcode;

    private String rxname;

    private String tblname;

    private String model;

    private String excelsheetname;

    private Short excelfromrow;

    private Short fortimes;

    private Short flags;

    private String groovy;

    private String operator;
    
    @DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate = new Date();
	
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate = new Date();
    
    public String getRxcode() {
        return rxcode;
    }

    public void setRxcode(String rxcode) {
        this.rxcode = rxcode == null ? null : rxcode.trim();
    }

    public String getRxname() {
        return rxname;
    }

    public void setRxname(String rxname) {
        this.rxname = rxname == null ? null : rxname.trim();
    }

    public String getTblname() {
        return tblname;
    }

    public void setTblname(String tblname) {
        this.tblname = tblname == null ? null : tblname.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getExcelsheetname() {
        return excelsheetname;
    }

    public void setExcelsheetname(String excelsheetname) {
        this.excelsheetname = excelsheetname == null ? null : excelsheetname.trim();
    }

    public Short getExcelfromrow() {
        return excelfromrow;
    }

    public void setExcelfromrow(Short excelfromrow) {
        this.excelfromrow = excelfromrow;
    }

    public Short getFortimes() {
        return fortimes;
    }

    public void setFortimes(Short fortimes) {
        this.fortimes = fortimes;
    }

    public Short getFlags() {
        return flags;
    }

    public void setFlags(Short flags) {
        this.flags = flags;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getGroovy() {
        return groovy;
    }

    public void setGroovy(String groovy) {
        this.groovy = groovy == null ? null : groovy.trim();
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
    
}