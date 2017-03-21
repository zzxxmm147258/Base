package com.hibo.cms.lims.service;

import java.util.List;

import com.hibo.cms.lims.model.Datalims;
import com.hibo.cms.lims.model.DatalimsKey;



/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午1:32:24</p>
 * <p>类全名：com.hibo.cms.lims.service.IDatalimsService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IDatalimsService {
	/**
	 * <p>功能：查询所有按岗位定义权限数据<p>
	 * <p>创建日期：2015年10月19日 下午4:05:55<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
    List<Datalims> selectAll();
    /**
     * <p>功能：根据权限类型ID查询按岗位定义权限数据<p>
     * <p>创建日期：2015年10月19日 下午4:07:19<p>
     * <p>作者：曾小明<p>
     * @param datalimmid
     * @return
     */
    List<Datalims> selectDatalims(String datalimmid);
	/**
	 * <p>功能：插入按权限岗位定义权限数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:12<p>
	 * <p>作者：曾小明<p>
	 * @param datalims
	 * @return
	 */
	int insert(Datalims datalims);
	/**
	 * <p>功能：删除按岗位定义权限数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:55<p>
	 * <p>作者：曾小明<p>
	 * @param datalimsid
	 * @return
	 */
	int deleteByDatalimsId(String datalimsid);
	/**
	 * <p>功能：<p>
	 * <p>创建日期：2015年10月19日 下午4:10:01<p>
	 * <p>作者：曾小明<p>
	 * @param olddatalimsid
	 * @param datalims
	 * @return
	 */
	int updateByDatalimsId(String olddatalimsid,Datalims datalims);
	/**
	 * <p>功能：删除按岗位定义权关联父表的数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:55<p>
	 * <p>作者：曾小明<p>
	 * @param limid
	 * @return
	 */
	int delete(String limid);
	
	Datalims selectByPrimaryKey(DatalimsKey key);
	
	int updateByPrimaryKeySelective(DatalimsKey key,Datalims record);
	
	int deleteByPrimaryKey(DatalimsKey key);
}
