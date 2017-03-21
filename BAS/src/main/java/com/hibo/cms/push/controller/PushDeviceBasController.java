package com.hibo.cms.push.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.push.model.PushDeviceBas;
import com.hibo.cms.push.service.IPushDeviceBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月8日 下午5:36:15</p>
 * <p>类全名：com.hibo.cms.push.controller.PushDeviceBasController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping(value = "/member")
public class PushDeviceBasController {
	
	@Autowired
	private IPushDeviceBasService pushDeviceService;

	/**
	 * <p>功能：添加用户相关设备ID </p>
	 * <p>创建日期：2015年12月24日 下午6:16:53</p>
	 * 作者：崔志敏
	 * @param model
	 * @param userId
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/push/addPushDevice", method = RequestMethod.POST)
	@ResponseBody
	public String addPushDevice(String userId, String deviceId, String flag) {
		Message message=new Message();
		userId=Envparam.getUserId();
		try{
			if (!StrUtil.isnull2(userId) && !StrUtil.isnull2(deviceId) && !StrUtil.isnull2(flag)) {
				PushDeviceBas bean = new PushDeviceBas();
				bean.setUserId(userId);
				bean.setDeviceId(deviceId);
				bean.setModifyDate(new Date());
				bean.setAttrName1(flag);
				int updateStatus = pushDeviceService.updatePushDevice(bean);
				if (updateStatus == 0) {
					bean.setCreateDate(new Date());
					pushDeviceService.addPushDevice(bean);
				} 
				message.setSuccess(true);
				message.setMessage("提交成功");
			} else {
				message.setSuccess(false);
				message.setMessage("提交失败");
			}
		}catch(Exception e){
			message.setSuccess(false);
			message.setMessage("提交失败");
			e.printStackTrace();
		}
		return JsonUtil.toJsonString(message);
	}
}
