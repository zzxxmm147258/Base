package com.hibo.cms.articlenew.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.articlenew.dao.BasArticleButtonMapper;
import com.hibo.cms.articlenew.dao.BasArticleNewMapper;
import com.hibo.cms.articlenew.model.BasArticleButton;
import com.hibo.cms.articlenew.model.BasArticleNew;
import com.hibo.cms.articlenew.service.IBasArticleNewService;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月30日 下午2:36:14</p>
 * <p>类全名：com.hibo.cms.articlenew.service.impl.BasArticleNewServiceImpl</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
@Service
@Transactional
public class BasArticleNewServiceImpl implements IBasArticleNewService {
	@Autowired
	private BasArticleButtonMapper babm;
	@Autowired
	private BasArticleNewMapper banm;
	@Override
	public int saveOrUpdate(BasArticleNew ban) {
		
			try {
				int b1 = babm.deleteByArticleId(ban.getId(), 1);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("按钮1不存在，不需要删除");
			}
			try {
				int b2=babm.deleteByArticleId(ban.getId(), 2);
			} catch (Exception e) {
				System.out.println("按钮2不存在，不需要删除");
			}
		
//		处理按钮1
		if(!StrUtil.isnull(ban.getBtnName01())){
			BasArticleButton bab1=new BasArticleButton();
			bab1.setArticleId(ban.getId());
			bab1.setName(ban.getBtnName01());
			bab1.setUrl(ban.getBtnUrl01());
			bab1.setLocation(ban.getBtnLoc01());
			bab1.setIcon(ban.getBtnIconUrl01());
			bab1.setIconSmall(ban.getBtnIconSmall01());
			bab1.setSort(1);
			babm.insertSelective(bab1);
			ban.setBtnIcon01(null);
		}
//		处理按钮2
		if(!StrUtil.isnull(ban.getBtnName02())){
			BasArticleButton bab2=new BasArticleButton();
			bab2.setArticleId(ban.getId());
			bab2.setName(ban.getBtnName02());
			bab2.setUrl(ban.getBtnUrl02());
			bab2.setLocation(ban.getBtnLoc02());
			bab2.setIcon(ban.getBtnIconUrl02());
			bab2.setIconSmall(ban.getBtnIconSmall02());
			bab2.setSort(2);
			babm.insertSelective(bab2);
			ban.setBtnIcon02(null);
		}
		BasArticleNew selectByPrimaryKey = banm.selectByPrimaryKey(ban.getId());
		if(selectByPrimaryKey==null){
			return banm.insertSelective(ban);
		}else{
			return banm.updateByPrimaryKeySelective(ban);
		}
	}
	@Override
	public BasArticleNew selectById(String id) {
		return banm.selectByPrimaryKey(id);
	}
	@Override
	public PageList<BasArticleNew> selectPaginition(Map map,PageBounds pb) {
		int[] p = StrUtil.getPageLimit(String.valueOf(pb.getPage()), String.valueOf(pb.getLimit()), 10);
		Integer count = banm.selectCount(map);
		map.put("start", p[0]);
		map.put("limit", p[1]);
		List<BasArticleNew> list = banm.selectPaginition(map);
		return new PageList<BasArticleNew>(list,new Paginator(pb.getPage(), pb.getLimit(), count));
	}
	@Override
	public int deleteById(String id) {
		return banm.deleteByPrimaryKey(id);
	}

}
