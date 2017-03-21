package com.hibo.cms.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.lims.dao.BusheetlimMapper;
import com.hibo.cms.lims.model.Busheetlim;
import com.hibo.cms.lims.model.BusheetlimKey;
import com.hibo.cms.lims.service.IBusheetlimService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午9:47:00</p>
 * <p>类全名：com.hibo.cms.lims.service.impl.BusheetlimServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service("busheetlimServiceImpl")
public class BusheetlimServiceImpl implements IBusheetlimService{

	@Autowired
	private BusheetlimMapper busheetlimMapper;
	
	@Override
	public List<Busheetlim> selectAll() {
		return busheetlimMapper.selectAll();
	}

	@Override
	public List<Busheetlim> selectBusheetlim(String datalimmid) {
		return busheetlimMapper.selectBusheetlim(datalimmid);
	}

	@Override
	public int insert(Busheetlim busheetlim) {
		return busheetlimMapper.insert(busheetlim);
	}

	@Override
	public int deleteByBusheetlimId(String busheetlimid) {
		return busheetlimMapper.deleteByBusheetlimId(busheetlimid);
	}

	@Override
	public int updateByBusheetlimId(String oldbusheetlimid, Busheetlim busheetlim) {
		return busheetlimMapper.updateByBusheetlimId(oldbusheetlimid, busheetlim);
	}

	@Override
	public int delete(String limid) {
		return busheetlimMapper.delete(limid);
	}

	@Override
	public Busheetlim selectByPrimaryKey(BusheetlimKey key) {
		return busheetlimMapper.selectByPrimaryKey(key);
	}

	@Override
	public int deleteByPrimaryKey(BusheetlimKey key) {
		return busheetlimMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(BusheetlimKey key,Busheetlim record) {
		return busheetlimMapper.updateByPrimaryKeySelective(key, record);
	}

}
