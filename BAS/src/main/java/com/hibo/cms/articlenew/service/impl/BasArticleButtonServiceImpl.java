package com.hibo.cms.articlenew.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibo.cms.articlenew.dao.BasArticleButtonMapper;
import com.hibo.cms.articlenew.model.BasArticleButton;
import com.hibo.cms.articlenew.service.IBasArticleButtonService;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年6月1日 下午4:04:26</p>
 * <p>类全名：com.hibo.cms.articlenew.service.impl.BasArticleButtonServiceImpl</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
@Service
@Transactional
public class BasArticleButtonServiceImpl implements IBasArticleButtonService {
	@Autowired
	private BasArticleButtonMapper babm;
	@Override
	public List<BasArticleButton> selectByArticleId(String articleId) {
		return babm.selectByArticleId(articleId);
	}
	
}
