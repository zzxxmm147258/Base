package com.hibo.cms.quartz.service.impl;

import java.util.Date;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.quartz.dao.QuartzUpdateBasMapper;
import com.hibo.cms.quartz.model.QuartzUpdateBas;
import com.hibo.cms.quartz.service.IQuartzUpdateBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月26日 下午4:37:21</p>
 * <p>类全名：com.hibo.cms.quartz.service.impl.QuartzUpdateBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class QuartzUpdateBasServiceImpl implements IQuartzUpdateBasService{

	@Autowired
	private QuartzUpdateBasMapper quartzUpdateBasMapper;
	@Override
	public int deleteByPrimaryKey(String id) {
		return quartzUpdateBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(QuartzUpdateBas record) {
		return quartzUpdateBasMapper.insert(record);
	}

	@Override
	public int insertSelective(QuartzUpdateBas record) {
		return quartzUpdateBasMapper.insertSelective(record);
	}

	@Override
	public QuartzUpdateBas selectByPrimaryKey(String id) {
		return quartzUpdateBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(QuartzUpdateBas record) {
		return quartzUpdateBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(QuartzUpdateBas record) {
		return quartzUpdateBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<QuartzUpdateBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return quartzUpdateBasMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	public Date getDatebyId(String id) {
		Date date = quartzUpdateBasMapper.getDatebyId(id);
		if (date == null)
			date = new Date();
		return date;
	}

	@Override
	public int updateDatebyId(String id, Date updateDate) {
		return quartzUpdateBasMapper.updateDatebyId(id, updateDate);
	}
	
	@Override
	public int getLocked(String id) {
		return quartzUpdateBasMapper.getLocked(id);
	}
	
	@Override
	public int releaseLocked(String id) {
		return quartzUpdateBasMapper.releaseLocked(id);
	}

}
