package com.hibo.cms.push.model;

import java.util.Date;

public class PushDeviceBas {
    private String userId;

    private String deviceId;

    private String attrName1;

    private String attrName2;

    private Date createDate;

    private Date modifyDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getAttrName1() {
        return attrName1;
    }

    public void setAttrName1(String attrName1) {
        this.attrName1 = attrName1 == null ? null : attrName1.trim();
    }

    public String getAttrName2() {
        return attrName2;
    }

    public void setAttrName2(String attrName2) {
        this.attrName2 = attrName2 == null ? null : attrName2.trim();
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