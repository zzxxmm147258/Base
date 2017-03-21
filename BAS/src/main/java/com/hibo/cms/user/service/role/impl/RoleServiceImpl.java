package com.hibo.cms.user.service.role.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.user.dao.RoleMapper;
import com.hibo.cms.user.dao.RoleMenuMapper;
import com.hibo.cms.user.dao.RoleResourceMapper;
import com.hibo.cms.user.dao.RoleUsersMapper;
import com.hibo.cms.user.model.Role;
import com.hibo.cms.user.model.RoleMenuKey;
import com.hibo.cms.user.model.RoleResourceKey;
import com.hibo.cms.user.model.RoleUsersKey;
import com.hibo.cms.user.service.role.IRoleService;

/**
 * <p>标题：角色服务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-7 下午5:16:51</p>
 * <p>类全名：com.hibo.cms.service.role.impl.RoleServiceImpl</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleUsersMapper roleUserMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	@Override
	@RemoteAnno(value="BAS_READ")
	public Set<String> findRoles(String roleIds) {
		return null;
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public Set<String> findPermissions(String roleIds) {
		return null;
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Role> findAllRoles() {
		return roleMapper.selectAllRoles();
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(Role role) {
		return roleMapper.insert(role);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public Role selectByRoleId(String roleid) {
		return roleMapper.selectByRoleId(roleid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int update(String oldroleid, Role role) {
		//修改关系表中的对应字段
		RoleUsersKey ru = new RoleUsersKey();
		ru.setRoleid(role.getRoleid());
		ru.setRolename(role.getRolename());
		roleUserMapper.updateByRoleUsers(ru, oldroleid, null);
		RoleMenuKey rm = new RoleMenuKey();
		rm.setRoleid(role.getRoleid());
		rm.setRolename(role.getRolename());
		roleMenuMapper.updateByRoleMenu(rm, oldroleid, null);
		RoleResourceKey rr = new RoleResourceKey();
		rr.setRoleid(role.getRoleid());
		rr.setRolename(role.getRolename());
		roleResourceMapper.updateByRoleResource(rr, oldroleid, null, null);
		return roleMapper.update(oldroleid, role);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int deleteByRoleId(String roleid) {
		roleUserMapper.deleteByRoleId(roleid);
		roleMenuMapper.deleteMenuIdByRoleId(roleid);
		roleResourceMapper.deleteByRoleid(roleid);
		return roleMapper.deleteByRoleId(roleid);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Role> selectByUserId(String userid) {
		return roleMapper.selectByUserId(userid);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<String> selectRoleIdByUserId(String userid) {
		return roleUserMapper.selectRoleIdByUserId(userid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertList(List<RoleUsersKey> roleUsers) {
		return roleUserMapper.insertList(roleUsers);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteRoleIdByUserId(String userid) {
		return roleUserMapper.deleteRoleIdByUserId(userid);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<Role> selectByCondition(Role role,PageBounds pageBounds) {
		return roleMapper.selectByCondition(role,pageBounds);
	}

}
