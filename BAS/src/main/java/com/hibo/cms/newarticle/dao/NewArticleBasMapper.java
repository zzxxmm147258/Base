package com.hibo.cms.newarticle.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.newarticle.model.NewArticleBas;

public interface NewArticleBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(NewArticleBas record);

    int insertSelective(NewArticleBas record);

    NewArticleBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NewArticleBas record);

    int updateByPrimaryKeyWithBLOBs(NewArticleBas record);

    int updateByPrimaryKey(NewArticleBas record);
    
    PageList<NewArticleBas> selectPage(@Param("wStr")String wStr,PageBounds pageBounds);
    
    PageList<NewArticleBas> selectYicPage(@Param("wStr")String wStr,PageBounds pageBounds);
    
    List<NewArticleBas> selectByCategroy(@Param("categoryId")String categoryId,@Param("categorygId")String categorygId);
    
    List<NewArticleBas> selectByPage(@Param("categoryId")String categoryId,@Param("categorygId")String categorygId,PageBounds pageBounds);

    List<NewArticleBas> selectByCategorygId(@Param("categorygId")String categorygId,PageBounds pageBounds);

    List<NewArticleBas> selectByShopId(@Param("shopId")String shopId);

    
}