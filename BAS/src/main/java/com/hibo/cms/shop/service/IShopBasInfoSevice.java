package com.hibo.cms.shop.service;

import java.util.List;

import com.hibo.cms.shop.model.ShopBasInfo;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月30日 下午2:26:00</p>
 * <p>类全名：com.hibo.cms.shop.service.IShopBasInfoSevice</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public interface IShopBasInfoSevice {
	
    int deleteByShopId(String shopId);

    int insert(ShopBasInfo shopBasInfo);

    int insertSelective(ShopBasInfo shopBasInfo);

    List<ShopBasInfo> selectListByIdAndType(String shopId,String showtype);
    
    List<ShopBasInfo> selectListByShopId(String shopId);

    int updateByShopIdSelective(ShopBasInfo shopBasInfo);

    int updateByShopId(ShopBasInfo record);
    
    ShopBasInfo selectByPrimaryKey(String id);
    
    List<ShopBasInfo> selectByShopId(String shopId);
    
    int deleteId(String id);
    
    int update(ShopBasInfo shopBasInfo);
}
