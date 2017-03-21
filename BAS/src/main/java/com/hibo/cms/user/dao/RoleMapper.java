package com.hibo.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Role;

public interface RoleMapper {
    int deleteByRoleId(String roleid);

    int insert(Role role);

//    int insertSelective(Role record);

    Role selectByRoleId(String roleid);

//    int updateByPrimaryKeySelective(Role record);

    int update(@Param("oldroleid") String oldroleid,@Param("role") Role role);
    
	List<Role> selectAllRoles();
	
	List<Role> selectByUserId(String userid);
	
	/**
	 * 条件查找
	 * @return
	 */
	PageList<Role> selectByCondition(Role role,PageBounds pageBounds);
}