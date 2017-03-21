package com.hibo.cms.cate.service;

import java.util.List;

import com.hibo.cms.cate.model.CategoryBas;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年8月19日 上午10:53:37</p>
 * <p>类全名：com.hibo.cms.cate.service.ICategoryBasService</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public interface ICategoryBasService {
	List<CategoryBas> findAll(String code,boolean isAll);
	
	int deleteByPrimaryKey(String code);

    int insert(CategoryBas record);

    int insertSelective(CategoryBas record);

    CategoryBas selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(CategoryBas record);

    int updateByPrimaryKey(CategoryBas record);
}
