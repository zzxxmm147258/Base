package com.hibo.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Resource;

public interface ResourceMapper {
    int delete(String resourceid);

    int insert(Resource resource);

    Resource select(String resourceid);

    int update(@Param("resource")Resource resource,@Param("oldresourceid")String oldresourceid);
    
    PageList<Resource> selectAll(PageBounds pageBounds);
    
    List<Resource> selectAll();
    
    List<Resource> selectFuzzy(Resource resource);

}