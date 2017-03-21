package com.hibo.cms.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.eclipse.jetty.util.ajax.JSON;
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
import com.hibo.cms.shop.model.ShopBas;
import com.hibo.cms.shop.service.IShopBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.user.model.Password;
import com.hibo.cms.user.model.Role;
import com.hibo.cms.user.model.User;
import com.hibo.cms.user.service.role.IRoleService;
import com.hibo.cms.user.service.user.IUserService;
import com.hibo.cms.util.Envparam;

@Controller
@RequestMapping("/admin/main")
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IShopBasService shopBasService;
	
	/**
	 * 查询所有
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model, HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<User> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = userService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		List<ShopBas> shops = shopBasService.selectAll();
		model.addAttribute("shops",shops);
		String[] utypes={"10","20"};
		Map<String, String> lockeds = new HashMap<String, String>();
		lockeds.put("0", "未锁定");
		lockeds.put("1", "已锁定");
		model.addAttribute("utypes",utypes);
		model.addAttribute("lockeds", lockeds);
		return "user/list";
	}
	
	/**
	 * 编辑“用户-角色”关系时选择用户时模态框的模糊查询
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String list(User user){
		List<User> list = userService.selectFuzzy(user);
		return JsonUtil.toJsonString(list);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest request){
		User user = new User();
		List<ShopBas> shop=shopBasService.selectAll();
		List<Role> roles = roleService.findAllRoles();
		model.addAttribute("shops",shop);
		model.addAttribute("user",user);
		model.addAttribute("roles", roles);
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		return "user/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Valid User user,BindingResult result,HttpServletRequest request){
		String roleStr = request.getParameter("roleStr");
		if(result.hasErrors() || "".equals(roleStr)){
			List<ShopBas> shop=shopBasService.selectAll();	
			List<Role> roles = roleService.findAllRoles();
			model.addAttribute("shops",shop);
			model.addAttribute("roles", roles);
			model.addAttribute("noRole", true);
			return "user/add";
		}
		user.setOperator(Envparam.getLoginInfo().getUsername());
		userService.insert(user,roleStr);
		return "redirect:/admin/main/add?addSuccess=true";
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String userid){
		int row = userService.deleteByUserId(userid);
		String json = JSON.toString(row);
		return json;
	}
	
	@RequestMapping(value="/{userid}/update",method=RequestMethod.GET)
	public String update(@PathVariable String userid,Model model){
		User user = userService.selectByUserId(userid);
		List<ShopBas> shop=shopBasService.selectAll();	
		model.addAttribute("shops",shop);
		user.setPassword(null);
		model.addAttribute(user);
		return "user/add";
	}
	@RequestMapping(value="/{userid}/update",method=RequestMethod.POST)
	public String update(@Valid User user,BindingResult result,Model model){
		user.setOperator(Envparam.getLoginInfo().getUsername());
		int num = userService.update(user);
		model.addAttribute("updateSuccess", true);
		return "user/add";
	}
	
	@RequestMapping(value="/chgPassWord",method=RequestMethod.GET)
	public String chgPassWord(Model model){
		if(null != Envparam.getUser()){
			model.addAttribute(new Password());
			return "user/password";
		}
		return "admin/login";
	}
	
	@RequestMapping(value="/chgPassWord",method=RequestMethod.POST)
	public String chgPassWord(@Valid Password password,BindingResult result,Model model){
		User user = Envparam.getUser();
		if(null == user){
			return "admin/login";
		}
		if(result.hasErrors()){
			return "user/password";
		}
		String chgStr = userService.chgPassWord(user, password);
		if(null != chgStr){
			model.addAttribute("error", chgStr);
			return "user/password";
		}
		model.addAttribute("success", "密码修改成功！");
		return "user/password";
	}
	
	/*	@RequestMapping(value="/{userid}/power",method=RequestMethod.GET)
	public String power(@PathVariable String userid,Model model){
		List<Role> roles = roleService.findAllRoles();
		List<String> roleIds = roleService.selectRoleIdByUserId(userid);
		model.addAttribute("roles", roles);
		model.addAttribute("roleIds",roleIds);
		return "user/power";
		
	}
	@RequestMapping(value="/{userid}/power",method=RequestMethod.POST)
	public String power(@PathVariable String userid,String roleid[]){
		int delNum = roleService.deleteRoleIdByUserId(userid);
		if(null!=roleid){
			String userName = Envparam.getUser().getUsername();
			List<RoleUsersKey> roleUsers = new ArrayList<RoleUsersKey>();
			RoleUsersKey roleUser = null;
			for(String rid:roleid){
				roleUser = new RoleUsersKey();
				roleUser.setUserid(userid);
				roleUser.setRoleid(rid);
				roleUser.setOperator(userName);
				roleUsers.add(roleUser);
			}
			int num = roleService.insertList(roleUsers);	
		}
		return "redirect:/admin/main/list";
		
	}*/
}
