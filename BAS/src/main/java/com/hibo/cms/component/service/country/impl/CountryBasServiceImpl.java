package com.hibo.cms.component.service.country.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.component.dao.CountryBasMapper;
import com.hibo.cms.component.model.CountryBas;
import com.hibo.cms.component.service.country.ICountryBasService;
import com.hibo.cms.lims.QueryFilterBuilder;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月11日 下午3:28:19</p>
 * <p>类全名：com.hibo.cms.component.service.country.impl.ICountryBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class CountryBasServiceImpl implements ICountryBasService{

	@Autowired
	private CountryBasMapper countryBasMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return countryBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CountryBas record) {
		return countryBasMapper.insert(record);
	}

	@Override
	public int insertSelective(CountryBas record) {
		return countryBasMapper.insertSelective(record);
	}

	@Override
	public CountryBas selectByPrimaryKey(String id) {
		return countryBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CountryBas record) {
		return countryBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CountryBas record) {
		return countryBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<CountryBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return countryBasMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	public List<String> selectAll() {
		return countryBasMapper.selectAll();
	}

	@Override
	public List<CountryBas> selectList(String title) {
		return countryBasMapper.selectList(title);
	}

	
}
