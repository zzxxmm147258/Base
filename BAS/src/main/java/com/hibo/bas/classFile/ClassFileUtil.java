package com.hibo.bas.classFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.bas.http.HttpRequester;
import com.hibo.bas.http.HttpRequester.HttpRespons;
import com.hibo.bas.spring.SpringWebUtil;
import com.hibo.bas.util.StrUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月12日 上午10:19:46</p>
 * <p>类全名：com.hibo.bas.classFile.ClassFileUtil</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class ClassFileUtil {
	private static final ClassLoader classLoader = ClassFileUtil.class.getClassLoader();
	private static final Logger log = LoggerFactory.getLogger(ClassFileUtil.class);
	private static String classFindPath = File.separator + "hibo";
	static{
		// 通过类路径获取
		String reaPpath = ClassFileUtil.getFileRealPath(File.separator);
		int p = reaPpath.lastIndexOf("WEB-INF");
		if (p > 0) {
			reaPpath = reaPpath.substring(0, p - 1);
			int l = reaPpath.lastIndexOf(File.separator);
			classFindPath = reaPpath.substring(l);
		}
	}
	/**
	 * 部分路径地址以类根路径开始
	 * @param path=hibo/ipaddr/ipAddr.dat
	 * @return
	 */
	public static String getFileRealPath(String path) {
		return classLoader.getResource(path).getPath();
	}

	/**
	 * @功能:获取工程目录
	 * @作者:周雷
	 * @时间:2015年11月28日 下午3:28:05
	 * @return
	 */
	public static String getContextPath() {
		// 通过servletContext获取
		String contextPath = SpringWebUtil.getContextPath();
		// 判断是否获取到ervletContext的工程路径
		if (null == contextPath) {
			contextPath = classFindPath;
		}
		return contextPath;
	}

	/**
	 * 打开本地或网路的文件返回输入流
	 * @param path 文件路径
	 * @return InputStream
	 * @throws FileNotFoundException
	 */
	public static InputStream getInputStream01(String path) throws Exception {
		InputStream is = null;
		if (!StrUtil.isnull(path)) {
			boolean isUrl = path.startsWith("https:") || path.startsWith("http:") || path.startsWith("www.");
			if (isUrl) {
				HttpRequester httpRequester = new HttpRequester();
				HttpRespons rs = httpRequester.sendGet(path);
				is = new ByteArrayInputStream(rs.getBytes());
			} else {
				File file = new File(path);
				if(file.exists()){
					is = new FileInputStream(file);
				}else{
					throw new FileNotFoundException("未找到文件:" + path);
				}
			}
		} else {
			throw new FileNotFoundException("文件地址不能为空");
		}
		return is;
	}
	
	/**
	 * 打开类路径下的文件返回输入流
	 * @param path 文件路径
	 * @return InputStream
	 * @throws FileNotFoundException
	 */
	public static InputStream getInputStream(String path) throws Exception {
		InputStream is = null;
		if (!StrUtil.isnull(path)) {
			boolean isUrl = path.startsWith("https:") || path.startsWith("http:") || path.startsWith("www.");
			if (isUrl) {
				HttpRequester httpRequester = new HttpRequester();
				HttpRespons rs = httpRequester.sendGet(path);
				is = new ByteArrayInputStream(rs.getBytes());
			} else {
				is = classLoader.getResourceAsStream(path);
			}
			if (null == is) {
				throw new FileNotFoundException("未找到文件:" + path);
			}
		} else {
			throw new FileNotFoundException("文件地址不能为空");
		}
		return is;
	}
	
	/**
	 * 打开本地或网路的文件返回字节
	 * @param path 文件路径
	 * @return InputStream
	 * @throws FileNotFoundException
	 */
	public static byte[] getBytes1(String path) throws Exception {
		byte[] bytes = null;
		if (!StrUtil.isnull(path)) {
			boolean isUrl = path.startsWith("https:") || path.startsWith("http:") || path.startsWith("www.");
			if (isUrl) {
				HttpRequester httpRequester = new HttpRequester();
				HttpRespons rs = httpRequester.sendGet(path);
				bytes = rs.getBytes();
			} else {
				File file = new File(path);
				if(file.exists()){
					FileInputStream is = new FileInputStream(file);
					bytes = new byte[is.available()];
					is.read(bytes);
					if(null!=is){
						is.close();
					}
				}else{
					throw new FileNotFoundException("未找到文件:" + path);
				}
				
			}
			if (null == bytes) {
				throw new FileNotFoundException("未找到文件:" + path);
			}
		} else {
			throw new FileNotFoundException("文件地址不能为空");
		}
		return bytes;
	}

	/**
	 * 打开类路径下的文件返回字节
	 * @param path 文件路径
	 * @return InputStream
	 * @throws FileNotFoundException
	 */
	public static byte[] getBytes(String path) throws Exception {
		byte[] bytes = null;
		if (!StrUtil.isnull(path)) {
			boolean isUrl = path.startsWith("https:") || path.startsWith("http:") || path.startsWith("www.");
			if (isUrl) {
				HttpRequester httpRequester = new HttpRequester();
				HttpRespons rs = httpRequester.sendGet(path);
				bytes = rs.getBytes();
			} else {
				InputStream is = classLoader.getResourceAsStream(path);
				bytes = new byte[is.available()];
				is.read(bytes);
				if(null!=is){
					is.close();
				}
			}
			if (null == bytes) {
				throw new FileNotFoundException("未找到文件:" + path);
			}
		} else {
			throw new FileNotFoundException("文件地址不能为空");
		}
		return bytes;
	}
	
	
	/**
	 * 打开类路径下的文件返回UTF-8字符串
	 * @param path 文件路径
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String getFileToStr(String path) throws Exception {
		return getFileToStr(path, "utf8");
	}

	/**
	 * 打开类路径下的文件返回字符串
	 * 
	 * @param path
	 *            文件路径
	 * @param charset
	 *            字符集
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String getFileToStr(String path, String charset) throws Exception {
		if (log.isInfoEnabled()) {
			log.info("查找文件" + path);
		}
		InputStream is = ClassFileUtil.getInputStream(path);
		StringBuffer buffer = new StringBuffer();
		byte[] bytes = new byte[1024];
		int len = -1;
		while ((len = is.read(bytes)) != -1) {
			is.read(bytes, 0, len);
			buffer.append(new String(bytes, charset));
		}
		if (log.isInfoEnabled()) {
			log.info("找到文件" + path);
		}
		return buffer.toString();
	}

	/**
	 * 获取某个路径下以startWith开头的所有文件夹
	 * 
	 * @param path
	 * @param startWith
	 * @return
	 */
	public static List<File> getFolderStartWith(String path, String StartWith) {
		return getFolderList(path, "*" + StartWith);
	}

	/**
	 * 获取某个路径下以endWith结尾的所有文件夹
	 * 
	 * @param path
	 * @param startWith
	 * @return
	 */
	public static List<File> getFolderEndWith(String path, String EndWith) {
		return getFolderList(path, EndWith + "*");
	}

	/**
	 * 获取某个路径下的所有文件夹
	 * 
	 * @param path
	 * @param startWith
	 *            以XXX开头或结尾
	 * @return
	 */
	public static List<File> getFolderList(String path, String prefix) {
		List<List<File>> list = getFolderAndFile(path, prefix, "dir");
		return null == list ? null : list.get(0);
	}

	/**
	 * 获取某个路径下以startWith开头的所有文件
	 * 
	 * @param path
	 * @param startWith
	 * @return
	 */
	public static List<File> getFileStartWith(String path, String StartWith) {
		return getFileList(path, StartWith + "*");
	}

	/**
	 * 获取某个路径下以endWith结尾的所有文件
	 * 
	 * @param path
	 * @param startWith
	 * @return
	 */
	public static List<File> getFileEndWith(String path, String EndWith) {
		return getFileList(path, "*" + EndWith);
	}

	/**
	 * 获取某个路径下的所有文件
	 * 
	 * @param path
	 * @param startWith
	 *            以XXX开头或结尾
	 * @return
	 */
	public static List<File> getFileList(String path, String prefix) {
		List<List<File>> list = getFolderAndFile(path, prefix, "file");
		return null == list ? null : list.get(0);
	}

	public static File getFile(String path) {
		if (log.isInfoEnabled()) {
			log.info("查找文件" + path);
		}
		String urlPath = getFileRealPath(path);
		File file = new File(urlPath);
		if (!file.exists()) {
			return null;
		}
		if (log.isInfoEnabled()) {
			log.info("找到文件" + file.getName());
		}
		return file;
	}

	/**
	 * 获取某个路径下的所有文件
	 * 
	 * @param path
	 * @param type
	 *            类型 null为所有类型
	 * @return
	 */
	public static List<List<File>> getFolderAndFile(String path, String prefix, String type) {
		if (log.isInfoEnabled()) {
			log.info("查找到文件" + path);
		}
		String urlPath = getFileRealPath(path);
		File file = new File(urlPath);
		List<List<File>> filelists = null;
		if (!file.exists()) {
			return filelists;
		}
		if (log.isInfoEnabled()) {
			log.info("找到文件" + file.getName());
		}
		if (file.isDirectory()) {
			boolean isfile = "file".equals(type) || null == type;
			boolean isDirectory = "dir".equals(type) || null == type;
			File[] files = file.listFiles();
			filelists = new ArrayList<List<File>>();
			if (null != files && files.length > 0) {
				List<File> fileDirectory = new ArrayList<File>();
				List<File> filelist = new ArrayList<File>();
				for (File file2 : files) {
					String name = file2.getName();
					boolean isPrefix = false;
					if (null != prefix) {
						if (prefix.startsWith("*")) {
							String eprefix = prefix.substring(1);
							isPrefix = name.endsWith(eprefix);
						} else if (prefix.endsWith("*")) {
							String sprefix = prefix.substring(0, prefix.length() - 1);
							isPrefix = name.startsWith(sprefix);
						} else {
							isPrefix = name.equals(prefix);
						}
					}
					if (file2.isDirectory() && isPrefix && isDirectory) {
						fileDirectory.add(file2);
						filelists.add(fileDirectory);
					} else if (file2.isFile() && isPrefix && isfile) {
						filelist.add(file2);
						filelists.add(filelist);
					}
				}
			}
			if (filelists.isEmpty()) {
				filelists = null;
			}
		}
		return filelists;
	}
//	public static void main(String[] args) {
//		System.err.println(ClassFileUtil.getFileRealPath(""));
//	}
}
