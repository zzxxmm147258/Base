package com.hibo.cms.language.dao;

import com.hibo.cms.language.model.UserLanguageBas;

public interface UserLanguageBasMapper {
    int deleteByPrimaryKey(String userid);

    int insert(UserLanguageBas record);

    int insertSelective(UserLanguageBas record);

    UserLanguageBas selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(UserLanguageBas record);

    int updateByPrimaryKey(UserLanguageBas record);
}