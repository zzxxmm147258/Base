package com.hibo.bas.fileplugin.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.fileplugin.model.FileInfo;
import com.hibo.bas.fileplugin.model.FileInfo.FileType;
import com.hibo.bas.fileplugin.model.FileInfo.OrderType;

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
 * 创建日期：2015年8月4日 上午9:24:40
 * </p>
 * <p>
 * 类全名：com.hibo.ebusi.product.service.FileService
 * </p>
 * 作者：马骏达 初审： 复审：
 */
public interface FileService {

	/**
	 * 文件验证
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 文件验证是否通过
	 */
	boolean isValid(FileType fileType, MultipartFile multipartFile, String filedef);
	
	boolean isValid(FileType fileType, MultipartFile multipartFile);

	String upload(MultipartFile multipartFile);
	
	String upload(MultipartFile multipartFile, String filedef, boolean async);
	
	String upload(File file);
	
	String upload(File file, String filedef, boolean async);
	
	public String upload(InputStream inputStream,String suffix);
	
	public String upload(InputStream inputStream, String filedef, String suffix);
	
	public String[] upload2(InputStream inputStream, String  sizeStr, String suffix) throws Exception;
	
	public String[] upload2(InputStream inputStream, String  sizeStr, String suffix, String logoText) throws Exception;
	
	public String[] upload2(InputStream inputStream, String  sizeStr, String suffix ,int model) throws Exception;
	
	public String[] upload2(InputStream inputStream, String  sizeStr, String suffix ,int model, String logoText) throws Exception;
	
	String[] upload(MultipartFile multipartFile, String sizeStr);
	
	String[] upload(MultipartFile multipartFile, String sizeStr, String logoText);
	
	String[] upload(MultipartFile multipartFile, String sizeStr,int model);
	
	String[] upload(MultipartFile multipartFile, String sizeStr,int model, String logoText);
	
	String[] upload(MultipartFile multipartFile, String filedef, String sizeStr, boolean async,int model, String logoText);
	
	String[] upload(File file, String sizeStr, int model);
	
	String[] upload(File file, String sizeStr, int model, String logoTex);
	
	String[] upload(File file, String filedef, String sizeStr, boolean async, int model, String logoTex);
	
	/**
	 * 文件浏览
	 * 
	 * @param path
	 *            浏览路径
	 * @param fileType
	 *            文件类型
	 * @param orderType
	 *            排序类型
	 * @return 文件信息
	 */
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType);
	
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType, String filedef);
	
}
