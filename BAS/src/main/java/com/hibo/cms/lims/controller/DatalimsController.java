package com.hibo.cms.lims.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.hibo.cms.lims.model.Datalimflds;
import com.hibo.cms.lims.model.Datalimm;
import com.hibo.cms.lims.model.Datalimop;
import com.hibo.cms.lims.model.Datalims;
import com.hibo.cms.lims.model.DatalimsKey;
import com.hibo.cms.lims.service.IDatalimfldsService;
import com.hibo.cms.lims.service.IDatalimmService;
import com.hibo.cms.lims.service.IDatalimopService;
import com.hibo.cms.lims.service.IDatalimsService;
import com.hibo.cms.user.model.Role;
import com.hibo.cms.user.service.role.IRoleService;



/**   
 * <p>标题：按岗位定义权限</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午4:05:18</p>
 * <p>类全名：com.hibo.cms.lims.controller.DatalimsController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/datalims")
public class DatalimsController {

	@Autowired
	private IDatalimsService datalimsService;
	
	@Autowired
	private IDatalimmService datalimmService;
	
	@Autowired
	private IDatalimopService datalimopService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IDatalimfldsService datalimfldsService;
	/**
	 * <p>功能：删除操作<p>
	 * <p>创建日期：2015年10月22日 上午11:25:32<p>
	 * <p>作者：曾小明<p>
	 * @param datalimsid
	 * @return
	 */
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String delete(String limid,String opid,String utype, String ucode, Short idx){
		DatalimsKey key=new DatalimsKey();
		key.setLimid(limid);
		key.setOpid(opid);
		key.setUcode(ucode);
		key.setUtype(utype);
		key.setIdx(idx);
		int row=datalimsService.deleteByPrimaryKey(key);
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
		//获取角色信息————对应用户编码
		List<Role> role=roleService.findAllRoles();
		model.addAttribute("role", role);
		//获取字段
		List<Datalimflds> datalimflds=datalimfldsService.selectDatalimflds(datalimmid);
		model.addAttribute("datalimflds", datalimflds);
		
		//获取操作编码
		List<Datalimop> datalimop=datalimopService.selectDatalimop(datalimmid);
		model.addAttribute("datalimop", datalimop);
		
		Datalims bean = new Datalims();
		List<Datalimm> datalimm=new ArrayList<Datalimm>();
		Datalimm d = datalimmService.selectByDatalimmId(datalimmid);
		datalimm.add(d);
		model.addAttribute("datalimm", datalimm);
		model.addAttribute("bean", bean);
		model.addAttribute("method", "post");
		
		model.addAttribute("child", "datalims");
		model.addAttribute("datalimmid", datalimmid);
		return "lims/datalims_edit";
	}

	/**
	 * <p>功能：添加按岗位查询权限<p>
	 * <p>创建日期：2015年10月22日 上午9:20:51<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param bean
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/{datalimmid}/add", method = RequestMethod.POST)
	public String add(Model model,@ModelAttribute("bean")@Valid Datalims bean, BindingResult result,@PathVariable(value="datalimmid") String datalimmid){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		bean.setModifydate(formatter.format(new Date()));
		bean.setUtype("G");
		Datalims datalims=datalimsService.selectByPrimaryKey(bean);
		if(datalims==null){
		datalimsService.insert(bean);
		}
		model.addAttribute("child", "datalims");
		model.addAttribute("datalimmid", datalimmid);
		return "redirect:/admin/datalimm/list";
	}
	
	/**
	 * <p>功能：修改按岗位权限数据<p>
	 * <p>创建日期：2015年10月21日 下午2:23:35<p>
	 * <p>作者：曾小明<p>
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/{limid}/{opid}/{utype}/{ucode}/{idx}/update",method=RequestMethod.GET)
	public String update(Model model,@PathVariable String limid,@PathVariable String opid,
			@PathVariable String utype,@PathVariable String ucode,@PathVariable Short idx
			){
		DatalimsKey key=new DatalimsKey();
		key.setLimid(limid);
		key.setOpid(opid);
		key.setUcode(ucode);
		key.setUtype(utype);
		key.setIdx(idx);
		//获取权限类型
		List<Datalimm> datalimm=new ArrayList<Datalimm>();
		Datalimm limm = datalimmService.selectByDatalimmId(limid);
		datalimm.add(limm);
		model.addAttribute("datalimm", datalimm);
		
		Datalims datalims =datalimsService.selectByPrimaryKey(key);
		model.addAttribute("method", "put");
		model.addAttribute("bean", datalims);
		
		
		
		//获取角色信息————对应用户编码
		List<Role> role=roleService.findAllRoles();
		model.addAttribute("role", role);
		
		
		//获取当前父表字段显示在下拉框
		List<Datalimflds> datalimflds=datalimfldsService.selectDatalimflds(limid);
		model.addAttribute("datalimflds", datalimflds);

		//获取当前父表的操作编码
		List<Datalimop> datalimop=datalimopService.selectDatalimop(limid);
		model.addAttribute("datalimop", datalimop);
		
		
		model.addAttribute("child", "datalims");
		model.addAttribute("datalimmid", limid);
		return "lims/datalims_edit";
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
	@RequestMapping(value = "/{limid}/{opid}/{utype}/{ucode}/{idx}/update", method = RequestMethod.PUT)
	public String update(@ModelAttribute("bean")@Valid Datalims bean, BindingResult result, Model model,@PathVariable String limid,@PathVariable String opid,
			@PathVariable String utype,@PathVariable String ucode,@PathVariable Short idx){
		DatalimsKey key=new DatalimsKey();
		key.setLimid(limid);
		key.setOpid(opid);
		key.setUcode(ucode);
		key.setUtype(utype);
		key.setIdx(idx);
		
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		bean.setModifydate(formatter.format(new Date()));
		try{
		  this.datalimsService.updateByPrimaryKeySelective(key, bean);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		model.addAttribute("child", "datalims");
		model.addAttribute("datalimmid", limid);
		return "redirect:/admin/datalimm/list";
	}
	
	
}
