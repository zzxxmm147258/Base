package com.hibo.cms.language.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.language.model.LanguageBas;
import com.hibo.cms.language.service.ILanguageBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年8月17日 上午10:26:12</p>
 * <p>类全名：com.hibo.cms.language.controller.LanguageBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/language")
public class LanguageBasController {
     
	@Autowired
	private ILanguageBasService languageBasService;
	
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<LanguageBas> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = languageBasService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		return "language/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,String id){
		LanguageBas property = languageBasService.selectByPrimaryKey(id);
		LanguageBas bean=new LanguageBas();
		if(property!=null){//为更新操作
			model.addAttribute("bean",property);
		}else{//新增操作
			model.addAttribute("bean",bean);
		}
		model.addAttribute("method", "post");
		return "language/add";
	}
	
	/**
	 * <p>功能：添加或删除<p>
	 * <p>创建日期：2016年4月21日 上午10:19:08<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid LanguageBas bean,Model model){
		LanguageBas property = languageBasService.selectByPrimaryKey(bean.getId());
		if(property==null){//如果为空则新增 
			languageBasService.insertSelective(bean);
		}else{//不为空则修改
			languageBasService.updateByPrimaryKeySelective(bean);
		}
		model.addAttribute("updateSuccess", true);
		return "redirect:/admin/language/list";
	}
	
	/**
	 * <p>功能：删除操作<p>
	 * <p>创建日期：2016年3月21日 下午2:15:29<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String delete(String id){
		int now = languageBasService.deleteByPrimaryKey(id);
		return JSON.toString(now);
	}
}
