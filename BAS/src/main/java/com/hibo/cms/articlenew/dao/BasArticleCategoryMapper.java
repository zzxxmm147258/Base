package com.hibo.cms.articlenew.dao;

import java.util.List;

import com.hibo.cms.articlenew.model.BasArticleCategory;

public interface BasArticleCategoryMapper {

	int deleteByPrimaryKey(String id);

    int insert(BasArticleCategory record);

    int insertSelective(BasArticleCategory record);

    BasArticleCategory selectByPrimaryKey(String id);
    List<BasArticleCategory> selectAll();

    int updateByPrimaryKeySelective(BasArticleCategory record);

    int updateByPrimaryKey(BasArticleCategory record);
}