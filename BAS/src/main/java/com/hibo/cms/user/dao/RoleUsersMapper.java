package com.hibo.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Role;
import com.hibo.cms.user.model.RoleUsersKey;
import com.hibo.cms.user.model.User;


public interface RoleUsersMapper {
    int deleteByPrimaryKey(RoleUsersKey roleUser);

    int insert(RoleUsersKey roleUser);

//    int insertSelective(RoleUsersKey record);
	List<RoleUsersKey> selectAll();
	
	List<String> selectRoleIdByUserId(String userid);
	
	int insertList(List<RoleUsersKey> roleUsers);
	
	RoleUsersKey select(@Param("roleid")String roleid,@Param("userid")String userid);
	
	int update(@Param("oldroleid")String roleid,@Param("olduserid")String userid,@Param("ru")RoleUsersKey ru);
	
	/**
	 * 通过userid删除所有对应ru关系
	 * @param userid
	 * @return
	 */
	int deleteRoleIdByUserId(String userid);
	
	/**
	 * 通过roleid删除所有对应ru关系
	 * @param roleid
	 * @return
	 */
	int deleteByRoleId(String roleid);
	
	
	/**
	 * 条件查找
	 * @return
	 */
	PageList<RoleUsersKey> selectByCondition(RoleUsersKey ru,PageBounds pageBounds);
	
	/**
	 * 修改用户或角色时，同时修改ru关系中的对应字段
	 * @param ru
	 * @param oldroleid
	 * @param olduserid
	 * @return
	 */
	int updateByRoleUsers(@Param("ru")RoleUsersKey ru,@Param("oldroleid")String oldroleid,@Param("olduserid")String olduserid);
	
	/**
	 * <p>功能： 为这个用户设置角色</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年11月10日 上午10:28:22</p>
	 * @param user
	 * @param roleUserList
	 * @return
	 */
	int insertRuList(@Param("user")User user,@Param("list")List<RoleUsersKey> roleUserList);
}