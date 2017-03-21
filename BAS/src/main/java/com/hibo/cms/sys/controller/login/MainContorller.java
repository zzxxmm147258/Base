package com.hibo.cms.sys.controller.login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.constant.Message;
import com.hibo.bas.util.StrUtil;
import com.hibo.bas.xml.XmlAnalysis;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.shiro.util.SysFormAuthenticationFilter;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.sys.utils.login.LoginUtil;
import com.hibo.cms.sys.utils.menu.MenuUtil;
import com.hibo.cms.user.model.Menu;
import com.hibo.cms.user.service.menu.IMenuService;
import com.hibo.cms.util.Envparam;
import com.hibo.cms.util.ImageCode;
import com.hibo.cms.visit.service.IVisitBasService;

/**
 * <p> 标题：</p>
 * <p> 功能： </p>
 * <p> 版权： Copyright © 2015 HIBO </p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月24日 上午11:34:09</p>
 * <p>类全名：com.hibo.cms.sys.controller.login.MainContorller</p>
 * 作者：Victor 
 * 初审： 
 * 复审：
 */
@Controller
public class MainContorller {
	@Autowired
	public IMenuService menuService;
	@Resource
	private IVisitBasService visitBasService;
	private static final Logger log = LoggerFactory.getLogger(SysFormAuthenticationFilter.class);
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		String re = "redirect:/main/cms/home.html";
		if (!Envparam.isLogin()) {
			re = "redirect:/welcome/login.html";
		}
		if (log.isInfoEnabled()) {
			log.info("登录返回地址/main");
		}
		return re;
	}
	
	@RequestMapping(value = "/mainsuccess")
	@ResponseBody
	public String mainsuccess() {
		if (log.isInfoEnabled()) {
			log.info("登录返回地址/mainsuccess");
		}
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", true);
		return JsonUtil.toJsonString(map);
	}
	
	@RequestMapping(value = "/main/menu")
	@ResponseBody
	public String mainMenu() {
		LoginInfo loginInfo = Envparam.getLoginInfo();
		Message message = new Message(true);
		if (null == loginInfo) {
			message.setSuccess(false);
			message.setMessage("系统信息获取失败!");
		} else {
			List<Menu> menuList = null;
			// 初始化菜单
			if (loginInfo.isSuperAdmin()) {
				// TODO 初始化超级管理员信息
				List<Map<String, Object>> menus = XmlAnalysis.XmlIsTOMapList();
				message.setDatas(menus);
				
			} else {
				String userid = loginInfo.getUser().getUserid();
				String sysid = loginInfo.getSysid();
				menuList = menuService.selectMenusByUserId(userid, sysid);// 获取到此角色有权限访问的所有菜单
				menuList = MenuUtil.getMenuList(menuList, 2); // 组装
				message.setDatas(menuList);
			}
		}
		return JsonUtil.toJsonString(message);
	}
	@RequestMapping(value = "/admin")
	public String admin() {
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/")
	public String index() {
		String rooturi = LoginUtil.ROOTURI;
		if(null==rooturi){
			rooturi = "redirect:login";
		}else if(rooturi.endsWith(".html")){
			rooturi = "redirect:"+rooturi;
		}else{
			rooturi ="forward:"+rooturi;
		}
        return rooturi;
	}
	
	
	@RequestMapping(value="/main/home",method=RequestMethod.GET)
	public String home(){
		return "admin/home";
	}
	/**
	 * @功能:生成二维码
	 * @作者:周雷
	 * @时间:2015年12月3日 下午5:52:37
	 * @return
	 */
	@RequestMapping(value="/main/common/createD2code",method=RequestMethod.GET)
	public String createD2code1() {
		return "d2code/d2code";
	}
	@RequestMapping(value="/main/common/createD2code",method=RequestMethod.POST)
	public String createD2code(String contents,String size,@RequestParam("d2code")MultipartFile d2code,HttpServletResponse response,Model model) {
		int sizes = 400;
		try {
			sizes = StrUtil.obj2int(size, 400);
			sizes = Math.max(sizes, 50);
			sizes = Math.min(sizes, 1000);
			byte[] imagBytes = null;
			if(!d2code.isEmpty()){
				imagBytes = new ImageCode(sizes, contents, d2code.getInputStream()).d2CodeTobytes();
			}else{
				imagBytes = new ImageCode(sizes, contents).d2CodeTobytes();
			}
			Envparam.setVFormSession("imagBytes", imagBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("size", sizes);
		String imageName = "HIBO" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			imageName = imageName + random.nextInt(10);
		}
		model.addAttribute("imageName", imageName);
		model.addAttribute("contents", contents);
		return "d2code/d2code";
	}
	
	@RequestMapping(value="/main/common/D2imag/{imageName}.png",method=RequestMethod.GET)
	public void D2imag(HttpServletResponse response) throws IOException {
		byte[] imagBytes = (byte[]) Envparam.removeVFormSession("imagBytes");
		if(null!=imagBytes){
			response.getOutputStream().write(imagBytes);
		}
	}
	
	@RequestMapping(value="/main/bas/sql",method=RequestMethod.POST)
	@ResponseBody
	public String sqlSelect(String sql){
		Message message = new Message(true);
		try{
			List<LinkedHashMap<String, Object>> list=visitBasService.selectDataList(sql);
			//List<Object[]> list=visitBasService.selectSQL(sql);
			/*if(list!=null && list.size()>0){
				Map<String, Object> key=list.get(0);
				
			    
			}*/
			message.setDatas(list);
		}catch(Exception e){
			message.setSuccess(false);
			message.setMessage(e.getMessage());
		}
		return JsonUtil.toJsonString(message);
	}

}
