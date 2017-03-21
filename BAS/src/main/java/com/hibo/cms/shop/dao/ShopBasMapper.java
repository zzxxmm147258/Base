package com.hibo.cms.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.MallShop;
import com.hibo.cms.shop.model.ShopBas;
import com.hibo.cms.shop.model.ShopConditionBas;

public interface ShopBasMapper {
    int deleteByPrimaryKey(String shopId);

    int insert(ShopBas record);

    int insertSelective(ShopBas record);

    ShopBas selectByPrimaryKey(String shopId);

    int updateByPrimaryKeySelective(ShopBas record);

    int updateByPrimaryKey(ShopBas record);
    
    PageList<ShopBas> selectByCondition(@Param("shopCondition")ShopConditionBas shopCondition,PageBounds pageBounds);
    
    PageList<ShopBas> selectByCondition(@Param("shopCondition")ShopConditionBas shopCondition);
    
    List<ShopBas> selectAll();
    
    List<ShopBas> selectByCity(@Param("city")String city,@Param("shopType")String shopType);
    
    List<ShopBas> selectCity(@Param("city")String city);
    
    List<ShopBas> selectFuzzy(@Param("shopname")String shopname);
    
    int update(@Param("key") ShopBas key,@Param("record") ShopBas record);
    
    List<String> selectShopTypeCity(@Param("shoptype")String shoptype);
    
    List<ShopBas> selectByUserId(@Param("userid")String userid);
    
    /**
     * <p>功能：查询店铺的详情，包括省份<p>
     * <p>创建日期：2015年12月21日 上午9:21:13<p>
     * <p>作者：曾小明<p>
     * @param shopId
     * @return
     */
    ShopBas selectByDetail(@Param("shopId")String shopId);
    
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年2月19日 下午4:18:13<p>
     * <p>作者：曾小明<p>
     * @param wStr
     * @param pageBounds
     * @return
     */
    PageList<ShopBas> selectByPage(@Param("wStr")String wStr,PageBounds pageBounds);
    
    
    /**
     * <p>功能：根据楼层查询有用的商户<p>
     * <p>创建日期：2016年5月18日 上午11:09:11<p>
     * <p>作者：曾小明<p>
     * @param floorId
     * @return
     */
    List<ShopBas> selectAllAvailableByFloor(String floorId);

    
    /**
     * <p>功能：按各种添加查询商户<p>
     * <p>创建日期：2016年5月18日 下午3:24:57<p>
     * <p>作者：曾小明<p>
     * @param shop
     * @return
     */
    List<ShopBas> selectByShop(MallShop shop);
}