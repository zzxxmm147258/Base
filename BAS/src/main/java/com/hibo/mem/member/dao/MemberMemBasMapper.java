package com.hibo.mem.member.dao;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.user.model.User;
import com.hibo.mem.member.model.BaseMem;

public interface MemberMemBasMapper {
	
	User selectByUsername(@Param("username")String username,@Param("utype")String utype);
	
    BaseMem selectBaseMem(String userid);
    
}