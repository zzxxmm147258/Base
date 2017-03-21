package com.hibo.cms.readexcel.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Readexceldefg implements Serializable{
    
	private static final long serialVersionUID = -7962092633537712230L;

	@NotEmpty(message="不能为空")
	private String fldname;

    private String rxcode;
	
	private String flddisplayname;

    private String excelcol;

    private String fldtype;

    private Short fldsize;

    private Short flddecimal;

    private String flddefault;

    private String groupdesc;

    private Short idx;

    private Short flags;

    private String groovy;
    
    private String operator;
    
    @DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate = new Date();
	
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate = new Date();

    public String getFldname() {
        return fldname;
    }

    public void setFldname(String fldname) {
        this.fldname = fldname == null ? null : fldname.trim();
    }

    public String getRxcode() {
        return rxcode;
    }

    public void setRxcode(String rxcode) {
        this.rxcode = rxcode == null ? null : rxcode.trim();
    }
    
    public String getFlddisplayname() {
        return flddisplayname;
    }

    public void setFlddisplayname(String flddisplayname) {
        this.flddisplayname = flddisplayname == null ? null : flddisplayname.trim();
    }

    public String getExcelcol() {
        return excelcol;
    }

    public void setExcelcol(String excelcol) {
        this.excelcol = excelcol == null ? null : excelcol.trim();
    }

    public String getFldtype() {
        return fldtype;
    }

    public void setFldtype(String fldtype) {
        this.fldtype = fldtype == null ? null : fldtype.trim();
    }

    public Short getFldsize() {
        return fldsize;
    }

    public void setFldsize(Short fldsize) {
        this.fldsize = fldsize;
    }

    public Short getFlddecimal() {
        return flddecimal;
    }

    public void setFlddecimal(Short flddecimal) {
        this.flddecimal = flddecimal;
    }

    public String getFlddefault() {
        return flddefault;
    }

    public void setFlddefault(String flddefault) {
        this.flddefault = flddefault == null ? null : flddefault.trim();
    }

    public String getGroupdesc() {
        return groupdesc;
    }

    public void setGroupdesc(String groupdesc) {
        this.groupdesc = groupdesc == null ? null : groupdesc.trim();
    }

    public Short getIdx() {
        return idx;
    }

    public void setIdx(Short idx) {
        this.idx = idx;
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