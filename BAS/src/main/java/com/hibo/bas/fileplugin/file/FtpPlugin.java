/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hibo.bas.fileplugin.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

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
 * 创建日期：2015年8月4日 上午9:29:07
 * </p>
 * <p>
 * 类全名：com.hibo.ebusi.product.service.impl.FileServiceImpl
 * </p>
 * 作者：马骏达 初审： 复审：
 */
@Component("ftpPlugin")
public class FtpPlugin extends StoragePlugin {

	@Override
	public String getName() {
		return "FTP存储";
	}

	@Override
	public String getVersion() {
		return "";
	}

	@Override
	public String getAuthor() {
		return "";
	}

	@Override
	public String getSiteUrl() {
		return "";
	}

	@Override
	public String getInstallUrl() {
		return "";
	}

	@Override
	public String getUninstallUrl() {
		return "";
	}

	@Override
	public String getSettingUrl() {
		return "";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		Map<String, String> ftpInfo = getFtpInfo(contentType);
		if (!"".equals(ftpInfo.get("host")) && !"".equals(ftpInfo.get("username"))
				&& !"".equals(ftpInfo.get("password"))) {
			FTPClient ftpClient = new FTPClient();
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(file);
				ftpClient.connect(ftpInfo.get("host"), 21);
				ftpClient.login(ftpInfo.get("username"), ftpInfo.get("password"));
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				path = ftpInfo.get("path") + path;
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
					String directory = StringUtils.substringBeforeLast(path, "/");
					String filename = StringUtils.substringAfterLast(path, "/");
					if (!ftpClient.changeWorkingDirectory(directory)) {
						String[] paths = StringUtils.split(directory, "/");
						String p = "/";
						ftpClient.changeWorkingDirectory(p);
						for (String s : paths) {
							p += s + "/";
							if (!ftpClient.changeWorkingDirectory(p)) {
								ftpClient.makeDirectory(s);
								ftpClient.changeWorkingDirectory(p);
							}
						}
					}
					ftpClient.storeFile(filename, inputStream);
					ftpClient.logout();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	@Override
	public String getUrl(String path) {
		return DataConfig.getConfig("IMGFTPROOT") + getFtpInfo("all").get("path") + path;
	}

	@Override
	public List<FileInfo> browser(String path) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		// PluginConfig pluginConfig = getPluginConfig();
		// if (pluginConfig != null) {
		Map<String, String> ftpInfo = getFtpInfo("all");
		String urlPrefix = DataConfig.getConfig("IMGFTPROOT");
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpInfo.get("host"), 21);
			ftpClient.login(ftpInfo.get("username"), ftpInfo.get("password"));
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()) && ftpClient.changeWorkingDirectory(path)) {
				for (FTPFile ftpFile : ftpClient.listFiles()) {
					FileInfo fileInfo = new FileInfo();
					fileInfo.setName(ftpFile.getName());
					fileInfo.setUrl(urlPrefix + path + ftpFile.getName());
					fileInfo.setIsDirectory(ftpFile.isDirectory());
					fileInfo.setSize(ftpFile.getSize());
					fileInfo.setLastModified(ftpFile.getTimestamp().getTime());
					fileInfos.add(fileInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
				}
			}
		}
		// }
		return fileInfos;
	}

	@Override
	public int compareTo(StoragePlugin o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map<String, String> getFtpInfo(String type) {
		Map<String, String> info = new HashMap<String, String>();
		String ftpUrl = "";
		if (type.contains("image") || type.contains("all")) {
			ftpUrl = DataConfig.getConfig("IMGFTPURL");
		}
		if (!ftpUrl.endsWith("/")) {
			ftpUrl = ftpUrl + "/";
		}
		if (ftpUrl != null && ftpUrl.length() > 0 && ftpUrl.startsWith("ftp://")) {
			Date now = new Date();
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
			String datePath = simpleDate.format(now).toString() + "/";
			ftpUrl = ftpUrl.substring(6);
			String username = ftpUrl.substring(0, ftpUrl.indexOf(":"));
			info.put("username", username);
			String password = ftpUrl.substring(ftpUrl.indexOf(":") + 1, ftpUrl.indexOf("@"));
			info.put("password", password);
			String host = ftpUrl.substring(ftpUrl.indexOf("@") + 1, ftpUrl.indexOf("/"));
			String path = ftpUrl.substring(ftpUrl.indexOf("/")) + datePath;
			info.put("host", host);
			info.put("path", path);
		}

		return info;
	}

}