package com.hibo.cms.user.service.resource;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Resource;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月14日 下午3:03:51</p>
 * <p>类全名：com.hibo.cms.user.service.resource.IResourceService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IResourceService {
	
	/**
	 * 查出所有(不分页)
	 * @return
	 */
	List<Resource> selectAll();

	/**
	 * 查出所有(分页)
	 * @return
	 */
	public PageList<Resource> selectAll(PageBounds pageBounds);

	/**
	 * 插入一条
	 * @param resource
	 * @return
	 */
	public int insert(Resource resource);
	
	/**
	 * 根据id删除这一条
	 * @param id
	 * @return
	 */
	public int delete(String resourceid);
	
	/**
	 * 更新
	 * @param resource
	 * @param oldresourceid
	 * @return
	 */
	public int update(Resource resource,String oldresourceid);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Resource select(String resourceid);
	
	/**
	 * 模糊查找
	 * @return
	 */
	List<Resource> selectFuzzy(Resource resource);
	
}
