package com.hibo.cms.config.dao;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.config.model.Sysoptions;

public interface SysoptionsMapper {
    int deleteByPrimaryKey(String conno);

    int insert(Sysoptions record);

    int insertSelective(Sysoptions record);

    int updateByPrimaryKeySelective(Sysoptions record);

    int updateByPrimaryKey(Sysoptions record);

	Sysoptions selectByPrimaryKey(String id);

	Sysoptions selectByconno(String conno);

	PageList<Sysoptions> selectSysoptionsList(PageBounds pageBounds);
	
	PageList<Sysoptions> findByTwoType(@Param("wStr")String wStr,PageBounds pageBounds);

	Sysoptions findByEname(String ename);

	Sysoptions findByType(String type);
}