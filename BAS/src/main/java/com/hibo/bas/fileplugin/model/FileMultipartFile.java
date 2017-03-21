package com.hibo.bas.fileplugin.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月20日 下午9:52:13</p>
 * <p>类全名：com.hibo.bas.fileplugin.model.FileMultipartFile</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class FileMultipartFile implements Serializable{
	
	private static final long serialVersionUID = 2480948990297048713L;
	
	MultipartFile[] files;

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
}
