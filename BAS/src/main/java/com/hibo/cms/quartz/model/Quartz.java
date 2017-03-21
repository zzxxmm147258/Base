package com.hibo.cms.quartz.model;

import java.util.Date;

import org.springframework.stereotype.Component;
/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月14日 下午8:14:32</p>
 * <p>类全名：com.hibo.cms.quartz.model.Quartz</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */

import com.hibo.bas.model.ModelBas;
@Component
public class Quartz extends ModelBas{
	
	private static final long serialVersionUID = -3405366055673834165L;
	
	private String jobname;
	
	private String description;
	
	private String jobgroup;
	
	private String tiggername;
	
	private String tiggergroup;
	
	private String jobparams;
	
	private String executeclass;
	
	private String executetime;
	
	private Date startDate;
	
	private Date endDate;
	
	private String schename;
	//当前状态
	private int qtype;
	//当前状态名
	private String qtypename;
	//定义状态
	private int utype;
	//所在服务器
	private String qserver;
	
	private boolean disabled;
	
	private String isqserver;//辅助字段
	private String iscount;//辅助字段
	private String status;//辅助字段
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsqserver() {
		return isqserver;
	}

	public void setIsqserver(String isqserver) {
		this.isqserver = isqserver;
	}

	public String getIscount() {
		return iscount;
	}

	public void setIscount(String iscount) {
		this.iscount = iscount;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobgroup() {
		return jobgroup;
	}

	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}

	public String getTiggername() {
		return tiggername;
	}

	public void setTiggername(String tiggername) {
		this.tiggername = tiggername;
	}

	public String getTiggergroup() {
		return tiggergroup;
	}

	public void setTiggergroup(String tiggergroup) {
		this.tiggergroup = tiggergroup;
	}

	public String getJobparams() {
		return jobparams;
	}

	public void setJobparams(String jobparams) {
		this.jobparams = jobparams;
	}

	public String getExecuteclass() {
		return executeclass;
	}

	public void setExecuteclass(String executeclass) {
		this.executeclass = executeclass;
	}

	public String getExecutetime() {
		return executetime;
	}

	public void setExecutetime(String executetime) {
		this.executetime = executetime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSchename() {
		return schename;
	}

	public void setSchename(String schename) {
		this.schename = schename;
	}

	public int getQtype() {
		return qtype;
	}

	public void setQtype(int qtype) {
		this.qtype = qtype;
	}

	public String getQtypename() {
		return qtypename;
	}

	public void setQtypename(String qtypename) {
		this.qtypename = qtypename;
	}

	public int getUtype() {
		return utype;
	}

	public void setUtype(int utype) {
		this.utype = utype;
	}

	public String getQserver() {
		return qserver;
	}

	public void setQserver(String qserver) {
		this.qserver = qserver;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
}
