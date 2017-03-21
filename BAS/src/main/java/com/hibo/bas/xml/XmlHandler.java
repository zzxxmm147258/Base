package com.hibo.bas.xml;

import java.io.InputStream;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.hibo.bas.util.StrUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月10日 下午7:10:32</p>
 * <p>类全名：com.hibo.bas.xml.XmlHandler</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
/*
	java.net.URL url = com.hibo.bas.util.ResUtil.urlFromResource("res:ui/UIAttrs.xml");
	snsoft.xml.XmlElement rootE = snsoft.xml.XmlHandler.parseXML(url.toString());
*/
public class XmlHandler extends DefaultHandler {
	final Stack<XmlElement> stack = new Stack<XmlElement>(); // XmlElememt

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		final XmlElement e = stack.pop();
		if (e.value != null) {
			e.value = e.value.trim();
			if (e.value.length() == 0)
				e.value = null;
		}
	}

	static public void putAttributes(org.xml.sax.Attributes attributes,
			java.util.Map to) {
		if (attributes != null) {
			int n = attributes.getLength();
			for (int i = 0; i < n; i++) {
				to.put(attributes.getQName(i), attributes.getValue(i));
			}
		}
	}

	XmlElement rootElement;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		XmlElement parent = stack.isEmpty() ? null : stack.peek();
		XmlElement e = new XmlElement(parent, uri, localName, qName);
		putAttributes(attributes, e.attrs);
		if (parent != null)
			parent.subElements.add(e);
		else {
			if (rootElement != null)
				throw new java.lang.IllegalArgumentException();
			rootElement = e;
		}
		stack.push(e);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (length > 0) {
			XmlElement e = stack.peek();
			String value = new String(ch, start, length);// .trim();
			if (e.value == null)
				e.value = value;
			else
				e.value += value;
		}
	}

	static public XmlElement parseXML(String uri)
			throws org.xml.sax.SAXException,
			javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		javax.xml.parsers.SAXParserFactory saxParserFactory = javax.xml.parsers.SAXParserFactory
				.newInstance();
		javax.xml.parsers.SAXParser saxParser = saxParserFactory.newSAXParser();
		XmlHandler xmlHandler = new XmlHandler();
		saxParser.parse(uri, xmlHandler);
		return xmlHandler.rootElement;
	}

	/*
	 * <Response><logisticProviderID>YTO</logisticProviderID><success>false</success
	 * ><reason>S01,非法的XML格式 (Illegal XML format)</reason></Response>
	 */
	public static XmlElement parseXML(InputStream is)
			throws org.xml.sax.SAXException,
			javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		javax.xml.parsers.SAXParserFactory saxParserFactory = javax.xml.parsers.SAXParserFactory
				.newInstance();
		javax.xml.parsers.SAXParser saxParser = saxParserFactory.newSAXParser();
		XmlHandler xmlHandler = new XmlHandler();
		saxParser.parse(is, xmlHandler);
		return xmlHandler.rootElement;
	}

	public static XmlElement parseXML(byte data[])
			throws org.xml.sax.SAXException,
			javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		return parseXML(new java.io.ByteArrayInputStream(data));
	}

	public static XmlElement parseXML(java.io.File file)
			throws org.xml.sax.SAXException,
			javax.xml.parsers.ParserConfigurationException, java.io.IOException {
		try (final java.io.InputStream is = new java.io.FileInputStream(file)) {
			return parseXML(is);
		}
	}

	static public int getXmlMetasPos(byte data[]) {
		int p0 = 0;
		final int n = data.length;
		for (;;) {
			for (; p0 < n - 3 && data[p0] <= ' '; p0++)
				;
			if (p0 >= n - 3 || data[p0] != '<' || data[p0 + 1] != '?')
				return p0;
			int q = StrUtil.indexOf(data, p0 + 2, new byte[] { '?', '>' });
			if (q < 0)
				return p0;
			p0 = q + 2;
		}
	}
}
