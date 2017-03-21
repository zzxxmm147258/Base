package com.hibo.cms.newarticle.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.hibo.cms.cate.model.CategoryBas;
import com.hibo.cms.cate.service.ICategoryBasService;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.newarticle.model.ArticleButtonBas;
import com.hibo.cms.newarticle.model.NewArticleBas;
import com.hibo.cms.newarticle.service.IArticleButtonBasService;
import com.hibo.cms.newarticle.service.INewArticleBasService;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月19日 上午10:46:28</p>
 * <p>类全名：com.hibo.cms.newarticle.controller.NewArticleBas</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping(value="/main/newarticle")
public class NewArticleBasController {

	@Autowired
	private INewArticleBasService newArticleBasService;
	
	@Autowired
	private IArticleButtonBasService articleButtonBasService;
	
	@Resource
	private FileService fileServie;
	
	@Resource
	private ICategoryBasService categoryService;
	
	@RequestMapping(value="/list")
	public String selectALitle(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<NewArticleBas> pageList=null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			pageList = newArticleBasService.selectPage(map, new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
		    model.addAttribute("list", pageList);
		    model.addAttribute("page", pageList.getPaginator());
		    model.addAttribute("selectMap",selectMap);
		}else{
			model.addAttribute("select",select);
		}
		Map<String, String> is_publications = new HashMap<String, String>();//是否发布
		is_publications.put("0", "否");
		is_publications.put("1", "是");
		model.addAttribute("is_publications", is_publications);
		
		Map<String, String> is_tops = new HashMap<String, String>();//是否置顶
		is_tops.put("0", "否");
		is_tops.put("1", "是");
		model.addAttribute("is_tops", is_tops);
		
		List<CategoryBas> categorys = categoryService.findAll("0010", true);
		model.addAttribute("categorys", categorys.get(0).getCates());
		
		String path = request.getContextPath();
		//获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		model.addAttribute("basePath", basePath);
		
		return "newarticle/list";
	}
	
	
	@RequestMapping("/add")
	public String addAdvert(Model model) {
		List<CategoryBas> categorys = categoryService.findAll("0010", true);
		model.addAttribute("categorys", categorys.get(0).getCates());
		List<CategoryBas> returnType = categoryService.findAll("0020", true);
		model.addAttribute("returnType", returnType.get(0).getCates());
		return "newarticle/add";
	}
	
	@RequestMapping("/save")
	public String saveAdvert(NewArticleBas article,@RequestParam(value = "img_url") MultipartFile img_url,@RequestParam(value = "img_logo") MultipartFile img_logo) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		     if(!StrUtil.isnull(article.getEffective_date())){//入库日期
		    	 article.setEffectiveDate(formatter.parse(article.getEffective_date()));
		      }
		     if(!StrUtil.isnull(article.getA_date())){//自定义
		    	 article.setActiveDate(formatter.parse(article.getA_date()));
		      }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		if(!img_url.isEmpty()){//预览图
			String[] url = fileServie.upload(img_url,"200*200,600*800");
			article.setIcon(url[0]);//原图
			article.setIconSmall(url[1]);//小图
			article.setIconOther(url[2]);//自定义大小
			}
		if(!img_logo.isEmpty()){//备用图
			String[] url = fileServie.upload(img_logo,"200*200,600*800");
			article.setImg(url[0]);
			article.setImgSmall(url[1]);
			article.setImgOther(url[2]);
			}
		article.setOperator(Envparam.getUser().getUsername());
		newArticleBasService.insertSelective(article);
		return "redirect:list";
	}
	
	@RequestMapping("/edit")
	public String editAdvert(Model model,String id) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		NewArticleBas ad = newArticleBasService.selectByPrimaryKey(id);
		List<ArticleButtonBas> btn=articleButtonBasService.selectBymId(id);
		if(btn!=null && btn.size()>0){
			for(int i=0;i<btn.size();i++){
				if(btn.get(i).getSort()==1){
					ad.setBtnName01(btn.get(i).getName());
					ad.setBtnUrl01(btn.get(i).getUrl());
				}
				if(btn.get(i).getSort()==2){
					ad.setBtnName02(btn.get(i).getName());
					ad.setBtnUrl02(btn.get(i).getUrl());
				}
			}
		}
		model.addAttribute("article", ad);
		if(ad.getEffectiveDate()!=null){
		   model.addAttribute("effective_date", formatter.format(ad.getEffectiveDate()));
		}
		if(ad.getActiveDate()!=null){
			   model.addAttribute("a_date", formatter.format(ad.getActiveDate()));
			}
		List<CategoryBas> categorys = categoryService.findAll("0010", true);
		model.addAttribute("categorys", categorys.get(0).getCates());
		List<CategoryBas> returnType = categoryService.findAll("0020", true);
		model.addAttribute("returnType", returnType.get(0).getCates());
		
		List<CategoryBas> articleCategorys = categoryService.findAll(ad.getCategoryId(), true);
		model.addAttribute("articleCategorys", articleCategorys.get(0).getCates());
		
		return "newarticle/add";
	}
	
	@RequestMapping("/update")
	public String updateAdvert(NewArticleBas articleShop,@RequestParam(value = "img_url") MultipartFile img_url,@RequestParam(value = "img_logo") MultipartFile img_logo) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		     if(!StrUtil.isnull(articleShop.getEffective_date())){//入库日期
		    	 articleShop.setEffectiveDate(formatter.parse(articleShop.getEffective_date()));
		      }
		     if(!StrUtil.isnull(articleShop.getA_date())){//自定义
		    	 articleShop.setActiveDate(formatter.parse(articleShop.getA_date()));
		      }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		if(!img_url.isEmpty()){//预览图
			String[] url = fileServie.upload(img_url,"200*200,600*800");
			articleShop.setIcon(url[0]);//原图
			articleShop.setIconSmall(url[1]);//小图
			articleShop.setIconOther(url[2]);//自定义大小
			}
		if(!img_logo.isEmpty()){//备用图
			String[] url = fileServie.upload(img_logo,"200*200,600*800");
			articleShop.setImg(url[0]);
			articleShop.setImgSmall(url[1]);
			articleShop.setImgOther(url[2]);
			}
		articleShop.setOperator(Envparam.getUser().getUsername());
		newArticleBasService.updateByPrimaryKeySelective(articleShop);
		return "redirect:list";
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String id){
		int row = newArticleBasService.deleteByPrimaryKey(id);
		String json = JSON.toString(row);
		return json;
	}
}
