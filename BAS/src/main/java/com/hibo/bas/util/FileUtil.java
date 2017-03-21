package com.hibo.bas.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午6:13:36</p>
 * <p>类全名：com.hibo.bas.util.FileUtil</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class FileUtil {
	private FileUtil() {
	}

	static public final boolean makeDir(String path, boolean forFile) {
		return makeDir(new File(path), forFile);
	}

	static public final boolean makeDir(File f, boolean forFile) {
		if (!forFile) {
			if (f.exists()) {
				return f.isDirectory();
			}
			return f.mkdirs();
		}
		return makeDir(f.getParentFile(), false);
	}

	static public final boolean makeDir(String dir) {
		return makeDir(dir, false);
	}

	static public final void removeEmptyDir(String path, boolean forFile) {
		File f = new File(path);
		if (forFile) {
			f = f.getParentFile();
		}
		for (;; f = f.getParentFile()) {
			if (f.isFile())
				break;
			String list[] = f.isDirectory() ? f.list() : null;
			if (list != null && list.length > 0)
				break;
			f.delete();
		}
	}

	static public void deleteDir(File file) {
		if (file == null)
			return;
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File a[] = file.listFiles();
			if (a != null)
				for (int i = 0; i < a.length; i++) {
					deleteDir(a[i]);
				}
			file.delete();
		}
	}

	static public String toUserHomeFile(String fileName, boolean mkdir) {
		String path = System.getProperty("user.home") + File.separatorChar
				+ fileName;
		if (mkdir)
			FileUtil.makeDir(path, true);
		return new File(path).getAbsolutePath();
	}

	/*
	 * filename = FileUtil.addSuffix(filename,"txt");
	 */
	static public String addSuffix(String fileName, String suffix) {
		if (fileName == null)
			return null;
		int n = fileName.length();
		for (int i = n - 1; i >= 0; i--) {
			char c = fileName.charAt(i);
			if (c == '.')
				return fileName;
			if (c == '/' || c == '\\')
				break;
		}
		return fileName + "." + suffix;
	}

	public static final String invalidChars = "\\/:*?\"<>|\n\r\t";

	static public boolean isValidFileName(String fn) {
		if (fn == null || fn.length() == 0)
			return false;
		for (int i = 0; i < fn.length(); i++) {
			// char c = fn.charAt(i);
			if (invalidChars.indexOf(fn.charAt(i)) >= 0)
				return false;
		}
		return true;
		// for()
	}

	static public String replaceInvalidFnChars(String fileName, char to) {
		return replaceInvalidFnChars(fileName, to, false);
	}

	static public String replaceInvalidFnChars(String fileName, char to,
			boolean replaceNonGBK) {
		if (fileName == null)
			return null;
		StringBuilder sb = new StringBuilder();
		int nReplaced = 0;
		for (int i = 0; i < fileName.length(); i++) {
			char c = fileName.charAt(i);
			// System.out.println("c="+c+":0x"+Integer.toHexString(c));
			if (invalidChars.indexOf(c) >= 0) {
				c = to;
				nReplaced++;
			}
			sb.append(c);
		}
		return nReplaced > 0 ? sb.toString() : fileName;
	}

	static public String readTextFile(String fileName) throws IOException {
		return readTextFile(fileName, null);
	}

	static public String readTextFile(String fileName, String charSet)
			throws IOException {
		try (java.io.Reader r = charSet == null ? new java.io.FileReader(
				fileName) : new java.io.InputStreamReader(
				new java.io.FileInputStream(fileName), charSet);) {
			return DataStream.toString(r);
		}
	}

	static public String readTextFile(java.io.File file, String charSet)
			throws IOException {
		try (java.io.Reader r = charSet == null ? new java.io.FileReader(file)
				: new java.io.InputStreamReader(new java.io.FileInputStream(
						file), charSet);) {
			return DataStream.toString(r);
		}
	}

	static public byte[] readFile(String fileName) throws IOException {
		java.io.FileInputStream is = new java.io.FileInputStream(fileName);
		try {
			return DataStream.toByteArray(is);
		} finally {
			try {
				is.close();
			} catch (Throwable ex) {
			}
		}
	}

	static public byte[] readFile(File file) throws IOException {
		java.io.FileInputStream is = new java.io.FileInputStream(file);
		try {
			return DataStream.toByteArray(is);
		} finally {
			try {
				is.close();
			} catch (Throwable ex) {
			}
		}
	}

	static public void copyFile(File file, java.io.OutputStream os)
			throws IOException {
		try (final java.io.FileInputStream is = new java.io.FileInputStream(
				file);) {
			DataStream.copy(is, os);// toByteArray(is);
		}
	}

	static public void writeFile(String fileName, byte data[])
			throws IOException {
		java.io.FileOutputStream os = new java.io.FileOutputStream(fileName);
		try {
			if (data != null)
				os.write(data);
		} finally {
			try {
				os.close();
			} catch (Throwable ex) {
			}
		}
	}

	static public void writeFile(java.io.File file, byte data[])
			throws IOException {
		java.io.FileOutputStream os = new java.io.FileOutputStream(file);
		try {
			if (data != null)
				os.write(data);
		} finally {
			try {
				os.close();
			} catch (Throwable ex) {
			}
		}
	}

	static public void writeFile(String fileName, java.io.InputStream is)
			throws IOException {
		java.io.FileOutputStream os = new java.io.FileOutputStream(fileName);
		try {
			DataStream.copy(is, os);
		} finally {
			try {
				os.close();
			} catch (Throwable ex) {
			}
		}
	}

	static public void writeFile(java.io.File file, java.io.InputStream is)
			throws IOException {
		java.io.FileOutputStream os = new java.io.FileOutputStream(file);
		try {
			DataStream.copy(is, os);
		} finally {
			try {
				os.close();
			} catch (Throwable ex) {
			}
		}
	}

	static public java.io.File createTempFile(String fileName,
			boolean deleteOnExit) throws IOException {
		if (fileName == null)
			return null;
		fileName = fileName.replace('\\', '/');
		int p = fileName.lastIndexOf('/');
		if (p >= 0)
			fileName = fileName.substring(p + 1);
		p = fileName.lastIndexOf('.');
		String suffix = p >= 0 ? fileName.substring(p) : "";
		String prefix = p >= 0 ? fileName.substring(0, p) : fileName;
		if (prefix.length() > 16)
			prefix = prefix.substring(0, 16);
		if (prefix.length() < 3)
			prefix = prefix + StrUtil.newString('_', 3 - prefix.length());
		File tmpFile = File.createTempFile(prefix, suffix);
		if (deleteOnExit)
			tmpFile.deleteOnExit();
		return tmpFile;
	}

	static public String copyFile(String toFileName, boolean createTemp,
			String fileName) throws IOException {
		String copyToFile;
		if (toFileName == null || createTemp) {
			File temp = createTempFile(toFileName == null ? fileName
					: toFileName, true);
			copyToFile = temp.getAbsolutePath();
		} else {
			copyToFile = toFileName;
		}
		java.io.FileInputStream is = null;
		try {
			writeFile(copyToFile, is = new java.io.FileInputStream(fileName));
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Throwable ex) {
				}
		}
		return copyToFile;
	}

	static public void copyGZipFile(java.io.File toFile, java.io.File fromFile)
			throws IOException {
		java.io.FileInputStream fs = new java.io.FileInputStream(fromFile);
		try {
			writeFile(toFile, new java.util.zip.GZIPInputStream(fs));
		} finally {
			if (fs != null)
				try {
					fs.close();
				} catch (Throwable ex) {
				}
		}
	}

	/*
	 * 从URL下成 文件 static public File downloadFile(String url,File file) throws
	 * IOException { HttpConnection con = new HttpConnection(url,"GET"); try {
	 * InputStream is = con.getInputStream(); if( file==null ) { String fileName
	 * = null; String contentDisposition =
	 * con.getHeaderField("Content-Disposition"); if( contentDisposition!=null )
	 * { String a[] = StrUtil.splitString(contentDisposition,';'); for(int
	 * j=0;j<a.length;j++) { if( a[j].startsWith("filename=") ) { fileName =
	 * a[j].substring(9); if( fileName.length()>2 && fileName.charAt(0)=='"' &&
	 * fileName.charAt(fileName.length()-1)=='"' ) { fileName =
	 * fileName.substring(1,fileName.length()-1); } if( fileName!=null &&
	 * fileName.startsWith("=?") ) //&& fileName.endsWith("?=") ) {
	 * //=?gb2312?B?0MXWvdKzw7yjqDU4OTkyMjQ5o6kyKDA1LTI1LTE2LTMwLTQ5KSgwNQ==?=
	 * =?gb2312?B?LTI1LTE3LTI2LTUxKSgwNS0yNS0xNy0zMS01NSkoMDUtMjUtMTctNA==?=
	 * =?gb2312?B?OC0yMSkoMDUtMjUtMTgtMzUtMjgpKDA1LTI1LTE4LTQzLTA3KSgwNQ==?=
	 * =?gb2312?B?LTI1LTE4LTU=?= // ?gb2312?B?sKGwoS50eHQ=?= fileName =
	 * HtmlUtil.mimeBase64Decode(fileName); } else { fileName = new
	 * String(fileName.getBytes("8859_1")); } break; } } } if( fileName==null )
	 * fileName = "Temp"; file = createTempFile(fileName,true); }
	 * java.io.FileOutputStream os = new FileOutputStream(file); try {
	 * DataStream.copy(is,os); } finally { os.close(); } return file; } finally
	 * { con.close(); } }
	 */
	static public boolean isTempFile(File file) {
		if (file == null)
			return false;
		String path = file.getAbsolutePath();
		return path.startsWith(new File(System.getProperty("java.io.tmpdir"))
				.getAbsolutePath());
	}

	static public boolean filenmLike(String fileName, String pattern) {
		return com.hibo.bas.file.FileSystem.like(fileName, pattern);
	}

	static public String joinPath(String path1, String path2) {
		if (path1 == null || path1.length() == 0)
			return path2;
		if (path2 == null || path2.length() == 0)
			return path1;
		path1 = path1.replace('\\', '/');
		path2 = path2.replace('\\', '/');
		if (!path1.endsWith("/"))
			path1 += "/";
		return path1 + path2;
	}
	
	private static Map<String,String> visitRoots = new HashMap<String,String>();
	
	public static Map<String,String> getAllVisitRoot()
	{
		if (visitRoots.size() <= 0){
			final Map<String,String> config = DataConfig.getServerConfig();
			final Iterator<String> keys = config.keySet().iterator();
	//		Map<String,String> visitroots = new HashMap<String,String>();
			while (keys.hasNext())
			{
				final String key = (String) keys.next();
				if (!key.endsWith(".VISIT"))
				{
					continue;
				}
				final String root = key.substring(0, key.indexOf(".VISIT"));
				if (config.get("FS." + root) != null && ((String) config.get("FS." + root)).length() > 0)
				{
					visitRoots.put(root, config.get(key));
				}
			}
		}
		
		return visitRoots;
	}
	
	/**
	 * 使用时调用 
	 * @param urlStr
	 * @return
	 */
	public static String replaceRootToUrl(String urlStr)
	{ 
		if(StrUtil.isnull(urlStr))
		{
			return urlStr;
		}
		Pattern ptn = Pattern.compile("\\$_atturl_\\$\\{([^\\}]+)\\}hbsoft_\\$");
		Matcher m = ptn.matcher(urlStr);
		while (m.find())
		{
			String rootName = m.group(1);
			String docUrl = DataConfig.getConfig(rootName+".VISIT");
			urlStr = urlStr.replace("$_atturl_${" + rootName + "}hbsoft_$", docUrl);
			m = ptn.matcher(urlStr);
		}
		return urlStr;
	}
	
	/**
	 * 保存时调用 
	 * @param urlStr
	 * @return
	 */
	public static String replaceUrlToRoot(String urlStr){
		if(StrUtil.isnull(urlStr))
		{
			return urlStr;
		}
		Map<String,String> visitroots = getAllVisitRoot();
		
		Iterator<String> vpRoots = visitroots.keySet().iterator();
		while (vpRoots.hasNext())
		{
			String rootName = StrUtil.obj2str(vpRoots.next());
			String url = StrUtil.obj2str(visitroots.get(rootName));
			if(urlStr.indexOf(url) >= 0)
			{
				urlStr = urlStr.replace(url, "$_atturl_${" + rootName + "}hbsoft_$");
			}
		}
		return urlStr;
	}
	
}
/*
 用文件头判断。直接读取文件的前几个字节。
常用文件的文件头如下：
JPEG (jpg)，文件头：FFD8FF
PNG (png)，文件头：89504E47
GIF (gif)，文件头：47494638
TIFF (tif)，文件头：49492A00 
Windows Bitmap (bmp)，文件头：424D
CAD (dwg)，文件头：41433130
Adobe Photoshop (psd)，文件头：38425053
Rich Text Format (rtf)，文件头：7B5C727466
XML (xml)，文件头：3C3F786D6C
HTML (html)，文件头：68746D6C3E
Email [thorough only] (eml)，文件头：44656C69766572792D646174653A
Outlook Express (dbx)，文件头：CFAD12FEC5FD746F 
Outlook (pst)，文件头：2142444E 
MS Word/Excel (xls.or.doc)，文件头：D0CF11E0
MS Access (mdb)，文件头：5374616E64617264204A
WordPerfect (wpd)，文件头：FF575043
Postscript. (eps.or.ps)，文件头：252150532D41646F6265
Adobe Acrobat (pdf)，文件头：255044462D312E
Quicken (qdf)，文件头：AC9EBD8F 
Windows Password (pwl)，文件头：E3828596 
ZIP Archive (zip)，文件头：504B0304 
RAR Archive (rar)，文件头：52617221 
Wave (wav)，文件头：57415645 
AVI (avi)，文件头：41564920 
Real Audio (ram)，文件头：2E7261FD 
Real Media (rm)，文件头：2E524D46 
MPEG (mpg)，文件头：000001BA 
MPEG (mpg)，文件头：000001B3
Quicktime (mov)，文件头：6D6F6F76 
Windows Media (asf)，文件头：3026B2758E66CF11 
MIDI (mid)，文件头：4D546864
 
*/
