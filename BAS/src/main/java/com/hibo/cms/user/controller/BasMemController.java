package com.hibo.cms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.cms.sys.shiro.util.PasswordHelper;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.user.model.User;
import com.hibo.cms.user.service.user.IUserService;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年6月28日 上午11:06:11</p>
 * <p>类全名：com.hibo.mem.member.controller.BasMemController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/bas")
public class BasMemController {

	@Autowired
	private IUserService userService;
	
	/**
	 * <p>功能：修改密码<p>
	 * <p>创建日期：2016年6月28日 上午11:40:30<p>
	 * <p>作者：曾小明<p>
	 * @param old_password
	 * @param username
	 * @param new_password1
	 * @param new_password2
	 * @return
	 */
	@RequestMapping(value="/password.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String chgUsername(String old_password,String new_password1,String new_password2){
	    Message message=new Message(true);
		User user=userService.selectByUserId(Envparam.getUserId());
	     if(user!=null){
	    	 if(new_password1.equals(new_password2)){
	    		 String old_p = PasswordHelper.getPasswordByPS(old_password, user.getUsername(), user.getSalt());
	    		 if(old_p.equals(user.getPassword())){
	    			 String  new_p= PasswordHelper.getPasswordByPS(new_password1, user.getUsername(), user.getSalt());
	    			 user.setPassword(new_p);
	    			 int now=userService.changePassword(new_password1, user.getUserid(), user.getUsername());
	    			 if(now>0){
	    				    message.setSuccess(true);
		   		    	    message.setMessage("密码修改成功");
	    			 }else{
	    				   message.setSuccess(false);
		   		    	    message.setMessage("密码修改失败");
	    			 }
	    			}else{
	    				message.setSuccess(false);
	   		    	    message.setMessage("旧密码错误");
	    			}
	    	 }else{
	    		 message.setSuccess(false);
		    	 message.setMessage("新密码不一致");
	    	 }
	     }else{
	    	 message.setSuccess(false);
	    	 message.setMessage("尚未登录");
	     }
		if(message.isSuccess()){			//修改Envparam中登录信息
			Envparam.getLoginInfo().setUsername(user.getUsername());
			Envparam.getUser().setUsername(user.getUsername());
		};
		return JsonUtil.toJsonString(message);
	}
}
