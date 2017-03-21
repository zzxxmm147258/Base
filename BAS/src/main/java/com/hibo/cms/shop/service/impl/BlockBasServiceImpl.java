package com.hibo.cms.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.shop.dao.BlockBasMapper;
import com.hibo.cms.shop.model.BlockBas;
import com.hibo.cms.shop.service.IBlockBasService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月18日 下午6:05:04</p>
 * <p>类全名：com.hibo.cms.shop.service.impl.BlockBasServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class BlockBasServiceImpl implements IBlockBasService {
	
	@Autowired
	private BlockBasMapper blockBasMapper;

	@Override
	public List<BlockBas> selectAllAvailableByDistrict(String district) {
		return blockBasMapper.selectAllAvailableByDistrict(district);
	}

	@Override
	public int deleteByPrimaryKey(String blockId) {
		return blockBasMapper.deleteByPrimaryKey(blockId);
	}

	@Override
	public int insert(BlockBas record) {
		return blockBasMapper.insert(record);
	}

	@Override
	public int insertSelective(BlockBas record) {
		return blockBasMapper.insertSelective(record);
	}

	@Override
	public BlockBas selectByPrimaryKey(String blockId) {
		return blockBasMapper.selectByPrimaryKey(blockId);
	}

	@Override
	public int updateByPrimaryKeySelective(BlockBas record) {
		return blockBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BlockBas record) {
		return blockBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<BlockBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return blockBasMapper.selectByCondition(wStr, pageBounds);
	}

	
	@Override
	public List<BlockBas> selectAll() {
		return blockBasMapper.selectAll();
	}

	@Override
	public BlockBas selectByName(String blockId) {
		return blockBasMapper.selectByName(blockId);
	}

	@Override
	public List<BlockBas> selectAllName() {
		return blockBasMapper.selectAllName();
	}

}
