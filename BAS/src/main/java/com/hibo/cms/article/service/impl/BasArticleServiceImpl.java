package com.hibo.cms.article.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.article.dao.BasArticleMapper;
import com.hibo.cms.article.model.BasArticle;
import com.hibo.cms.article.service.IBasArticleService;
import com.hibo.cms.lims.QueryFilterBuilder;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月29日 下午6:43:19</p>
 * <p>类全名：com.hibo.cms.article.service.impl.BasArticleServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class BasArticleServiceImpl implements IBasArticleService{
  
	@Autowired
	private BasArticleMapper basArticleMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return basArticleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(BasArticle record) {
		return basArticleMapper.insert(record);
	}

	@Override
	public int insertSelective(BasArticle record) {
		return basArticleMapper.insertSelective(record);
	}

	@Override
	public BasArticle selectByPrimaryKey(String id) {
		return basArticleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BasArticle record) {
		return basArticleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(BasArticle record) {
		return basArticleMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(BasArticle record) {
		return basArticleMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<BasArticle> selectPage(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return basArticleMapper.selectPage(wStr, pageBounds);
	}

	@Override
	public List<BasArticle> selectArticleMainId(String articleMainId) {
		return basArticleMapper.selectArticleMainId(articleMainId);
	}

	@Override
	public List<BasArticle> selectBasArticle(Map map) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return basArticleMapper.selectAll(wStr);
	}

	@Override
	public List<BasArticle> selectBasArticle(String title, String author, String datefrom, String dateto,String articleCategory,String category) {
		return basArticleMapper.selectBasArticle(title, author, datefrom, dateto,articleCategory,category);
	}

	@Override
	public List<BasArticle> selectmId(String articleMainId) {
		return basArticleMapper.selectmId(articleMainId);
	}

	@Override
	public List<BasArticle> selectByIsTop(String articleCategory,String category) {
		return basArticleMapper.selectByIsTop(articleCategory,category);
	}

	@Override
	public List<BasArticle> selectByArticle(String articleCategory,String category,String excCategory,int page, int limit) {
		return basArticleMapper.selectByArticle(articleCategory,category,excCategory, page, limit);
	}

	@Override
	public BasArticle selectByCategroy(String category, String articleCategory) {
		return basArticleMapper.selectByCategroy(category, articleCategory);
	}

	@Override
	public List<BasArticle> selectArticle(String title, String articleCategory, String matitle) {
		return basArticleMapper.selectArticle(title, articleCategory, matitle);
	}

	@Override
	public BasArticle selectByBuildingId(String buildingId) {
		return basArticleMapper.selectByBuildingId(buildingId);
	}

	@Override
	public List<BasArticle> selectByAd(String articleCategory, int page, int limit) {
		return basArticleMapper.selectByAd(articleCategory, page, limit);
	}

	@Override
	public List<BasArticle> selectByGroupId(String articleCategory, int page, int limit) {
		return basArticleMapper.selectByGroupId(articleCategory, page, limit);
	}
}
