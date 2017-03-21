package com.hibo.bas.mail.service;

import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hibo.bas.mail.model.Attachment;
import com.hibo.bas.mail.model.MailModel;
import com.hibo.bas.util.StrUtil;

import freemarker.template.Template;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月13日 下午1:50:10</p>
 * <p>类全名：com.hibo.bas.mail.service.MailSend</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Service
public class MailSend {
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SimpleMailMessage simpleMailMessage;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * 内部线程类，利用线程池异步发邮件
	 */
	private class SendMailThread implements Runnable {

		private MimeMessage message;

		private SendMailThread(MimeMessage message) {
			super();
			this.message = message;
		}

		@Override
		public void run() {
			javaMailSender.send(message);
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            收件人邮箱
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @throws Exception 
	 */
	public void sendMail(MailModel mailModel) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, mailModel.getEncoding());
		messageHelper.setFrom(new InternetAddress(simpleMailMessage.getFrom(), mailModel.getFromNikeName()));
		if (StrUtil.isnull(mailModel.getTitle())) {
			messageHelper.setSubject(simpleMailMessage.getSubject());
		} else {
			messageHelper.setSubject(mailModel.getTitle());
		}
		messageHelper.setTo(mailModel.getMailto());
		List<InternetAddress> cclist = mailModel.getCcList();
		for (InternetAddress cc : cclist) {
			messageHelper.addCc(cc);
		}
		messageHelper.setSentDate(mailModel.getSentDate());
		String ftl = mailModel.getFtl();
		if (!StrUtil.isnull(ftl)) {
			// 从FreeMarker模板生成邮件内容
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftl);
			// 模板中用${x}站位，map中key为x的value会替换占位符内容。
			mailModel.setText(FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getMap()));
			mailModel.setTextHtml(true);
		}
		messageHelper.setText(mailModel.getText(), mailModel.isTextHtml());
		List<Attachment> attachmentList = mailModel.getAttachment();
		for (Attachment atta : attachmentList) {
			messageHelper.addAttachment(atta.getFileName(), atta.getData());
		}
		SendMailThread task = new SendMailThread(message);
		this.taskExecutor.execute(task);
	}
}
