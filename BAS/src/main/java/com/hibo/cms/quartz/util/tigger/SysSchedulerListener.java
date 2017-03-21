package com.hibo.cms.quartz.util.tigger;

import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;

public class SysSchedulerListener implements SchedulerListener {

	@Override
	public void jobScheduled(Trigger trigger) {
	}

	@Override
	public void jobUnscheduled(String triggerName, String triggerGroup) {

	}

	@Override
	public void triggerFinalized(Trigger trigger) {

	}

	@Override
	public void triggersPaused(String triggerName, String triggerGroup) {

	}

	@Override
	public void triggersResumed(String triggerName, String triggerGroup) {

	}

	@Override
	public void jobsPaused(String jobName, String jobGroup) {

	}

	@Override
	public void jobsResumed(String jobName, String jobGroup) {

	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {

	}

	@Override
	public void schedulerShutdown() {

	}

}
