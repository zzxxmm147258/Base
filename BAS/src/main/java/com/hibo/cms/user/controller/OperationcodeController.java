package com.hibo.cms.user.controller;

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

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.user.model.Operationcode;
import com.hibo.cms.user.model.User;
import com.hibo.cms.user.service.operationcode.IOperationcodeService;
import com.hibo.cms.util.Envparam;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月14日 下午4:43:30</p>
 * <p>类全名：com.hibo.cms.user.controller.OperationcodeController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/operationcode")
public class OperationcodeController {

	@Autowired
	private IOperationcodeService operationcodeService;
	
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		//获取请求链接，用于页面分页用
		String url=request.getServletPath()+"?"+request.getQueryString();
		if(url.indexOf("&currentPage")>0){
			url = url.substring(0, url.indexOf("&currentPage"));
		}
		
		PageList<Operationcode> pageList = operationcodeService.selectAll(new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
		model.addAttribute("page", pageList.getPaginator());
		model.addAttribute("list", pageList);
		model.addAttribute("url", url);
		return "operationcode/list";
	}
	
	/**
	 * 编辑“用户-资源”关系时选择操作时模态框的模糊查询
	 * @param operationcode
	 * @return
	 */
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String list(Operationcode operationcode){
		List<Operationcode> list = operationcodeService.selectFuzzy(operationcode);
		return JsonUtil.toJsonString(list);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest request){
		model.addAttribute(new Operationcode());
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		return "operationcode/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid Operationcode operationcode,BindingResult result,Model model){
		if(result.hasErrors()){
			return "operationcode/add";
		}
		operationcode.setOperator(Envparam.getUser().getUsername());
		int num = operationcodeService.insert(operationcode);
		return "redirect:/admin/operationcode/add?addSuccess=true";
	}
	
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	public String update(Model model,@PathVariable String id){
		Operationcode operationcode = operationcodeService.select(id);
		model.addAttribute(operationcode);
		return "operationcode/add";
	}
	@RequestMapping(value="/{id}/update",method=RequestMethod.POST)
	public String update(@Valid Operationcode operationcode,BindingResult result,Model model,@PathVariable String id){
		if(result.hasErrors()){
			return "operationcode/add";
		}
		operationcode.setOperator(Envparam.getUser().getUsername());
		int num = operationcodeService.update(operationcode,id);
		model.addAttribute("updateSuccess", true);
		return "operationcode/add";
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int del(String operaid){
		int num = operationcodeService.delete(operaid);
		return num;
	}
	
}
