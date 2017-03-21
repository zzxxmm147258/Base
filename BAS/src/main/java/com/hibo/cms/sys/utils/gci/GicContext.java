package com.hibo.cms.sys.utils.gci;

import java.io.Serializable;
import java.util.Map;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月20日 下午5:02:22</p>
 * <p>类全名：com.hibo.cms.sys.utils.gci.GicContext</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class GicContext implements Serializable{
	private static final long serialVersionUID = -5115785475958498328L;
	/**请求参数*/
	private Map<String,Object> requestParamsMap;
	/**结果集参数*/
	private Map<String,Object> resultMap;
	/**配置参数*/
	private Map<String,String> paramsMap;
	
	
	public Map<String, Object> getRequestParamsMap() {
		return requestParamsMap;
	}
	public void setRequestParamsMap(Map<String, Object> requestParamsMap) {
		this.requestParamsMap = requestParamsMap;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	public Map<String, String> getParamsMap() {
		return paramsMap;
	}
	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}
	
}
