package com.hibo.cms.article.model;

import java.util.Date;
import java.util.List;

import com.hibo.bas.model.ModelBas;

public class BasArticleMain extends ModelBas{
	
	private static final long serialVersionUID = -5897231396032125358L;

    private String articleCategory;

    private Integer status;

    private Date effectiveDate;

    private Date dateFrom;

    private Date dateTo;

    private String title;


    private String operator;
    
    private  String  category;
    
    List<BasArticle> basArticle;
    

    private String date_from;//辅助字段

    private String date_to;//辅助字段
    
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDate_from() {
		return date_from;
	}

	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}

	public String getDate_to() {
		return date_to;
	}

	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}


	public List<BasArticle> getBasArticle() {
		return basArticle;
	}

	public void setBasArticle(List<BasArticle> basArticle) {
		this.basArticle = basArticle;
	}

	public String getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory == null ? null : articleCategory.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }
}