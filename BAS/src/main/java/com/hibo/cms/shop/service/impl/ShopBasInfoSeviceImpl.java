package com.hibo.cms.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hibo.cms.shop.dao.ShopBasInfoMapper;
import com.hibo.cms.shop.model.ShopBasInfo;
import com.hibo.cms.shop.service.IShopBasInfoSevice;
/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月30日 下午2:27:11</p>
 * <p>类全名：com.hibo.cms.shop.service.impl.ShopBasInfoSeviceImpl</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Service
public class ShopBasInfoSeviceImpl implements IShopBasInfoSevice {

	@Resource
	private ShopBasInfoMapper shopBasInfoMapper;
	
	@Override
	public int deleteByShopId(String shopId) {
		return shopBasInfoMapper.deleteByShopId(shopId);
	}

	@Override
	public int insert(ShopBasInfo shopBasInfo) {
		return shopBasInfoMapper.insert(shopBasInfo);
	}

	@Override
	public int insertSelective(ShopBasInfo shopBasInfo) {
		return shopBasInfoMapper.insertSelective(shopBasInfo);
	}

	@Override
	public List<ShopBasInfo> selectListByIdAndType(String shopId, String showtype) {
		return shopBasInfoMapper.selectListByIdAndType(shopId, showtype);
	}

	@Override
	public List<ShopBasInfo> selectListByShopId(String shopId) {
		return shopBasInfoMapper.selectListByShopId(shopId);
	}

	@Override
	public int updateByShopIdSelective(ShopBasInfo shopBasInfo) {
		return shopBasInfoMapper.updateByShopIdSelective(shopBasInfo);
	}

	@Override
	public int updateByShopId(ShopBasInfo shopBasInfo) {
		return shopBasInfoMapper.updateByShopId(shopBasInfo);
	}

	@Override
	public ShopBasInfo selectByPrimaryKey(String id) {
		return shopBasInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ShopBasInfo> selectByShopId(String shopId) {
		return shopBasInfoMapper.selectByShopId(shopId);
	}

	@Override
	public int deleteId(String id) {
		return shopBasInfoMapper.deleteId(id);
	}

	@Override
	public int update(ShopBasInfo shopBasInfo) {
		return shopBasInfoMapper.update(shopBasInfo);
	}

}
