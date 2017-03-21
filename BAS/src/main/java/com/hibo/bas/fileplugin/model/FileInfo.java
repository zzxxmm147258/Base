package com.hibo.bas.fileplugin.model;
import java.util.Date;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年8月4日 上午9:25:49
 * </p>
 * <p>
 * 类全名：.FileInfo
 * </p>
 * 作者：马骏达 初审： 复审：
 */
public class FileInfo {

	/**
	 * 文件类型
	 */
	public enum FileType {

		/** 图片 */
		image,

		/** Flash */
		flash,

		/** 媒体 */
		media,

		/** 文件 */
		file
	}

	/**
	 * 排序类型
	 */
	public enum OrderType {

		/** 名称 */
		name,

		/** 大小 */
		size,

		/** 类型 */
		type
	}

	/** 名称 */
	private String name;

	/** URL */
	private String url;

	/** 是否为目录 */
	private Boolean isDirectory;

	/** 大小 */
	private Long size;

	/** 最后修改日期 */
	private Date lastModified;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取URL
	 * 
	 * @return URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置URL
	 * 
	 * @param url
	 *            URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取是否为目录
	 * 
	 * @return 是否为目录
	 */
	public Boolean getIsDirectory() {
		return isDirectory;
	}

	/**
	 * 设置是否为目录
	 * 
	 * @param isDirectory
	 *            是否为目录
	 */
	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	/**
	 * 获取大小
	 * 
	 * @return 大小
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * 设置大小
	 * 
	 * @param size
	 *            大小
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * 获取最后修改日期
	 * 
	 * @return 最后修改日期
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * 设置最后修改日期
	 * 
	 * @param lastModified
	 *            最后修改日期
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
