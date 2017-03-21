package com.hibo.cms.lims.service;

import java.util.List;

import com.hibo.cms.lims.model.Busheetlim;
import com.hibo.cms.lims.model.BusheetlimKey;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午9:23:41</p>
 * <p>类全名：com.hibo.cms.lims.service.IBusheetlimService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IBusheetlimService {

	/**
	 * <p>功能：查询所有单据权限关系<p>
	 * <p>创建日期：2015年10月19日 下午4:05:55<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
    List<Busheetlim> selectAll();
    /**
     * <p>功能：根据权限类型ID查询单据权限关系数据<p>
     * <p>创建日期：2015年10月19日 下午4:07:19<p>
     * <p>作者：曾小明<p>
     * @param datalimmid
     * @return
     */
    List<Busheetlim> selectBusheetlim(String datalimmid);
	/**
	 * <p>功能：插入单据权限关系数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:12<p>
	 * <p>作者：曾小明<p>
	 * @param busheetlim
	 * @return
	 */
	int insert(Busheetlim busheetlim);
	/**
	 * <p>功能：删除按岗位定义权限数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:55<p>
	 * <p>作者：曾小明<p>
	 * @param busheetlimid
	 * @return
	 */
	int deleteByBusheetlimId(String busheetlimid);
	/**
	 * <p>功能：<p>
	 * <p>创建日期：2015年10月19日 下午4:10:01<p>
	 * <p>作者：曾小明<p>
	 * @param oldbusheetlimid
	 * @param busheetlim
	 * @return
	 */
	int updateByBusheetlimId(String oldbusheetlimid,Busheetlim busheetlim);
	/**
	 * <p>功能：删除按岗位定义权限关联父表的数据<p>
	 * <p>创建日期：2015年10月19日 下午4:08:55<p>
	 * <p>作者：曾小明<p>
	 * @param limid
	 * @return
	 */
	int delete(String limid);
	
	Busheetlim selectByPrimaryKey(BusheetlimKey key);
	
	int deleteByPrimaryKey(BusheetlimKey key);
	
	 int updateByPrimaryKeySelective(BusheetlimKey key,Busheetlim record);

}
