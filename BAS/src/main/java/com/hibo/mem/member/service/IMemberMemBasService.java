package com.hibo.mem.member.service;

import com.hibo.cms.user.model.User;
import com.hibo.mem.member.model.BaseMem;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月27日 下午5:04:37</p>
 * <p>类全名：com.hibo.mm.member.service.IMemberMmService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IMemberMemBasService {
	
	/**
     * 根据用户名（手机号）查询用户
     * @param username
     * @return
     */
    User selectByUsername(String username,String utype);
    
    BaseMem selectBaseMem(String userid);
    
}
