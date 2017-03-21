package com.hibo.cms.lims.controller;

import java.util.List;

import javax.validation.Valid;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.lims.model.Busheetlim;
import com.hibo.cms.lims.model.Datalimflds;
import com.hibo.cms.lims.model.Datalimm;
import com.hibo.cms.lims.model.Datalimop;
import com.hibo.cms.lims.model.Datalims;
import com.hibo.cms.lims.service.IBusheetlimService;
import com.hibo.cms.lims.service.IDatalimfldsService;
import com.hibo.cms.lims.service.IDatalimmService;
import com.hibo.cms.lims.service.IDatalimopService;
import com.hibo.cms.lims.service.IDatalimsService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.user.model.User;
import com.hibo.cms.util.Envparam;



/**   
 * <p>标题：权限类型添加，查询，修改，删除</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 上午9:19:01</p>
 * <p>类全名：com.hibo.cms.lims.controller.DatalimmController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/datalimm")
public class DatalimmController {
	
	@Autowired
	private IDatalimmService datalimmService;
	@Autowired
	private IDatalimsService datalimsService;
	@Autowired
	private IBusheetlimService busheetlimService;
	@Autowired
	private IDatalimfldsService datalimfldsService;
	@Autowired
	private IDatalimopService datalimopService;
	
	
	
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model,@RequestParam(value="child",required=false) String child, @RequestParam(value="datalimmid",required=false) String datalimmid){
		List<Datalimm> datalimm = datalimmService.selectAll();
		model.addAttribute("datalimm", datalimm);
		model.addAttribute("child", child);
		model.addAttribute("datalimmid", datalimmid);
		return "lims/datalims_list";
	}
	/**
	 * <p>功能：根据权限类型查询字表按岗位定义权限数据<p>
	 * <p>创建日期：2015年10月20日 下午4:51:59<p>
	 * <p>作者：曾小明<p>
	 * @param datalimmid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getDatalims",method=RequestMethod.POST)
	@ResponseBody
	public String getChild(String datalimmid,Model model){
		List<Datalims> datalims = datalimsService.selectDatalims(datalimmid);
		model.addAttribute("datalims", datalims);
		String json = JsonUtil.toJsonString(datalims);
		return json;
	}
	/**
	 * <p>功能：根据权限类型查询单据权限关系数据<p>
	 * <p>创建日期：2015年10月20日 下午4:55:01<p>
	 * <p>作者：曾小明<p>
	 * @param datalimmid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getBusheetlim",method=RequestMethod.POST)
	@ResponseBody
	public String getBusheetlim(String datalimmid,Model model){
		List<Busheetlim> busheetlim = busheetlimService.selectBusheetlim(datalimmid);
		model.addAttribute("busheetlim", busheetlim);
		String json = JsonUtil.toJsonString(busheetlim);
		return json;
	}
	/**
	 * <p>功能：根据权限类型查询权限字段数据<p>
	 * <p>创建日期：2015年10月20日 下午4:56:50<p>
	 * <p>作者：曾小明<p>
	 * @param datalimmid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getDatalimflds",method=RequestMethod.POST)
	@ResponseBody
	public String getDatalimflds(String datalimmid,Model model){
		List<Datalimflds> datalimflds = datalimfldsService.selectDatalimflds(datalimmid);
		model.addAttribute("datalimflds", datalimflds);
		String json = JsonUtil.toJsonString(datalimflds);
		return json;
	}
	/**
	 * <p>功能：根据权限类型查询权限操作数据<p>
	 * <p>创建日期：2015年10月20日 下午4:58:24<p>
	 * <p>作者：曾小明<p>
	 * @param datalimmid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getDatalimop",method=RequestMethod.POST)
	@ResponseBody
	public String getDatalimop(String datalimmid,Model model){
		List<Datalimop> datalimop = datalimopService.selectDatalimop(datalimmid);
		model.addAttribute("datalimop", datalimop);
		String json = JsonUtil.toJsonString(datalimop);
		return json;
	}
	
	/**
	 * <p>功能：删除权限类型数据，并把字表关联数据删除<p>
	 * <p>创建日期：2015年10月20日 下午4:51:10<p>
	 * <p>作者：曾小明<p>
	 * @param datalimmid
	 * @return
	 */
	@RequestMapping(value="/{limid}/del")
	public String del(@PathVariable String limid){
		datalimsService.delete(limid);
		busheetlimService.delete(limid);
		datalimfldsService.delete(limid);
		datalimopService.delete(limid);
		datalimmService.deleteByDatalimmId(limid);
		return "redirect:/admin/datalimm/list";
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String delete(String datalimmid){
		int row=datalimsService.delete(datalimmid);
		busheetlimService.delete(datalimmid);
		datalimfldsService.delete(datalimmid);
		datalimopService.delete(datalimmid);
		datalimmService.deleteByDatalimmId(datalimmid);
		String json = JSON.toString(row);
		return json;
	}
	/**
	 * <p>功能：修改权限类型<p>
	 * <p>创建日期：2015年10月21日 下午2:23:35<p>
	 * <p>作者：曾小明<p>
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/{limid}/update",method=RequestMethod.GET)
	public String getUpdate(@PathVariable String limid,Model model){
		Datalimm datalimm=datalimmService.selectByDatalimmId(limid);	
		model.addAttribute("method", "put");
		model.addAttribute("bean", datalimm);
		return "lims/edit";
		
	}
	
	/**
	 * <p>功能：跳转到添加页面<p>
	 * <p>创建日期：2015年10月21日 下午5:30:48<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createProfile(Model model){
		Datalimm bean = new Datalimm();
		model.addAttribute("bean", bean);
		model.addAttribute("method", "post");
		return "lims/edit";
	}
	/**
	 * <p>功能：添加权限类型<p>
	 * <p>创建日期：2015年10月22日 上午9:20:51<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param bean
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/limid", method = RequestMethod.POST)
	public String add(Model model,@ModelAttribute("bean")@Valid Datalimm bean, BindingResult result){
		Datalimm datalimm=datalimmService.selectByDatalimmId(bean.getLimid());
		if(datalimm==null){
		User user = Envparam.getUser();// 或当前登录用户
		bean.setModifier(user.getUserid());
		datalimmService.insert(bean);
		}
		model.addAttribute("datalimmid", bean.getLimid());
		return "redirect:/admin/datalimm/list";
	}
	
	/**
	 * <p>功能：修改权限类型<p>
	 * <p>创建日期：2015年10月22日 上午9:21:41<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/limid", method = RequestMethod.PUT)
	public String update(@ModelAttribute("bean")@Valid Datalimm bean, BindingResult result, Model model){
		User user = Envparam.getUser();// 或当前登录用户
		bean.setModifier(user.getUserid());
		this.datalimmService.updateByDatalimmId(bean.getLimid(), bean);
		return "redirect:/admin/datalimm/list";
	}
	
	
}
