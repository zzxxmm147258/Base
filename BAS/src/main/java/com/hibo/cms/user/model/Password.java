package com.hibo.cms.user.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月10日 下午2:46:20</p>
 * <p>类全名：com.hibo.cms.user.model.Password</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public class Password {
	
	@NotEmpty(message="密码不能为空")
	private String oldPassword;
	
	@NotEmpty(message="密码不能为空")
	@Length(min=6,max=20,message="密码长度必须在6至20之间")
	private String newPassword;
	
	@NotEmpty(message="密码不能为空")
	@Length(min=6,max=20,message="密码长度必须在6至20之间")
	private String newPassword2;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

}
