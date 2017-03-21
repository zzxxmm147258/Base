package com.hibo.cms.user.service.operationcode.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.user.dao.OperationcodeMapper;
import com.hibo.cms.user.dao.RoleResourceMapper;
import com.hibo.cms.user.model.Operationcode;
import com.hibo.cms.user.model.RoleResourceKey;
import com.hibo.cms.user.service.operationcode.IOperationcodeService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月14日 下午4:38:26</p>
 * <p>类全名：com.hibo.cms.user.service.operationcode.impl.OperationcodeService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class OperationcodeService implements IOperationcodeService {

	@Autowired
	private OperationcodeMapper operationcodeMapper;
	@Autowired
	private RoleResourceMapper roleResourceMapper;

	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<Operationcode> selectAll(PageBounds pageBounds) {
		return operationcodeMapper.selectAll(pageBounds);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(Operationcode operationcode) {
		return operationcodeMapper.insert(operationcode);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int delete(String operaid) {
		return operationcodeMapper.delete(operaid);
	}

	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int update(Operationcode operationcode, String oldoperaid) {
		//修改rr关系中的对应字段
		RoleResourceKey rr = new RoleResourceKey();
		rr.setOperaid(operationcode.getOperaid());
		rr.setOperaname(operationcode.getOperaname());
		roleResourceMapper.updateByRoleResource(rr, null, null, oldoperaid);
		return operationcodeMapper.update(operationcode, oldoperaid);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public Operationcode select(String operaid) {
		return operationcodeMapper.select(operaid);
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Operationcode> selectAll() {
		return operationcodeMapper.selectAll();
	}

	@Override
	@RemoteAnno(value="BAS_READ")
	public List<Operationcode> selectFuzzy(Operationcode operationcode) {
		return operationcodeMapper.selectFuzzy(operationcode);
	}
	
}
