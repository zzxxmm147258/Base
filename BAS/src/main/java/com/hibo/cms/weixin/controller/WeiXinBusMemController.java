package com.hibo.cms.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.weixin.model.WeiXinBusMem;
import com.hibo.cms.weixin.service.IWeiXinBusMemService;


/**   
 * <p>标题：企业微信</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月25日 下午3:59:34</p>
 * <p>类全名：com.hibo.mem.weixin.controller.WeiXinMemController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/mainte/weixinbus")
public class WeiXinBusMemController {

	@Autowired 
	private IWeiXinBusMemService weiXinMemService;
	
	
	
	/**
	 * <p>功能：企业微信关联<p>
	 * <p>创建日期：2015年11月25日 下午6:45:03<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<WeiXinBusMem> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = weiXinMemService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			for(int i=0;i<pageList.size();i++){
				if(pageList.get(i).getUserid()!=null&&pageList.get(i).getUserid().length()==11){
					pageList.get(i).setUserid(pageList.get(i).getUserid().substring(0,3)+"****"+pageList.get(i).getUserid().substring(7, pageList.get(i).getUserid().length()));
				}
			}
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		Map<String, String> lockeds = new HashMap<String, String>();
		lockeds.put("0", "未禁用");
		lockeds.put("1", "已禁用");
		model.addAttribute("lockeds", lockeds);
		
		Map<String, String> subscribes = new HashMap<String, String>();
		subscribes.put("0", "未关注");
		subscribes.put("1", "已关注");
		model.addAttribute("subscribes", subscribes);
		
		return "weixin/list_bus";
	}
	
	/**
	 * <p>功能：删除<p>
	 * <p>创建日期：2015年12月2日 下午6:54:48<p>
	 * <p>作者：曾小明<p>
	 * @param district
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String openid){
		int row = weiXinMemService.deleteByPrimaryKey(openid);
		String json = JSON.toString(row);
		return json;
	}
	
	/**
	 * <p>功能：修改<p>
	 * <p>创建日期：2015年12月2日 下午6:58:05<p>
	 * <p>作者：曾小明<p>
	 * @param openid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{openid}/update",method=RequestMethod.GET)
	public String update(@PathVariable(value="openid") String openid,Model model){
		WeiXinBusMem bean=weiXinMemService.selectByPrimaryKey(openid);
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		return "weixin/add_bus";
	}
	@RequestMapping(value="/{openid}/update",method=RequestMethod.POST)
	public String update(@PathVariable(value="openid") String openid,@Valid WeiXinBusMem bean,Model model){
		weiXinMemService.updateByPrimaryKeySelective(bean);
		model.addAttribute("updateSuccess", true);
		return "redirect:/main/mainte/weixinbus/list";
	}
	
	/**
	 * <p>功能：添加<p>
	 * <p>创建日期：2015年12月2日 下午6:59:58<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		WeiXinBusMem bean=new WeiXinBusMem();
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		return "weixin/add_bus";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Valid WeiXinBusMem bean){
		weiXinMemService.insertSelective(bean);
		return "redirect:/main/mainte/weixinbus/list";
	}
}
