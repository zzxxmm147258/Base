package com.hibo.bas.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午4:09:09</p>
 * <p>类全名：com.hibo.bas.util.ResUtil</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class ResUtil {
	/*
	 * 中文： file.encoding=GB18030 ， 英文 file.encoding=Cp1252
	 * (ASCII、GB2312、GBK到GB18030) 繁体 file.encoding=MS950 defaultEncoding =
	 * "ISO-8859-1"; user.language=zh user.country=CN AppProps.CharSet=GBK
	 * -Xms128m -Xmx256m -Dfile.encoding=Cp1252 -Duser.language=en
	 * -Duser.country=US
	 */

	final static String CharSet;

	static {
		String charSet = null;
		try {
			charSet = System.getProperty("CharSet");
		} catch (Throwable ex) {
		}
		CharSet = charSet;
	}

	static public java.nio.charset.Charset getCharset(String charsetName) {
		if (charsetName == null)
			return StandardCharsets.UTF_8;
		return java.nio.charset.Charset.forName(charsetName);
	}

	final public static InputStreamReader newInputStreamReader(InputStream _is)
			throws IOException {
		return newInputStreamReader(_is, null);
	}

	final public static InputStreamReader newInputStreamReader(InputStream _is,
			final String defaultCharset) throws IOException {
		String charSet = defaultCharset;// ResUtil.getLanguageCharSet(language);
		java.io.PushbackInputStream bis = new java.io.PushbackInputStream(_is,
				256 + 2);
		_testCharSet: {
			byte firstLine[] = new byte[256];
			int lh = bis.read(firstLine, 0, 3);
			if (lh >= 2
					&& ((firstLine[0] & 0xff) == 0xff && (firstLine[1] & 0xff) == 0xfe)
					|| ((firstLine[0] & 0xff) == 0xfe && (firstLine[1] & 0xff) == 0xff)) {
				charSet = "UNICODE";
				bis.unread(firstLine, 0, lh);
				lh = 0;
				break _testCharSet;
			} else if (lh == 3 && (firstLine[0] & 0xff) == 0xef
					&& (firstLine[1] & 0xff) == 0xbb
					&& (firstLine[2] & 0xff) == 0xbf) {
				charSet = "UTF-8";
				lh = 0;
			}
			if (lh > 0)
				bis.unread(firstLine, 0, lh);
			int i = 0;
			int i0 = -1, i1 = -1;
			for (; i < 256;) {
				int c = bis.read();
				if (c < 0)
					break;
				firstLine[i] = (byte) c;
				if (i0 < 0 && c > ' ')
					i0 = i;
				if (c > ' ')
					i1 = i;
				i++;
				if (c < ' ')
					break;
			}

			if (i0 >= 0 && i1 - i0 >= 3 && firstLine[i0] == '<'
					&& firstLine[i0 + 1] == '?' && firstLine[i1] == '>'
					&& firstLine[i1 - 1] == '?') {
				String s = new String(firstLine, i0 + 2, i1 - 1 - (i0 + 2))
						.trim();
				if (s.startsWith("encoding=")) {
					charSet = s.substring(9).trim();
					if (charSet.length() > 2 && charSet.charAt(0) == '"'
							&& charSet.charAt(charSet.length() - 1) == '"')
						charSet = charSet.substring(1, charSet.length() - 1);
				}
			} else {
				if (i > 0)
					bis.unread(firstLine, 0, i);
			}
		}
		return charSet == null ? new java.io.InputStreamReader(bis)
				: new java.io.InputStreamReader(bis, charSet);
	}

	final public static InputStreamReader newInputStreamReader(String location,
			final String defaultCharset) throws IOException {
		java.net.URL url = urlFromResource(location);
		return newInputStreamReader(url.openStream(), defaultCharset);
	}

	public static java.net.URL urlFromResource(String location)
			throws java.net.MalformedURLException {
		if (location == null)
			return null;
		return new java.net.URL(location);
	}

	public static String textFromResource(String location)
			throws java.io.IOException {
		return textFromResource(location, ResUtil.CharSet);
	}

	public static String textFromResource(String location, String charSet)
			throws java.io.IOException {
		java.net.URL url = urlFromResource(location);
		if (url == null)
			return null;
		InputStreamReader reader = newInputStreamReader(location, charSet);
		try {
			return StrUtil.toString(reader);
		} finally {
			try {
				reader.close();
			} catch (Throwable ex) {
			}
		}
	}
}
