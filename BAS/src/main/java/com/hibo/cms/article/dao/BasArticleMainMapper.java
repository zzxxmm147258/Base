package com.hibo.cms.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.article.model.BasArticleMain;

public interface BasArticleMainMapper {
    int deleteByPrimaryKey(String id);

    int insert(BasArticleMain record);

    int insertSelective(BasArticleMain record);

    BasArticleMain selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasArticleMain record);

    int updateByPrimaryKey(BasArticleMain record);
    
    PageList<BasArticleMain> selectPage(@Param("wStr")String wStr,PageBounds pageBounds);
    
    List<BasArticleMain> selectAll();
    
    List<BasArticleMain> selectByAll(@Param("wStr")String wStr);
    
    List<BasArticleMain> selectDic(@Param("dic")String dic);
}