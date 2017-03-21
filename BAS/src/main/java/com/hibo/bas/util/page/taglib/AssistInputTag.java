package com.hibo.bas.util.page.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.servlet.tags.form.InputTag;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月21日 下午4:05:19</p>
 * <p>类全名：com.hibo.bas.util.page.taglib.TextareaTag</p>
 * 作者：tozhimin
 * 初审：
 * 复审：
 */
public class AssistInputTag extends InputTag {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3211034250910977900L;
	
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int execute() throws JspException {
		try {
			/*TagWriter tagWriter = new TagWriter(this.pageContext);
			tagWriter.startTag("div");
			tagWriter.writeAttribute("class", "error");
			tagWriter.startTag("input");
			tagWriter.writeOptionalAttributeValue("class", "form-control");
			tagWriter.writeOptionalAttributeValue("value", "cuizhimin");
			tagWriter.endTag();
			tagWriter.endTag();*/
			StringBuilder sb = new StringBuilder();
			sb.append("<div class='modal fade' id='modals' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'>"
					+ "<div class='modal-dialog' role='document'>"
					+ "<div class='modal-content'>"
					+ "<div class='modal-header'>"
					+ "<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
					+ "	<h4 class='modal-title' id='myModalLabel'>正在努力加载中。。。</h4>"
					+ "</div></div></div></div>");
			this.pageContext.getOut().append(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return this.execute();
	}
}
