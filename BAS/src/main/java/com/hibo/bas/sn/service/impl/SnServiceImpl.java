package com.hibo.bas.sn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibo.bas.sn.dao.SnMapper;
import com.hibo.bas.sn.model.Sn;
import com.hibo.bas.sn.service.ISnService;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年8月25日 下午3:17:23
 * </p>
 * <p>
 * 类全名：com.hibo.ebusi.sn.service.impl.SnService
 * </p>
 * 作者：马骏达 初审： 复审：
 */
@Service("SnServiceImpl")
public class SnServiceImpl implements ISnService {

	private SnMapper snMapper;

	public SnMapper getSnMapper() {
		return snMapper;
	}

	@Autowired
	public void setSnMapper(SnMapper snMapper) {
		this.snMapper = snMapper;
	}

	@Override
	@Transactional(readOnly = false)
	public synchronized String generateSn(int type) {
		return generate(type);
	}

	public String generate(int type) {
		Sn sn = snMapper.selectByType(type);
		String value = "";
		if (sn != null) {
			Long lastValue = sn.getLastValue() + 1L;
			sn.setLastValue(lastValue);
			snMapper.updateByPrimaryKeySelective(sn);
			value = sn.getPrefix() + lastValue.toString();
		}
		return value;
	}

}
