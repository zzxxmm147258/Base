package com.hibo.cms.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.comment.model.CommentUserBas;

public interface CommentUserBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommentUserBas record);

    int insertSelective(CommentUserBas record);

    CommentUserBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommentUserBas record);

    int updateByPrimaryKey(CommentUserBas record);
    
    List<CommentUserBas> selectBymId(@Param("mId")String mId,@Param("page")int page,@Param("limit")int limit);

}