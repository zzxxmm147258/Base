package com.hibo.cms.comment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.hibo.cms.comment.dao.CommentBasMapper;
import com.hibo.cms.comment.model.Comment;
import com.hibo.cms.comment.service.ICommentBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年6月13日 下午3:37:12</p>
 * <p>类全名：com.hibo.cms.comment.service.impl.CommentServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class CommentBasServiceImpl implements ICommentBasService{

	@Autowired
	private CommentBasMapper commentMapper;
	
	@Override
	public List<Comment> selectBymId(String mId,String type, PageBounds pageBounds) {
		return commentMapper.selectBymId(mId, type, pageBounds);
	}

	@Override
	public Comment selectByName(String id) {
		return commentMapper.selectByName(id);
	}

	@Override
	public List<Comment> selectByUserId(String userId, PageBounds pageBounds) {
		return commentMapper.selectByUserId(userId,pageBounds);
	}

}
