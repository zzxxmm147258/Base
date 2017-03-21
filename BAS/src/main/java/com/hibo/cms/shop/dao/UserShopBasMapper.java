package com.hibo.cms.shop.dao;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.UserShopBas;
import com.hibo.cms.shop.model.UserShopBasKey;

public interface UserShopBasMapper {
    int deleteByPrimaryKey(UserShopBasKey key);

    int insert(UserShopBas record);

    int insertSelective(UserShopBas record);

    UserShopBas selectByPrimaryKey(UserShopBasKey key);

    int updateByPrimaryKeySelective(UserShopBas record);

    int updateByPrimaryKey(UserShopBas record);
    
    PageList<UserShopBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
    int update(@Param("key") UserShopBasKey key,@Param("record") UserShopBas record);
    
    int deleteByShopId(String shopId);
    
    int deleteByUserId(String userId);

}