package com.hibo.bas.xml;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.hibo.bas.exception.HookedException;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月10日 下午7:13:14</p>
 * <p>类全名：com.hibo.bas.xml.XmlElement</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
final public class XmlElement {
	final public String qName, localName, uri;
	public String value;
	XmlElement parentNode;

	// final public int index; // 在 parent 中的
	public XmlElement(XmlElement parentNode, String uri, String localName,
			String qName)// ,int index)
	{
		this.parentNode = parentNode;
		this.uri = uri;
		this.qName = qName;
		this.localName = localName;
		// this.index = index;
	}

	public XmlElement(XmlElement parentNode, String tagName) {
		this.parentNode = parentNode;
		this.uri = null;
		this.qName = tagName;
		this.localName = null;
	}

	public XmlElement(String uri, String localName, String qName)// ,int index)
	{
		// this.parentNode = parentNode;
		this.uri = uri;
		this.qName = qName;
		this.localName = localName;
		// this.index = index;
	}

	public XmlElement(String qName) {
		this.qName = qName;
		this.uri = null;
		this.localName = null;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// public org.xml.sax.Attributes attributes;
	// private String attrQNames[], attrValues[];
	final java.util.Map<String, String> attrs = new java.util.LinkedHashMap<String, String>();

	public java.util.Map<String, String> getAttributes() {
		return attrs;
	}

	public XmlElement getParentNode() {
		return parentNode;
	}

	public String getAttribute(String name) {
		return (String) attrs.get(name);
	}

	public String getAttribute(String name, String defaultValue) {
		String v = (String) attrs.get(name);
		return v == null ? defaultValue : v;
	}

	public int getAttributeAsInt(String name, int val) {
		String v = (String) attrs.get(name);
		if (v == null || v.length() == 0)
			return val;
		return Integer.decode(v);
	}

	public String getAttribute(String names[]) {
		for (int i = 0; i < names.length; i++) {
			String s = attrs.get(names[i]);
			if (s != null)
				return s;
		}
		return null;
	}

	public void setAttribute(String name, String value) {
		if (value == null)
			attrs.remove(name);
		else
			attrs.put(name, value);
	}

	public void setAttributes(java.util.Map<String, String> attrs) {
		this.attrs.putAll(attrs);
	}

	public int getAttributeLength() {
		return attrs.size();
		// return attrQNames==null ? 0 : attrQNames.length;//.getLength();
	}

	public String[] getAttributeNames() {
		java.util.Set<String> i = attrs.keySet();
		return i.toArray(new String[i.size()]);

		// return attrQNames;
	}

	public final java.util.List<XmlElement> subElements = new java.util.ArrayList<XmlElement>();

	public XmlElement[] getSubElements() {
		return subElements.size() == 0 ? null : subElements
				.toArray(new XmlElement[subElements.size()]);
	}

	public XmlElement getSubElement(String qname) {
		return getSubElement(qname, subElements);
	}

	// public XmlElement getSubElement(String qnames[])
	// {
	// return getSubElement(qnames,0);
	// }
	// private XmlElement getSubElement(String qnames[],int idx)
	// {
	// XmlElement e = getSubElement(qnames[idx]);
	// return idx<qnames.length-1 && e!=null ? e.getSubElement(qnames,idx+1) :
	// null;
	// }
	public XmlElement getSubElement(String qnames[]) {
		return getSubElement(qnames, 0, false);
	}

	public XmlElement getSubElement(String qnames[], boolean addIfNull) {
		return getSubElement(qnames, 0, addIfNull);
	}

	private XmlElement getSubElement(String qnames[], int idx, boolean addIfNull) {
		XmlElement e = getSubElement(qnames[idx]);
		if (e == null && addIfNull) {
			subElements.add(e = new XmlElement(qName));
		}
		return idx < qnames.length - 1 && e != null ? e.getSubElement(qnames,
				idx + 1, addIfNull) : e;
	}

	public void addSubElement(XmlElement e) {
		e.parentNode = this;
		subElements.add(e);
	}

	static private XmlElement getSubElement(String qname,
			java.util.List<XmlElement> elements) {
		if (elements == null || qname == null)
			return null;
		for (int i = 0; i < elements.size(); i++) {
			XmlElement e = elements.get(i);// (XmlElement)elements.elementAt(i);
			if (qname.equalsIgnoreCase(e.qName))
				return e;
		}
		return null;
	}

	/*
	 * static private String getSubElementValue(String
	 * qname,java.util.List<XmlElement> elements) { XmlElement e =
	 * getSubElement(qname,elements); return e==null ? null : e.value; }
	 */
	public void list(java.io.PrintStream out, int baseIndent, int indent,
			boolean onlyLGT) {
		list(out, baseIndent, indent, onlyLGT ? 3 : 0xffff);
	}

	/*
	 * escapeChas#1 :
	 */
	public void list(java.io.PrintStream out, int baseIndent, int indent,
			int escapeChas) {
		// StringBuffer sIndent = new StringBuffer();
		for (int i = 0; i < baseIndent; i++)
			out.print(' ');
		out.print("<" + qName);
		if (localName != null && ((String) localName).length() > 0)
			out.print(" name=\"" + localName + "\"");
		for (final String name : this.attrs.keySet())
		// if( attrQNames!=null ) for(int i=0;i<attrQNames.length;i++)
		{
			out.print(" " + name + "=\""
					+ xmlEscape(attrs.get(name), escapeChas) + "\"");
			// out.print(" "+attrQNames[i]+"=\""+xmlEscape(attrValues[i],onlyLGT)+"\"");
		}
		int nSub = subElements.size();
		if (nSub == 0) {
			if (value != null && value.length() > 0)
				out.println(">" + xmlEscape(value, escapeChas) + "</" + qName
						+ ">");
			else
				out.println("/>");
		} else {
			out.println(">");
			if (value != null && value.length() > 0) {
				for (int i = 0; i < baseIndent + (indent < 0 ? 2 : indent); i++)
					out.print(' ');
				out.println(value);
			}
			for (int i = 0; i < nSub; i++)
				(subElements.get(i)).list(out, baseIndent
						+ (indent < 0 ? 2 : indent), indent, escapeChas);

			for (int i = 0; i < baseIndent; i++)
				out.print(' ');
			out.println("</" + qName + ">");
		}
	}

	// snsoft.util.HtmlUtil.xmlEscapeChars
	// &#13;&#10;
	// static String xmlEscapeChars =
	// snsoft.util.HtmlUtil.xmlEscapeChars;//"\"&<>"; //"'\"&<>";
	// static String xmlEscapeText[] =
	// snsoft.util.HtmlUtil.xmlEscapeText;//{"&quot;","&amp;","&lt;","&gt;"};
	public final static String xmlEscapeChars = "<>&\"'\r\n";// \"&<>";
	public final static String xmlEscapeText[] = { "&lt;", "&gt;", "&amp;",
			"&quot;", "&apos;", "&#13;", "&#10;" };

	/*
	 * escapeChas#1 : 转换 > #2 转换 < #4 : 转换 & #8 : 转换 " #0x10 : 转换 ' #0x20 : \r
	 * #0x40 : \n
	 */
	public static String xmlEscape(String s, int escapeChas) // boolean onlyLGT)
	{
		if (s == null)
			return s;
		StringBuilder sb = new StringBuilder();
		int ne = 0;
		int l = s.length();
		for (int i = 0; i < l; i++) {
			char c = s.charAt(i);
			int j = xmlEscapeChars.indexOf(c, 0);// onlyLGT?xmlEscapeChars.length()-2:0);
			if (j < 0 || (escapeChas & (1 << j)) == 0)
				sb.append(c);
			else {
				sb.append(xmlEscapeText[j]);
				ne++;
			}
		}
		return ne == 0 ? s : sb.toString();
	}

	public String toString(int indent) {
		return toString(indent, false);
	}

	public String toString(int indent, boolean onlyLGT) {
		java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
		list(new java.io.PrintStream(os), 0, indent, onlyLGT ? 3 : 0xffff);
		return new String(os.toByteArray());
	}

	@Override
	public String toString() {
		return toString(2);
	}

	public String toString(boolean onlyLGT) {
		return toString(2, onlyLGT);
	}

	public static void list(java.io.PrintStream out, XmlElement[] elements,
			int baseIndent, int indent, boolean onlyLGT) {
		if (elements == null)
			return;
		for (int i = 0; i < elements.length; i++) {
			elements[i].list(out, baseIndent, indent, onlyLGT);
		}
	}

	public static void list(java.io.PrintStream out, XmlElement e,
			boolean onlyLGT) {
		if (e != null)
			e.list(out, 0, 2, onlyLGT);
	}

	public static void list(java.io.PrintStream out, XmlElement e,
			int escapeChas) {
		if (e != null)
			e.list(out, 0, 2, escapeChas);
	}

	public static void list(java.io.PrintStream out, XmlElement[] elements,
			boolean onlyLGT) {
		list(out, elements, 0, 2, onlyLGT);
	}

	public static void list(java.io.PrintStream out, XmlElement[] elements) {
		list(out, elements, 0, 2, false);
	}

	public static String toString(XmlElement[] elements, boolean onlyLGT) {
		java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
		list(new java.io.PrintStream(os), elements, 0, 2, onlyLGT);
		return new String(os.toByteArray());
	}

	public void visit(XmlElementVisitor visitor) {
		visit(visitor, null);
	}

	void visit(XmlElementVisitor visitor, XmlElement parent[]) {
		if (visitor.visitXmlElement(this, parent) || this.subElements == null)
			return;
		XmlElement p[] = new XmlElement[(parent == null ? 0 : parent.length) + 1];
		if (p.length > 1)
			System.arraycopy(parent, 0, p, 0, parent.length);
		p[p.length - 1] = this;
		for (final XmlElement e : subElements) {
			e.visit(visitor, p);
		}
	}

	abstract public static interface XmlElementFilter // implements
														// XmlElementFilter
	{
		public XmlElement addElementIfNoMatched(XmlElement parent);

		public boolean filterAccept(XmlElement e);
	}

	public static class ScriptFilter implements XmlElementFilter {
		/*
		 * 标签
		 */
		final public String tagName;

		public ScriptFilter(String tagName) {
			this.tagName = tagName;
		}

		public ScriptFilter(String tagName, String filterScript) {
			this.tagName = tagName;
			this.filterScript = filterScript;
		}

		public ScriptFilter(String tagName, String filterScript,
				boolean addIfNoMatch) {
			this.tagName = tagName;
			this.filterScript = filterScript;
			this.addIfNoMatch = addIfNoMatch;
		}

		/*
		 * 过滤条件： JavaScrpt 语法
		 */
		public String filterScript;

		public boolean addIfNoMatch;

		@Override
		public String toString() {
			return filterScript == null ? tagName : tagName + "["
					+ filterScript + "]";
		}

		@Override
		public XmlElement addElementIfNoMatched(XmlElement parent) {
			if (addIfNoMatch) {
				XmlElement e = new XmlElement(this.tagName);
				parent.addSubElement(e);
				return e;
			}
			return null;
		}

		@Override
		public boolean filterAccept(XmlElement e) {
			if (tagName != null && !tagName.equals(e.qName)) {
				return false;
			}
			if (filterScript != null) {
				ScriptEngineManager sem = new ScriptEngineManager();
				ScriptEngine se = sem.getEngineByName("javascript");
				try {
					if (e.parentNode != null) {
						int i = e.parentNode.subElements.indexOf(e);
						if (i >= 0)
							se.put("__index__", i);
					}
					for (String name : e.getAttributes().keySet()) {
						se.put(name.replace(':', '_'), e.getAttribute(name));
					}
					Object v = se.eval(this.filterScript);
					if (!(v instanceof Boolean) || !(Boolean) v)
						return false;
				} catch (Throwable ex) {
					throw HookedException.toRuntimeException(ex);
				}
			}
			return true;
		}
	}

	public XmlElement[] getElements(XmlElementFilter[] path) {
		if (path == null || path.length == 0) {
			return new XmlElement[] { this };
		}
		final java.util.List<XmlElement> list = new java.util.ArrayList<>();
		this.filterElements(list, path, 0);
		return list.size() == 0 ? null : list.toArray(new XmlElement[list
				.size()]);
	}

	private void filterElements(java.util.List<XmlElement> to,
			XmlElementFilter[] path, int jPath) {
		if (jPath >= path.length || this.subElements == null)
			return;
		final XmlElementFilter filter = path[jPath]; 
		final java.util.List<XmlElement> list = new java.util.ArrayList<>();
		for (int i = 0; i < this.subElements.size(); i++) {
			final XmlElement e = this.subElements.get(i);
			if (filter.filterAccept(e)) {
				list.add(e);// return e;
			}
		}
		if (list.size() == 0) {
			XmlElement e = filter.addElementIfNoMatched(this);
			if (e != null)
				list.add(e);
		}
		if (jPath == path.length - 1) {
			to.addAll(list);
		} else
			for (XmlElement e : list) {
				e.filterElements(to, path, jPath + 1);
			}
	}

	public void saveTo(java.io.File file, String charset)
			throws java.io.IOException {
		try (java.io.PrintStream out = new java.io.PrintStream(file, charset);// "UTF-8");
		) {
			out.println("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
			list(out, 0, 2, 0xffff);
		}
	}

	public void saveTo(java.io.File file) throws java.io.IOException {
		saveTo(file, "UTF-8");
	}

	// static public void main(String args[])
	// {
	// XmlElement e = new XmlElement("","a","b");
	// e.setValue("swor<dm/>an'\"&k");
	// list(System.out, new XmlElement[]{e},false);
	// list(System.out, new XmlElement[]{e},true);
	// System.out.println(e.toString(false));
	// System.out.println(e.toString(true));
	// }
}
