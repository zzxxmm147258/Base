package com.hibo.cms.articlenew.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibo.cms.articlenew.dao.BasArticleCategoryGMapper;
import com.hibo.cms.articlenew.dao.BasArticleCategoryMapper;
import com.hibo.cms.articlenew.model.BasArticleCategory;
import com.hibo.cms.articlenew.model.BasArticleCategoryG;
import com.hibo.cms.articlenew.service.IBasCategoryService;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月30日 下午4:04:47</p>
 * <p>类全名：com.hibo.cms.articlenew.service.impl.CategoryServiceImpl</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
@Service
@Transactional
public class BasCategoryServiceImpl implements IBasCategoryService {
	@Autowired
	private BasArticleCategoryMapper bacm;
	@Autowired
	private BasArticleCategoryGMapper bacgm;
	@Override
	public List<BasArticleCategory> listCategory() {
		return bacm.selectAll();
	}

	@Override
	public List<BasArticleCategoryG> listCategoryG() {
		return bacgm.selectAll();
	}

	@Override
	public List<BasArticleCategoryG> listCategoryGByCategoryCode(String lcode) {
		return bacgm.selectByLcode(lcode);
	}

}
