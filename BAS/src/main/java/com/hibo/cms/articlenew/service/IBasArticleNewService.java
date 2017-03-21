package com.hibo.cms.articlenew.service;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.articlenew.model.BasArticleNew;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月30日 下午2:35:29</p>
 * <p>类全名：com.hibo.cms.articlenew.service.IBasArticleNewService</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
public interface IBasArticleNewService {
	int saveOrUpdate(BasArticleNew ban);
	BasArticleNew selectById(String id);
	PageList<BasArticleNew> selectPaginition(Map map,PageBounds pb);
	int deleteById(String id);
}
