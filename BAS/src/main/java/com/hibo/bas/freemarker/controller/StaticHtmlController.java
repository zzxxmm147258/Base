package com.hibo.bas.freemarker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hibo.bas.freemarker.IStaticService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月3日 下午4:18:03</p>
 * <p>类全名：com.hibo.bas.freemarker.controller.StaticHtmlController</p>
 * 作者：马骏达
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping(value = "/makehtml")
public class StaticHtmlController {

	@Resource(name = "staticServiceImpl")
	public IStaticService staticServiceImpl;

	@RequestMapping(value = "/test")
	public ModelAndView makeHtml(Model model) {
		Map<String, Object> value = new HashMap<String, Object>();
		List<String> types = new ArrayList<String>();
		types.add("1");
		types.add("2");
		types.add("3");
		value.put("types", types);
		int i = staticServiceImpl.build("list.ftl", "/news/test.html", value);
		System.out.println("生成的数量" + i);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("types", types);
		modelAndView.setViewName("sendMailTemplate/list");
		return modelAndView;
	}
}
