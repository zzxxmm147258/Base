package com.hibo.cms.lims.model;



public class DatalimsKey {
	
	
    private Short idx;
	 
    private String limid;

    private String opid;
    
    private String ucode;
 
    private String utype;

    public Short getIdx() {
        return idx;
    }

    public void setIdx(Short idx) {
        this.idx = idx;
    }

    public String getLimid() {
        return limid;
    }

    public void setLimid(String limid) {
        this.limid = limid == null ? null : limid.trim();
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode == null ? null : ucode.trim();
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype == null ? null : utype.trim();
    }
}