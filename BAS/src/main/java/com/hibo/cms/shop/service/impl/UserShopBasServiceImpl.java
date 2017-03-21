package com.hibo.cms.shop.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.shop.dao.UserShopBasMapper;
import com.hibo.cms.shop.model.UserShopBas;
import com.hibo.cms.shop.model.UserShopBasKey;
import com.hibo.cms.shop.service.IUserShopBasService;
import com.hibo.cms.sys.utils.anno.RemoteAnno;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午2:36:10</p>
 * <p>类全名：com.hibo.cms.shop.service.impl.UserShopBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class UserShopBasServiceImpl implements IUserShopBasService{

	@Autowired
	private UserShopBasMapper userShopBasMapper;
	
	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteByPrimaryKey(UserShopBasKey key) {
		return userShopBasMapper.deleteByPrimaryKey(key);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(UserShopBas record) {
		return userShopBasMapper.insert(record);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertSelective(UserShopBas record) {
		return userShopBasMapper.insertSelective(record);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public UserShopBas selectByPrimaryKey(UserShopBasKey key) {
		return userShopBasMapper.selectByPrimaryKey(key);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateByPrimaryKeySelective(UserShopBas record) {
		return userShopBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateByPrimaryKey(UserShopBas record) {
		return userShopBasMapper.updateByPrimaryKey(record);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<UserShopBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return userShopBasMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int update(UserShopBasKey key, UserShopBas record) {
		return userShopBasMapper.update(key, record);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteByShopId(String shopId) {
		return userShopBasMapper.deleteByShopId(shopId);
	}

}
