package com.hibo.cms.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.article.dao.BasArticleRelationMapper;
import com.hibo.cms.article.model.BasArticleRelation;
import com.hibo.cms.article.model.BasArticleRelationKey;
import com.hibo.cms.article.service.IBasArticleRelationService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月1日 下午3:01:32</p>
 * <p>类全名：com.hibo.cms.article.service.impl.BasArticleRelationServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class BasArticleRelationServiceImpl implements IBasArticleRelationService{
  
	  @Autowired
	  private BasArticleRelationMapper basArticleRelationMapper;

	@Override
	public int deleteByPrimaryKey(BasArticleRelationKey key) {
		return basArticleRelationMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(BasArticleRelation record) {
		return basArticleRelationMapper.insert(record);
	}

	@Override
	public int insertSelective(BasArticleRelation record) {
		return basArticleRelationMapper.insertSelective(record);
	}

	@Override
	public BasArticleRelation selectByPrimaryKey(BasArticleRelationKey key) {
		return basArticleRelationMapper.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(BasArticleRelation record) {
		return basArticleRelationMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BasArticleRelation record) {
		return basArticleRelationMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<BasArticleRelation> selectAll() {
		return basArticleRelationMapper.selectAll();
	}

	@Override
	public List<BasArticleRelation> selectArticleMainId(String articleMainId) {
		return basArticleRelationMapper.selectArticleMainId(articleMainId);
	}

	@Override
	public int deleteArticleMainId(String articleMainId) {
		return basArticleRelationMapper.deleteArticleMainId(articleMainId);
	}
}
