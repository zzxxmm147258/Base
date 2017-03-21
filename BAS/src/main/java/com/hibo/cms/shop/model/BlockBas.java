package com.hibo.cms.shop.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.hibo.bas.util.ObjectId;

public class BlockBas implements Serializable{

	private static final long serialVersionUID = -8241520468884020158L;

	private String blockId = ObjectId.getUUId();

    private String blockNo;

    private String district;

    private String blockName;

    private String blockLogo;

    private Integer idx;

    private Boolean availabe;

    private String operator;

    @DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate = new Date();
	
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate = new Date();

	private String tourImg;
	
	
	
    public String getTourImg() {
		return tourImg;
	}

	public void setTourImg(String tourImg) {
		this.tourImg = tourImg;
	}

	public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId == null ? null : blockId.trim();
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo == null ? null : blockNo.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName == null ? null : blockName.trim();
    }

    public String getBlockLogo() {
        return blockLogo;
    }

    public void setBlockLogo(String blockLogo) {
        this.blockLogo = blockLogo == null ? null : blockLogo.trim();
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
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
}