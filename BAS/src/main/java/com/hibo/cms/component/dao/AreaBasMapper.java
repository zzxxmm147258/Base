package com.hibo.cms.component.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.component.model.AreaBas;

public interface AreaBasMapper {
    int deleteByPrimaryKey(String id);

    int insert(AreaBas record);

    int insertSelective(AreaBas record);

    AreaBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AreaBas record);

    int updateByPrimaryKeyWithBLOBs(AreaBas record);

    int updateByPrimaryKey(AreaBas record);
    
    
    AreaBas selectById(String id);
	
	List<AreaBas> selectByParent(@Param("parent") String parent);
	
	
	String selectFullNameById(String id);
	
	int delete(@Param(value="id")String id);
	
	int update(AreaBas area);
	
	List<AreaBas> selectByAreaBas(@Param("province") String province,@Param("city") String city);

	List<String> selectByCity(@Param("city") String city);
	
	List<String> selectCity(@Param("city") String city);
	
	List<String> selectByProvinces();
	
	List<String> selectByCitys(@Param("province") String province);
	
	List<String> selectByAreas(@Param("province") String province,@Param("city") String city);
	
	/**
	 * <p>功能：查询热门城市</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年11月16日 下午7:40:24</p>
	 * @return
	 */
	List<String> selectHotcity();
	
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
	
	List<String> selectByProvince(@Param("province") String province);
	
	List<String> selectByArea(@Param("area") String area);
	
	/**
	 * <p>功能：查询城市<p>
	 * <p>创建日期：2016年8月30日 上午9:56:35<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
	List<AreaBas> selectYicAreaBas();
}