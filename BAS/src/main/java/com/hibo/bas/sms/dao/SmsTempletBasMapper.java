package com.hibo.bas.sms.dao;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.sms.model.SmsTempletBas;

public interface SmsTempletBasMapper {
    int deleteByPrimaryKey(String tempNo);

    int insert(SmsTempletBas smsTempletBas);

    int insertSelective(SmsTempletBas smsTempletBas);

    SmsTempletBas selectByPrimaryKey(String tempNo);

    int updateByPrimaryKeySelective(SmsTempletBas smsTempletBas);

    int updateByPrimaryKey(SmsTempletBas smsTempletBas);
    
    PageList<SmsTempletBas> selectPage(@Param("wStr")String wStr,PageBounds pageBounds);
}