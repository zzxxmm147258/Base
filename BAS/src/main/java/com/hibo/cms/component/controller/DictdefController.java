package com.hibo.cms.component.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.component.model.Dictdef;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictdef.IDictdefService;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;

@Controller
@RequestMapping("/admin/dictdef")
public class DictdefController {
	
	@Autowired
	private IDictdefService dictdefService;
	@Autowired
	private IDictinfoService dictinfoService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		List<Dictdef> dictdefs = dictdefService.selectAll();
		model.addAttribute("dictdefs", dictdefs);
		return "component/list";
	}
	
	@RequestMapping(value="/getChild",method=RequestMethod.POST)
	@ResponseBody
	public String getChild(int dictid){
		List<Dictinfo> dictinfos = dictinfoService.selectByDictid(dictid,null);
		String json = JsonUtil.toJsonString(dictinfos);
		return json;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public int add(Dictdef dictdef){
		int num = dictdefService.insert(dictdef);
		return dictdef.getDictid();
	}
	
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public int del(int dictid){
		int num = dictdefService.deleteByDictid(dictid);
		dictinfoService.delectByDictid(dictid);
		return num;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public int update(int old_dictid,Dictdef dictdef){
		int num = dictdefService.updateByDictid(old_dictid, dictdef);
		dictinfoService.updateDictidByDictid(dictdef.getDictid(), old_dictid);
		return num;
	}
}
