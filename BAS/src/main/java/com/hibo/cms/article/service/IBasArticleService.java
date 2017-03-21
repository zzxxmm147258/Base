package com.hibo.cms.article.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.article.model.BasArticle;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年2月29日 下午6:41:41</p>
 * <p>类全名：com.hibo.cms.article.service.IBasArticleService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IBasArticleService {
    
	int deleteByPrimaryKey(String id);

    int insert(BasArticle record);

    int insertSelective(BasArticle record);

    BasArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasArticle record);

    int updateByPrimaryKeyWithBLOBs(BasArticle record);

    int updateByPrimaryKey(BasArticle record);
    
    PageList<BasArticle> selectPage(Map map,PageBounds pageBounds);
    
    BasArticle selectByCategroy(String category,String articleCategory);
    
    /**
     * <p>功能：根据主表查询字表<p>
     * <p>创建日期：2016年3月1日 下午3:53:16<p>
     * <p>作者：曾小明<p>
     * @param articleMainId
     * @return
     */
    List<BasArticle> selectArticleMainId(String articleMainId);
    
    List<BasArticle> selectBasArticle(Map map);
    
    /**
     * <p>功能：根据这四个条件模糊查询<p>
     * <p>创建日期：2016年3月2日 上午9:44:50<p>
     * <p>作者：曾小明<p>
     * @param title
     * @param author
     * @param datefrom
     * @param dateto
     * @return
     */
    List<BasArticle> selectBasArticle(String title,String author,String datefrom,String dateto,String articleCategory,String category);

    
    /**
     * <p>功能：根据主表查询字表<p>
     * <p>创建日期：2016年3月1日 下午3:53:16<p>
     * <p>作者：曾小明<p>
     * @param articleMainId
     * @return
     */
    List<BasArticle> selectmId(String articleMainId);
    /**
	 * <p>功能：查询分类的置顶文章
	 * 查询条件：已发布，有效，在时间范围内，且是置顶的文章<p>
	 * <p>创建日期：2016年3月2日 下午2:20:55<p>
	 * <p>作者：曾小明<p>
	 * @param articleCategory
	 * @return
	 */
    List<BasArticle> selectByIsTop(String articleCategory,String category);
    
    /**
     * <p>功能：根据分类查询文章列表<p>
     * <p>创建日期：2016年3月2日 下午2:53:35<p>
     * <p>作者：曾小明<p>
     * @param articleCategory
     * @return
     */
    List<BasArticle> selectByArticle(String articleCategory,String category,String excCategory,int page, int limit);
 
    /**
     * <p>功能：楼盘文章<p>
     * <p>创建日期：2016年3月21日 下午8:10:16<p>
     * <p>作者：曾小明<p>
     * @param title
     * @param articleCategory
     * @param matitle
     * @return
     */
    List<BasArticle> selectArticle(String title,String articleCategory,String matitle);


    BasArticle selectByBuildingId(String buildingId);
    
    /**
     * <p>功能：查询惠民广告<p>
     * <p>创建日期：2016年3月2日 下午2:53:35<p>
     * <p>作者：曾小明<p>
     * @param articleCategory
     * @return
     */
    List<BasArticle> selectByAd(String articleCategory,int page, int limit);
    
    
    /**
     * <p>功能：查询惠民广告<p>
     * <p>创建日期：2016年3月2日 下午2:53:35<p>
     * <p>作者：曾小明<p>
     * @param articleCategory
     * @return
     */
    List<BasArticle> selectByGroupId(String articleCategory,int page, int limit);
}
