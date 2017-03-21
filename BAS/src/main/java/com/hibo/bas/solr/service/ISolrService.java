package com.hibo.bas.solr.service;

import java.util.HashMap;
import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.solr.model.Product;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月6日 下午1:17:30</p>
 * <p>类全名：com.hibo.bas.solr.service.ISolrService</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
public interface ISolrService {
//	将对象添加到solr,如果存在，则更新
	public void addOrUpdate(Product product);
//	从solr中查询分页对象（所有）
	public  PageList<Product> findAll(PageBounds pageBounds);
//	根据多条件查询分页对象
	public  PageList<Product> findByKeyWords(String keyword,HashMap<String,Object> keywords,PageBounds pageBounds);
//	根据id删除solr中数据
	public void deleteById(String id);
//	根据多个id批量删除solr中数据
	public void deleteByIds(List<String> ids);
}
