package com.hibo.bas.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * <p>标题：数据流处理 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午4:42:21</p>
 * <p>类全名：com.hibo.bas.util.DataStream</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class DataStream {

    static public byte[] toByteArray(InputStream in) throws IOException
    {
        if( in==null )
        	return null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy( in, out );
        return out.toByteArray();
    }
    
    static public byte[] toByteArray(Object x) throws IOException
    {
        if( x==null || x instanceof byte[] )
            return (byte[])x;
        if( x instanceof InputStream )
            return toByteArray((InputStream)x);
        throw new java.lang.IllegalArgumentException();
    }
    
    static public byte[] toByteArray(InputStream in,int length) throws IOException
    {
        if( in==null ) return null;
        byte a[] = new byte[length];
        DataInputStream is = in instanceof DataInputStream ? (DataInputStream)in : new DataInputStream(in);
        is.readFully(a);
        return a;
    }
    
    static public int copy(InputStream in,OutputStream out) throws IOException
    {
        int n = 0;
        final byte[] buffer = new byte[64*1024];
        for(;;)
        {
            final int count = in.read(buffer);
            if (count < 0)
                break;
            out.write(buffer,0,count);
            n += count;
        }
        return n;
    }
    
    static public String toString(java.io.Reader reader) throws IOException
    {
    	StringBuilder sb = new StringBuilder();
        char buffer[] = new char[16*1024];
        for(;;) {
            int count = reader.read(buffer);
            if( count<0 ) break;
            sb.append(buffer,0,count);
        }
        return sb.toString();
    }
    
	static public String copyAndCalcMD5(InputStream in, OutputStream out) throws IOException
	{
		final byte[] buffer = new byte[2 * 1024];//计算MD5,缓存大反倒比较慢
		java.security.MessageDigest md;
		try
		{
			md = java.security.MessageDigest.getInstance("MD5");
		} catch (java.security.NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
		if (md != null)
		{
			md.reset();
			int n = 0;
			while ((n = in.read(buffer)) >= 0)
			{
				md.update(buffer, 0, n);
				out.write(buffer, 0, n);
			}
			out.flush();
			if (md != null)
			{
				String md5 = StrUtil.toHexString(md.digest());
				return md5;
			}
		}
		return null;
	}
}
