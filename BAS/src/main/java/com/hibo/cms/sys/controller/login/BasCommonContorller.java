package com.hibo.cms.sys.controller.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.sys.utils.json.JsonUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月27日 上午9:38:00</p>
 * <p>类全名：com.hibo.cms.sys.controller.login.BasCommonContorller</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Controller
@RequestMapping(value="/common")
public class BasCommonContorller {
	
	@RequestMapping(value="/test/json",method=RequestMethod.POST)
	@ResponseBody
	public String test_json(String phone,String template,int minitue) {
		Map<String, String> m = new HashMap<String,String>();
		m.put("name", "JAVA工程师:周雷");
		String ss = JsonUtil.toJsonString(m);
		return ss;
	}
}
