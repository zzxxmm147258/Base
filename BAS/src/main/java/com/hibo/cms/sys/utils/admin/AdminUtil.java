package com.hibo.cms.sys.utils.admin;

import org.apache.commons.lang3.StringUtils;

import com.hibo.bas.util.DataConfig;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月24日 上午9:47:34</p>
 * <p>类全名：com.hibo.cms.sys.utils.admin.AdminUtil</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
public class AdminUtil {
	
	private static String[] Admins = null;
	static{
		String Admin = DataConfig.getConfig("Admin");
		if(!StringUtils.isEmpty(Admin)){
			String[] Adminp = Admin.split(",");
			Admins = new String[Adminp.length>1?Adminp.length:2];
			for (int i=0;i<Adminp.length;i++) {
					Admins[i] = Adminp[i];
			}
		}
	}
	public static boolean isAdmin(String username) {
		//是否超级用户登录
		boolean isAdmin = false;
		if (Admins!=null) {
			if(Admins[0].equals(username)){
				isAdmin = true;
			}
		}
		return isAdmin;
	}
	public static String[] getAdmins() {
		return Admins;
	}
}
