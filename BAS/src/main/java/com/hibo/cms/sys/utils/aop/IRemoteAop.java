package com.hibo.cms.sys.utils.aop;
import org.aspectj.lang.ProceedingJoinPoint;

import com.hibo.cms.sys.utils.anno.RemoteAnno;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月8日 上午11:05:08</p>
 * <p>类全名：com.hibo.cms.sys.utils.aop.IRemoteAop</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public interface IRemoteAop {

	/**
	 * 执行前
	 * 
	 * @param <T>
	 * @param jp
	 */
	public Object around(ProceedingJoinPoint jp,RemoteAnno ra) throws Throwable;
}
