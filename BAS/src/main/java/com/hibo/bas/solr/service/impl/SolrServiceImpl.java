package com.hibo.bas.solr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.hibo.bas.solr.model.Product;
import com.hibo.bas.solr.service.ISolrService;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月6日 下午1:56:00</p>
 * <p>类全名：com.hibo.bas.solr.service.impl.SolrServiceImpl</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
@Service("solrService")
@Transactional
public class SolrServiceImpl implements ISolrService {
//	客户端连接solr服务器
	@Resource(name="httpSolrServer")
	private HttpSolrServer httpSolrServer;
//	将对象添加到solr,如果存在，则更新
	@Override
	public void addOrUpdate(Product product) {
		SolrInputDocument doc = new SolrInputDocument();
//		主键
		doc.setField("id", product.getId());
//		自定义字段开始
		doc.setField("hiboName", product.getName());
		HashMap<String, Object> hashMap = product.getHashMap();
		Set<Entry<String, Object>> entrySet = hashMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			doc.setField(entry.getKey(), entry.getValue());
		}
//		自定义字段结束
		try {
			httpSolrServer.add(doc);
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
//	从solr中查询分页对象（所有）
	@Override
	public PageList<Product> findAll(PageBounds pageBounds) {
		SolrQuery solrQuery=new SolrQuery();
		solrQuery.set("q", "*:*");
		solrQuery.setStart(pageBounds.getOffset());
		solrQuery.setRows(pageBounds.getLimit());
		QueryResponse response=null;
		try {
			response = httpSolrServer.query(solrQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SolrDocumentList docs = response.getResults();
		ArrayList<Product> products=new ArrayList<>();
		long totalCount = docs.getNumFound();
		for (SolrDocument doc : docs) {
			Product product=new Product();
			 Map<String, Object> map = doc.getFieldValueMap();
			 HashMap<String,Object> map2=new HashMap<>();
			 Set<String> keySet = map.keySet();
			 for (String key : keySet) {
				if(key.equals("id")){
					product.setId((String) map.get(key));
				}else if(key.equals("hiboName")){
					product.setName((String) map.get(key));
				}else if(key.equals("_version_")){
					
				}else{
					map2.put(key, map.get(key));
				}
			}
			 product.setHashMap(map2);
			 products.add(product);
			}
			int page = pageBounds.getPage();
			int limit = pageBounds.getLimit();
			Paginator paginator=new Paginator(page, limit, (int) totalCount);
		PageList<Product> pageList=new PageList<>(products,paginator);
		return pageList;
	}
//	根据多条件查询分页对象,高亮显示关键字
	@Override
	public PageList<Product> findByKeyWords(String keyword,
			HashMap<String, Object> keywords, PageBounds pageBounds) {
		SolrQuery solrQuery=new SolrQuery();
		solrQuery.set("q", "hiboName:" + keyword);
		if (keywords!=null&&keywords.size()>0){
			Set<Entry<String, Object>> entrySet = keywords.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				solrQuery.addFilterQuery(entry.getKey()+":"+entry.getValue());
			}
		}
//		关键词高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("hiboName");
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		
//		
		solrQuery.setStart(pageBounds.getOffset());
		solrQuery.setRows(pageBounds.getLimit());
		QueryResponse response=null;
		try {
			 response = httpSolrServer.query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		Map<String, Map<String, List<String>>> highlighting 
			= response.getHighlighting();
			SolrDocumentList docs = response.getResults();
			ArrayList<Product> products=new ArrayList<>();
			long totalCount = docs.getNumFound();
			for (SolrDocument doc : docs) {
				Product product=new Product();
				 Map<String, Object> map = doc.getFieldValueMap();
				 HashMap<String,Object> map2=new HashMap<>();
				 Set<String> keySet = map.keySet();
				 for (String key : keySet) {
					if(key.equals("id")){
						product.setId((String) map.get(key));
					}else if(key.equals("hiboName")){
						Map<String, List<String>> map3 = highlighting.get((String) doc.get("id"));
						List<String> list = map3.get("hiboName");
						product.setName(list.get(0));
					}else if(key.equals("_version_")){
					}else{
						map2.put(key, map.get(key));
					}
				}
				 product.setHashMap(map2);
				 products.add(product);
			}
			int page = pageBounds.getPage();
			int limit = pageBounds.getLimit();
			Paginator paginator=new Paginator(page, limit, (int) totalCount);
			PageList<Product> pageList=new PageList<>(products,paginator);
		return pageList;
	}
//	根据id删除solr中数据
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		try {
			httpSolrServer.deleteById(id);
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	根据多个id批量删除solr中数据
	@Override
	public void deleteByIds(List<String> ids) {
		// TODO Auto-generated method stub
		try {
			httpSolrServer.deleteById(ids);
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
