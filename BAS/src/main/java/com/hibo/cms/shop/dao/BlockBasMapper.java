package com.hibo.cms.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.BlockBas;

public interface BlockBasMapper {
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
    PageList<BlockBas> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
    List<BlockBas> selectAll();
    
    List<BlockBas> selectAllAvailableByDistrict(String district);
    
    BlockBas selectByName(String blockId);
    
    List<BlockBas> selectAllName();
}