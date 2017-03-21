package com.hibo.cms.comment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.comment.dao.CommentUserBasMapper;
import com.hibo.cms.comment.model.CommentUserBas;
import com.hibo.cms.comment.service.ICommentUserBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月31日 下午1:49:00</p>
 * <p>类全名：com.hibo.cms.comment.service.impl.CommentUserBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class CommentUserBasServiceImpl implements ICommentUserBasService{

	@Autowired
	private CommentUserBasMapper commentUserBasMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return commentUserBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CommentUserBas record) {
		return commentUserBasMapper.insert(record);
	}

	@Override
	public int insertSelective(CommentUserBas record) {
		return commentUserBasMapper.insertSelective(record);
	}

	@Override
	public CommentUserBas selectByPrimaryKey(String id) {
		return commentUserBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CommentUserBas record) {
		return commentUserBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CommentUserBas record) {
		return commentUserBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<CommentUserBas> selectBymId(String mId, int page, int limit) {
		return commentUserBasMapper.selectBymId(mId, page, limit);
	}

}
