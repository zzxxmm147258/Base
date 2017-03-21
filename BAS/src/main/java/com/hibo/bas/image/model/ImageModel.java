package com.hibo.bas.image.model;

import java.io.InputStream;
import java.io.Serializable;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月3日 下午3:26:06</p>
 * <p>类全名：com.hibo.bas.image.model.ImageModel</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class ImageModel implements Serializable{

	private static final long serialVersionUID = -7039139600741717472L;
	
	private String suffix = "png";
	
	private InputStream inputStream;
	
	private byte[] bytes;

	public ImageModel() {
	}
	
	public ImageModel(InputStream inputStream, String suffix) {
		this.inputStream = inputStream;
		this.suffix = suffix;
	}
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
