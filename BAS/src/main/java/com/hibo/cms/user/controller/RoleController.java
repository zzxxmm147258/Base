package com.hibo.cms.user.controller;

import java.util.ArrayList;
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
import com.hibo.cms.user.model.Role;
import com.hibo.cms.user.model.RoleMenuKey;
import com.hibo.cms.user.service.menu.IMenuService;
import com.hibo.cms.user.service.role.IRoleService;
import com.hibo.cms.util.Envparam;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月12日 下午4:14:06</p>
 * <p>类全名：com.hibo.cms.user.controller.RoleController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	
	/**
	 * 查询所有
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model, HttpServletRequest request){
		
		//获取请求链接，用于页面分页用
		String url=request.getServletPath()+"?"+request.getQueryString();
		if(url.indexOf("&currentPage")>0){
			url = url.substring(0, url.indexOf("&currentPage"));
		}
		
		Role role = new Role();
		role.setRoleid(request.getParameter("roleid"));
		role.setRotype(request.getParameter("rotype"));
		role.setRolename(request.getParameter("rolename"));
		String select = request.getParameter("select");
		PageList<Role> pageList = null;
		if(select==null){
			pageList = roleService.selectByCondition(role,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
		}else{
			model.addAttribute("select",select);
		}
		model.addAttribute("co",role);
		model.addAttribute("list", pageList);
		model.addAttribute("url", url);
		return "/role/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest request){
		model.addAttribute(new Role());
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		return "/role/add";
		
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid Role role,BindingResult result,Model model){
		if(null != roleService.selectByRoleId(role.getRoleid())){
			model.addAttribute("idError", "此ID已存在");
			return "user/add";
		}
		if(result.hasErrors()){
			return "role/add";
		}
		String userName = Envparam.getUser().getUsername();
		role.setOperator(userName);
		int num = roleService.insert(role);
		return "redirect:/admin/role/add?addSuccess=true";
	}
	
	@RequestMapping(value="/{roleid}/update",method=RequestMethod.GET)
	public String update(Model model,@PathVariable String roleid){
		Role role = roleService.selectByRoleId(roleid);
		model.addAttribute(role);
		return "/role/add";
	}
	@RequestMapping(value="/{roleid}/update",method=RequestMethod.POST)
	public String update(@Valid Role role,BindingResult result,Model model,@PathVariable String roleid){
		if(result.hasErrors()){
			return "/role/add";
		}
		String userName = Envparam.getUser().getUsername();
		role.setOperator(userName);
		roleService.update(roleid, role);
		model.addAttribute("updateSuccess", true);
		return "/role/add";
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int del(String roleid){
		int num = roleService.deleteByRoleId(roleid);
		return num;
	}

/*	@RequestMapping(value="/{roleid}/power",method=RequestMethod.GET)
	public String power(@PathVariable String roleid,Model model){
		List<Menu> menus = menuService.findAllMenus();
		List<String> menuIds = menuService.selectMenuIdsByRoleId(roleid);
		model.addAttribute("menus", menus);
		model.addAttribute("menuIds",menuIds);
		return "role/power";
	}
	@RequestMapping(value="/{roleid}/power",method=RequestMethod.POST)
	public String power(@PathVariable String roleid,String menuid[]){
		int delNum = menuService.deleteMenuIdByRoleId(roleid);
		if(null!=menuid){
			List<RoleMenuKey> roleMenus = new ArrayList<RoleMenuKey>();
			RoleMenuKey roleMenu = null;
			for(String mid:menuid){
				roleMenu = new RoleMenuKey();
				roleMenu.setRoleid(roleid);
				roleMenu.setMenuid(mid);
				roleMenus.add(roleMenu);
			}
			int num = menuService.insertList(roleMenus);	
		}
		return "redirect:/admin/role/list";
		
	}*/
	
	/*	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		List<Role> roles = roleService.findAllRoles();
		model.addAttribute("roles",roles);
		return "/role/list";
	}*/
	
	/*	@RequestMapping(value="/{roleid}/update",method=RequestMethod.GET)
	public String update(@PathVariable String roleid,Model model){
		Role role = roleService.selectByRoleId(roleid);
		model.addAttribute(role);
		return "/role/add";
		
	}
	@RequestMapping(value="/{roleid}/update",method=RequestMethod.POST)
	public String update(Role role){
		int num = roleService.updateByRoleId(role);
		return "redirect:/admin/role/list";
	}*/
/*	@RequestMapping(value="/add.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String add(Role role){
		String userName = Envparam.getUser().getUsername();
		role.setOperator(userName);
		int num = roleService.insert(role);
		return role.getRoleid();
	}*/
	
/*	@RequestMapping(value="update.ajax",method=RequestMethod.POST)
	@ResponseBody
	public int update(String old_roleid,Role role){
		String userName = Envparam.getUser().getUsername();
		role.setOperator(userName);
		int num = roleService.update(old_roleid, role);
		return num;
	}*/

}
