package com.hibo.cms.feedback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.feedback.dao.FeedbackUserBasMapper;
import com.hibo.cms.feedback.model.FeedbackUserBas;
import com.hibo.cms.feedback.service.IFeedbackUserBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月21日 下午1:41:02</p>
 * <p>类全名：com.hibo.cms.feedback.service.impl.FeedbackUserBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class FeedbackUserBasServiceImpl implements IFeedbackUserBasService{

	@Autowired
	private FeedbackUserBasMapper feedbackUserBasMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return feedbackUserBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FeedbackUserBas record) {
		return feedbackUserBasMapper.insert(record);
	}

	@Override
	public int insertSelective(FeedbackUserBas record) {
		return feedbackUserBasMapper.insertSelective(record);
	}

	@Override
	public FeedbackUserBas selectByPrimaryKey(String id) {
		return feedbackUserBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FeedbackUserBas record) {
		return feedbackUserBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FeedbackUserBas record) {
		return feedbackUserBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<FeedbackUserBas> selectByUserId(String userid) {
		return feedbackUserBasMapper.selectByUserId(userid);
	}
}
