package com.hibo.cms.quartz.util.scheduler.Impl;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.hibo.cms.quartz.util.scheduler.ISysScheduler;
import com.hibo.cms.sys.utils.invoke.InvokeUtil;

public class SysSchedulerImpl implements ISysScheduler{
	// 创建一个Scheduler
	private  Scheduler sched;
	// 任务要清单组名
	public  static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	// 定时器组名
	public  static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	public SysSchedulerImpl(Scheduler sched) {
		this.sched = sched;
	}

	public  void addJob(String jobName, Class<?> jobClass, String time) {
		this.addJob(jobName, null, jobClass, time);
	}

	public  void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<?> jobClass, String time) {
		this.addJob(jobName, null, jobGroupName, triggerName, triggerGroupName, jobClass, time);
	}

	@Override
	public void addJob(String jobName, String jobClassName, String time) {
		this.addJob(jobName, null, jobClassName, time);
	}

	@Override
	public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,String jobClassName, String time) {
		this.addJob(jobName, null, jobGroupName, triggerName, triggerGroupName, jobClassName, time);
	}

	@Override
	public void addJob(String jobName, Map<Object, Object> jobParams, String jobClassName, String time) {
		this.addJob(jobName, jobParams, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, jobClassName, time);
	}

	@Override
	public void addJob(String jobName, Map<Object, Object> jobParams, Class<?> jobClass, String time) {
		this.addJob(jobName, jobParams, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, jobClass, time);
	}
	
	@Override
	public void addJob2(String jobName, String jobParams, String triggerName, String jobClassName, String time) {
		addJob2(jobName, jobParams, JOB_GROUP_NAME, triggerName, TRIGGER_GROUP_NAME, jobClassName, time);
	}
	@Override
	public void addJob2(String jobName, String jobParams, String jobGroupName, String triggerName, String triggerGroupName, String jobClassName, String time) {
		Map<Object, Object> map = null;
		if(null!=jobParams){
			String m = jobParams.trim();
			if(!"null".equals(m)&&!"".equals(m)){
				map = new HashMap<Object,Object>();
				String[] mapValues = jobParams.split(";");
				for (String value : mapValues) {
					String[] values = value.split(":");
					map.put(values[0].trim(), values[1].trim());
				}
			}
		}
		try {
			this.addJob(jobName, map, jobGroupName, triggerName, triggerGroupName, InvokeUtil.ClassForName(jobClassName), time);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addJob(String jobName, Map<Object, Object> jobParams, String jobGroupName, String triggerName, String triggerGroupName, String jobClassName, String time) {
		try {
			this.addJob(jobName, jobParams, jobGroupName, triggerName, triggerGroupName, InvokeUtil.ClassForName(jobClassName), time);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	public void addJob(String jobName, Map<Object, Object> jobParams, String jobGroupName, String triggerName, String triggerGroupName, Class<?> jobClass, String time) {
		try {
			JobDetail jobDetail = sched.getJobDetail(jobName, jobGroupName);
			if(null==jobDetail){
				jobDetail = new JobDetail(jobName, jobGroupName, jobClass);// 任务名，任务组，任务执行类
				if (jobParams == null)
					jobParams = new HashMap<Object,Object>();
				String schedName = sched.getSchedulerName();
				
				jobParams.put("jobkey", schedName+"_"+jobName);
//				if(null!=jobParams&&jobParams.size()>0){
					jobDetail.getJobDataMap().clear();
					jobDetail.getJobDataMap().putAll(jobParams);
//				}
				// 触发器
				CronTrigger tigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);
				if(null==tigger){
					tigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
					CronExpression cexp = new CronExpression(time);//定义Cron表达式
					tigger.setCronExpression(cexp);// 触发器时间设定
				}else{
					CronExpression cexp = new CronExpression(time);//定义Cron表达式
					tigger.setCronExpression(cexp);// 触发器时间设定
				}
				sched.scheduleJob(jobDetail, tigger);
			}else{
				throw new RuntimeException("添加失败,已存在"+jobName+"任务");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public  void modifyJobTime(String triggerName, String time) {
		this.modifyJobTime(triggerName, TRIGGER_GROUP_NAME, time);
	}
	
	@Override
	public  void modifyJobTime(String jobName, Map<Object, Object> jobParams, String triggerName,String jobClassName, String time) {
		try {
			sched.deleteJob(jobName, JOB_GROUP_NAME);
			addJob(jobName, jobParams, JOB_GROUP_NAME, triggerName, TRIGGER_GROUP_NAME, jobClassName, time);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public  void modifyJobTime(String triggerName, String triggerGroupName, String time) {
		try {
			CronTrigger tigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);
			if (tigger == null) {
				throw new RuntimeException("无"+triggerName+"定时器");
			}
			String oldTime = tigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) tigger;
				// 修改时间
				ct.setCronExpression(time);
				// 重启触发器
				sched.resumeTrigger(triggerName, triggerGroupName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public void resumeTrigger(String triggerName){
		resumeTrigger(triggerName, TRIGGER_GROUP_NAME);
	}
	
	@Override
	public void resumeTrigger(String triggerName,String triggerGroupName){
		try {
			CronTrigger tigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);
			if (tigger == null) {
				throw new RuntimeException("无"+triggerName+"定时器");
			}
			sched.resumeTrigger(triggerName, triggerGroupName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void pasueTrigger(String triggerName){
		pasueTrigger(triggerName, TRIGGER_GROUP_NAME);
	}
	
	@Override
	public void pasueTrigger(String triggerName,String triggerGroupName){
		try {
			CronTrigger tigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);
			if (tigger == null) {
				throw new RuntimeException("无"+triggerName+"定时器");
			}
			sched.pauseTrigger(triggerName, triggerGroupName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public  void removeTigJob(String jobName) {
		removeTigJob(jobName, jobName);
	}
	
	@Override
	public  void removeTigJob(String jobName, String triggerName) {
		removeTigJob(jobName, JOB_GROUP_NAME, triggerName, TRIGGER_GROUP_NAME);
	}
	
	@Override
	public  void removeTigJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			if (sched.getTrigger(triggerName, triggerGroupName) != null) {
				sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
				sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
			}
			if(sched.getJobDetail(jobName, jobGroupName)!=null){
				sched.deleteJob(jobName, jobGroupName);// 删除任务
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void start() {
		try {
			if (!sched.isStarted())
				sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutdown() {
		try {
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void standby() {
		try {
			if (!sched.isInStandbyMode())
				sched.standby();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Scheduler getScheduler() {
		return sched;
	}

	@Override
	public String toString() {
		return sched.toString();
	}

	@Override
	public boolean isStarted() {
		try {
			return sched.isStarted();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isStandby() {
		try {
			return sched.isInStandbyMode();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void pauseJob(String jobName) {
		pauseJob(jobName, JOB_GROUP_NAME);
	}

	@Override
	public void pauseJob(String jobName, String jobGroupName) {
		try {
			if (!sched.isShutdown()) {
				sched.pauseJob(jobName, jobGroupName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeJob(String jobName) {
		removeJob(jobName, JOB_GROUP_NAME);
	}

	@Override
	public void removeJob(String jobName, String jobGroupName) {
		try {
			if (!sched.isShutdown()) {
				sched.resumeJob(jobName, jobGroupName);;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void removeTrigger(String triggerName,String triggerGroupName){
		try {
			if (!sched.isShutdown()) {
				sched.resumeTrigger(triggerName, triggerGroupName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void resumeJob(String jobName) {
		try {
			sched.resumeJob(jobName, JOB_GROUP_NAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public CronTrigger getTrigger(String triggerName) {
		try {
			return (CronTrigger) sched.getTrigger(triggerName, TRIGGER_GROUP_NAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public JobDetail getJob(String jobName) {
		try {
			return sched.getJobDetail(jobName, JOB_GROUP_NAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
