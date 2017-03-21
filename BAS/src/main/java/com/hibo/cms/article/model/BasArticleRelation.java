package com.hibo.cms.article.model;

public class BasArticleRelation extends BasArticleRelationKey {
    private Boolean isTop;

    private Integer idx;

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }
}