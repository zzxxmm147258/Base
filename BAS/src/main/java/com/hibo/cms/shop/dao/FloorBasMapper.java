package com.hibo.cms.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.FloorBas;

public interface FloorBasMapper {
    int deleteByPrimaryKey(String floorId);

    int insert(FloorBas record);

    int insertSelective(FloorBas record);

    FloorBas selectByPrimaryKey(String floorId);

    int updateByPrimaryKeySelective(FloorBas record);

    int updateByPrimaryKey(FloorBas record);
    
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年2月19日 上午9:38:12<p>
     * <p>作者：曾小明<p>
     * @param wStr
     * @param pageBounds
     * @return
     */
    PageList<FloorBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
    List<FloorBas> selectAll();
    
    List<FloorBas> selectAllAvailableByBuilding(String building);
    
    FloorBas selectByName(String floorId);
    
    List<FloorBas> selectAllName();
    
    /**
     * <p>功能：条件模糊查询所有的楼层<p>
     * <p>创建日期：2016年2月22日 下午2:10:50<p>
     * <p>作者：曾小明<p>
     * @param floorName
     * @return
     */
    List<FloorBas> selectByAllName(@Param("floorName")String floorName);
}