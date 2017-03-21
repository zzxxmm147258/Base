package com.hibo.cms.filter.service;

import java.util.List;

import com.hibo.cms.filter.model.SysIPFilter;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月17日 下午6:54:49</p>
 * <p>类全名：com.hibo.cms.filter.service.IPFilterService</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public interface IPFilterService {
	
	public List<SysIPFilter> getIPFilterList(boolean isAllowed);
	
	public int addIp(SysIPFilter ipFilter);
	
	public int updataIp(SysIPFilter ipFilter);
	
	public int delIp(String id);
	
	public SysIPFilter selectById(String id);
}
