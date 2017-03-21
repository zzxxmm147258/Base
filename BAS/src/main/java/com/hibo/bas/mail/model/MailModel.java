package com.hibo.bas.mail.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月12日 下午7:22:02</p>
 * <p>类全名：com.hibo.bas.mail.model.MailModel</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class MailModel {

	private InternetAddress mailto;

	private String title;

	private String FromNikeName;

	private String ftl;
	
	private String text;
	
	private boolean textHtml;

	private String encoding = "UTF-8";
	
	private Date sentDate = new Date();
	
	private List<InternetAddress> ccList = new ArrayList<InternetAddress>();

	private List<Attachment> Attachment = new ArrayList<Attachment>();

	private Map<String, Object> map = new HashMap<String, Object>();

	public MailModel() {
	}

	public MailModel(InternetAddress mailto,String title, String text,boolean textHtml) {
		this.mailto = mailto;
		this.title = title;
		this.text=text;
		this.textHtml = textHtml;
	}
	
	public MailModel(InternetAddress mailto,String title, String ftl) {
		this.mailto = mailto;
		this.title = title;
		this.ftl=ftl;
	}
	
	public MailModel(String FromNikeName,InternetAddress mailto,String title, String ftl) {
		this.mailto = mailto;
		this.title = title;
		this.FromNikeName = FromNikeName;
		this.ftl=ftl;
	}

	public InternetAddress getMailto() {
		return mailto;
	}

	public void setMailto(InternetAddress mailto) {
		this.mailto = mailto;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFromNikeName() {
		return FromNikeName;
	}

	public void setFromNikeName(String fromNikeName) {
		FromNikeName = fromNikeName;
	}

	public String getFtl() {
		return ftl;
	}

	public void setFtl(String ftl) {
		this.ftl = ftl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isTextHtml() {
		return textHtml;
	}

	public void setTextHtml(boolean textHtml) {
		this.textHtml = textHtml;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public List<InternetAddress> getCcList() {
		return ccList;
	}

	public void setCcList(List<InternetAddress> ccList) {
		this.ccList = ccList;
	}

	public List<Attachment> getAttachment() {
		return Attachment;
	}

	public void setAttachment(List<Attachment> attachment) {
		Attachment = attachment;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
