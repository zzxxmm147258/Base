package com.hibo.cms.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.user.model.Shirolimit;
import com.hibo.cms.user.service.shirolimit.IShirolimitService;
import com.hibo.cms.util.Envparam;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月29日 上午10:34:42</p>
 * <p>类全名：com.hibo.cms.user.controller.ShiroController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/shirolimit")
public class ShirolimitController {

	@Autowired
	private IShirolimitService shirolimitService;
	
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		//获取请求链接，用于页面分页用
		String url=request.getServletPath()+"?"+request.getQueryString();
		if(url.indexOf("&currentPage")>0){
			url = url.substring(0, url.indexOf("&currentPage"));
		}
		
		PageList<Shirolimit> pageList = shirolimitService.selectAll(new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
		model.addAttribute("page", pageList.getPaginator());
		model.addAttribute("list", pageList);
		model.addAttribute("url", url);
		return "shirolimit/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest request){
		model.addAttribute(new Shirolimit());
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		return "shirolimit/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid Shirolimit shirolimit,BindingResult result,Model model){
		if(result.hasErrors()){
			return "shirolimit/add";
		}
		shirolimit.setOperator(Envparam.getUser().getUsername());
		shirolimit.setSysid(shirolimit.getSysid().substring(0, 2));
		int num = shirolimitService.insert(shirolimit);
		return "redirect:/admin/shirolimit/add?addSuccess=true";
	}
	
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	public String update(Model model,@PathVariable String id){
		Shirolimit shirolimit = shirolimitService.select(id);
		model.addAttribute(shirolimit);
		return "shirolimit/add";
	}
	@RequestMapping(value="/{id}/update",method=RequestMethod.POST)
	public String update(@Valid Shirolimit shirolimit,BindingResult result,Model model){
		if(result.hasErrors()){
			return "shirolimit/add";
		}
		shirolimit.setOperator(Envparam.getUser().getUsername());
		shirolimit.setSysid(shirolimit.getSysid().substring(0, 2));
		int num = shirolimitService.update(shirolimit);
		model.addAttribute("updateSuccess", true);
		return "shirolimit/add";
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int del(String shiroid){
		int num = shirolimitService.delete(shiroid);
		return num;
	}
}
