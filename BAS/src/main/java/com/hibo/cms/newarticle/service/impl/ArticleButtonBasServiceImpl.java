package com.hibo.cms.newarticle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.newarticle.dao.ArticleButtonBasMapper;
import com.hibo.cms.newarticle.model.ArticleButtonBas;
import com.hibo.cms.newarticle.service.IArticleButtonBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月19日 上午10:25:34</p>
 * <p>类全名：com.hibo.cms.newarticle.service.impl.ArticleButtonBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class ArticleButtonBasServiceImpl implements IArticleButtonBasService{

	@Autowired
	private ArticleButtonBasMapper articleButtonBasMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return articleButtonBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ArticleButtonBas record) {
		return articleButtonBasMapper.insert(record);
	}

	@Override
	public int insertSelective(ArticleButtonBas record) {
		return articleButtonBasMapper.insertSelective(record);
	}

	@Override
	public ArticleButtonBas selectByPrimaryKey(String id) {
		return articleButtonBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ArticleButtonBas record) {
		return articleButtonBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ArticleButtonBas record) {
		return articleButtonBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteBymId(String mId) {
		return articleButtonBasMapper.deleteBymId(mId);
	}

	@Override
	public List<ArticleButtonBas> selectBymId(String mId) {
		return articleButtonBasMapper.selectBymId(mId);
	}

}
