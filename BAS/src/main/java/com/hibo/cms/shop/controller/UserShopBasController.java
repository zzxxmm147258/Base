package com.hibo.cms.shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.shop.model.ShopBas;
import com.hibo.cms.shop.model.UserShopBas;
import com.hibo.cms.shop.model.UserShopBasKey;
import com.hibo.cms.shop.service.IShopBasService;
import com.hibo.cms.shop.service.IUserShopBasService;
import com.hibo.cms.user.model.User;
import com.hibo.cms.user.service.user.IUserService;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：会员商户关联</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午3:10:16</p>
 * <p>类全名：com.hibo.cms.shop.controller.UserShopBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/usershop")
public class UserShopBasController {

	@Autowired
	private IShopBasService shopBasService;
	
	@Autowired
	private IUserShopBasService userShopBasService;
	
	@Autowired
	private IUserService userService;
	/**IUserService
	 * <p>功能：查找用户商户<p>
	 * <p>创建日期：2015年11月19日 下午4:46:28<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<UserShopBas> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = userShopBasService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		List<ShopBas> shopname=shopBasService.selectAll();
		model.addAttribute("shopname", shopname);
		
		List<User> username=userService.selectUserList();
		model.addAttribute("username", username);
		
		Map<String, String> availabes = new HashMap<String, String>();
		availabes.put("0", "未启用");
		availabes.put("1", "已启用");
		model.addAttribute("availabes", availabes);
		return "shop/usershop/list";
	}
	
	/**
	 * <p>功能：删除用户商户信息<p>
	 * <p>创建日期：2015年11月19日 下午4:51:30<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String id){
		UserShopBas key=new UserShopBas();
		String[] ids=null;
		if(id!=null&&id.length()>0){
			ids=id.split("-");
			if(ids.length==2){
				key.setUserid(ids[0].trim());
				key.setShopId(ids[1].trim());
			}
		}
		int row = userShopBasService.deleteByPrimaryKey(key);
		String json = JSON.toString(row);
		return json;
	}
	
	/**
	 * <p>功能：修改用户商户<p>
	 * <p>创建日期：2015年11月19日 下午5:34:31<p>
	 * <p>作者：曾小明<p>
	 * @param shopId
	 * @param userid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{userid}/{shopId}/update",method=RequestMethod.GET)
	public String update(@PathVariable(value="shopId") String shopId,@PathVariable(value="userid") String userid,Model model){
		UserShopBas key=new UserShopBas();
		key.setUserid(userid);
		key.setShopId(shopId);
		UserShopBas bean=userShopBasService.selectByPrimaryKey(key);
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		
		List<ShopBas> shopname=shopBasService.selectAll();
		model.addAttribute("shopname",shopname);
		
		List<User> username=new ArrayList<User>();
		User user=new User();
		user.setUserid(bean.getUserid());
		user.setUsername(bean.getUsername());
		username.add(user);
		model.addAttribute("username",username);
		return "shop/usershop/add";
	}
	@RequestMapping(value="/{userid}/{shopId}/update",method=RequestMethod.POST)
	public String update(@PathVariable(value="shopId") String shopId,@PathVariable(value="userid") String userid,@Valid UserShopBas bean,Model model){
		UserShopBasKey key=new UserShopBasKey();
		key.setUserid(userid);
		key.setShopId(shopId);
		bean.setOperator(Envparam.getUser().getUsername());
		ShopBas shopBas=shopBasService.selectByPrimaryKey(bean.getShopId());
		bean.setShopname(shopBas.getShopname());//获取商圈名称
		try {
			userShopBasService.update(key, bean);
		} catch (Exception e) {
		}
		model.addAttribute("updateSuccess", true);
		return "redirect:/admin/usershop/list";
	}
	
	/**
	 * <p>功能：添加用户商品<p>
	 * <p>创建日期：2015年11月19日 下午5:34:09<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		UserShopBas bean=new UserShopBas();
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		List<ShopBas> shopname=shopBasService.selectAll();//所有商户
		model.addAttribute("shopname",shopname);
		List<User> username=userService.selectUserList();//所有用户
		model.addAttribute("username",username);
		return "shop/usershop/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Valid UserShopBas bean){
		UserShopBas userShopBas=userShopBasService.selectByPrimaryKey(bean);
		if(userShopBas==null){
		bean.setOperator(Envparam.getUser().getUsername());
		ShopBas shopBas=shopBasService.selectByPrimaryKey(bean.getShopId());
		bean.setShopname(shopBas.getShopname());//获取商圈名称
		User user=userService.selectByUserId(bean.getUserid());
		bean.setUsername(user.getUsername());
		userShopBasService.insertSelective(bean);
		}
		return "redirect:/admin/usershop/list";
	}
}
