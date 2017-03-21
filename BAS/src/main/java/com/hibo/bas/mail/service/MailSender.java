package com.hibo.bas.mail.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月27日 下午2:44:19</p>
 * <p>类全名：com.hibo.bas.service.MailSender</p>
 * 作者：zhimin
 * 初审：
 * 复审：
 */
@Service
public class MailSender {

	@Autowired	
    private JavaMailSender javaMailSender;
	
	@Autowired
    private SimpleMailMessage simpleMailMessage;
	
	@Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	
	public SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    /**
     * 构建邮件内容，发送邮件。
     * @param user  用户
     * @param url   链接
     */
	public void send(String email, String ftl, Map<String, Object> map) {
		
		//String nickname = user.getUsername();
		String text = "";
		try {
			// 从FreeMarker模板生成邮件内容
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftl);
			// 模板中用${XXX}站位，map中key为XXX的value会替换占位符内容。
			text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		SendMailThread task = new SendMailThread(email, null, text);
		this.taskExecutor.execute(task);
		//this.taskExecutor.getThreadPoolExecutor().remove(task);
	}
    
    /**
     * 内部线程类，利用线程池异步发邮件
     */
	private class SendMailThread implements Runnable {
		
		private String to;
		private String subject;
		private String content;

		private SendMailThread(String to, String subject, String content) {
			super();
			this.to = to;
			this.subject = subject;
			this.content = content;
		}
		
		@Override
		public void run() {
			sendMail(to, subject, content);
		}
	}
    
    /**
     * 发送邮件
     * @param to        收件人邮箱
     * @param subject   邮件主题
     * @param content   邮件内容
     */
	public void sendMail(String to, String subject, String content) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(simpleMailMessage.getFrom());
			if (subject != null) {
				messageHelper.setSubject(subject);
			} else {
				messageHelper.setSubject(simpleMailMessage.getSubject());
			}
			messageHelper.setTo(to);
			messageHelper.setText(content, true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		} 
	}
}
