package com.hibo.bas.coderule.model;

import java.io.Serializable;

import com.hibo.bas.coderule.nume.CodeRuleNume;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月19日 下午1:56:19</p>
 * <p>类全名：com.hibo.bas.coderule.model.CodeRule</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class CodeRule implements Serializable{
	
	private static final long serialVersionUID = -4348078191942171740L;

	private String value;
	
	private int num;
	
	private CodeRuleNume type;
	
	public CodeRule() {
	}
	
	public CodeRule(String value,int num,CodeRuleNume type) {
		this.value = value;
		this.num = num;
		this.type = type;
	}
	
	public CodeRule(int num,CodeRuleNume type) {
		this.num = num;
		this.type = type;
	}
	
	public CodeRule(String value,CodeRuleNume type) {
		this.value = value;
		this.type = type;
	}
	
	public CodeRule(CodeRuleNume type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public CodeRuleNume getType() {
		return type;
	}

	public void setType(CodeRuleNume type) {
		this.type = type;
	}

	
}
