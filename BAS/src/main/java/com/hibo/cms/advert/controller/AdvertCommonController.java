package com.hibo.cms.advert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.advert.model.AdvertBas;
import com.hibo.cms.advert.service.IAdvertBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月22日 下午6:09:16</p>
 * <p>类全名：com.hibo.cms.advert.controller.AdvertCommonController</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Controller
@RequestMapping("/common/advert")
public class AdvertCommonController {
	
	@Autowired
	private IAdvertBasService advertBasService;
	/**
	 * @功能:根据groupId查出对应列表
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月22日 下午6:11:47
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value="/selectByGroupId.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String ajaxSelectByGroupId(String adPosition,String page,String limit){
		int[] p = StrUtil.getPageLimit(page, limit, 4);
		List<AdvertBas> list = advertBasService.selectByGroupIdLimit(adPosition, p[0], p[1]);
		return JsonUtil.toJsonString(list);
	}
}
