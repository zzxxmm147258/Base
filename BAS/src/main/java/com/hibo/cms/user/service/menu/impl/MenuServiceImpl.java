package com.hibo.cms.user.service.menu.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.user.dao.MenuMapper;
import com.hibo.cms.user.dao.RoleMenuMapper;
import com.hibo.cms.user.model.Menu;
import com.hibo.cms.user.model.RoleMenuKey;
import com.hibo.cms.user.service.menu.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService{
	
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Menu> selectMenusByUserId(String userid,String sysid) {
		
		List<String> menuids = menuMapper.selectMenuIdByUserId(userid,sysid); //查出此用户对应的直接菜单id集合
		if(menuids.size()!=0 && null != menuids){
			List<String> midList = new ArrayList<String>(); //加完前缀和后缀的直接菜单集，用来查询本身和子菜单
			Set<String> pset = null; //用于查询父级菜单
			for(String menuid : menuids){
				midList.add(menuid+"%");
				int length = menuid.length();
				if(length>2){
					pset = new HashSet<String>();
					for(int i=2;i<=length-4;i+=2){
						pset.add(menuid.substring(0, length-i));
					}
				}
			}
			List<Menu> menuList = menuMapper.selectMenusByMenuids(midList,pset,sysid); //查出此用户有权限使用的所有菜单（父级、本身、子级）
			return menuList;
		}else{
			return null;
		}
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(Menu menu) {
		return menuMapper.insert(menu);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int deleteByMenuid(String menuid) {
		roleMenuMapper.deleteByMenuId(menuid);
		return menuMapper.deleteByMenuid(menuid);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public Menu selectByMenuid(String menuid) {
		return menuMapper.selectByMenuid(menuid);
	}

	/*@Override
	public int updateByMenu(Menu menu) {
		return menuMapper.updateByMenu(menu);
	}*/
	
	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int update(String oldmenuid, Menu menu) {
		//修改rm关系中的对应字段
		RoleMenuKey rm = new RoleMenuKey();
		rm.setMenuid(menu.getMenuid());
		rm.setMenuname(menu.getMenuname());
		roleMenuMapper.updateByRoleMenu(rm, null, oldmenuid);
		return menuMapper.update(oldmenuid, menu);
	}
	
	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Menu> findAllMenus() {
		return menuMapper.selectAllMenus();
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<String> selectMenuIdsByRoleId(String roleid) {
		return roleMenuMapper.selectMenuIdsByRoleId(roleid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteMenuIdByRoleId(String roleid) {
		return roleMenuMapper.deleteMenuIdByRoleId(roleid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertList(List<RoleMenuKey> roleMenus) {
		return roleMenuMapper.insertList(roleMenus);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public String selectMaxChildId(String parentId) {
		return menuMapper.selectMaxChildId(parentId);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<Menu> selectMenusBySysId(String sysid, PageBounds pageBounds) {
		return menuMapper.selectMenusBySysId(sysid, pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Menu> selectFuzzy(Menu menu) {
		return menuMapper.selectFuzzy(menu);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<Menu> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return menuMapper.selectByCondition(wStr, pageBounds);
	}

}
