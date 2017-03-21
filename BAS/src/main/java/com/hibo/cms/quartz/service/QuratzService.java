package com.hibo.cms.quartz.service;

import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;

import com.hibo.cms.quartz.model.Quartz;

public interface QuratzService {
	
	String startQz(String qzname) throws SchedulerException;
	
	String shutdownQz(String qzname) throws SchedulerException;
	
	String addQz(Quartz quartzModel) throws SchedulerException;
	
	String modifyQz(Quartz quartzModel) throws SchedulerException;
	
	String pasueJob(String id, String qzname, String jobName) throws SchedulerException;
	
	List<Map<String,Object>> gerQzNames() throws SchedulerException;
	
	Map<String,Object> getJobByQz(String qzname);
	
	String standbyQz(String qzname) throws SchedulerException;
	
	String removeJob(String id, String qzname, String jobName) throws SchedulerException;
	
	String resumeJob(Quartz quartzModel) throws SchedulerException;
	
	List<Quartz> findAllQzByName(String qzname);
	
}
