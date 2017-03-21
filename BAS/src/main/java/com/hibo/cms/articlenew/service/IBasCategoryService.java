package com.hibo.cms.articlenew.service;

import java.util.List;

import com.hibo.cms.articlenew.model.BasArticleCategory;
import com.hibo.cms.articlenew.model.BasArticleCategoryG;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月30日 下午4:04:21</p>
 * <p>类全名：com.hibo.cms.articlenew.service.ICategoryService</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
public interface IBasCategoryService {
	List<BasArticleCategory> listCategory();
	List<BasArticleCategoryG> listCategoryG();
	List<BasArticleCategoryG> listCategoryGByCategoryCode(String lcode);
}
