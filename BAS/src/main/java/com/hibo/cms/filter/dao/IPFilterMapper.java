package com.hibo.cms.filter.dao;

import java.util.List;

import com.hibo.cms.filter.model.SysIPFilter;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月16日 下午1:52:47</p>
 * <p>类全名：com.hibo.cms.filter.dao.IPFilterMapper</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public interface IPFilterMapper {
	
	List<SysIPFilter> getIPFilterList(boolean isAllowed);
	
	int addIp(SysIPFilter ipFilter);
	
	int updataIp(SysIPFilter ipFilter);
	
	int delIp(String id);
	SysIPFilter selectById(String id);

}
