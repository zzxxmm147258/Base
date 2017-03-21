package com.hibo.cms.lims.service;

import java.util.List;


import com.hibo.cms.lims.model.Datalimm;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 上午9:14:37</p>
 * <p>类全名：com.hibo.cms.lims.service.IDatalimmService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IDatalimmService {
    
	List<Datalimm> selectAll();
	
	int insert(Datalimm datalimm);
	
	int deleteByDatalimmId(String datalimmid);
	
	int updateByDatalimmId(String olddatalimmid,Datalimm datalimm);
	
	Datalimm selectByDatalimmId(String limid);
}
