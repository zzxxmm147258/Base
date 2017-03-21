package com.hibo.bas.file;

import java.io.FileNotFoundException;
import java.io.IOException;

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
 * 创建日期：2015年7月9日 下午6:08:07
 * </p>
 * <p>
 * 类全名：com.hibo.bas.file.LocalFileSystem
 * </p>
 * 作者：陈国红 初审： 复审：
 * 
 * @version 1.0
 */
public class LocalFileSystem extends FileSystem {
	final public String rootPath;

	public LocalFileSystem(String rootPath) {
		if (rootPath != null) {
			if (rootPath.startsWith("file:///")) {
				rootPath = rootPath.substring(7);
				if (rootPath.length() > 3 && rootPath.charAt(2) == ':') // windows
					rootPath = rootPath.substring(1);
			} else if (rootPath.startsWith("file:")) {
				rootPath = rootPath.substring(5);
			}
		}
		this.rootPath = rootPath;

	}

	@Override
	public String toURL() {
		return "file:" + rootPath;
	}

	@Override
	public com.hibo.bas.file.File[] listFiles(String path, boolean subdir)
			throws java.io.IOException {
		path = pathNormalize(path);
		final java.io.File f = path == null ? new java.io.File(rootPath)
				: new java.io.File(rootPath, path);
		if (!f.isDirectory())
			return null;
		java.util.List<com.hibo.bas.file.File> v = new java.util.ArrayList<com.hibo.bas.file.File>();
		String prePath = path;
		if (prePath != null && (".".equals(prePath) || prePath.length() == 0))
			prePath = null;
		listFiles(prePath, f, subdir, v, null);
		return (com.hibo.bas.file.File[]) v
				.toArray(new com.hibo.bas.file.File[v.size()]);
	}

	public void listFiles(String path, boolean subdir,
			java.util.List<com.hibo.bas.file.File> to, String excludesDirs[])
			throws java.io.IOException {
		path = pathNormalize(path);
		final java.io.File f = path == null ? new java.io.File(rootPath)
				: new java.io.File(rootPath, path);// new
													// java.io.File(rootPath,path);
		if (!f.isDirectory())
			return;
		String prePath = path;
		if (prePath != null && (".".equals(prePath) || prePath.length() == 0))
			prePath = null;
		String exDirs[][] = null;
		if (excludesDirs != null) {
			exDirs = new String[excludesDirs.length][];
			for (int i = 0; i < excludesDirs.length; i++) {
				exDirs[i] = com.hibo.bas.util.StrUtil.splitString(
						excludesDirs[i], '/');
			}
		}
		listFiles(prePath, f, subdir, to, exDirs);
	}

	@Override
	public com.hibo.bas.file.File getFile(String fileName) {
		fileName = pathNormalize(fileName);
		java.io.File fi = fileName == null ? new java.io.File(rootPath)
				: new java.io.File(rootPath, this.gziped ? fileName + ".~gzip"
						: fileName);
		if (!fi.exists())
			return null;

		return new com.hibo.bas.file.File(fileName, fi.isDirectory(),
				fi.lastModified(), fi.length(), !fi.canWrite(), fi.isHidden());
	}

	@Override
	public boolean isReadonly(com.hibo.bas.file.File file) {
		if (file == null)
			return false;
		final java.io.File f = new java.io.File(rootPath, file.getPath());
		return !f.canWrite();
	}

	@Override
	public void setReadonly(com.hibo.bas.file.File file, boolean readOnly) {
		final java.io.File f = new java.io.File(rootPath, file.getPath());
		if (readOnly)
			f.setReadOnly();
		else
			f.setWritable(true);
	}

