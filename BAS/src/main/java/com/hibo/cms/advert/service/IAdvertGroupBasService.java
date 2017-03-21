package com.hibo.cms.advert.service;

import java.util.List;

import com.hibo.cms.advert.model.AdvertGroupBas;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月3日 下午2:58:30</p>
 * <p>类全名：com.hibo.cms.advert.service.IAdvertGroupBasService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IAdvertGroupBasService {
	
	int deleteByPrimaryKey(String id);

    int insert(AdvertGroupBas advertGroupBas);

    AdvertGroupBas selectByPrimaryKey(String id);

    int updatePrimaryKey(AdvertGroupBas advertGroupBas);
    
    List<AdvertGroupBas> selectAll();

}
