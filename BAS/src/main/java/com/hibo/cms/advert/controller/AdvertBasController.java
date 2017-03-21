package com.hibo.cms.advert.controller;

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
import com.hibo.cms.advert.model.AdvertBas;
import com.hibo.cms.advert.service.IAdvertBasService;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月2日 下午1:54:25</p>
 * <p>类全名：com.hibo.cms.advert.controller.AdvertBasController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/advert")
public class AdvertBasController {
	
	@Autowired
	private IAdvertBasService advertBasService;

	@Resource
	private FileService fileServie;
	
	@Resource
	private IDictinfoService dictinfoService;
	
	/**
	 * <p>功能：根据groupId查出对应列表</p>
	 * <p>创建日期：2016年3月3日下午3:46:32</p>
	 * <p>作者：吕康</p>
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value="/selectByGroupId.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String ajaxSelectByGroupId(String groupId){
		List<AdvertBas> list = advertBasService.selectByGroupId(groupId);
		return JsonUtil.toJsonString(list);
	}
	
	/**
	 * <p>功能：删除</p>
	 * <p>创建日期：2016年3月3日下午3:50:16</p>
	 * <p>作者：吕康</p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int ajaxDel(String id){
		int num = advertBasService.deleteByPrimaryKey(id);
		return num;
	}
	
	/**
	 * <p>功能：添加</p>
	 * <p>创建日期：2016年3月3日下午4:05:10</p>
	 * <p>作者：吕康</p>
	 * @param groupId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{groupId}/add",method=RequestMethod.GET)
	public String add(Model model){
		AdvertBas advert = new AdvertBas();
		model.addAttribute("bean", advert);
		return "advert/advert_edit";
	}
	
	@RequestMapping(value="/{groupId}/add",method=RequestMethod.POST)
	public String add(@PathVariable String groupId,AdvertBas bean,@RequestParam(value = "img") MultipartFile img){
		if(!img.isEmpty()){
			String url = fileServie.upload(img);
			bean.setPath(url);
		}
		bean.setGroupId(groupId);
		advertBasService.insertSelective(bean);
		return "redirect:/main/advertgroup/list?parentId="+groupId;
	}
	
	/**
	 * <p>功能：更新</p>
	 * <p>创建日期：2016年3月3日下午4:05:01</p>
	 * <p>作者：吕康</p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	public String update(@PathVariable String id,Model model){
		AdvertBas advert = advertBasService.selectByPrimaryKey(id);
		model.addAttribute("bean", advert);
		return "advert/advert_edit";
	}
	@RequestMapping(value="/{id}/update",method=RequestMethod.POST)
	public String update(AdvertBas bean,@RequestParam(value = "img") MultipartFile img){
		if(!img.isEmpty()){
			String url = fileServie.upload(img);
			bean.setPath(url);
		}
		advertBasService.updateByPrimaryKeySelective(bean);
		return "redirect:/main/advertgroup/list?parentId="+bean.getGroupId();
	}
	

}
