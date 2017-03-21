package com.hibo.cms.lims.model;

public class Busheetlim extends BusheetlimKey{
	
	
    private String resname;

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname == null ? null : resname.trim();
    }
}