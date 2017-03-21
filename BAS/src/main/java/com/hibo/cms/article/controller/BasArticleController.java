package com.hibo.cms.article.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.hibo.cms.article.model.BasArticle;
import com.hibo.cms.article.service.IBasArticleService;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：微信文章</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月29日 下午6:42:06</p>
 * <p>类全名：com.hibo.cms.article.controller.BasArticleController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/article")
public class BasArticleController {

	@Autowired
	private IBasArticleService basArticleService;

	@Resource
	private IDictinfoService dictinfoService;
	
	@Resource
	private FileService fileServie;
	
	/**
	 * <p>功能：文章列表<p>
	 * <p>创建日期：2016年2月29日 下午6:47:49<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String selectALitle(Model model,HttpServletRequest request){
		String select = request.getParameter("select");
		PageList<BasArticle> pageList=null;
		String category=null;
		if(select==null){
			Map map = request.getParameterMap();
			Map selectMap = MapUtil.getMapValues(map);
			if(selectMap.get("category")!=null){
				category=selectMap.get("category").toString();
			}
			pageList = basArticleService.selectPage(map, new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
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
		if(category!=null&&category.length()>0){//文章大类ID
		   List<Dictinfo> articleCategory = dictinfoService.selectByDictid(Integer.parseInt(category), null);// 文章分类
		   model.addAttribute("articleCategorys", articleCategory);
		}else{
			List<Dictinfo> articleCategory = dictinfoService.selectByDictid(1012, null);// 文章分类
			model.addAttribute("articleCategorys", articleCategory);
		}
		List<Dictinfo> categorys = dictinfoService.selectByDictid(1011, null);// 文章大类
		model.addAttribute("categorys", categorys);
		
		String path = request.getContextPath();
		//获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		model.addAttribute("basePath", basePath);
		
		return "article/list";
	}
	
	
	/**
	 * <p>功能：跳转到添加页面<p>
	 * <p>创建日期：2016年2月29日 下午6:49:02<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String addAdvert(Model model) {
		List<Dictinfo> articleCategory = dictinfoService.selectByDictid(1012, null);// 文章分类
		model.addAttribute("articleCategorys", articleCategory);
		List<Dictinfo> dictinfo = dictinfoService.selectByDictid(1010);
		model.addAttribute("dictinfo", dictinfo);
		List<Dictinfo> category = dictinfoService.selectByDictid(1011, null);// 文章大类
		model.addAttribute("categorys", category);
		return "article/add";
	}
	
    /**
     * <p>功能：保存数据<p>
     * <p>创建日期：2016年2月29日 下午6:50:16<p>
     * <p>作者：曾小明<p>
     * @param article
     * @return
     */
	@RequestMapping("/save")
	public String saveAdvert(BasArticle article,@RequestParam(value = "img") MultipartFile img,@RequestParam(value = "img_logo") MultipartFile img_logo) {
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
		if(!img.isEmpty()){//预览图
			String url = fileServie.upload(img);
			article.setUrl(url);
			}
		if(!img_logo.isEmpty()){//logo
			String[] url = fileServie.upload(img_logo,"200*200");
			article.setAttr5(url[1]);
			}
		article.setOperator(Envparam.getUser().getUsername());
		basArticleService.insertSelective(article);
		return "redirect:list";
	}
	
	/**
	 * <p>功能：编辑<p>
	 * <p>创建日期：2016年2月29日 下午6:50:41<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String editAdvert(Model model,String id) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasArticle ad = basArticleService.selectByPrimaryKey(id);
		model.addAttribute("article", ad);
		if(ad.getEffectiveDate()!=null){
		   model.addAttribute("effective_date", formatter.format(ad.getEffectiveDate()));
		}
		if(ad.getActiveDate()!=null){
			   model.addAttribute("a_date", formatter.format(ad.getActiveDate()));
			}
		List<Dictinfo> dictinfo = dictinfoService.selectByDictid(1010);
		model.addAttribute("dictinfo", dictinfo);
		List<Dictinfo> category = dictinfoService.selectByDictid(1011, null);// 文章大类
		model.addAttribute("categorys", category);
		List<Dictinfo> articleCategory = dictinfoService.selectByDictid(Integer.parseInt(ad.getCategory()), null);// 文章分类
		model.addAttribute("articleCategorys", articleCategory);
		return "article/add";
	}
	
	
	/**
	 * <p>功能：修改保存<p>
	 * <p>创建日期：2016年2月29日 下午6:51:48<p>
	 * <p>作者：曾小明<p>
	 * @param articleShop
	 * @return
	 */
	@RequestMapping("/update")
	public String updateAdvert(BasArticle articleShop,@RequestParam(value = "img") MultipartFile img,@RequestParam(value = "img_logo") MultipartFile img_logo) {
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
		if(!img.isEmpty()){//预览图
			String url = fileServie.upload(img);
			articleShop.setUrl(url);
			}
		if(!img_logo.isEmpty()){//logo图
			String[] url = fileServie.upload(img_logo,"200*200");
			articleShop.setAttr5(url[1]);
			}
		articleShop.setOperator(Envparam.getUser().getUsername());
		basArticleService.updateByPrimaryKeySelective(articleShop);
		return "redirect:list";
	}
	
	
	/**
	 * <p>功能：删除<p>
	 * <p>创建日期：2016年2月29日 下午6:52:13<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String deleteSupplier(String id){
		int i =  basArticleService.deleteByPrimaryKey(id);
		if(i>0){
			return JsonUtil.toJsonString("success");
		}
		return JsonUtil.toJsonString("error");
	}
	
	/**
	 * <p>功能：删除操作<p>
	 * <p>创建日期：2016年3月1日 上午9:41:43<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String id){
		int row = basArticleService.deleteByPrimaryKey(id);
		String json = JSON.toString(row);
		return json;
	}
	
	/**
	 * <p>功能：模态框条件查询文章<p>
	 * <p>创建日期：2016年3月7日 下午4:58:03<p>
	 * <p>作者：曾小明<p>
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list.ajax")
	@ResponseBody
	public String getProductListAjax(HttpServletResponse response, HttpServletRequest request, Model model) {
		String title = request.getParameter("title").trim();
		String author = request.getParameter("author").trim();
		String datefrom = request.getParameter("datefrom").trim();
		String dateto = request.getParameter("dateto").trim();
		String articleCategoryId = request.getParameter("articleCategoryId").trim();//文章类型
		String categoryId = request.getParameter("categoryId").trim();//文章大类
		Map htObject = new HashMap();
		List<BasArticle> product = basArticleService.selectBasArticle(title, author, datefrom, dateto,articleCategoryId,categoryId);
		htObject.put("success", true);
		htObject.put("result", product);
		return JsonUtil.toJsonString(htObject);
	}
	
  
	/**
	 * <p>功能：根据大类查子类<p>
	 * <p>创建日期：2016年3月7日 下午3:45:04<p>
	 * <p>作者：曾小明<p>
	 * @param category
	 * @return
	 */
	@RequestMapping(value="/listCategory.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByCategory(String category){
		List<Dictinfo> list = dictinfoService.selectByDictid(Integer.parseInt(category), null);// 文章分类（如政策，专题，人物。。。）
		return JsonUtil.toJsonString(list);
	}

	
	@RequestMapping(value = "/article.ajax")
	@ResponseBody
	public String getAjax(BasArticle article, Model model) {
		String title = article.getTitle();
		String articleCategoryId ="101400";
		String matitle = null;//置顶标题
		List<BasArticle> product = basArticleService.selectArticle(title, articleCategoryId,matitle);
		return JsonUtil.toJsonString(product);
	}
	
}
