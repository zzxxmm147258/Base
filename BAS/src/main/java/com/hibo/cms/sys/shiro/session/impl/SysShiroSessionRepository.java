package com.hibo.cms.sys.shiro.session.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.cms.sys.cache.SysCache;
import com.hibo.cms.sys.cache.impl.redis.CacheStaticUtil;
import com.hibo.cms.sys.shiro.session.ShiroSessionRepository;

import redis.clients.jedis.exceptions.JedisException;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月3日 下午3:26:18</p>
 * <p>类全名：com.hibo.cms.sys.shiro.session.impl.RedisShiroSessionRepository</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public class SysShiroSessionRepository implements ShiroSessionRepository {
	private static final Logger log = LoggerFactory.getLogger(SysShiroSessionRepository.class);
	private CacheManager cacheManager;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void saveSession(Session session) {
		if (session == null || session.getId() == null) {
			log.error("session或者session id为空");
			return;
		}
		if (log.isDebugEnabled()) {
			log.debug("Shiro-save-key=" + session.getId() + " loginInfo =" + (session.getAttribute("loginInfo") != null));
		}
		String key = (String) session.getId();
		try {
			SysCache<Object, Object> cache = (SysCache<Object, Object>) cacheManager.getCache(CacheStaticUtil.REDIS_CACHE_TYPE_SESSION);
			Long timeOut = session.getTimeout() / 1000;
			cache.put(key, session, timeOut.intValue());
		} catch (JedisException e) {
			log.error("保存session失败", e);
		}
	}

	@Override
	public void deleteSession(Serializable id) {
		if (id == null) {
			log.error("deleteSession:id为空");
			return;
		}
		if (log.isDebugEnabled()) {
			log.debug("Shiro-del-Key=" + id);
		}
		String key = (String) id;
		try {
			SysCache<Object, Object> cache = (SysCache<Object, Object>) cacheManager.getCache(CacheStaticUtil.REDIS_CACHE_TYPE_SESSION);
			cache.remove(key);
		} catch (JedisException e) {
			log.error("删除session失败", e);
		}
	}

	@Override
	public Session getSession(Serializable id) {
		if (id == null) {
			log.error("getSession:id为空");
			return null;
		}
		Session session = null;
		String key = (String) id;
		try {
			SysCache<Object, Object> cache = (SysCache<Object, Object>) cacheManager.getCache(CacheStaticUtil.REDIS_CACHE_TYPE_SESSION);
			session = (Session) cache.get(key);
		} catch (JedisException e) {
			log.error("获取session失败", e);
		}
		if (log.isDebugEnabled()) {
			log.debug("Shiro-get-key=" + id + " session =" + (session != null));
		}
		return session;
	}

	@Override
	public Collection<Session> getAllSessions() {
		Set<Session> sessions = new HashSet<Session>();
		try {
			SysCache<Object, Object> cache = (SysCache<Object, Object>) cacheManager.getCache(CacheStaticUtil.REDIS_CACHE_TYPE_SESSION);
			Collection<Object> byteKeys = cache.values();
			if (byteKeys != null && byteKeys.size() > 0) {
				for (Object bs : byteKeys) {
					sessions.add((Session) bs);
				}
			}
		} catch (JedisException e) {
			log.error("获取所有session失败", e);
		}
		return sessions;
	}

}
