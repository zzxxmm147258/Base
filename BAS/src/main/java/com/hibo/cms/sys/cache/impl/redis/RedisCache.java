package com.hibo.cms.sys.cache.impl.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hibo.cms.sys.cache.SysCache;
import com.hibo.cms.sys.utils.serialize.SerializeUtils;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 上午10:57:12</p>
 * <p>类全名：com.hibo.cms.sys.cache.impl.redis.RedisCache</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class RedisCache<K, V> implements SysCache<K, V>{
   
    private static final Logger log = LoggerFactory.getLogger(RedisCache.class);
    
    private static String ssid;
    
    private String keyPrname;
 
    public String getKeyPrname() {
        return keyPrname;
    }
    public void setKeyPrname(String keyPrname) {
    	if (RedisUtil.bRedisStatus){
    		this.keyPrname = keyPrname;
        }else{
        	this.keyPrname = null;
        }
    }
 
    public RedisCache(String keyPrname) {
    	setKeyPrname(keyPrname);
    }
    private String getKey(K key){
    	if(null==keyPrname){
    		return null;
    	}
    	String ssId = ":";
    	if(null!=ssid){
    		ssId = ssId+ssid+":";
    	}
    	return keyPrname + ssId +key;
    }
	@Override
	public V get(K key) throws CacheException{
        try {
            if (key == null) {
                return null;
            } else {
            	String keyStr = getKey(key);
            	if(null==keyStr){
            		return null;
            	}
            	if(log.isDebugEnabled()){
            		boolean isExists = RedisUtil.isExists(keyStr);
            		if(isExists){
            			log.debug("缓存Redis中获取key为[" + key + "]的数据");
            		}else{
            			log.debug("缓存Redis中创建key为[" + key + "]的数据");
            		}
            	}
                String rawValue = RedisUtil.get(keyStr);
                V value = SerializeUtils.deserialize(rawValue);
                return value;
            }
        } catch (Throwable t) {
              	if(log.isErrorEnabled()){
              		log.error("缓存Redis中创建key为[" + key + "]的对象异常." + t.getMessage());
              	}
            throw new CacheException(t);
        }
 
    }
    @Override
	public V put(K key, V value, int expire) throws CacheException {
        try {
        	if(log.isDebugEnabled()){
        		log.debug("缓存Redis中添加key为[" + key + "]的数据-时效为"+expire+"秒");
        	}
        	String keyStr = getKey(key);
        	if(null==keyStr){
        		return null;
        	}
        	RedisUtil.set(keyStr,SerializeUtils.serialize(value), expire);
            return value;
        } catch (Throwable t) {
        	if(log.isErrorEnabled()){
        		log.error("缓存Redis中添加key为[" + key + "]的数据" + t.getMessage());
          	}
            throw new CacheException(t);
        }
	}
	@Override
    public V put(K key, V value) throws CacheException {
		if(log.isDebugEnabled()){
    		log.debug("缓存Redis中添加key为[" + key + "]的数据");
    	}
    	
        try {
        	String keyStr = getKey(key);
        	if(null==keyStr){
        		return null;
        	}
        	RedisUtil.set(keyStr,SerializeUtils.serialize(value));
            return value;
        } catch (Throwable t) {
        	if(log.isErrorEnabled()){
        		log.error("缓存Redis中添加key为[" + key + "]的数据异常." + t.getMessage());
          	}
            throw new CacheException(t);
        }
    }
    @Override
    public V remove(K key) throws CacheException {
    	if(log.isDebugEnabled()){
    		log.debug("缓存Redis中删除key为[" + key + "]的数据");
    	}
        try {
            String keyStr = getKey(key);
            if(null==keyStr){
        		return null;
        	}
            V previous = get(key);
            RedisUtil.del(keyStr);
            return previous;
        } catch (Throwable t) {
        	if(log.isErrorEnabled()){
        		log.error("删除key为["+key+"]的redis缓存异常." + t.getMessage());
          	}
            throw new CacheException(t);
        }
    }
 
	@Override
    public void clear() throws CacheException {
    	if(log.isDebugEnabled()){
    		log.debug("清除redis中"+keyPrname+"类型的缓存!");
    	}
    	Set<K> keys = keys();
    	if(!isEmpty(keys)){
    		RedisUtil.del(keys);
    	}
    }
 
    @Override
    public int size() {
        try {
            Long longSize = 0l;
            Set<K> keys = keys();
            if(null!=keys){
            	longSize = new Long(keys.size());
            }
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
 
    @SuppressWarnings("unchecked")
	@Override
    public Set<K> keys() {
        try {
        	if(null==this.keyPrname){
        		return null;
        	}
            Set<byte[]> keys = RedisUtil.keys(this.keyPrname + "*");
            if (isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                return (Set<K>) keys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
 
    @SuppressWarnings("unchecked")
	@Override
    public Collection<V> values() {
        try {
        	if(null==this.keyPrname){
        		return null;
        	}
            Set<byte[]> keys = RedisUtil.keys(this.keyPrname + "*");
            if (!isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (byte[] key : keys) {
                    V value = get((K) key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

	public String getRedisInfo(){
		if(null==this.keyPrname){
    		return null;
    	}
		return RedisUtil.getRedisInfo();
	}
	public String getClientList(){
		if(null==this.keyPrname){
    		return null;
    	}
		return RedisUtil.getClientList();
	}

	/**
	 * 查看缓存是否存在
	 * @param key
	 * @return
	 */
	@Override
	public boolean isExists(K key) {
		String keyStr = getKey(key);
		if(null==keyStr){
    		return false;
    	}
		return RedisUtil.isExists(keyStr);
	}
	
	/**
	 * 获取缓存剩余时间
	 * @param key
	 * @return
	 */
	@Override
	public Long getLastTime(K key) {
		String keyStr = getKey(key);
		if(null==keyStr){
    		return 0l;
    	}
		return RedisUtil.getLastTime(keyStr);
	}
	
	private boolean isEmpty(Collection<?> c){
		return c == null || c.isEmpty();
	}
}
