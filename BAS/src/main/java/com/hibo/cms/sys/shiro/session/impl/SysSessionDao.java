package com.hibo.cms.sys.shiro.session.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.cms.sys.cache.impl.redis.RedisUtil;
import com.hibo.cms.sys.shiro.session.ShiroSessionRepository;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月31日 上午9:46:25</p>
 * <p>类全名：com.hibo.cms.sys.shiro.session.SysSessionDao</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public class SysSessionDao extends AbstractSessionDAO {
	
	private static final Logger log = LoggerFactory.getLogger(SysSessionDao.class);

	private ShiroSessionRepository shiroSessionRepository;
	
    private ConcurrentMap<Serializable, Session> sessions;

    private boolean bRedis = true;
	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
		if (!RedisUtil.bRedisStatus){
			this.sessions=new ConcurrentHashMap<Serializable, Session>();
			this.bRedis = false;
		}
	}
	
    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        }
        return sessions.putIfAbsent(id, session);
    }

	@Override
	public void update(Session session) throws UnknownSessionException {
		if (session instanceof ValidatingSession) {
            if (((ValidatingSession) session).isValid()) {
            	if (bRedis){
            		getShiroSessionRepository().saveSession(session);
            	}else{
            		storeSession(session.getId(), session);
            	}
            } else {
                uncache(session);
            }
        } else {
        	if (log.isInfoEnabled()) {
    			log.info("更新session:sessionId=" + session.getId() + "缓存");
    		}
        	if (bRedis){
        		getShiroSessionRepository().saveSession(session);
        	}else{
        		storeSession(session.getId(), session);
        	}
        }
	}
    
	protected void uncache(Session session) {
	        if (session == null) {
	            return;
	        }
	        Serializable id = session.getId();
	        if (id == null) {
	            return;
	        }
	        if (bRedis){
		        Session ss = getShiroSessionRepository().getSession(id);
		        if (ss != null) {
		        	getShiroSessionRepository().deleteSession(id);
		        }
	        }else{
	        	this.sessions.remove(id);
	        }
	    }
	@Override
	public void delete(Session session) {
		if (session == null) {
			log.error("session is not null,delete failed");
			return;
		}
		Serializable id = session.getId();
		if (id != null)
			if(log.isInfoEnabled()){
				log.info("删除更新session:sessionId="+id+"缓存");
			}
			if (bRedis){
				getShiroSessionRepository().deleteSession(id);
			}else{
				this.sessions.remove(id);
			}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		if(log.isInfoEnabled()){
			log.info("获取在线session");
		}
		if (bRedis){
			return getShiroSessionRepository().getAllSessions();
		}else{
	        Collection<Session> values = sessions.values();
	        if (CollectionUtils.isEmpty(values)) {
	            return Collections.emptySet();
	        } else {
	            return Collections.unmodifiableCollection(values);
	        }
		}
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		if (bRedis)
			getShiroSessionRepository().saveSession(session);
		else
			storeSession(sessionId, session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (bRedis)
			return getShiroSessionRepository().getSession(sessionId);
		else
			return this.sessions.get(sessionId);
	}
	
}
