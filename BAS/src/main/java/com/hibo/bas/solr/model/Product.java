package com.hibo.bas.solr.model;

import java.util.HashMap;

import com.hibo.bas.model.ModelBas;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月6日 下午1:12:57</p>
 * <p>类全名：com.hibo.bas.solr.model.Product</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
public class Product extends ModelBas {
	private String name;
	private HashMap<String,Object> hashMap;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<String, Object> getHashMap() {
		return hashMap;
	}
	public void setHashMap(HashMap<String, Object> hashMap) {
		this.hashMap = hashMap;
	}
	@Override
	public String toString() {
		return "Product [id="+ super.getId() +",name=" + name + ", hashMap=" + hashMap + "]";
	}
	
}
