package com.hibo.cms.sys.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月3日 下午3:02:06</p>
 * <p>类全名：com.hibo.cms.sys.shiro.session.ShiroSessionRepository</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public interface ShiroSessionRepository {

	void saveSession(Session session);

	void deleteSession(Serializable sessionId);

	Session getSession(Serializable sessionId);

	Collection<Session> getAllSessions();
}
