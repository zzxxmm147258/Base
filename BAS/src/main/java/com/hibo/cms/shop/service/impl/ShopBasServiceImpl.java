package com.hibo.cms.shop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.shop.dao.ShopBasMapper;
import com.hibo.cms.shop.model.MallShop;
import com.hibo.cms.shop.model.ShopBas;
import com.hibo.cms.shop.model.ShopConditionBas;
import com.hibo.cms.shop.service.IShopBasService;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.sys.utils.json.JsonUtil;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午2:36:47</p>
 * <p>类全名：com.hibo.cms.shop.service.impl.ShopBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class ShopBasServiceImpl implements IShopBasService{
	
	@Autowired
	private ShopBasMapper shopBasMapper;

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int deleteByPrimaryKey(String shopId) {
		return shopBasMapper.deleteByPrimaryKey(shopId);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(ShopBas record) {
		return shopBasMapper.insert(record);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertSelective(ShopBas record) {
		return shopBasMapper.insertSelective(record);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public ShopBas selectByPrimaryKey(String shopId) {
		return shopBasMapper.selectByPrimaryKey(shopId);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateByPrimaryKeySelective(ShopBas record) {
		return shopBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateByPrimaryKey(ShopBas record) {
		return shopBasMapper.updateByPrimaryKey(record);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<ShopBas> selectAll() {
		return shopBasMapper.selectAll();
	}

	/**
     * <p>功能：通过城市名称查找这个城市的所有商户<p>
     * <p>创建日期：2015年11月21日 下午5:29:40<p>
     * <p>作者：曾小明<p>
     * @param cityname
     * @return
     */
	@Override
	@RemoteAnno(value="BAS_READ")
	public String selectByCity(String city,String shopType) {
		List<ShopBas> list=shopBasMapper.selectByCity(city,shopType);
		if(list.size()==0){
			return null;
		}
		String json = JsonUtil.toJsonString(list);
		return json;
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<ShopBas> selectFuzzy(String shopname) {
		return shopBasMapper.selectFuzzy(shopname);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int update(ShopBas key, ShopBas record) {
		return shopBasMapper.update(key, record);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<String> selectShopTypeCity(String shoptype) {
		return shopBasMapper.selectShopTypeCity(shoptype);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public Map selectByUserId(String userid) {
		List<ShopBas> list=shopBasMapper.selectByUserId(userid);
		Map<String, String> shop = new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			shop.put(list.get(i).getShopId(), list.get(i).getShopname());
		}
		return shop;
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<ShopBas> selectByUserIdList(String userid) {
		return shopBasMapper.selectByUserId(userid);
	}

	@Override
	public ShopBas selectByDetail(String shopId) {
		return shopBasMapper.selectByDetail(shopId);
	}

	@Override
	public PageList<ShopBas> selectByCondition(ShopConditionBas shopCondition, PageBounds pageBounds) {
		return shopBasMapper.selectByCondition(shopCondition, pageBounds);
	}

	@Override
	public PageList<ShopBas> selectByCondition(ShopConditionBas shopCondition) {
		return shopBasMapper.selectByCondition(shopCondition);
	}

	@Override
	public PageList<ShopBas> selectByPage(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return shopBasMapper.selectByPage(wStr, pageBounds);
	}

	@Override
	public List<ShopBas> selectAllAvailableByFloor(String floorId) {
		return shopBasMapper.selectAllAvailableByFloor(floorId);
	}

	@Override
	public List<ShopBas> selectByShop(MallShop shop) {
		return shopBasMapper.selectByShop(shop);
	}

	@Override
	public List<ShopBas> selectCity(String city) {
		return shopBasMapper.selectCity(city);
	}

}
