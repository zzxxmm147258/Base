package com.hibo.cms.shop.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.BlockBas;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月18日 下午6:04:32</p>
 * <p>类全名：com.hibo.cms.shop.service.IBlockBasService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IBlockBasService {

	/**
	 * <p>功能：根据商圈ID查出所有可用街区 </p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2016年2月18日 下午6:05:25</p>
	 * @param district
	 * @return
	 */
	List<BlockBas> selectAllAvailableByDistrict(String district);
	
	int deleteByPrimaryKey(String blockId);

    int insert(BlockBas record);

    int insertSelective(BlockBas record);

    BlockBas selectByPrimaryKey(String blockId);

    int updateByPrimaryKeySelective(BlockBas record);

    int updateByPrimaryKey(BlockBas record);
    
    /**
     * <p>功能：分页查询<p>
     * <p>创建日期：2016年2月19日 上午9:38:12<p>
     * <p>作者：曾小明<p>
     * @param wStr
     * @param pageBounds
     * @return
     */
    PageList<BlockBas> selectByCondition(Map map,PageBounds pageBounds);
    
    /**
     * <p>功能：查询所有街区<p>
     * <p>创建日期：2016年2月19日 上午9:45:40<p>
     * <p>作者：曾小明<p>
     * @return
     */
    List<BlockBas> selectAll();
    
    
    BlockBas selectByName(String blockId);
    
    List<BlockBas> selectAllName();
	
}
