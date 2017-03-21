package com.hibo.cms.user.service.shirolimit;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Shirolimit;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月29日 上午10:31:41</p>
 * <p>类全名：com.hibo.cms.user.service.shiro.IShiroService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IShirolimitService {

	/**
	 * 查出所有
	 * @return
	 */
	public PageList<Shirolimit> selectAll(PageBounds pageBounds);

	/**
	 * 插入一条
	 * @param shirolimit
	 * @return
	 */
	public int insert(Shirolimit shirolimit);
	
	/**
	 * 根据id删除这一条
	 * @param id
	 * @return
	 */
	public int delete(String id);
	
	/**
	 * 更新
	 * @param shirolimit
	 * @return
	 */
	public int update(Shirolimit shirolimit);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Shirolimit select(String id);
	
	public List<Shirolimit> selectAllList();
}
