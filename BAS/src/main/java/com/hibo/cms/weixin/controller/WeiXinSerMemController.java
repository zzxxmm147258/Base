package com.hibo.cms.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.weixin.model.WeiXinSerMem;
import com.hibo.cms.weixin.service.IWeiXinSerMemService;

/**   
 * <p>标题：微信会员关联</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月25日 下午7:00:43</p>
 * <p>类全名：com.hibo.cms.weixin.controller.WeiXinSerMemController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/mainte/weixinser")
public class WeiXinSerMemController {
	
	
	@Autowired 
	private IWeiXinSerMemService weiXinSerMemService;
	
	/**
	 * <p>功能：会员微信关联<p>
	 * <p>创建日期：2015年11月26日 下午6:48:41<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<WeiXinSerMem> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = weiXinSerMemService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
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
		
		return "weixin/list_ser";
	}

}
