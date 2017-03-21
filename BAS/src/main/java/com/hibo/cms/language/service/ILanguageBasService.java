package com.hibo.cms.language.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.language.model.LanguageBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年8月17日 上午10:26:52</p>
 * <p>类全名：com.hibo.cms.language.service.ILanguageBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface ILanguageBasService {

	
	int deleteByPrimaryKey(String id);

    int insert(LanguageBas record);

    int insertSelective(LanguageBas record);

    LanguageBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LanguageBas record);

    int updateByPrimaryKey(LanguageBas record);
    
    PageList<LanguageBas> selectByCondition(Map map,PageBounds pageBounds);
    
    List<LanguageBas> selectAll();
}
