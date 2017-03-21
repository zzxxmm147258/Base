package com.hibo.cms.component.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;

@Controller
@RequestMapping("/admin/dictinfo")
public class DictinfoController {
	
	@Autowired
	private IDictinfoService dictinfoService;
	
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public int del(String dictid,String code){
		int num = dictinfoService.delete(dictid, code);
		return num;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Dictinfo dictinfo){
		int num = dictinfoService.insert(dictinfo);
		if(num > 0){
			return JsonUtil.toJsonString(dictinfo.getCode());
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public int update(String old_code,Dictinfo dictinfo){
		int num = dictinfoService.updateByOldKey(old_code, dictinfo);
		return num;
	}
	
}
