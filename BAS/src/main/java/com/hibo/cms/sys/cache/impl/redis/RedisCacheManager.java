package com.hibo.cms.sys.cache.impl.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月12日 下午5:01:44</p>
 * <p>类全名：com.hibo.cms.sys.cache.impl.redis.RedisCacheManager</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class RedisCacheManager implements CacheManager {

	/**
	 * This class's private log instance.
	 */
	private static final Logger log = LoggerFactory.getLogger(EhCacheManager.class);

	// 缓存暂存
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		try {
			if (log.isTraceEnabled()) {
				log.trace("获取redis缓存:" + name);
			}
			return new RedisCache<K, V>(name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
