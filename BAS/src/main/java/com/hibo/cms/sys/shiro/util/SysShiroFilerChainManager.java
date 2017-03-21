package com.hibo.cms.sys.shiro.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;

/**
 * <p>标题： 添加拦截器</p>
 * <p>功能： 动态添加拦截器的匹配 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月2日 下午3:20:51</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.SysShiroFilerChainManager</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class SysShiroFilerChainManager {
	
	private DefaultFilterChainManager filterChainManager;
	
	@Resource
	public void setFilterChainManager(DefaultFilterChainManager filterChainManager) {
		this.filterChainManager = filterChainManager;
	}

	private Map<String, NamedFilterList> defaultFilterChains;
	public void init() {
		defaultFilterChains = new HashMap<String, NamedFilterList>(filterChainManager.getFilterChains());
	}

	public void initFilterChains(List<Map<String, String>> urlFilters) {
		// 1、首先删除以前老的filter chain并注册默认的
		filterChainManager.getFilterChains().clear();
		if (defaultFilterChains != null) {
			filterChainManager.getFilterChains().putAll(defaultFilterChains);
		}
		// 2、循环URL Filter 注册filter chain
		for (Map<String, String> urlFilter : urlFilters) {
			String url = urlFilter.get("url");
			// 注册验证拦截器
			if (!StringUtils.isEmpty(urlFilter.get("filter"))) {
				filterChainManager.addToChain(url, urlFilter.get("filter"));
			}
			// 注册roles filter
			if (!StringUtils.isEmpty(urlFilter.get("roles"))) {
				filterChainManager.addToChain(url, "roles", urlFilter.get("role"));
			}
			// 注册perms filter
			if (!StringUtils.isEmpty(urlFilter.get("perms"))) {
				filterChainManager.addToChain(url, "perms", urlFilter.get("perms"));
			}
		}
	}
}
