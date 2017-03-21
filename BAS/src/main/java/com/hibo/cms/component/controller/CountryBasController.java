package com.hibo.cms.component.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.ObjectId;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.component.model.CountryBas;
import com.hibo.cms.component.service.country.ICountryBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.user.model.User;


/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月11日 下午3:34:40</p>
 * <p>类全名：com.hibo.cms.component.controller.CountryBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/countrybas")
public class CountryBasController {

	@Autowired
	private ICountryBasService countryBasService;
	
	/**
	 * <p>功能：查询国家<p>
	 * <p>创建日期：2015年11月12日 上午9:42:17<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<CountryBas> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = countryBasService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		Map<String, String> lockeds = new HashMap<String, String>();
		lockeds.put("0", "未启用");
		lockeds.put("1", "已启用");
		model.addAttribute("lockeds", lockeds);
		return "component/country/list";
	}
	
	/**
	 * <p>功能：删除国家<p>
	 * <p>创建日期：2015年11月12日 上午9:42:39<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String id){
		int row = countryBasService.deleteByPrimaryKey(id);
		String json = JSON.toString(row);
		return json;
	}
	
	/**
	 * <p>功能：修改国家<p>
	 * <p>创建日期：2015年11月12日 上午9:45:20<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	public String update(@PathVariable(value="id") String id,Model model){
		CountryBas bean=countryBasService.selectByPrimaryKey(id);
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		return "component/country/add";
	}
	@RequestMapping(value="/{id}/update",method=RequestMethod.POST)
	public String update(@PathVariable(value="id") String id,@Valid CountryBas bean,Model model){
		countryBasService.updateByPrimaryKey(bean);
		model.addAttribute("updateSuccess", true);
		return "redirect:/admin/countrybas/list";
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		CountryBas bean=new CountryBas();
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		return "component/country/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Valid CountryBas bean){
		bean.setId(ObjectId.getUUId());
		countryBasService.insert(bean);
		return "redirect:/admin/countrybas/list";
	}
	
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectlist(User user){
		String title=user.getUsername();
		List<CountryBas> city = countryBasService.selectList(title);
		List<User> list=new ArrayList<User>();
		if(city!=null&&city.size()>0){
			for(int i=0;i<city.size();i++){
				User u=new User();
				u.setUsername(city.get(i).getTitle());
				u.setUserid(city.get(i).getCode());
				list.add(u);
			}
		}
		return JsonUtil.toJsonString(list);
	}
}
