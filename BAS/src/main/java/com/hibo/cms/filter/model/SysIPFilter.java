package com.hibo.cms.filter.model;

import com.hibo.bas.model.ModelBas;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月16日 下午1:47:30</p>
 * <p>类全名：com.hibo.cms.filter.model.SysIPFilter</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class SysIPFilter extends ModelBas{
	
	private static final long serialVersionUID = -6772648703112351929L;
	
	/**IP*/
	private String ip;
	
	/**名称*/
	private String name;
	
	/**端口*/
	private String port;
	
	/**备注*/
	private String remark;
	
	/**是否拦截,或允许*/
	private boolean ispassd;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isIspassd() {
		return ispassd;
	}

	public void setIspassd(boolean ispassd) {
		this.ispassd = ispassd;
	}

}
