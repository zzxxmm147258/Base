package com.hibo.cms.articlenew.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.articlenew.model.BasArticleButton;

public interface BasArticleButtonMapper {

	int deleteByPrimaryKey(String id);
	int deleteByArticleId(@Param("articleId")String ArticleId,@Param("num")int num);

    int insert(BasArticleButton record);

    int insertSelective(BasArticleButton record);

    BasArticleButton selectByPrimaryKey(String id);
    List<BasArticleButton> selectByArticleId(String articleId);

    int updateByPrimaryKeySelective(BasArticleButton record);

    int updateByPrimaryKey(BasArticleButton record);
}