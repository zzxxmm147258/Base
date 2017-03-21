package com.hibo.cms.user.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Menu;

public interface MenuMapper {
	
	String selectMaxChildId(String parentId);
	
    int deleteByMenuid(String menuid);

    int insert(Menu menu);
    
    Menu selectByMenuid(String menuid);

    PageList<Menu> selectMenusBySysId(String sysid,PageBounds pageBounds);

    /**
     * 分页条件查找
     * @param wStr
     * @param pageBounds
     * @return
     */
    PageList<Menu> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
	List<Menu> selectMenusByMenuids(@Param("likelist") List<String> likelist,@Param("islist") Set<String> islist,@Param("sysid") String sysid);
	
	List<String> selectMenuIdByUserId(@Param("userid") String userid,@Param("sysid") String sysid);
	
	//int updateByMenu(Menu menu);
	int update(@Param("oldmenuid") String oldmenuid,@Param("menu") Menu menu);
	
	List<Menu> selectAllMenus();
	
	List<Menu> selectFuzzy(Menu menu);
}