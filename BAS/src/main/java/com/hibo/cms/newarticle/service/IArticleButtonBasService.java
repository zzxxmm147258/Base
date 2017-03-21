package com.hibo.cms.newarticle.service;

import java.util.List;

import com.hibo.cms.newarticle.model.ArticleButtonBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月19日 上午10:22:49</p>
 * <p>类全名：com.hibo.cms.newarticle.service.IArticleButtonBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IArticleButtonBasService {

	int deleteByPrimaryKey(String id);

    int insert(ArticleButtonBas record);

    int insertSelective(ArticleButtonBas record);

    ArticleButtonBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArticleButtonBas record);

    int updateByPrimaryKey(ArticleButtonBas record);
    
    int deleteBymId(String mId);
    
    List<ArticleButtonBas> selectBymId(String mId);
}
