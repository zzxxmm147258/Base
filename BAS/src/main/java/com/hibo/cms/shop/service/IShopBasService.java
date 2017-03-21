package com.hibo.cms.shop.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.MallShop;
import com.hibo.cms.shop.model.ShopBas;
import com.hibo.cms.shop.model.ShopConditionBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午2:35:13</p>
 * <p>类全名：com.hibo.cms.shop.service.IShopBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IShopBasService {

	    /**
	     * <p>功能：根据商户主键shopId删除商户数据<p>
	     * <p>创建日期：2015年11月19日 下午2:58:27<p>
	     * <p>作者：曾小明<p>
	     * @param shopId
	     * @return
	     */
	    int deleteByPrimaryKey(String shopId);

	    /**
	     * <p>功能：不判空新增商户数据<p>
	     * <p>创建日期：2015年11月19日 下午3:01:54<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int insert(ShopBas record);

	    /**
	     * <p>功能：判空新增商户数据<p>
	     * <p>创建日期：2015年11月19日 下午3:02:26<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int insertSelective(ShopBas record);

	    /**
	     * <p>功能：根据商户主键shopId查找商户信息<p>
	     * <p>创建日期：2015年11月19日 下午3:02:53<p>
	     * <p>作者：曾小明<p>
	     * @param shopId
	     * @return
	     */
	    ShopBas selectByPrimaryKey(String shopId);

	    /**
	     * <p>功能：判空修改商户数据<p>
	     * <p>创建日期：2015年11月19日 下午3:03:38<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int updateByPrimaryKeySelective(ShopBas record);

	    /**
	     * <p>功能：不判空修改商户数据<p>
	     * <p>创建日期：2015年11月19日 下午3:04:26<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int updateByPrimaryKey(ShopBas record);
	    
	    /**
	     * <p>功能：分页查询 </p>
	     * <p>作者：吕康</p>
	     * <p>创建日期：2016年2月18日 下午5:02:59</p>
	     * @param shopCondition
	     * @param pageBounds
	     * @return
	     */
	    PageList<ShopBas> selectByCondition(ShopConditionBas shopCondition,PageBounds pageBounds);
	    
	    /**
	     * <p>功能： </p>
	     * <p>作者：吕康</p>
	     * <p>创建日期：2016年2月18日 下午5:02:48</p>
	     * @param shopCondition
	     * @return
	     */
	    PageList<ShopBas> selectByCondition(@Param("shopCondition")ShopConditionBas shopCondition);
	    /**
	     * <p>功能：查询所有商户<p>
	     * <p>创建日期：2015年11月19日 下午3:57:59<p>
	     * <p>作者：曾小明<p>
	     * @return
	     */
	    List<ShopBas> selectAll();
	    
	    /**
	     * <p>功能：通过城市名称查找这个城市的所有商户<p>
	     * <p>创建日期：2015年11月21日 下午5:29:40<p>
	     * <p>作者：曾小明<p>
	     * @param cityname
	     * @return
	     */
	    String selectByCity(String city,String shopType);
	    /**
	     * <p>功能：根据商户名称模糊查询<p>
	     * <p>创建日期：2015年11月23日 下午2:20:26<p>
	     * <p>作者：曾小明<p>
	     * @param shopname
	     * @return
	     */
	    List<ShopBas> selectFuzzy(String shopname);
	    /**
	     * <p>功能：修改商户的所有信息<p>
	     * <p>创建日期：2015年11月23日 下午5:09:18<p>
	     * <p>作者：曾小明<p>
	     * @param key
	     * @param record
	     * @return
	     */
	    int update(ShopBas key,ShopBas record);
	    
	    /**
	     * <p>功能：根据商户类型查询商圈城市<p>
	     * <p>创建日期：2015年11月24日 上午10:18:24<p>
	     * <p>作者：曾小明<p>
	     * @param shoptype
	     * @return
	     */
	    List<String> selectShopTypeCity(String shoptype);
	    
	    
	    /**
	     * <p>功能：根据用户ID查找对应的的商户<p>
	     * <p>创建日期：2015年12月9日 下午6:25:42<p>
	     * <p>作者：曾小明<p>
	     * @param userid
	     * @return
	     */
	    Map selectByUserId(String userid);
	    
	    /**
	     * <p>功能：查询用户的商户<p>
	     * <p>创建日期：2015年12月10日 上午9:57:37<p>
	     * <p>作者：曾小明<p>
	     * @param userid
	     * @return
	     */
	    List<ShopBas> selectByUserIdList(String userid);
	    
	    
	    /**
	     * <p>功能：查询店铺的详情，包括省份<p>
	     * <p>创建日期：2015年12月21日 上午9:21:13<p>
	     * <p>作者：曾小明<p>
	     * @param shopId
	     * @return
	     */
	    ShopBas selectByDetail(String shopId);
	    
	    /**
	     * <p>功能：分页查询<p>
	     * <p>创建日期：2016年2月19日 下午4:18:54<p>
	     * <p>作者：曾小明<p>
	     * @param map
	     * @param pageBounds
	     * @return
	     */
	    PageList<ShopBas> selectByPage(Map map,PageBounds pageBounds);
	    
	    
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

	    
	    /**
	     * <p>功能：根据城市查询店铺<p>
	     * <p>创建日期：2016年9月7日 下午1:59:40<p>
	     * <p>作者：曾小明<p>
	     * @param city
	     * @return
	     */
	    List<ShopBas> selectCity(String city);
}
