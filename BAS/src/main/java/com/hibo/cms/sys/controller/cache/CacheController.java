package com.hibo.cms.sys.controller.cache;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hibo.bas.util.DataConfig;
import com.hibo.cms.config.util.SysConfigUtil;
import com.hibo.cms.sys.cache.Utils.SysCacheManager;

/** <p>标题：设置缓存</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月2日 下午6:48:28</p>
 * <p>类全名：com.hibo.cms.sys.controller.cache.CacheController</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/admin/cache")
public class CacheController {

	@RequestMapping(value="/clear",method=RequestMethod.GET)
	public String clear(){
		return "cache/clear";
	}
	
	/**
	 * <p>功能：清理缓存 </p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年12月2日 下午7:23:32</p>
	 * @param name
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/clear",method=RequestMethod.POST)
	public String clear(String name, String key, boolean isConfig, Model model) {
		try{
		if (isConfig) {
			DataConfig.SetServerConfigNull();
			SysConfigUtil.setSysConfigNull();
		} else {
			model.addAttribute("name", name);
			model.addAttribute("key", key);
			if (null == name || "".equals(name) || null == key || "".equals(key)) {
				model.addAttribute("msg", "name或key都不能为空！");
				return "cache/clear";
			}
			boolean b = SysCacheManager.isExistsGlobalCache(name, key);
			if (!b) {
				model.addAttribute("msg", "该缓存不存在！");
				return "cache/clear";
			} else {
				SysCacheManager.removeToGlobalCache(name, key);
			}
			
		}
			model.addAttribute("msg", "缓存清理成功！");
		}catch(Exception e){
			model.addAttribute("msg", "缓存清理失败 ");
			model.addAttribute("error",e.getMessage());
		}
		return "cache/clear";
	}
	
}
