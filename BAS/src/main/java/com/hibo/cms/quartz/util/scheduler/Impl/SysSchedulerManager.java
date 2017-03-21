package com.hibo.cms.quartz.util.scheduler.Impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.hibo.bas.util.StrUtil;
import com.hibo.cms.quartz.util.scheduler.ISysScheduler;

public class SysSchedulerManager {

	private static StdSchedulerFactory schedFact = new StdSchedulerFactory();;
	
	private static String SCHEDULER_NAME = "SYSQZ";

	private static Map<String, ISysScheduler> map = new HashMap<String, ISysScheduler>();

	/**
	 * 
	 * @param isSingle
	 *            是否全局通用一个任务调度控制
	 *            ,为true则同一控制,所有的都同时通过任务清单开关开启,不要执行开启关闭停止动作,如需个人应用用false自行设置即可
	 * @return
	 * @throws SchedulerException
	 */
	public static ISysScheduler getISysScheduler(String scheName) throws SchedulerException {
		return getISysScheduler(scheName, "1");
	}
	
	public synchronized static ISysScheduler getISysScheduler(String scheName, String threadCount) throws SchedulerException {
		if (null == scheName) {
			scheName = SCHEDULER_NAME;
		}
		ISysScheduler sysScheduler = null;
		if (map.containsKey(scheName)) {
			sysScheduler = map.get(scheName);
		} else {
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", scheName);
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			if (StrUtil.obj2int(threadCount) > 5)
				threadCount = "5";
			props.put("org.quartz.threadPool.threadCount", threadCount);// 必填
			props.put("org.quartz.scheduler.makeSchedulerThreadDaemon",true);
			schedFact.initialize(props);
			Scheduler scheduler = schedFact.getScheduler();
			// TODO 添加自定义监听
			// scheduler.addSchedulerListener(null);
			// scheduler.addGlobalJobListener(null);
			// scheduler.addGlobalTriggerListener(null);
			sysScheduler = new SysSchedulerImpl(scheduler);
			map.put(scheName, sysScheduler);
		}
		return sysScheduler;
	}

	public static Set<String> getAllScheName() {
		return map.keySet();
	}
	
	public static void removeScheduler(String scheName) {
		ISysScheduler sche = map.remove(scheName);
		sche.shutdown();
	}
	
	public static Collection<?> getAllSchedulers() {
		try {
			return schedFact.getAllSchedulers();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
