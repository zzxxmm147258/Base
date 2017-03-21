package com.hibo.cms.version.dao;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.version.model.VersionControlBas;

public interface VersionControlBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(VersionControlBas record);

    int insertSelective(VersionControlBas record);

    VersionControlBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VersionControlBas record);

    int updateByPrimaryKey(VersionControlBas record);
    
    /**
     * <p>功能：查询最新更新<p>
     * <p>创建日期：2016年5月9日 下午5:01:26<p>
     * <p>作者：曾小明<p>
     * @param type
     * @return
     */
    VersionControlBas selectByType(@Param("type")String type);
    
    PageList<VersionControlBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
}