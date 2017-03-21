package com.hibo.cms.version.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.hibo.bas.util.StrUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.version.model.VersionControlBas;
import com.hibo.cms.version.service.IVersionControlBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月10日 上午10:14:40</p>
 * <p>类全名：com.hibo.cms.version.controller.VersionControlBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/bas/version")
public class VersionControlBasController {
	

	@Autowired
	private IVersionControlBasService versionControlBasService;
	
	/**
	 * <p>功能：查询<p>
	 * <p>创建日期：2016年5月9日 下午5:41:06<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String selectALitle(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<VersionControlBas> pageList=null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = versionControlBasService.selectByCondition(map, new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
		    model.addAttribute("list", pageList);
		    model.addAttribute("page", pageList.getPaginator());
		    model.addAttribute("selectMap",selectMap);
		}else{
			model.addAttribute("select",select);
		}
		return "version/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,String id){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		VersionControlBas property = versionControlBasService.selectByPrimaryKey(id);
		VersionControlBas bean=new VersionControlBas();
		if(property!=null){//为更新操作
			if(property.getVersionDate()!=null){
				property.setVersion_date(formatter.format(property.getVersionDate()));
			}
			model.addAttribute("bean",property);
		}else{//新增操作
			model.addAttribute("bean",bean);
		}
		model.addAttribute("method", "post");
		return "version/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid VersionControlBas bean,Model model){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		VersionControlBas property = versionControlBasService.selectByPrimaryKey(bean.getId());
		try {
			 if(!StrUtil.isnull(bean.getVersion_date())){
				bean.setVersionDate(formatter.parse(bean.getVersion_date()));
			  }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		if(property==null){//如果为空则新增 
			versionControlBasService.insertSelective(bean);
		}else{//不为空则修改
			versionControlBasService.updateByPrimaryKeySelective(bean);
		}
		model.addAttribute("updateSuccess", true);
		return "redirect:/main/bas/version/list";
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String delete(String id){
		int now = versionControlBasService.deleteByPrimaryKey(id);
		return JSON.toString(now);
	}

}
