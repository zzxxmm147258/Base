package com.hibo.bas.dbutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>标题：数据源服务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-4 下午7:07:03</p>
 * <p>类全名：com.hibo.base.dbutil.DataSource</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @version 1.0
 */
public class DataSource {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	private static final Logger log = LoggerFactory.getLogger(DataSource.class);
	public static void setDataSource(String dataSource) {
		contextHolder.set(dataSource);
		if(log.isDebugEnabled()){
			 String msg = "in thread [" + Thread.currentThread().getName() + "]";
			log.debug("数据源 Set=" + dataSource + " "+msg);
		}
	}

	public static String getDataSource() {
		if(log.isDebugEnabled()){
			String msg = "in thread [" + Thread.currentThread().getName() + "]";
			log.debug("数据源 Get=" + contextHolder.get()+ " "+msg);
		}
		return contextHolder.get();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}
}
