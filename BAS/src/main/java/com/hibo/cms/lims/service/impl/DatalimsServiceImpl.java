package com.hibo.cms.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.lims.dao.DatalimsMapper;
import com.hibo.cms.lims.model.Datalims;
import com.hibo.cms.lims.model.DatalimsKey;
import com.hibo.cms.lims.service.IDatalimsService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午1:39:18</p>
 * <p>类全名：com.hibo.cms.lims.service.impl.DatalimsServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service("datalimsServiceImpl")
public class DatalimsServiceImpl implements IDatalimsService{
	
	@Autowired
	private DatalimsMapper datalimsMapper;
	@Override
	public List<Datalims> selectAll() {
		return datalimsMapper.selectAll();
	}

	@Override
	public int insert(Datalims datalims) {
		return datalimsMapper.insert(datalims);
	}

	@Override
	public int deleteByDatalimsId(String datalimsid) {
		return datalimsMapper.deleteByDatalimsId(datalimsid);
	}

	@Override
	public int updateByDatalimsId(String olddatalimsid, Datalims datalims) {
		return datalimsMapper.updateByDatalimsId(olddatalimsid, datalims);
	}

	@Override
	public List<Datalims> selectDatalims(String datalimmid) {
		return datalimsMapper.selectDatalims(datalimmid);
	}

	@Override
	public int delete(String limid) {
		return datalimsMapper.delete(limid);
	}

	@Override
	public Datalims selectByPrimaryKey(DatalimsKey key) {
		return datalimsMapper.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(DatalimsKey key,Datalims record) {
		return datalimsMapper.updateByPrimaryKeySelective(key,record);
	}

	@Override
	public int deleteByPrimaryKey(DatalimsKey key) {
		return datalimsMapper.deleteByPrimaryKey(key);
	}

}
