package com.hibo.cms.sys.cache.impl.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月16日 下午7:56:03</p>
 * <p>类全名：com.hibo.cms.sys.cache.impl.map.SysCache</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 * @param <K>
 * @param <V>
 */
public class SysCache<K, V> implements Map<K, V>,Serializable{
	
	private static final long serialVersionUID = -8364805288600561841L;
	
	private Map<K,V> map = new HashMap<K,V>();

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public V get(Object key) {
		return null;
	}

	@Override
	public V put(K key, V value) {
		return null;
	}
	
	public V put(K key, V value, long setTime) {
		return map.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		
	}

	@Override
	public void clear() {
		
	}

	@Override
	public Set<K> keySet() {
		return null;
	}

	@Override
	public Collection<V> values() {
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return null;
	}
	

}
