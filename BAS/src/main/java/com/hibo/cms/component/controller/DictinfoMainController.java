package com.hibo.cms.component.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年1月28日 上午9:37:46</p>
 * <p>类全名：com.hibo.cms.component.controller.DictinfoMainController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/dictinfo")
public class DictinfoMainController {
	
	@Autowired
	private IDictinfoService dictinfoService;
	
	/**
	 * 辅助录入根据主表ID，查询字表数据
	 * 要求主表Id已知
	 * @return
	 */
	@RequestMapping(value = "/{id}/find", method = RequestMethod.GET)
	public ModelAndView findByDictid(ModelAndView model, @PathVariable("id")Integer id,
			@RequestParam(value = "flag", defaultValue = "", required = false)String flag,
			@RequestParam(value = "currentSalesStatus", required = false)String currentSalesStatus
			){
		model.addObject("diciInfoList", this.dictinfoService.selectByDictid(id,currentSalesStatus));
		model.addObject("flag", flag);
		model.setViewName("/component/result");
		return model;
	}
	
	/**
	 * <p>功能：根据Dictid查出对应字典数据 </p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2016年1月28日 上午9:51:23</p>
	 * @param dictid
	 * @param current
	 * @return
	 */
	@RequestMapping(value = "/ajaxFindByDictid", method = RequestMethod.POST)
	public String ajaxFindByDictid(int dictid,String current){
		List<Dictinfo> list = dictinfoService.selectByDictid(dictid,current);
		return JsonUtil.toJsonString(list);
	}

}
