package com.hibo.cms.article.model;

import java.util.Date;

import com.hibo.bas.model.ModelBas;
import com.hibo.bas.util.StrUtil;

public class BasArticle extends ModelBas{
    
	private static final long serialVersionUID = -5897231396032125358L;

    private String author;

    private Long hits;

    private Boolean isPublication;

    private Boolean isTop;

    private String seoDescription;

    private String seoKeywords;

    private String seoTitle;

    private String title;

    private String articleCategory;

    private String content;
    
    private String operator;
    
    private String url;
    
    private Date effectiveDate;
    
    private  String  category;
    
    private Boolean isUrl; 
    
    private String contentUrl;
    
    private Date activeDate;
    
    private Integer sort;
    
    private String attr1;
    
    private String attr2;
    
    private String btnName01;
    
    private String btnUrl01;
    
    private String attr3;
    
	private String btnName02;
    
    private String btnUrl02;
    
    private String effective_date;//辅助字段
    private Integer idx;//辅助字段

    //辅助字段
    private String matitle;
    private String articleId;
    private Date  activedate;
    private String active_date;
    private String  a_date;
    
    
    //新增字段
    private String attr4;
    private String attr5;
    private String attr6;
    private String attr7;
    private String attr8;
    private String attr9;
    private String attr10;
    private String attr11;

    
    
	public String getAttr4() {
		return attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public String getAttr5() {
		return attr5;
	}

	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	}

	public String getAttr6() {
		return attr6;
	}

	public void setAttr6(String attr6) {
		this.attr6 = attr6;
	}

	public String getAttr7() {
		return attr7;
	}

	public void setAttr7(String attr7) {
		this.attr7 = attr7;
	}

	public String getAttr8() {
		return attr8;
	}

	public void setAttr8(String attr8) {
		this.attr8 = attr8;
	}

	public String getAttr9() {
		return attr9;
	}

	public void setAttr9(String attr9) {
		this.attr9 = attr9;
	}

	public String getAttr10() {
		return attr10;
	}

	public void setAttr10(String attr10) {
		this.attr10 = attr10;
	}

	public String getAttr11() {
		return attr11;
	}

	public void setAttr11(String attr11) {
		this.attr11 = attr11;
	}

	public String getA_date() {
		return a_date;
	}

	public void setA_date(String a_date) {
		this.a_date = a_date;
	}

	public Date getActivedate() {
		return activedate;
	}

	public void setActivedate(Date activedate) {
		this.activedate = activedate;
	}

	public String getActive_date() {
		return active_date;
	}

	public void setActive_date(String active_date) {
		this.active_date = active_date;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getMatitle() {
		return matitle;
	}

	public void setMatitle(String matitle) {
		this.matitle = matitle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getIsUrl() {
		return isUrl;
	}

	public void setIsUrl(Boolean isUrl) {
		this.isUrl = isUrl;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public String getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public Boolean getIsPublication() {
        return isPublication;
    }

    public void setIsPublication(Boolean isPublication) {
        this.isPublication = isPublication;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription == null ? null : seoDescription.trim();
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords == null ? null : seoKeywords.trim();
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle == null ? null : seoTitle.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory == null ? null : articleCategory.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
		if(!StrUtil.isnull(attr2)){
			String[] v = attr2.split(";");
			if(v.length>1){
				btnUrl01 = v[1];
				btnName01 = v[0];
			}else{
				btnName01 = v[0];
			}
		}
	}

	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
		if(!StrUtil.isnull(attr3)){
			String[] v = attr3.split(";");
			if(v.length>1){
				btnUrl02 = v[1];
				btnName02 = v[0];
			}else{
				btnName02 = v[0];
			}
		}
	}

	public String getBtnName01() {
		return btnName01;
	}

	public void setBtnName01(String btnName01) {
		this.btnName01 = btnName01;
	}

	public String getBtnUrl01() {
		return btnUrl01;
	}

	public void setBtnUrl01(String btnUrl01) {
		this.btnUrl01 = btnUrl01;
	}

	public String getBtnName02() {
		return btnName02;
	}

	public void setBtnName02(String btnName02) {
		this.btnName02 = btnName02;
	}

	public String getBtnUrl02() {
		return btnUrl02;
	}

	public void setBtnUrl02(String btnUrl02) {
		this.btnUrl02 = btnUrl02;
	}
    
	
    
}