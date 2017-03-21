package com.hibo.cms.component.service.areabas;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.component.model.AreaBas;



/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月19日 上午10:01:42</p>
 * <p>类全名：com.hibo.ebusi.payment.service.IAreaService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IAreaBasService {
	
	/**
	 * 根据地区的ID查出这条地区信息
	 * @param id
	 * @return
	 */
	AreaBas selectById(String id);
	/**
	 * 根据父级ID查出对应的区域
	 * @param parent
	 * @return
	 */
	List<AreaBas> selectByParent(String parent);
	/**
	 * 根据ID查出地区全名
	 * @param id
	 * @return
	 */
	String selectFullNameById(String id);
	/**
	 * 添加
	 * @param area
	 * @return
	 */
	int insert(AreaBas area);
	/**
	 * 根据ID删除此地区及下属地区
	 * @param id
	 * @return
	 */
	int delete(String id);
	/**
	 * 修改此条地区信息
	 * @param area
	 * @return
	 */
	int update(AreaBas area);

	/**
	 * <p>功能：根据省查询市，根据省市查询地区<p>
	 * <p>创建日期：2015年11月11日 下午7:32:35<p>
	 * <p>作者：曾小明<p>
	 * @param province
	 * @param city
	 * @return
	 */
	List<AreaBas> selectByAreaBas( String province, String city);

	/**
	 * <p>功能：模糊查询城市<p>
	 * <p>创建日期：2015年11月11日 下午7:32:18<p>
	 * <p>作者：曾小明<p>
	 * @param city
	 * @return
	 */
	List<String> selectByCity( String city);
	
	List<String> selectCity( String city);
	/**
	 * <p>功能：查询所有的省<p>
	 * <p>创建日期：2015年11月11日 下午8:00:05<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
    List<String> selectByProvinces();
	
    /**
     * <p>功能：选择省后查询当前省的市<p>
     * <p>创建日期：2015年11月11日 下午8:00:19<p>
     * <p>作者：曾小明<p>
     * @param province
     * @return
     */
	List<String> selectByCitys(@Param("province") String province);
	
	/**
	 * <p>功能：选择省市后差区<p>
	 * <p>创建日期：2015年11月11日 下午8:00:45<p>
	 * <p>作者：曾小明<p>
	 * @param province
	 * @param city
	 * @return
	 */
	List<String> selectByAreas(@Param("province") String province,@Param("city") String city);
	
	/**
	 * <p>功能： 查询热门城市</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年11月16日 下午7:41:00</p>
	 * @return
	 */
	List<String> selectHotcity();
	
	/**
	 * <p>功能：模糊查询省<p>
	 * <p>创建日期：2015年11月25日 下午7:55:17<p>
	 * <p>作者：曾小明<p>
	 * @param province
	 * @return
	 */
	List<String> selectByProvince( String province);
	/**
	 * <p>功能：模糊查询区<p>
	 * <p>创建日期：2015年11月26日 下午12:03:40<p>
	 * <p>作者：曾小明<p>
	 * @param area
	 * @return
	 */
	List<String> selectByArea(String area);
	
	/**
	 * <p>功能： 根据省查城市</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年12月21日 上午10:36:58</p>
	 * @param province
	 * @return
	 */
	List<String> selectCityByProvince(String province);
	
	/**
	 * <p>功能：根据城市查地区 </p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年12月21日 上午10:40:01</p>
	 * @param city
	 * @return
	 */
	List<String> selectAreaByCity(String city);
	
	/**
	 * <p>功能：翌成查询城市<p>
	 * <p>创建日期：2016年8月30日 上午9:56:35<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
	List<AreaBas> selectYicAreaBas();
}
