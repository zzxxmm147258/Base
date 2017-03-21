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
import com.hibo.bas.util.StrUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.shop.model.FloorBas;
import com.hibo.cms.shop.model.ShopBas;
import com.hibo.cms.shop.model.ShopBasInfo;
import com.hibo.cms.shop.service.IFloorBasService;
import com.hibo.cms.shop.service.IShopBasInfoSevice;
import com.hibo.cms.shop.service.IShopBasService;
import com.hibo.cms.shop.service.IUserShopBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：商户</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午3:09:55</p>
 * <p>类全名：com.hibo.cms.shop.controller.ShopBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/shop")
public class ShopBasController {
	@Autowired
	private IShopBasService		shopBasService;
	@Autowired
	private IFloorBasService	floorBasService;
	@Resource
	private FileService			fileServie;
	@Autowired
	private IUserShopBasService	userShopBasService;
	@Resource
	private IDictinfoService	dictinfoService;
	@Autowired
	private IShopBasInfoSevice	shopBasInfoSevice;

	/**
	 * <p>功能：查询商户<p>
	 * <p>创建日期：2015年11月19日 下午3:21:12<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, HttpServletRequest request) {
		String select = request.getParameter("select");
		PageList<ShopBas> pageList = null;
		if (select == null) {
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = shopBasService.selectByPage(map, new PageBounds(PageUtils.getCurrentPage(request), PageUtils.getPageSize(request)));
			for (int i = 0; i < pageList.size(); i++) {//把多张图片的地址转成数组
				if (!StrUtil.isnull(pageList.get(i).getImages())) {
					String[] img1 = pageList.get(i).getImages().split(",");
					pageList.get(i).setImagess(img1);
				}
				if (!StrUtil.isnull(pageList.get(i).getChaIcon())) {
					String[] img2 = pageList.get(i).getChaIcon().split(",");
					pageList.get(i).setChaIcons(img2);
				}
			}
			model.addAttribute("page", pageList.getPaginator());
			model.addAttribute("selectMap", selectMap);
			model.addAttribute("list", pageList);
		} else {
			model.addAttribute("select", select);
		}
		List<FloorBas> floorBas = floorBasService.selectAllName();//楼层信息
		model.addAttribute("floorBas", floorBas);
		Map<String,String> availabes = new HashMap<String,String>();
		availabes.put("0", "未启用");
		availabes.put("1", "已启用");
		model.addAttribute("availabes", availabes);
		return "shop/shop/list";
	}

	/**
	 * <p>功能：删除商户<p>
	 * <p>创建日期：2015年11月19日 下午3:22:11<p>
	 * <p>作者：曾小明<p>
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value = "/del.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String del(String shopId) {
		int row = shopBasService.deleteByPrimaryKey(shopId);
		userShopBasService.deleteByShopId(shopId);
		String json = JSON.toString(row);
		return json;
	}

	/**
	 * <p>功能：修改商户<p>
	 * <p>创建日期：2015年11月19日 下午3:23:57<p>
	 * <p>作者：曾小明<p>
	 * @param shopId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{shopId}/update", method = RequestMethod.GET)
	public String update(@PathVariable(value = "shopId") String shopId, Model model) {
		ShopBas bean = shopBasService.selectByPrimaryKey(shopId);
		model.addAttribute("bean", bean);
		model.addAttribute("method", "post");
		List<FloorBas> floorBas = floorBasService.selectAllName();//楼层信息
		model.addAttribute("floorBas", floorBas);
		return "shop/shop/add";
	}

	@RequestMapping(value = "/{shopId}/update", method = RequestMethod.POST)
	public String update(@PathVariable(value = "shopId") String shopId, @Valid ShopBas bean, Model model, @RequestParam(value = "img") MultipartFile img, @RequestParam(value = "img1") MultipartFile[] img1, @RequestParam(value = "img2") MultipartFile[] img2) {
		if (!img.isEmpty()) {
			String url = fileServie.upload(img);
			bean.setShopLogo(url);
		}
		if (img1.length > 0) {
			StringBuffer images = new StringBuffer();
			for (int i = 0; i < img1.length; i++) {
				String url1 = fileServie.upload(img1[i]);
				images.append(url1 + ",");
			}
			bean.setImages(images.toString());
		}
		if (img2.length > 0) {
			StringBuffer chaIcon = new StringBuffer();
			for (int i = 0; i < img2.length; i++) {
				String url2 = fileServie.upload(img2[i]);
				chaIcon.append(url2 + ",");
			}
			bean.setChaIcon(chaIcon.toString());
		}
		bean.setOperator(Envparam.getUser().getUsername());
		ShopBas key = new ShopBas();
		key.setShopId(shopId);
		try {
			shopBasService.update(key, bean);
		} catch (Exception e) {
		}
		model.addAttribute("updateSuccess", true);
		return "redirect:/admin/shop/list";
	}

	/**
	 * <p>功能：新增商户<p>
	 * <p>创建日期：2015年11月19日 下午3:24:57<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		ShopBas bean = new ShopBas();
		model.addAttribute("bean", bean);
		model.addAttribute("method", "post");
		List<FloorBas> floorBas = floorBasService.selectAllName();//楼层信息
		model.addAttribute("floorBas", floorBas);
		return "shop/shop/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Model model, @Valid ShopBas bean, @RequestParam(value = "img") MultipartFile img, @RequestParam(value = "img1") MultipartFile[] img1, @RequestParam(value = "img2") MultipartFile[] img2) {
		if (!img.isEmpty()) {
			String url = fileServie.upload(img);
			bean.setShopLogo(url);
		}
		if (img1.length > 0) {
			StringBuffer images = new StringBuffer();
			for (int i = 0; i < img1.length; i++) {
				String url1 = fileServie.upload(img1[i]);
				images.append(url1 + ",");
			}
			bean.setImages(images.toString());
		}
		if (img2.length > 0) {
			StringBuffer chaIcon = new StringBuffer();
			for (int i = 0; i < img2.length; i++) {
				String url2 = fileServie.upload(img2[i]);
				chaIcon.append(url2 + ",");
			}
			bean.setChaIcon(chaIcon.toString());
		}
		bean.setOperator(Envparam.getUser().getUsername());
		FloorBas floorBas = floorBasService.selectByPrimaryKey(bean.getFloorId());
		try {
			shopBasService.insertSelective(bean);
		} catch (Exception e) {
		}
		return "redirect:/admin/shop/list";
	}

	/**
	 * <p>功能：根据商户名称模糊查询商户<p>
	 * <p>创建日期：2015年11月23日 下午2:16:53<p>
	 * <p>作者：曾小明<p>
	 * @param shop
	 * @return
	 */
	@RequestMapping(value = "/list.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String list(ShopBas shop) {
		List<ShopBas> list = shopBasService.selectFuzzy(shop.getShopname());
		return JsonUtil.toJsonString(list);
	}

