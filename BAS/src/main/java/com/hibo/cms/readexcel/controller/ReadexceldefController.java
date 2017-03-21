package com.hibo.cms.readexcel.controller;

import java.util.List;

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

import com.hibo.cms.readexcel.model.Readexceldef;
import com.hibo.cms.readexcel.service.readexceldef.IReadexceldefService;
import com.hibo.cms.util.Envparam;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午5:46:44</p>
 * <p>类全名：com.hibo.cms.importexcel.controller.ReadexceldefController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/readexceldef")
public class ReadexceldefController {
	
	@Autowired
	private IReadexceldefService readexceldefService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		List<Readexceldef> list = readexceldefService.selectAll();
		model.addAttribute("list",list);
		return "readexcel/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest request){
		Readexceldef readexceldef = new Readexceldef();
		model.addAttribute(readexceldef);
		return "readexcel/def/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Readexceldef readexceldef,Model model){
		readexceldef.setOperator(Envparam.getUser().getUsername());
		readexceldefService.insert(readexceldef);
		return "redirect:/admin/readexceldef/list";
	}
	
	@RequestMapping(value="/{rxcode}/update",method=RequestMethod.GET)
	public String update(Model model,@PathVariable String rxcode){
		Readexceldef readexceldef = readexceldefService.select(rxcode);
		model.addAttribute(readexceldef);
		return "readexcel/def/add";
	}
	@RequestMapping(value="/{rxcode}/update",method=RequestMethod.POST)
	public String update(@Valid Readexceldef readexceldef,BindingResult result,Model model,@PathVariable String rxcode){
		if(result.hasErrors()){
			return "readexcel/def/add";
		}
		readexceldef.setOperator(Envparam.getUser().getUsername());
		readexceldefService.updateByOldrxcode(readexceldef, rxcode);
		return "redirect:/admin/readexceldef/list";
	}
	
/*	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int del(String rxcode){
		int num = readexceldefService.delete(rxcode);
		return num;
	}*/
	
	/**
	 * 查看详细信息
	 * @param rxcode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{rxcode}/view", method = RequestMethod.GET)
	public String update(@PathVariable("rxcode")String rxcode, Model model){
		model.addAttribute("bean",readexceldefService.select(rxcode));
		return "readexcel/def/view";
	}
	
	/**
	 * 删除
	 * @param rxcode
	 * @return
	 */
	@RequestMapping(value = "/{rxcode}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("rxcode")String rxcode){
		readexceldefService.delete(rxcode);
		return "redirect:/admin/readexceldef/list";
	}

}
