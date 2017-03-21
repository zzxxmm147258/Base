package com.hibo.cms.article.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.advert.model.AdvertBas;
import com.hibo.cms.advert.service.IAdvertBasService;
import com.hibo.cms.article.model.BasArticle;
import com.hibo.cms.article.service.IBasArticleService;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月2日 下午2:12:33</p>
 * <p>类全名：com.hibo.cms.common.controller.shop.ArticleController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/common/article")
public class ArticleCommonController {
	
	@Resource
	private IDictinfoService dictinfoService;
	
	@Autowired
	private IBasArticleService basArticleService;
	
	@Autowired
	private IAdvertBasService advertBasService;

	/**
	 * <p>功能：查询所谓文章分类<p>
	 * <p>创建日期：2016年3月2日 下午2:18:11<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByList(String code){
		if(StrUtil.isnull(code)){
			return null;
		}
		int icode = StrUtil.obj2int(code);
		List<Dictinfo> list = dictinfoService.selectByDictid(icode, null);// 文章分类（如政策，专题，人物。。。）
		return JsonUtil.toJsonString(list);
	}

	
	/**
	 * <p>功能：查询分类的置顶文章
	 * 查询条件：已发布，有效，在时间范围内，且是置顶的文章<p>
	 * <p>创建日期：2016年3月2日 下午2:20:55<p>
	 * <p>作者：曾小明<p>
	 * @param articleCategory
	 * @return
	 */
	@RequestMapping(value="/istop.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByIsTop(String articleCategory,String category){
		List<BasArticle> list=basArticleService.selectByIsTop(articleCategory,category);
		return JsonUtil.toJsonString(list);
	}
	
	/**
	 * <p>功能：很据列别查询文章列表
	 * 条件：已发布，当前时间大于生效时间，排除置顶文章<p>
	 * <p>创建日期：2016年3月2日 下午2:51:27<p>
	 * <p>作者：曾小明<p>
	 * @param articleCategory
	 * @return
	 */
	@RequestMapping(value="/article.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByArticle(String articleCategory,String category,String excCategory,String page, String limit){
		int[] p = StrUtil.getPageLimit(page, limit, 100);
		List<BasArticle> list=basArticleService.selectByArticle(articleCategory,category,excCategory, p[0], p[1]);
		return JsonUtil.toJsonString(list);
	}
	
	
	/**
	 * <p>功能：文章预览<p>
	 * <p>创建日期：2016年3月3日 下午3:32:20<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/details")
	public String selectByArticleDetails(String id,Model model){
		//获取文章ID
		if(id!=null&&id.length()>0){
		    String[] ids=id.split("-");
		    if(ids.length==2){
		    id=ids[0];
		    }
		}
		BasArticle bean=basArticleService.selectByPrimaryKey(id);
		model.addAttribute("bean",bean);
		return "article/details";
	}
	
	/**
	 * <p>功能：文章预览<p>
	 * <p>创建日期：2016年3月3日 下午3:32:20<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/categroyDetails")
	public String selectByArticleDetails(String categroy,String articleCategory,Model model){
		BasArticle bean=basArticleService.selectByCategroy(categroy,articleCategory);
		model.addAttribute("bean",bean);
		return "article/details";
	}
	/**
	 * @功能:
	 * @作者:周雷
	 * @时间:2016年3月10日 上午9:51:12
	 * @param categroy
	 * @param articleCategory
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/findAvailable.ajax")
	@ResponseBody
	public String selectAvailableByPosition(String adPosition){
		List<AdvertBas> list = advertBasService.selectAvailableByPosition(adPosition);
		return JsonUtil.toJsonString(list);
	}
	
	/**
	 * <p>功能：惠民广告<p>
	 * <p>创建日期：2016年3月23日 下午3:20:29<p>
	 * <p>作者：曾小明<p>
	 * @param articleCategory
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/advert.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByAd(String articleCategory,String page, String limit){
		int[] p = StrUtil.getPageLimit(page, limit, 100);
		List<BasArticle> list=basArticleService.selectByAd(articleCategory, p[0], p[1]);
		return JsonUtil.toJsonString(list);
	}
	
	
	/**
	 * <p>功能：<p>
	 * <p>创建日期：2016年3月24日 下午1:03:15<p>
	 * <p>作者：曾小明<p>
	 * @param articleCategory
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/selectByGroupId.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByGroupId(String articleCategory,String page, String limit){
		int[] p = StrUtil.getPageLimit(page, limit, 100);
		List<BasArticle> list=basArticleService.selectByGroupId(articleCategory, p[0], p[1]);
		return JsonUtil.toJsonString(list);
	}
	/**
	 * @功能:文章预览
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月14日 下午6:52:38
	 * @param id
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/article")
	public String selectByArticle(String id,String title,Model model,HttpServletRequest request){
		//获取文章ID
		if(id!=null&&id.length()>0){
		    String[] ids=id.split("-");
		    if(ids.length==2){
		    id=ids[0];
		    }
		}
		BasArticle bean=basArticleService.selectByPrimaryKey(id);
		String isHead = StrUtil.obj2str(request.getParameter("isHead"));
		isHead = StrUtil.isnull(isHead)?"auto":isHead;
		if ("true".equalsIgnoreCase(isHead)) {
			String attr1 = bean.getAttr1();
			bean.setAttr1(attr1.replaceFirst("NO", "YES"));
		} else if ("false".equalsIgnoreCase(isHead)){
			String attr1 = bean.getAttr1();
			bean.setAttr1(attr1.replaceFirst("YES", "NO"));
		}
		List<Dictinfo> dictinfo = dictinfoService.selectByDictid(1010);
		model.addAttribute("isHead", isHead);
		model.addAttribute("dictinfo", dictinfo);
		model.addAttribute("bean",bean);
		model.addAttribute("title",title);
		return "article/article";
	}
	
	/**
	 * @功能:文章预览
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月14日 下午6:52:43
	 * @param categroy
	 * @param articleCategory
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/categroyArticle")
	public String selectByArticles(String categroy, String articleCategory, String title, Model model, HttpServletRequest request) {
		BasArticle bean = basArticleService.selectByCategroy(categroy, articleCategory);
		String isHead = StrUtil.obj2str(request.getParameter("isHead"));
		isHead = StrUtil.isnull(isHead)?"auto":isHead;
		if ("true".equalsIgnoreCase(isHead)) {
			String attr1 = bean.getAttr1();
			bean.setAttr1(attr1.replaceFirst("NO", "YES"));
		} else if ("false".equalsIgnoreCase(isHead)){
			String attr1 = bean.getAttr1();
			bean.setAttr1(attr1.replaceFirst("YES", "NO"));
		}
		List<Dictinfo> dictinfo = dictinfoService.selectByDictid(1010);
		model.addAttribute("isHead", isHead);
		model.addAttribute("dictinfo", dictinfo);
		model.addAttribute("bean", bean);
		model.addAttribute("title", title);
		return "article/article";
	}
}
