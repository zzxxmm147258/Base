package com.hibo.cms.quartz.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.quartz.model.QuartzUpdateBas;

public interface QuartzUpdateBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(QuartzUpdateBas record);

    int insertSelective(QuartzUpdateBas record);

    QuartzUpdateBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuartzUpdateBas record);

    int updateByPrimaryKey(QuartzUpdateBas record);
    
    PageList<QuartzUpdateBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
    Date getDatebyId(@Param("id")String id);
    
    int updateDatebyId(@Param("id")String id, @Param("updateDate")Date updateDate);
    
    int getLocked(@Param("id")String id);
    
    int releaseLocked(@Param("id")String id);
}