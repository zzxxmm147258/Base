package com.hibo.cms.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.RoleMenuKey;

public interface RoleMenuMapper {
	
	List<RoleMenuKey> selectAll();
	
	List<String> selectMenuIdsByRoleId(String roleid);
	
	/**
	 * 根据roleid删除对应的rm关系
	 * @param roleid
	 * @return
	 */
	int deleteMenuIdByRoleId(String roleid);
	
	/**
	 * 根据menuid删除对应的rm关系
	 * @param menuid
	 * @return
	 */
	int deleteByMenuId(String menuid);
	
	int insertList(List<RoleMenuKey> roleMenus);
	
    int deleteByPrimaryKey(RoleMenuKey roleMenu);

    int insert(RoleMenuKey roleMenu);

//    int insertSelective(RoleMenuKey record);
    
    RoleMenuKey select(@Param("roleid")String roleid,@Param("menuid")String menuid);
    
    int update(@Param("oldroleid")String roleid,@Param("oldmenuid")String menuid,@Param("rm")RoleMenuKey rm);
    
    /**
	 * 条件查找
	 * @return
	 */
	PageList<RoleMenuKey> selectByCondition(RoleMenuKey rm,PageBounds pageBounds);
	
	/**
	 * 修改菜单或角色时，同时修改rm关系中的对应字段
	 * @param rm
	 * @param oldroleid
	 * @param oldmenuid
	 * @return
	 */
	int updateByRoleMenu(@Param("rm")RoleMenuKey rm,@Param("oldroleid")String oldroleid,@Param("oldmenuid")String oldmenuid);
}