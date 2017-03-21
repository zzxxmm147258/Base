package com.hibo.cms.sys.controller.remote;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.spring.SpringContextUtil;
import com.hibo.cms.sys.utils.invoke.InvokeUtil;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.sys.utils.remote.RemoteInteractionUtil;
import com.hibo.cms.sys.utils.serialize.SerializeUtils;
import com.hibo.cms.util.Envparam;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月26日 下午12:32:51</p>
 * <p>类全名：com.hibo.cms.sys.controller.remote.RemoteInteractionContorller</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping(value = "/remoteinteraction")
public class RemoteInteractionContorller {
	
	/**
	 * @param beanName bean的名字
	 * @param methodName  方法的名字
	 * @param params 请求参数
	 * @return
	 */
	

/*	@RequestMapping(value = "/{beanName}/{methodName}", method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public <V> String remoteJson(HttpServletRequest request,@PathVariable(value = "beanName") String beanName, @PathVariable(value = "methodName") String methodName,@RequestParam(value="sParaTemp")String sParaTemp) {
		Map<String, Object> param = SerializeUtils.deserialize(sParaTemp);
		String conno = (String) param.remove("conno");
		String remoteTime = (String) param.remove("remoteTime");
		String remoterandom = (String) param.remove("remoterandom");
		String msgSignature = (String) param.remove("msgSignature");
		String ip = request.getRemoteAddr();
		Map<String, Object> map = remoteValue(beanName, methodName, param, conno, remoteTime, remoterandom, msgSignature,ip);
		return JsonUtil.toJsonString(map);
	}*/
	
	@RequestMapping(value = "/{beanName}/{methodName}/{remoteTime}", method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public <V> String remoteJson2(HttpServletRequest request,@PathVariable(value = "beanName") String beanName, @PathVariable(value = "methodName") String methodName,@RequestParam(value="sParaTemp")String sParaTemp) {
		Map<String, Object> param = SerializeUtils.deserialize(sParaTemp);
		String conno = (String) param.remove("conno");
		String remoteTime = (String) param.remove("remoteTime");
		String remoterandom = (String) param.remove("remoterandom");
		String msgSignature = (String) param.remove("msgSignature");
		Object[] args = (Object[]) param.remove("params");
		String ip = request.getRemoteAddr();
		Map<String, Object> map = remoteValue(beanName, methodName, args, conno, remoteTime, remoterandom, msgSignature,ip);
		return JsonUtil.toJsonString(map);
	}
	
	/**
	 * @功能:远程调用方法
	 * @作者:周雷
	 * @时间:2015年12月7日 下午8:34:31
	 * @param beanName bean名称
	 * @param methodName 方法名
	 * @param params 参数
	 * @param conno 数据库加密数据编号
	 * @param remoteTime 时间戳
	 * @param remoterandom 随机数
	 * @param msgSignature 密文
	 * @param ip
	 * @return
	 */
	private Map<String, Object> remoteValue(String beanName, String methodName, Object[] params, String conno, String remoteTime, String remoterandom, String msgSignature,String ip) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		boolean isSuccess = false;
		Object obj="凭证错误";
		try {
			
			Session session = Envparam.getSession();
			// 验证凭证
			boolean ispassed = RemoteInteractionUtil.isAcrossed(conno, remoteTime, remoterandom, msgSignature,ip);
			if (ispassed&&null!=session) {
				obj = InvokeUtil.invokeFromBean(SpringContextUtil.getBean(beanName), methodName, params);
				isSuccess = true;
			}
		} catch (Exception e) {
			obj = e.getMessage();
		}
		reMap.put("success", isSuccess);
		reMap.put("result", SerializeUtils.serialize(obj));
		return reMap;
	}
	
	/**
	 * @功能:远程调用方法(废弃)
	 * @作者:周雷
	 * @时间:2015年12月7日 下午8:33:31
	 * @param beanName
	 * @param methodName
	 * @param params
	 * @param conno
	 * @param remoteTime
	 * @param remoterandom
	 * @param msgSignature
	 * @param ip
	 * @return
	 */
/*	private Map<String, Object> remoteValue(String beanName, String methodName, Map<String, Object> params, String conno, String remoteTime, String remoterandom, String msgSignature,String ip) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		boolean isSuccess = false;
		Object obj="凭证错误";
		try {
			Session session = Envparam.getSession();
			// 验证凭证
			boolean ispassed = RemoteInteractionUtil.isAcrossed(conno, remoteTime, remoterandom, msgSignature,ip);
			if (ispassed&&null!=session) {
				obj = InvokeUtil.getValueFromMethod(SpringContextUtil.getBean(beanName), methodName, params);
				isSuccess = true;
			}
		} catch (Exception e) {
			obj = e.getMessage();
		}
		reMap.put("success", isSuccess);
		reMap.put("result", SerializeUtils.serialize(obj));
		return reMap;
	}*/
}
