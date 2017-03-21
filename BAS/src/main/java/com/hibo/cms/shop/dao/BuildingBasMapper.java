package com.hibo.cms.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.BuildingBas;

public interface BuildingBasMapper {
    int deleteByPrimaryKey(String buildingId);

    int insert(BuildingBas record);

    int insertSelective(BuildingBas record);

    BuildingBas selectByPrimaryKey(String buildingId);

    int updateByPrimaryKeySelective(BuildingBas record);

    int updateByPrimaryKey(BuildingBas record);
    
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年2月19日 上午9:38:12<p>
     * <p>作者：曾小明<p>
     * @param wStr
     * @param pageBounds
     * @return
     */
    PageList<BuildingBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
    List<BuildingBas> selectAll();
    
    List<BuildingBas> selectAllAvailableByBlock(String block);
    
    BuildingBas selectByName(String buildingId);
    
    List<BuildingBas> selectAllName();
}