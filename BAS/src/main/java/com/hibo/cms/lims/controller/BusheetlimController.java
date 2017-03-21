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

import com.hibo.cms.lims.model.Busheetlim;
import com.hibo.cms.lims.model.BusheetlimKey;
import com.hibo.cms.lims.model.Datalimm;
import com.hibo.cms.lims.service.IBusheetlimService;
import com.hibo.cms.lims.service.IDatalimmService;
import com.hibo.cms.user.model.Resource;
import com.hibo.cms.user.service.resource.IResourceService;





/**   
 * <p>标题：</p>
 * <p>功能： 单据权限关系</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 下午3:21:51</p>
 * <p>类全名：com.hibo.cms.lims.controller.BusheetlimController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/busheetlim")
public class BusheetlimController {
	
	@Autowired
	private IBusheetlimService busheetlimService;
	
	@Autowired
	private IDatalimmService datalimmService;
	
	@Autowired
	private IResourceService resourceImpl;
	/**
	 * <p>功能：删除操作<p>
	 * <p>创建日期：2015年10月22日 上午11:25:32<p>
	 * <p>作者：朱运松<p>
	 * @param datalimsid
	 * @return
	 */
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String delete( String limid, String resourceid, Short idx ){
		BusheetlimKey key=new BusheetlimKey();
		key.setLimid(limid);
		key.setResourceid(resourceid);
		key.setIdx(idx);
		int row=busheetlimService.deleteByPrimaryKey(key);
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
		//获取父表ID
		List<Datalimm> datalimm=new ArrayList<Datalimm>();
		Datalimm d = datalimmService.selectByDatalimmId(datalimmid);
		datalimm.add(d);
		model.addAttribute("datalimm", datalimm);
		//获取资源信息
		List<Resource> resource=resourceImpl.selectAll();
		model.addAttribute("resource", resource);
		
		Busheetlim bean = new Busheetlim();
		model.addAttribute("bean", bean);
		model.addAttribute("method", "post");
		
		model.addAttribute("child", "busheetlim");
		model.addAttribute("datalimmid", datalimmid);
		return "lims/busheetlim_edit";
	}

	/**
	 * <p>功能：添加单据权限关系<p>
	 * <p>创建日期：2015年10月22日 上午9:20:51<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param bean
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/{datalimmid}/add", method = RequestMethod.POST)
	public String add(@PathVariable(value="datalimmid") String datalimmid,Model model,@ModelAttribute("bean")@Valid Busheetlim bean, BindingResult result){
		Busheetlim busheetlim =busheetlimService.selectByPrimaryKey(bean);
		if(busheetlim==null){
		busheetlimService.insert(bean);
		}
		model.addAttribute("child", "busheetlim");
		model.addAttribute("datalimmid", datalimmid);
		return "redirect:/admin/datalimm/list";
	}
	
	/**
	 * <p>功能：修改单据权限关系<p>
	 * <p>创建日期：2015年10月21日 下午2:23:35<p>
	 * <p>作者：曾小明<p>
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/{limid}/{resourceid}/{idx}/update",method=RequestMethod.GET)
	public String update(Model model,@PathVariable String limid,@PathVariable String resourceid,@PathVariable Short idx){
		BusheetlimKey key=new BusheetlimKey();
		key.setLimid(limid);
		key.setResourceid(resourceid);
		key.setIdx(idx);
		List<Datalimm> datalimm=new ArrayList<Datalimm>();
		Datalimm limm = datalimmService.selectByDatalimmId(limid);
		datalimm.add(limm);
		model.addAttribute("datalimm", datalimm);
		Busheetlim busheetlim =busheetlimService.selectByPrimaryKey(key);
		model.addAttribute("method", "put");
		model.addAttribute("bean", busheetlim);
		
	 //获取资源号。联合主键不能修改
		List<Resource> resource=resourceImpl.selectAll();
		model.addAttribute("resource", resource);
		
		model.addAttribute("child", "busheetlim");
		model.addAttribute("datalimmid",limid);
		return "lims/busheetlim_edit";
	}
	
	/**
	 * <p>功能：修改单据权限关系<p>
	 * <p>创建日期：2015年10月22日 上午9:21:41<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{limid}/{resourceid}/{idx}/update", method = RequestMethod.PUT)
	public String update(@ModelAttribute("bean")@Valid Busheetlim bean, BindingResult result, Model model,@PathVariable String limid,@PathVariable String resourceid,@PathVariable Short idx){
		BusheetlimKey key=new BusheetlimKey();
		key.setLimid(limid);
		key.setResourceid(resourceid);
		key.setIdx(idx);
	    try {
	    	this.busheetlimService.updateByPrimaryKeySelective(key, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    model.addAttribute("child", "busheetlim");
		model.addAttribute("datalimmid", limid);
		return "redirect:/admin/datalimm/list";
	}

}
