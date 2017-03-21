package com.hibo.bas.file;

import java.io.IOException;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午6:44:43</p>
 * <p>类全名：com.hibo.bas.file.FtpClient</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public interface FtpClient extends java.lang.AutoCloseable
{
	//public String system() throws java.io.IOException ;
	public void changeDir(String remoteDirectory) throws IOException;
	public File[] listFiles(String dir,String charSet) throws IOException;
	public void makeDir(String dir) throws IOException; //throws IOException
	public boolean deleteFile(String name) throws IOException;
	public boolean deleteDir(String dir) throws IOException;
	public java.io.InputStream openRead(String fileName) throws IOException;
	public java.io.OutputStream openWrite(String fileName) throws IOException;
	public boolean setFileTime(String name,long date) throws IOException;
	public boolean renameTo(String from, String to) throws IOException ;
}
