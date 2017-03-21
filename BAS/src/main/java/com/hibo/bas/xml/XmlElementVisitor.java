package com.hibo.bas.xml;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月10日 下午7:16:27</p>
 * <p>类全名：com.hibo.bas.xml.XmlElementVisitor</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public interface XmlElementVisitor 
{
	/*
	  return true : 将不再继续访问 子节点。
	 */
	public boolean visitXmlElement(XmlElement e,XmlElement parent[] );
}
