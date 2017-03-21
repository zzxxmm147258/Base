package com.hibo.cms.advert.dao;

import java.util.List;

import com.hibo.cms.advert.model.AdvertGroupBas;

public interface AdvertGroupBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdvertGroupBas advertGroupBas);

    int insertSelective(AdvertGroupBas advertGroupBas);

    AdvertGroupBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdvertGroupBas advertGroupBas);

    int updateByPrimaryKey(AdvertGroupBas advertGroupBas);
    
    List<AdvertGroupBas> selectAll();
}