package com.hibo.cms.lims.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.lims.model.Datalimm;
import com.hibo.cms.lims.model.Datalimop;
import com.hibo.cms.lims.model.DatalimopKey;
import com.hibo.cms.lims.service.IDatalimmService;
import com.hibo.cms.lims.service.IDatalimopService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 下午3:27:44</p>
 * <p>类全名：com.hibo.cms.lims.controller.DatalimopController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/datalimop")
public class DatalimopController {
	
	@Autowired
	private IDatalimopService datalimopService;
	
	@Autowired
	private IDatalimmService datalimmService;
	/**
	 * <p>功能：删除操作<p>
	 * <p>创建日期：2015年10月22日 上午11:25:32<p>
	 * <p>作者：曾小明<p>
	 * @param datalimsid
	 * @return
	 */
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String delete(String limid,String opid){
		DatalimopKey key=new DatalimopKey();
		key.setLimid(limid);
		key.setOpid(opid);
		int row=datalimopService.deleteByPrimaryKey(key);
		String json = JSON.toString(row);
		return json;
	}
	/**
	 * <p>功能：跳转到添加页面<p>
	 * <p>创建日期：2015年10月21日 下午5:30:48<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{datalimmid}/add", method = RequestMethod.GET)
	public String createProfile(@PathVariable(value="datalimmid") String datalimmid,Model model){
		List<Datalimm> datalimm=new ArrayList<Datalimm>();
		Datalimm d = datalimmService.selectByDatalimmId(datalimmid);
		datalimm.add(d);
		model.addAttribute("datalimm", datalimm);
		
		Datalimop bean = new Datalimop();
		model.addAttribute("bean", bean);
		model.addAttribute("method", "post");
		
		model.addAttribute("child", "datalimop");
		model.addAttribute("datalimmid", datalimmid);
		return "lims/datalimop_edit";
	}

	/**
	 * <p>功能：添加权限操作<p>
	 * <p>创建日期：2015年10月22日 上午9:20:51<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param bean
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/{datalimmid}/add", method = RequestMethod.POST)
	public String add(Model model,@ModelAttribute("bean")@Valid Datalimop bean, BindingResult result,@PathVariable(value="datalimmid") String datalimmid){
		Datalimop datalimop=datalimopService.selectByPrimaryKey(bean);
		if(datalimop==null){
		datalimopService.insert(bean);
		}
		model.addAttribute("child", "datalimop");
		model.addAttribute("datalimmid", datalimmid);
		return "redirect:/admin/datalimm/list";
	}
	
	/**
	 * <p>功能：修改权限操作<p>
	 * <p>创建日期：2015年10月21日 下午2:23:35<p>
	 * <p>作者：曾小明<p>
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/{limid}/{opid}/update",method=RequestMethod.GET)
	public String update(Model model,@PathVariable String limid,@PathVariable String opid){
		DatalimopKey key=new DatalimopKey();
		key.setLimid(limid);
		key.setOpid(opid);
		List<Datalimm> datalimm=new ArrayList<Datalimm>();
		Datalimm limm = datalimmService.selectByDatalimmId(limid);
		datalimm.add(limm);
		model.addAttribute("datalimm", datalimm);
		Datalimop datalimop =datalimopService.selectByPrimaryKey(key);
		model.addAttribute("method", "put");
		model.addAttribute("bean", datalimop);
		
		model.addAttribute("child", "datalimop");
		model.addAttribute("datalimmid", limid);
		return "lims/datalimop_edit";
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
	@RequestMapping(value = "/{limid}/{opid}/update", method = RequestMethod.PUT)
	public String update(@ModelAttribute("bean")@Valid Datalimop bean, BindingResult result, Model model,@PathVariable String limid,@PathVariable String opid){
		DatalimopKey key=new DatalimopKey();
		key.setLimid(limid);
		key.setOpid(opid);
		try {
		this.datalimopService.updateByPrimaryKeySelective(key, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("child", "datalimop");
		model.addAttribute("datalimmid", limid);
		return "redirect:/admin/datalimm/list";
	}
}
