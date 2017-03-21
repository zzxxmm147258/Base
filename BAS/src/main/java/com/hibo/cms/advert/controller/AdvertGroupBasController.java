package com.hibo.cms.advert.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.advert.model.AdvertGroupBas;
import com.hibo.cms.advert.service.IAdvertGroupBasService;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月3日 下午3:03:19</p>
 * <p>类全名：com.hibo.cms.advert.controller.AdvertGroupBasController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/advertgroup")
public class AdvertGroupBasController {
	
	@Autowired
	private IAdvertGroupBasService advertGroupBasService;
	@Autowired
	private IDictinfoService dictinfoService;
	
	/**
	 * <p>功能：列出全部分组</p>
	 * <p>创建日期：2016年3月3日下午4:29:47</p>
	 * <p>作者：吕康</p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		List<AdvertGroupBas> list = advertGroupBasService.selectAll();
		model.addAttribute("list", list);
		model.addAttribute("parentId", request.getParameter("parentId"));
		return "advert/list";
	}
	
	/**
	 * <p>功能：添加</p>
	 * <p>创建日期：2016年3月3日下午4:33:53</p>
	 * <p>作者：吕康</p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		List<Dictinfo> adPositions = dictinfoService.selectByDictid(5010);
		AdvertGroupBas advertGroup = new AdvertGroupBas();
		model.addAttribute("bean", advertGroup);
		model.addAttribute("adPositions", adPositions);
		return "advert/advertgroup_edit";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(AdvertGroupBas bean){
		advertGroupBasService.insert(bean);
		return "redirect:list?parentId="+bean.getId();
	}
	
	/**
	 * <p>功能：更新</p>
	 * <p>创建日期：2016年3月3日下午4:35:11</p>
	 * <p>作者：吕康</p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	public String update(@PathVariable String id,Model model){
		List<Dictinfo> adPositions = dictinfoService.selectByDictid(5010);
		AdvertGroupBas advertGroup = advertGroupBasService.selectByPrimaryKey(id);
		model.addAttribute("bean", advertGroup);
		model.addAttribute("adPositions", adPositions);
		return "advert/advertgroup_edit";
	}
	@RequestMapping(value="/{id}/update",method=RequestMethod.POST)
	public String update(AdvertGroupBas bean){
		advertGroupBasService.updatePrimaryKey(bean);
		return "redirect:/main/advertgroup/list?parentId="+bean.getId();
	}
	
	/**
	 * <p>功能：根据id删除</p>
	 * <p>创建日期：2016年3月3日下午4:39:57</p>
	 * <p>作者：吕康</p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int ajaxDel(String id){
		int num = advertGroupBasService.deleteByPrimaryKey(id);
		return num;
	}
	

}
