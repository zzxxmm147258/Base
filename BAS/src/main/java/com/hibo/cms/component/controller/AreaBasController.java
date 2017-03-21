package com.hibo.cms.component.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.fileplugin.service.FileService;
import com.hibo.cms.component.model.AreaBas;
import com.hibo.cms.component.service.areabas.IAreaBasService;


/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月19日 上午9:53:27</p>
 * <p>类全名：com.hibo.ebusi.payment.controller.AreaController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/areabas")
public class AreaBasController {

	@Autowired
	private IAreaBasService areaBasService;
	
	@Resource
	private FileService fileServie;
	
	@RequestMapping(value="/{parent}/list",method=RequestMethod.GET)
	public String getArea(@PathVariable("parent") String parent,Model model){
		List<AreaBas> areas = areaBasService.selectByParent(parent);
		String parentName = "顶级地区";
		if(!parent.equals("0")){
			String grand = "0";
			AreaBas parentArea = areaBasService.selectById(parent);
			if(parentArea.getParent()!=null && !parentArea.getParent().equals("")){
				grand = parentArea.getParent();
			}
			parentName = parentArea.getName();
			model.addAttribute("grand", grand);
		}
		model.addAttribute("areas", areas);
		model.addAttribute("parent", parent);
		model.addAttribute("parentName", parentName);
		return "component/area/list";
	}
	
	@RequestMapping(value="/{parentId}/add",method=RequestMethod.GET)
	public String add(Model model,@PathVariable String parentId){
		AreaBas area = new AreaBas();
		String parentName = "顶级地区";
		if(!parentId.equals("0")){
			AreaBas parentArea = areaBasService.selectById(parentId);
			area.setParent(parentId);
			area.setFullName(parentArea.getFullName());
			area.setTreePath(parentArea.getTreePath()+parentId+",");
			area.setArea1((Integer.parseInt(parentArea.getArea1())+1)+"");
			parentName = parentArea.getName();
		}else{
			area.setTreePath(",");
			area.setArea1("1");
		}
		model.addAttribute("parentName", parentName);
		model.addAttribute("area",area);
		return "component/area/add";
	}
	@RequestMapping(value="/{parentId}/add",method=RequestMethod.POST)
	public String add(Model model,@PathVariable String parentId,AreaBas area,@RequestParam(value = "img") MultipartFile img){
		if(!img.isEmpty()){//图片
			String[] url = fileServie.upload(img, "200*200");
			area.setImgUrl(url[0]);
			area.setImgSmallurl(url[1]);
		}
		area.setFullName(area.getFullName()+area.getName());
		int num = areaBasService.insert(area);
		return "redirect:/admin/areabas/" + parentId + "/list";
	}
	
	@RequestMapping(value="/{areaId}/update",method=RequestMethod.GET)
	public String update(@PathVariable String areaId,Model model){
		String parentName = "顶级地区";
		AreaBas area = areaBasService.selectById(areaId);
		String parent = area.getParent();
		String newFullName = "";
		if(parent != null && !parent.equals("")){
			AreaBas parentArea = areaBasService.selectById(parent);
			parentName = parentArea.getName();
			newFullName = parentArea.getFullName();
		}
		area.setFullName(newFullName);
		model.addAttribute("parentName",parentName);
		model.addAttribute("area",area);
		return "component/area/add";
	}
	@RequestMapping(value="/{areaId}/update",method=RequestMethod.POST)
	public String update(AreaBas area,@RequestParam(value = "img") MultipartFile img){
		if(!img.isEmpty()){//图片
			String[] url = fileServie.upload(img, "200*200");
			area.setImgUrl(url[0]);
			area.setImgSmallurl(url[1]);
		}else{
			
		}
		area.setFullName(area.getFullName()+area.getName());
		int num = areaBasService.update(area);
		String parentId = area.getParent();
		if(parentId==null || parentId.equals("")){
			parentId = "0";
		}
		return "redirect:/admin/areabas/" + parentId + "/list";
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int del(String areaId){
		int num = areaBasService.delete(areaId);
		return num;
	}
	
}
