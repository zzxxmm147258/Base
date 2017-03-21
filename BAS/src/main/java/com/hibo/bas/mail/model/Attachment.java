package com.hibo.bas.mail.model;

import org.springframework.core.io.ByteArrayResource;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月12日 下午5:31:00</p>
 * <p>类全名：com.hibo.bas.mail.model.Attachment</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class Attachment {
	
	private String fileName;
	
	private ByteArrayResource data;

	public Attachment() {
	}
	
	public Attachment(String fileName,ByteArrayResource data) {
		this.fileName = fileName;
		this.data = data;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ByteArrayResource getData() {
		return data;
	}

	public void setData(ByteArrayResource data) {
		this.data = data;
	}

	

	
}
