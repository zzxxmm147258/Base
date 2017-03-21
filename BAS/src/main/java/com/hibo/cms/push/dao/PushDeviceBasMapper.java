package com.hibo.cms.push.dao;

import java.util.List;

import com.hibo.cms.push.model.PushDeviceBas;

public interface PushDeviceBasMapper {
    int deleteByPrimaryKey(String userId);

    int insert(PushDeviceBas record);

    int insertSelective(PushDeviceBas record);

    PushDeviceBas selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(PushDeviceBas record);

    int updateByPrimaryKey(PushDeviceBas record);
    
    public List<PushDeviceBas> findAll();
    
    public List<PushDeviceBas> findIosOrAndroidAll(String attrName1);
}