package com.hibo.cms.user.service.resource.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.user.dao.ResourceMapper;
import com.hibo.cms.user.dao.RoleResourceMapper;
import com.hibo.cms.user.model.Resource;
import com.hibo.cms.user.model.RoleResourceKey;
import com.hibo.cms.user.service.resource.IResourceService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月14日 下午3:06:06</p>
 * <p>类全名：com.hibo.cms.user.service.resource.impl.ResourceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class ResourceImpl implements IResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<Resource> selectAll(PageBounds pageBounds) {
		return resourceMapper.selectAll(pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(Resource resource) {
		return resourceMapper.insert(resource);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int delete(String resourceid) {
		//roleResourceMapper.deleteByResourceid(resourceid);
		return resourceMapper.delete(resourceid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int update(Resource resource, String oldresourceid) {
		//修改rr中的对应字段
		RoleResourceKey rr = new RoleResourceKey();
		rr.setResourceid(resource.getResourceid());
		rr.setResname(resource.getResname());
		roleResourceMapper.updateByRoleResource(rr, null, oldresourceid,null);
		return resourceMapper.update(resource, oldresourceid);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public Resource select(String resourceid) {
		return resourceMapper.select(resourceid);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Resource> selectAll() {
		return resourceMapper.selectAll();
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Resource> selectFuzzy(Resource resource) {
		return resourceMapper.selectFuzzy(resource);
	}

}
