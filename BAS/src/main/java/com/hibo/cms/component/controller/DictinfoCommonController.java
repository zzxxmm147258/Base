package com.hibo.cms.component.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月22日 下午3:35:22</p>
 * <p>类全名：com.hibo.cms.component.controller.DictinfoCommonController</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Controller
@RequestMapping("/common/dictinfo")
public class DictinfoCommonController {
	
	@Autowired
	private IDictinfoService dictinfoService;
	
	/**
	 * @功能:根据Dictid查出对应字典数据
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年3月22日 下午3:36:26
	 * @param dictid
	 * @param current
	 * @return
	 */
	@RequestMapping(value = "/findByDictid.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxFindByDictid(int dictid){
		List<Dictinfo> list = dictinfoService.selectByDictid(dictid);
		return JsonUtil.toJsonString(list);
	}
}