	static void listFiles(String prePath, final java.io.File f, boolean subdir,
			java.util.List<com.hibo.bas.file.File> to, String excludesDirs[][]) {
		String files[] = f.list();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			java.io.File fi = new java.io.File(f, files[i]);
			String name = prePath == null ? files[i]
					: (prePath.endsWith("/") ? prePath + files[i] : prePath
							+ "/" + files[i]);
			com.hibo.bas.file.File file = new com.hibo.bas.file.File(name,
					fi.isDirectory(), fi.lastModified(), fi.length());
			to.add(file);
			if (subdir
					&& file.isDir()
					&& (excludesDirs == null || !likeOneOf(file.path,
							excludesDirs))) {
				listFiles(name, fi, subdir, to, excludesDirs);
			}
		}
	}

	@Override
	public boolean deleteFile(String path, boolean deleteNonEmptyDir)
			throws java.io.IOException {
		path = pathNormalize(path);
		final java.io.File f = path == null ? new java.io.File(rootPath)
				: new java.io.File(rootPath, path);
		if (deleteNonEmptyDir && f.isDirectory()) {
			String files[] = f.list();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					deleteFile(path + "/" + files[i], true);
				}
			}
		}
		return f.delete();
	}

	@Override
	public boolean mkdir(String path) throws IOException {
		path = pathNormalize(path);
		final java.io.File f = path == null ? new java.io.File(rootPath)
				: new java.io.File(rootPath, path);
		return f.mkdirs();
	}

	public java.io.File toLocalFile(String fileName) {
		fileName = pathNormalize(fileName);
		return new java.io.File(rootPath, fileName);
	}

	@Override
	public java.io.InputStream openFile(String fileName)
			throws java.io.IOException {
		final String fn = pathNormalize(fileName);
		if (fn == null)
			throw new FileNotFoundException(fileName);
		if (this.gziped) {
			final java.io.File f = new java.io.File(rootPath, fn + ".~gzip");
			return new java.util.zip.GZIPInputStream(
					new java.io.FileInputStream(f));
		}
		final java.io.File f = new java.io.File(rootPath, fn);
		return new java.io.FileInputStream(f);
	}

	@Override
	public java.io.InputStream openFile(com.hibo.bas.file.File file)
			throws java.io.IOException {
		return openFile(file.path);
	}

	@Override
	public java.io.OutputStream openWrite(String fileName, boolean gzip)
			throws java.io.IOException {
		pathNormalize(fileName); // 检查文件 有效
		final java.io.File f = new java.io.File(rootPath, this.gziped
				&& !fileName.endsWith(".~gzip") ? fileName + ".~gzip"
				: fileName);
		com.hibo.bas.util.FileUtil.makeDir(f.getAbsolutePath(), true);
		// @SuppressWarnings("resource")
		java.io.OutputStream os = new java.io.FileOutputStream(f);
		return gzip || this.gziped ? new java.util.zip.GZIPOutputStream(os)
				: os;
	}

	@Override
	public void writeFile(String fileName, java.io.InputStream data, long time)
			throws java.io.IOException {
		pathNormalize(fileName); // 检查文件 有效
		writeFile(fileName, data, time, false);
	}

	@Override
	public void writeFile(String fileName, java.io.InputStream data, long time,
			boolean gzip) throws java.io.IOException {
		pathNormalize(fileName); // 检查文件 有效
		final java.io.File f = new java.io.File(rootPath, this.gziped
				&& !fileName.endsWith(".~gzip") ? fileName + ".~gzip"
				: fileName);
		com.hibo.bas.util.FileUtil.makeDir(f.getAbsolutePath(), true);
		java.io.OutputStream os = new java.io.FileOutputStream(f);
		if (gzip || this.gziped)
			os = new java.util.zip.GZIPOutputStream(os);
		try {
			com.hibo.bas.util.DataStream.copy(data, os);
		} finally {
			os.close();
		}
		if (time > 0)
			f.setLastModified(time);
	}

	@Override
	public String writeFile(String fileName, java.io.InputStream data,
			boolean calcMD5, long time) throws java.io.IOException {
		if (calcMD5) {
			pathNormalize(fileName); // 检查文件 有效
			final java.io.File f = new java.io.File(rootPath, this.gziped
					&& !fileName.endsWith(".~gzip") ? fileName + ".~gzip"
					: fileName);
			com.hibo.bas.util.FileUtil.makeDir(f.getAbsolutePath(), true);
			java.io.OutputStream os = new java.io.FileOutputStream(f);
			if (this.gziped)
				os = new java.util.zip.GZIPOutputStream(os);
			String md5 = null;
			try {
				md5 = com.hibo.bas.util.DataStream.copyAndCalcMD5(data, os);
			} finally {
				os.close();
			}
			if (time > 0)
				f.setLastModified(time);
			return md5;
		} else {
			writeFile(fileName, data, time);
			return null;
		}
	}

	@Override
	public void setFileTime(String fileName, long time)
			throws java.io.IOException {
		pathNormalize(fileName); // 检查文件 有效
		final java.io.File f = new java.io.File(rootPath, fileName);
		f.setLastModified(time);
		// throw new RuntimeException();
	}

	@Override
	public boolean renameTo(com.hibo.bas.file.File file,
			com.hibo.bas.file.File toFile) {
		final java.io.File f1 = new java.io.File(rootPath, file.path);
		final java.io.File f2 = new java.io.File(rootPath, toFile.path);
		f2.getParentFile().mkdirs();
		return f1.renameTo(f2);
	}
}
