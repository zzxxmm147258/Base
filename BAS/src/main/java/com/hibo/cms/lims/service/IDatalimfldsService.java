package com.hibo.cms.lims.service;

import java.util.List;

import com.hibo.cms.lims.model.Datalimflds;
import com.hibo.cms.lims.model.DatalimfldsKey;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午10:01:23</p>
 * <p>类全名：com.hibo.cms.lims.service.IDatalimfldsService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IDatalimfldsService {

	/**
	 * <p>功能：查询所有权限字段数据<p>
	 * <p>创建日期：2015年10月19日 下午4:05:55<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
    List<Datalimflds> selectAll();
    /**
     * <p>功能：根据权限类型ID查询权限字段数据<p>
     * <p>创建日期：2015年10月19日 下午4:07:19<p>
     * <p>作者：曾小明<p>
     * @param datalimmid
     * @return
     */
    List<Datalimflds> selectDatalimflds(String datalimmid);
	/**
	 * <p>功能：插入权限字段数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:12<p>
	 * <p>作者：曾小明<p>
	 * @param datalimflds
	 * @return
	 */
	int insert(Datalimflds datalimflds);
	/**
	 * <p>功能：删除权限字段数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:55<p>
	 * <p>作者：曾小明<p>
	 * @param datalimfldsid
	 * @return
	 */
	int deleteByDatalimfldsId(String datalimfldsid);
	/**
	 * <p>功能：修改权限字段数据<p>
	 * <p>创建日期：2015年10月19日 下午4:10:01<p>
	 * <p>作者：曾小明<p>
	 * @param olddatalimfldsid
	 * @param datalimflds
	 * @return
	 */
	int updateByDatalimfldsId(String olddatalimfldsid,Datalimflds datalimflds);

	/**
	 * <p>功能：删除权限字段关联父表的数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:55<p>
	 * <p>作者：曾小明<p>
	 * @param limid
	 * @return
	 */
	int delete(String limid);
	
	int deleteByPrimaryKey(DatalimfldsKey key);
	
	 Datalimflds selectByPrimaryKey(DatalimfldsKey key);
	 
	 int updateByPrimaryKeySelective(DatalimfldsKey key,Datalimflds record);
}
