package com.hibo.cms.comment.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.hibo.cms.comment.model.Comment;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年6月13日 下午3:36:43</p>
 * <p>类全名：com.hibo.cms.comment.service.ICommentService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface ICommentBasService {

    List<Comment> selectBymId(@Param("mId")String mId,@Param("type")String type,PageBounds pageBounds);

    /**
     * <p>功能：查询单条评论或者回复<p>
     * <p>创建日期：2016年6月14日 下午7:38:29<p>
     * <p>作者：曾小明<p>
     * @param id
     * @return
     */
    Comment selectByName(String id);
    
    List<Comment> selectByUserId(@Param("userId")String userId,PageBounds pageBounds);

	
}
