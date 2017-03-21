package com.hibo.cms.config.service;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.config.model.Sysoptions;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月6日 下午4:02:18</p>
 * <p>类全名：com.hibo.cms.config.service.ISysConfigService</p>
 * 作者：马骏达
 * 初审：周雷
 * 复审：周雷
 */
public interface ISysConfigService {

	public PageList<Sysoptions> getPluginConfigList(PageBounds pageBounds);
	/*
	 * 条件查询*/
	public PageList<Sysoptions> getSelectAlitel(Map map,PageBounds pageBounds);

	public int savePluginConfig(Sysoptions pluginConfig);

	public Sysoptions getPluginConfigById(String id);

	public int updatePluginConfig(Sysoptions pluginConfig);

	public int deletePluginConfigById(String id);

	public Sysoptions findByEname(String ename);
}
