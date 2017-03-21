package com.hibo.bas.file;

import java.io.IOException;
import java.io.OutputStream;

import com.hibo.bas.exception.ExceptionUtil;
import com.hibo.bas.exception.HookedException;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.DataStream;
import com.hibo.bas.util.MapUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午6:42:44</p>
 * <p>类全名：com.hibo.bas.file.FtpFileSystem</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class FtpFileSystem extends com.hibo.bas.file.FileSystem {
	protected final String host;
	protected final int port;
	protected final String user, password;
	protected final String rootPath;
	protected com.hibo.bas.file.FtpClient ftpClient;
	protected String charSet;

	public FtpFileSystem(String host, String user, String password,
			String rootPath) throws IOException {
		this(host, 21, user, password, rootPath, null);
	}

	public FtpFileSystem(String host, int port, String user, String password,
			String rootPath) throws IOException {
		this(host, port, user, password, rootPath, null);
	}

	public FtpFileSystem(String host, int port, String user, String password,
			String rootPath, String ftpType) throws IOException {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.rootPath = rootPath;
		ftpClient = newFtpClient(host, port, user, password, ftpType);
	}

	@Override
	public String toURL() {
		String url = "ftp://" + user + ":" + password + "@" + host + ":" + port;
		if (rootPath != null)
			url += rootPath;
		return url;
	}

	public FtpFileSystem(String url) throws IOException {
		if (!url.startsWith("ftp://") && !url.startsWith("sftp://"))
			throw new IllegalArgumentException(url);
		final boolean sftp = url.startsWith("sftp://");
		String s = url.substring(sftp ? 7 : 6);
		int p = s.indexOf('/');
		if (p < 0) {
			rootPath = null;
		} else {
			rootPath = s.substring(p);
			s = s.substring(0, p);
		}
		p = s.indexOf('@');
		if (p < 0) {
			user = "anonymous";
			password = "";
		} else {
			String u = s.substring(0, p);
			s = s.substring(p + 1);
			p = u.indexOf(':');
			user = p < 0 ? u : u.substring(0, p);
			password = p < 0 ? "" : u.substring(p + 1);
		}
		p = s.indexOf(':');
		host = p < 0 ? s : s.substring(0, p);
		port = p < 0 ? 21 : Integer.parseInt(s.substring(p + 1));
		ftpClient = newFtpClient(host, port, user, password, sftp ? "SFTP"
				: "FTP");
	}

	/*
	 * FTP.ClientClass=com.hibo.bas.file.ApacheFtpClient
	 */
	static String ftpClientClass;

	static com.hibo.bas.file.FtpClient newFtpClient(String host, int port,
			String user, String password, String protocol) throws IOException {
		if (protocol == null)
			protocol = "FTP";
		assert "FTP".equals(protocol) || "SFTP".equals(protocol);
		try {
			//ftpClientClass = "com.hibo.bas.file.ApacheFtpClient?dataConnectionMode=2";
			
			  if (ftpClientClass == null) { 
				  try { 
					  ftpClientClass = DataConfig.getConfig(protocol + ".ClientClass"); 
				  } catch(Throwable ex) { 
				  }
			  }
			  if (ftpClientClass == null) 
				  ftpClientClass = "com.hibo.bas.file.ApacheFtpClient?DataConnectionMode=2";

			final int p = ftpClientClass.indexOf('?');
			final String className = p >= 0 ? ftpClientClass.substring(0, p) : ftpClientClass;
			final java.util.Map<String,String> xparams = p >= 0 ? MapUtil.parseParameterTo(
					ftpClientClass.substring(p + 1), null) : null;
			return (com.hibo.bas.file.FtpClient) Class
					.forName(className)
					.getConstructor(String.class, Integer.TYPE, String.class,
							String.class, java.util.Map.class)
					.newInstance(host, port, user, password, xparams);
		} catch (Throwable ex) {
			ex = ExceptionUtil.getThrowable(ex, true);
			if (ex instanceof java.net.ConnectException)
				throw HookedException.newExceptionByErrCode(null, "000021", ex,
						host + ":" + port);
			if (ex instanceof IOException)
				throw (IOException) ex;
			// HookedException.g
			throw HookedException.toRuntimeException(ex);
		}
	}

	public void setCharset(String charSet) {
		this.charSet = charSet;
	}

	public com.hibo.bas.file.FtpClient getFtpClient() {
		return ftpClient;
	}

	@Override
	public com.hibo.bas.file.File[] listFiles(String path, boolean subdir)
			throws IOException {
		pathNormalize(path); // 检查文件 有效
		java.util.List<com.hibo.bas.file.File> v = new java.util.ArrayList<com.hibo.bas.file.File>();
		listFiles(v, path, subdir);
		return v.toArray(new com.hibo.bas.file.File[v.size()]);
	}

	private void listFiles(java.util.List<com.hibo.bas.file.File> to,
			String path, boolean subdir) throws IOException {
		com.hibo.bas.file.File a[] = ftpClient.listFiles(
				FtpFileSystem.buildFullPath(rootPath, path), this.charSet);
		if (a == null)
			return;
		for (int i = 0; i < a.length; i++) {
			if (path != null && path.length() > 0)
				a[i] = new com.hibo.bas.file.File(path + "/" + a[i].path,
						a[i].isdir, a[i].time, a[i].length);
			to.add(a[i]);
			String name = a[i].getName();
			if (subdir && a[i].isdir && !".".equals(name) && !"..".equals(name))
				listFiles(to, a[i].getPath(), subdir);
		}
	}

	public boolean exists(String path) {
		return true;
	}

	@Override
	public boolean deleteFile(String fileName, boolean deleteNonEmptyDir)
			throws IOException {
		pathNormalize(fileName); // 检查文件 有效
		int p = fileName.lastIndexOf('/');
		String cdTo = buildFullPath(rootPath, p > 0 ? fileName.substring(0, p)
				: null);
		ftpClient.changeDir(cdTo);
		return ftpClient.deleteFile(p > 0 ? fileName.substring(p + 1)
				: fileName);
	}

	@Override
	public boolean deleteDir(String fileName) throws IOException {
		pathNormalize(fileName); // 检查文件 有效
		int p = fileName.lastIndexOf('/');
		String cdTo = buildFullPath(rootPath, p > 0 ? fileName.substring(0, p)
				: null);
		ftpClient.changeDir(cdTo);
		return ftpClient
				.deleteDir(p > 0 ? fileName.substring(p + 1) : fileName);
	}

	@Override
	public java.io.InputStream openFile(String fileName) throws IOException {
		pathNormalize(fileName); // 检查文件 有效
		int p = fileName.lastIndexOf('/');
		String cdTo = buildFullPath(rootPath, p > 0 ? fileName.substring(0, p)
				: null);
		ftpClient.changeDir(cdTo);
		return ftpClient.openRead(p > 0 ? fileName.substring(p + 1) : fileName);
	}

	@Override
	public java.io.InputStream openFile(com.hibo.bas.file.File file)
			throws IOException {
		return openFile(file.getPath());
	}

	@Override
	public void writeFile(String fileName, java.io.InputStream data, long time)
			throws IOException {
		fileName = pathNormalize(fileName); // 检查文件 有效
		int p = fileName.lastIndexOf('/');
		if (p > 0) {
			mkdir(fileName.substring(0, p));
		}
		changeDir(p > 0 ? fileName.substring(0, p) : null);

		final String name;
		OutputStream os = ftpClient.openWrite(name = p > 0 ? fileName
				.substring(p + 1) : fileName);
		try {
			DataStream.copy(data, os);
		} finally {
			os.close();
		}
		if (time > 0)
			ftpClient.setFileTime(name, time);
	}

	@Override
	public String writeFile(String fileName, java.io.InputStream data,
			boolean calcMD5, long time) throws java.io.IOException {
		if (calcMD5) {
			fileName = pathNormalize(fileName); // 检查文件 有效
			int p = fileName.lastIndexOf('/');
			if (p > 0) {
				mkdir(fileName.substring(0, p));
			}
			changeDir(p > 0 ? fileName.substring(0, p) : null);
			final String name;
			OutputStream os = ftpClient.openWrite(name = p > 0 ? fileName
					.substring(p + 1) : fileName);
			String md5 = null;
			try {
				md5 = DataStream.copyAndCalcMD5(data, os);
			} finally {
				os.close();
			}
			if (time > 0)
				ftpClient.setFileTime(name, time);
			return md5;
		} else {
			writeFile(fileName, data, time);
			return null;
		}
	}

	@Override
	public boolean mkdir(String path) throws IOException {
		pathNormalize(path);
		int p0 = 0;
		IOException exception = null;
		for (; p0 < path.length();) {
			int p = path.indexOf('/', p0);
			if (p == p0)
				break;
			changeDir(p0 == 0 ? null : path.substring(0, p0));
			// String d = p<0 ?
			try {
				ftpClient.makeDir(p < 0 ? path.substring(p0) : path.substring(
						p0, p));
			} catch (java.io.IOException ex) {
				if (exception == null) {
					// System.err.println("建立目录失败:"+path); //ERROR
					exception = ex;
				}
			}
			if (p < 0)
				break;
			p0 = p + 1;
		}
		try {
			changeDir(path);
			return true;
		} catch (java.io.IOException ex) {
			throw exception == null ? ex : exception;
		}
		// ftpClient.m
	}

	void changeDir(String path) throws IOException {
		String cdTo = buildFullPath(rootPath, path);
		ftpClient.changeDir(cdTo);
	}

	protected String toRealPath(String path) {
		return buildFullPath(rootPath, path);
	}

	@Override
	public void writeFile(String fileName, byte data[]) throws IOException {
		pathNormalize(fileName); // 检查文件 有效
		int p = fileName.lastIndexOf('/');
		if (p > 0) {
			mkdir(fileName.substring(0, p));
		}
		changeDir(p > 0 ? fileName.substring(0, p) : null);
		OutputStream os = ftpClient.openWrite(p > 0 ? fileName.substring(p + 1)
				: fileName);
		try {
			os.write(data);
		} finally {
			os.close();
		}
	}

	@Override
	public java.io.OutputStream openWrite(String fileName, boolean gzip)
			throws java.io.IOException {
		pathNormalize(fileName); // 检查文件 有效
		int p = fileName.lastIndexOf('/');
		if (p > 0) {
			mkdir(fileName.substring(0, p));
		}
		changeDir(p > 0 ? fileName.substring(0, p) : null);
		OutputStream os = ftpClient.openWrite(p > 0 ? fileName.substring(p + 1)
				: fileName);
		return gzip || this.gziped ? os = new java.util.zip.GZIPOutputStream(os)
				: os;
	}

	@Override
	public void setFileTime(String fileName, long time) throws IOException {
		pathNormalize(fileName); // 检查文件 有效
		int p = fileName.lastIndexOf('/');
		String cdTo = buildFullPath(rootPath, p > 0 ? fileName.substring(0, p)
				: null);
		// ftpClient.cd(cdTo);
		ftpClient.changeDir(cdTo);
		// MDTM
		ftpClient.setFileTime(p > 0 ? fileName.substring(p + 1) : fileName,
				time);
	}

	@Override
	public void close() {
		try {
			if (ftpClient != null)
				ftpClient.close();
			// ftpClient.closeServer();
			ftpClient = null;
		} catch (Throwable ex) {
			// ex.printStackTrace();
		}
	}

	@Override
	public void setReadonly(com.hibo.bas.file.File file, boolean readOnly) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadonly(com.hibo.bas.file.File file) {
		throw new UnsupportedOperationException();
		// return os==null;
	}

	@Override
	public com.hibo.bas.file.File getFile(String fileName) {
		// String cdTo = buildPath(rootPath,path);
		//
		try {
			fileName = pathNormalize(fileName); // 检查文件 有效
			if (fileName == null || fileName.length() == 0) //
			{
				// rootPath
				return new com.hibo.bas.file.File("", true, 0, 0);
			}
			int p = fileName.lastIndexOf('/');
			final String path = p > 0 ? fileName.substring(0, p) : null;
			final String name = p >= 0 ? fileName.substring(p + 1) : fileName;
			com.hibo.bas.file.File a[] = ftpClient.listFiles(
					FtpFileSystem.buildFullPath(rootPath, path), this.charSet);
			if (a == null)
				return null;
			for (int i = 0; i < a.length; i++) {
				if (name.equals(a[i].path)) {
					if (path != null && path.length() > 0)
						a[i] = new com.hibo.bas.file.File(path + "/"
								+ a[i].path, a[i].isdir, a[i].time, a[i].length);
					return a[i];
				}
			}
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public boolean renameTo(com.hibo.bas.file.File file,
			com.hibo.bas.file.File toFile)
	// throws java.io.IOException
	{
		try {
			return this.ftpClient.renameTo(toRealPath(file.path),
					toRealPath(toFile.path));
		} catch (Throwable ex) {
			throw HookedException.toRuntimeException(ex);
		}
	}
};
