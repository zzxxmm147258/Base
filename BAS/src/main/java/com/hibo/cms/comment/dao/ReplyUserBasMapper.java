package com.hibo.cms.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.comment.model.ReplyUserBas;

public interface ReplyUserBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReplyUserBas record);

    int insertSelective(ReplyUserBas record);

    ReplyUserBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReplyUserBas record);

    int updateByPrimaryKey(ReplyUserBas record);
    
    List<ReplyUserBas> selectByCommentId(@Param("commentId")String commentId,@Param("page")int page,@Param("limit")int limit);
}