package com.hibo.cms.language.service;

import com.hibo.cms.language.model.UserLanguageBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月24日 下午4:50:29</p>
 * <p>类全名：com.hibo.cms.language.service.IUserLanguageBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IUserLanguageBasService {

	int deleteByPrimaryKey(String userid);

    int insert(UserLanguageBas record);

    int insertSelective(UserLanguageBas record);

    UserLanguageBas selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(UserLanguageBas record);

    int updateByPrimaryKey(UserLanguageBas record);
}
