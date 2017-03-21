package com.hibo.cms.shop.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.util.ObjectId;
import com.hibo.bas.util.ObjectUtil;
/**
 * <p>标题：商铺</p>
 * <p>功能：商铺简介信息 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月30日 下午2:02:13</p>
 * <p>类全名：com.hibo.cms.shop.model.ShopBasInfo</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class ShopBasInfo implements Serializable{
	
	private static final long serialVersionUID = 5478842496639305056L;

	/**商铺id*/
	private String shopId;
	/**Id*/
    private String id= ObjectId.getUUId();
    /**商铺名*/
    private String shopname;
    /**展示类型*/
    private String showType;
    /**展示位置*/
    private Integer showPoint;
    /**展示标题*/
    private String showTitle;
    /**展示图片*/
    private String showImage;
    /**展示说明*/
    private String showExplain;
    /**是否启用*/
    private Boolean aviliable;
    /**操作人*/
    private String operator;
    
    private MultipartFile imgs;
    
    

    public MultipartFile getImgs() {
		return imgs;
	}

	public void setImgs(MultipartFile imgs) {
		this.imgs = imgs;
	}

	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate = new Date();

    @DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate = new Date();

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType == null ? null : showType.trim();
    }

    public Integer getShowPoint() {
        return showPoint;
    }

    public void setShowPoint(Integer showPoint) {
        this.showPoint = showPoint;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle == null ? null : showTitle.trim();
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage == null ? null : showImage.trim();
    }

    public String getShowExplain() {
        return showExplain;
    }

    public void setShowExplain(String showExplain) {
        this.showExplain = showExplain == null ? null : showExplain.trim();
    }

    public Boolean getAviliable() {
        return aviliable;
    }

    public void setAviliable(Boolean aviliable) {
        this.aviliable = aviliable;
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