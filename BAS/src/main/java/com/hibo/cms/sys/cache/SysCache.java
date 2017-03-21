package com.hibo.cms.sys.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月3日 上午11:59:43</p>
 * <p>类全名：com.hibo.cms.sys.cache.SysCache</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @param <V>
 * @param <K>
 */
public interface SysCache<K, V> extends Cache<K, V>{

	@Override
	public V get(K key) throws CacheException;

	@Override
	public V put(K key, V value) throws CacheException;
	
	public V put(K key, V value, int expire) throws CacheException;

	@Override
	public V remove(K key) throws CacheException;

	@Override
	public void clear() throws CacheException;

	@Override
	public int size();

	@Override
	public Set<K> keys();

	@Override
	public Collection<V> values();
	
	public boolean isExists(K key);
	
	public Long getLastTime(K key);

}
