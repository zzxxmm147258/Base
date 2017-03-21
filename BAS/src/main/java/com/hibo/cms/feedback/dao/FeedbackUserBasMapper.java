package com.hibo.cms.feedback.dao;

import java.util.List;

import com.hibo.cms.feedback.model.FeedbackUserBas;

public interface FeedbackUserBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(FeedbackUserBas record);

    int insertSelective(FeedbackUserBas record);

    FeedbackUserBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FeedbackUserBas record);

    int updateByPrimaryKey(FeedbackUserBas record);
    
    List<FeedbackUserBas> selectByUserId(String userid);
}