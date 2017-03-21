package com.hibo.cms.version.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.version.model.VersionControlBas;
import com.hibo.cms.version.service.IVersionControlBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月10日 上午10:14:51</p>
 * <p>类全名：com.hibo.cms.version.controller.VersionControlBasCommonController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/common/bas/version")
public class VersionControlBasCommonController {

	
	@Autowired
	private IVersionControlBasService versionControlBasService;
	
	
	/**
	 * <p>功能：查询最新版本<p>
	 * <p>创建日期：2016年5月9日 下午5:13:36<p>
	 * <p>作者：曾小明<p>
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/selectByType",method=RequestMethod.POST)
	@ResponseBody
	public String selectByType(String type){
		Message message=new Message();
		VersionControlBas bean=versionControlBasService.selectByType(type);
		if(bean!=null){
			message.setSuccess(true);
			message.setMessage("最新版本信息！");
			message.setDatas(bean);
		}else{
			message.setSuccess(false);
			message.setMessage("暂无更新！");
		}
		return JsonUtil.toJsonString(message);
	}
	
}
