package com.hibo.cms.user.dao;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.RoleResourceKey;

public interface RoleResourceMapper {
    int delete(String roreid);
    
    /**
     * 根据roleid删除对应的rr关系
     * @param roleid
     * @return
     */
    int deleteByRoleid(String roleid);
    
    /**
     * 根据resourceid删除对应的rr关系
     * @param resourceid
     * @return
     */
    int deleteByResourceid(String resourceid);

    int insert(RoleResourceKey rr);

    RoleResourceKey select(String roreid);

    int update(RoleResourceKey rr);
    
    /**
	 * 条件查找
	 * @return
	 */
	PageList<RoleResourceKey> selectByCondition(RoleResourceKey rr,PageBounds pageBounds);
	
	/**
	 * 修改资源或角色时，同时修改rr关系中的对应字段
	 * @param rr
	 * @param oldroleid
	 * @param oldresourceid
	 * @return
	 */
	int updateByRoleResource(@Param("rr")RoleResourceKey rr,@Param("oldroleid")String oldroleid,@Param("oldresourceid")String oldresourceid,@Param("oldoperaid")String oldoperaid);

}