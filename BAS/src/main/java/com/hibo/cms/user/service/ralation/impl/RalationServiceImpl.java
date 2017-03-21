package com.hibo.cms.user.service.ralation.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.user.dao.RoleMenuMapper;
import com.hibo.cms.user.dao.RoleResourceMapper;
import com.hibo.cms.user.dao.RoleUsersMapper;
import com.hibo.cms.user.model.RoleMenuKey;
import com.hibo.cms.user.model.RoleResourceKey;
import com.hibo.cms.user.model.RoleUsersKey;
import com.hibo.cms.user.service.ralation.IRalationService;
/** <p>标题：关系关联</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月14日 下午3:06:06</p>
 * <p>类全名：com.hibo.cms.user.service.resource.impl.ResourceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class RalationServiceImpl implements IRalationService {

	@Autowired
	private RoleUsersMapper roleUserMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	@Override
	@RemoteAnno(value="BAS_READ")
	public List<RoleUsersKey> selectAllRoleUser() {
		return roleUserMapper.selectAll();
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<RoleMenuKey> selectAllRoleMenu() {
		return roleMenuMapper.selectAll();
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteRuByPrimaryKey(RoleUsersKey roleUser) {
		return roleUserMapper.deleteByPrimaryKey(roleUser);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteRmByPrimaryKey(RoleMenuKey roleMenu) {
		return roleMenuMapper.deleteByPrimaryKey(roleMenu);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertRu(RoleUsersKey roleUser) {
		return roleUserMapper.insert(roleUser);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertRm(RoleMenuKey roleMenu) {
		return roleMenuMapper.insert(roleMenu);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<RoleUsersKey> selectRuByCondition(RoleUsersKey ru, PageBounds pageBounds) {
		return roleUserMapper.selectByCondition(ru, pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public RoleUsersKey selectRu(String roleid, String userid) {
		return roleUserMapper.select(roleid, userid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateRu(String roleid, String userid, RoleUsersKey ru) {
		return roleUserMapper.update(roleid, userid, ru);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<RoleMenuKey> selectRmByCondition(RoleMenuKey rm, PageBounds pageBounds) {
		return roleMenuMapper.selectByCondition(rm, pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public RoleMenuKey selectRm(String roleid, String menuid) {
		return roleMenuMapper.select(roleid, menuid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateRm(String roleid, String menuid, RoleMenuKey rm) {
		return roleMenuMapper.update(roleid, menuid, rm);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<RoleResourceKey> selectRrByCondition(RoleResourceKey rr, PageBounds pageBounds) {
		return roleResourceMapper.selectByCondition(rr, pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public RoleResourceKey selectRr(String roreid) {
		return roleResourceMapper.select(roreid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteRrByPrimaryKey(String roreid) {
		return roleResourceMapper.delete(roreid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertRr(RoleResourceKey roleResource) {
		return roleResourceMapper.insert(roleResource);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateRr(RoleResourceKey rr) {
		return roleResourceMapper.update(rr);
	}


}
