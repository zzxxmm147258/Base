package com.hibo.cms.weixin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.weixin.model.WeiXinBusMem;

public interface WeiXinBusMemMapper {
    int deleteByPrimaryKey(String openid);

    int insert(WeiXinBusMem record);

    int insertSelective(WeiXinBusMem record);

    WeiXinBusMem selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(WeiXinBusMem record);

    int updateByPrimaryKey(WeiXinBusMem record);
    
    PageList<WeiXinBusMem> selectByCondition(@Param("wStr")String wStr,PageBounds pageBounds);
    
    int update(@Param("locked") int locked,@Param("flags") int flags,@Param("openid")String openid);
    
    List<WeiXinBusMem> select3All(String mId);
    
    
}