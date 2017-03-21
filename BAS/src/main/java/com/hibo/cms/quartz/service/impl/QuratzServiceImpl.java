package com.hibo.cms.quartz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.ObjectId;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.quartz.dao.QuratzMapper;
import com.hibo.cms.quartz.model.Quartz;
import com.hibo.cms.quartz.service.QuratzService;
import com.hibo.cms.quartz.util.init.QuartzInit;
import com.hibo.cms.quartz.util.scheduler.ISysScheduler;
import com.hibo.cms.quartz.util.scheduler.Impl.SysSchedulerImpl;
import com.hibo.cms.quartz.util.scheduler.Impl.SysSchedulerManager;
import com.hibo.cms.quartz.util.tigger.TiggerStaticValue;

@Service
public class QuratzServiceImpl implements QuratzService{
	
	@Resource
	private QuratzMapper quratzMapper;
	
	@Override
	public String startQz(String qzname) throws SchedulerException {
		ISysScheduler scheduler = SysSchedulerManager.getISysScheduler(qzname);
		scheduler.start();
		return scheduler.isStarted()&&!scheduler.isStandby()?"open":"close";
	}

	@Override
	public String shutdownQz(String qzname) throws SchedulerException {
		ISysScheduler scheduler = SysSchedulerManager.getISysScheduler(qzname);
		scheduler.shutdown();
		return scheduler.isStarted()&&!scheduler.isStandby()?"open":"close";
	}
	
	@Override
	public String standbyQz(String qzname) throws SchedulerException {
		ISysScheduler scheduler = SysSchedulerManager.getISysScheduler(qzname);
		scheduler.standby();
		return scheduler.isStarted()&&!scheduler.isStandby()?"open":"close";
	}

