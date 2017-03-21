package com.hibo.cms.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.hibo.cms.comment.model.Comment;

public interface CommentBasMapper {
	
    List<Comment> selectBymId(@Param("mId")String mId,@Param("type")String type,PageBounds pageBounds);
    
    List<Comment> selectByUserId(@Param("userId")String userId,PageBounds pageBounds);

    Comment selectByName(String id);
}