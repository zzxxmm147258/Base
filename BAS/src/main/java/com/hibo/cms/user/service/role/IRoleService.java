package com.hibo.cms.user.service.role;

import java.util.List;
import java.util.Set;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Role;
import com.hibo.cms.user.model.RoleUsersKey;

/**
 * <p>标题：角色接口 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-7 下午5:13:26</p>
 * <p>类全名：com.hibo.cms.service.role.IRoleService</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @version 1.0
 */
public interface IRoleService {
	/**
	 * 根据角色编号得到角色标识符列表
	 * @param roleIds
	 * @return
	 */
	Set<String> findRoles(String roleIds);
	/**
	 * 根据角色编号得到权限字符串列表
	 * @param roleIds
	 * @return
	 */
	Set<String> findPermissions(String roleIds); 
	
	/**
	 * 查出所有的角色信息
	 * @return
	 */
	List<Role> findAllRoles();
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	int insert(Role role);
	/**
	 * 根据roleid查出此角色
	 * @param roleid
	 * @return
	 */
	Role selectByRoleId(String roleid);
	
	/**
	 * 根据角色的id修改此角色
	 * @param record
	 * @return
	 */
	int update(String oldroleid,Role role);
	/**
	 * 根据roleid删除角色
	 * @param roleid
	 * @return
	 */
	int deleteByRoleId(String roleid);
	
	/**
	 * 根据userid查询此用户的角色
	 * @param userid
	 * @return
	 */
	List<Role> selectByUserId(String userid);
	
	/**
	 * 根据userid查询roleid
	 * @param userid
	 * @return
	 */
	List<String> selectRoleIdByUserId(String userid);
	
	/**
	 * 添加用户角色关系
	 * @param roleUser
	 * @return
	 */
	int insertList(List<RoleUsersKey> roleUsers);
	
	/**
	 * 通过userid删除此用户所有的角色关系
	 * @param userid
	 * @return
	 */
	int deleteRoleIdByUserId(String userid);
	
	/**
	 * 条件查找
	 * @return
	 */
	PageList<Role> selectByCondition(Role role,PageBounds pageBounds);
}
