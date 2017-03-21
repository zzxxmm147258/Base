package com.hibo.cms.sys.controller.mess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年1月14日 上午10:23:03</p>
 * <p>类全名：com.hibo.cms.sys.controller.mess.MessController</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Controller    
@RequestMapping(value="/message")
public class MessController {
	@RequestMapping(value="/{type}")
	public String messageType(@PathVariable(value="type")String type,String message,Model model){
		model.addAttribute("message", message);
		return "message/"+type;
	}
}
