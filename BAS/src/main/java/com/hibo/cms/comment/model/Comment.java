package com.hibo.cms.comment.model;

import java.util.Date;

public class Comment {

	/** id */
	private String id;

	/** 评论回复内容 **/
	private String conunt;

	private String num;// 回复数

	private String userid;

	private Date createDate;

	private String username;

	private String truename;

	private String headpicture;

	private String nickname;

	private String busername;

	private String btruename;

	private String bheadpicture;

	private String bnickname;

	private String praiseNum;

	private String buserid;

	private boolean iszan;

	private String coimgUrl;

	private String coimgSmallurl;

	private String coimgSmaother;

	private String type;
	
	private String 	isRead;
	
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getCoimgUrl() {
		return coimgUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCoimgUrl(String coimgUrl) {
		this.coimgUrl = coimgUrl;
	}

	public String getCoimgSmallurl() {
		return coimgSmallurl;
	}

	public void setCoimgSmallurl(String coimgSmallurl) {
		this.coimgSmallurl = coimgSmallurl;
	}

	public String getCoimgSmaother() {
		return coimgSmaother;
	}

	public void setCoimgSmaother(String coimgSmaother) {
		this.coimgSmaother = coimgSmaother;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public boolean isIszan() {
		return iszan;
	}

	public void setIszan(boolean iszan) {
		this.iszan = iszan;
	}

	public String getBuserid() {
		return buserid;
	}

	public void setBuserid(String buserid) {
		this.buserid = buserid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConunt() {
		return conunt;
	}

	public void setConunt(String conunt) {
		this.conunt = conunt;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getHeadpicture() {
		return headpicture;
	}

	public void setHeadpicture(String headpicture) {
		this.headpicture = headpicture;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBusername() {
		return busername;
	}

	public void setBusername(String busername) {
		this.busername = busername;
	}

	public String getBtruename() {
		return btruename;
	}

	public void setBtruename(String btruename) {
		this.btruename = btruename;
	}

	public String getBheadpicture() {
		return bheadpicture;
	}

	public void setBheadpicture(String bheadpicture) {
		this.bheadpicture = bheadpicture;
	}

	public String getBnickname() {
		return bnickname;
	}

	public void setBnickname(String bnickname) {
		this.bnickname = bnickname;
	}

	public String getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}

}