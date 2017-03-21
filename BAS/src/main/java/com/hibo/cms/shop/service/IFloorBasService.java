package com.hibo.cms.shop.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.FloorBas;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月18日 下午6:21:25</p>
 * <p>类全名：com.hibo.cms.shop.service.IFloorBasService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IFloorBasService {
	
	/**
	 * <p>功能： 根据楼ID查出所有可用层</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2016年2月18日 下午6:22:15</p>
	 * @param building
	 * @return
	 */
	List<FloorBas> selectAllAvailableByBuilding(String building);
	
	int deleteByPrimaryKey(String floorId);

    int insert(FloorBas record);

    int insertSelective(FloorBas record);

    FloorBas selectByPrimaryKey(String floorId);

    int updateByPrimaryKeySelective(FloorBas record);

    int updateByPrimaryKey(FloorBas record);
    
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年2月19日 上午9:38:12<p>
     * <p>作者：曾小明<p>
     * @param wStr
     * @param pageBounds
     * @return
     */
    PageList<FloorBas> selectByCondition(Map map,PageBounds pageBounds);
    
    List<FloorBas> selectAll();
    
    FloorBas selectByName(String floorId);
    
    List<FloorBas> selectAllName();
    
    /**
     * <p>功能：条件模糊查询所有的楼层<p>
     * <p>创建日期：2016年2月22日 下午2:10:50<p>
     * <p>作者：曾小明<p>
     * @param floorName
     * @return
     */
    List<FloorBas> selectByAllName(String floorName);

}
