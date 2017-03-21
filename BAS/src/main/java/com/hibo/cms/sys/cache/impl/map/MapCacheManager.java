package com.hibo.cms.sys.cache.impl.map;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月16日 下午7:49:29</p>
 * <p>类全名：com.hibo.cms.sys.cache.impl.map.MapCacheManager</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 * @param <K>
 * @param <K>
 * @param <V>
 * @param <V>
 */
public abstract class MapCacheManager implements CacheManager{
	
	

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		
		return null;
	}
}
