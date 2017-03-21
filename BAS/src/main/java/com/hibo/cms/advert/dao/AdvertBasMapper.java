package com.hibo.cms.advert.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.advert.model.AdvertBas;

public interface AdvertBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdvertBas record);

    int insertSelective(AdvertBas record);

    AdvertBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdvertBas record);

    int updateByPrimaryKeyWithBLOBs(AdvertBas record);

    int updateByPrimaryKey(AdvertBas record);
    
    PageList<AdvertBas> selectPage(@Param("wStr")String wStr,PageBounds pageBounds);
    
    /**
     * <p>功能：根据groupId查出列表</p>
     * <p>创建日期：2016年3月3日下午3:37:54</p>
     * <p>作者：吕康</p>
     * @param groupId
     * @return
     */
    List<AdvertBas> selectByGroupId(String groupId);
    
    /**
     * @功能:根据code查出列表
     * @描述:
     * @作者:周雷
     * @时间:2016年3月22日 下午6:17:40
     * @param groupId
     * @return
     */
    List<AdvertBas> selectByGroupIdLimit(@Param("adPosition")String adPosition,@Param("page")int page,@Param("limit")int limit);
    /**
     * <p>功能：根据位置code查出可用广告</p>
     * <p>创建日期：2016年3月4日上午11:16:48</p>
     * <p>作者：吕康</p>
     * @param adPosition
     * @return
     */
    List<AdvertBas> selectAvailableByPosition(String adPosition);
}