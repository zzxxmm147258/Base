package com.hibo.cms.advert.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.advert.model.AdvertBas;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月2日 下午2:22:20</p>
 * <p>类全名：com.hibo.cms.advert.service.IAdvertBasService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IAdvertBasService {
	
	int deleteByPrimaryKey(String id);

    int insert(AdvertBas record);

    int insertSelective(AdvertBas record);

    AdvertBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdvertBas record);

    int updateByPrimaryKeyWithBLOBs(AdvertBas record);

    int updateByPrimaryKey(AdvertBas record);
    
    PageList<AdvertBas> selectPage(Map map,PageBounds pageBounds);
    
    /**
     * <p>功能：根据groupId查出列表</p>
     * <p>创建日期：2016年3月3日下午3:37:54</p>
     * <p>作者：吕康</p>
     * @param groupId
     * @return
     */
    List<AdvertBas> selectByGroupId(String groupId);
    /**
     * @功能:根据groupId查出列表
     * @描述:
     * @作者:周雷
     * @时间:2016年3月22日 下午6:17:40
     * @param groupId
     * @return
     */
    List<AdvertBas> selectByGroupIdLimit(String adPosition,int page,int limit);
    
    /**
     * <p>功能：根据位置code查出可用广告</p>
     * <p>创建日期：2016年3月4日上午11:16:48</p>
     * <p>作者：吕康</p>
     * @param adPosition
     * @return
     */
    List<AdvertBas> selectAvailableByPosition(String adPosition);

}
