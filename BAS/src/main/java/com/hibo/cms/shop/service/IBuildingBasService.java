package com.hibo.cms.shop.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.BuildingBas;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月18日 下午6:14:44</p>
 * <p>类全名：com.hibo.cms.shop.service.IBuildingBasService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IBuildingBasService {
	
	/**
	 * <p>功能：根据街区ID查出所有可用楼 </p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2016年2月18日 下午6:15:32</p>
	 * @param block
	 * @return
	 */
	List<BuildingBas> selectAllAvailableByBlock(String block);

	
	int deleteByPrimaryKey(String buildingId);

    int insert(BuildingBas record);

    int insertSelective(BuildingBas record);

    BuildingBas selectByPrimaryKey(String buildingId);

    int updateByPrimaryKeySelective(BuildingBas record);

    int updateByPrimaryKey(BuildingBas record);
    
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年2月19日 上午9:38:12<p>
     * <p>作者：曾小明<p>
     * @param wStr
     * @param pageBounds
     * @return
     */
    PageList<BuildingBas> selectByCondition(Map map,PageBounds pageBounds);
    
    List<BuildingBas> selectAll();
    
    BuildingBas selectByName(String buildingId);
    
    List<BuildingBas> selectAllName();
}
