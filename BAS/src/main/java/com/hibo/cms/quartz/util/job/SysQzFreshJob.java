package com.hibo.cms.quartz.util.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibo.bas.dbutil.DataSource;
import com.hibo.bas.spring.SpringContextUtil;
import com.hibo.bas.util.DataConfig;
import com.hibo.cms.quartz.dao.QuratzMapper;
import com.hibo.cms.quartz.util.scheduler.ISysScheduler;
import com.hibo.cms.quartz.util.scheduler.Impl.SysSchedulerManager;

/** 
 * <p>标题：任务调度</p>
 * <p>功能：任务调度任务同步程序 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月21日 下午2:44:50</p>
 * <p>类全名：com.hibo.cms.quartz.util.job.SysQzFreshJob</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class SysQzFreshJob implements Job{

	private static QuratzMapper quratzMapper = null;
	
	private static final Logger log = LoggerFactory.getLogger(SysQzFreshJob.class);
	
	private static List<Map<String,Object>> quartzList = new ArrayList<Map<String,Object>>();
	
	public SysQzFreshJob() {
		if(null==quratzMapper){
			quratzMapper = SpringContextUtil.getBean(QuratzMapper.class);
		}
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			long startTime = System.currentTimeMillis();
			// 获取默认连接的数据库
			DataSource.setDataSource(DataConfig.getConfig("DEFAULTDB"));
			String date = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.sss").format(new Date());
			String sys_job_name = context.getJobDetail().getName();
			if (log.isInfoEnabled()) {
				log.info("系统" + sys_job_name + "定时任务开始更新所有任务状态--更新时间:" + date);
			}
			String qserver = DataConfig.getConfig("QUARTZ.KEY");
			int count = quratzMapper.selectCountByQserver(qserver);
			if (count != quartzList.size()) {
				// 重新装载数据
				quartzList = quratzMapper.findAllQzByQserver(qserver);
			}
			// 当前数据是否改变
			boolean is_change = false;
			int n = 0;
			for (Map<String, Object> map : quartzList) {
				int qtype = (int) map.get("qtype");
				String triggerName = (String) map.get("tiggername");
				String triggerGroup = (String) map.get("tiggergroup");
				String schename = (String) map.get("schename");
				ISysScheduler sche = SysSchedulerManager.getISysScheduler(schename);
				boolean isstart = sche.isStarted() && !sche.isStandby();
				int status = 8;
				if (isstart) {
					status = SysSchedulerManager.getISysScheduler(schename).getScheduler().getTriggerState(triggerName, triggerGroup);
				}
				if (qtype != status) {
					String id = (String) map.get("id");
					int k = quratzMapper.updateStatusById(id, status);
					n = n + k;
					is_change = true;
				}
			}
			if (is_change) {
				// 重新装载数据
				quartzList = quratzMapper.findAllQzByQserver(qserver);
			}
			long endTime = System.currentTimeMillis();
			double time = (endTime - startTime) / 1000.000;
			if (log.isInfoEnabled()) {
				log.info("系统" + sys_job_name + "定时任务开始更新所有任务状态--" + n + "条--用时:" + time + "秒--下次更新时间:" + new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.sss").format(context.getNextFireTime()));
			}
		} catch (SchedulerException e) {
			if(log.isErrorEnabled()){
				log.error("定时任务的更新任务异常:"+e.getMessage());
			}
			e.printStackTrace();
		}
	}
}
