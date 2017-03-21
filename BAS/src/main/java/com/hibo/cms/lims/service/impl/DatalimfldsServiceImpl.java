package com.hibo.cms.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.lims.dao.DatalimfldsMapper;
import com.hibo.cms.lims.model.Datalimflds;
import com.hibo.cms.lims.model.DatalimfldsKey;
import com.hibo.cms.lims.service.IDatalimfldsService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午10:31:36</p>
 * <p>类全名：com.hibo.cms.lims.service.impl.DatalimfldsServiceImpl</p>
 * 作者：崔志敏
 * 初审：
 * 复审：
 */
@Service("datalimfldsServiceImpl")
public class DatalimfldsServiceImpl implements IDatalimfldsService{
   
	@Autowired
	private DatalimfldsMapper datalimfldsMapper;
	
	@Override
	public List<Datalimflds> selectAll() {
		return datalimfldsMapper.selectAll();
	}

	@Override
	public List<Datalimflds> selectDatalimflds(String datalimmid) {
		return datalimfldsMapper.selectDatalimflds(datalimmid);
	}

	@Override
	public int insert(Datalimflds datalimflds) {
		return datalimfldsMapper.insert(datalimflds);
	}

	@Override
	public int deleteByDatalimfldsId(String datalimfldsid) {
		return datalimfldsMapper.deleteByDatalimfldsId(datalimfldsid);
	}

	@Override
	public int updateByDatalimfldsId(String olddatalimfldsid, Datalimflds datalimflds) {
		return datalimfldsMapper.updateByDatalimfldsId(olddatalimfldsid, datalimflds);
	}

	@Override
	public int delete(String limid) {
		return datalimfldsMapper.delete(limid);
	}

	@Override
	public int deleteByPrimaryKey(DatalimfldsKey key) {
		return datalimfldsMapper.deleteByPrimaryKey(key);
	}

	@Override
	public Datalimflds selectByPrimaryKey(DatalimfldsKey key) {
		return datalimfldsMapper.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(DatalimfldsKey key,Datalimflds record) {
		return datalimfldsMapper.updateByPrimaryKeySelective(key, record);
	}

}
