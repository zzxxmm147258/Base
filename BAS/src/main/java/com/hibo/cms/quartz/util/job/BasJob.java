package com.hibo.cms.quartz.util.job;

import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hibo.bas.spring.SpringContextUtil;
import com.hibo.bas.util.DateUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.quartz.service.IQuartzUpdateBasService;
import com.hibo.cms.quartz.util.tigger.TiggerStaticValue;
import com.hibo.cms.sys.utils.remote.RemoteInteractionUtil;

/** <p>标题：任务基类，用于记录任务执行情况</p>
 * <p>功能： 用于记录任务执行情况</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年1月6日 下午6:50:49</p>
 * <p>类全名：com.hibo.cms.quartz.util.job.BasJob</p>
 * 作者：cgh
 * 初审：
 * 复审：
 * @DisallowConcurrentExecution：将该注解加到job类上，告诉Quartz不要并发地执行同一个job定义（这里指特定的job类）的多个实例。
 * @PersistJobDataAfterExecution   将该注解加在job类上，告诉Quartz在成功执行了job类的execute方法后（没有发生任何异常），更新JobDetail中JobDataMap的数据，使得该job（即JobDetail）在下一次执行的时候，JobDataMap中是更新后的数据，而不是更新前的旧数据
 */

public class BasJob  implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap map = context.getJobDetail().getJobDataMap();
		String key = StrUtil.obj2str(map.get("jobkey"));
		String locked = StrUtil.obj2str(map.get("jobLocked"));
		int lock = 0;
		IQuartzUpdateBasService quartzUpdateBasService = SpringContextUtil.getBean("quartzUpdateBasServiceImpl");
		try{
			if (!StrUtil.isnull(locked))
				lock = quartzUpdateBasService.getLocked(locked);
			long num = StrUtil.obj2long(TiggerStaticValue.JOB_INFO.get(key+"_totle"), 0);
			TiggerStaticValue.JOB_INFO.put(key+"_totle", ++num);
			Date date = new Date(); 
			if (lock > 0 || StrUtil.isnull(locked)){
				run(context, map);
			}
			long sec = DateUtil.diffSeconds(new Date(), date);
			long maxSec = StrUtil.obj2long(TiggerStaticValue.JOB_INFO.get(key+"_sec"), 0);
			if (sec > maxSec)
				TiggerStaticValue.JOB_INFO.put(key+"_sec", sec);
		}catch(Exception e){
			long num = StrUtil.obj2long(TiggerStaticValue.JOB_INFO.get(key+"_excep"), 0);
			TiggerStaticValue.JOB_INFO.put(key+"_excep", ++num);
			TiggerStaticValue.JOB_INFO.put(key+"_excepMsg", e.getMessage());
			e.printStackTrace();
		}finally{
			if (lock > 0)
				quartzUpdateBasService.releaseLocked(locked);
		}
	}
	
	public void run(JobExecutionContext context, JobDataMap map){
		String beanName = StrUtil.obj2str(map.get("jobBeanName"));
		if (!StrUtil.isnull(beanName)){
			String method = StrUtil.obj2str(map.get("jobBeanMethod"));
			String remoteType = StrUtil.obj2str(map.get("jobRemoteType"));
			if (remoteType == null)
				remoteType = "";
			Object[] args = {map};
			try{
				RemoteInteractionUtil.getRemoteObjByConno(remoteType, beanName, method, args);
			}catch(Exception ex){
				throw new RuntimeException(ex);
			}
		}
	}
}
