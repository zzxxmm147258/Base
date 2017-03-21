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
import com.hibo.cms.shop.model.DistrictBas;
import com.hibo.cms.shop.service.IBlockBasService;
import com.hibo.cms.shop.service.IDistrictBasService;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：街区列表</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月19日 上午9:37:01</p>
 * <p>类全名：com.hibo.cms.shop.controller.BlockBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/block")
public class BlockBasController {
	
	@Autowired
	private IBlockBasService blockBasService;
	
	@Autowired
	private IDistrictBasService districtBasService;

	@Resource
	private FileService fileServie;
	/**
	 * <p>功能：查询街区<p>
	 * <p>创建日期：2016年2月19日 上午9:54:44<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<BlockBas> pageList = null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = blockBasService.selectByCondition(map,new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap",selectMap);
			model.addAttribute("list", pageList);
		}else{
			model.addAttribute("select",select);
		}
		List<DistrictBas> disename=districtBasService.selectAll();
		model.addAttribute("disename", disename);
		Map<String, String> availabes = new HashMap<String, String>();
		availabes.put("0", "未启用");
		availabes.put("1", "已启用");
		model.addAttribute("availabes", availabes);
		return "shop/block/list";
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
	public String del(String blockId){
		int row = blockBasService.deleteByPrimaryKey(blockId);
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
	@RequestMapping(value="/{blockId}/update",method=RequestMethod.GET)
	public String update(@PathVariable(value="blockId") String blockId,Model model){
		BlockBas bean=blockBasService.selectByPrimaryKey(blockId);
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		List<DistrictBas> district=districtBasService.selectAll();//商圈信息
		model.addAttribute("district",district);
		return "shop/block/add";
	}
	@RequestMapping(value="/{blockId}/update",method=RequestMethod.POST)
	public String update(@PathVariable(value="blockId") String blockId,@Valid BlockBas bean,Model model,@RequestParam(value = "img") MultipartFile img,
			@RequestParam(value = "tour_img") MultipartFile tour_img){
		if(!img.isEmpty()){
			String url = fileServie.upload(img);
			bean.setBlockLogo(url);
			}
		if(!tour_img.isEmpty()){
			String url = fileServie.upload(tour_img);
			bean.setTourImg(url);
			}
		bean.setOperator(Envparam.getUser().getUsername());
		try {
			blockBasService.updateByPrimaryKeySelective(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("updateSuccess", true);
		return "redirect:/admin/block/list";
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
		BlockBas bean=new BlockBas();
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		List<DistrictBas> district=districtBasService.selectAll();//商圈信息
		model.addAttribute("district",district);
		return "shop/block/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Valid BlockBas bean,@RequestParam(value = "img") MultipartFile img,
			@RequestParam(value = "tour_img") MultipartFile tour_img){
		if(!img.isEmpty()){
			String url = fileServie.upload(img);
			bean.setBlockLogo(url);
			}
		if(!tour_img.isEmpty()){
			String url = fileServie.upload(tour_img);
			bean.setTourImg(url);
			}
		bean.setOperator(Envparam.getUser().getUsername());
		try {
			blockBasService.insertSelective(bean);
		} catch (Exception e) {
           e.printStackTrace();
		}
		return "redirect:/admin/block/list";
	}
	
}
