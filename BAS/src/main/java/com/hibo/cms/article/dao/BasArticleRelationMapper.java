package com.hibo.cms.article.dao;

import java.util.List;

import com.hibo.cms.article.model.BasArticleRelation;
import com.hibo.cms.article.model.BasArticleRelationKey;

public interface BasArticleRelationMapper {
    int deleteByPrimaryKey(BasArticleRelationKey key);

    int insert(BasArticleRelation record);

    int insertSelective(BasArticleRelation record);

    BasArticleRelation selectByPrimaryKey(BasArticleRelationKey key);

    int updateByPrimaryKeySelective(BasArticleRelation record);

    int updateByPrimaryKey(BasArticleRelation record);
    
    List<BasArticleRelation> selectAll();
    
    /**
     * <p>功能：根据主表查询<p>
     * <p>创建日期：2016年3月1日 下午3:04:51<p>
     * <p>作者：曾小明<p>
     * @param articleMainId
     * @return
     */
    List<BasArticleRelation> selectArticleMainId(String articleMainId);
    
    /**
     * <p>功能：根据父表删除字表<p>
     * <p>创建日期：2016年3月1日 下午3:16:56<p>
     * <p>作者：曾小明<p>
     * @param articleMainId
     * @return
     */
    int deleteArticleMainId(String articleMainId);
}