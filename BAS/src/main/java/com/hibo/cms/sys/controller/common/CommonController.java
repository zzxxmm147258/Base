package com.hibo.cms.sys.controller.common;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.language.model.UserLanguageBas;
import com.hibo.cms.language.service.IUserLanguageBasService;
import com.hibo.cms.sys.utils.captcha.CaptchaSessionUtil;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.token.utils.TokenLogin;
import com.hibo.cms.user.model.User;
import com.hibo.cms.util.Envparam;
import com.hibo.mem.member.model.BaseMem;
import com.hibo.mem.member.service.IMemberMemBasService;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月28日 上午10:46:07</p>
 * <p>类全名：com.hibo.cms.sys.controller.common.BasCommonController</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Controller
@RequestMapping(value="/common")
public class CommonController {
	
	@Resource
	private IMemberMemBasService memberMemBasService;
	
	@Resource
	private IUserLanguageBasService userLanguageBasService;
	
   
	@RequestMapping(value="/bas/islogin",method=RequestMethod.POST)
	@ResponseBody
	public String isLogin(String token,String utype){
		Message message = TokenLogin.tokenEffective(token, utype);
		if(!message.isNoLogin()){
			BaseMem baseMem = new BaseMem();
			if(Envparam.getLoginInfo().isSuperAdmin()){
				baseMem.setUsername("superman");
				baseMem.setTruename("超级管理员");
				message.setUrl(DataConfig.getSysHome());
			}else if("20".equals(Envparam.getLoginInfo().getType())){
				baseMem.setUserid(Envparam.getUserId());
				baseMem.setUtype(Envparam.getLoginInfo().getType());
				baseMem.setUsername(Envparam.getUserName());
				User user = Envparam.getUser();
				baseMem.setTruename(user.getTruename());
				baseMem.setHeadpicture(user.getHeadpicture());
				baseMem.setNickname(user.getNickname());
				baseMem.setSex(null!=user.getSex()?user.getSex().toString():null);
				baseMem.setSysId(Envparam.getSysId());
				message.setUrl(DataConfig.getSysHome());
			}else{
				baseMem = memberMemBasService.selectBaseMem(Envparam.getUserId());
			}
			message.setDatas(baseMem);
		}else{
			message.setNoLogin(true);
		}
		if(null!=Envparam.getVFormSession("loginToUri")){
			message.setUrl(Envparam.removeVFormSession("loginToUri").toString());
		}
		message.setTitle(DataConfig.getSysTitle());
		return JsonUtil.toJsonString(message);
	}
	@RequestMapping(value="/bas/setlanguage",method=RequestMethod.POST)
	@ResponseBody
	public String setlanguage(String language) {
		Message message = new Message(true);
		if (StrUtil.isnull2(language)) {
			language = "zh";
		}
		try {
			int now = 0;
			if (Envparam.isLogin()) {
				String userid = Envparam.getUserId();
				UserLanguageBas lang = userLanguageBasService.selectByPrimaryKey(userid);
				if (lang != null) {
					lang.setUserid(language);
					now = userLanguageBasService.updateByPrimaryKey(lang);
				} else {
					UserLanguageBas bean = new UserLanguageBas();
					bean.setUserid(userid);
					bean.setLanguage(language);
					now = userLanguageBasService.insertSelective(bean);
				}
				if (now > 0) {
					message.setSuccess(true);
					message.setDatas(language);
					message.setMessage("当前语言设置为" + language);
				} else {
					message.setSuccess(false);
					message.setMessage("当前语言设置失败");
				}
			}
			Envparam.setVFormSession("language", language);
			message.setMessage("语言已设置到session");
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("当前语言设置失败");
		}

		return JsonUtil.toJsonString(message);
	}
}
