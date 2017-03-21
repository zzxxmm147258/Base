package com.hibo.cms.user.service.user;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Password;
import com.hibo.cms.user.model.User;

/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-7 上午9:33:29</p>
 * <p>类全名：com.hibo.cms.service.user.IUserService</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @version 1.0
 */
public interface IUserService {
	/**
	 * 根据用户ID查找用户
	 * @param userid
	 * @return
	 */
	User selectByUserId(String userid);
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	public User selectUserByUserName(String userName);
	/**
	 * 修改密码
	 * @param userId
	 * @param newPassword
	 */
	public int changePassword(String password,String userid,String username);
	
	/**
	 * <p>功能：重设支付密码<p>
	 * <p>创建日期：2015年9月21日 上午10:03:05<p>
	 * <p>作者：朱运松<p>
	 * @param password
	 * @param userid
	 * @param username
	 * @return
	 */
	public int changePayPass(String password,String userid,String username);
	
	/**
	 * 查询出所有用户
	 * @return
	 */
	public List<User> selectUserList();
	/**
	 * 添加一个用户
	 * @param user
	 * @return
	 */
	public int insert(User record);
	/**
	 * 根据用户ID删除用户
	 * @param userid
	 * @return
	 */
	public int deleteByUserId(String userid);
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	int update(User user);
	
	/**
	 * <p>功能：注册<p>
	 * <p>创建日期：2015年9月9日 上午9:41:50<p>
	 * <p>作者：朱运松<p>
	 * @param record
	 * @return
	 */
	public int insertSelective(User record);
	
	/**
	 * <p>功能：修改<p>
	 * <p>创建日期：2015年9月9日 上午10:16:31<p>
	 * <p>作者：朱运松<p>
	 * @param olduserid
	 * @param user
	 * @return
	 */
	int updateByPrimaryKeySelective(User user);
	
	/**
	 * 修改密码
	 * @param password
	 * @return
	 */
	public String chgPassWord(User user,Password password);

	/**
	 * 条件查找
	 * @param user
	 * @return
	 */
	PageList<User> selectByCondition(Map map,PageBounds pageBounds);
	
	/**
	 * 模糊查找
	 * @param user
	 * @return
	 */
	List<User> selectFuzzy(User user);
	
	/**
	 * <p>功能： 插入用户同时时设置角色</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年11月10日 上午9:31:51</p>
	 * @param record
	 * @param roleStr
	 * @return
	 */
	public int insert(User user,String roleStr);
	
	/**
	 * <p>功能： 更新用户同时时设置角色</p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年11月16日 上午11:28:14</p>
	 * @param user
	 * @param roleStr
	 * @return
	 */
	int update(User user,String roleStr);
}
