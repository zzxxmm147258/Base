package com.hibo.cms.article.service;

import java.util.List;

import com.hibo.cms.article.model.BasArticleRelation;
import com.hibo.cms.article.model.BasArticleRelationKey;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月1日 下午3:01:11</p>
 * <p>类全名：com.hibo.cms.article.service.IBasArticleRelationService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IBasArticleRelationService {
      
	int deleteByPrimaryKey(BasArticleRelationKey key);

    int insert(BasArticleRelation record);

    int insertSelective(BasArticleRelation record);

    BasArticleRelation selectByPrimaryKey(BasArticleRelationKey key);

    int updateByPrimaryKeySelective(BasArticleRelation record);

    int updateByPrimaryKey(BasArticleRelation record);
    
    List<BasArticleRelation> selectAll();
    
    /**
     * <p>功能：根据主表查询<p>
     * <p>创建日期：2016年3月1日 下午3:04:51<p>
     * <p>作者：曾小明<p>
     * @param articleMainId
     * @return
     */
    List<BasArticleRelation> selectArticleMainId(String articleMainId);
    /**
     * <p>功能：根据父表删除字表<p>
     * <p>创建日期：2016年3月1日 下午3:17:26<p>
     * <p>作者：曾小明<p>
     * @param articleMainId
     * @return
     */
    int deleteArticleMainId(String articleMainId);
}
