package com.hibo.cms.sys.cache.Utils;

import javax.annotation.Resource;

import org.apache.shiro.cache.CacheManager;

import com.hibo.cms.sys.cache.SysCache;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 上午10:55:01</p>
 * <p>类全名：com.hibo.cms.sys.cache.Utils.SysCacheManager</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class SysCacheManager {
	private static CacheManager cacheManager;
	@Resource
	public void setCacheManager(CacheManager cacheManager) {
		SysCacheManager.cacheManager = cacheManager;
	}

	/**
	 * 通过name类型获取全局的缓存
	 * @param name 自定义分类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> SysCache<V, K> getGlobalCache(String name){
		return (SysCache<V, K>) cacheManager.getCache(name);
	}

	
	/**
	 * 添加name类型的缓存数据
	 * @param name
	 * @param key
	 * @param value
	 * @param expire 有效时间(秒),重新put会使上次put的时间时效，为0使为永久缓存
	 */
	public static <K, V> void putToGlobalCache(String name,K key, V value,int expire){
		SysCacheManager.getGlobalCache(name).put(key, value, expire);
	}
	
	public static <K, V> void put(String name,K key, V value,int expire){
		SysCacheManager.getGlobalCache(name).put(key, value, expire);
	}
	
	/**
	 * 移除name类型的缓存数据
	 * @param name
	 * @param key
	 * @param value
	 */
	public static <K, V> void removeToGlobalCache(String name,K key){
		SysCacheManager.getGlobalCache(name).remove(key);
	}
	
	public static <K, V> void removeCache(String name,K key){
		SysCacheManager.getGlobalCache(name).remove(key);
	}
	
	/**
	 * 获取name类型的缓存数据
	 * @param name
	 * @param key
	 */
	public static <K, V> Object getFromGlobalCache(String name,K key){
		return SysCacheManager.getGlobalCache(name).get(key);
	}
	
	public static <K, V> Object get(String name,K key){
		return SysCacheManager.getGlobalCache(name).get(key);
	}
	
	
	/**
	 * 查看name类型缓存是否存在
	 * @param key
	 * @return
	 */
	public static boolean isExistsGlobalCache(String name,String key) {
		return SysCacheManager.getGlobalCache(name).isExists(key);
	}
	
	public static boolean isCache(String name,String key) {
		return SysCacheManager.getGlobalCache(name).isExists(key);
	}
	
	/**
	 * 获取name类型缓存剩余时间-秒
	 * @param key
	 * @return
	 */
	public static Long getGlobalCacheRestTime(String name,String key) {
		return SysCacheManager.getGlobalCache(name).getLastTime(key);
	}
	
	public static Long getTime(String name,String key) {
		return SysCacheManager.getGlobalCache(name).getLastTime(key);
	}

}
