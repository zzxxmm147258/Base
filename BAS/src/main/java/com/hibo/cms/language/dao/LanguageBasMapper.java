package com.hibo.cms.language.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.language.model.LanguageBas;

public interface LanguageBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(LanguageBas record);

    int insertSelective(LanguageBas record);

    LanguageBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LanguageBas record);

    int updateByPrimaryKey(LanguageBas record);
    
    PageList<LanguageBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
    List<LanguageBas> selectAll();
 }