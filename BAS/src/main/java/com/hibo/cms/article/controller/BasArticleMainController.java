package com.hibo.cms.article.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.article.model.BasArticle;
import com.hibo.cms.article.model.BasArticleMain;
import com.hibo.cms.article.model.BasArticleRelation;
import com.hibo.cms.article.model.BasArticleRelationKey;
import com.hibo.cms.article.service.IBasArticleMainService;
import com.hibo.cms.article.service.IBasArticleRelationService;
import com.hibo.cms.article.service.IBasArticleService;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月1日 下午3:07:50</p>
 * <p>类全名：com.hibo.cms.article.controller.BasArticleMainController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/articlemain")
public class BasArticleMainController {
	
	@Autowired
	private IBasArticleMainService basArticleMainService;
	
	@Autowired
	private IBasArticleRelationService basArticleRelationService;

	@Autowired
	private IBasArticleService basArticleService;
	
	@Resource
	private IDictinfoService dictinfoService;

	@RequestMapping(value="/list")
	public String list(Model model,@RequestParam(defaultValue = "", required = false, value = "id")String id,HttpServletRequest request){
		String category=null;
		Map map = request.getParameterMap();
		Map selectMap = MapUtil.getMapValues(map);
		if(selectMap.get("category")!=null){
			category=selectMap.get("category").toString();
		}
		List<BasArticleMain> basArticleMain = basArticleMainService.selectByAll(map);
		model.addAttribute("basArticleMain", basArticleMain);
		model.addAttribute("selectMap",selectMap);
		model.addAttribute("id",id);
		
		if(category!=null&&category.length()>0){//文章大类ID
		    List<Dictinfo> articleCategory = dictinfoService.selectByDictid(Integer.parseInt(category), null);// 文章分类
		    model.addAttribute("articleCategorys", articleCategory);
		}else{
			List<Dictinfo> articleCategory = dictinfoService.selectByDictid(1012, null);// 文章分类
			model.addAttribute("articleCategorys", articleCategory);
		}
		
		List<Dictinfo> categorys = dictinfoService.selectByDictid(1011, null);// 文章大类
		model.addAttribute("categorys", categorys);
		
		return "articlemain/list";
	}
	
