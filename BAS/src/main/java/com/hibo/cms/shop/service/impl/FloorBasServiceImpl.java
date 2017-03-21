package com.hibo.cms.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.shop.dao.FloorBasMapper;
import com.hibo.cms.shop.model.FloorBas;
import com.hibo.cms.shop.service.IFloorBasService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月18日 下午6:21:51</p>
 * <p>类全名：com.hibo.cms.shop.service.impl.FloorBasServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class FloorBasServiceImpl implements IFloorBasService {

	@Autowired
	private FloorBasMapper floorBasMapper;
	
	@Override
	public List<FloorBas> selectAllAvailableByBuilding(String building) {
		return floorBasMapper.selectAllAvailableByBuilding(building);
	}

	@Override
	public int deleteByPrimaryKey(String floorId) {
		return floorBasMapper.deleteByPrimaryKey(floorId);
	}

	@Override
	public int insert(FloorBas record) {
		return floorBasMapper.insert(record);
	}

	@Override
	public int insertSelective(FloorBas record) {
		return floorBasMapper.insertSelective(record);
	}

	@Override
	public FloorBas selectByPrimaryKey(String floorId) {
		return floorBasMapper.selectByPrimaryKey(floorId);
	}

	@Override
	public int updateByPrimaryKeySelective(FloorBas record) {
		return floorBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FloorBas record) {
		return floorBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<FloorBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return floorBasMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	public List<FloorBas> selectAll() {
		return floorBasMapper.selectAll();
	}

	@Override
	public FloorBas selectByName(String floorId) {
		return floorBasMapper.selectByName(floorId);
	}

	@Override
	public List<FloorBas> selectAllName() {
		return floorBasMapper.selectAllName();
	}

	@Override
	public List<FloorBas> selectByAllName(String floorName) {
		return floorBasMapper.selectByAllName(floorName);
	}

}
