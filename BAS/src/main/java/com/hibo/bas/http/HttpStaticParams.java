package com.hibo.bas.http;
/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月9日 下午4:43:14</p>
 * <p>类全名：com.hibo.bas.http.HttpStaticParams</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class HttpStaticParams {
	
	/** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
	public static int SOTIMEOUT = 30000;

	/** 连接超时时间，由bean factory设置，缺省为8秒钟 */
	public static int CONNECTIONTIMEOUT = 8000;
	
	/**  请求发起方的ip地址 */
	public static String CLIENTIP;
	
	/** 设置字符集 */
	public static String INPUT_CHARSET = "UTF-8";
}
