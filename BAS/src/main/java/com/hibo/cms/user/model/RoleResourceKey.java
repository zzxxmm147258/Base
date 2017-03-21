package com.hibo.cms.user.model;

import com.hibo.bas.model.ModelBas;
import com.hibo.bas.util.ObjectId;

public class RoleResourceKey extends ModelBas{
	private static final long serialVersionUID = 1L;

	private String roreid = ObjectId.getUUId();;

    private String sysid;

    private String roleid;

    private String resourceid;

    private String operaid;

    private Integer idx;

    private String rolename;

    private String resname;

    private String operaname;

    private Integer availabe;

    private String operator;

    public String getRoreid() {
        return roreid;
    }

    public void setRoreid(String roreid) {
        this.roreid = roreid == null ? null : roreid.trim();
    }

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid == null ? null : sysid.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid == null ? null : resourceid.trim();
    }

    public String getOperaid() {
        return operaid;
    }

    public void setOperaid(String operaid) {
        this.operaid = operaid == null ? null : operaid.trim();
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname == null ? null : resname.trim();
    }

    public String getOperaname() {
        return operaname;
    }

    public void setOperaname(String operaname) {
        this.operaname = operaname == null ? null : operaname.trim();
    }

    public Integer getAvailabe() {
        return availabe;
    }

    public void setAvailabe(Integer availabe) {
        this.availabe = availabe;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }
}