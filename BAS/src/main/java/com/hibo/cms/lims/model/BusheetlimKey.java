package com.hibo.cms.lims.model;



public class BusheetlimKey {
	

	
    private Short idx;

    private String limid;

    private String resourceid;

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

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid == null ? null : resourceid.trim();
    }
}