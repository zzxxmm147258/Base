package com.hibo.cms.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.shop.dao.BuildingBasMapper;
import com.hibo.cms.shop.model.BuildingBas;
import com.hibo.cms.shop.service.IBuildingBasService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月18日 下午6:15:08</p>
 * <p>类全名：com.hibo.cms.shop.service.impl.BuildingBasServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class BuildingBasServiceImpl implements IBuildingBasService {

	@Autowired
	private BuildingBasMapper buildingBasMapper;
	
	@Override
	public List<BuildingBas> selectAllAvailableByBlock(String block) {
		return buildingBasMapper.selectAllAvailableByBlock(block);
	}

	@Override
	public int deleteByPrimaryKey(String buildingId) {
		return buildingBasMapper.deleteByPrimaryKey(buildingId);
	}

	@Override
	public int insert(BuildingBas record) {
		return buildingBasMapper.insert(record);
	}

	@Override
	public int insertSelective(BuildingBas record) {
		return buildingBasMapper.insertSelective(record);
	}

	@Override
	public BuildingBas selectByPrimaryKey(String buildingId) {
		return buildingBasMapper.selectByPrimaryKey(buildingId);
	}

	@Override
	public int updateByPrimaryKeySelective(BuildingBas record) {
		return buildingBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BuildingBas record) {
		return buildingBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<BuildingBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return buildingBasMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	public List<BuildingBas> selectAll() {
		return buildingBasMapper.selectAll();
	}

	@Override
	public BuildingBas selectByName(String buildingId) {
		return buildingBasMapper.selectByName(buildingId);
	}

	@Override
	public List<BuildingBas> selectAllName() {
		return buildingBasMapper.selectAllName();
	}

}
