package com.hibo.cms.comment.dao;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.comment.model.PraiseUserBas;

public interface PraiseUserBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(PraiseUserBas record);

    int insertSelective(PraiseUserBas record);

    PraiseUserBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PraiseUserBas record);

    int updateByPrimaryKey(PraiseUserBas record);
    
    PraiseUserBas selectBymId(@Param("mId")String mId,@Param("userid")String userid);
    
    int selectCount(String commentId);
}