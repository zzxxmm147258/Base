package com.hibo.cms.sys.utils.gci;

import java.util.HashMap;
import java.util.Map;

import com.hibo.cms.sys.utils.invoke.InvokeUtil;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月24日 下午12:05:43</p>
 * <p>类全名：com.hibo.cms.sys.utils.gci.GicUtil</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class GicUtil {
	
	/**
	 * @功能:通过反射调用程序
	 * @描述:
	 * @作者:周雷
	 * @时间:2015年11月24日 下午12:05:43
	 * @param program01 类名参数  com.hibo.cms.sys.utils.gci.GicUtil ? A=1 & B=2
	 * @param params
	 * @param resultMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> runProgram(String program01, Map<String, Object> params, Map<String, Object> resultMap) throws Exception {
		GicContext context = new GicContext();
		context.setRequestParamsMap(params);
		context.setResultMap(resultMap);
		Map<String,String> map = new HashMap<String,String>();
		if(program01.indexOf('?')>=0){
			String[] program01s = program01.split("\\?");
			program01 = program01s[0];
			String param = program01s[1];
			String[] newparams = param.split("&");
			for (String keyValue : newparams) {
				int p = keyValue.indexOf('=');
				map.put(keyValue.substring(0, p), keyValue.substring(p+1));
			}
		}
		context.setParamsMap(map);
		Object[] args = {context};
		resultMap = (Map<String, Object>) InvokeUtil.invokeNewInstance(program01, "excute", args);
		return resultMap;
	}
}