	/**
	 * <p>功能：跳转到添加页面<p>
	 * <p>创建日期：2016年1月19日 下午2:47:06<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addshop", method = RequestMethod.GET)
	public String addBasShop(Model model) {
		List<FloorBas> floorBas = floorBasService.selectAllName();//楼层信息
		model.addAttribute("floorBas", floorBas);
		List<Dictinfo> showType = dictinfoService.selectByDictid(1010, null);// 订单来源
		model.addAttribute("showType", showType);
		return "shop/shop/add_info";
	}

	/**
	 * <p>功能：<p>
	 * <p>创建日期：2016年1月19日 下午1:59:14<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param shopBas
	 * @param img
	 * @param img1
	 * @param img2
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, @Valid ShopBas shopBas, @RequestParam(value = "img") MultipartFile img, @RequestParam(value = "img1") MultipartFile[] img1, @RequestParam(value = "img2") MultipartFile[] img2) {
		if (!img.isEmpty()) {
			String url = fileServie.upload(img);
			shopBas.setShopLogo(url);
		}
		if (img1.length > 0) {
			StringBuffer images = new StringBuffer();
			for (int i = 0; i < img1.length; i++) {
				if (!img1[i].isEmpty()) {
					String url1 = fileServie.upload(img1[i]);
					images.append(url1 + ",");
				}
			}
			if (images.length() > 0) {
				shopBas.setImages(images.toString());
			}
		}
		if (img2.length > 0) {
			StringBuffer chaIcon = new StringBuffer();
			for (int i = 0; i < img2.length; i++) {
				if (!img2[i].isEmpty()) {
					String url2 = fileServie.upload(img2[i]);
					chaIcon.append(url2 + ",");
				}
			}
			if (chaIcon.length() > 0) {
				shopBas.setChaIcon(chaIcon.toString());
			}
		}
		shopBas.setOperator(Envparam.getUser().getUsername());
		try {
			int now = shopBasService.insertSelective(shopBas);
			if (now > 0) {
				List<ShopBasInfo> list = shopBas.getShopbasinfo();
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						ShopBasInfo bean = list.get(i);
						bean.setShopId(shopBas.getShopId());
						bean.setShopname(shopBas.getShopname());
						bean.setOperator(Envparam.getUser().getUsername());
						if (!bean.getImgs().isEmpty()) {//展示图片
							String showImage = fileServie.upload(bean.getImgs());
							bean.setShowImage(showImage);
						}
						shopBasInfoSevice.insertSelective(bean);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("shopId", shopBas.getShopId());
		return "redirect:/admin/shop/edit";
	}

	/**
	 * <p>功能：编辑商户信息<p>
	 * <p>创建日期：2016年1月19日 下午1:58:09<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(Model model, @RequestParam(defaultValue = "", required = false, value = "shopId") String shopId) {
		List<FloorBas> floorBas = floorBasService.selectAllName();//楼层信息
		model.addAttribute("floorBas", floorBas);
		ShopBas shopBas = shopBasService.selectByPrimaryKey(shopId);
		List<Dictinfo> showType = dictinfoService.selectByDictid(1010, null);// 订单来源
		model.addAttribute("showType", showType);
		if (shopBas != null) {//回显楼层名称
			FloorBas floor = floorBasService.selectByName(shopBas.getFloorId());
			if (floor != null) {// 吕亚南增加判断
				model.addAttribute("floorName", floor.getFloorName());
				List<ShopBasInfo> list = shopBasInfoSevice.selectByShopId(shopId);
				shopBas.setShopbasinfo(list);
				model.addAttribute("shopBas", shopBas);
			}
		}
		return "shop/shop/edit_info";
	}

	/**
	 * <p>功能：修改商户信息<p>
	 * <p>创建日期：2016年1月19日 下午2:52:50<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param shopBas
	 * @param img
	 * @param img1
	 * @param img2
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, @Valid ShopBas shopBas, @RequestParam(value = "img") MultipartFile img, @RequestParam(value = "img1") MultipartFile[] img1, @RequestParam(value = "img2") MultipartFile[] img2) {
		if (!img.isEmpty()) {
			String url = fileServie.upload(img);
			shopBas.setShopLogo(url);
		}
		if (img1.length > 0) {
			StringBuffer images = new StringBuffer();
			for (int i = 0; i < img1.length; i++) {
				if (!img1[i].isEmpty()) {//图片文件不为空
					String url1 = fileServie.upload(img1[i]);
					images.append(url1 + ",");
				}
			}
			if (images.length() > 0) {//图片路径不为空
				shopBas.setImages(images.toString());
			}
		}
		if (img2.length > 0) {
			StringBuffer chaIcon = new StringBuffer();
			for (int i = 0; i < img2.length; i++) {
				if (!img2[i].isEmpty()) {//图片文件不为空
					String url2 = fileServie.upload(img2[i]);
					chaIcon.append(url2 + ",");
				}
			}
			if (chaIcon.length() > 0) {
				shopBas.setChaIcon(chaIcon.toString());
			}
		}
		shopBas.setOperator(Envparam.getUser().getUsername());
		try {
			int now = shopBasService.updateByPrimaryKeySelective(shopBas);
			if (now > 0) {
				//shopBasInfoSevice.deleteByShopId(shopBas.getShopId());//删除原来的
				List<ShopBasInfo> list = shopBas.getShopbasinfo();
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {//有值为空的删除
						if (list.get(i).getAviliable() == null || StrUtil.isnull(list.get(i).getShowType())) {
							list.remove(i);
						}
					}
					for (int i = 0; i < list.size(); i++) {
						ShopBasInfo bean = list.get(i);
						bean.setShopId(shopBas.getShopId());
						bean.setShopname(shopBas.getShopname());
						bean.setOperator(Envparam.getUser().getUsername());
						if (!bean.getImgs().isEmpty()) {//展示图片
							String showImage = fileServie.upload(bean.getImgs());
							bean.setShowImage(showImage);
						 }
						ShopBasInfo p=shopBasInfoSevice.selectByPrimaryKey(bean.getId());
						if(p==null){
						   shopBasInfoSevice.insertSelective(bean);
						}else{
							shopBasInfoSevice.update(bean);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("shopId", shopBas.getShopId());
		return "redirect:/admin/shop/edit";
	}
}