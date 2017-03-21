package com.hibo.cms.newarticle.service.impl;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.newarticle.dao.ArticleButtonBasMapper;
import com.hibo.cms.newarticle.dao.NewArticleBasMapper;
import com.hibo.cms.newarticle.model.ArticleButtonBas;
import com.hibo.cms.newarticle.model.NewArticleBas;
import com.hibo.cms.newarticle.service.INewArticleBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月19日 上午10:26:05</p>
 * <p>类全名：com.hibo.cms.newarticle.service.impl.NewArticleBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class NewArticleBasServiceImpl implements INewArticleBasService{

	@Autowired
	private ArticleButtonBasMapper articleButtonBasMapper;
	
	@Autowired
	private NewArticleBasMapper newArticleBasMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return newArticleBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(NewArticleBas record) {
		return newArticleBasMapper.insert(record);
	}

	@Override
	@Transient
	public int insertSelective(NewArticleBas bean) {
		if(!StrUtil.isnull(bean.getBtnName01()) && !StrUtil.isnull(bean.getBtnUrl01())){
			ArticleButtonBas btn1=new ArticleButtonBas();
			btn1.setArticleId(bean.getId());
			btn1.setName(bean.getBtnName01());
			btn1.setUrl(bean.getBtnUrl01());
			btn1.setSort(1);
			articleButtonBasMapper.insertSelective(btn1);
		}
		if(!StrUtil.isnull(bean.getBtnName02()) && !StrUtil.isnull(bean.getBtnUrl02())){
			ArticleButtonBas btn2=new ArticleButtonBas();
			btn2.setArticleId(bean.getId());
			btn2.setName(bean.getBtnName02());
			btn2.setUrl(bean.getBtnUrl02());
			btn2.setSort(2);
			articleButtonBasMapper.insertSelective(btn2);
		}
		bean.setViewType(bean.getAttr1());
		return newArticleBasMapper.insertSelective(bean);
	}

	@Override
	public NewArticleBas selectByPrimaryKey(String id) {
		return newArticleBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(NewArticleBas record) {
		return newArticleBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transient
	public int updateByPrimaryKeyWithBLOBs(NewArticleBas bean) {
		int now=articleButtonBasMapper.deleteBymId(bean.getId());
		if(now>=0){
		if(!StrUtil.isnull(bean.getBtnName01()) && !StrUtil.isnull(bean.getBtnUrl01())){
			ArticleButtonBas btn1=new ArticleButtonBas();
			btn1.setArticleId(bean.getId());
			btn1.setName(bean.getBtnName01());
			btn1.setUrl(bean.getBtnUrl01());
			btn1.setSort(12);
			articleButtonBasMapper.insertSelective(btn1);
		}
		if(!StrUtil.isnull(bean.getBtnName02()) && !StrUtil.isnull(bean.getBtnUrl02())){
			ArticleButtonBas btn2=new ArticleButtonBas();
			btn2.setArticleId(bean.getId());
			btn2.setName(bean.getBtnName02());
			btn2.setUrl(bean.getBtnUrl02());
			btn2.setSort(2);
			articleButtonBasMapper.insertSelective(btn2);
		}
		}
		bean.setViewType(bean.getAttr1());
		return newArticleBasMapper.updateByPrimaryKeyWithBLOBs(bean);
	}

	@Override
	public int updateByPrimaryKey(NewArticleBas record) {
		return newArticleBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<NewArticleBas> selectPage(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return newArticleBasMapper.selectPage(wStr, pageBounds);
	}

	@Override
	public PageList<NewArticleBas> selectYicPage(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return newArticleBasMapper.selectYicPage(wStr, pageBounds);
	}
	
	@Override
	public List<NewArticleBas> selectByCategroy(String categoryId, String categorygId) {
		return newArticleBasMapper.selectByCategroy(categoryId, categorygId);
	}

	@Override
	public List<NewArticleBas> selectByPage(String categoryId, String categorygId, PageBounds pageBounds) {
		return newArticleBasMapper.selectByPage(categoryId, categorygId, pageBounds);
	}

	@Override
	public List<NewArticleBas> selectByCategorygId(String categorygId, PageBounds pageBounds) {
		return newArticleBasMapper.selectByCategorygId(categorygId, pageBounds);
	}

	@Override
	public List<NewArticleBas> selectByShopId(String shopId) {
		return newArticleBasMapper.selectByShopId(shopId);
	}

}
