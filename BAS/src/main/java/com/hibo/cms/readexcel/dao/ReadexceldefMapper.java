package com.hibo.cms.readexcel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.readexcel.model.Readexceldef;

public interface ReadexceldefMapper {
    int delete(String rxcode);

    int insert(Readexceldef readexceldef);

    Readexceldef select(String rxcode);

    int update(Readexceldef readexceldef);
    
    int updateByOldrxcode(@Param("rdexdef")Readexceldef readexceldef,@Param("oldrxcode")String oldrxcode);
    
    List<Readexceldef> selectAll();

}