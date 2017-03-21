package com.hibo.cms.user.service.user.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.shop.dao.ShopBasMapper;
import com.hibo.cms.shop.dao.UserShopBasMapper;
import com.hibo.cms.shop.model.ShopBas;
import com.hibo.cms.shop.model.UserShopBas;
import com.hibo.cms.shop.model.UserShopBasKey;
import com.hibo.cms.sys.shiro.util.PasswordHelper;
import com.hibo.cms.sys.utils.anno.RemoteAnno;
import com.hibo.cms.sys.utils.invoke.InvokeUtil;
import com.hibo.cms.user.dao.RoleUsersMapper;
import com.hibo.cms.user.dao.UserMapper;
import com.hibo.cms.user.model.Password;
import com.hibo.cms.user.model.RoleUsersKey;
import com.hibo.cms.user.model.User;
import com.hibo.cms.user.service.user.IUserService;
import com.hibo.cms.util.Envparam;

/**
 * <p>标题：用户服务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-7 上午9:33:53</p>
 * <p>类全名：com.hibo.cms.service.user.impl.UserService</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private RoleUsersMapper roleUsersMapper;
	@Autowired
	private UserShopBasMapper userShopBasMapper;
	@Autowired
	private ShopBasMapper shopBasMapper;
	
	private PasswordHelper passwordHelper;
	
	public PasswordHelper getPasswordHelper() {
		return passwordHelper;
	}
	@Autowired
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		this.passwordHelper = passwordHelper;
	}
	private UserMapper userMapper;
	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	@Override
	@RemoteAnno(value="BAS_AUTHEN,BAS_READ")
	public User selectUserByUserName(String userName) {
		System.err.println("查 User表，当前数据源："+userName+ " " +com.hibo.bas.dbutil.DataSource.getDataSource());
		return userMapper.queryUserByUserName(userName);
	}
	@Override
	@RemoteAnno(value="BAS_READ")
	public List<User> selectUserList() {
		return userMapper.selectUserList();
	}
	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insert(User user) {
		passwordHelper.encryptPassword(user);
		return userMapper.insert(user);
	}
	
	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int deleteByUserId(String userid) {
		userShopBasMapper.deleteByUserId(userid);		//删除用户-商户关联关系
		roleUsersMapper.deleteRoleIdByUserId(userid);
		int k = userMapper.deleteByUserId(userid);
		String sync = DataConfig.getConfig("BAS.ACTIVITI.SYNC");
		if("true".equals(sync)&&k>0){
			try {
				Object[] args = {userid};
				InvokeUtil.invokeFromBean("actUserServiceImpl", "delete", args );
			} catch (Exception e) {
				throw new RuntimeException("工作流用户同步失败:"+e.getMessage());
			}
		}
		return k;
	}
	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int changePassword(String password,String userid,String username){
		String[] ss = PasswordHelper.encryptPassword(password,username);
		return userMapper.changePassword(ss[0], ss[1], userid);
	}
	@Override
	@RemoteAnno(value="BAS_READ")
	public User selectByUserId(String userid) {
		return userMapper.selectByUserId(userid);
	}
	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int update(User user) {
		if(StrUtil.isnull(user.getShopid())){
			userShopBasMapper.deleteByUserId(user.getUserid());
		}else{
			UserShopBasKey userShopKey = new UserShopBas();
			userShopKey.setShopId(user.getShopid());
			userShopKey.setUserid(user.getUserid());
			UserShopBas userShop = userShopBasMapper.selectByPrimaryKey(userShopKey);
			if(null==userShop){
				userShopBasMapper.deleteByUserId(user.getUserid());
				ShopBas shop = shopBasMapper.selectByPrimaryKey(user.getShopid());
				userShop = new UserShopBas();
				userShop.setUserid(user.getUserid());
				userShop.setUsername(user.getUsername());
				userShop.setShopId(user.getShopid());
				userShop.setAvailabe(true);
				userShop.setShopname(shop.getShopname());
				userShop.setOperator(Envparam.getLoginInfo().getUsername());
				userShopBasMapper.insertSelective(userShop);
			}
		}
		if(user.getPassword() != null && user.getPassword()!=""){
			passwordHelper.encryptPassword(user);
		}else{
			user.setPassword(null); //用户修改信息时没有输入密码，则不修改密码
		}
		//修改用户和角色时，同时修改ru关系中的对应字段
		RoleUsersKey ru = new RoleUsersKey();
		ru.setUsername(user.getUsername());
		roleUsersMapper.updateByRoleUsers(ru, null, user.getUserid()); 
		int k = userMapper.updateByPrimaryKeySelective(user);
		String sync = DataConfig.getConfig("BAS.ACTIVITI.SYNC");
		if("true".equals(sync)&&k>0){
			try {
				Object[] args = {user.getUserid()};
				InvokeUtil.invokeFromBean("actUserServiceImpl", "update", args );
			} catch (Exception e) {
				throw new RuntimeException("工作流用户同步失败:"+e.getMessage());
			}
		}
		return k;
	}
	
	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int insertSelective(User record) {
		passwordHelper.encryptPassword(record);
		int k = userMapper.insertSelective(record);
		String sync = DataConfig.getConfig("BAS.ACTIVITI.SYNC");
		if("true".equals(sync)&&k>0){
			try {
				Object[] args = {record.getUserid()};
				InvokeUtil.invokeFromBean("actUserServiceImpl", "insert", args );
			} catch (Exception e) {
				throw new RuntimeException("工作流用户同步失败:"+e.getMessage());
			}
		}
		return k;
	}
	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int updateByPrimaryKeySelective(User user) {
		int k =  userMapper.updateByPrimaryKeySelective(user);
		String sync = DataConfig.getConfig("BAS.ACTIVITI.SYNC");
		if("true".equals(sync)&&k>0){
			try {
				Object[] args = {user.getUserid()};
				InvokeUtil.invokeFromBean("actUserServiceImpl", "update", args );
			} catch (Exception e) {
				throw new RuntimeException("工作流用户同步失败:"+e.getMessage());
			}
		}
		return k;
	}
	@Override
	@RemoteAnno(value="BAS_WRITE")
	public int changePayPass(String password, String userid, String username) {
		String[] ss = PasswordHelper.encryptPassword(password,username);
		return userMapper.changePayPass(ss[0], ss[1], userid);
	}
	@Override
	@RemoteAnno(value="BAS_WRITE")
	public String chgPassWord(User user,Password password) {
		if(!password.getNewPassword().equals(password.getNewPassword2())){
			return "您输入的新密码不一致！";
		}
		user = userMapper.selectByUserId(user.getUserid());
		//验证旧密码
		String p = PasswordHelper.getPasswordByPS(password.getOldPassword(), user.getUsername(), user.getSalt());
		if(!p.equals(user.getPassword())){
			return "旧密码输入错误！";
		}
		user.setPassword(password.getNewPassword());
		passwordHelper.encryptPassword(user);
		userMapper.changePassword(user.getSalt(), user.getPassword(), user.getUserid());
		return null;
	}
	@Override
	@RemoteAnno(value="BAS_READ")
	public PageList<User> selectByCondition(Map map,PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return userMapper.selectByCondition(wStr,pageBounds);
	}
	@Override
	@RemoteAnno(value="BAS_READ")
	public List<User> selectFuzzy(User user) {
		return userMapper.selectFuzzy(user);
	}
	
	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int insert(User user, String roleStr) {
		String[] roles = roleStr.split(";");	//根据";"拆分后的String数组
		String[] ro = null;						//根据":"拆分后的String数组
		List<RoleUsersKey> roleUserList = new ArrayList<RoleUsersKey>();
		for(int i=0;i<roles.length;i++){
			ro = roles[i].split(":");
			if(!StrUtil.isnull(ro[0]) && !StrUtil.isnull(ro[1])){
				RoleUsersKey roleUser = new RoleUsersKey();
				roleUser.setRoleid(ro[0]);
				roleUser.setRolename(ro[1]);
				roleUser.setAvailabe(true);
				roleUserList.add(roleUser);
			}
		}
		roleUsersMapper.insertRuList(user,roleUserList);
		if(!StrUtil.isnull(user.getShopid())){
			ShopBas shop = shopBasMapper.selectByPrimaryKey(user.getShopid());
			UserShopBas userShop = new UserShopBas();
			userShop.setUserid(user.getUserid());
			userShop.setUsername(user.getUsername());
			userShop.setShopId(user.getShopid());
			userShop.setAvailabe(true);
			userShop.setShopname(shop.getShopname());
			userShop.setOperator(Envparam.getLoginInfo().getUsername());
			userShopBasMapper.insertSelective(userShop);
		}
		passwordHelper.encryptPassword(user);
		int k = userMapper.insert(user);
		String sync = DataConfig.getConfig("BAS.ACTIVITI.SYNC");
		if("true".equals(sync)&&k>0){
			try {
				Object[] args = {user.getUserid()};
				InvokeUtil.invokeFromBean("actUserServiceImpl", "insert", args );
			} catch (Exception e) {
				throw new RuntimeException("工作流用户同步失败:"+e.getMessage());
			}
		}
		return k;
	}
	@Override
	@RemoteAnno(value="BAS_WRITE")
	@Transactional
	public int update(User user, String roleStr) {
		String[] roles = roleStr.split(";");	//根据";"拆分后的String数组
		String[] ro = null;						//根据":"拆分后的String数组
		List<RoleUsersKey> roleUserList = new ArrayList<RoleUsersKey>();
		for(int i=0;i<roles.length;i++){
			ro = roles[i].split(":");
			RoleUsersKey roleUser = new RoleUsersKey();
			roleUser.setRoleid(ro[0]);
			roleUser.setRolename(ro[1]);
			roleUser.setAvailabe(true);
			roleUserList.add(roleUser);
		}
		roleUsersMapper.deleteRoleIdByUserId(user.getUserid());
		roleUsersMapper.insertRuList(user,roleUserList);
		int k = userMapper.updateByPrimaryKeySelective(user);
		String sync = DataConfig.getConfig("BAS.ACTIVITI.SYNC");
		if("true".equals(sync)&&k>0){
			try {
				Object[] args = {user.getUserid()};
				InvokeUtil.invokeFromBean("actUserServiceImpl", "update", args );
			} catch (Exception e) {
				throw new RuntimeException("工作流用户同步失败:"+e.getMessage());
			}
		}
		return k;
	}
}
