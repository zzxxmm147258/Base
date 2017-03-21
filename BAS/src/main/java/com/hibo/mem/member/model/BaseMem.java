package com.hibo.mem.member.model;

import java.io.Serializable;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年6月16日 下午5:12:59</p>
 * <p>类全名：com.hibo.mem.member.model.BaseMem</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class BaseMem implements Serializable{

	private static final long serialVersionUID = 2928548650566375638L;

	private String userid;

	private String username;
	
    private String truename;

    private String sex;
    
    private String nickname;

    private String headpicture;
    
    private String utype;
    
    private String sysId;
    
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadpicture() {
		return headpicture;
	}

	public void setHeadpicture(String headpicture) {
		this.headpicture = headpicture;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	
}
