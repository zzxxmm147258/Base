package com.hibo.cms.user.service.shirolimit.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.sys.shiro.util.SysShiroFilterFactoryBean;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.user.dao.ShirolimitMapper;
import com.hibo.cms.user.model.Shirolimit;
import com.hibo.cms.user.service.shirolimit.IShirolimitService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月29日 上午10:32:55</p>
 * <p>类全名：com.hibo.cms.user.service.shiro.impl.ShiroServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class ShirolimitServiceImpl implements IShirolimitService {

	@Autowired
	private ShirolimitMapper shirolimitMapper;

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<Shirolimit> selectAll(PageBounds pageBounds) {
		return shirolimitMapper.selectAll(pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(Shirolimit shirolimit) {
		int k = shirolimitMapper.insert(shirolimit);
		//重置shiro权限
		SysShiroFilterFactoryBean.initFilterChainManager();
		return k;
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int delete(String id) {
		int k = shirolimitMapper.delete(id);
		//重置shiro权限
		SysShiroFilterFactoryBean.initFilterChainManager();
		return k;
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int update(Shirolimit shirolimit) {
		int k = shirolimitMapper.update(shirolimit);
		//重置shiro权限
		SysShiroFilterFactoryBean.initFilterChainManager();
		return k;
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public Shirolimit select(String id) {
		return shirolimitMapper.select(id);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Shirolimit> selectAllList() {
		return shirolimitMapper.selectAllList();
	}
	
}
