package com.hibo.cms.sys.utils.aop.impl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.sys.utils.aop.IRemoteAop;
import com.hibo.cms.sys.utils.remote.RemoteInteractionUtil;

/** 
 * <p>标题：注解远程调用</p>
 * <p>功能： 通过在spring扫描的方法上添加注解的方式实现远程访问</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月8日 上午11:06:07</p>
 * <p>类全名：com.hibo.cms.sys.utils.aop.impl.RemoteAopImpl</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class RemoteAopImpl implements IRemoteAop {

	
	@Override
	public Object around(ProceedingJoinPoint jp, RemoteAnno ra) throws Throwable {
		if (!RemoteInteractionUtil.ISREMOTE) {
			return jp.proceed();
		}
		String[] ras = ra.value().split(",");
		String conno = null;
		for (String rastr : ras) {
			if (!StrUtil.isnull(RemoteInteractionUtil.getConnoType(rastr))) {
				conno = rastr;
				break;
			}
		}
		if(StrUtil.isnull(conno)){
			return jp.proceed();
		}
		try {
			Class<?> clazz = jp.getTarget().getClass();
			String beanName = ra.beanName();
			if (StrUtil.isnull(beanName)) {
				Service service = clazz.getAnnotation(Service.class);
				Component component = clazz.getAnnotation(Component.class);
				Controller controller = clazz.getAnnotation(Controller.class);
				if (null != service) {
					beanName = service.value();
				} else if (null != component) {
					beanName = component.value();
				} else if (null != controller) {
					beanName = controller.value();
				}
			}
			if (StrUtil.isnull(beanName)) {
				beanName = clazz.getSimpleName();
				String nameAfter = beanName.substring(1, beanName.length());
				beanName = (beanName.charAt(0) + "").toLowerCase() + nameAfter;
			}
			String methodNmae = ra.methodName();
			if (null == methodNmae || "".equals(methodNmae)) {
				methodNmae = jp.getSignature().getName();
			}
			return RemoteInteractionUtil.getRemoteObjByConno(conno, beanName, methodNmae, jp);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
}
