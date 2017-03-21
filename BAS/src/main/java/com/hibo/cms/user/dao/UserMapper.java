package com.hibo.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.User;

public interface UserMapper {
	
User queryUserByUserName(@Param("username")String username);
	
    int deleteByPrimaryKey(String userid);

    int insert(User user);

    int insertSelective(User user);

    User selectByUserId(String userid);
    
    PageList<User> selectUserList(PageBounds pageBounds);
    
    List<User> selectUserList();

    int updateByPrimaryKeySelective(User user);
    
    int updateUserid(@Param("userid")String userid,@Param("olduserid") String olduserid);

    //int update(User user);
    
    int changePassword(@Param("salt")String salt,@Param("password")String password,@Param("userid")String userid);
    
    int deleteByUserId(String userid);
    
    
    int changePayPass(@Param("salt")String salt,@Param("password")String password,@Param("userid")String userid);

	// add by mjd
	User queryUserByPhone(String mobile);
	
	/**
	 * 分页条件查找
	 * @param user
	 * @return
	 */
	PageList<User> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
	
	/**
	 * 条件查找
	 * @param user
	 * @return
	 */
	List<User> selectByCondition();
	
	/**
	 * 模糊查找
	 * @param user
	 * @return
	 */
	List<User> selectFuzzy(User user);
}