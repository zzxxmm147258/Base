package com.hibo.cms.shop.model;

import java.io.Serializable;

/** <p>标题：商铺条件model</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月18日 下午4:56:01</p>
 * <p>类全名：com.hibo.cms.shop.model.ShopConditionBas</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public class ShopConditionBas implements Serializable{
	
	private static final long serialVersionUID = 7378741519558903950L;

	private String shopname;
    
    private String floorNo;
    
    private String buildingNo;
    
    private String blockNo;
    
    private String district;
    
    private String city;

	public String getShopname() {
		return shopname;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public String getDistrict() {
		return district;
	}

	public String getCity() {
		return city;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
}
