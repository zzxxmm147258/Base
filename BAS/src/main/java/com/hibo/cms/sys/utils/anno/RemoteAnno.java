package com.hibo.cms.sys.utils.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月8日 上午10:43:54</p>
 * <p>类全名：com.hibo.cms.sys.utils.anno.RemoteAnno</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteAnno {
	
	public String value();
	
	public String beanName() default "";
	
	public String methodName() default "";
}