	@Transactional
	@Override
	public String addQz(Quartz quartzModel) throws SchedulerException {
		Quartz qz = quratzMapper.selectByJobName(quartzModel.getSchename(),quartzModel.getJobname());
		if(null!=qz){
			throw new RuntimeException("已存在"+quartzModel.getJobname()+"任务!");
		}
		String mapValue = quartzModel.getJobparams();
		Map<Object, Object> map = null;
		String m = null;
		if(null!=mapValue){
			m = mapValue.trim();
			if(!"null".equals(m)&&!"".equals(m)){
				map = new HashMap<Object,Object>();
				String[] mapValues = mapValue.split(";");
				for (String value : mapValues) {
					String[] values = value.split(":");
					map.put(values[0].trim(), values[1].trim());
				}
			}else{
				m = "";
			}
		}
		quartzModel.setJobparams(m);
		String id = ObjectId.getUUId();
		quartzModel.setId(id);
		quartzModel.setTiggername(quartzModel.getJobname());
		if(null==quartzModel.getJobgroup()){
			quartzModel.setJobgroup(SysSchedulerImpl.JOB_GROUP_NAME);
		}
		if(null==quartzModel.getTiggergroup()){
			quartzModel.setTiggergroup(SysSchedulerImpl.TRIGGER_GROUP_NAME);
		}
		int utype= quartzModel.getUtype();
//		if(utype!=1||!isAdd){
//			quartzModel.setQtype(-1);
//			quartzModel.setUtype(0);
//		}
		int k = quratzMapper.addQz(quartzModel);
		if(k>0){
			boolean isAdd = QuartzInit.getparren(quartzModel.getQserver());
			// 是否是本服务器任务
			if(isAdd && utype==1){
				ISysScheduler scheduler = SysSchedulerManager.getISysScheduler(quartzModel.getSchename());
				scheduler.addJob(quartzModel.getJobname(), map, quartzModel.getExecuteclass(), quartzModel.getExecutetime());
				scheduler.start();
			}
		}else{
			throw new RuntimeException("添加失败!");
		}
		return null;
	}
	@Transactional
	@Override
	public String modifyQz(Quartz quartzModel) throws SchedulerException {
		String mapValue = quartzModel.getJobparams();
		Map<Object, Object> map = null;
		String m = null;
		if(null!=mapValue){
			m = mapValue.trim();
			if(!"null".equals(m)&&!"".equals(m)){
				map = new HashMap<Object,Object>();
				String[] mapValues = mapValue.split(";");
				for (String value : mapValues) {
					String[] values = value.split(":");
					map.put(values[0].trim(), values[1].trim());
				}
			}else{
				m = "";
			}
		}
		quartzModel.setJobparams(m);
		int utype= quartzModel.getUtype();
//		if(utype!=1){
//			quartzModel.setQtype(-1);
//		}
		int k = quratzMapper.updateQzById(quartzModel);
		if(k>0){
			boolean isAdd = QuartzInit.getparren(quartzModel.getQserver());
			ISysScheduler sche = SysSchedulerManager.getISysScheduler(quartzModel.getSchename());
			if(isAdd && utype==1){
				sche.modifyJobTime(quartzModel.getJobname(), map, quartzModel.getJobname(), quartzModel.getExecuteclass(), quartzModel.getExecutetime());
			}else{
				sche.removeTigJob(quartzModel.getJobname(), SysSchedulerImpl.JOB_GROUP_NAME, quartzModel.getJobname(), SysSchedulerImpl.TRIGGER_GROUP_NAME);
			}
		}else{
			throw new RuntimeException("更新失败!");
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> gerQzNames() throws SchedulerException {
		String qserver = DataConfig.getConfig("QUARTZ.KEY");
		List<Map<String,Object>> qznamesDb = quratzMapper.findAllQzNames(qserver);
		Map<String,Boolean> set = new HashMap<String,Boolean>();
		for (Map<String,Object> map : qznamesDb) {
			toUPMap(map);
			boolean ispass = "true".equals(map.get("isqserver"));
			if(ispass){
				String key = (String) map.get("schename");
				set.put(key, ispass);
				ISysScheduler scheduler = SysSchedulerManager.getISysScheduler(key);
				String color = scheduler.isStarted()&&!scheduler.isStandby()?"green":"red";
				map.put("status", color);
			}
		}
		Set<String> qznamesQz = SysSchedulerManager.getAllScheName();
		if(null==qznamesQz){
			qznamesQz = new HashSet<String>();
		}
		List<String> keyList = new ArrayList<String>();
		if(null!=qznamesQz&&qznamesQz.size()>0){
			for (String key : qznamesQz) {
				if(!set.containsKey(key)){
					if(!key.equals(QuartzInit.SYS_QZ_NAME)){
						keyList.add(key);
					}
				}
			}
		}
		for (String key : keyList) {
			SysSchedulerManager.removeScheduler(key);
		}
		return qznamesDb;
	}

	
	@SuppressWarnings("unused")
	private void toUPMap(Map<String,Object> map){
		String[] keys="schename,isqserver,iscount".split(",");
		for (String key : keys) {
			map.put(key, map.get(key)==null?map.get(key.toUpperCase()):map.get(key));
		}
	}
	
	
	@Override
	public Map<String, Object> getJobByQz(String qzname) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 获取任务
			List<Quartz> qzList = quratzMapper.findAllQzByName(qzname);
			ISysScheduler sched = SysSchedulerManager.getISysScheduler(qzname);
			for (Quartz quartz : qzList) {
				// 是否是本服务器任务
				boolean isAdd = QuartzInit.getparren(quartz.getQserver());
				// 是否在调度器内
				if (isAdd) {
					int status = 8;
					if(sched.isStarted()&&!sched.isStandby()){
						status = sched.getScheduler().getTriggerState(quartz.getTiggername(), quartz.getTiggergroup());
					}
					quartz.setQtype(status);
					
				}
				String key = qzname+"_"+quartz.getJobname();
				String msg = TiggerStaticValue.JOB_STATUS.get(quartz.getQtype());
				msg += "<br>总数："+StrUtil.obj2long(TiggerStaticValue.JOB_INFO.get(key+"_totle"),0);
				msg += "<br>异常："+StrUtil.obj2long(TiggerStaticValue.JOB_INFO.get(key+"_excep"),0);
				msg += "<br>时间："+StrUtil.obj2long(TiggerStaticValue.JOB_INFO.get(key+"_sec"),0)+"s";
				msg += "<br>异常："+TiggerStaticValue.JOB_INFO.get(key+"_excepMsg");
				quartz.setQtypename(msg);
				quartz.setDisabled(!isAdd);
			}
			String op = sched.isStarted() && !sched.isStandby() ? "open" : "close";
			map.put("type", op);
			map.put("list", qzList);
		} catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException(e);
		}
		return map;
	}
	@Transactional
	@Override
	public String removeJob(String id, String qzname, String jobName) throws SchedulerException {
		try {
			int k = quratzMapper.deleteQzById(id);
			if (k > 0) {
				ISysScheduler sche = SysSchedulerManager.getISysScheduler(qzname);
				sche.removeTigJob(jobName, SysSchedulerImpl.JOB_GROUP_NAME, jobName, SysSchedulerImpl.TRIGGER_GROUP_NAME);
			} else {
				throw new RuntimeException("删除失败!");
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
	@Transactional
	@Override
	public String pasueJob(String id, String qzname, String jobName) throws SchedulerException {
		int k = quratzMapper.updateStatusById(id, 1);
		if(k>0){
			SysSchedulerManager.getISysScheduler(qzname).pauseJob(jobName);
		}else{
			throw new RuntimeException("暂停失败!");
		}
		return null;
	}

	@Override
	public List<Quartz> findAllQzByName(String qzname) {
		return quratzMapper.findAllQzByName(qzname);
	}
	@Transactional
	@Override
	public String resumeJob(Quartz quartzModel) throws SchedulerException {
		Quartz quartz = new Quartz();
		quartz.setId(quartzModel.getId());
		quartz.setQtype(quartzModel.getQtype());
		quartz.setUtype(quartzModel.getUtype());
		int k = quratzMapper.updateQzById(quartz);
		if(k>0){
			ISysScheduler sche = SysSchedulerManager.getISysScheduler(quartzModel.getSchename());
			JobDetail job = sche.getJob(quartzModel.getJobname());
			if(null==job){
				sche.addJob2(quartzModel.getJobname(), quartzModel.getJobparams(), quartzModel.getJobname(), quartzModel.getExecuteclass(), quartzModel.getExecutetime());
			}else{
				sche.resumeJob(quartzModel.getJobname());
			}
		}else{
			throw new RuntimeException("重启失败!");
		}
		return null;
	}
}
