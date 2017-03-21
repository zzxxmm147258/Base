package com.hibo.cms.version.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.version.dao.VersionControlBasMapper;
import com.hibo.cms.version.model.VersionControlBas;
import com.hibo.cms.version.service.IVersionControlBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月10日 上午9:45:28</p>
 * <p>类全名：com.hibo.cms.version.service.VersionControlBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class VersionControlBasServiceImpl implements IVersionControlBasService{

	@Autowired
	private VersionControlBasMapper versionControlBasMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return versionControlBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VersionControlBas record) {
		return versionControlBasMapper.insert(record);
	}

	@Override
	public int insertSelective(VersionControlBas record) {
		return versionControlBasMapper.insertSelective(record);
	}

	@Override
	public VersionControlBas selectByPrimaryKey(String id) {
		return versionControlBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VersionControlBas record) {
		return versionControlBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VersionControlBas record) {
		return versionControlBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public VersionControlBas selectByType(String type) {
		return versionControlBasMapper.selectByType(type);
	}

	@Override
	public PageList<VersionControlBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return versionControlBasMapper.selectByCondition(wStr, pageBounds);
	}

}
