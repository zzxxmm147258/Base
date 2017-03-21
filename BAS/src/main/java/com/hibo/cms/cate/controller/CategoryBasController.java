package com.hibo.cms.cate.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.cms.cate.service.ICategoryBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年8月19日 上午10:53:06</p>
 * <p>类全名：com.hibo.cms.cate.controller.CategoryBasController</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Controller
@RequestMapping("/common/bas/categroy")
public class CategoryBasController {
	@Resource
	private ICategoryBasService categoryService;
	
	@RequestMapping(value="/catelist",method=RequestMethod.POST)
	@ResponseBody
	public String getCategoryList(String code){
		Message message = new Message(true);
		try {
			message.setDatas(categoryService.findAll(code, true));
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
		}
		return JsonUtil.toJsonString(message);
	}
}
