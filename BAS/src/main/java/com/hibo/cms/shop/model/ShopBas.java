package com.hibo.cms.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


public class ShopBas implements Serializable{
    
	private static final long serialVersionUID = 557825506721769205L;

	private String shopId;

    private String shopname;
    
    private String enshopname;

    private String floorId;
    
    private String district;;

    private String disename;

    private Boolean availabe;

    private String operator;
    
    private String shopAddress;
    
    private String shopType;
    
    private String shopLogo;
    
    private Double longitude;
    
    private Double latitude;
    
    private Integer nice;
    
    private Integer sort;
    
    private String  phone;

    private String  perConsume;
    
    private String  images;
    
    private String  chaIcon;
    
    private String  province;
    
    private String  city;
    
    private String replyNo;

    private Integer districtIndex;
    
    private Integer   blockIndex;
    
    private Integer buildingIndex;
    
    private Integer floorIndex;
    
    private String imagess[];
    
    private String chaIcons[];
    
    private List<ShopBasInfo> shopbasinfo;

    
	public String getEnshopname() {
		return enshopname;
	}

	public void setEnshopname(String enshopname) {
		this.enshopname = enshopname;
	}

	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate = new Date();

    @DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate=new Date();
    
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDisename() {
		return disename;
	}

	public void setDisename(String disename) {
		this.disename = disename;
	}

	public Integer getBuildingIndex() {
		return buildingIndex;
	}

	public void setBuildingIndex(Integer buildingIndex) {
		this.buildingIndex = buildingIndex;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
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
		this.operator = operator;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}


	public Integer getNice() {
		return nice;
	}

	public void setNice(Integer nice) {
		this.nice = nice;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPerConsume() {
		return perConsume;
	}

	public void setPerConsume(String perConsume) {
		this.perConsume = perConsume;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getChaIcon() {
		return chaIcon;
	}

	public void setChaIcon(String chaIcon) {
		this.chaIcon = chaIcon;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(String replyNo) {
		this.replyNo = replyNo;
	}

	public Integer getDistrictIndex() {
		return districtIndex;
	}

	public void setDistrictIndex(Integer districtIndex) {
		this.districtIndex = districtIndex;
	}

	public String[] getImagess() {
		return imagess;
	}

	public void setImagess(String[] imagess) {
		this.imagess = imagess;
	}

	public String[] getChaIcons() {
		return chaIcons;
	}

	public void setChaIcons(String[] chaIcons) {
		this.chaIcons = chaIcons;
	}

	public List<ShopBasInfo> getShopbasinfo() {
		return shopbasinfo;
	}

	public void setShopbasinfo(List<ShopBasInfo> shopbasinfo) {
		this.shopbasinfo = shopbasinfo;
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

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public Integer getBlockIndex() {
		return blockIndex;
	}

	public void setBlockIndex(Integer blockIndex) {
		this.blockIndex = blockIndex;
	}

	public Integer getBuidingIndex() {
		return buildingIndex;
	}

	public void setBuidingIndex(Integer buildingIndex) {
		this.buildingIndex = buildingIndex;
	}

	public Integer getFloorIndex() {
		return floorIndex;
	}

	public void setFloorIndex(Integer floorIndex) {
		this.floorIndex = floorIndex;
	}
    
    

   
}