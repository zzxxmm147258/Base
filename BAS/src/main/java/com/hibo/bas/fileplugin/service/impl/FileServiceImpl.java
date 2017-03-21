package com.hibo.bas.fileplugin.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.file.FileSystem;
import com.hibo.bas.fileplugin.model.FileInfo;
import com.hibo.bas.fileplugin.model.FileInfo.FileType;
import com.hibo.bas.fileplugin.model.FileInfo.OrderType;
import com.hibo.bas.fileplugin.service.FileService;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.StrUtil;

/**
 * <p>标题：</p>
 * <p>功能： 文件上传公共方法，保证文件，返回访问URL</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月25日 下午5:46:51</p>
 * <p>类全名：com.hibo.bas.fileplugin.service.impl.FileServiceImpl</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
@Service("fileServiceImpl")
public class FileServiceImpl implements FileService {

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;

	private String filedef = "DEFFILEPATH";
	
	
	@Override
	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		return isValid(fileType, multipartFile, filedef);
	}	
	
	@Override
	public boolean isValid(FileType fileType, MultipartFile multipartFile, String filedef) {

		 if (multipartFile == null) {
			 return false;
		 }
		 
		 int fileMaxSize = DataConfig.getIntConfig(filedef+".SIZE",2);
		 if (multipartFile.getSize() > fileMaxSize * 1024L * 1024L) {
			 return false;
		 }
		 String okfileType = DataConfig.getConfig(filedef+".TYPE");
		 String[] uploadExtensions = StrUtil.splitString(okfileType, ',');;

		 if (ArrayUtils.isNotEmpty(uploadExtensions)) {
			 return FilenameUtils.isExtension(multipartFile.getOriginalFilename(),uploadExtensions);
		 }
		 
		return true;
	}

	@Override
	public String upload(MultipartFile multipartFile) {
		return upload(multipartFile, filedef, false);
	}
	
	/**
	 * 按 MultipartFile 保存文件
	 */
	@Override
	public String upload(MultipartFile multipartFile, String filedef, boolean async) {
		if (multipartFile == null) {
			return null;
		}
		
		if (filedef == null)
			filedef = this.filedef;
		
		String rootPath = DataConfig.getConfig("FS."+filedef);
		String url = DataConfig.getConfig(filedef+".VISIT");
		if ( url == null || rootPath == null || !(rootPath.startsWith("ftp:") || rootPath.startsWith("file:")))
			return null;
		
		try
		{
			String type= FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			String dir = FileSystem.getUuidFilePath(type);
			if (async){
				File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
				if (!tempFile.getParentFile().exists()) {
					tempFile.getParentFile().mkdirs();
				}
				multipartFile.transferTo(tempFile);
				addTask(rootPath, dir, tempFile);
			}else{
				FileSystem fs = FileSystem.getFileSystem(rootPath);
				try{
					fs.writeFile(dir, multipartFile.getInputStream());
				}finally{
					fs.close();
				}
			}
			return url + dir;
		} catch (IOException e1)
		{
			e1.printStackTrace();
			throw new RuntimeException("无法获取文件操作对象FileSystem，请检查Config相关配置", e1);
		}
	}
	
	@Override
	public String upload(InputStream inputStream,String suffix) {
		return upload(inputStream,this.filedef,suffix);
	}
	
	/**
	 * 按 InputStream 流保存文件
	 */
	@Override
	public String upload(InputStream inputStream, String filedef, String suffix) {
		if (inputStream == null) {
			return null;
		}
		if (filedef == null)
			filedef = this.filedef;
		
		String rootPath = DataConfig.getConfig("FS."+filedef);
		String url = DataConfig.getConfig(filedef+".VISIT");
		if ( url == null || rootPath == null || !(rootPath.startsWith("ftp:") || rootPath.startsWith("file:")))
			return null;
		
		try
		{	
			String fileNmae = new SimpleDateFormat("yyMMddHHmmsssss").format(new java.util.Date())+StrUtil.randomStr(4)+"."+suffix;
			String type= FilenameUtils.getExtension(fileNmae);
			String dir = FileSystem.getUuidFilePath(type);
				FileSystem fs = FileSystem.getFileSystem(rootPath);
				try{
					fs.writeFile(dir, inputStream);
				}finally{
					fs.close();
				}
			return url + dir;
		} catch (IOException e1)
		{
			e1.printStackTrace();
			throw new RuntimeException("无法获取文件操作对象FileSystem，请检查Config相关配置", e1);
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("文件保存异常！");
		}
	}
	
	@Override
	public String[] upload2(InputStream inputStream, String  sizeStr, String suffix) throws Exception{
		return upload2(inputStream, sizeStr, suffix, null);
	}
	
	@Override
	public String[] upload2(InputStream inputStream, String  sizeStr, String suffix, String logoText) throws Exception{
		return upload2(inputStream,  sizeStr, suffix , 2, logoText);
	}
	
	@Override
	public String[] upload2(InputStream inputStream, String  sizeStr, String suffix ,int model) throws Exception{
		return upload2(inputStream, sizeStr, suffix ,model, null);
	}
	
	/**
	 * 按InputStream文件，保存图片，可以处理图片大小，加文字水印
	 */
	@Override
	public String[] upload2(InputStream inputStream, String  sizeStr, String suffix ,int model, String logoText) throws Exception{
		String[] urlArr = new String[1];
//		inputStream.mark(0);
//		String url = upload(inputStream, filedef, suffix);
//		inputStream.reset();
		if (!StrUtil.isnull(sizeStr)){
			String[][] sizeStrArr = StrUtil.splitString(sizeStr, ',', '*');
			int len = sizeStrArr.length;
			urlArr = new String[len+1];
			String fileNmae = new SimpleDateFormat("yyMMddHHmmsssss").format(new java.util.Date())+StrUtil.randomStr(4)+"."+suffix;
			FilenameUtils.getExtension(fileNmae);
			for(int i = 0; i < len; i++){
				int destWidth = StrUtil.obj2int(sizeStrArr[i][0]);
				int destHeight = destWidth;
				if (sizeStrArr[i].length > 1)
					destHeight = StrUtil.obj2int(sizeStrArr[i][1]);
				File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".jpg");
				try{
					com.hibo.bas.image.ImageUtils.zoom(inputStream, tempFile, destWidth, destHeight, model, logoText);
					urlArr[i+1] = upload(tempFile);
				} finally {
					FileUtils.deleteQuietly(tempFile);
				}
			}
		}
		inputStream.mark(0);
		String url = null;
		if (StrUtil.isnull2(logoText)){
			url = upload(inputStream, filedef, suffix);
		}else{
			File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".jpg");
			try{
				com.hibo.bas.image.ImageUtils.waterMarkByText(inputStream, tempFile, logoText);
				url = upload(tempFile);
			} finally {
				FileUtils.deleteQuietly(tempFile);
			}
		}
		//inputStream.reset();
		
		urlArr[0] = url;
		return urlArr;
		
	}
	
	@Override
	public String upload(File file) {
		return upload(file, filedef, false);
	}
	
	/**
	 * file  图片文件
	 * filedef  FTP文件保证地址的配置码
	 * async  是否异步保存，  true  异步
	 */
	@Override
	public String upload(File file, String filedef, boolean async) {
		if (file == null) {
			return null;
		}
		
		if (filedef == null)
			filedef = this.filedef;
		
		String rootPath = DataConfig.getConfig("FS."+filedef);
		String url = DataConfig.getConfig(filedef+".VISIT");
		if ( url == null || rootPath == null || !(rootPath.startsWith("ftp:") || rootPath.startsWith("file:")))
			return null;
		
		try
		{
			String type= FilenameUtils.getExtension(file.getName());
			String dir = FileSystem.getUuidFilePath(type);
			if (async){
				addTask(rootPath, dir, file);
			}else{
				FileSystem fs = FileSystem.getFileSystem(rootPath);
				try{
					fs.writeFile(dir, file);
				}finally{
					fs.close();
				}
			}
			return url + dir;
		} catch (IOException e1)
		{
			e1.printStackTrace();
			throw new RuntimeException("无法获取文件操作对象FileSystem，请检查Config相关配置", e1);
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("文件保存异常！");
		}
	}
	
	/**
	 * sizeStr 为微缩图的大小描述，下列描述正确   800    800*900   800,1000    800*900,1000*1000
	 * 没有指定高度时，默认宽、高一样。
	 * 返回值 ， [0] 为原图， [1][2] 为大小描述的顺序
	 */
	
	@Override
	public String[] upload(MultipartFile multipartFile, String sizeStr){
		return upload(multipartFile, sizeStr,0, null);
	}
	
	@Override
	public String[] upload(MultipartFile multipartFile, String sizeStr, String logoText){
		return upload(multipartFile, sizeStr,0, logoText);
	}
	
	@Override
	public String[] upload(MultipartFile multipartFile, String sizeStr,int model){
		return upload(multipartFile, filedef, sizeStr, false,model, null);
	}
	
	@Override
	public String[] upload(MultipartFile multipartFile, String sizeStr,int model, String logoText){
		return upload(multipartFile, filedef, sizeStr, false,model, logoText);
	}
	
	/**
	 * 按MultipartFile文件，保存图片，可以处理图片大小，加文字水印
	 */
	@Override
	public String[] upload(MultipartFile multipartFile, String filedef, String sizeStr, boolean async,int model, String logoText){
		String type= FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		if (StrUtil.isnull(type))
			type = "";
		else
			type = "."+type;
		File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + type);
		try
		{
			if (!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdirs();
			}
			multipartFile.transferTo(tempFile);
			return upload(tempFile, filedef, sizeStr, async,model, logoText);
		} catch (IOException e1)
		{
			throw new RuntimeException("无法获取文件操作对象FileSystem，请检查Config相关配置", e1);
		} finally {
			if (!async)
				FileUtils.deleteQuietly(tempFile);
		}
	}
	
	/**
	 * sizeStr 为微缩图的大小描述，下列描述正确   800    800*900   800,1000    800*900,1000*1000
	 * 没有指定高度时，默认宽、高一样。
	 * 返回值 ， [0] 为原图， [1][2] 为大小描述的顺序
	 */
	
	@Override
	public String[] upload(File file, String sizeStr,int model){
		return upload(file, filedef, sizeStr, false,model, null);
	}
	
	@Override
	public String[] upload(File file, String sizeStr,int model, String logoText){
		return upload(file, filedef, sizeStr, false,model, logoText);
	}
	
	/**
	 * 按File文件，保存图片，可以处理图片大小，加文字水印
	 */
	@Override
	public String[] upload(File file, String filedef, String sizeStr, boolean async,int model, String logoText){
		String[] urlArr = new String[1];
		//String url = upload(file, filedef, async);
		if (!StrUtil.isnull(sizeStr)){
			String[][] sizeStrArr = StrUtil.splitString(sizeStr, ',', '*');
			int len = sizeStrArr.length;
			urlArr = new String[len+1];
			String type= FilenameUtils.getExtension(file.getName());
			for(int i = 0; i < len; i++){
				int destWidth = StrUtil.obj2int(sizeStrArr[i][0]);
				int destHeight = destWidth;
				if (sizeStrArr[i].length > 1)
					destHeight = StrUtil.obj2int(sizeStrArr[i][1]);
				File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".jpg");
				try{
					com.hibo.bas.image.ImageUtils.zoom(file, tempFile, destWidth, destHeight,model, logoText);
					urlArr[i+1] = upload(tempFile, filedef, async);
				} finally {
					if (!async)
						FileUtils.deleteQuietly(tempFile);
				}
			}
		}
		String url = null;
		if (StrUtil.isnull2(logoText)){
			url = upload(file, filedef, async);
		}else{
			File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".jpg");
			try{
				com.hibo.bas.image.ImageUtils.waterMarkByText(file,tempFile,logoText);
				url = upload(tempFile, filedef, async);
			} finally {
				FileUtils.deleteQuietly(tempFile);
			}
		}
		urlArr[0] = url;
		return urlArr;
	}
	
	private void addTask(final String rootPath, final String dir, final File tempFile) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					FileSystem fs = FileSystem.getFileSystem(rootPath);
					try {
						fs.writeFile(dir, tempFile);
					} finally {
						try {
							fs.close();
						} catch (Exception ex) {

						}
						FileUtils.deleteQuietly(tempFile);
					}
				} catch (IOException e1) {
					
				}
			}
		});
	}
	
	@Override
	public List<FileInfo> browser(String path, FileType fileType, OrderType orderType, String filedef) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		return fileInfos;
	}
	
	@Override
	public List<FileInfo> browser(String path, FileType fileType, OrderType orderType) {
		/*
		List<String> uploadType = getUploadType();
		if (path != null) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (!path.endsWith("/")) {
				path += "/";
			}
		} else {
			path = "/";
		}
		String uploadPath = "";
		if (uploadType.contains("FILE")) {
			uploadPath = DataConfig.getConfig("IMGFILEPATH");
		} else if (uploadType.contains("FTP")) {
			uploadPath = DataConfig.getConfig("IMGFTPATH");
		}
		String browsePath = StringUtils.substringBefore(uploadPath, "${");
		browsePath = StringUtils.substringBeforeLast(browsePath, "/") + path;

		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		if (browsePath.indexOf("..") >= 0) {
			return fileInfos;
		}
		for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
			if (uploadType.contains("FTP") && storagePlugin instanceof FtpPlugin) {
				fileInfos = storagePlugin.browser(browsePath);
			}
			if (uploadType.contains("FILE") && storagePlugin instanceof FilePlugin) {
				fileInfos = storagePlugin.browser(browsePath);
			}
		}
		if (orderType == OrderType.size) {
			Collections.sort(fileInfos, new SizeComparator());
		} else if (orderType == OrderType.type) {
			Collections.sort(fileInfos, new TypeComparator());
		} else {
			Collections.sort(fileInfos, new NameComparator());
		}
		*/
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		return fileInfos;
	}
	
	/*
	private class NameComparator implements Comparator<FileInfo> {
		@Override
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory())
					.append(fileInfos1.getName(), fileInfos2.getName()).toComparison();
		}
	}

	private class SizeComparator implements Comparator<FileInfo> {
		@Override
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory())
					.append(fileInfos1.getSize(), fileInfos2.getSize()).toComparison();
		}
	}

	private class TypeComparator implements Comparator<FileInfo> {
		@Override
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory())
					.append(FilenameUtils.getExtension(fileInfos1.getName()),
							FilenameUtils.getExtension(fileInfos2.getName()))
					.toComparison();
		}
	}
	*/
	
}
