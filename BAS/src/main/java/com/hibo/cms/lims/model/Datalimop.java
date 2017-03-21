package com.hibo.cms.lims.model;



public class Datalimop extends DatalimopKey{
	
	
    private String opnm;

    public String getOpnm() {
        return opnm;
    }

    public void setOpnm(String opnm) {
        this.opnm = opnm == null ? null : opnm.trim();
    }
}