package com.hibo.cms.lims.service;

import java.util.List;

import com.hibo.cms.lims.model.Datalimop;
import com.hibo.cms.lims.model.DatalimopKey;




/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午10:42:37</p>
 * <p>类全名：com.hibo.cms.lims.service.IDatalimopService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IDatalimopService {

	/**
	 * <p>功能：查询所有权限操作数据<p>
	 * <p>创建日期：2015年10月19日 下午4:05:55<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
    List<Datalimop> selectAll();
    /**
     * <p>功能：根据权限类型ID查询权限操作数据<p>
     * <p>创建日期：2015年10月19日 下午4:07:19<p>
     * <p>作者：曾小明<p>
     * @param datalimmid
     * @return
     */
    List<Datalimop> selectDatalimop(String datalimmid);
	/**
	 * <p>功能：插入权限数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:12<p>
	 * <p>作者：曾小明<p>
	 * @param datalimop
	 * @return
	 */
	int insert(Datalimop datalimop);
	/**
	 * <p>功能：删除权限操作数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:55<p>
	 * <p>作者：曾小明<p>
	 * @param datalimopid
	 * @return
	 */
	int deleteByDatalimopId(String datalimopid);
	/**
	 * <p>功能：修改权限操作数据<p>
	 * <p>创建日期：2015年10月19日 下午4:10:01<p>
	 * <p>作者：曾小明<p>
	 * @param olddatalimopid
	 * @param datalimop
	 * @return
	 */
	int updateByDatalimopId(String olddatalimopid,Datalimop datalimop);
	/**
	 * <p>功能：删除权限操作关联父表的数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:55<p>
	 * <p>作者：曾小明<p>
	 * @param limid
	 * @return
	 */
	int delete(String limid);
	
	int deleteByPrimaryKey(DatalimopKey key);
	
	 Datalimop selectByPrimaryKey(DatalimopKey key);

	 int updateByPrimaryKeySelective(DatalimopKey key,Datalimop record);

}
