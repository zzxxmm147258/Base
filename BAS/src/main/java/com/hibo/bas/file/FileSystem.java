package com.hibo.bas.file;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import com.hibo.bas.util.Const;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.DataStream;
import com.hibo.bas.util.FileUtil;
import com.hibo.bas.util.ObjectId;
import com.hibo.bas.util.StrUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午6:02:52</p>
 * <p>类全名：com.hibo.bas.file.FileSystem</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public abstract class FileSystem implements Closeable {
	abstract public String toURL();

	abstract public File[] listFiles(String path, boolean subdir)
			throws java.io.IOException;

	abstract public boolean deleteFile(String path, boolean deleteNonEmptyDir)
			throws java.io.IOException;

	abstract public boolean mkdir(String path) throws IOException;

	abstract public java.io.InputStream openFile(String fileName)
			throws java.io.IOException;

	abstract public java.io.InputStream openFile(File file)
			throws java.io.IOException;

	abstract public void setFileTime(String fileName, long time)
			throws java.io.IOException;

	abstract public void writeFile(String fileName, java.io.InputStream data,
			long time) throws java.io.IOException;

	abstract public java.io.OutputStream openWrite(String fileName, boolean gzip)
			throws java.io.IOException;

	/**
	 * @param fileName
	 * @param data
	 * @param calcMD5
	 * @param time
	 * @return
	 * @throws java.io.IOException
	 */
	abstract public String writeFile(String fileName, java.io.InputStream data,
			boolean calcMD5, long time) throws java.io.IOException;

	/*
	 * 文件不存在时，返回空
	 */
	abstract public File getFile(String fileName);

	abstract public void setReadonly(File file, boolean readOnly);

	abstract public boolean isReadonly(File file);

	abstract public boolean renameTo(File file, File toFile);

	final public boolean deleteFile(File file, boolean deleteNonEmptyDir)
			throws java.io.IOException {
		return deleteFile(file.path, deleteNonEmptyDir);
	}

	public boolean deleteDir(String fileName) throws IOException {
		return deleteFile(fileName, true);
	}

	// abstract public void writeFile(String fileName,java.io.InputStream data)
	// throws java.io.IOException;
	final public void writeFile(String fileName, java.io.InputStream data)
			throws java.io.IOException {
		writeFile(fileName, data, 0);
	}

	final public void writeFile(File file, java.io.InputStream data)
			throws java.io.IOException {
		writeFile(file.path, data, 0);
	}

	final public void writeFile(String fileName, java.io.File file)
			throws java.io.IOException {
		try (final java.io.FileInputStream is = new java.io.FileInputStream(
				file);) {
			writeFile(fileName, is, 0);
		}
	}

	// {
	// writeFile(fileName,data);
	// if( time>0 )
	// setFileTime(fileName,time);
	// }

	public void writeFile(String fileName, byte[] data, long time)
			throws IOException {
		writeFile(fileName, new java.io.ByteArrayInputStream(
				data == null ? new byte[0] : data), time);
	}

	public void writeFile(String fileName, byte[] data) throws IOException {
		writeFile(fileName, data, 0);
	}

	// @Override
	public void writeFile(String fileName, java.io.InputStream data, long time,
			boolean gzip) throws java.io.IOException {
		writeFile(fileName, data, 0, gzip);
	}

	public void copyFile(File fromFile, File toFile) throws IOException {
		final java.io.InputStream is = openFile(fromFile);
		try {
			writeFile(toFile, is);
		} finally {
			is.close();
		}
	}

	public int copyFiles(String toPath, FileSystem fs, String srcPath,
			String includes[][], String excludes[][])
			throws java.io.IOException {
		if (srcPath == null || "/".equals(srcPath))
			srcPath = "";
		final File[] files = fs.listFiles(srcPath, true);
		if (files == null || files.length == 0) {
			return 0;
		}
		// [end add]
		for (final File file : files) {
			if (file.isDir())
				continue;
			String path = file.path;

			if (srcPath != null && srcPath.length() > 0)
				path = path.substring(srcPath.length() + 1);

			if (includes != null && !likeOneOf(path, includes)) {
				continue;
			}
			if (excludes != null && likeOneOf(path, excludes)) {
				continue;
			}

			try (final java.io.InputStream is = fs.openFile(file);) {
				writeFile(
						toPath == null || "/".equals(toPath)
								|| toPath.length() == 0 ? path : toPath + "/"
								+ path, is);
			}
		}
		return files.length;
	}

	public int copyFiles(String toPath, FileSystem fs, String srcPath,
			String includes, String excludes) throws java.io.IOException {
		return copyFiles(toPath, fs, srcPath,
				StrUtil.splitString(includes, ',', '/'),
				StrUtil.splitString(excludes, ',', '/'));
	}

	static public String createTempFileName(String path, String fileName) {
		if (fileName == null)
			fileName = "Tmp";
		fileName = fileName.replace('\\', '/');
		int p = fileName.lastIndexOf('/');
		if (p >= 0)
			fileName = fileName.substring(p + 1);
		p = fileName.lastIndexOf('.');
		String prefix = p >= 0 ? fileName.substring(0, p) : fileName;
		String suffix = p >= 0 ? fileName.substring(p) : "";
		return path + "/" + prefix + "~" + System.currentTimeMillis() + "~"
				+ Const.random.nextInt() + suffix;
	}

	@Override
	public void close() {
	}

	@Override
	public void finalize() throws Throwable {
		close();
		super.finalize();
	}

	public void setFileTime(String fileName, java.util.Date time)
			throws IOException {
		setFileTime(fileName, time.getTime());
	}

	public static boolean like(String fileName, String a2[]) {
		String a1[] = StrUtil.splitString(fileName.replace('\\', '/'), '/');
		return like(a1, 0, a2, 0);
	}

	public static boolean likeOneOf(String fileName, String a2[][]) {
		if (a2 == null)
			return false;
		String a1[] = StrUtil.splitString(fileName.replace('\\', '/'), '/');
		for (int i = 0; i < a2.length; i++) {
			if (FileSystem.like(a1, 0, a2[i], 0))
				return true;
		}
		return false;
	}

	public static boolean like(String text[], int oText, String pattern[],
			int oPattern) {
		int ltext = text.length - oText;
		int lpattern = pattern.length - oPattern;
		for (int i = 0; i < lpattern; i++) {
			String cc = pattern[i + oPattern];
			if (cc.equals("**")) {
				if (i == lpattern - 1)
					return true;
				for (int iText = oText; iText < ltext + oText; iText++)
					if (like(text, iText, pattern, oPattern + i + 1))
						return true;
				return false;
			}
			if (i >= ltext)
				return false;
			// if( countChar(text[oText+i],'.')!=countChar(cc,'.') )
			// return false;
			if (!like(text[oText + i], 0, cc, 0))
				return false;
		}
		return ltext == lpattern;
	}

	public static boolean like(String fileName, String pattern) {
		// fileName = fileName.replace('\\','/');
		String a1[] = StrUtil.splitString(fileName.replace('\\', '/'), '/');
		String a2[] = StrUtil.splitString(pattern.replace('\\', '/'), '/');
		return like(a1, 0, a2, 0);
	}

	public static boolean like(String text, int oText, String pattern,
			int oPattern) // ,boolean ignoreCase
	{
		if (text == null)
			return pattern == null; // 2002-11-08
		int ltext = text.length() - oText;
		int lpattern = pattern.length() - oPattern;
		for (int i = 0; i < lpattern; i++) {
			char c = pattern.charAt(i + oPattern);
			if (c == '*') {
				if (i == lpattern - 1)
					return true;
				for (int iText = oText; iText < ltext + oText; iText++) {
					if (like(text, iText, pattern, oPattern + i + 1)) // ,ignoreCase
						return true;
				}
				return false;
			}
			if (i >= ltext)
				return false;
			if (c == '?') {
				continue;
			}
			{
				if (c != text.charAt(oText + i))
					return false;
			}
		}
		return ltext == lpattern
				|| (lpattern >= ltext + 1 && endsWithStarsPattern(pattern,
						ltext));
	}

	final static private boolean endsWithStarsPattern(String text, int from) {
		if (from < 0)
			from = 0;
		for (; from < text.length(); from++) {
			char c = text.charAt(from);
			if (c != '*')
				return false;
		}
		return true;
	}

	/*
	 * fileName : FileSystem 中的文件名 java.io.File file ： 本地文件
	 */
	public java.io.File downloadFile(final String fileName, java.io.File file,
			boolean caseGzip) throws java.io.IOException {
		boolean unzip = false;
		int p = fileName.lastIndexOf('/');
		String name = p < 0 ? fileName : fileName.substring(p + 1);
		if (caseGzip && (name.endsWith(".~gzip") || name.endsWith(".$gzip"))) {
			name = name.substring(0, name.length() - 6);
			unzip = true;
		}
		if (file == null) {
			if (name == null)
				name = "Temp";
			file = FileUtil.createTempFile(name, true);
		}
		java.io.FileOutputStream os = null;
		java.io.InputStream is = null;// openFile(fileName);
		try {
			os = new java.io.FileOutputStream(file);
			is = openFile(fileName);
			if (unzip)
				DataStream.copy(new java.util.zip.GZIPInputStream(is), os);
			else
				DataStream.copy(is, os);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Throwable ex) {
				}
			if (os != null)
				try {
					os.close();
				} catch (Throwable ex) {
				}
		}
		return file;
	}

	/*
	 * fileName = "path1/path2/file.txt"
	 */
	final public byte[] readFile(final String fileName)
			throws java.io.IOException {
		java.io.InputStream is = openFile(fileName);
		try {
			return DataStream.toByteArray(is);
		} finally {
			if (is != null)
				is.close();
		}
	}

	final public byte[] readFile(final File file) throws java.io.IOException {
		java.io.InputStream is = openFile(file);
		try {
			return DataStream.toByteArray(is);
		} finally {
			if (is != null)
				is.close();
		}
	}

	public java.io.InputStream openZFile(final String fileName, boolean caseGzip)
			throws java.io.IOException {
		boolean unzip = false;
		int p = fileName.lastIndexOf('/');
		String name = p < 0 ? fileName : fileName.substring(p + 1);
		if (caseGzip && (name.endsWith(".~gzip") || name.endsWith(".$gzip"))) {
			name = name.substring(0, name.length() - 6);
			unzip = true;
		}
		java.io.InputStream is = openFile(fileName);
		return unzip ? new java.util.zip.GZIPInputStream(is) : is;
	}

	final public boolean renameTo(String path, String toPath) {
		return renameTo(new File(path, false, 0, 0), new File(toPath, false, 0,
				0));
	}

	static public FileSystem getFileSystem(String url)
			throws java.io.IOException {
		if (url == null) {
			return null;
		}
		if (url.startsWith("file:"))
			return new LocalFileSystem(url);
		else if (url.startsWith("ftp:") || url.startsWith("sftp:"))
			return new FtpFileSystem(url);
		throw new IllegalArgumentException(url);
	}

	public InputStream zopenFile(String fileName) throws IOException {
		throw new java.lang.UnsupportedOperationException();
	}

	public InputStream zopenFile(File file) throws IOException {
		return zopenFile(file.path);
	}

	/*
	 * 文件系统中的文件 压缩方式 保存. 这种情况下， File 中的 size 为 压缩后的文件大小
	 */
	protected boolean gziped;

	public void setGZipped(boolean gziped) {
		this.gziped = gziped;
	}

	// @Override
	public boolean isGZipped() {
		return gziped;
	}

	static public String pathNormalize(String path) {
		if (path == null || path.length() == 0)
			return path;
		path = path.replace('\\', '/');
		String pathA[] = StrUtil.splitString(path, '/');
		java.util.List<String> list = new java.util.ArrayList<String>();
		for (int i = 0; i < pathA.length; i++) {
			if (pathA[i].length() == 0 || pathA[i].equals("."))
				continue;
			if (pathA[i].equals("..")) {
				if (list.size() == 0)
					throw new java.lang.IllegalArgumentException("无效文件路径:"
							+ path);
				list.remove(list.size() - 1);
			} else {
				list.add(pathA[i]);
			}
		}
		if (list.size() == 0)
			return null;
		return StrUtil.objcat(list.toArray(), '/');
	}

	static private String removePathEndSlash(String path) {
		if (path != null && path.length() > 1 && path.endsWith("/"))
			return path.substring(0, path.length() - 1);
		return path;
	}

	protected static String buildFullPath(String root, String path) {
		pathNormalize(path); // 检查文件 有效
		// String fpath;
		if (root == null || root.equals("/")) {
			return path == null ? "/" : removePathEndSlash("/" + path);
		}
		if (path == null || path.length() == 0)
			return removePathEndSlash(root);
		if (root.endsWith("/")) {
			return removePathEndSlash(root + path);
		}
		return removePathEndSlash(path.startsWith("/") ? root + path : root
				+ "/" + path);
	}

	/**
	 * 保存文件并返回访问的URL
	 * @param filedef 保存路径类型
	 * @param data   文件byte[]
	 * @param type   文件类型
	 * @return
	 */
	public static String getSaveFileUrl(String filedef, byte[] data, String type){
		if (StrUtil.isnull(filedef))
			filedef = "DEFFILEPATH";
		String rootPath = DataConfig.getConfig("FS."+filedef);
		String url = DataConfig.getConfig(filedef+".VISIT");
		String dir = getUuidFilePath(type);
		try {
			FileSystem fs = FileSystem.getFileSystem(rootPath);
			try {
				fs.writeFile(dir, data);
			} finally {
				fs.close();
			}
			return url + dir;
		} catch (IOException e1) {
			throw new RuntimeException("无法获取文件操作对象FileSystem，请检查Config相关配置", e1);
		}
	}
	
	/**
	 * 保存文件并返回访问的URL
	 * @param filedef 保存路径类型
	 * @param data   文件IO流
	 * @param type   文件类型
	 * @return
	 */
	public static String getSaveFileUrl(String filedef, java.io.InputStream data, String type){
		if (StrUtil.isnull(filedef))
			filedef = "DEFFILEPATH";
		String rootPath = DataConfig.getConfig("FS."+filedef);
		String url = DataConfig.getConfig(filedef+".VISIT");
		String dir = getUuidFilePath(type);
		try {
			FileSystem fs = FileSystem.getFileSystem(rootPath);
			try {
				fs.writeFile(dir, data);
			} finally {
				fs.close();
			}
			return url + dir;
		} catch (IOException e1) {
			throw new RuntimeException("无法获取文件操作对象FileSystem，请检查Config相关配置", e1);
		}
	}
	
	public static String getUuidFilePath(String type){
		if (StrUtil.isnull(type))
			type = "";
		else
			type = "."+type;
		String fileName = ObjectId.getUUId() + type;
		String dir1 = fileName.substring(0, 2);
		String dir2 = fileName.substring(2, 4);
		String dir3 = fileName.substring(4, 6);
		return dir1 + "/" + dir2 + "/" + dir3 + "/" + fileName;
	}
}