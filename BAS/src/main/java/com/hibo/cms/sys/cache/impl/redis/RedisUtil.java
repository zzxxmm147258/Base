package com.hibo.cms.sys.cache.impl.redis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.StrUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月12日 下午7:33:40</p>
 * <p>类全名：com.hibo.cms.sys.cache.impl.redis.RedisUtil</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class RedisUtil {
	private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);
	private static JedisPool jedisPool = null;
	private static int connectionTimeout = 10000;
	private static int soTimeout=2000;
	private static int database = 0;
	public static boolean bRedisStatus;
	private static String clientName = "HIBO-REDIS";
	/**
	 * 初始化redis
	 */
	static {
		init();
	}

	/**
	 * 初始化方法
	 */
	public static void init() {
		String host1 = DataConfig.getConfig("REDIS.HOST");
		if (host1 != null) {
			String host = "127.0.0.1";
			int port = 6379;
			if (host1.indexOf(":") > 0) {
				host = host1.substring(0, host1.indexOf(":"));
				port = StrUtil.obj2int(host1.substring(host1.indexOf(":") + 1));
			} else {
				if (port <= 0)
					port = 6379;
				host=host1;
			}
			String password1 = DataConfig.getConfig("REDIS.PASSWORD");
			String password = null;
			if (password1 != null && !"".equals(password1)) {
				password = password1;
			}
			if (null == host || 0 == port) {
				log.info("初始化redis配置文件！");
				throw new NullPointerException("找不到redis配置");
			}
			if (jedisPool == null) {
				jedisPool = new JedisPool(new JedisPoolConfig(), host, port, connectionTimeout, soTimeout, password, database, clientName);
				bRedisStatus = true;
			}
		}
	}

	public static Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}

	/**
	 * get value from redis
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = getJedis();
		try {
			value = jedis.get(key);
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * 查看缓存是否存在
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isExists(String key) {
		Jedis jedis = getJedis();
		boolean t = false;
		try {
			t = jedis.exists(key);
		} finally {
			jedis.close();
		}
		return t;
	}

	/**
	 * 获取缓存剩余时间
	 * 
	 * @param key
	 * @return
	 */
	public static Long getLastTime(String key) {
		Jedis jedis = getJedis();
		long t = 0;
		try {
			t = jedis.ttl(key);
		} finally {
			jedis.close();
		}
		return t;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String set(String key, String value) {
		return set(key, value, 0);
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 *            有效时间
	 * @return
	 */
	public static String set(String key, String value, int expire) {
		Jedis jedis = getJedis();
		try {
			if(0!=expire){
				jedis.setex(key, expire, value);
			}else{
				jedis.set(key, value);
			}
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * del
	 * 
	 * @param key
	 */
	public static void del(String key) {
		Jedis jedis = getJedis();
		try {
			jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	/**
	 * del
	 * 
	 * @param <K>
	 * @param key
	 */
	public static <K> void del(Set<K> keys) {
		Jedis jedis = getJedis();
		try {
			for (K k : keys) {
				String key = new String((byte[]) k);
				jedis.del(key);
			}
		} finally {
			jedis.close();
		}
	}

	/**
	 * flush
	 */
	public static void flushDB() {
		Jedis jedis = getJedis();
		try {
			jedis.flushDB();
		} finally {
			jedis.close();
		}
	}

	/**
	 * size
	 */
	public static Long dbSize() {
		Long dbSize = 0L;
		Jedis jedis = getJedis();
		try {
			dbSize = jedis.dbSize();
		} finally {
			jedis.close();
		}
		return dbSize;
	}

	/**
	 * keys
	 * 
	 * @param regex
	 * @return
	 */
	public static Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;
		Jedis jedis = getJedis();
		try {
			keys = jedis.keys(pattern.getBytes());
		} finally {
			jedis.close();
		}
		return keys;
	}

	public static String getRedisInfo() {
		String info = null;
		Jedis jedis = getJedis();
		try {
			info = jedis.info();
		} finally {
			jedis.close();
		}
		return info;
	}

	public static String getClientList() {
		String clientList = null;
		Jedis jedis = getJedis();
		try {
			clientList = jedis.clientList();
		} finally {
			jedis.close();
		}
		return clientList;
	}
}
