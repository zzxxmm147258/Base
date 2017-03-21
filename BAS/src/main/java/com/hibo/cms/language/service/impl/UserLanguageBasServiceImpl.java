package com.hibo.cms.language.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.language.dao.UserLanguageBasMapper;
import com.hibo.cms.language.model.UserLanguageBas;
import com.hibo.cms.language.service.IUserLanguageBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月24日 下午4:50:42</p>
 * <p>类全名：com.hibo.cms.language.service.impl.UserLanguageBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class UserLanguageBasServiceImpl implements IUserLanguageBasService{

	@Autowired
	private UserLanguageBasMapper userLanguageBasMapper;
	
	@Override
	public int deleteByPrimaryKey(String userid) {
		return userLanguageBasMapper.deleteByPrimaryKey(userid);
	}

	@Override
	public int insert(UserLanguageBas record) {
		return userLanguageBasMapper.insert(record);
	}

	@Override
	public int insertSelective(UserLanguageBas record) {
		return userLanguageBasMapper.insertSelective(record);
	}

	@Override
	public UserLanguageBas selectByPrimaryKey(String userid) {
		return userLanguageBasMapper.selectByPrimaryKey(userid);
	}

	@Override
	public int updateByPrimaryKeySelective(UserLanguageBas record) {
		return userLanguageBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserLanguageBas record) {
		return userLanguageBasMapper.updateByPrimaryKey(record);
	}

}
