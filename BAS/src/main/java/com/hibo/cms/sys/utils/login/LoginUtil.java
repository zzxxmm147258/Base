package com.hibo.cms.sys.utils.login;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import com.hibo.bas.classFile.ClassFileUtil;
import com.hibo.bas.exception.shiro.RunAuthenticationException;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;
/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月13日 上午10:11:20</p>
 * <p>类全名：com.hibo.cms.sys.utils.login.LoginUtil</p>
 * 作者：lei zhou
 * 初审：
 * 复审：
 */
public class LoginUtil {
	private static final Logger log = LoggerFactory.getLogger(LoginUtil.class);
	public static final String ADMSTR = "sys_login_admin_All_prems";
	private static Map<String,String[]> LoginMapConfg=new HashMap<String,String[]>();
	private static List<String> loginUriList = new ArrayList<String>();
	private static boolean ISCAPTCHA = true;
	public static String ROOTURI=null;
	static{
		if(log.isInfoEnabled()){
			log.info("=="+ClassFileUtil.getContextPath()+"==初始化登陆配置。。。。。。");
		}
		String LOGINCONFG = DataConfig.getConfig("LOGINCONFG");
		if(null!=LOGINCONFG){
			LOGINCONFG = LOGINCONFG.replaceAll("%%", "**");
			String[] LoginConfgs = LOGINCONFG.split(";");
			for (String LoginConfgStr : LoginConfgs) {
				String[] LoginConfgStrs = LoginConfgStr.split(",");
				if(LoginConfgStrs.length<3){
					if(log.isInfoEnabled()){
						log.error("LOGINCONFG--登陆配置格式有误！");
					}
					throw new RuntimeException("LOGINCONFG--登陆配置格式有误！");
				}else{
					loginUriList.add(LoginConfgStrs[1]);
					LoginMapConfg.put(LoginConfgStrs[1], LoginConfgStrs);
				}
			}
		}
		ROOTURI = DataConfig.getConfig("ROOTURI");
		ISCAPTCHA = !"false".equalsIgnoreCase(DataConfig.getConfig("ISCAPTCHA"));
	}
	
	public static boolean isLoginEmpty(){
		return loginUriList.size()<=0;
	}
	
	public static String getLoginFilterUrl(String loginUri){
		String[] ll = LoginMapConfg.get(loginUri);
		return null==ll?null:ll[0];
	}
	
	public static String getLoginControllerUrl(String loginUri){
		String[] ll = LoginMapConfg.get(loginUri);
		return null==ll?null:ll[1];
	}
	
	public static String getLoginSuccessUrl(String loginUri){
		String[] ll = LoginMapConfg.get(loginUri);
		return null==ll?null:ll[2];
	}
	
	public static int loginUriSize(){
		return LoginMapConfg.size();
	}
	
	public static List<String> loginUriSet(){
		return loginUriList;
	}
	
	public static Map<String,String[]> loginUriMap(){
		return LoginMapConfg;
	}
	
	public static boolean isCaptcha() {
		return ISCAPTCHA;
	}
	public static ModelMap checkLogin(HttpServletRequest request, ModelMap model){
		return checkLogin(request, model, false);
	}
	
	public static ModelMap checkLogin(HttpServletRequest request, ModelMap model, boolean bLogin){
        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
        String obj = (String) request.getAttribute("shiroLoginFailure_message");
        String error =  getLoginError(exceptionClassName, obj, bLogin);
        LoginInfo loginInfo = Envparam.getLoginInfo();
        model.addAttribute("loginInfo",loginInfo);
		model.addAttribute("error", error);
		return model;
	}
	
	public static String checkLoginJson(HttpServletRequest request){
		return checkLoginJson(request,false);
	}
	
	public static String checkLoginJson(HttpServletRequest request,boolean bLogin){
        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
        String obj = (String) request.getAttribute("shiroLoginFailure_message");
        Map<String,Object> map = new HashMap<String,Object>();
        String error = getLoginError(exceptionClassName, obj, bLogin);
        if(null!=error){
        	map.put("error", error);
        }
        map.put("success", false);
		return JsonUtil.toJsonString(map);
	}
	
	public static String getLoginError(String exceptionClassName,String obj,boolean bLogin){
		String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名或密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名或密码错误";
        } else if(LockedAccountException.class.getName().equals(exceptionClassName)) {
            error = "账号已被禁用";
        } else if(RunAuthenticationException.class.getName().equals(exceptionClassName)) {
        	if(null!=obj&&!obj.isEmpty()){
        		error = obj;
        	}else{
        		error = "用户名或密码错误!";
        	}
        	System.err.println("login1="+exceptionClassName+obj);
        } else if(exceptionClassName != null) {
        	if(null!=obj&&!obj.isEmpty()){
        		error = obj;
        	}else{
        		error="用户名或密码错误!!";
        	}
        	System.err.println("login2="+exceptionClassName+obj);
        } else if (bLogin){
        	error="登录超时";
        }
        if(!StrUtil.isnull(error)){
        	LoginInfo loginInfo = (LoginInfo) Envparam.getVFormSession("loginInfo");
        	if(null!=loginInfo){
        		loginInfo.setUser(null);
        		Envparam.setVFormSession("loginInfo", loginInfo);
        	}
        }
		return error;
	}
	
	public static boolean isMainPath(String uri){
		return (!StrUtil.isnull(uri))&&(uri.startsWith("/admin") || uri.startsWith("/main"));
	}
	
}
