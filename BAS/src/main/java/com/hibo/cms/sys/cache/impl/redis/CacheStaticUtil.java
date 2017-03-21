package com.hibo.cms.sys.cache.impl.redis;
/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 上午10:57:18</p>
 * <p>类全名：com.hibo.cms.sys.cache.impl.redis.CacheStaticUtil</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class CacheStaticUtil {
	//session缓存
	public static final String REDIS_CACHE_TYPE_SESSION = "hibo_shiro_session";
	//用户信息缓存
	public static final String REDIS_CACHE_TYPE_LOGININFO = "hibo_logininfo";
}
