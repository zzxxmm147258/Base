package com.hibo.mem.member.service;

import com.hibo.mem.member.model.LoginInfoMem;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月29日 下午2:41:37</p>
 * <p>类全名：com.hibo.mm.member.service.ILogiInfoMemService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface ILoginInfoMemService {

	/**
	 * <p>功能：新增会员登录信息<p>
	 * <p>创建日期：2015年10月29日 下午2:42:29<p>
	 * <p>作者：曾小明<p>
	 * @param record
	 * @return
	 */
	 int insert(LoginInfoMem record);
	 
	 /**
	  * <p>功能：根据ID查询会员登录信息<p>
	  * <p>创建日期：2015年10月29日 下午2:43:11<p>
	  * <p>作者：曾小明<p>
	  * @param id
	  * @return
	  */
	 LoginInfoMem selectByPrimaryKey(String id);
	 
	 /**
	  * <p>功能：删除会员登录信息<p>
	  * <p>创建日期：2015年10月29日 下午2:44:37<p>
	  * <p>作者：曾小明<p>
	  * @param id
	  * @return
	  */
	 int deleteByPrimaryKey(String id);
	 
	 /**
	  * <p>功能：修改会员登录信息<p>
	  * <p>创建日期：2015年10月29日 下午2:45:36<p>
	  * <p>作者：曾小明<p>
	  * @param record
	  * @return
	  */
	 int updateByPrimaryKey(LoginInfoMem record);
	 
}
