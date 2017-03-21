package com.hibo.cms.lims.model;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月17日 下午3:13:25</p>
 * <p>类全名：com.hibo.cms.lims.model.Datalimm</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public class Datalimm {


	//权限类型
	private String limid;//权限编码
	
	private String limnm;//权限名称
	
	private int flags;//标记
	private String modifier;//修改人
	
	 
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifydate = new Date();//修改日期
	 
	private int startflags;//启用标记
	private String ord;//序号
	public Datalimm(){
		super();
	}
	
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	public Datalimm(String limid, String limnm, int flags, String modifier, Date modifydate, int startflags,
			String ord) {
		super();
		this.limid = limid;
		this.limnm = limnm;
		this.flags = flags;
		this.modifier = modifier;
		this.modifydate = modifydate;
		this.startflags = startflags;
		this.ord = ord;
	}





	public String getLimid() {
		return limid;
	}
	public void setLimid(String limid) {
		this.limid = limid;
	}
	public String getLimnm() {
		return limnm;
	}
	public void setLimnm(String limnm) {
		this.limnm = limnm;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifydate() {
		return modifydate;
	}
	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}
	public int getStartflags() {
		return startflags;
	}
	public void setStartflags(int startflags) {
		this.startflags = startflags;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	
}
