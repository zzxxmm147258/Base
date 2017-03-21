package com.hibo.cms.sys.utils.gci;

import java.io.Serializable;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月8日 下午5:52:56</p>
 * <p>类全名：com.hibo.cms.sys.utils.gci.GicResult</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class GicResult implements Serializable{
	
	private static final long serialVersionUID = 2204085110562777637L;
	
	private String result;//结果
	private boolean visible = false;//是否显示
	private boolean available = false;//是否可用
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
}
