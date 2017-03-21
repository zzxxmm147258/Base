package com.hibo.mem.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.user.model.User;
import com.hibo.mem.member.dao.MemberMemBasMapper;
import com.hibo.mem.member.model.BaseMem;
import com.hibo.mem.member.service.IMemberMemBasService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月27日 下午5:05:17</p>
 * <p>类全名：com.hibo.mm.member.service.impl.MemberMmServicesImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class MemberMemBasServiceImpl implements IMemberMemBasService{

	@Autowired
	private MemberMemBasMapper memberMemBasMapper;
	
	@Override
	public User selectByUsername(String username,String utype) {
		return memberMemBasMapper.selectByUsername(username,utype);
	}
	
	@Override
	public BaseMem selectBaseMem(String userid) {
		return memberMemBasMapper.selectBaseMem(userid);
	}
}
