package com.hibo.bas.constant;

import java.io.Serializable;

import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.hibo.bas.basenum.MessageCodeEnum;
import com.hibo.cms.util.Envparam;

/** 
 * <p>标题：提示信息</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年1月20日 上午11:19:58</p>
 * <p>类全名：com.hibo.bas.constant.Message</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 7857188843832143509L;

	private boolean success;
	
	private boolean noLogin = !Envparam.isLogin();
	
	private boolean bool;

	private MessageCodeEnum code;
	
	private String message;
	
	private Object datas;
	
	private String title;
	
	private String url;
	
	private Paginator page;
	
	public Message() {
	}
	
	public Message(boolean success) {
		this.success = success;
	}
	
	public Message(boolean success,MessageCodeEnum code) {
		this.success = success;
		this.code = code;
	}

	public Message(boolean success,String message) {
		this.success = success;
		this.message = message;
	}
	
	public Message(boolean success,boolean noLogin, String message) {
		this.success = success;
		this.noLogin = noLogin;
		this.message = message;
	}
	
	public Message(boolean success,Object datas) {
		this.success = success;
		this.datas = datas;
	}
	
	public Message(boolean success,MessageCodeEnum code,String message) {
		this.success = success;
		this.message = message;
		this.code = code;
	}
	
	public Message(boolean success,String message,Object datas) {
		this.success = success;
		this.message = message;
		this.datas = datas;
	}
	
	public Message(boolean success,MessageCodeEnum code,String message,Object datas) {
		this.success = success;
		this.message = message;
		this.datas = datas;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isNoLogin() {
		return noLogin;
	}

	public void setNoLogin(boolean noLogin) {
		this.noLogin = noLogin;
	}

	public MessageCodeEnum getCode() {
		return code;
	}

	public void setCode(MessageCodeEnum code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDatas() {
		return datas;
	}

	public void setDatas(Object datas) {
		this.datas = datas;
	}

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Paginator getPage() {
		return page;
	}

	public void setPage(Paginator page) {
		this.page = page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
