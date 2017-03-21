package com.hibo.bas.util;
/**
 * <p>标题： Html工具类</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 </p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午6:19:37</p>
 * <p>类全名：com.hibo.bas.util.HtmlUtil</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class HtmlUtil {
	public final static String xmlEscapeChars = "<>&\"'";// \"&<>";
	public final static String xmlEscapeText[] = { "&lt;", "&gt;", "&amp;",
			"&quot;", "&apos;" };

	static public String htmlEncode(String text) {
		return htmlEncode(text, null);
	}

	// htmlEncode(text,"<br>")
	static public String htmlEncode(String text, String lineRetText) {
		if (text == null)
			return text;
		final int textLength = text.length();
		if (textLength == 0)
			return text;
		StringBuilder textBuffer = new StringBuilder();
		htmlEncode(textBuffer, text, lineRetText, 0);
		return textBuffer.toString();
	}

	/*
	 * options#1 : 不转换空格
	 */
	static public void htmlEncode(java.lang.Appendable textBuffer, String text,
			String lineRetText, int options) {
		// String encodeChars = "<>";
		// String encodeTo[] = {"&lt;","&gt;"};
		try {
			boolean preBlank = true;
			final int textLength = text.length();
			for (int i = 0; i < textLength; i++) {
				char c = text.charAt(i);
				if (preBlank && c == ' ') {
					textBuffer.append("&nbsp;");
				} else {
					int p = xmlEscapeChars.indexOf(c);
					if (p >= 0) {
						textBuffer.append(xmlEscapeText[p]);
						continue;
					}
					switch (c) {
					case '\t':
						for (int j = 0; j < 4; j++)
							textBuffer.append("&nbsp;");
						break;
					default:
						if (c == '\r' && i < textLength - 1
								&& text.charAt(i + 1) == '\n')
							continue;
						if (c == '\n' && lineRetText != null) {
							textBuffer.append(lineRetText).append('\r');
						}
						textBuffer.append(c);
						if (c == '\n')
							preBlank = true;
					}
					if (c > ' ')
						preBlank = false;
				}
			}
		} catch (java.io.IOException ex) {
			throw com.hibo.bas.exception.HookedException.toRuntimeException(ex);
		}
	}

	static public String htmlEncode(char c) {
		int p = xmlEscapeChars.indexOf(c);
		return p >= 0 ? xmlEscapeText[p] : null;
	}

	static public String htmlDecode(String text) {
		return htmlDecode(text, 0);
	}

	/*
	 * options : 1: 不转换 xmlEscapeText //'\"&<>
	 */
	static public String htmlDecode(String text, int options) {
		if (text == null)
			return text;
		// &#x6C49;
		StringBuilder textBuffer = new StringBuilder();
		int nDecoded = 0;
		final int n = text.length();
		for (int i = 0; i < n; i++) {
			char c = text.charAt(i);
			if (c == '&') {
				int p = text.indexOf(';', i + 1);
				if (p >= 0) {
					String s = text.substring(i, p + 1);
					int j = (options & 1) == 0 ? StrUtil.indexOf(xmlEscapeText,
							s) : -1;
					if (j >= 0) {
						textBuffer.append(xmlEscapeChars.charAt(j));
						nDecoded++;
						i = p;
						continue;
					}
					if (s.length() >= 4 && s.charAt(1) == '#') {
						s = s.substring(2, s.length() - 1).toUpperCase();
						int radix = 10;
						if (s.charAt(0) == 'X') {
							s = s.substring(1);
							radix = 16;
						}
						textBuffer.append((char) Integer.parseInt(s, radix));
						nDecoded++;
						i = p;
						continue;
					}
				}
			}
			textBuffer.append(c);
		}
		if (nDecoded > 0)
			return textBuffer.toString();
		return text;
	}

	public static int findTagPosition(String htmlText, final int start,
			String tag, boolean tagStart) {
		final int n = htmlText.length();
		tag = tag.toLowerCase();
		for (int i = start; i < n; i++) {
			char c = htmlText.charAt(i);
			if (c == '<') {
				int p = htmlText.indexOf('>', i + 1);
				if (p < 0)
					return -1;
				String s = htmlText.substring(i + 1, p).trim().toLowerCase();
				final int ns = s.length();
				if (ns > 0) {
					StringBuilder sb = new StringBuilder();
					int j = 0;
					for (; j < ns; j++) {
						c = s.charAt(j);
						if (c >= 'a' && c <= 'z')
							break;
						if (c > ' ')
							sb.append(c);
					}
					for (; j < ns; j++) {
						c = s.charAt(j);
						if (c < 'a' || c > 'z')
							break;
						sb.append(c);
					}
					s = sb.toString();
					if (s.equalsIgnoreCase(tag))
						return tagStart ? i : p + 1;
				}
				i = p;
			}
		}
		return -1;
	}

	public static String getHtmlBody(String htmlText) {
		if (htmlText == null)
			return null;
		int p1 = findTagPosition(htmlText, 0, "body", false);
		if (p1 < 0)
			return htmlText;
		int p2 = findTagPosition(htmlText, p1, "/body", true);
		if (p2 < 0)
			p2 = findTagPosition(htmlText, p1, "/html", true);
		return p2 > 0 ? htmlText.substring(p1, p2) : htmlText.substring(p1);
	}

	// <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	static public String setHtmlContentType(String html, String contentType) {
		if (html == null)
			return null;
		int p = findTagPosition(html, 0, "html", false);
		if (p > 0) {
			return html.substring(0, p)
					+ "<meta http-equiv=\"Content-Type\" content=\""
					+ contentType + "\" />" + html.substring(p);
		}
		return "<html><meta http-equiv=\"Content-Type\" content=\""
				+ contentType + "\" />" + html + "</html>";
	}

	static public String removeHtmlComment(String htmlText) {
		if (htmlText == null)
			return null;
		StringBuilder sb = new StringBuilder();
		int from = 0;
		for (;;) {
			int p = htmlText.indexOf("<!--", from);
			if (p < 0) {
				break;
			}
			int q = htmlText.indexOf("-->", p + 4);
			if (q < 0)
				break;
			// htmlText = htmlText.substring(0,p)+htmlText.substring(q+3);
			if (p > from)
				sb.append(htmlText.substring(from, p));
			from = q + 3;
		}
		if (from == 0)
			return htmlText;
		sb.append(htmlText.substring(from));
		return sb.toString();
	}

	/*
	 * "attr11 = v1 attr2 = v2 ..." nameCase: 1:toLowerCase, 2:toUpercase
	 */
	static public String[][] parseElementAttributes(String text,
			java.util.Map<String, String> to, int nameCase) {
		final int n = text.length(); // boolean inStr = false;
		int i0 = 0;
		java.util.List<String[]> v = new java.util.ArrayList<String[]>();
		for (;;) {
			int p = text.indexOf('=', i0);
			if (p < 0)
				break;
			String name = text.substring(i0, p).trim();
			String value;
			p++;
			for (; p < n && text.charAt(p) <= ' '; p++)
				;
			char c = p < n ? text.charAt(p) : 0;
			if (c == '"') {
				int q = text.indexOf('"', p + 1);
				if (q < 0) {
					break; // ERROR
				}
				value = text.substring(p + 1, q);
				i0 = q + 1;
			} else {
				int q = p;
				for (; q < n; q++) {
					c = text.charAt(q);
					if (c <= ' ' || c == '>' || c == '<')
						break;
				}
				value = text.substring(p, q);
				i0 = q + 1;
			}
			if (nameCase == 1)
				name = name.toLowerCase();
			else if (nameCase == 2)
				name = name.toUpperCase();
			if (to != null)
				to.put(name, value);
			v.add(new String[] { name, value });
		}
		return v.toArray(new String[v.size()][]);
	}

	static public String toJsString(String text, char delim)// ,String space)
	{

		StringBuilder sb = new StringBuilder();
		toJsString(sb, text, delim);
		return sb.toString();
	}

	static public void toJsString(final java.lang.Appendable sb, String text,
			char delim) {
		toJsString(sb, text, delim, null, 0);
	}

	/*
	 * opts-- #1 --> 0xa1 - 0xff : 使用 \xXX 编码
	 */
	static public void toJsString(final java.lang.Appendable sb, String text,
			char delim, String forecEncodeChars, int opts)// ,String space)
	{
		try {
			if (text == null) {
				sb.append("null");
				return;
			}
			sb.append(delim);
			final int n = text.length();
			for (int i = 0; i < n; i++) {
				char c = text.charAt(i);
				if (c == delim) {
					sb.append('\\');
				} else
					switch (c) {
					case '\\':
						sb.append('\\');
						break;
					case '\t': {
						sb.append('\\');
						c = 't';
					}
						break;
					case '\r':
						sb.append('\\');
						c = 'r';
						break;
					case '\n':
						sb.append('\\');
						c = 'n';
						break;
					default:
						if (forecEncodeChars != null
								&& forecEncodeChars.indexOf(c) >= 0
								|| ((opts & 1) != 0 && c >= 0x7f && c <= 0xff)) {
							if (c < 0xff) {
								int c1 = c >> 4, c2 = c & 0xf;
								sb.append("\\x")
										.append(c1 >= 10 ? (char) (c1 - 10 + 'A')
												: (char) (c1 + '0'))
										.append(c2 >= 10 ? (char) (c2 - 10 + 'A')
												: (char) (c2 + '0'));
								continue;
							} else {
								int c1 = (c >> 12) & 0xf, c2 = (c >> 8) & 0xf, c3 = (c >> 4) & 0xf, c4 = c & 0xf;
								sb.append("\\u")
										.append(c1 >= 10 ? (char) (c1 - 10 + 'A')
												: (char) (c1 + '0'))
										.append(c2 >= 10 ? (char) (c2 - 10 + 'A')
												: (char) (c2 + '0'))
										.append(c3 >= 10 ? (char) (c3 - 10 + 'A')
												: (char) (c3 + '0'))
										.append(c4 >= 10 ? (char) (c4 - 10 + 'A')
												: (char) (c4 + '0'));
								continue;
							}
						}
					}
				sb.append(c);
			}
			sb.append(delim);
		} catch (java.io.IOException ex) {
			throw com.hibo.bas.exception.HookedException.toRuntimeException(ex);
		}
	} // toJsString

	static String toString(byte code[], String charSet) {
		try {
			return new String(code, charSet);
		} catch (java.io.UnsupportedEncodingException ex) {
		}
		try {
			return new String(code, "8859_1");
		} catch (java.io.UnsupportedEncodingException ex) {
			throw com.hibo.bas.exception.HookedException.toRuntimeException(ex);
		}
	}

	static public String getContentTypeCharset(String contentType) {
		if (contentType == null)
			return null;
		String a[] = StrUtil.splitString(contentType, ';');
		if (a.length <= 1)
			return null;
		for (int j = 1; j < a.length; j++) {
			String s = a[j].trim();
			int p = s.indexOf('=');
			if (p < 0 || !s.substring(0, p).trim().equalsIgnoreCase("charset"))
				continue;
			s = s.substring(p + 1).trim();
			if (s.length() > 2 && s.charAt(0) == '"'
					&& s.charAt(s.length() - 1) == '"')
				s = s.substring(1, s.length() - 1);
			if ("GB2312".equalsIgnoreCase(s) || "x-gbk".equalsIgnoreCase(s))
				s = "GBK";
			return s;
		}
		return null;
	}

	static public String mimeBase64Decode(String text) {
		if (text == null)
			return null;
		if (text.startsWith("=?") && text.endsWith("?="))
			try {
				int p = text.indexOf("?B?");
				if (p > 0) {
					String charSet = text.substring(2, p);
					return toString(
							Base64Decode.decode(text.substring(p + 3,
									text.length() - 2)), charSet);
				}
				p = text.indexOf("?Q?");
				if (p > 0) {
					String charSet = text.substring(2, p);
					return toString(
							Base64Decode.decodeQ(text.substring(p + 3,
									text.length() - 2)), charSet);
				}
			} catch (Throwable ex) {
				throw com.hibo.bas.exception.HookedException
						.toRuntimeException(ex);
			}
		throw new java.lang.IllegalArgumentException(text);
	}

	static public java.util.Map parseMeta(String metaText) {
		java.util.Map v = new java.util.HashMap();
		final int n = metaText.length();
		for (;;) {
			int p = metaText.indexOf('=');
			if (p < 0)
				break;
			String name = metaText.substring(0, p).trim().toLowerCase();
			for (p++; p < n && metaText.charAt(p) <= ' '; p++)
				;
			if (p >= n)
				break;
			char c = metaText.charAt(p);
			if (c == '"' || c == '\'') {
				int q = metaText.indexOf(c, p + 1);
				if (q < 0)
					break;
				v.put(name, metaText.substring(p + 1, q).trim());
				metaText = metaText.substring(q + 1).trim();
			} else {
				int q = metaText.indexOf(' ', p + 1);
				if (q < 0) {
					v.put(name, metaText.substring(p).trim());
					break;
				} else {
					v.put(name, metaText.substring(p, q).trim());
					metaText = metaText.substring(q + 1).trim();
				}
			}
		}
		return v;
	}

	static public String getContentTypeFromMeta(byte a[]) {
		if (a == null)
			return null;
		final int n = a.length;
		for (int i = 0; i < n - 1; i++) {
			if (a[i] == '<' && a[i + 1] != '/') {
				int i0 = i + 1;
				for (; i < n && a[i] != '>'; i++)
					;
				if (i >= n)
					break;
				for (; i0 < n && (a[i0] & 0xff) <= ' '; i0++)
					;
				StringBuilder sb = new StringBuilder();
				for (; i0 < n
						&& ((a[i0] >= 'a' && a[i0] <= 'z') || (a[i0] >= 'A' && a[i0] <= 'Z')); i0++)
					sb.append((char) a[i0]);
				if ("meta".equalsIgnoreCase(sb.toString()))
					try {
						java.util.Map v = parseMeta(new String(a, i0, i - i0,
								"8859_1").trim());
						if (v != null
								&& "Content-Type".equalsIgnoreCase((String) v
										.get("http-equiv"))) {
							return (String) v.get("content");
						}
					} catch (Throwable ex) {
					}
			}
		}
		return null;
	}

	static public String hexEncode(String text) {
		if (text == null)
			return null;
		StringBuilder sb = new StringBuilder();
		final int n = text.length();
		for (int i = 0; i < n; i++) {
			char c = text.charAt(i);
			for (int j = 3; j >= 0; j--) {
				int x = (c >> (j * 4)) & 0xf;
				sb.append((char) (x < 10 ? '0' + x : 'A' + x - 10));
			}
		}
		return sb.toString();
	}

	static public String utf8HexEncode(String text) throws java.io.IOException {
		if (text == null)
			return null;
		byte a[] = text.getBytes("UTF-8");
		StringBuilder sb = new StringBuilder();
		final int n = a.length;
		for (int i = 0; i < n; i++) {
			int x = (a[i] >> 4) & 0xf;
			sb.append((char) (x < 10 ? '0' + x : 'A' + x - 10));
			x = a[i] & 0xf;
			sb.append((char) (x < 10 ? '0' + x : 'A' + x - 10));
		}
		return sb.toString();
	}

	static public String utf8HexDecode(String text) throws java.io.IOException {
		if (text == null)
			return null;
		final int n = text.length();
		if ((n & 1) != 0)
			throw new java.lang.IllegalArgumentException();
		byte a[] = new byte[n >> 1];// int nk = 0;
		for (int i = 0; i < a.length; i++) {
			// char c = 0;
			int c = 0;
			for (int j = 0; j < 2; j++) {
				int x = text.charAt(i * 2 + j);
				if (x >= '0' && x <= '9')
					x -= '0';
				else if (x >= 'A' && x <= 'F')
					x -= 'A' - 10;
				else if (x >= 'a' && x <= 'f')
					x -= 'a' - 10;
				else
					throw new java.lang.IllegalArgumentException(text);
				if (j == 0)
					c |= x << 4;
				else
					c |= x;
			}
			// sb.append(c);
			a[i] = (byte) c;
		}
		return new String(a, "UTF-8");
	}

	//
	static public void main(String args[]) {
		try {
			for (int i = 0; i < 0xffff; i++) {
				String s = "" + (char) i;// "asdd\"sssd汉字\r\n\00h#$%%^";
				String s2 = utf8HexEncode(s);
				System.out.println(s2 + ":" + utf8HexDecode(s2));
				if (!s.equals(utf8HexDecode(s2)))
					throw new RuntimeException();
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
}
