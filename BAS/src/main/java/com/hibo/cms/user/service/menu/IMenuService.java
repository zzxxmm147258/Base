package com.hibo.cms.user.service.menu;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Menu;
import com.hibo.cms.user.model.RoleMenuKey;

public interface IMenuService {
	
	String selectMaxChildId(String parentId);
	
	PageList<Menu> selectMenusBySysId(String sysid,PageBounds pageBounds);
	/**
     * 分页条件查找
     * @param wStr
     * @param pageBounds
     * @return
     */
    PageList<Menu> selectByCondition(Map map,PageBounds pageBounds);
	
	List<Menu> selectMenusByUserId(String id,String sysid);
	
	int insert(Menu menu);
	
	int deleteByMenuid(String menuid);
	
	Menu selectByMenuid(String menuid);
	
	//int updateByMenu(Menu menu);
	
	int update(String oldmenuid,Menu menu);
	
	/**
	 * 查出所有的菜单
	 * @return
	 */
	List<Menu> findAllMenus();
	
	/**
	 * 根据角色ID查出所有对应菜单关系
	 * @param roleid
	 * @return
	 */
	List<String> selectMenuIdsByRoleId(String roleid);
	
	/**
	 * 通过角色ID删除对应的所有菜单关系
	 * @param roleid
	 * @return
	 */
	int deleteMenuIdByRoleId(String roleid);
	
	/**
	 * 插入角色-菜单关系
	 * @param roleMenus
	 * @return
	 */
	int insertList(List<RoleMenuKey> roleMenus);
	
	/**
	 * 模糊查询
	 * @param menu
	 * @return
	 */
	List<Menu> selectFuzzy(Menu menu);
}
