package com.hibo.cms.articlenew.dao;

import java.util.List;
import java.util.Map;

import com.hibo.cms.articlenew.model.BasArticleNew;

public interface BasArticleNewMapper {


	int deleteByPrimaryKey(String id);

    int insert(BasArticleNew record);

    int insertSelective(BasArticleNew record);

    BasArticleNew selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasArticleNew record);

    int updateByPrimaryKeyWithBLOBs(BasArticleNew record);

    int updateByPrimaryKey(BasArticleNew record);

	List<BasArticleNew> selectPaginition(Map map);

	Integer selectCount(Map map);
}