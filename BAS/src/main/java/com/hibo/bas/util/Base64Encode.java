package com.hibo.bas.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午5:35:37</p>
 * <p>类全名：com.hibo.bas.util.Base64Encode</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class Base64Encode extends OutputStream {
	final java.io.OutputStream os;
	int remainder = 0;
	int bitsRemainder = 0;
	int outCount = 0;

	public Base64Encode() {
		os = new ByteArrayOutputStream();
	}

	public Base64Encode(java.io.OutputStream os) {
		this.os = os;
	}

	/**
	 * Writes the specified byte to this output stream. The general contract for
	 * <code>write</code> is that one byte is written to the output stream. The
	 * byte to be written is the eight low-order bits of the argument
	 * <code>b</code>. The 24 high-order bits of <code>b</code> are ignored.
	 * <p>
	 * Subclasses of <code>OutputStream</code> must provide an implementation
	 * for this method.
	 * 
	 * @param b
	 *            the <code>byte</code>.
	 * @exception IOException
	 *                if an I/O error occurs. In particular, an
	 *                <code>IOException</code> may be thrown if the output
	 *                stream has been closed.
	 */
	@Override
	public void write(int indata) throws IOException {
		indata &= 0xff;
		if (closed) // || indata>255
			throw new IOException();
		for (;;) {
			if (bitsRemainder >= 6) {
				os.write(toBase64Code((remainder >> (bitsRemainder - 6)) & 63));
				outCount++;
				bitsRemainder -= 6;
				continue;
			}
			if (indata < 0)
				break;
			remainder = (remainder << 8) | (indata & 0xff);
			bitsRemainder += 8;
			indata = -1;
		}
	}

	boolean closed;

	@Override
	public void close() throws IOException {
		if (!closed) {
			if (bitsRemainder > 0) // bitsRemainder<6
			{
				os.write(toBase64Code((remainder << (6 - bitsRemainder)) & 63));
				outCount++;
			}
			for (; (outCount & 3) != 0; outCount++)
				os.write('=');
			os.close();
			closed = true;
		}
	}

	public static final int toBase64Code(int x) {
		if (x < 0 || x >= 64)
			throw new IllegalArgumentException();
		if (x <= 25)
			return (char) ('A' + x);
		if (x <= 51)
			return (char) ('a' + x - 26);
		if (x <= 61)
			return (char) ('0' + x - 52);
		if (x == 62)
			return '+';
		if (x == 63)
			return '/';
		return '=';
	}

	public byte[] toByteArray() throws IOException {
		if (!closed)
			throw new IOException();
		if (!(os instanceof ByteArrayOutputStream))
			throw new IOException();
		return ((ByteArrayOutputStream) os).toByteArray();// buffer.toString();
	}

	public String getAsString() throws IOException {
		if (!closed)
			throw new IOException();
		if (!(os instanceof ByteArrayOutputStream))
			throw new IOException();
		return new String(((ByteArrayOutputStream) os).toByteArray(), "8859_1");// buffer.toString();
	}

	static public String encode(byte a[]) throws IOException {
		Base64Encode encode = new Base64Encode();
		encode.write(a);
		encode.close();
		return encode.getAsString();
	}

	static public String encodeString(String text, String charset)
			throws IOException {
		return encode(charset == null ? text.getBytes() : text
				.getBytes(charset));
	}
}
