package com.hibo.cms.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.component.model.CountryBas;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.shop.dao.DistrictBasMapper;
import com.hibo.cms.shop.model.DistrictBas;
import com.hibo.cms.shop.service.IDistrictBasService;
import com.hibo.cms.sys.utils.anno.RemoteAnno;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午2:36:28</p>
 * <p>类全名：com.hibo.cms.shop.service.impl.DistrictBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class DistrictBasServiceImpl implements IDistrictBasService{

	@Autowired
	private DistrictBasMapper districtBasMapper;
	
	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteByPrimaryKey(String district) {
		return districtBasMapper.deleteByPrimaryKey(district);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(DistrictBas record) {
		return districtBasMapper.insert(record);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertSelective(DistrictBas record) {
		return districtBasMapper.insertSelective(record);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public DistrictBas selectByPrimaryKey(String district) {
		return districtBasMapper.selectByPrimaryKey(district);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateByPrimaryKeySelective(DistrictBas record) {
		return districtBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateByPrimaryKey(DistrictBas record) {
		return districtBasMapper.updateByPrimaryKey(record);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<DistrictBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return districtBasMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<DistrictBas> selectAll() {
		return districtBasMapper.selectAll();
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<DistrictBas> selectAllAvailable() {
		return districtBasMapper.selectAllAvailable();
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<DistrictBas> selectBy(String district, String disname, String city) {
		return districtBasMapper.selectBy(district, disname, city);
	}

}
