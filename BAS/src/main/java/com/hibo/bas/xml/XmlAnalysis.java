package com.hibo.bas.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hibo.bas.classFile.ClassFileUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月28日 下午5:01:53</p>
 * <p>类全名：com.hibo.bas.xml.XmlAnalysis</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class XmlAnalysis {

	public static List<Map<String, Object>> XmlIsTOMapList() {
		File file = ClassFileUtil.getFile("hibo/menu/mainMenu.xml");
		InputStream is = null;
		List<Map<String, Object>> list = null;
		try {
			is = new FileInputStream(file);
			XmlElement rootE = com.hibo.bas.xml.XmlHandler.parseXML(is);
			list = getWhileXmlElement(rootE, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String,Object>> getWhileXmlElement(XmlElement rootE,List<Map<String,Object>> list){
		if(null==list){
			list = new ArrayList<Map<String, Object>>();
		}
		if(null!=rootE){
				for (XmlElement wsE:rootE.subElements) {
					if("menu".equals(wsE.qName)){
						Map<String,Object> map = new HashMap<String,Object>();
						for (XmlElement wsE2:wsE.subElements) {
							if("children".equals(wsE2.qName)){
								List<Map<String,Object>> childrenList = getWhileXmlElement(wsE2, null);
								map.put("children", childrenList);
							}else{
								map.put(wsE2.qName, wsE2.value);
							}
						}
						list.add(map);
					}
				}
			}
		return list;
	}
}
