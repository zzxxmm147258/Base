package com.hibo.cms.push.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.push.dao.PushDeviceBasMapper;
import com.hibo.cms.push.model.PushDeviceBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月8日 下午5:24:57</p>
 * <p>类全名：com.hibo.cms.push.service.PushDeviceBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class PushDeviceBasServiceImpl implements IPushDeviceBasService{

	@Autowired
	private PushDeviceBasMapper pushDeviceMapper;

	@Override
	public int addPushDevice(PushDeviceBas record) {
		return pushDeviceMapper.insert(record);
	}

	@Override
	public int updatePushDevice(PushDeviceBas record) {
		return pushDeviceMapper.updateByPrimaryKey(record);
	}

	@Override
	public String[] findByUserId(String userId) {
		
		PushDeviceBas bean = pushDeviceMapper.selectByPrimaryKey(userId);
		if(bean != null){
			String[] str = new String[2];
			str[0] = bean.getDeviceId();//设备ID
			str[1] = bean.getAttrName1();//设备
			return str;
		}
		return null;
	}

	@Override
	public List<PushDeviceBas> findAll() {
		return pushDeviceMapper.findAll();
	}

	@Override
	public List<PushDeviceBas> findIosOrAndroidAll(String attrName1) {
		return this.pushDeviceMapper.findIosOrAndroidAll(attrName1);
	}
}
