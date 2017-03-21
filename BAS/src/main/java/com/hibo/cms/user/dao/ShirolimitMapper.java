package com.hibo.cms.user.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Shirolimit;

public interface ShirolimitMapper {
    int delete(String id);

    int insert(Shirolimit shirolimit);

    Shirolimit select(String id);
    
    List<Shirolimit> selectAllList();
    
    PageList<Shirolimit> selectAll(PageBounds pageBounds);

    int update(Shirolimit shirolimit);
}