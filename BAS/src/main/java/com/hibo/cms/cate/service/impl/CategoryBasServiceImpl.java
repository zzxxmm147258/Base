package com.hibo.cms.cate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.cate.dao.CategoryBasMapper;
import com.hibo.cms.cate.model.CategoryBas;
import com.hibo.cms.cate.service.ICategoryBasService;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2016年8月19日 上午10:53:33
 * </p>
 * <p>
 * 类全名：com.hibo.cms.cate.service.impl.CategoryBasServiceImpl
 * </p>
 * 作者：周雷 初审：周雷 复审：周雷
 */
@Service
public class CategoryBasServiceImpl implements ICategoryBasService {
	@Autowired
	private CategoryBasMapper categoryBasMapper;

	@Override
	public List<CategoryBas> findAll(String code, boolean isAll) {
		List<CategoryBas> cates = categoryBasMapper.findAll(code);
		Map<String, CategoryBas> cateSet = new HashMap<String, CategoryBas>();
		for (CategoryBas category : cates) {
			cateSet.put(category.getCode(), category);
		}
		cates.clear();
		CategoryBas category = null;
		Set<String> keys = cateSet.keySet();
		for (String codeKey : keys) {
			CategoryBas cate = cateSet.get(codeKey);
			if (!StrUtil.isnull(code) && cate.getCode().equals(code)) {
				category = cate;
			}
			if (cate.getIsTop()) {
				cates.add(cate);
			} else {
				String pid = cate.getpCode();
				if (cateSet.containsKey(pid)) {
					cateSet.get(pid).getCates().add(cate);
				}
			}
		}
		if (null != category) {
			cates.clear();
			cates.add(category);
		}
		return cates;
	}

	private List<String> findChildCates(String code, List<CategoryBas> list) {
		List<String> thisCates = new ArrayList<String>();
		if (list != null) {
			for (CategoryBas categoryBas : list) {
				thisCates.add(categoryBas.getCode());
				if (categoryBas.getCates().size() > 0) {
					thisCates.addAll(this.findChildCates(code, categoryBas.getCates()));
				}
			}
		}
		return thisCates;
	}

	@Override
	public int insertSelective(CategoryBas category) {
		return categoryBasMapper.insertSelective(category);
	}

	@Override
	public int updateByPrimaryKeySelective(CategoryBas category) {
		return categoryBasMapper.updateByPrimaryKeySelective(category);
	}

	@Override
	public int deleteByPrimaryKey(String code) {
		List<String> list = this.findChildCates(code, this.findAll(code, true));
		int now=0;
		for(int i=0;i<list.size();i++){
			now+=categoryBasMapper.deleteByPrimaryKey(list.get(i));
		}
		return now;
	}

	@Override
	public int insert(CategoryBas record) {
		return categoryBasMapper.insert(record);
	}

	@Override
	public CategoryBas selectByPrimaryKey(String code) {
		return categoryBasMapper.selectByPrimaryKey(code);
	}

	@Override
	public int updateByPrimaryKey(CategoryBas record) {
		return categoryBasMapper.updateByPrimaryKey(record);
	}

}
