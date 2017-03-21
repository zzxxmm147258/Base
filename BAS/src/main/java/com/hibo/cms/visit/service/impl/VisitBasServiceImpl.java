package com.hibo.cms.visit.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hibo.cms.visit.dao.VisitBasMapper;
import com.hibo.cms.visit.model.VisitBas;
import com.hibo.cms.visit.service.IVisitBasService;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月5日 上午10:25:29</p>
 * <p>类全名：com.hibo.cms.visit.service.impl.VisitBasServiceImpl</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Service
public class VisitBasServiceImpl implements IVisitBasService {
	
	@Resource
	private VisitBasMapper visitBasMapper;
	
	@Override
	public Map<String, Object> selectDataMap(String sql) {
		return visitBasMapper.selectDataMap(sql);
	}

	@Override
	public List<LinkedHashMap<String, Object>> selectDataList(String sql) {
		return visitBasMapper.selectDataList(sql);
	}

	@Override
	public int insert(VisitBas record) {
		return visitBasMapper.insert(record);
	}

}
