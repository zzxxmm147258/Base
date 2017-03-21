package com.hibo.cms.component.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.component.model.CountryBas;

public interface CountryBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(CountryBas record);

    int insertSelective(CountryBas record);

    CountryBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CountryBas record);

    int updateByPrimaryKey(CountryBas record);
    
    PageList<CountryBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);

    List<String> selectAll();
    
    List<CountryBas> selectList(@Param("title")String title);
}