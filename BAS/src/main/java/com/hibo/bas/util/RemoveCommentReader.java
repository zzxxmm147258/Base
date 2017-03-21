package com.hibo.bas.util;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月10日 下午2:31:18</p>
 * <p>类全名：com.hibo.bas.util.RemoveCommentReader</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class RemoveCommentReader extends Reader {
	protected Reader in;

	public RemoveCommentReader(Reader in) {
		this.in = in;
	}

	public RemoveCommentReader(InputStream in) {
		this.in = new java.io.InputStreamReader(in);
	}

	public RemoveCommentReader(InputStream in, String charSet)
			throws UnsupportedEncodingException {
		this.in = charSet == null ? new java.io.InputStreamReader(in)
				: new java.io.InputStreamReader(in, charSet);
	}

	public RemoveCommentReader(String text) {
		this.in = new java.io.StringReader(text);
	}

	int inString, cb, cprev;
	boolean inComment;

	// private final int _read()
	@Override
	public int read() throws java.io.IOException {
		int c;
		if (cb != 0) {
			c = cb;
			cb = 0;
			return cprev = c;
		}
		// super.read()
		// if(
		lr: for (;;) {
			c = in.read();
			if (c < 0)
				return -1;
			if (inString != 0 && inString == c && cprev != '\\') {
				inString = 0;
				break;
			}
			if (inComment) {
				if (c == '\n')
					break;
				for (; c == '*';) {
					c = in.read();
					if (c == '/') {
						inComment = false;
						return cprev = ' ';
					}
					if (c == '\n')
						break lr;
				}
				continue;
			}
			// !inComment :
			if (c == '/' && inString == 0) {
				int next = in.read();
				if (next == '/') {
					for (;;) {
						c = in.read();
						if (c < 0)
							return -1;
						if (c == '\n')
							break;
					}
				} else if (next == '*') {
					inComment = true;
					continue;
				} else {
					cb = next;
					break;
				}
			}
			if ((c == '"' || c == '"') && inString == 0)
				inString = c;
			break;
		} // for(;;)
		if (c == '\n')
			inString = 0;
		return cprev = c;
	}

	@Override
	public int read(char cbuf[], int off, int len) throws java.io.IOException {
		// java.io.StringReader r;
		int n = 0;
		for (; n < len; n++) {
			int c = read();
			if (c < 0) {
				if (n == 0)
					return -1;
				break;
			}
			cbuf[off++] = (char) c;
		}
		return n;
	}

	@Override
	public void close() throws java.io.IOException {
		in.close();
	}

	public String readLine() throws java.io.IOException {
		StringBuilder sb = new StringBuilder();
		for (;;) {
			int c = read();
			if (c < 0 || c == '\n') {
				if (c < 0 && sb.length() == 0)
					return null;
				break;
			}
			sb.append((char) c);
		}
		return sb.toString();
	}

	public String readText() throws java.io.IOException {
		StringBuilder sb = new StringBuilder();
		for (;;) {
			int c = read();
			if (c < 0)
				break;
			sb.append((char) c);
		}
		return sb.toString();
	}

	static public String removeComment(String text)
	{
		if (text == null)
			return null;
		try {
			return new RemoveCommentReader(new StringReader(text)).readText();
		} catch (Throwable ex) {
			throw com.hibo.bas.exception.HookedException.toRuntimeException(ex);
		}
	}
}