package com.hibo.bas.fileplugin.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.hibo.bas.fileplugin.StoragePlugin;
import com.hibo.bas.fileplugin.model.FileInfo;
import com.hibo.bas.util.DataConfig;

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
 * 创建日期：2015年8月31日 下午5:19:19
 * </p>
 * <p>
 * 类全名：com.hibo.ebusi.plugin.file.FilePlugin
 * </p>
 * 作者：马骏达 初审： 复审：
 */
@Component("filePlugin")
public class FilePlugin extends StoragePlugin implements ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public int compareTo(StoragePlugin o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSiteUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInstallUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUninstallUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSettingUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void upload(String path, File file, String contentType) {
		String filePath = DataConfig.getConfig("IMGFILEPATH");
		if (filePath != null) {
			if (!filePath.startsWith("/")) {
				filePath = "/" + filePath;
			}
			if (!filePath.endsWith("/")) {
				filePath = filePath + "/";
			}
		}
		filePath = filePath + path;
		File destFile = new File(servletContext.getRealPath(filePath));
		try {
			FileUtils.moveFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl(String path) {
		String url = DataConfig.getConfig("SITEURL") + DataConfig.getConfig("IMGFILEPATH") + path;
		return url;
	}

	@Override
	public List<FileInfo> browser(String path) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		File directory = new File(servletContext.getRealPath(path));
		if (directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(file.getName());
				fileInfo.setUrl(DataConfig.getConfig("SITEURL") + path + file.getName());
				fileInfo.setIsDirectory(file.isDirectory());
				fileInfo.setSize(file.length());
				fileInfo.setLastModified(new Date(file.lastModified()));
				fileInfos.add(fileInfo);
			}
		}
		return fileInfos;
	}

}
