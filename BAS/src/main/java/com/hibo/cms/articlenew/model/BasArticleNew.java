package com.hibo.cms.articlenew.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.model.ModelBas;

public class BasArticleNew extends ModelBas{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4072615208035488211L;

	private String author;

    private String title;

    private Boolean isUrl;

    private String outerUrl;

    private MultipartFile iconFile;
    private String icon;

    private String iconSmall;

    private String iconOther;
    private String iconOtherSize;

    private MultipartFile imgFile;
    private String img;

    private String imgSmall;

    private String imgOther;
    private String imgOtherSize;

    private Long hits;

    private Boolean isPublication;

    private Boolean isTop;


    private String effectiveDates;
    private Date effectiveDate;

    private String activeDates;
    
    private Date activeDate;

    private String operator;

    private String categorygId;

    private String categoryId;

    private Integer sort;

    private String viewType;

    private Boolean isTools;

    private String returnType;
    private String btnName01;
    private MultipartFile btnIcon01;
    private String btnIconUrl01;
    private String btnIconSmall01;
    private Integer btnLoc01;
    private String btnUrl01;
    private String btnName02;
    private MultipartFile btnIcon02;
    private String btnIconUrl02;
    private String btnIconSmall02;
    private Integer btnLoc02;
    private String btnUrl02;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;

    private String attr5;

    private String attr6;

    private String attr7;

    private String attr8;

    private String attr9;

    private String attr10;

    private String content;

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

	public String getEffectiveDates() {
		return effectiveDates;
	}

	public void setEffectiveDates(String effectiveDates) {
		this.effectiveDates = effectiveDates;
	}

	public String getActiveDates() {
		return activeDates;
	}

	public void setActiveDates(String activeDates) {
		this.activeDates = activeDates;
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

    public String getCategorygId() {
        return categorygId;
    }

    public void setCategorygId(String categorygId) {
        this.categorygId = categorygId == null ? null : categorygId.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
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

	
	public MultipartFile getIconFile() {
		return iconFile;
	}

	public void setIconFile(MultipartFile iconFile) {
		this.iconFile = iconFile;
	}

	public MultipartFile getImgFile() {
		return imgFile;
	}

	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}

	public String getIconOtherSize() {
		return iconOtherSize;
	}

	public void setIconOtherSize(String iconOtherSize) {
		this.iconOtherSize = iconOtherSize;
	}

	public String getImgOtherSize() {
		return imgOtherSize;
	}

	public void setImgOtherSize(String imgOtherSize) {
		this.imgOtherSize = imgOtherSize;
	}

	public MultipartFile getBtnIcon01() {
		return btnIcon01;
	}

	public void setBtnIcon01(MultipartFile btnIcon01) {
		this.btnIcon01 = btnIcon01;
	}

	public MultipartFile getBtnIcon02() {
		return btnIcon02;
	}

	public void setBtnIcon02(MultipartFile btnIcon02) {
		this.btnIcon02 = btnIcon02;
	}

	public String getBtnIconSmall01() {
		return btnIconSmall01;
	}

	public void setBtnIconSmall01(String btnIconSmall01) {
		this.btnIconSmall01 = btnIconSmall01;
	}

	public String getBtnIconSmall02() {
		return btnIconSmall02;
	}

	public void setBtnIconSmall02(String btnIconSmall02) {
		this.btnIconSmall02 = btnIconSmall02;
	}

	public String getBtnIconUrl01() {
		return btnIconUrl01;
	}

	public void setBtnIconUrl01(String btnIconUrl01) {
		this.btnIconUrl01 = btnIconUrl01;
	}

	public String getBtnIconUrl02() {
		return btnIconUrl02;
	}

	public void setBtnIconUrl02(String btnIconUrl02) {
		this.btnIconUrl02 = btnIconUrl02;
	}

	public String getBtnName01() {
		return btnName01;
	}

	public void setBtnName01(String btnName01) {
		this.btnName01 = btnName01;
	}

	public String getBtnName02() {
		return btnName02;
	}

	public void setBtnName02(String btnName02) {
		this.btnName02 = btnName02;
	}

	

	public Integer getBtnLoc01() {
		return btnLoc01;
	}

	public void setBtnLoc01(Integer btnLoc01) {
		this.btnLoc01 = btnLoc01;
	}

	public Integer getBtnLoc02() {
		return btnLoc02;
	}

	public void setBtnLoc02(Integer btnLoc02) {
		this.btnLoc02 = btnLoc02;
	}

	public String getBtnUrl01() {
		return btnUrl01;
	}

	public void setBtnUrl01(String btnUrl01) {
		this.btnUrl01 = btnUrl01;
	}

	public String getBtnUrl02() {
		return btnUrl02;
	}

	public void setBtnUrl02(String btnUrl02) {
		this.btnUrl02 = btnUrl02;
	}

	
	
    
}