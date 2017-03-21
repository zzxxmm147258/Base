package com.hibo.bas.dbutil;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import com.hibo.bas.classFile.ClassFileUtil;
/**
 * <p>标题：数据源管理 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-4 下午6:04:51</p>
 * <p>类全名：com.hibo.base.dbutil.DynamicDataSource</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @version 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
	public DynamicDataSource(){
		if(log.isInfoEnabled()){
			log.info("=="+ClassFileUtil.getContextPath()+"==初始化数据源START..................");
		}
		Map<Object, Object> targetDataSources = DataSourceUtil.getTargetDataSources();
		this.setTargetDataSources(targetDataSources);
		this.setDefaultTargetDataSource(targetDataSources.get(DataSourceUtil.getDefaultDataSource()));
		this.afterPropertiesSet();
		if(log.isInfoEnabled()){
			log.info("=="+ClassFileUtil.getContextPath()+"==初始化数据源END..................");
		}
	}
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSource.getDataSource();
	}
	@Override
	public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
		super.setDataSourceLookup(dataSourceLookup);
	}

}
