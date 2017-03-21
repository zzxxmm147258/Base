package com.hibo.cms.lims.model;



public class Datalimflds extends DatalimfldsKey  {
	
	
    private String cfldname;

    private Integer fldtype;

    private Integer idx;

    private Integer fldopts;

    private String unlimckvals;

    public String getCfldname() {
        return cfldname;
    }

    public void setCfldname(String cfldname) {
        this.cfldname = cfldname == null ? null : cfldname.trim();
    }

    public Integer getFldtype() {
        return fldtype;
    }

    public void setFldtype(Integer fldtype) {
        this.fldtype = fldtype;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getFldopts() {
        return fldopts;
    }

    public void setFldopts(Integer fldopts) {
        this.fldopts = fldopts;
    }

    public String getUnlimckvals() {
        return unlimckvals;
    }

    public void setUnlimckvals(String unlimckvals) {
        this.unlimckvals = unlimckvals == null ? null : unlimckvals.trim();
    }
}