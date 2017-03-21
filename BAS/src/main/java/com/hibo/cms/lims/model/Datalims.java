package com.hibo.cms.lims.model;



public class Datalims extends DatalimsKey {
	
	
    private String startdate;

    private String enddate;

	private String modifydate;//修改日期

    private String fldname01;

    private String values01;

    private String fldname02;

    private String values02;

    private String fldname03;

    private String values03;

    

   

    public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public String getFldname01() {
        return fldname01;
    }

    public void setFldname01(String fldname01) {
        this.fldname01 = fldname01 == null ? null : fldname01.trim();
    }

    public String getValues01() {
        return values01;
    }

    public void setValues01(String values01) {
        this.values01 = values01 == null ? null : values01.trim();
    }

    public String getFldname02() {
        return fldname02;
    }

    public void setFldname02(String fldname02) {
        this.fldname02 = fldname02 == null ? null : fldname02.trim();
    }

    public String getValues02() {
        return values02;
    }

    public void setValues02(String values02) {
        this.values02 = values02 == null ? null : values02.trim();
    }

    public String getFldname03() {
        return fldname03;
    }

    public void setFldname03(String fldname03) {
        this.fldname03 = fldname03 == null ? null : fldname03.trim();
    }

    public String getValues03() {
        return values03;
    }

    public void setValues03(String values03) {
        this.values03 = values03 == null ? null : values03.trim();
    }
}