	/**
	 * <p>功能：根据父表查询字表<p>
	 * <p>创建日期：2016年3月1日 下午3:14:57<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getArticle",method=RequestMethod.POST)
	@ResponseBody
	public String getArticle(String id,Model model){
		List<BasArticle> basg = basArticleService.selectArticleMainId(id);
		model.addAttribute("basg", basg);
		String json = JsonUtil.toJsonString(basg);
		return json;
	}
	
	/**
	 * <p>功能：删除主表<p>
	 * <p>创建日期：2016年3月1日 下午3:18:37<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delmain.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String delMain(String id){
		basArticleMainService.deleteByPrimaryKey(id);
		int row=basArticleRelationService.deleteArticleMainId(id);
		String json = JSON.toString(row);
		return json;
	}
	
	/**
	 * <p>功能：删除字表<p>
	 * <p>创建日期：2016年3月1日 下午6:15:01<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String id){
		BasArticleRelationKey key=new BasArticleRelationKey();
		if(id!=null&&id.length()>0){
		    String[] ids=id.split("-");
		    if(ids.length==2){
		    key.setArticleId(ids[0]);
		    key.setArticleMainId(ids[1]);
		    }
		}
		int row=basArticleRelationService.deleteByPrimaryKey(key);
		String json = JSON.toString(row);
		return json;
	}
	/**
	 * <p>功能：添加<p>
	 * <p>创建日期：2016年3月1日 下午3:19:16<p>
	 * <p>作者：曾小明<p>
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpServletRequest request,@RequestParam(defaultValue = "", required = false, value = "id")String id){
		BasArticleMain bean=new BasArticleMain();
		model.addAttribute("bean",bean);
		model.addAttribute("method", "post");
		model.addAttribute("id",id);
		if(null != request.getParameter("addSuccess")){
			model.addAttribute("addSuccess", true);
		}
		List<Dictinfo> category = dictinfoService.selectByDictid(1011, null);// 文章大类
		model.addAttribute("categorys", category);
		List<Dictinfo> articleCategory = dictinfoService.selectByDictid(1012, null);// 文章分类
		model.addAttribute("articleCategorys", articleCategory);
		return "articlemain/add";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String add(Model model,@Valid BasArticleMain bean,@RequestParam(value = "date_from") String date_from,@RequestParam(value = "date_to") String date_to){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		    if(date_from!=null&& ! date_from.equals("")){//时间从
			bean.setDateFrom(formatter.parse(date_from));
			}
			if(date_to!=null&& ! date_to.equals("")){//时间到
			bean.setDateTo(formatter.parse(date_to));
	     	}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(!StrUtil.isnull(bean.getStatus().toString())&&bean.getStatus().toString().equals("70")){//状态为有效时修改生效时间
			bean.setEffectiveDate(new Date());
		}
		bean.setOperator(Envparam.getUser().getUsername());
		basArticleMainService.insertSelective(bean);
		//关联表
		if(bean.getBasArticle()!=null&&bean.getBasArticle().size()>0){
			List<BasArticle> list=bean.getBasArticle();
			for(int i=0;i<list.size();i++){
				BasArticleRelation bas=new BasArticleRelation();
				bas.setArticleMainId(bean.getId());//主表ID
				bas.setArticleId(list.get(i).getId());//文章表ID
				bas.setIsTop(list.get(i).getIsTop());//是否置顶
				bas.setIdx(list.get(i).getIdx());
				try {
					basArticleRelationService.insertSelective(bas);//保存关联表数据
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/main/articlemain/list";
	}
	
	/**
	 * <p>功能：修改<p>
	 * <p>创建日期：2016年3月1日 下午7:05:49<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	public String edit(@PathVariable(value="id") String id,Model model){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasArticleMain bean=basArticleMainService.selectByPrimaryKey(id);
		List<BasArticle> list=basArticleService.selectmId(id);
		bean.setBasArticle(list);
		model.addAttribute("list", list);
		model.addAttribute("bean",bean);
		if(bean.getDateFrom()!=null){//时间回显
			model.addAttribute("date_from", formatter.format(bean.getDateFrom()));
		}
		if(bean.getDateTo()!=null){//时间回显
			model.addAttribute("date_to", formatter.format(bean.getDateTo()));
		}
		List<Dictinfo> articleCategory = dictinfoService.selectByDictid(Integer.parseInt(bean.getCategory()), null);// 文章分类
		model.addAttribute("articleCategorys", articleCategory);
		List<Dictinfo> category = dictinfoService.selectByDictid(1011, null);// 文章大类
		model.addAttribute("categorys", category);
		model.addAttribute("method", "post");
		
		return "articlemain/add";
	}
	
	/**
	 * <p>功能：编辑保存<p>
	 * <p>创建日期：2016年3月2日 上午10:25:59<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @param model
	 * @param bean
	 * @param date_from
	 * @param date_to
	 * @return
	 */
	@RequestMapping(value="/{id}/update",method=RequestMethod.POST)
	public String update(@PathVariable(value="id") String id,Model model,@Valid BasArticleMain bean,@RequestParam(value = "date_from") String date_from,@RequestParam(value = "date_to") String date_to){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		    if(date_from!=null&& ! date_from.equals("")){//时间从
			bean.setDateFrom(formatter.parse(date_from));
			}
			if(date_to!=null&& ! date_to.equals("")){//时间到
			bean.setDateTo(formatter.parse(date_to));
	     	}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(!StrUtil.isnull(bean.getStatus().toString())&&bean.getStatus().toString().equals("70")){//状态为有效时修改生效时间
			bean.setEffectiveDate(new Date());
		}
		bean.setOperator(Envparam.getUser().getUsername());
		basArticleMainService.updateByPrimaryKeySelective(bean);
		//删除原来的
		int row=basArticleRelationService.deleteArticleMainId(bean.getId());
		//关联表
		if(bean.getBasArticle()!=null&&bean.getBasArticle().size()>0){
			List<BasArticle> list=bean.getBasArticle();
			for(int i=0;i<list.size();i++){
				BasArticleRelation bas=new BasArticleRelation();
				bas.setArticleMainId(bean.getId());//主表ID
				bas.setArticleId(list.get(i).getId());//文章表ID
				bas.setIsTop(list.get(i).getIsTop());//是否置顶
				bas.setIdx(list.get(i).getIdx());
				try {
					basArticleRelationService.insertSelective(bas);//保存关联表数据
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/main/articlemain/list";
	}

}
