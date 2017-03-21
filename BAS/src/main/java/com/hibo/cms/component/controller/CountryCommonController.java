package com.hibo.cms.component.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hibo.cms.component.service.country.ICountryBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月12日 上午9:26:58</p>
 * <p>类全名：com.hibo.cms.component.controller.CountryCommonController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/common/areabas")
public class CountryCommonController {

	@Autowired
	private ICountryBasService countryBasService;
	
	/**
	 * <p>功能：查询所有国家<p>
	 * <p>创建日期：2015年11月12日 上午9:35:10<p>
	 * <p>作者：曾小明<p>
	 * @return
	 */
    public   String getCountry(){
    	List<String> country=countryBasService.selectAll();
    	String json = JsonUtil.toJsonString(country);
		return json;
	}
}
