package com.hibo.cms.comment.service.impl;

import java.beans.Transient;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.comment.dao.CommentUserBasMapper;
import com.hibo.cms.comment.dao.ReplyUserBasMapper;
import com.hibo.cms.comment.model.CommentUserBas;
import com.hibo.cms.comment.model.ReplyUserBas;
import com.hibo.cms.comment.service.IReplyUserBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月31日 下午1:48:20</p>
 * <p>类全名：com.hibo.cms.comment.service.impl.ReplyUserBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class ReplyUserBasServiceImpl implements IReplyUserBasService{

	@Autowired
	private ReplyUserBasMapper  replyUserBasMapper;

	@Autowired
	private CommentUserBasMapper  commentUserBasMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return replyUserBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transient
	public int insert(ReplyUserBas record,CommentUserBas com,ReplyUserBas re) {
		if(com!=null){
		   commentUserBasMapper.updateByPrimaryKeySelective(com);
		}
		if(re!=null){
			replyUserBasMapper.updateByPrimaryKey(re);
		}
		return replyUserBasMapper.insert(record);
	}

	@Override
	public int insertSelective(ReplyUserBas record) {
		return replyUserBasMapper.insertSelective(record);
	}

	@Override
	public ReplyUserBas selectByPrimaryKey(String id) {
		return replyUserBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ReplyUserBas record) {
		return replyUserBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ReplyUserBas record) {
		return replyUserBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ReplyUserBas> selectByCommentId(String commentId, int page, int limit) {
		return replyUserBasMapper.selectByCommentId(commentId, page, limit);
	}

	
}
