package com.hibo.bas.util.page.taglib;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.github.miemiedev.mybatis.paginator.domain.Paginator;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月30日 下午2:21:51</p>
 * <p>类全名：com.hibo.cms.sys.dao.page.taglib.PageTaglib</p>
 * 作者：cuizhimin
 * 初审：
 * 复审：
 */
public class PageTaglib extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	// 分页链接
	private String url = "";
	
	// 分页参数名称，比方说page
	private String pageParamName = "page";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPageParamName() {
		if (StringUtils.isBlank(pageParamName)) {
			this.pageParamName = "page";
		}
		return pageParamName;
	}

	public void setPageParamName(String pageParamName) {
		this.pageParamName = pageParamName;
	}

	public int execute() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
			Paginator page = (Paginator) request.getAttribute(this.getPageParamName());
			if (page == null) {
				throw new NullPointerException("page 为null 。");
			}
			int pageSize = page.getLimit();
			StringBuilder sb = new StringBuilder();
			sb.append("<div style='text-align:center'><nav><ul class=\"pagination \">");
			if (page.getPage() == 1) {
				sb.append("<li class=\"disabled\"><a href=\"#\">首页</a></li>");
			}
			if (page.getPage() != 1)
				sb.append(generateUrl(pageSize,1, "首页"));
			if (page.isHasPrePage()) {
				sb.append(generateUrl(pageSize,page.getPage() - 1, "上一页"));
			} else {
				sb.append("<li class=\"disabled\"><a href=\"#\">上一页</a></li>");
			}
			List<Long> pageNums = PageNums.generateLinkPageNumbers(page.getPage(), page.getTotalPages(), 5);
			if (pageNums != null && pageNums.size() > 0) {
				int length = pageNums.size();
				for (int i = 0; i < length; i++) {
					Long j = pageNums.get(i);
					if (page.getPage() == j) {
						sb.append("<li class=\"active\"><a href=\"#\">" + j + "</a></li>");
					} else {
						sb.append(generateUrl(pageSize,j, j + ""));
					}
				}
			} else {
				sb.append("<li class=\"disabled\"><a href=\"#\">" + 1 + "</a></li>");
			}
			if (pageNums.size() < 1) {
				sb.append("<li class=\"disabled\"><a href=\"#\">1</a></li>");
//				for (int dec = (pageNums.size() < 1) ? 1 : 0; dec < 5 - pageNums.size(); dec++) {
//					sb.append("<li class=\"disabled\"><a href=\"#\">" + (pageNums.size() + dec + 1) + "</a></li>");
//				}
			}
			if (page.isHasNextPage()) {
				sb.append(generateUrl(pageSize, page.getPage() + 1, "下一页"));
			} else {
				sb.append("<li class=\"disabled\"><a href=\"#\">下一页</a></li>");
			}
			if (page.getPage() != page.getTotalPages() && page.getTotalPages() != page.getPage()
					&& page.getTotalPages() != 0) {
				sb.append(generateUrl(pageSize, page.getTotalPages(), "尾页"));
			} else {
				sb.append("<li class=\"disabled\"><a href=\"#\">尾页</a></li>");
			}
			sb.append("<li style='margin-left:10px;float: left;'><form method='post' action='" + request.getContextPath() + this.getUrl()+"'>"
					+ "每页<input type='text' name='pageSize' value='"+page.getLimit()+"' style='height:34px;width: 28px;color:#555;background-color:#fff;background-image:none;border:1px solid #ccc;border-radius:4px;'/>行，"
					+ "跳转到<input type='text' name='currentPage' value='"+page.getPage() +"'style='height:34px;width: 28px;color:#555;background-color:#fff;background-image:none;border:1px solid #ccc;border-radius:4px;'/>页"
					+ "<input type='hidden' name='totalPagesNumber' value='"+page.getTotalPages()+"'/><input type='button' class='btn btn-default' id='pageSelectGo' value='Go'/>"
					+ "</form></li></ul></nav></div>");
			sb.append("<script type='text/javascript'>  \n"
					
					+"$(function(){  \n"
					+"if($(\"#selectForm\").length!=0){  \n"
					+"	var html = '<input id=\"currentPage\" type=\"hidden\" name=\"currentPage\" value=\"1\"><input id=\"pageSize\" type=\"hidden\" name=\"pageSize\" value=\"10\">';  \n"
					+"    $(\"#selectForm\").append(html);  \n"   
					+"	$(\"nav li a\").click(function(){  \n"
					+"		var currentPage = $(this).attr(\"page\");  \n"
					+"		var pageSize = $(\"nav input[name=pageSize]\").val();  \n"
					+"		$(\"#currentPage\").val(currentPage);  \n"
					+"		$(\"#pageSize\").val(pageSize);  \n"
					+"		$(\"#selectForm\").submit();  \n"
					+"		return false;  \n"
					+"	})  \n"
					+"}  \n"
					+"});  \n"
					+"function pageFormSubmit(){  \n"
					+"		var pageSize = $(\"nav input[name=pageSize]\").val();  \n"
					+"		var currentPage = $(\"nav input[name=currentPage]\").val();  \n"
					+"		var totalPagesNumber = $(\"nav input[name=totalPagesNumber]\").val();  \n"
					+"		if(eval(currentPage)>eval(totalPagesNumber)){  \n"
					+"			currentPage = totalPagesNumber;  \n"
					+"		}  \n"
					+"		$(\"#currentPage\").val(currentPage);  \n"
					+"		$(\"#pageSize\").val(pageSize);  \n"
					+"		$(\"#selectForm\").submit();  \n"
					+"		return false;  \n"
					+"}	  \n"
					+"  $(\"#pageSelectGo\").click(function(){\n"
					+"		pageFormSubmit();\n"
					+"	});\n"
					+"</script>");
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

	private String generateUrl(int pageSize, long currentPage, String title, String css) {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		StringBuilder sb = new StringBuilder();
		sb.append("<li class='" + css + "'><a page='" + currentPage + "' href='");
		sb.append(request.getContextPath());
		sb.append(this.getUrl());
		if (this.getUrl().indexOf("?") == -1) {
			sb.append("?currentPage=");
			sb.append(currentPage);
			sb.append("&pageSize=");
		} else {
			sb.append("&currentPage=");
			sb.append(currentPage);
			sb.append("&pageSize=");
		}
		sb.append(pageSize);
		sb.append("'>" + title + " </a></li>");
		return sb.toString();
	}

	private String generateUrl(int pageSize,long currentPage, String title) {
		return this.generateUrl(pageSize, currentPage, title, "");
	}
}
