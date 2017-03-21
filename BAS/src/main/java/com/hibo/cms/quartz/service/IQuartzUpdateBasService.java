package com.hibo.cms.quartz.service;

import java.util.Date;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.quartz.model.QuartzUpdateBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月26日 下午4:37:00</p>
 * <p>类全名：com.hibo.cms.quartz.service.IQuartzUpdateBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IQuartzUpdateBasService {

	int deleteByPrimaryKey(String id);

    int insert(QuartzUpdateBas record);

    int insertSelective(QuartzUpdateBas record);

    QuartzUpdateBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuartzUpdateBas record);

    int updateByPrimaryKey(QuartzUpdateBas record);
	
	PageList<QuartzUpdateBas> selectByCondition(Map map,PageBounds pageBounds);
	
	Date getDatebyId(String id);
	
	int updateDatebyId(String id, Date updateDate);
	
	int getLocked(String id);
	
	int releaseLocked(String id);
}
