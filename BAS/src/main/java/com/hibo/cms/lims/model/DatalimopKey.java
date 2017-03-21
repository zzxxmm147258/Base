package com.hibo.cms.lims.model;



public class DatalimopKey {
	
	
    private String limid;

    private String opid;

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
}