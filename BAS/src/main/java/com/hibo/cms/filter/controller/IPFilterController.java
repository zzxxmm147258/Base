package com.hibo.cms.filter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.filter.model.SysIPFilter;
import com.hibo.cms.filter.service.IPFilterService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月18日 上午9:49:11</p>
 * <p>类全名：com.hibo.cms.filter.controller.IPFilterController</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping(value="/admin/bas/ipfilter")
public class IPFilterController {
	
	@Resource
	private IPFilterService ipFilterService;
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String addIpPage(SysIPFilter ipFilter){
		return "ipfilter/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addIp(SysIPFilter ipFilter,ModelMap mode){
		int k = ipFilterService.addIp(ipFilter);
		mode.addAllAttributes(getResultMap(k, "添加"));
		return "ipfilter/ipfilter";
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String SysIPFilter(String id,ModelMap mode){
		SysIPFilter ipFilter= ipFilterService.selectById(id);
		mode.addAttribute("ipFilter",ipFilter);
		return "ipfilter/add";
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String updataIp(SysIPFilter ipFilter,ModelMap mode){
		int k = ipFilterService.updataIp(ipFilter);
		mode.addAllAttributes(getResultMap(k, "更新"));
		return "ipfilter/ipfilter";
	}
	
	@RequestMapping(value="/delete.ajax",method=RequestMethod.POST)
	public String delIp(String id,ModelMap mode){
		int k = ipFilterService.delIp(id);
		mode.addAllAttributes(getResultMap(k, "删除"));
		return "ipfilter/ipfilter";
	}
	
	@RequestMapping(value="/list")
	public String ipList(ModelMap mode){
		mode.addAttribute("iplist", getList());
		return "ipfilter/ipfilter";
	}
	
	private List<SysIPFilter> getList(){
		List<SysIPFilter> list = new ArrayList<SysIPFilter>();
		list.addAll(ipFilterService.getIPFilterList(true));
		list.addAll(ipFilterService.getIPFilterList(false));
		return list;
	}
	
	private Map<String,Object> getResultMap(int k,String msg){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String message = msg+"失败!";
		boolean isSuccess = false;
		if(k>0){
			message = msg+"成功!";
			isSuccess = true;
			resultMap.put("iplist", getList());
		}
		resultMap.put("success", isSuccess);
		resultMap.put("message", message);
		return resultMap;
	}
}
