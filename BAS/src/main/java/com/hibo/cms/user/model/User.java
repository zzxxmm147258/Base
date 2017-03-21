package com.hibo.cms.user.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.hibo.bas.util.ObjectId;

public class User implements Serializable {
	private static final long	serialVersionUID	= -2049753777001749038L;
	private String				userid				= ObjectId.getUUId();
	@Length(min = 1, max = 64, message = "长度不能超过64")
	private String				username;
	@NotEmpty(message = "密码不能为空")
	@Length(min = 6, max = 20, message = "密码长度必须在6至20之间")
	private String				password;
	private String				salt;
	private String				nickname;
	private Boolean				sex;
	private Date				birthday;
	private String				card;
	private String				address;
	private String				headpicture;
	private String				mail;
	private String				truename;
	private String				phone;
	private String				starttime;
	private String				endtime;
	private Boolean				locked;
	@Length(min = 6, max = 20, message = "支付密码长度必须在6至20之间")
	private String				paypass;
	private String				paysalt;
	private String				worklist;
	private String				sysidlist;
	private String				utype				= "10";					//默认为会员
	private String				uname;										//会员名称
	private String				cardtype;
	private String				province;
	private String				city;
	private String				postcode;
	private String				emailisactive;
	private String				shopid;
	private String				accounttype;
	private String				operator;
	private String				rank;
	private Integer				score;
	private Date				regeistdate;
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date				createDate			= new Date();
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date				modifyDate			= new Date();

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card == null ? null : card.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getHeadpicture() {
		return headpicture;
	}

	public void setHeadpicture(String headpicture) {
		this.headpicture = headpicture == null ? null : headpicture.trim();
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail == null ? null : mail.trim();
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename == null ? null : truename.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime == null ? null : starttime.trim();
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime == null ? null : endtime.trim();
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getPaypass() {
		return paypass;
	}

	public void setPaypass(String paypass) {
		this.paypass = paypass == null ? null : paypass.trim();
	}

	public String getPaysalt() {
		return paysalt;
	}

	public void setPaysalt(String paysalt) {
		this.paysalt = paysalt == null ? null : paysalt.trim();
	}

	public String getWorklist() {
		return worklist;
	}

	public void setWorklist(String worklist) {
		this.worklist = worklist == null ? null : worklist.trim();
	}

	public String getSysidlist() {
		return sysidlist;
	}

	public void setSysidlist(String sysidlist) {
		this.sysidlist = sysidlist == null ? null : sysidlist.trim();
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype == null ? null : utype.trim();
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname == null ? null : uname.trim();
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype == null ? null : cardtype.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode == null ? null : postcode.trim();
	}

	public String getEmailisactive() {
		return emailisactive;
	}

	public void setEmailisactive(String emailisactive) {
		this.emailisactive = emailisactive == null ? null : emailisactive.trim();
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid == null ? null : shopid.trim();
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype == null ? null : accounttype.trim();
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator == null ? null : operator.trim();
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank == null ? null : rank.trim();
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getRegeistdate() {
		return regeistdate;
	}

	public void setRegeistdate(Date regeistdate) {
		this.regeistdate = regeistdate;
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