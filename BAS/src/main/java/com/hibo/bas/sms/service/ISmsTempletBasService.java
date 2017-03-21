package com.hibo.bas.sms.service;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.sms.model.SmsTempletBas;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月4日 下午2:55:21</p>
 * <p>类全名：com.hibo.bas.sms.service.ISmsTempletBasService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface ISmsTempletBasService {

	
	/**
	 * <p>功能： 根据模板ID查询对应短信模板</p>
	 * <p>作者：吕康</p>
	 * @param tempNo
	 * @return
	 */
	SmsTempletBas selectByPrimaryKey(String tempNo);
	
	/**
	 * <p>功能： 插入一条记录</p>
	 * <p>作者：吕康</p>
	 * @param smsTempletBas
	 * @return
	 */
	int insert(SmsTempletBas smsTempletBas);
	
	/**
	 * <p>功能： 根据tempNo删除此条记录</p>
	 * <p>作者：吕康</p>
	 * @param tempNo
	 * @return
	 */
	int deleteByPrimaryKey(String tempNo);
	
	/**
	 * <p>功能： 更新此条记录</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年11月6日 上午9:34:48</p>
	 * @param smsTempletBas
	 * @return
	 */
	int updateByPrimaryKey(SmsTempletBas smsTempletBas);
	
	int updateByPrimaryKeySelective(SmsTempletBas smsTempletBas);
	
	int insertSelective(SmsTempletBas smsTempletBas);
	
	PageList<SmsTempletBas> selectPage(Map map,PageBounds pageBounds);
	
}
