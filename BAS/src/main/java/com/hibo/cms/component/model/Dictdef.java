package com.hibo.cms.component.model;

import java.io.Serializable;

public class Dictdef implements Serializable{
	
	private static final long serialVersionUID = -4777371682194047322L;

	private Integer dictid;
	
	private String cname;
	
	private String ename;
	
    private String zhname;
	
	private String enname;
	
	private String remark;
	

	public String getZhname() {
		return zhname;
	}

	public void setZhname(String zhname) {
		this.zhname = zhname;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public Integer getDictid() {
		return dictid;
	}

	public void setDictid(Integer dictid) {
		this.dictid = dictid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
		this.zhname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
		this.enname = ename;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
