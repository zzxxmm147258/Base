package com.hibo.cms.newarticle.model;

import java.util.Date;

import com.hibo.bas.model.ModelBas;

public class NewArticleBas extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7968876908617069198L;

	private String author;

    private String title;
    
    private String entitle;

    private Boolean isUrl;

    private String outerUrl;

    private String icon;

    private String iconSmall;

    private String iconOther;

    private String img;

    private String imgSmall;

    private String imgOther;

    private String buttonId;

    private Long hits;

    private Boolean isPublication;

    private Boolean isTop;

    private Date effectiveDate;

    private Date activeDate;

    private String operator;

    private String categoryId;

    private String categorygId;

    private Integer sort;

    private String viewType;

    private Boolean isTools;

    private String returnType;

    private String attr1;
    
    private String attr2;
    
     private String btnName01;
    
    private String btnUrl01;

    private String attr3;
    
    private String btnName02;
    
    private String btnUrl02;

    private String attr4;

    private String attr5;

    private String attr6;

    private String attr7;

    private String attr8;

    private String attr9;

    private String attr10;

    private String content;
    
    private String shopId;

    private String effective_date;//辅助字段
    private Integer idx;//辅助字段
    private String matitle;//辅助字段
    private String articleId;//辅助字段
    private Date  activedate;//辅助字段
    private String active_date;//辅助字段
    private String  a_date;//辅助字段
    private String[] imgs;//辅助字段
    
    
    public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String[] getImgs() {
		return imgs;
	}

	public void setImgs(String[] imgs) {
		this.imgs = imgs;
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

	public String getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public String getMatitle() {
		return matitle;
	}

	public void setMatitle(String matitle) {
		this.matitle = matitle;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
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

	public String getA_date() {
		return a_date;
	}

	public void setA_date(String a_date) {
		this.a_date = a_date;
	}

	public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Boolean getIsUrl() {
        return isUrl;
    }

    public void setIsUrl(Boolean isUrl) {
        this.isUrl = isUrl;
    }

    public String getOuterUrl() {
        return outerUrl;
    }

    public void setOuterUrl(String outerUrl) {
        this.outerUrl = outerUrl == null ? null : outerUrl.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getIconSmall() {
        return iconSmall;
    }

    public void setIconSmall(String iconSmall) {
        this.iconSmall = iconSmall == null ? null : iconSmall.trim();
    }

    public String getIconOther() {
        return iconOther;
    }

    public void setIconOther(String iconOther) {
        this.iconOther = iconOther == null ? null : iconOther.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall == null ? null : imgSmall.trim();
    }

    public String getImgOther() {
        return imgOther;
    }

    public void setImgOther(String imgOther) {
        this.imgOther = imgOther == null ? null : imgOther.trim();
    }

    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId == null ? null : buttonId.trim();
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


    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getCategorygId() {
        return categorygId;
    }

    public void setCategorygId(String categorygId) {
        this.categorygId = categorygId == null ? null : categorygId.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
    }

    public Boolean getIsTools() {
        return isTools;
    }

    public void setIsTools(Boolean isTools) {
        this.isTools = isTools;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1 == null ? null : attr1.trim();
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2 == null ? null : attr2.trim();
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3 == null ? null : attr3.trim();
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4 == null ? null : attr4.trim();
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5 == null ? null : attr5.trim();
    }

    public String getAttr6() {
        return attr6;
    }

    public void setAttr6(String attr6) {
        this.attr6 = attr6 == null ? null : attr6.trim();
    }

    public String getAttr7() {
        return attr7;
    }

    public void setAttr7(String attr7) {
        this.attr7 = attr7 == null ? null : attr7.trim();
    }

    public String getAttr8() {
        return attr8;
    }

    public void setAttr8(String attr8) {
        this.attr8 = attr8 == null ? null : attr8.trim();
    }

    public String getAttr9() {
        return attr9;
    }

    public void setAttr9(String attr9) {
        this.attr9 = attr9 == null ? null : attr9.trim();
    }

    public String getAttr10() {
        return attr10;
    }

    public void setAttr10(String attr10) {
        this.attr10 = attr10 == null ? null : attr10.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getEntitle() {
		return entitle;
	}

	public void setEntitle(String entitle) {
		this.entitle = entitle;
	}
}