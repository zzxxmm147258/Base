package com.hibo.cms.advert.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.advert.dao.AdvertBasMapper;
import com.hibo.cms.advert.model.AdvertBas;
import com.hibo.cms.advert.service.IAdvertBasService;
import com.hibo.cms.lims.QueryFilterBuilder;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月2日 下午2:23:32</p>
 * <p>类全名：com.hibo.cms.advert.service.impl.AdvertBasServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class AdvertBasServiceImpl implements IAdvertBasService{

	@Autowired
	private AdvertBasMapper advertBasMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return advertBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AdvertBas record) {
		return advertBasMapper.insert(record);
	}

	@Override
	public int insertSelective(AdvertBas record) {
		return advertBasMapper.insertSelective(record);
	}

	@Override
	public AdvertBas selectByPrimaryKey(String id) {
		return advertBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AdvertBas record) {
		return advertBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(AdvertBas record) {
		return advertBasMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(AdvertBas record) {
		return advertBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<AdvertBas> selectPage(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return advertBasMapper.selectPage(wStr, pageBounds);
	}

	@Override
	public List<AdvertBas> selectByGroupId(String groupId) {
		return advertBasMapper.selectByGroupId(groupId);
	}

	@Override
	public List<AdvertBas> selectAvailableByPosition(String adPosition) {
		return advertBasMapper.selectAvailableByPosition(adPosition);
	}

	@Override
	public List<AdvertBas> selectByGroupIdLimit(String adPosition, int page, int limit) {
		return advertBasMapper.selectByGroupIdLimit(adPosition, page, limit);
	}

}
