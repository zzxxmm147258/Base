package com.hibo.cms.token.dao;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.token.model.TokenBas;

public interface TokenBasMapper {
	
    int deleteByPrimaryKey(String tokenNo);
    
    int deleteByPrimaryUserName(@Param("username")String username,@Param("utype")String utype);

    int insert(TokenBas record);

    int insertSelective(TokenBas record);

    TokenBas selectByPrimaryKey(String tokenNo);
    
    TokenBas selectByPrimaryUserName(@Param("username")String username,@Param("utype")String utype);

    int updateByPrimaryKeySelective(TokenBas record);

    int updateByPrimaryKey(TokenBas record);
}