package com.hibo.cms.article.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.article.model.BasArticleMain;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月1日 下午2:54:59</p>
 * <p>类全名：com.hibo.cms.article.service.IBasArticleMainService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IBasArticleMainService {
   
	int deleteByPrimaryKey(String id);

    int insert(BasArticleMain record);

    int insertSelective(BasArticleMain record);

    BasArticleMain selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasArticleMain record);

    int updateByPrimaryKey(BasArticleMain record);
    
    PageList<BasArticleMain> selectPage(Map map,PageBounds pageBounds);
    
    List<BasArticleMain> selectAll();
    
    List<BasArticleMain> selectByAll(Map map);
    
    List<BasArticleMain> selectDic(String dic);
}
