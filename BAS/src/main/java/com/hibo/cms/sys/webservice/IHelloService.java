package com.hibo.cms.sys.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-9 下午4:36:07</p>
 * <p>类全名：com.hibo.cms.webservice.IHelloService</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @version 1.0
 */
@WebService
public interface IHelloService {
	@WebMethod
	public void getHello(String str);
}
