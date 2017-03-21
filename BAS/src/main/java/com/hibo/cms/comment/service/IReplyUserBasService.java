package com.hibo.cms.comment.service;

import java.util.List;

import com.hibo.cms.comment.model.CommentUserBas;
import com.hibo.cms.comment.model.ReplyUserBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月31日 下午1:47:54</p>
 * <p>类全名：com.hibo.cms.comment.service.IReplyUserBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IReplyUserBasService {

	int deleteByPrimaryKey(String id);

    int insert(ReplyUserBas record,CommentUserBas com,ReplyUserBas re);

    int insertSelective(ReplyUserBas record);

    ReplyUserBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReplyUserBas record);

    int updateByPrimaryKey(ReplyUserBas record);
    
    /**
     * <p>功能：查询此评论的<p>
     * <p>创建日期：2016年6月12日 下午2:42:56<p>
     * <p>作者：曾小明<p>
     * @param commentId
     * @param page
     * @param limit
     * @return
     */
    List<ReplyUserBas> selectByCommentId(String commentId,int page,int limit);
}
