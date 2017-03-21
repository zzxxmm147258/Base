package com.hibo.cms.weixin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.weixin.dao.WeiXinBusMemMapper;
import com.hibo.cms.weixin.model.WeiXinBusMem;
import com.hibo.cms.weixin.service.IWeiXinBusMemService;


/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月25日 下午3:55:47</p>
 * <p>类全名：com.hibo.mem.weixin.service.impl.WeiXinMemServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class WeiXinBusMemServiceImpl implements IWeiXinBusMemService{

	@Autowired
	private WeiXinBusMemMapper weiXinBusMemMapper;

	@Override
	public int deleteByPrimaryKey(String openid) {
		return weiXinBusMemMapper.deleteByPrimaryKey(openid);
	}

	@Override
	public int insert(WeiXinBusMem record) {
		return weiXinBusMemMapper.insert(record);
	}

	@Override
	public int insertSelective(WeiXinBusMem record) {
		return weiXinBusMemMapper.insertSelective(record);
	}

	@Override
	public WeiXinBusMem selectByPrimaryKey(String openid) {
		return weiXinBusMemMapper.selectByPrimaryKey(openid);
	}

	@Override
	public int updateByPrimaryKeySelective(WeiXinBusMem record) {
		return weiXinBusMemMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WeiXinBusMem record) {
		return weiXinBusMemMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<WeiXinBusMem> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return weiXinBusMemMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	public List<WeiXinBusMem> select3All(String mId) {
		return weiXinBusMemMapper.select3All(mId);
	}

	
}
