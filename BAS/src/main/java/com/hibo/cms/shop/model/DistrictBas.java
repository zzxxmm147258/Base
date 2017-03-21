package com.hibo.cms.shop.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class DistrictBas {
    private String district;

    private String disname;

    private Boolean availabe;

    private String operator;
    
    private String city;
    
    private int idx;
    
    private String districtLogo;

    @DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate = new Date();

    @DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate=new Date();
    
    private String tourImg;
    
    
    public String getTourImg() {
		return tourImg;
	}

	public void setTourImg(String tourImg) {
		this.tourImg = tourImg;
	}

	public String getDistrictLogo() {
		return districtLogo;
	}

	public void setDistrictLogo(String districtLogo) {
		this.districtLogo = districtLogo;
	}

	public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getDisname() {
        return disname;
    }

    public void setDisname(String disname) {
        this.disname = disname == null ? null : disname.trim();
    }

    public Boolean getAvailabe() {
        return availabe;
    }

    public void setAvailabe(Boolean availabe) {
        this.availabe = availabe;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
     
}