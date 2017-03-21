package com.hibo.cms.component.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.component.service.areabas.IAreaBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.user.model.User;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月11日 下午3:12:32</p>
 * <p>类全名：com.hibo.cms.component.controller.AreaCommonController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/common/areabas")
public class AreaCommonController {

	@Autowired
	private IAreaBasService areaBasService;
	
	@RequestMapping(value="/getAddress",method=RequestMethod.POST)
	@ResponseBody
	public String getArea(String name,int param){
		List<String> address=new ArrayList<String>();
		//根据选择省查询市
		if(param==0){
			//address = areaBasService.selectByCitys(name);
			address = areaBasService.selectCityByProvince(name);
		}
		//根据选择市查询区
		if(param==1){
			//address = areaBasService.selectByAreas(null, name);
			address = areaBasService.selectAreaByCity(name);
		}
		String json = JsonUtil.toJsonString(address);
		return json;
	}
	
	@RequestMapping(value="/getCity",method=RequestMethod.POST)
	@ResponseBody
	public String getCity(Model model,String  city){
		List<String> citys=new ArrayList<String>();
		if(city!=null){
		citys = areaBasService.selectByCity(city);
		}
		model.addAttribute("city", citys);
		String json = JsonUtil.toJsonString(citys);
		return json;
	}
	
	/**
	 * 模糊查询城市
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String list(User user){
		String name=user.getUsername();
		//List<String> city = areaBasService.selectByCity(name);
		List<String> city = areaBasService.selectCity(name);
		List<User> list=new ArrayList<User>();
		if(city!=null&&city.size()>0){
			for(int i=0;i<city.size();i++){
				User u=new User();
				u.setUsername(city.get(i));
				u.setUserid(city.get(i));
				list.add(u);
			}
		}
		return JsonUtil.toJsonString(list);
	}
	
	/**
	 * <p>功能：模糊查询省,市，区<p>
	 * <p>创建日期：2015年11月26日 上午11:01:15<p>
	 * <p>作者：曾小明<p>
	 * @param user
	 * @param provincename
	 * @return
	 */
	@RequestMapping(value="/province.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectlist(String prname){
		List<String> provinces = areaBasService.selectByProvince(prname);
		return JsonUtil.toJsonString(provinces);
	}
	@RequestMapping(value="/city.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectCityList(String cityname){
		List<String> city = areaBasService.selectByCity(cityname);
		return JsonUtil.toJsonString(city);
	}
	@RequestMapping(value="/area.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectAreaList(String areaname){
		List<String> area = areaBasService.selectByArea(areaname);
		return JsonUtil.toJsonString(area);
	}
	
}
