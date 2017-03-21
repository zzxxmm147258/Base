package com.hibo.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Operationcode;

public interface OperationcodeMapper {
    int delete(String operaid);

    int insert(Operationcode operationcode);

    Operationcode select(String operaid);

    int update(@Param("operationcode")Operationcode operationcode,@Param("oldoperaid")String oldoperaid);
    
    PageList<Operationcode> selectAll(PageBounds pageBounds);
    
    List<Operationcode> selectAll();
    
    List<Operationcode> selectFuzzy(Operationcode operationcode);
}