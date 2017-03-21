package com.hibo.cms.user.service.ralation;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.RoleMenuKey;
import com.hibo.cms.user.model.RoleResourceKey;
import com.hibo.cms.user.model.RoleUsersKey;

public interface IRalationService {

	/**
	 * 从role_users关系表中获取全部关系
	 * @return
	 */
	List<RoleUsersKey> selectAllRoleUser();
	/**
	 * role_menu关系表中获取全部关系
	 * @return
	 */
	List<RoleMenuKey> selectAllRoleMenu();
	
	/**
	 * 删除role_users中的某一条关系
	 * @param roleUser
	 * @return
	 */
	int deleteRuByPrimaryKey(RoleUsersKey roleUser);
	
	/**
	 * 删除role_menu中的某一条关系
	 * @param roleMenu
	 * @return
	 */
	int deleteRmByPrimaryKey(RoleMenuKey roleMenu);
	
	/**
	 * 删除role_resource中的某一条关系
	 * @param roreid
	 * @return
	 */
	int deleteRrByPrimaryKey(String roreid);
	
	/**
	 * 插入一条role_users关系
	 * @param roleUser
	 * @return
	 */
	int insertRu(RoleUsersKey roleUser);
	
	/**
	 * 插入一条role_menu关系
	 * @param roleMenu
	 * @return
	 */
	int insertRm(RoleMenuKey roleMenu);
	
	/**
	 * 插入一条role_resource关系
	 * @param roleResource
	 * @return
	 */
	int insertRr(RoleResourceKey roleResource);
	
	/**
	 * 条件查找ru
	 * @return
	 */
	PageList<RoleUsersKey> selectRuByCondition(RoleUsersKey ru,PageBounds pageBounds);

	 /**
	 * 条件查找rm
	 * @return
	 */
	PageList<RoleMenuKey> selectRmByCondition(RoleMenuKey rm,PageBounds pageBounds);
	
	/**
	 * 条件查找
	 * @return
	 */
	PageList<RoleResourceKey> selectRrByCondition(RoleResourceKey rr,PageBounds pageBounds);
	
	/**
	 * 根据roleid和userid查出此条ru
	 * @param roleid
	 * @param userid
	 * @return
	 */
	RoleUsersKey selectRu(String roleid,String userid);
	
	/**
	 * 根据roleid和menuid查出此条rm
	 * @param roleid
	 * @param menuid
	 * @return
	 */
	RoleMenuKey selectRm(String roleid,String menuid);
	
	/**
	 * 根据roreid查出此条Rr
	 * @param roreid
	 * @return
	 */
	RoleResourceKey selectRr(String roreid);
	
	/**
	 * 更新ru
	 * @param roleid
	 * @param userid
	 * @param ru
	 * @return
	 */
	int updateRu(String roleid,String userid,RoleUsersKey ru);
	
	/**
	 * 更新rm
	 * @param roleid
	 * @param menuid
	 * @param ru
	 * @return
	 */
	int updateRm(String roleid,String menuid,RoleMenuKey rm);
	
	/**
	 * 更新rr
	 * @param rr
	 * @return
	 */
	int updateRr(RoleResourceKey rr);
}
