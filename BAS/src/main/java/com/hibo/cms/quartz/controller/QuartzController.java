package com.hibo.cms.quartz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.cms.quartz.model.Quartz;
import com.hibo.cms.quartz.service.QuratzService;
import com.hibo.cms.quartz.util.tigger.TiggerStaticValue;
import com.hibo.cms.sys.utils.json.JsonUtil;

@Controller
@RequestMapping(value = "/admin/quartz")
public class QuartzController {
	@Resource
	private QuratzService quratzService;
	private static final Logger log = LoggerFactory.getLogger(QuartzController.class);

	@RequestMapping(value = "operation/{qzname}/{type}")
	@ResponseBody
	public String setQuartz(@PathVariable(value = "qzname") String qzname, @PathVariable(value = "type") String type, Quartz quartzmodel) {
		Map<String, Object> rs = new HashMap<String, Object>();
		boolean isSuccess = false;
		String result = "请求命令错误!";
		try {
			if ("start".equals(type)) {
				result = quratzService.startQz(qzname);
				rs.put("type", result);
				isSuccess = true;
				if (log.isInfoEnabled()) {
					log.info("开启了" + qzname + "任务管理!");
				}
			} else if ("standby".equals(type)) {
				result = quratzService.standbyQz(qzname);
				rs.put("type", result);
				isSuccess = true;
			} else if ("add".equals(type)) {
				result = quratzService.addQz(quartzmodel);
				isSuccess = true;
				if (log.isInfoEnabled()) {
					log.info("添加了" + quartzmodel.getJobname() + "任务!");
				}
			} else if ("modify".equals(type)) {
				result = quratzService.modifyQz(quartzmodel);
				isSuccess = true;
			} else if ("pasue".equals(type)) {
				result = quratzService.pasueJob(quartzmodel.getId(), qzname, quartzmodel.getJobname());
				isSuccess = true;
			}else if ("resume".equals(type)) {
					result = quratzService.resumeJob(quartzmodel);
					isSuccess = true;
					if (log.isInfoEnabled()) {
						log.info("添加了" + quartzmodel.getJobname() + "任务!");
					}
			}else if ("remove".equals(type)) {
				result = quratzService.removeJob(quartzmodel.getId(), qzname, quartzmodel.getJobname());
				isSuccess = true;
			}else if ("clear".equals(type)){
				String key = qzname+"_"+quartzmodel.getJobname();
				TiggerStaticValue.JOB_INFO.remove(key+"_totle");
				TiggerStaticValue.JOB_INFO.remove(key+"_excep");
				TiggerStaticValue.JOB_INFO.remove(key+"_sec");
				TiggerStaticValue.JOB_INFO.remove(key+"_excepMsg");
				isSuccess = true;
			}
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		}
		rs.put("result", result);
		rs.put("success", isSuccess);
		return JsonUtil.toJsonString(rs);
	}

	@RequestMapping(value = "operation/{qzname}")
	@ResponseBody
	public String getJobByQz(@PathVariable(value = "qzname") String qzname) {
		Map<String, Object> map = quratzService.getJobByQz(qzname);
		String json = JsonUtil.toJsonString(map);
		return json;
	}

	@RequestMapping(value = "/list")
	public String getQuartzNames(ModelMap modelMap) {
		try {
			List<Map<String,Object>> qznames = quratzService.gerQzNames();
			modelMap.addAttribute("qznames", qznames);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "quartz/setQz";
	}
}
