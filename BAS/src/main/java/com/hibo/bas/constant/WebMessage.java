package com.hibo.bas.constant;
import java.io.Serializable;
/**
 * <p>标题：提示信息</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年1月20日 上午11:18:46</p>
 * <p>类全名：com.hibo.bas.constant.WebMessage</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class WebMessage implements Serializable{

	private static final long serialVersionUID = -5107424091833857801L;
	
	private int header = 0;
	
	private String title = "提示";
	
	private String message;
	
	private String okBtn;
	
	private String cancleBtn;
	
	private String oKUrl;

	private String cancleUrl;

	public WebMessage() {
	}
	
	public WebMessage(String message,String okBtn,String oKUrl) {
		this(message, okBtn, oKUrl, 0);
	}
	
	public WebMessage(String message,String okBtn,String oKUrl,int header) {
		this.message = message;
		this.okBtn = okBtn;
		this.oKUrl = oKUrl;
		this.header = header;
	}
	
	public WebMessage(String title,String message,String okBtn,String oKUrl) {
		this(title, message, okBtn, oKUrl, 0);
	}
	
	public WebMessage(String title,String message,String okBtn,String oKUrl,int header) {
		this.title = title;
		this.message = message;
		this.okBtn = okBtn;
		this.oKUrl = oKUrl;
		this.header = header;
	}
	
	public WebMessage(String title,String message,String okBtn,String oKUrl,String cancleBtn,String cancleUrl) {
		this(title, message, okBtn, oKUrl, cancleBtn, cancleUrl, 0);
	}
	
	public WebMessage(String title,String message,String okBtn,String oKUrl,String cancleBtn,String cancleUrl,int header) {
		this.title = title;
		this.message = message;
		this.okBtn = okBtn;
		this.oKUrl = oKUrl;
		this.cancleBtn = cancleBtn;
		this.cancleUrl = cancleUrl;
		this.header = header;
	}
	
	public int getHeader() {
		return header;
	}

	public void setHeader(int header) {
		this.header = header;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOkBtn() {
		return okBtn;
	}

	public void setOkBtn(String okBtn) {
		this.okBtn = okBtn;
	}

	public String getCancleBtn() {
		return cancleBtn;
	}

	public void setCancleBtn(String cancleBtn) {
		this.cancleBtn = cancleBtn;
	}

	public String getoKUrl() {
		return oKUrl;
	}

	public void setoKUrl(String oKUrl) {
		this.oKUrl = oKUrl;
	}

	public String getCancleUrl() {
		return cancleUrl;
	}

	public void setCancleUrl(String cancleUrl) {
		this.cancleUrl = cancleUrl;
	}
	
	
}
