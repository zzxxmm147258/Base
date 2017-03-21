package com.hibo.bas.coderule.model;

import java.io.Serializable;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月19日 下午1:52:53</p>
 * <p>类全名：com.hibo.bas.coderule.model.CodeModel</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class CodeModel implements Serializable{

	private static final long serialVersionUID = 2274015484530473196L;
	
	private String table;//表
	
	private String fild;//字段
	
	private String filter;//条件
	
	private CodeRule[] codeRule = new CodeRule[0];//规则

	public CodeModel() {
	}
	
	public CodeModel(String table,String fild,String filter,CodeRule[] codeRule) {
		this.table = table;
		this.fild = fild;
		this.filter = filter;
		this.codeRule = codeRule;
	}
	
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getFild() {
		return fild;
	}

	public void setFild(String fild) {
		this.fild = fild;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public CodeRule[] getCodeRule() {
		return codeRule;
	}

	public void setCodeRule(CodeRule[] codeRule) {
		this.codeRule = codeRule;
	}
	
	
}
