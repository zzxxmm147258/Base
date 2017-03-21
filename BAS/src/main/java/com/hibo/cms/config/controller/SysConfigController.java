package com.hibo.cms.config.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.config.model.Sysoptions;
import com.hibo.cms.config.service.ISysConfigService;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月12日 上午11:23:56</p>
 * <p>类全名：com.hibo.cms.config.controller.SysConfigController</p>
 * 作者：马骏达
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping(value = "/main/sysconfig")
public class SysConfigController {

	@Resource
	private ISysConfigService pluginService;;

	@RequestMapping(value = "/list")
	public String list(Model model,HttpServletRequest request) {
		PageList<Sysoptions> pluginConfigs = pluginService.getPluginConfigList(new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
		model.addAttribute("pluginConfigs", pluginConfigs);
		model.addAttribute("page", pluginConfigs.getPaginator());
		return "admin/sysconfig/list";
	}

	@RequestMapping(value = "/add")
	public String add(Model model) {
		return "admin/sysconfig/add";
	}

	@RequestMapping(value = "/save")
	public String save(Sysoptions pluginConfig, Model model) {
		if (null != pluginConfig) {
			pluginService.savePluginConfig(pluginConfig);
		}
		return "redirect:list";
	}

	@RequestMapping(value = "edit")
	public String edit(String id, Model model) {
		Sysoptions pluginConfig = pluginService.getPluginConfigById(id);
		model.addAttribute("pluginConfig", pluginConfig);
		return "admin/sysconfig/add";
	}

	@RequestMapping(value = "update")
	public String update(Sysoptions pluginConfig, Model model) {
		if (null != pluginConfig) {
			pluginService.updatePluginConfig(pluginConfig);
		}
		return "redirect:list";
	}

	@RequestMapping(value = "delete.ajax")
	@ResponseBody
	public Map<String, String> delete(String id, Model model) {
		Map<String, String> message = new HashMap<String, String>();
		if (pluginService.deletePluginConfigById(id) > 1) {
			message.put("type", "success");
		} else {
			message.put("type", "error");
		}
		return message;
	}
	
	@RequestMapping(value="selectALitle")
	public String selectALitle(Model model,HttpServletRequest request){
		Map map = request.getParameterMap();
		Map selectMap = MapUtil.getMapValues(map);
		PageList sList = pluginService.getSelectAlitel(map, new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
		model.addAttribute("pluginConfigs", sList);
		model.addAttribute("page", sList.getPaginator());
		model.addAttribute("selectMap",selectMap);
		return "admin/sysconfig/list";
		
	}
}
