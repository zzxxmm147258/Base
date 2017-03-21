package com.hibo.bas.sms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.sms.model.SmsTempletBas;
import com.hibo.bas.sms.service.ISmsTempletBasService;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.sys.utils.json.JsonUtil;

/** <p>标题：短信模板</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月6日 上午9:35:56</p>
 * <p>类全名：com.hibo.bas.sms.controller.SmsTempletBasController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/tetete")
public class SmsTempletBasController {

	
	@Autowired
	private ISmsTempletBasService smsTempletBasService;
	
	/**
	 * <p>功能：分页查询<p>
	 * <p>创建日期：2016年3月7日 下午5:56:11<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String selectALitle(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<SmsTempletBas> pageList=null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = smsTempletBasService.selectPage(map, new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
		    model.addAttribute("list", pageList);
		    model.addAttribute("page", pageList.getPaginator());
		    model.addAttribute("selectMap",selectMap);
		}else{
			model.addAttribute("select",select);
		}
		return "tetete/list";
	}
	
	/**
	 * <p>功能：添加页面<p>
	 * <p>创建日期：2016年3月7日 下午5:55:59<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
	@RequestMapping(value="/add")
	public String add(Model model){
		SmsTempletBas bean=new SmsTempletBas();
		 model.addAttribute("bean",bean);
		return "tetete/add";
	}
	
	/**
	 * <p>功能：保存数据<p>
	 * <p>创建日期：2016年3月7日 下午5:55:45<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/save")
	public String save(SmsTempletBas bean){
		smsTempletBasService.insertSelective(bean);
		return "redirect:list";
	}
	
	/**
	 * <p>功能：编辑页面<p>
	 * <p>创建日期：2016年3月7日 下午5:55:29<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param tempNo
	 * @return
	 */
	@RequestMapping("/edit")
	public String editAdvert(Model model,String tempNo) {
		SmsTempletBas bean=smsTempletBasService.selectByPrimaryKey(tempNo);
		 model.addAttribute("bean",bean);
		 return "tetete/add";
	}
	
	/**
	 * <p>功能：修改保存<p>
	 * <p>创建日期：2016年3月7日 下午5:55:13<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/update")
	public String update(SmsTempletBas bean){
		smsTempletBasService.updateByPrimaryKeySelective(bean);
		return "redirect:list";
	}
	
	/**
	 * <p>功能：删除<p>
	 * <p>创建日期：2016年3月7日 下午5:54:58<p>
	 * <p>作者：曾小明<p>
	 * @param tempNo
	 * @return
	 */
	@RequestMapping("/del.ajax")
	@ResponseBody
	public String delete(String tempNo){
		int i =  smsTempletBasService.deleteByPrimaryKey(tempNo);
		if(i>0){
			return JsonUtil.toJsonString(true);
		}
		return JsonUtil.toJsonString(false);
	}
	
}
