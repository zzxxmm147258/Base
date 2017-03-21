package com.hibo.bas.basenum;
/** 
 * <p>标题：信息传递枚举</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年1月20日 下午6:31:15</p>
 * <p>类全名：com.hibo.bas.basenum.MessageCodeEnum</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public enum MessageCodeEnum {
	/**成功*/
	S_100,
	/**成功*/
	S_LOGIN,
	/**失败*/
	F_100,
	/**未登录*/
	F_NOLOGIN,
	/**用户名密码为空*/
	F_U_P_NULL,
	/**登录失败*/
	F_LOGIN
}
