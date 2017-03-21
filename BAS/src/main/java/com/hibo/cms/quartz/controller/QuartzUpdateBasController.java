package com.hibo.cms.quartz.controller;


import java.util.Date;
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
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.quartz.model.QuartzUpdateBas;
import com.hibo.cms.quartz.service.IQuartzUpdateBasService;


/**   
 * <p>标题：更新记录表</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月26日 下午4:37:46</p>
 * <p>类全名：com.hibo.cms.quartz.controller.QuartzUpdateBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/quartzupdate")
public class QuartzUpdateBasController {

	@Autowired 
	private  IQuartzUpdateBasService quartzUpdateBasService;
	/**
	 * <p>功能：查询<p>
	 * <p>创建日期：2015年11月26日 下午4:49:08<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<QuartzUpdateBas> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = quartzUpdateBasService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		return "quartzupdate/list";
	}
	
	/**
	 * <p>功能：删除<p>
	 * <p>创建日期：2015年11月26日 下午4:50:31<p>
	 * <p>作者：曾小明<p>
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String id){
		int row = quartzUpdateBasService.deleteByPrimaryKey(id);
		String json = JSON.toString(row);
		return json;
	}
	/**
	 * <p>功能：修改更新记录表<p>
	 * <p>创建日期：2015年11月26日 下午4:52:29<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	public String update(@PathVariable(value="id") String id,Model model){
		QuartzUpdateBas bean=quartzUpdateBasService.selectByPrimaryKey(id);
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		return "quartzupdate/add";
	}
	@RequestMapping(value="/{id}/update",method=RequestMethod.POST)
	public String update(@PathVariable(value="id") String id,@Valid QuartzUpdateBas bean,Model model){
		//bean.setUpdateDate(new Date());
		quartzUpdateBasService.updateByPrimaryKeySelective(bean);
		model.addAttribute("updateSuccess", true);
		return "redirect:/admin/quartzupdate/list";
	}
	
	/**
	 * <p>功能：添加<p>
	 * <p>创建日期：2015年11月26日 下午4:55:27<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		QuartzUpdateBas bean=new QuartzUpdateBas();
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		return "quartzupdate/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Valid QuartzUpdateBas bean){
		//bean.setUpdateDate(new Date());
		quartzUpdateBasService.insertSelective(bean);
		return "redirect:/admin/quartzupdate/list";
	}
}
