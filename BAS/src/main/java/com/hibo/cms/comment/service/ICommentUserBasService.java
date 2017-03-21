package com.hibo.cms.comment.service;

import java.util.List;

import com.hibo.cms.comment.model.CommentUserBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月31日 下午1:47:13</p>
 * <p>类全名：com.hibo.cms.comment.service.ICommentUserBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface ICommentUserBasService {

	int deleteByPrimaryKey(String id);

    int insert(CommentUserBas record);

    int insertSelective(CommentUserBas record);

    CommentUserBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommentUserBas record);

    int updateByPrimaryKey(CommentUserBas record);
    
    /**
     * <p>功能：查询此商品的评论<p>
     * <p>创建日期：2016年6月12日 下午2:43:42<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param page
     * @param limit
     * @return
     */
    List<CommentUserBas> selectBymId(String mId,int page,int limit);
}
