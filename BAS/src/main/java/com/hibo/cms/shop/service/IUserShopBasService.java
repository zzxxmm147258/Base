package com.hibo.cms.shop.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.UserShopBas;
import com.hibo.cms.shop.model.UserShopBasKey;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午2:35:34</p>
 * <p>类全名：com.hibo.cms.shop.service.IUserShopBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IUserShopBasService {

	    /**
	     * <p>功能：根据联合主键删除用户商户信息<p>
	     * <p>创建日期：2015年11月19日 下午3:05:06<p>
	     * <p>作者：曾小明<p>
	     * @param key
	     * @return
	     */
	    int deleteByPrimaryKey(UserShopBasKey key);

	    /**
	     * <p>功能：不判空新增用户商户数据<p>
	     * <p>创建日期：2015年11月19日 下午3:05:52<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int insert(UserShopBas record);

	    /**
	     * <p>功能：判空新增用户商户数据<p>
	     * <p>创建日期：2015年11月19日 下午3:05:52<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int insertSelective(UserShopBas record);

	    /**
	     * <p>功能：根据联合主键查询用户商户信息<p>
	     * <p>创建日期：2015年11月19日 下午3:06:36<p>
	     * <p>作者：曾小明<p>
	     * @param key
	     * @return
	     */
	    UserShopBas selectByPrimaryKey(UserShopBasKey key);

	    /**
	     * <p>功能：判空修改用户商户信息<p>
	     * <p>创建日期：2015年11月19日 下午3:07:04<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int updateByPrimaryKeySelective(UserShopBas record);

	    /**
	     * <p>功能：不判空修改用户商户信息<p>
	     * <p>创建日期：2015年11月19日 下午3:07:04<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int updateByPrimaryKey(UserShopBas record);
	    
	    /**
	     * <p>功能：分页查找<p>
	     * <p>创建日期：2015年11月19日 下午3:07:32<p>
	     * <p>作者：曾小明<p>
	     * @param map
	     * @param pageBounds
	     * @return
	     */
	    PageList<UserShopBas> selectByCondition(Map map,PageBounds pageBounds);
	    
	    /**
	     * <p>功能：修改用户商户<p>
	     * <p>创建日期：2015年11月19日 下午5:14:22<p>
	     * <p>作者：曾小明<p>
	     * @param key
	     * @param record
	     * @return
	     */
	    int update(UserShopBasKey key,UserShopBas record);
	    /**
	     * <p>功能：删除商户时删除用户商户关联记录<p>
	     * <p>创建日期：2015年12月9日 下午6:13:26<p>
	     * <p>作者：曾小明<p>
	     * @param shopId
	     * @return
	     */
	    int deleteByShopId(String shopId);
}
