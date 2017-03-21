package com.hibo.cms.sys.utils.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月11日 下午4:46:15</p>
 * <p>类全名：com.hibo.cms.sys.utils.aop.IServiceAop</p>
 * 作者：Administrator
 * 初审：
 * 复审：
 */
public interface IServiceAop {
	public void before(JoinPoint jp);
	public Object around(ProceedingJoinPoint jp);
}
