package com.hibo.cms.user.controller;

import java.util.List;
import java.util.Map;

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
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.user.model.Menu;
import com.hibo.cms.user.service.menu.IMenuService;
import com.hibo.cms.util.Envparam;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {
	
	@Autowired
	private IMenuService menuService;

	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			PageList<Menu> pageList = null;
			pageList = menuService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			//pageList = menuService.selectMenusBySysId(sysid,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		
		//00系统登陆可以看所有系统的菜单，其他系统登录只能看自己的
		String sys = Envparam.getLoginInfo().getSysid();
		if(!"00".equals(sys)){
			model.addAttribute("sys", sys);
		}
		return "menu/list";
	}
	
	/**
	 * 编辑“用户-菜单”关系时选择菜单时模态框的模糊查询
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String list(Menu menu){
		List<Menu> list = menuService.selectFuzzy(menu);
		return JsonUtil.toJsonString(list);
	}

	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest request){
		model.addAttribute(new Menu());
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		return "menu/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid Menu menu,BindingResult result,Model model){
		if(result.hasErrors()){
			return "menu/add";
		}
		menu.setOperator(Envparam.getLoginInfo().getUsername());
		menuService.insert(menu);
		return "redirect:/admin/menu/add?addSuccess=true";
	}
	
	@RequestMapping(value="/{menuid}/update",method=RequestMethod.GET)
	public String update(@PathVariable String menuid,Model model){
		Menu menu = menuService.selectByMenuid(menuid);
		model.addAttribute("menu",menu);
		return "menu/add";
	}
	@RequestMapping(value="/{menuid}/update",method=RequestMethod.POST)
	public String update(@Valid Menu menu,BindingResult result,@PathVariable String menuid,Model model){
		if(result.hasErrors()){
			return "menu/add";
		}
		menu.setOperator(Envparam.getLoginInfo().getUsername());
		int num = menuService.update(menuid, menu);
		model.addAttribute("updateSuccess", true);
		return "menu/add";
	}
	

	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int del(String menuid){
		int num = menuService.deleteByMenuid(menuid);
		return num;
	}
	
/*	@RequestMapping(value="/add.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String add(Menu menu,HttpSession session){
		LoginInfo loginfo = (LoginInfo) session.getAttribute("loginInfo");
		menu.setSysid(loginfo.getSysid());
		int num = menuService.insert(menu);
		return menu.getMenuid();
	}
	
	
	@RequestMapping(value="update.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int update(String old_menuid,Menu menu){
		int num = menuService.update(old_menuid, menu);
		return num;
	}*/
	
	
	/*	//如果要让子节点ID自增，就通过这个方法
	@RequestMapping(value="/{parentId}/add",method=RequestMethod.GET)
	public String add(@PathVariable String parentId,Model model){
		Session session = SecurityUtils.getSubject().getSession();
		LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
		Menu menu = new Menu();
		//TODO:根据父级菜单的ID算出下一个子菜单ID
		if("root".equals(parentId)){
			parentId = "";
		}
		String id = menuService.selectMaxChildId(parentId+"__");
		if(null != id){
			int childId = Integer.parseInt(id)+1;
			id = String.valueOf(childId);
		}else{
			id = parentId+"00";
		}
		menu.setMenuid(id);
		menu.setSysid(loginInfo.getSysid());
		menu.setLevel(parentId.length()/2+1);
		model.addAttribute(menu);
		return "menu/add";
	}*/
}
