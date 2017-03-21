package com.hibo.cms.newarticle.dao;

import java.util.List;

import com.hibo.cms.newarticle.model.ArticleButtonBas;

public interface ArticleButtonBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArticleButtonBas record);

    int insertSelective(ArticleButtonBas record);

    ArticleButtonBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArticleButtonBas record);

    int updateByPrimaryKey(ArticleButtonBas record);
    
    int deleteBymId(String mId);
    
    List<ArticleButtonBas> selectBymId(String mId);
}