package com.hibo.cms.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.DistrictBas;

public interface DistrictBasMapper {
    int deleteByPrimaryKey(String district);

    int insert(DistrictBas record);

    int insertSelective(DistrictBas record);

    DistrictBas selectByPrimaryKey(String district);

    int updateByPrimaryKeySelective(DistrictBas record);

    int updateByPrimaryKey(DistrictBas record);
    
    PageList<DistrictBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
    List<DistrictBas> selectAll();
    
    List<DistrictBas> selectAllAvailable();
    
    /**
     * <p>功能：根据编号，名称，城市查询<p>
     * <p>创建日期：2016年5月18日 上午10:11:43<p>
     * <p>作者：曾小明<p>
     * @param district
     * @param disname
     * @param city
     * @return
     */
    List<DistrictBas> selectBy(@Param("district")String district,@Param("disname")String disname,@Param("city")String city);
    
}