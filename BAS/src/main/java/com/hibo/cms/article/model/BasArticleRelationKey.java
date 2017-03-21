package com.hibo.cms.article.model;

public class BasArticleRelationKey {
    private String articleId;

    private String articleMainId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId == null ? null : articleId.trim();
    }

    public String getArticleMainId() {
        return articleMainId;
    }

    public void setArticleMainId(String articleMainId) {
        this.articleMainId = articleMainId == null ? null : articleMainId.trim();
    }
}