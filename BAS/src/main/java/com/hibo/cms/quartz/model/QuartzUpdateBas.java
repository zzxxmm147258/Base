package com.hibo.cms.quartz.model;

import java.util.Date;

import com.hibo.bas.util.DateUtil;

public class QuartzUpdateBas {
    private String id;

    private String updateName;

    private Date updateDate;
    
    private String supdateDate;
    
    private int locked;
    
    private String attrName1;
    
    private String attrName2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
    }

    public Date getUpdateDate() {
		return updateDate;
	}

    public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
    
    public String getSupdateDate() {
		return DateUtil.dateToString(updateDate);
	}
    
    //接收网页提交的日期
	public void setSupdateDate(String supdateDate) {
		this.updateDate = DateUtil.toDate(supdateDate);
	}
	
	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}
	
    public String getAttrName1() {
		return attrName1;
	}

	public void setAttrName1(String attrName1) {
		this.attrName1 = attrName1;
	}
	
    public String getAttrName2() {
		return attrName2;
	}

	public void setAttrName2(String attrName2) {
		this.attrName2 = attrName2;
	}
	
}