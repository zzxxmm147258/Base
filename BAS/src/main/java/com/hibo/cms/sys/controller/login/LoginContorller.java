package com.hibo.cms.sys.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.basenum.MessageCodeEnum;
import com.hibo.bas.constant.Message;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.sys.utils.login.LoginUtil;
import com.hibo.cms.token.utils.TokenLogin;
import com.hibo.cms.util.Envparam;

/**
 * <p>标题：登录控制</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-15 下午2:05:08</p>
 * <p>类全名：com.hibo.cms.sys.controller.login.LoginContorller</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Controller
public class LoginContorller {
	
	@RequestMapping(value = "/newLogin.ajax")
	@ResponseBody
	public String newLogin() {
		Message message=new Message();
		Map<String,Map<String, String>> map=new HashMap<String,Map<String, String>>();
		Map<String, String> sysSelectMap=DataConfig.getSysIdMap();
		Map<String, String> dbSelectMap=DataConfig.getWorkSpaceMap(0);
		map.put("sysSelectMap", sysSelectMap);
		map.put("dbSelectMap", dbSelectMap);
		message.setDatas(map);
		return JsonUtil.toJsonString(message);
	}
	
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request) {
		String type = request.getParameter("dataType");
		if ("json".equalsIgnoreCase(type)) {
			return "forward:loginJson";
		}
		return "redirect:welcome/login.html";
	}

	@RequestMapping(value = "/loginJson")
	@ResponseBody
	public String loginJson(HttpServletRequest request) {
		return LoginUtil.checkLoginJson(request);
	}

	@RequestMapping(value = "/nologin")
	@ResponseBody
	public String nologin(HttpServletRequest request) {
		Message message = new Message(false, "noLogin", "未登录或登录超时!");
		message.setNoLogin(true);
		return JsonUtil.toJsonString(message);
	}

	@RequestMapping(value = "/tokenLogin",method=RequestMethod.POST)
	@ResponseBody
	public String tokenlogin(String username, String password, String utype,String sysid,String dbsource, HttpServletRequest request,HttpServletResponse response) {
		Message message = new Message();
		message.setNoLogin(true);
		try {
			if (StrUtil.isnull(username) || StrUtil.isnull(password)) {
				message.setCode(MessageCodeEnum.F_U_P_NULL);
				message.setMessage("用户名或密码不能为空!");
			}
			utype = StrUtil.isnull(utype) ? "10" : utype;
			boolean isLogin = Envparam.login(username, password, utype, dbsource, sysid); 
			if (isLogin) {
				String tokenKey = TokenLogin.LoginDo(request, response, Envparam.getLoginInfo());
				message.setSuccess(true);
				message.setCode(MessageCodeEnum.S_LOGIN);
				message.setDatas(tokenKey);
				message.setNoLogin(!isLogin);
				message.setSuccess(true);
				message.setMessage("登录成功！");
			} else {
				throw new RuntimeException("登录失败!");
			}
		} catch (Exception e) {
			String msg = LoginUtil.getLoginError(e.getClass().getName(), e.getMessage(), false);
			message.setCode(MessageCodeEnum.F_LOGIN);
			message.setSuccess(false);
			message.setMessage(msg);
			message.setSuccess(false);
			message.setMessage("登录失败！");
		}
		return JsonUtil.toJsonString(message);
	}
	
	@RequestMapping(value = "/loginOut")
	@ResponseBody
	public String loginOut() {
		Envparam.logout();
		Message message = new Message(true,"退出");
		message.setNoLogin(true);
		return JsonUtil.toJsonString(message);
	}

}
