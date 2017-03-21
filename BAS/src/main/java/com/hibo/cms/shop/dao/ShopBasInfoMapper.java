package com.hibo.cms.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.shop.model.ShopBasInfo;

public interface ShopBasInfoMapper {
	
    int deleteByShopId(String shopId);

    int insert(ShopBasInfo shopBasInfo);

    int insertSelective(ShopBasInfo shopBasInfo);

    List<ShopBasInfo> selectListByIdAndType(@Param("shopId")String shopId,@Param("showtype")String showtype);
    
    List<ShopBasInfo> selectListByShopId(String shopId);

    int updateByShopIdSelective(ShopBasInfo shopBasInfo);

    int updateByShopId(ShopBasInfo shopBasInfo);
    
    ShopBasInfo selectByPrimaryKey(String id);
    
    List<ShopBasInfo> selectByShopId(String shopId);
    
    int deleteId(String id);
    
    int update(ShopBasInfo shopBasInfo);
}