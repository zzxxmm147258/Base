package com.hibo.cms.articlenew.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.fileplugin.service.FileService;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.bas.util.page.taglib.utils.PageUtils;
import com.hibo.cms.articlenew.model.BasArticleButton;
import com.hibo.cms.articlenew.model.BasArticleCategory;
import com.hibo.cms.articlenew.model.BasArticleCategoryG;
import com.hibo.cms.articlenew.model.BasArticleNew;
import com.hibo.cms.articlenew.service.IBasArticleButtonService;
import com.hibo.cms.articlenew.service.IBasArticleNewService;
import com.hibo.cms.articlenew.service.IBasCategoryService;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月30日 下午1:02:36</p>
 * <p>类全名：com.hibo.cms.articlenew.controller.Controller</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */

@Controller
@RequestMapping(value="/main/articlenew")
public class ArticleNewController {
	@Autowired
	private IBasArticleNewService basArticleNewService;
	@Autowired
	private IDictinfoService dictinfoService;
	@Autowired
	private FileService fileServie;
	@Autowired
	private IBasCategoryService categoryService;
	@Autowired
	private IBasArticleButtonService buttonService;
	/**@功能:转到添加或修改页面
	 * @作者:lsw
	 * @时间:2016年5月30日 下午4:21:32
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addOrEdit")
	public String addOrEdit(String id,Model model){
		List<BasArticleCategory> categorys = categoryService.listCategory();
		List<Dictinfo> dictinfo = dictinfoService.selectByDictid(1010);
		List<Dictinfo> viewTypes = dictinfoService.selectByDictid(5011);
		model.addAttribute("categorys", categorys);
		model.addAttribute("dictinfo", dictinfo);
		model.addAttribute("viewTypes", viewTypes);
		if(!StrUtil.isnull(id)){
			BasArticleNew article=basArticleNewService.selectById(id);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date activeDate = article.getActiveDate();
			if(activeDate!=null){
			article.setActiveDates(format.format(activeDate));
			article.setActiveDate(null);}
			Date effectiveDate = article.getEffectiveDate();
			if(effectiveDate!=null){
			article.setEffectiveDates(format.format(effectiveDate));
			article.setEffectiveDate(null);}
			List<BasArticleButton> buttons = buttonService.selectByArticleId(id);
			if(buttons!=null&&buttons.size()>=1){
				BasArticleButton button1 =null;
				for (BasArticleButton basArticleButton : buttons) {
					if(basArticleButton.getSort()==1){
						button1=basArticleButton;
					}
				}
				article.setBtnName01(button1.getName());
				article.setBtnUrl01(button1.getUrl());
				article.setBtnIconSmall01(button1.getIconSmall());
				article.setBtnLoc01(button1.getLocation());
			}
			if(buttons!=null&&buttons.size()==2){
				BasArticleButton button2 =null;
				for (BasArticleButton basArticleButton : buttons) {
					if(basArticleButton.getSort()==2){
						button2=basArticleButton;
					}
				}
				article.setBtnName02(button2.getName());
				article.setBtnUrl02(button2.getUrl());
				article.setBtnIconSmall02(button2.getIconSmall());
				article.setBtnLoc02(button2.getLocation());
			}
			model.addAttribute("article", article);
		}
		return "articlenew/addoredit";
	}
	/**@功能:保存
	 * @作者:lsw
	 * @时间:2016年5月30日 下午4:21:48
	 * @param ban
	 * @return
	 */
	@RequestMapping(value="/save")
	public String save(BasArticleNew ban){
//		处理图片
		MultipartFile iconFile = ban.getIconFile();
		String iconOtherSize = ban.getIconOtherSize();
		MultipartFile imgFile = ban.getImgFile();
		String imgOtherSize = ban.getImgOtherSize();
		MultipartFile btnIcon01 = ban.getBtnIcon01();
		MultipartFile btnIcon02 = ban.getBtnIcon02();
		if(!iconFile.isEmpty()){
			try {
				StringBuilder wh=new StringBuilder();
				wh.append("200*200");
				boolean temp=false;
				if(!StrUtil.isnull(iconOtherSize)){
					wh.append(","+iconOtherSize);
					temp=true;
				}
				String[] upload = fileServie.upload(iconFile, wh.toString(),2);
				ban.setIcon(upload[0]);
				ban.setIconSmall(upload[1]);
				if(temp){
					ban.setIconOther(upload[2]);
				}
			} catch (Exception e) {
				System.out.println("图片上传失败！");
				e.printStackTrace();
			}
//			将文件清空
			ban.setIconFile(null);
		}
		if(!imgFile.isEmpty()){
			try {
				StringBuilder wh=new StringBuilder();
				wh.append("200*200");
				boolean temp=false;
				if(!StrUtil.isnull(imgOtherSize)){
					wh.append(","+imgOtherSize);
					temp=true;
				}
				String[] upload = fileServie.upload(imgFile, wh.toString(),2);
				ban.setImg(upload[0]);
				ban.setImgSmall(upload[1]);
				if(temp){
					ban.setImgOther(upload[2]);
				}
			} catch (Exception e) {
				System.out.println("图片上传失败！");
				e.printStackTrace();
			}
//			将文件清空
			ban.setImgFile(null);
		}
		if(!btnIcon01.isEmpty()){
			try {
				String[] upload1 = fileServie.upload(btnIcon01,"32*32",2);
				ban.setBtnIconUrl01(upload1[0]);
				ban.setBtnIconSmall01(upload1[1]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			将文件清空
			ban.setBtnIcon01(null);
		}
		if(!btnIcon02.isEmpty()){
			try {
				String[] upload2 = fileServie.upload(btnIcon02,"32*32",2);
				ban.setBtnIconUrl02(upload2[0]);
				ban.setBtnIconSmall02(upload2[1]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			将文件清空
			ban.setBtnIcon02(null);
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String activeDates = ban.getActiveDates();
		String effectiveDates = ban.getEffectiveDates();
		if(!StrUtil.isnull(activeDates)){
			try {
				Date date=format.parse(activeDates);
				ban.setActiveDate(date);
				ban.setActiveDates(null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!StrUtil.isnull(effectiveDates)){
			try {
				Date date=format.parse(effectiveDates);
				ban.setEffectiveDate(date);
				ban.setEffectiveDates(null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ban.setOperator(Envparam.getUserName());
		
		basArticleNewService.saveOrUpdate(ban);
		return "redirect:/main/articlenew/list";
	}
	/**@功能:通过大类查询小类
	 * @作者:lsw
	 * @时间:2016年5月30日 下午5:15:34
	 * @param lcode
	 * @return
	 */
	@RequestMapping(value="/listCategory.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String listCategory(String category){
		List<BasArticleCategoryG> articleCategorys = categoryService.listCategoryGByCategoryCode(category);
		return JsonUtil.toJsonString(articleCategorys);
	}
	/**@功能:列表首页
	 * @作者:lsw
	 * @时间:2016年5月31日 上午10:49:04
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request,Model model){
		List<BasArticleCategory> categorys = categoryService.listCategory();
		model.addAttribute("categorys", categorys);
		Map map = request.getParameterMap();
		Map selectMap = MapUtil.getMapValues(map);
		String categoryId = (String) selectMap.get("categoryId");
		if(!StrUtil.isnull(categoryId)){
			List<BasArticleCategoryG> articleCategorys = categoryService.listCategoryGByCategoryCode(categoryId);
			model.addAttribute("articleCategorys", articleCategorys);
		}
		Map map2=new HashMap<>();
		map2.putAll(selectMap);
		PageList<BasArticleNew> pageList = basArticleNewService.selectPaginition(map2, new PageBounds(PageUtils.getCurrentPage(request),PageUtils.getPageSize(request)));
	    model.addAttribute("list", pageList);
	    model.addAttribute("page", pageList.getPaginator());
	    model.addAttribute("selectMap",selectMap);
		return "articlenew/list";
	}
	@RequestMapping(value="/delete")
	public String delete(String id){
		int i=basArticleNewService.deleteById(id);
		return "redirect:/main/articlenew/list";
	}
}
