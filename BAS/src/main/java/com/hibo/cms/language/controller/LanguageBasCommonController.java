package com.hibo.cms.language.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.cms.language.model.LanguageBas;
import com.hibo.cms.language.service.ILanguageBasService;
import com.hibo.cms.sys.cache.Utils.SysCacheManager;
import com.hibo.cms.sys.utils.json.JsonUtil;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2016年8月18日 下午6:45:42
 * </p>
 * <p>
 * 类全名：com.hibo.cms.language.controller.LanguageBasCommonController
 * </p>
 * 作者：曾小明 初审： 复审：
 */
@Controller
@RequestMapping("/common/language")
public class LanguageBasCommonController {

	@Autowired
	private ILanguageBasService languageBasService;

	@RequestMapping(value = "/selectLanguage.ajax", method = RequestMethod.GET)
	@ResponseBody
	public String selectLanguage() {
		Message message = new Message();
		String cacheParam = "Language";
		String acheKey = "LanguageKey";
		try {
			if (SysCacheManager.isExistsGlobalCache(cacheParam, acheKey)) {//去缓存
				message = (Message) SysCacheManager.getFromGlobalCache(cacheParam, acheKey);
			} else {
				List<LanguageBas> list = languageBasService.selectAll();
				if (list != null && list.size() > 0) {
					message.setSuccess(true);
					message.setDatas(list);
					message.setMessage("语言对照数据");
					SysCacheManager.putToGlobalCache(cacheParam, acheKey, message, 1800);
				} else {
					message.setMessage("没有数据!");
					message.setSuccess(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage("后台错误!");
			message.setSuccess(false);
		}

		return JsonUtil.toJsonString(message);
	}
}
