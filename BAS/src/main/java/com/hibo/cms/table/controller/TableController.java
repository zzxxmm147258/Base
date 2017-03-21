package com.hibo.cms.table.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hibo.cms.table.model.Tables;
import com.hibo.cms.table.service.ITableService;

/** <p>标题：</p>
 * <p>功能： 维护表结构</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月30日 下午4:01:29</p>
 * <p>类全名：com.hibo.cms.table.controller.TableController</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */

@Controller
@RequestMapping("/admin/tables")
public class TableController {
	
	@Autowired
	private ITableService tableService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(
			Model model,
			HttpSession session
			){
		Tables table = new Tables();
		model.addAttribute("table",table);
		return "tables/list";
	}
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
//	@ResponseBody
	public String add(Model model, @Valid Tables table, BindingResult result){
		model.addAttribute("table",table);
		if(result.hasErrors()){
			return "系统ID不能为空！";//"tables/list";
		}
		String sysid = table.getSysid();
		String msg = tableService.createTable(sysid);
		model.addAttribute("msg", msg);
		return "tables/list";
	}
			
}
