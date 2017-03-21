package com.hibo.cms.config.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.config.dao.SysoptionsMapper;
import com.hibo.cms.config.model.Sysoptions;
import com.hibo.cms.config.service.ISysConfigService;
import com.hibo.cms.config.util.SysConfigUtil;
import com.hibo.cms.lims.QueryFilterBuilder;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年9月12日 上午11:09:54
 * </p>
 * <p>
 * 类全名：com.hibo.cms.config.service.impl.SysConfigServiceImpl
 * </p>
 * 作者：马骏达 初审： 复审：
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {

	private SysoptionsMapper sysOptionsMapper;

	public SysoptionsMapper getSysOptionsMapper() {
		return sysOptionsMapper;
	}

	@Autowired
	public void setSysOptionsMapper(SysoptionsMapper sysOptionsMapper) {
		this.sysOptionsMapper = sysOptionsMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public PageList<Sysoptions> getPluginConfigList(PageBounds pageBounds) {
		return sysOptionsMapper.selectSysoptionsList(pageBounds);
	}

	/*
	 * 条件查询
	 */
	@Override
	public PageList<Sysoptions> getSelectAlitel(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return sysOptionsMapper.findByTwoType(wStr, pageBounds);
	}

	@Override
	@Transactional(readOnly = false)
	public int savePluginConfig(Sysoptions pluginConfig) {
		SysConfigUtil.setSysConfigNull();
		return sysOptionsMapper.insert(pluginConfig);
	}

	@Override
	@Transactional(readOnly = true)
	public Sysoptions getPluginConfigById(String id) {
		return sysOptionsMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int updatePluginConfig(Sysoptions pluginConfig) {
		SysConfigUtil.setSysConfigNull();
		return sysOptionsMapper.updateByPrimaryKeySelective(pluginConfig);
	}

	@Override
	@Transactional(readOnly = false)
	public int deletePluginConfigById(String id) {
		SysConfigUtil.setSysConfigNull();
		return sysOptionsMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Sysoptions findByEname(String ename) {
		return sysOptionsMapper.findByEname(ename);
	}
}
