package com.hibo.cms.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.fileplugin.service.FileService;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.component.service.areabas.IAreaBasService;
import com.hibo.cms.shop.model.DistrictBas;
import com.hibo.cms.shop.service.IDistrictBasService;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：商圈</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午3:08:58</p>
 * <p>类全名：com.hibo.cms.shop.controller.DistrictBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/district")
public class DistrictBasController {
	
	@Autowired
	private IDistrictBasService districtBasService;
	
	@Autowired
	private IAreaBasService areaBasService;
	
	@Resource
	private FileService fileServie;
	/**
	 * <p>功能：查询商圈<p>
	 * <p>创建日期：2015年11月19日 下午3:12:49<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<DistrictBas> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = districtBasService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		Map<String, String> availabes = new HashMap<String, String>();
		availabes.put("0", "未启用");
		availabes.put("1", "已启用");
		model.addAttribute("availabes", availabes);
		return "shop/district/list";
	}
	
	
	/**
	 * <p>功能：删除商圈数据<p>
	 * <p>创建日期：2015年11月19日 下午3:14:00<p>
	 * <p>作者：曾小明<p>
	 * @param district
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String district){
		int row = districtBasService.deleteByPrimaryKey(district);
		String json = JSON.toString(row);
		return json;
	}
	
	/**
	 * <p>功能：修改商圈信息<p>
	 * <p>创建日期：2015年11月19日 下午3:17:16<p>
	 * <p>作者：曾小明<p>
	 * @param district
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{district}/update",method=RequestMethod.GET)
	public String update(@PathVariable(value="district") String district,Model model){
		DistrictBas bean=districtBasService.selectByPrimaryKey(district);
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		List<String> city = areaBasService.selectByCity(null);
		model.addAttribute("citys", city);
		
		return "shop/district/add";
	}
	@RequestMapping(value="/{district}/update",method=RequestMethod.POST)
	public String update(@PathVariable(value="district") String district,@Valid DistrictBas bean,Model model,@RequestParam(value = "img") MultipartFile img,
			@RequestParam(value = "tour_img") MultipartFile tour_img){
		if(!img.isEmpty()){
			String url = fileServie.upload(img);
			bean.setDistrictLogo(url);
			}
		if(!tour_img.isEmpty()){
			String url = fileServie.upload(tour_img);
			bean.setTourImg(url);
			}
		bean.setOperator(Envparam.getUser().getUsername());
		try {
			districtBasService.updateByPrimaryKeySelective(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("updateSuccess", true);
		return "redirect:/admin/district/list";
	}
	
	
	/**
	 * <p>功能：新增商圈信息<p>
	 * <p>创建日期：2015年11月19日 下午3:19:18<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		DistrictBas bean=new DistrictBas();
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		List<String> city = areaBasService.selectByCity(null);
		model.addAttribute("citys", city);
		return "shop/district/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Valid DistrictBas bean,@RequestParam(value = "img") MultipartFile img,
			@RequestParam(value = "tour_img") MultipartFile tour_img){
		if(!img.isEmpty()){
			String url = fileServie.upload(img);
			bean.setDistrictLogo(url);
			}
		if(!tour_img.isEmpty()){
			String url = fileServie.upload(tour_img);
			bean.setTourImg(url);
			}
		bean.setOperator(Envparam.getUser().getUsername());
		try {
			districtBasService.insertSelective(bean);
		} catch (Exception e) {
           e.printStackTrace();
		}
		return "redirect:/admin/district/list";
	}
}
