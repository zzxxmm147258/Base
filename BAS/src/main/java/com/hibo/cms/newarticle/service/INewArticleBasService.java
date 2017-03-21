package com.hibo.cms.newarticle.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.newarticle.model.NewArticleBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月19日 上午10:22:23</p>
 * <p>类全名：com.hibo.cms.newarticle.service.INewArticleBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface INewArticleBasService {
	
	int deleteByPrimaryKey(String id);

    int insert(NewArticleBas record);

    int insertSelective(NewArticleBas record);

    NewArticleBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NewArticleBas record);

    int updateByPrimaryKeyWithBLOBs(NewArticleBas record);

    int updateByPrimaryKey(NewArticleBas record);
    
    PageList<NewArticleBas> selectPage(Map map,PageBounds pageBounds);
    
    PageList<NewArticleBas> selectYicPage(Map map,PageBounds pageBounds);
    
    List<NewArticleBas> selectByCategroy(@Param("categoryId")String categoryId,@Param("categorygId")String categorygId);

    List<NewArticleBas> selectByPage(@Param("categoryId")String categoryId,@Param("categorygId")String categorygId,PageBounds pageBounds);

    List<NewArticleBas> selectByCategorygId(@Param("categorygId")String categorygId,PageBounds pageBounds);

    List<NewArticleBas> selectByShopId(@Param("shopId")String shopId);
}
