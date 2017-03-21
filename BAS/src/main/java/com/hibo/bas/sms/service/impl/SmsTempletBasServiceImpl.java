package com.hibo.bas.sms.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.sms.dao.SmsTempletBasMapper;
import com.hibo.bas.sms.model.SmsTempletBas;
import com.hibo.bas.sms.service.ISmsTempletBasService;
import com.hibo.cms.lims.QueryFilterBuilder;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月4日 下午2:55:54</p>
 * <p>类全名：com.hibo.bas.sms.service.impl.SmsTempletBasServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class SmsTempletBasServiceImpl implements ISmsTempletBasService{

	@Autowired
	private SmsTempletBasMapper smsTempletBasMapper;
	
	@Override
	public SmsTempletBas selectByPrimaryKey(String tempNo) {
		return smsTempletBasMapper.selectByPrimaryKey(tempNo);
	}

	@Override
	public int insert(SmsTempletBas smsTempletBas) {
		return smsTempletBasMapper.insert(smsTempletBas);
	}

	@Override
	public int deleteByPrimaryKey(String tempNo) {
		return smsTempletBasMapper.deleteByPrimaryKey(tempNo);
	}

	@Override
	public int updateByPrimaryKey(SmsTempletBas smsTempletBas) {
		return smsTempletBasMapper.updateByPrimaryKey(smsTempletBas);
	}

	@Override
	public int updateByPrimaryKeySelective(SmsTempletBas smsTempletBas) {
		return smsTempletBasMapper.updateByPrimaryKeySelective(smsTempletBas);
	}

	@Override
	public int insertSelective(SmsTempletBas smsTempletBas) {
		return smsTempletBasMapper.insertSelective(smsTempletBas);
	}

	@Override
	public PageList<SmsTempletBas> selectPage(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return smsTempletBasMapper.selectPage(wStr, pageBounds);
	}

}
