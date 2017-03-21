package com.hibo.cms.lims.model;


public class DatalimfldsKey {
	
	
    private String fldname;

    private String limid;

    public String getFldname() {
        return fldname;
    }

    public void setFldname(String fldname) {
        this.fldname = fldname == null ? null : fldname.trim();
    }

    public String getLimid() {
        return limid;
    }

    public void setLimid(String limid) {
        this.limid = limid == null ? null : limid.trim();
    }
}