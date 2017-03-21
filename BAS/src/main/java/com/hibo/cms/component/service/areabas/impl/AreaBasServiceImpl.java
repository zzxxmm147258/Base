package com.hibo.cms.component.service.areabas.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.component.dao.AreaBasMapper;
import com.hibo.cms.component.model.AreaBas;
import com.hibo.cms.component.service.areabas.IAreaBasService;



/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月19日 上午10:02:14</p>
 * <p>类全名：com.hibo.ebusi.payment.service.impl.AreaServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class AreaBasServiceImpl implements IAreaBasService {

	@Autowired
	private AreaBasMapper areaBasMapper;
	
	@Override
	public AreaBas selectById(String id) {
		return areaBasMapper.selectById(id);
	}
	
	@Override
	public List<AreaBas> selectByParent(String parent) {
		return areaBasMapper.selectByParent(parent);
	}

	@Override
	public String selectFullNameById(String id) {
		return areaBasMapper.selectFullNameById(id);
	}

	@Override
	public int insert(AreaBas area) {
		return areaBasMapper.insert(area);
	}

	@Override
	public int delete(String id) {
		return areaBasMapper.delete(id);
	}

	@Override
	public int update(AreaBas area) {
		return areaBasMapper.updateByPrimaryKeySelective(area);
	}

	@Override
	public List<AreaBas> selectByAreaBas(String province, String city) {
		return areaBasMapper.selectByAreaBas(province, city);
	}

	@Override
	public List<String> selectByCity(String city) {
		return areaBasMapper.selectByCity(city);
	}

	@Override
	public List<String> selectCity(String city) {
		return areaBasMapper.selectCity(city);
	}
	
	@Override
	public List<String> selectByProvinces() {
		return areaBasMapper.selectByProvinces();
	}

	@Override
	public List<String> selectByCitys(String province) {
		return areaBasMapper.selectByCitys(province);
	}

	@Override
	public List<String> selectByAreas(String province, String city) {
		return areaBasMapper.selectByAreas(province, city);
	}

	@Override
	public List<String> selectHotcity() {
		return areaBasMapper.selectHotcity();
	}

	@Override
	public List<String> selectByProvince(String province) {
		return areaBasMapper.selectByProvince(province);
	}

	@Override
	public List<String> selectByArea(String area) {
		return areaBasMapper.selectByArea(area);
	}

	@Override
	public List<String> selectCityByProvince(String province) {
		return areaBasMapper.selectCityByProvince(province);
	}

	@Override
	public List<String> selectAreaByCity(String city) {
		return areaBasMapper.selectAreaByCity(city);
	}

	@Override
	public List<AreaBas> selectYicAreaBas() {
		return areaBasMapper.selectYicAreaBas();
	}

}
