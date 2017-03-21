package com.hibo.cms.visit.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.visit.model.VisitBas;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月5日 上午10:20:00</p>
 * <p>类全名：com.hibo.cms.visit.service.IVisitBasService</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public interface IVisitBasService {
	
	int insert(VisitBas record);
	
	Map<String,Object> selectDataMap(@Param("sql")String sql);
	
    List<LinkedHashMap<String,Object>> selectDataList(@Param("sql")String sql);
}
