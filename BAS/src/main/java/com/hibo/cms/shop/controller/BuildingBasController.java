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
import com.hibo.cms.shop.model.BlockBas;
import com.hibo.cms.shop.model.BuildingBas;
import com.hibo.cms.shop.service.IBlockBasService;
import com.hibo.cms.shop.service.IBuildingBasService;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：单元楼</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月19日 下午2:01:26</p>
 * <p>类全名：com.hibo.cms.shop.controller.BuildingBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/building")
public class BuildingBasController {
    
	@Autowired
	private IBlockBasService blockBasService;
	
	@Autowired
	private IBuildingBasService buildingBasService;

	@Resource
	private FileService fileServie;
	
	/**
	 * <p>功能：查询单元楼<p>
	 * <p>创建日期：2016年2月19日 下午2:08:53<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<BuildingBas> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = buildingBasService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		List<BlockBas> blockBas=blockBasService.selectAll();//街区信息
		model.addAttribute("blockBas", blockBas);
		Map<String, String> availabes = new HashMap<String, String>();
		availabes.put("0", "未启用");
		availabes.put("1", "已启用");
		model.addAttribute("availabes", availabes);
		return "shop/building/list";
	}
	
	/**
	 * <p>功能：删除<p>
	 * <p>创建日期：2016年2月19日 上午9:55:26<p>
	 * <p>作者：曾小明<p>
	 * @param district
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String buildingId){
		int row = buildingBasService.deleteByPrimaryKey(buildingId);
		String json = JSON.toString(row);
		return json;
	}
	
	/**
	 * <p>功能：跳转到修改页面<p>
	 * <p>创建日期：2016年2月19日 上午9:56:59<p>
	 * <p>作者：曾小明<p>
	 * @param blockId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{buildingId}/update",method=RequestMethod.GET)
	public String update(@PathVariable(value="buildingId") String buildingId,Model model){
		BuildingBas bean=buildingBasService.selectByPrimaryKey(buildingId);
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		List<BlockBas> blockBas=blockBasService.selectAllName();//街区信息
		model.addAttribute("blockBas", blockBas);
		return "shop/building/add";
	}
	@RequestMapping(value="/{buildingId}/update",method=RequestMethod.POST)
	public String update(@PathVariable(value="buildingId") String buildingId,@Valid BuildingBas bean,Model model,@RequestParam(value = "img") MultipartFile img,
			@RequestParam(value = "tour_img") MultipartFile tour_img){
		if(!img.isEmpty()){
			String url = fileServie.upload(img);
			bean.setBuildingLogo(url);
			}
		if(!tour_img.isEmpty()){
			String url = fileServie.upload(tour_img);
			bean.setTourImg(url);
			}
		bean.setOperator(Envparam.getUser().getUsername());
		try {
			buildingBasService.updateByPrimaryKeySelective(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("updateSuccess", true);
		return "redirect:/admin/building/list";
	}
	
	/**
	 * <p>功能：添加<p>
	 * <p>创建日期：2016年2月19日 上午10:47:24<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		BuildingBas bean=new BuildingBas();
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		List<BlockBas> blockBas=blockBasService.selectAllName();//街区信息
		model.addAttribute("blockBas", blockBas);
		return "shop/building/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Valid BuildingBas bean,@RequestParam(value = "img") MultipartFile img,
			@RequestParam(value = "tour_img") MultipartFile tour_img){
		if(!img.isEmpty()){
			String url = fileServie.upload(img);
			bean.setBuildingLogo(url);
			}
		if(!tour_img.isEmpty()){
			String url = fileServie.upload(tour_img);
			bean.setTourImg(url);
			}
		bean.setOperator(Envparam.getUser().getUsername());
		try {
			buildingBasService.insertSelective(bean);
		} catch (Exception e) {
           e.printStackTrace();
		}
		return "redirect:/admin/building/list";
	}
	
}
