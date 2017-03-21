package com.hibo.cms.article.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.article.dao.BasArticleMainMapper;
import com.hibo.cms.article.model.BasArticleMain;
import com.hibo.cms.article.service.IBasArticleMainService;
import com.hibo.cms.lims.QueryFilterBuilder;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月1日 下午2:55:25</p>
 * <p>类全名：com.hibo.cms.article.service.impl.BasArticleMainServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class BasArticleMainServiceImpl implements IBasArticleMainService{
 
	@Autowired
	private BasArticleMainMapper basArticleMainMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return basArticleMainMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(BasArticleMain record) {
		return basArticleMainMapper.insert(record);
	}

	@Override
	public int insertSelective(BasArticleMain record) {
		return basArticleMainMapper.insert(record);
	}

	@Override
	public BasArticleMain selectByPrimaryKey(String id) {
		return basArticleMainMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BasArticleMain record) {
		return basArticleMainMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BasArticleMain record) {
		return basArticleMainMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<BasArticleMain> selectPage(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return basArticleMainMapper.selectPage(wStr, pageBounds);
	}

	@Override
	public List<BasArticleMain> selectAll() {
		return basArticleMainMapper.selectAll();
	}

	@Override
	public List<BasArticleMain> selectByAll(Map map) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return basArticleMainMapper.selectByAll(wStr);
	}

	@Override
	public List<BasArticleMain> selectDic(String dic) {
		return basArticleMainMapper.selectDic(dic);
	}

}
