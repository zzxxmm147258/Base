package com.hibo.cms.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.lims.dao.DatalimopMapper;
import com.hibo.cms.lims.model.Datalimop;
import com.hibo.cms.lims.service.IDatalimopService;
import com.hibo.cms.lims.model.DatalimopKey;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午11:12:35</p>
 * <p>类全名：com.hibo.cms.lims.service.impl.DatalimopServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service("datalimopServiceImpl")
public class DatalimopServiceImpl implements IDatalimopService{

	@Autowired
	private DatalimopMapper datalimopMapper;
	@Override
	public List<Datalimop> selectAll() {
		return datalimopMapper.selectAll();
	}

	@Override
	public List<Datalimop> selectDatalimop(String datalimmid) {
		return datalimopMapper.selectDatalimop(datalimmid);
	}

	@Override
	public int insert(Datalimop datalimop) {
		return datalimopMapper.insert(datalimop);
	}

	@Override
	public int deleteByDatalimopId(String datalimopid) {
		return datalimopMapper.deleteByDatalimopId(datalimopid);
	}

	@Override
	public int updateByDatalimopId(String olddatalimopid, Datalimop datalimop) {
		return datalimopMapper.updateByDatalimopId(olddatalimopid, datalimop);
	}

	@Override
	public int delete(String limid) {
		return datalimopMapper.delete(limid);
	}

	@Override
	public int deleteByPrimaryKey(DatalimopKey key) {
		return datalimopMapper.deleteByPrimaryKey(key);
	}

	@Override
	public Datalimop selectByPrimaryKey(DatalimopKey key) {
		return datalimopMapper.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(DatalimopKey key,Datalimop record) {
		return datalimopMapper.updateByPrimaryKeySelective(key, record);
	}

}
