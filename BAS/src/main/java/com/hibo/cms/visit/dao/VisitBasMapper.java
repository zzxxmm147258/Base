package com.hibo.cms.visit.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.visit.model.VisitBas;

public interface VisitBasMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(VisitBas record);

    int insertSelective(VisitBas record);

    VisitBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VisitBas record);

    int updateByPrimaryKey(VisitBas record);
    
	Map<String,Object> selectDataMap(@Param("sql")String sql);
	
    List<LinkedHashMap<String,Object>> selectDataList(@Param("sql")String sql);
    
}