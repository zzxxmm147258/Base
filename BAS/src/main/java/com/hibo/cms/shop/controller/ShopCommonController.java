package com.hibo.cms.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.shop.model.BlockBas;
import com.hibo.cms.shop.model.BuildingBas;
import com.hibo.cms.shop.model.DistrictBas;
import com.hibo.cms.shop.model.FloorBas;
import com.hibo.cms.shop.model.ShopBas;
import com.hibo.cms.shop.model.ShopBasInfo;
import com.hibo.cms.shop.model.ShopConditionBas;
import com.hibo.cms.shop.service.IBlockBasService;
import com.hibo.cms.shop.service.IBuildingBasService;
import com.hibo.cms.shop.service.IDistrictBasService;
import com.hibo.cms.shop.service.IFloorBasService;
import com.hibo.cms.shop.service.IShopBasInfoSevice;
import com.hibo.cms.shop.service.IShopBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月26日 下午2:31:00</p>
 * <p>类全名：com.hibo.cms.shop.controller.ShopCommonController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/common/shop")
public class ShopCommonController {
	
	@Autowired
	private IShopBasService shopBasService;
	@Autowired
	private IDistrictBasService districtBasService;
	@Autowired
	private IBlockBasService blockBasService;
	@Autowired
	private IBuildingBasService buildingBasService;
	@Autowired
	private IFloorBasService floorBasService;
	
	@Resource
	private IShopBasInfoSevice shopBasInfoSevice;
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public String list(int page,int limit,String district,String block,String buildingNo,String floors,String shopName){
		
		ShopConditionBas shopCondition = new ShopConditionBas();
		shopCondition.setDistrict(district);
		shopCondition.setBlockNo(block);
		shopCondition.setBuildingNo(buildingNo);
		shopCondition.setFloorNo(floors);
		shopCondition.setShopname(shopName);
		PageList<ShopBas> pageList = null;
		if(0==page && 0==limit){
			pageList = shopBasService.selectByCondition(shopCondition);
		}else{
			pageList = shopBasService.selectByCondition(shopCondition,new PageBounds(page,limit));
		}
		String result = JsonUtil.toJsonString(pageList);
		return result;
	}
	
	/**
	 * @功能:根据shopId获取商铺简介
	 * @作者:周雷
	 * @时间:2016年1月4日 上午10:49:56
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value="/shopDesc",method=RequestMethod.POST)
	@ResponseBody
	public String shopDesc(String shopId){
		List<ShopBasInfo> infoList = shopBasInfoSevice.selectListByShopId(shopId);
		String result = JsonUtil.toJsonString(infoList);
		return result;
	}
	
	/**
	 * @功能:根据shopId，showtype获取商铺简介
	 * @作者:周雷
	 * @时间:2016年1月4日 上午10:49:56
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value="/shopDescType",method=RequestMethod.POST)
	@ResponseBody
	public String shopDescType(String shopId,String showType){
		List<ShopBasInfo> infoList = shopBasInfoSevice.selectListByIdAndType(shopId, showType);
		String result = JsonUtil.toJsonString(infoList);
		return result;
	}
	
	/**
	 * <p>功能： 根据shopId查出商铺信息</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年12月30日 下午6:42:42</p>
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value="/shopInfo",method=RequestMethod.POST)
	@ResponseBody
	public String shopInfo(String shopId){
		ShopBas shop = shopBasService.selectByPrimaryKey(shopId);
		String result = JsonUtil.toJsonString(shop);
		return result;
	}
	
	/**
	 * <p>功能：查出所有可用商圈 </p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2016年2月18日 下午5:56:59</p>
	 * @return
	 */
	@RequestMapping(value="/getDistrict",method=RequestMethod.POST)
	@ResponseBody
	public String getDistrict(){
		List<DistrictBas> list = districtBasService.selectAllAvailable();
		return JsonUtil.toJsonString(list);
	}
	
	/**
	 * <p>功能：根据商圈id查出所有可用街区 </p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2016年2月18日 下午6:08:43</p>
	 * @param district
	 * @return
	 */
	@RequestMapping(value="/getBlock",method=RequestMethod.POST)
	@ResponseBody
	public String getBlock(String id){
		List<BlockBas> list = blockBasService.selectAllAvailableByDistrict(id);
		return JsonUtil.toJsonString(list);
	}
	
	/**
	 * <p>功能： 根据街区ID查出所有可用楼</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2016年2月18日 下午6:18:02</p>
	 * @param block
	 * @return
	 */
	@RequestMapping(value="/getBuilding",method=RequestMethod.POST)
	@ResponseBody
	public String getBuilding(String id){
		List<BuildingBas> list = buildingBasService.selectAllAvailableByBlock(id);
		return JsonUtil.toJsonString(list);
	}
	
	/**
	 * <p>功能： 根据楼ID查出所有可用层</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2016年2月18日 下午6:24:16</p>
	 * @param building
	 * @return
	 */
	@RequestMapping(value="/getFloorBas",method=RequestMethod.POST)
	@ResponseBody
	public String getFloorBas(String id){
		List<FloorBas> list = floorBasService.selectAllAvailableByBuilding(id);
		return JsonUtil.toJsonString(list);
	}
	
}
