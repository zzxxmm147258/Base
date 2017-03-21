package com.hibo.cms.cate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年8月19日 上午10:53:28</p>
 * <p>类全名：com.hibo.cms.cate.model.CategoryBas</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class CategoryBas implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5111787493451909104L;

	private String code;

    private String pCode;

    private String name;

    private String ename;
    
    private String zhname;

    private String enname;
    
    private String img;

    private String eimg;

    private Boolean isTop;

    private Boolean availabe;

    private String operator;
    
	private Date createDate = new Date();
	private Date modifyDate = new Date();
	private List<CategoryBas> cates = new ArrayList<CategoryBas>();
	private MultipartFile myPhoto;
	
	
	public String getZhname() {
		return zhname;
	}
	public void setZhname(String zhname) {
		this.zhname = zhname;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.zhname = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
		this.enname = ename;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getEimg() {
		return eimg;
	}
	public void setEimg(String eimg) {
		this.eimg = eimg;
	}
	public Boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
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
	public List<CategoryBas> getCates() {
		return cates;
	}
	public void setCates(List<CategoryBas> cates) {
		this.cates = cates;
	}
	public MultipartFile getMyPhoto() {
		return myPhoto;
	}
	public void setMyPhoto(MultipartFile myPhoto) {
		this.myPhoto = myPhoto;
	}
	
}
