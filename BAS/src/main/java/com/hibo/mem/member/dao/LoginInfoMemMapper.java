package com.hibo.mem.member.dao;

import com.hibo.mem.member.model.LoginInfoMem;

public interface LoginInfoMemMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoginInfoMem record);

    int insertSelective(LoginInfoMem record);

    LoginInfoMem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoginInfoMem record);

    int updateByPrimaryKey(LoginInfoMem record);
}