package com.hibo.cms.advert.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.advert.dao.AdvertGroupBasMapper;
import com.hibo.cms.advert.model.AdvertGroupBas;
import com.hibo.cms.advert.service.IAdvertGroupBasService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月3日 下午2:59:43</p>
 * <p>类全名：com.hibo.cms.advert.service.impl.AdvertGroupBasServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class AdvertGroupBasServiceImpl implements IAdvertGroupBasService{
	
	@Autowired
	private AdvertGroupBasMapper advertGroupBasMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return advertGroupBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AdvertGroupBas advertGroupBas) {
		return advertGroupBasMapper.insertSelective(advertGroupBas);
	}

	@Override
	public AdvertGroupBas selectByPrimaryKey(String id) {
		return advertGroupBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updatePrimaryKey(AdvertGroupBas advertGroupBas) {
		return advertGroupBasMapper.updateByPrimaryKeySelective(advertGroupBas);
	}

	@Override
	public List<AdvertGroupBas> selectAll() {
		return advertGroupBasMapper.selectAll();
	}

}
