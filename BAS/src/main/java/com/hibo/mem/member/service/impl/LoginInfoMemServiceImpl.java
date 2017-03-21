package com.hibo.mem.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.mem.member.dao.LoginInfoMemMapper;
import com.hibo.mem.member.model.LoginInfoMem;
import com.hibo.mem.member.service.ILoginInfoMemService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 下午2:46:10</p>
 * <p>类全名：com.hibo.mem.member.service.impl.LoginInfoMemServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class LoginInfoMemServiceImpl implements ILoginInfoMemService{

	@Autowired
	private LoginInfoMemMapper loginInfoMemMapper;
	
	@Override
	@RemoteAnno("BAS_WRITE,BAS_AUTHEN,MEM_AUTHEN")
	public int insert(LoginInfoMem record) {
		return loginInfoMemMapper.insertSelective(record);
	}

	@Override
	@RemoteAnno("BAS_READ")
	public LoginInfoMem selectByPrimaryKey(String id) {
		return loginInfoMemMapper.selectByPrimaryKey(id);
	}

	@Override
	@RemoteAnno("BAS_WRITE")
	public int deleteByPrimaryKey(String id) {
		return loginInfoMemMapper.deleteByPrimaryKey(id);
	}

	@Override
	@RemoteAnno("BAS_WRITE")
	public int updateByPrimaryKey(LoginInfoMem record) {
		return loginInfoMemMapper.updateByPrimaryKeySelective(record);
	}

}
