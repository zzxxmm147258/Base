package com.hibo.cms.sys.utils.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hibo.cms.user.model.Menu;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月24日 上午11:37:34</p>
 * <p>类全名：com.hibo.cms.sys.utils.menu.MenuUtil</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public class MenuUtil {
	/**
	 * 菜单
	 * @param menuList list的menu
	 * @param p 级次长度
	 * @return
	 */
	public static List<Menu> getMenuList(List<Menu> menuList,int p){
		List<Menu> menus = new ArrayList<Menu>();
		if(null==menuList||menuList.size()<=0){
			return menus;
		}
		Map<String,Menu> menuMap = getMenuMap(menuList);
		for (Menu menu : menuList) {
			String id = menu.getMenuid();
			if(id.length()-2>p){
				String parentId = id.substring(0, id.length()-p);
				if(menuMap.containsKey(parentId)){
					Menu parentMenu = menuMap.get(parentId);
					List<Menu> menuChild = parentMenu.getChildren();
					if(null==menuChild){
						menuChild = new ArrayList<Menu>();
					}
					menuChild.add(menu);
					parentMenu.setChildren(menuChild);
					
				}
			}else{
				menus.add(menu);
			}

		}

		return menus;
	}
	public static Map<String,Menu> getMenuMap(List<Menu> menuList){
		Map<String,Menu> menuMap= new HashMap<String,Menu>();
		for (Menu menu : menuList) {
			menuMap.put(menu.getMenuid(), menu);
		}
		return menuMap;
	}
	
}
