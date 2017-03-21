package com.hibo.cms.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.lims.dao.DatalimmMapper;
import com.hibo.cms.lims.model.Datalimm;
import com.hibo.cms.lims.service.IDatalimmService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 上午9:16:08</p>
 * <p>类全名：com.hibo.cms.lims.service.impl.DatalimmServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service("datalimmServiceImpl")
public class DatalimmServiceImpl implements IDatalimmService{
  
	@Autowired
	private DatalimmMapper datalimmMapper;
	
	@Override
	public List<Datalimm> selectAll() {
		return datalimmMapper.selectAll();
	}

	@Override
	public int insert(Datalimm datalimm) {
		return datalimmMapper.insert(datalimm);
	}

	@Override
	public int deleteByDatalimmId(String datalimmid) {
		return datalimmMapper.deleteByDatalimmId(datalimmid);
	}

	@Override
	public int updateByDatalimmId(String olddatalimmid, Datalimm datalimm) {
		return datalimmMapper.updateByDatalimmId(olddatalimmid, datalimm);
	}

	@Override
	public Datalimm selectByDatalimmId(String limid) {
		return datalimmMapper.selectByDatalimmId(limid);
	}

}
