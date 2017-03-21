package com.hibo.bas.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.hibo.bas.util.Diagnostic;
import com.hibo.bas.util.ObjectUtil;
import com.hibo.bas.util.StrUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午6:47:07</p>
 * <p>类全名：com.hibo.bas.file.ApacheFtpClient</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
/*
FTP.ClientClass=com.hibo.bas.file.ApacheFtpClient
FTP.ClientClass=com.hibo.bas.file.ApacheFtpClient?Encoding=UTF8&Ftp.DataTimeout=30000&Ftp.DefaultTimeout=30000
FTP.ClientClass=com.hibo.bas.file.ApacheFtpClient?Encoding=UTF8&Ftp.DataTimeout=30000&DataConnectionMode=2
new ApacheFtpClient("192.168.1.9",21,"ftp","2012",null);

FTPSClient
http://blog.csdn.net/luoshenfu001/article/details/7448385
*/
public class ApacheFtpClient extends org.apache.commons.net.ftp.FTPClient
		implements com.hibo.bas.file.FtpClient {
	public ApacheFtpClient(String host, int port, String user, String password,
			java.util.Map<String, Object> xparams) throws IOException {
		super.setConnectTimeout(60000);// MapUtil.getIntValue(xparams,"ConnectTimeout",
										// 60000));//60000);
		String defEncoding = xparams == null ? null : (String) xparams.get("Encoding");
		if (defEncoding != null)
			encoding = defEncoding;
		super.setControlEncoding(encoding);// "GBK");
		if (xparams != null) {
			ObjectUtil.set(this, "Ftp.", xparams, true, true);
		}
		// encoding = this.getControlEncoding();
		/*
		 * ACTIVE_REMOTE_DATA_CONNECTION_MODE = 1;
		 * PASSIVE_LOCAL_DATA_CONNECTION_MODE = 2;
		 */
		// enterLocalPassiveMode();
		// this.setDataTimeout(timeout); // milliseconds
		// this.setDefaultTimeout(timeout);
		// super.setDefaultTimeout(1*60*1000) ;
		super.connect(host, port);
		super.login(user, password);// "xmn5sp7");
		ApacheFtpClient.this
				.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
		// super.setDataTimeout(timeout)
		// ftpClient.enterLocalActiveMode();

		final int dataConnectionMode = xparams == null ? 2 : StrUtil.obj2int(
				xparams.get("DataConnectionMode"), 2);
		//System.err.println("dataConnectionMode="+dataConnectionMode);
		
		if (dataConnectionMode == 2){
			//每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据
			this.enterLocalPassiveMode(); // PASSIVE_LOCAL_DATA_CONNECTION_MODE
		}
											// = 2;
		if (Diagnostic.isLevelEnableOutput(7)) {
			System.out.println("ApacheFtpClient.DefaultTimeout = "
					+ this.getDefaultTimeout());
			System.out.println("ApacheFtpClient.ConnectTimeout = "
					+ this.getConnectTimeout());
		}
	}

	public void setDataConnectionMode(int m) {
		if (m == 2)
			this.enterLocalPassiveMode();
		else if (m == 0)
			this.enterLocalActiveMode();
	}

	String encoding = "GB18030";

	public void setEncoding(String encoding) {
		this.encoding = encoding == null ? "GB18030" : encoding;
		super.setControlEncoding(this.encoding);
	}

	protected String encode(String text) throws IOException {
		if (encoding.equals(getControlEncoding())) {
			return text;
		}
		return text == null ? null : new String(text.getBytes(encoding),
				getControlEncoding());
		// [end add]
	}

	@Override
	public void changeDir(String remoteDirectory) throws IOException {
		if (!super.changeWorkingDirectory(encode(remoteDirectory))) {
			// super.getd
			throw new java.io.IOException("目录错误:" + remoteDirectory);
			// new Exception("changeDir : "+remoteDirectory).printStackTrace();;
		}

	}

	@Override
	public com.hibo.bas.file.File[] listFiles(String dir, String charSet)
			throws IOException {
		for (; dir != null && dir.endsWith("/.");) {
			dir = dir.substring(0, dir.length() - 2);
		}
		this.changeDir(dir);
		org.apache.commons.net.ftp.FTPFile ftpFiles[] = super.listFiles();
		if (ftpFiles == null)
			return null;
		// com.hibo.bas.file.File[] files = new
		// com.hibo.bas.file.File[ftpFiles.length];
		java.util.List<com.hibo.bas.file.File> v = new java.util.ArrayList<com.hibo.bas.file.File>();
		for (int i = 0; i < ftpFiles.length; i++) {
			org.apache.commons.net.ftp.FTPFile ftpFile = ftpFiles[i];
			// ftpFile.isDirectory()
			java.util.Calendar ftpDate = ftpFile.getTimestamp();
			String name = ftpFile.getName();
			if (".".equals(name) || "..".equals(name))
				continue;
			v.add(new com.hibo.bas.file.File(name, ftpFile.isDirectory(),
					ftpDate == null ? 0 : ftpDate.getTimeInMillis(), ftpFile
							.getSize()));
			// com.hibo.bas.file.File file
		}
		return v.toArray(new com.hibo.bas.file.File[v.size()]);
		// return null;
	}

	@Override
	public void makeDir(String dir) throws IOException {
		if (!super.makeDirectory(encode(dir))) {
			throw new java.io.IOException("建立目录错误:" + dir);
		}
		// new String(dir.getBytes(encoding),"iso-8859-1"));//dir);
	}

	@Override
	public boolean deleteFile(String fileName) throws IOException {
		return super.deleteFile(encode(fileName));// new
													// String(fileName.getBytes(encoding),"iso-8859-1"));
		// return false;
	}

	@Override
	public boolean deleteDir(String dir) throws IOException {
		return super.removeDirectory(encode(dir));
		// return false;
	}

	@Override
	public InputStream openRead(String fileName) throws IOException {
		final java.io.InputStream in = retrieveFileStream(encode(fileName));
		if (in == null)
			return null;
		return new FtpInputStream(in);

	}

	final class FtpInputStream extends java.io.FilterInputStream {
		public FtpInputStream(InputStream is) {
			super(is);
		}

		@Override
		public void close() throws IOException {
			super.close();
			completePendingCommand();
		}
	}

	@Override
	public OutputStream openWrite(String fileName) throws IOException {

		final OutputStream out = super.storeFileStream(encode(fileName));
		{

			final int replyCode = ApacheFtpClient.this.getReplyCode(); // FILE_STATUS_OK
																		// =
																		// 150;
			if (replyCode >= 400) {
				if (out != null)
					out.close();

				throw new java.io.IOException("文件(" + fileName
						+ ")上传失败: replyCode=" + replyCode);
			}

		}
		if (out == null)
			return null;
		return new FtpOutputStream(out);// new
										// String(fileName.getBytes(encoding),"iso-8859-1")));
	}

	final class FtpOutputStream extends java.io.FilterOutputStream {
		public FtpOutputStream(OutputStream out) // throws IOException
		{
			super(out);

		}

		@Override
		public void write(byte b[], int off, int len) throws IOException {
			out.write(b, off, len);
		}

		@Override
		public void close() throws IOException {
			if (out != null)
				super.close();
			completePendingCommand();
		}
	} // class FtpOutputStream

	@Override
	public boolean setFileTime(String name, long date) throws IOException {
		// ftpClient.setf
		return false;
	}

	@Override
	public boolean renameTo(String from, String to) throws IOException {
		int p = to.lastIndexOf('/');
		if (p > 0) {
			String dir = to.substring(0, p);
			int p0 = 0;
			for (;;) {
				p = dir.indexOf('/', p0 + 1);
				if (p < 0)
					break;
				makeDirectory(dir.substring(0, p)); // ???
				p0 = p;
			}
			makeDirectory(dir); // ???
		}
		return super.rename(encode(from), encode(to));
	}

	@Override
	public void close() {
		try {
			super.disconnect();
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
}

/*
在项目中使用commons-net-3.0.1.jar实现FTP文件的下载，在windows xp上运行正常，但是放到linux上，却出现问题，
程序运行到 FTPClient.listFiles()或者FTPClient.retrieveFile()方法时，就停止在那里，什么反应都没有，出现假死状态。
解决办法。在调用这两个方法之前，调用FTPClient.enterLocalPassiveMode();这个方法的意思就是每次数据连接之前，
ftp client告诉ftp server开通一个端口来传输数据。为什么要这样做呢，因为ftp server可能每次开启不同的端口来传输数据，
	但是在linux上，由于安全限制，可能某些端口没有开启，所以就出现阻塞
*/