package com.hibo.cms.weixin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.weixin.model.WeiXinSerMem;

public interface WeiXinSerMemMapper {
    int deleteByPrimaryKey(String openid);

    int insert(WeiXinSerMem record);

    int insertSelective(WeiXinSerMem record);

    WeiXinSerMem selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(WeiXinSerMem record);

    int updateByPrimaryKey(WeiXinSerMem record);
    
    PageList<WeiXinSerMem> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);

    List<WeiXinSerMem> select2All(String mId);
    
    WeiXinSerMem selectByNameKey(String openid);
    
    WeiXinSerMem selectByAppKey(String openid,String appkey);
}