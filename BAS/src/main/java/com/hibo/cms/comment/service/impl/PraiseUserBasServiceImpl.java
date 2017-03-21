package com.hibo.cms.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.comment.dao.PraiseUserBasMapper;
import com.hibo.cms.comment.model.PraiseUserBas;
import com.hibo.cms.comment.service.IPraiseUserBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月31日 下午1:49:36</p>
 * <p>类全名：com.hibo.cms.comment.service.impl.PraiseUserBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class PraiseUserBasServiceImpl implements IPraiseUserBasService{

	@Autowired
	private PraiseUserBasMapper praiseUserBasMapper;
	
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return praiseUserBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(PraiseUserBas record) {
		return praiseUserBasMapper.insert(record);
	}

	@Override
	public int insertSelective(PraiseUserBas record) {
		return praiseUserBasMapper.insertSelective(record);
	}

	@Override
	public PraiseUserBas selectByPrimaryKey(String id) {
		return praiseUserBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(PraiseUserBas record) {
		return praiseUserBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PraiseUserBas record) {
		return praiseUserBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PraiseUserBas selectBymId(String mId, String userid) {
		return praiseUserBasMapper.selectBymId(mId, userid);
	}

	@Override
	public int selectCount(String commentId) {
		return praiseUserBasMapper.selectCount(commentId);
	}

	
}
