package com.hibo.cms.sys.utils.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** <p>标题：用户信息注解</p>
 * <p>功能：请求注解 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月22日 下午2:10:17</p>
 * <p>类全名：com.hibo.cms.sys.utils.anno.SysAnnoLoginInfo</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginInfoAnno {
	  /**
     * 当前用户在request中的名字
     *
     * @return
     */
//    String value() default Constants.CURRENT_USER;
}
