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

import com.hibo.cms.readexcel.model.Readexceldefg;
import com.hibo.cms.readexcel.service.readexceldefg.IReadexceldefgService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午5:47:46</p>
 * <p>类全名：com.hibo.cms.importexcel.controller.ReadexceldefgController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/readexceldefg")
public class ReadexceldefgController {

	@Autowired
	private IReadexceldefgService readexceldefgService;
	
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String list(String rxcode){
		List<Readexceldefg> list = readexceldefgService.selectByRxcode(rxcode);
		String json = JsonUtil.toJsonString(list);
		return json;
	}
	
	@RequestMapping(value="/{rxcode}/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest request){
		Readexceldefg eadexceldefg = new Readexceldefg();
		model.addAttribute(eadexceldefg);
		return "readexcel/defg/add";
	}
	@RequestMapping(value="/{rxcode}/add",method=RequestMethod.POST)
	public String add(Readexceldefg eadexceldefg,@PathVariable String rxcode,Model model){
		eadexceldefg.setRxcode(rxcode);
		eadexceldefg.setOperator(Envparam.getUser().getUsername());
		readexceldefgService.insert(eadexceldefg);
		return "redirect:/admin/readexceldef/list";
	}
	
	@RequestMapping(value="/{rxcode}/{fldname}/update",method=RequestMethod.GET)
	public String update(Model model,@PathVariable String rxcode,@PathVariable String fldname){
		Readexceldefg readexceldefg = readexceldefgService.select(fldname, rxcode);
		model.addAttribute(readexceldefg);
		return "readexcel/defg/add";
	}
	@RequestMapping(value="/{rxcode}/{fldname}/update",method=RequestMethod.POST)
	public String update(@Valid Readexceldefg readexceldefg,BindingResult result,Model model,@PathVariable String rxcode,@PathVariable String fldname){
		if(result.hasErrors()){
			return "readexcel/defg/add";
		}
		readexceldefg.setOperator(Envparam.getUser().getUsername());
		readexceldefgService.updateByOldKey(readexceldefg, fldname, rxcode);
		return "redirect:/admin/readexceldef/list";
	}
	
	/**
	 * 查看详细信息
	 * @param rxcode
	 * @param fldname
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{rxcode}/{fldname}/view", method = RequestMethod.GET)
	public String update(@PathVariable("rxcode")String rxcode,@PathVariable String fldname, Model model){
		model.addAttribute("bean",readexceldefgService.select(fldname, rxcode));
		return "readexcel/defg/view";
	}
	
	/**
	 * 删除
	 * @param rxcode
	 * @param fldname
	 * @return
	 */
	@RequestMapping(value = "/{rxcode}/{fldname}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("rxcode")String rxcode,@PathVariable String fldname){
		readexceldefgService.delete(fldname, rxcode);
		return "redirect:/admin/readexceldef/list";
	}
}
