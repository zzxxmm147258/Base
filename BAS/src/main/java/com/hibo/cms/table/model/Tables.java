package com.hibo.cms.table.model;

import org.hibernate.validator.constraints.NotEmpty;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月30日 下午5:34:09</p>
 * <p>类全名：com.hibo.cms.table.model.Tables</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public class Tables {
	
	@NotEmpty(message="系统ID不能为空")
	private String sysid;

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
}
