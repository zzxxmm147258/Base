package com.hibo.cms.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.article.model.BasArticle;

public interface BasArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(BasArticle record);

    int insertSelective(BasArticle record);

    BasArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasArticle record);

    int updateByPrimaryKeyWithBLOBs(BasArticle record);

    int updateByPrimaryKey(BasArticle record);
    
    PageList<BasArticle> selectPage(@Param("wStr")String wStr,PageBounds pageBounds);
    
    List<BasArticle> selectAll(@Param("wStr")String wStr);
    
    BasArticle selectByCategroy(@Param("category")String category,@Param("articleCategory")String articleCategory);
    /**
     * <p>功能：根据主表查询字表<p>
     * <p>创建日期：2016年3月1日 下午3:53:16<p>
     * <p>作者：曾小明<p>
     * @param articleMainId
     * @return
     */
    List<BasArticle> selectArticleMainId(String articleMainId);
    
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
    List<BasArticle> selectBasArticle(@Param("title")String title,@Param("author")String author,
    		@Param("datefrom")String datefrom,@Param("dateto")String dateto,@Param("articleCategory")String articleCategory,@Param("category")String category);

    /**
     * <p>功能：查询字表<p>
     * <p>创建日期：2016年3月2日 上午11:04:25<p>
     * <p>作者：曾小明<p>
     * @param articleMainId
     * @return
     */
    List<BasArticle> selectmId(String articleMainId);
    
    /**
     * <p>功能：根据类别查询置顶的文章<p>
     * <p>创建日期：2016年3月2日 下午2:25:03<p>
     * <p>作者：曾小明<p>
     * @param articleCategory
     * @return
     */
    List<BasArticle> selectByIsTop(@Param("articleCategory")String articleCategory,@Param("category")String category);
    
    /**
     * <p>功能：根据分类查询文章列表<p>
     * <p>创建日期：2016年3月2日 下午2:53:35<p>
     * <p>作者：曾小明<p>
     * @param articleCategory
     * @return
     */
    List<BasArticle> selectByArticle(@Param("articleCategory")String articleCategory,@Param("category")String category,@Param("excCategory")String excCategory,@Param("page")int page, @Param("limit")int limit);
    
    
    List<BasArticle> selectArticle(@Param("title")String title,@Param("articleCategory")String articleCategory,@Param("matitle")String matitle);

    
    BasArticle selectByBuildingId(String buildingId);
    
    /**
     * <p>功能：查询惠民广告<p>
     * <p>创建日期：2016年3月2日 下午2:53:35<p>
     * <p>作者：曾小明<p>
     * @param articleCategory
     * @return
     */
    List<BasArticle> selectByAd(@Param("articleCategory")String articleCategory,@Param("page")int page, @Param("limit")int limit);
    
    
    /**
     * <p>功能：查询首页广告<p>
     * <p>创建日期：2016年3月2日 下午2:53:35<p>
     * <p>作者：曾小明<p>
     * @param articleCategory
     * @return
     */
    List<BasArticle> selectByGroupId(@Param("articleCategory")String articleCategory,@Param("page")int page, @Param("limit")int limit);

}