package com.hibo.cms.language.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.language.dao.LanguageBasMapper;
import com.hibo.cms.language.model.LanguageBas;
import com.hibo.cms.language.service.ILanguageBasService;
import com.hibo.cms.lims.QueryFilterBuilder;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年8月17日 上午10:27:16</p>
 * <p>类全名：com.hibo.cms.language.service.impl.LanguageBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class LanguageBasServiceImpl implements ILanguageBasService{

	@Autowired
	private LanguageBasMapper languageBasMapper;
	@Override
	public int deleteByPrimaryKey(String id) {
		return languageBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LanguageBas record) {
		return languageBasMapper.insert(record);
	}

	@Override
	public int insertSelective(LanguageBas record) {
		return languageBasMapper.insertSelective(record);
	}

	@Override
	public LanguageBas selectByPrimaryKey(String id) {
		return languageBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LanguageBas record) {
		return languageBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LanguageBas record) {
		return languageBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<LanguageBas> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return languageBasMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	public List<LanguageBas> selectAll() {
		return languageBasMapper.selectAll();
	}

}
