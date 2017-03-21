package com.hibo.cms.image.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.image.model.ImageStoreBas;

public interface ImageStoreBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(ImageStoreBas record);

    int insertSelective(ImageStoreBas record);

    ImageStoreBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImageStoreBas record);

    int updateByPrimaryKey(ImageStoreBas record);
    
    /**
     * <p>功能：根据主表系统号类型查询图片地址<p>
     * <p>创建日期：2016年9月12日 上午10:27:31<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param sys
     * @param type
     * @return
     */
    List<ImageStoreBas> selectBymIdOrSysOrType(@Param("mId")String mId,@Param("sys")String sys,@Param("type")String type);

    int deleteBymId(String mid);
}