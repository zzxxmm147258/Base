package com.hibo.cms.filter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hibo.bas.dbutil.DataSource;
import com.hibo.bas.dbutil.DataSourceUtil;
import com.hibo.cms.filter.dao.IPFilterMapper;
import com.hibo.cms.filter.model.SysIPFilter;
import com.hibo.cms.filter.service.IPFilterService;
import com.hibo.cms.filter.util.FilterParamsUtil;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月17日 下午6:55:31</p>
 * <p>类全名：com.hibo.cms.filter.service.impl.IPFilterServiceImpl</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
@Service
public class IPFilterServiceImpl implements IPFilterService {

	@Resource
	private IPFilterMapper ipFilterMapper;
	
	@Override
	public int addIp(SysIPFilter ipFilter){
		//切换到默认数据源
		DataSource.setDataSource(DataSourceUtil.getDefaultDataSource());
		int k = ipFilterMapper.addIp(ipFilter);
		FilterParamsUtil.setInit(true);
		return k;
	}
	
	@Override
	public int updataIp(SysIPFilter ipFilter){
		//切换到默认数据源
		DataSource.setDataSource(DataSourceUtil.getDefaultDataSource());
		int k = ipFilterMapper.updataIp(ipFilter);
		FilterParamsUtil.setInit(true);
		return k;
	}
	
	@Override
	public int delIp(String id){
		//切换到默认数据源
		DataSource.setDataSource(DataSourceUtil.getDefaultDataSource());
		int k = ipFilterMapper.delIp(id);
		FilterParamsUtil.setInit(true);
		return k;
	}

	@Override
	public List<SysIPFilter> getIPFilterList(boolean isAllowed) {
		//切换到默认数据源
		DataSource.setDataSource(DataSourceUtil.getDefaultDataSource());
		return ipFilterMapper.getIPFilterList(isAllowed);
	}

	@Override
	public SysIPFilter selectById(String id) {
		return ipFilterMapper.selectById(id);
	}
	
	
}
