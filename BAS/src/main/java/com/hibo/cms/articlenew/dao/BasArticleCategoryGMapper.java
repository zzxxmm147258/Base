package com.hibo.cms.articlenew.dao;

import java.util.List;

import com.hibo.cms.articlenew.model.BasArticleCategoryG;

public interface BasArticleCategoryGMapper {

	int deleteByPrimaryKey(String id);

    int insert(BasArticleCategoryG record);

    int insertSelective(BasArticleCategoryG record);

    BasArticleCategoryG selectByPrimaryKey(String id);
    List<BasArticleCategoryG> selectAll();
    List<BasArticleCategoryG> selectByLcode( String lcode);
    int updateByPrimaryKeySelective(BasArticleCategoryG record);

    int updateByPrimaryKey(BasArticleCategoryG record);
}