package com.hibo.cms.quartz.util.scheduler;

import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
public interface ISysScheduler {
	
	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobClassName
	 *            任务类全路径名
	 * @param time
	 *            时间设置，参考quartz说明文档
	 */
	public  void addJob(String jobName, String jobClassName, String time);
	
	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobParams
	 *            任务参数
	 * @param jobClassName
	 *            任务类全路径名
	 * @param time
	 *            时间设置，参考quartz说明文档
	 */
	public  void addJob(String jobName, Map<Object,Object> jobParams, String jobClassName, String time);
	
	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 */
	public  void addJob(String jobName, Class<?> jobClass, String time);	
	
	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobParams
	 * 			     任务参数
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 */
	public  void addJob(String jobName, Map<Object,Object> jobParams, Class<?> jobClass, String time);
	
	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param tiggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档 
	 */
	public  void addJob(String jobName, String jobGroupName, String tiggerName, String triggerGroupName, Class<?> jobClass, String time);
	
	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobParams
	 * 			     任务参数
	 * @param jobGroupName
	 *            任务组名
	 * @param tiggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档 
	 */
	public  void addJob(String jobName, Map<Object,Object> jobParams, String jobGroupName, String tiggerName, String triggerGroupName, String jobClassName, String time);	
	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param tiggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档 
	 */
	public  void addJob(String jobName, Map<Object,Object> jobParams, String jobGroupName, String tiggerName, String triggerGroupName, Class<?> jobClass, String time);
	
	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param tiggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClassName
	 *            任务类全路径名
	 * @param time
	 *            时间设置，参考quartz说明文档 
	 */
	public  void addJob(String jobName, String jobGroupName, String tiggerName, String triggerGroupName, String jobClassName, String time);
	
	/**
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param tiggerName
	 * @param time 
	 */
	public  void modifyJobTime(String tiggerName, String time);
	
	/**
	 * @Description: 修改一个任务的触发时间
	 * 
	 * @param tiggerName
	 * @param triggerGroupName
	 * @param time 
	 */
	public  void modifyJobTime(String tiggerName, String triggerGroupName, String time);
	
	/**
	 * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName 
	 */
	public  void removeTigJob(String jobName);
	
	/**
	 * @Description: 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param tiggerName
	 * @param triggerGroupName
	 */
	public  void removeTigJob(String jobName, String jobGroupName, String tiggerName, String triggerGroupName);
	
	/**
	 * @Description:启动所有定时任务 
	 */
	public void start();
	
	/**
	 * @Description:关闭所有定时任务 
	 */
	public void shutdown();
	
	public Scheduler getScheduler();
	
	public void standby();
	
	public void pauseJob(String jobName);
	
	public void pauseJob(String jobName, String jobGroupName);
	
	public void pasueTrigger(String tiggerName);
	
	public void pasueTrigger(String tiggerName,String triggerGroupName);
	
	public void resumeTrigger(String tiggerName);
	
	public void resumeTrigger(String tiggerName,String triggerGroupName);
	
	public void removeTigJob(String jobName, String jobGroupName);
	
	public void removeJob(String jobName);
	
	public void removeJob(String jobName, String jobGroupName);
	
	public void resumeJob(String jobName);
	
	public boolean isStarted();

	public boolean isStandby();
	
	public void addJob2(String jobName, String jobParams, String triggerName, String jobClassName, String time);
	
	public void addJob2(String jobName, String jobParams, String jobGroupName, String tiggerName, String triggerGroupName, String jobClassName, String time);

	public  void modifyJobTime(String jobName, Map<Object, Object> jobParams, String tiggerName,String jobClassName, String time);

	public void removeTrigger(String tiggerName,String triggerGroupName);
	
	public CronTrigger getTrigger(String tiggerName);
	
	public JobDetail getJob(String jobName);
}
