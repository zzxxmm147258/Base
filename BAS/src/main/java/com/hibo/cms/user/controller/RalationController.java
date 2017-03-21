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
import com.hibo.cms.user.model.Menu;
import com.hibo.cms.user.model.Operationcode;
import com.hibo.cms.user.model.Resource;
import com.hibo.cms.user.model.Role;
import com.hibo.cms.user.model.RoleMenuKey;
import com.hibo.cms.user.model.RoleResourceKey;
import com.hibo.cms.user.model.RoleUsersKey;
import com.hibo.cms.user.model.User;
import com.hibo.cms.user.service.menu.IMenuService;
import com.hibo.cms.user.service.operationcode.IOperationcodeService;
import com.hibo.cms.user.service.ralation.IRalationService;
import com.hibo.cms.user.service.resource.IResourceService;
import com.hibo.cms.user.service.role.IRoleService;
import com.hibo.cms.user.service.user.IUserService;
import com.hibo.cms.util.Envparam;

@Controller
@RequestMapping("/admin/ralation")
public class RalationController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private IOperationcodeService operationcodeService;
	@Autowired
	private IRalationService ralationService;
	
	@RequestMapping(value="/ru/list",method=RequestMethod.GET)
	public String ruList(Model model, HttpServletRequest request){
		String select = request.getParameter("select");
		if(select==null){
			//获取请求链接，用于页面分页用
			String url=request.getServletPath()+"?"+request.getQueryString();
			if(url.indexOf("&currentPage")>0){
				url = url.substring(0, url.indexOf("&currentPage"));
			}
			RoleUsersKey ru = new RoleUsersKey();
			ru.setRoleid(request.getParameter("roleid"));
			ru.setUserid(request.getParameter("userid"));
			PageList<RoleUsersKey> pageList = null;
			pageList = ralationService.selectRuByCondition(ru,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("co",ru);
			model.addAttribute("list", pageList);
			model.addAttribute("url", url);
		}else{
			model.addAttribute("select",select);
		}
		return "ralation/ru/list";
	}
	
	@RequestMapping(value="/rm/list",method=RequestMethod.GET)
	public String rmList(Model model, HttpServletRequest request){
		String select = request.getParameter("select");
		if(select==null){
			//获取请求链接，用于页面分页用
			String url=request.getServletPath()+"?"+request.getQueryString();
			if(url.indexOf("&currentPage")>0){
				url = url.substring(0, url.indexOf("&currentPage"));
			}
			RoleMenuKey rm = new RoleMenuKey();
			rm.setRoleid(request.getParameter("roleid"));
			rm.setMenuid(request.getParameter("menuid"));
			PageList<RoleMenuKey> pageList = null;
			pageList = ralationService.selectRmByCondition(rm,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("co",rm);
			model.addAttribute("list", pageList);
			model.addAttribute("url", url);
		}else{
			model.addAttribute("select",select);
		}
		return "ralation/rm/list";
	}
	
	@RequestMapping(value="/rr/list",method=RequestMethod.GET)
	public String rrList(Model model, HttpServletRequest request){
		String select = request.getParameter("select");
		if(select==null){
		//获取请求链接，用于页面分页用
		String url=request.getServletPath()+"?"+request.getQueryString();
		if(url.indexOf("&currentPage")>0){
			url = url.substring(0, url.indexOf("&currentPage"));
		}
		
		RoleResourceKey rr = new RoleResourceKey();
		rr.setRoleid(request.getParameter("roleid"));
		rr.setResourceid(request.getParameter("resourceid"));
		rr.setOperaid(request.getParameter("operaid"));
		rr.setSysid(request.getParameter("sysid"));
		PageList<RoleResourceKey> pageList = null;
			pageList = ralationService.selectRrByCondition(rr,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("co",rr);
			model.addAttribute("list", pageList);
			model.addAttribute("url", url);
		}else{
			model.addAttribute("select",select);
		}
		return "ralation/rr/list";
	}

	@RequestMapping(value="/ru/del",method=RequestMethod.POST)
	@ResponseBody
	public int ruDel(String userid,String roleid){
		RoleUsersKey roleUser = new RoleUsersKey();
		roleUser.setUserid(userid);
		roleUser.setRoleid(roleid);
		int num = ralationService.deleteRuByPrimaryKey(roleUser);
		return num;
	}
	@RequestMapping(value="/rm/del",method=RequestMethod.POST)
	@ResponseBody
	public int rmDel(String roleid,String menuid){
		RoleMenuKey roleMenu = new RoleMenuKey();
		roleMenu.setRoleid(roleid);
		roleMenu.setMenuid(menuid);
		int num = ralationService.deleteRmByPrimaryKey(roleMenu);
		return num;
	}
	@RequestMapping(value="/rr/del",method=RequestMethod.POST)
	@ResponseBody
	public int rrDel(String roreid){
		int num = ralationService.deleteRrByPrimaryKey(roreid);
		return num;
	}
	
	@RequestMapping(value="/ru/add",method=RequestMethod.GET)
	public String ruAdd(Model model,HttpServletRequest request){
		List<User> users = userService.selectUserList();
		List<Role> roles = roleService.findAllRoles();
		model.addAttribute("users", users);
		model.addAttribute("roles", roles);
		model.addAttribute(new RoleUsersKey());
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		return "ralation/ru/add";
	}
	@RequestMapping(value="/ru/add",method=RequestMethod.POST)
	public String ruAdd(@Valid RoleUsersKey ru,BindingResult result,Model model){
		if(result.hasErrors()){
			List<User> users = userService.selectUserList();
			List<Role> roles = roleService.findAllRoles();
			model.addAttribute("users", users);
			model.addAttribute("roles", roles);
			return "ralation/ru/add";
		}
		ru.setOperator(Envparam.getLoginInfo().getUsername());
		int num;
		try{  										//防止插入已存在的一条数据
			num = ralationService.insertRu(ru);
		}catch(Exception e){
			model.addAttribute("error",1);
			List<User> users = userService.selectUserList();
			List<Role> roles = roleService.findAllRoles();
			model.addAttribute("users", users);
			model.addAttribute("roles", roles);
			return "ralation/ru/add";
		}
		return "redirect:/admin/ralation/ru/add?addSuccess=true";
	}

	@RequestMapping(value="/rm/add",method=RequestMethod.GET)
	public String rmAdd(Model model,HttpServletRequest request){
		List<Role> roles = roleService.findAllRoles();
		List<Menu> menus = menuService.findAllMenus();
		model.addAttribute("roles", roles);
		model.addAttribute("menus", menus);
		model.addAttribute(new RoleMenuKey());
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		return "ralation/rm/add";
	}
	@RequestMapping(value="/rm/add",method=RequestMethod.POST)
	public String rmAdd(@Valid RoleMenuKey rm,BindingResult result,Model model){
		if(result.hasErrors()){
			List<Role> roles = roleService.findAllRoles();
			List<Menu> menus = menuService.findAllMenus();
			model.addAttribute("roles", roles);
			model.addAttribute("menus", menus);
			return "ralation/rm/add";
		}
		rm.setOperator(Envparam.getLoginInfo().getUsername());
		int num;
		try{  		      //防止插入已存在的一条数据
			num = ralationService.insertRm(rm);
		}catch(Exception e){
			model.addAttribute("error",1);
			List<Role> roles = roleService.findAllRoles();
			List<Menu> menus = menuService.findAllMenus();
			model.addAttribute("roles", roles);
			model.addAttribute("menus", menus);
			return "ralation/rm/add";
		}
		return "redirect:/admin/ralation/rm/add?addSuccess=true";
	}
	
	@RequestMapping(value="/rr/add",method=RequestMethod.GET)
	public String rrAdd(Model model,HttpServletRequest request){
		List<Role> roles = roleService.findAllRoles();
		List<Resource> resources = resourceService.selectAll();
		List<Operationcode> operationcodes = operationcodeService.selectAll();
		model.addAttribute("roles", roles);
		model.addAttribute("resources", resources);
		model.addAttribute("operationcodes", operationcodes);
		model.addAttribute(new RoleResourceKey());
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		return "ralation/rr/add";
	}
	@RequestMapping(value="/rr/add",method=RequestMethod.POST)
	public String rrAdd(@Valid RoleResourceKey rr,BindingResult result,Model model){
		if(result.hasErrors()){
			List<Role> roles = roleService.findAllRoles();
			List<Resource> resources = resourceService.selectAll();
			List<Operationcode> operationcodes = operationcodeService.selectAll();
			model.addAttribute("roles", roles);
			model.addAttribute("resources", resources);
			model.addAttribute("operationcodes", operationcodes);
			model.addAttribute(new RoleResourceKey());
			return "ralation/rr/add";
		}
		rr.setOperator(Envparam.getLoginInfo().getUsername());
		int num = ralationService.insertRr(rr);
		return "redirect:/admin/ralation/rr/add?addSuccess=true";
	}
	
	@RequestMapping(value="/ru/{roleid}/{userid}/update",method=RequestMethod.GET)
	public String ruUpdate(Model model,@PathVariable String roleid,@PathVariable String userid){
		RoleUsersKey ru = ralationService.selectRu(roleid, userid);
		List<User> users = userService.selectUserList();
		List<Role> roles = roleService.findAllRoles();
		model.addAttribute("users", users);
		model.addAttribute("roles", roles);
		model.addAttribute("roleUsersKey", ru);
		return "ralation/ru/add";
	}
	@RequestMapping(value="/ru/{roleid}/{userid}/update",method=RequestMethod.POST)
	public String ruUpdate(@Valid RoleUsersKey ru,BindingResult result,
			@PathVariable String roleid,@PathVariable String userid,Model model){
		if(result.hasErrors()){
			List<User> users = userService.selectUserList();
			List<Role> roles = roleService.findAllRoles();
			model.addAttribute("users", users);
			model.addAttribute("roles", roles);
			return "ralation/ru/add";
		}
		String userName = Envparam.getLoginInfo().getUsername();
		ru.setOperator(userName);
		ralationService.updateRu(roleid, userid, ru);
		model.addAttribute("updateSuccess", true);
		return "ralation/ru/add";
		
	}
	
	@RequestMapping(value="/rm/{roleid}/{menuid}/update",method=RequestMethod.GET)
	public String rmUpdate(Model model,@PathVariable String roleid,@PathVariable String menuid){
		RoleMenuKey rm = ralationService.selectRm(roleid, menuid);
		List<Role> roles = roleService.findAllRoles();
		List<Menu> menus = menuService.findAllMenus();
		model.addAttribute("roles", roles);
		model.addAttribute("menus", menus);
		model.addAttribute("roleMenuKey", rm);
		return "ralation/rm/add";
	}
	@RequestMapping(value="/rm/{roleid}/{menuid}/update",method=RequestMethod.POST)
	public String rmUpdate(@Valid RoleMenuKey rm,BindingResult result,
			@PathVariable String roleid,@PathVariable String menuid,Model model){
		if(result.hasErrors()){
			List<Role> roles = roleService.findAllRoles();
			List<Menu> menus = menuService.findAllMenus();
			model.addAttribute("roles", roles);
			model.addAttribute("menus", menus);
			return "ralation/rm/add";
		}
		String userName = Envparam.getLoginInfo().getUsername();
		rm.setOperator(userName);
		ralationService.updateRm(roleid, menuid, rm);
		model.addAttribute("updateSuccess", true);
		return "ralation/rm/add";
		
	}
	@RequestMapping(value="/rr/{roreid}/update",method=RequestMethod.GET)
	public String rrUpdate(Model model,@PathVariable String roreid){
		RoleResourceKey rr = ralationService.selectRr(roreid);
		List<Role> roles = roleService.findAllRoles();
		List<Resource> resources = resourceService.selectAll();
		List<Operationcode> operationcodes = operationcodeService.selectAll();
		model.addAttribute("roles", roles);
		model.addAttribute("resources", resources);
		model.addAttribute("operationcodes", operationcodes);
		model.addAttribute("roleResourceKey", rr);
		return "ralation/rr/add";
	}
	@RequestMapping(value="/rr/{roreid}/update",method=RequestMethod.POST)
	public String rrUpdate(@Valid RoleResourceKey rr,BindingResult result,Model model){
		if(result.hasErrors()){
			List<Role> roles = roleService.findAllRoles();
			List<Resource> resources = resourceService.selectAll();
			List<Operationcode> operationcodes = operationcodeService.selectAll();
			model.addAttribute("roles", roles);
			model.addAttribute("resources", resources);
			model.addAttribute("operationcodes", operationcodes);
			return "ralation/rr/add";
		}
		String userName = Envparam.getLoginInfo().getUsername();
		rr.setOperator(userName);
		ralationService.updateRr(rr);
		model.addAttribute("updateSuccess", true);
		return "ralation/rr/add";
		
	}
}
