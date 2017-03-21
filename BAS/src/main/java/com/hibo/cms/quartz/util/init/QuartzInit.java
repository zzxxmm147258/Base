package com.hibo.cms.quartz.util.init;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.quartz.dao.QuratzMapper;
import com.hibo.cms.quartz.model.Quartz;
import com.hibo.cms.quartz.util.job.SysQzFreshJob;
import com.hibo.cms.quartz.util.scheduler.ISysScheduler;
import com.hibo.cms.quartz.util.scheduler.Impl.SysSchedulerManager;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月17日 下午4:51:29</p>
 * <p>类全名：com.hibo.cms.quartz.util.init.QuartzInit</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class QuartzInit implements ApplicationListener<ContextRefreshedEvent> {
	@Resource
	private QuratzMapper quratzMapper;
	private static final Logger log = LoggerFactory.getLogger(QuartzInit.class);
	public static final String SYS_QZ_NAME = "SYS_QZ_NAME_LISTENER";
	public static final String SYS_QZ_JOBNAME = "SYS_QZ_JOBNAME_LISTENER";

	public void init() {
		// 启动定时任务

		String qserver = DataConfig.getConfig("QUARTZ.KEY");
		if(!StrUtil.isnull(qserver)){
			List<Map<String, Object>> qznamesDb = null;
			try {
				qznamesDb = quratzMapper.findAllQzNames(qserver);
			} catch (Exception e) {
				e.printStackTrace();
				if (log.isErrorEnabled()) {
					log.error("查询定时任务异常:" + e.getMessage());
				}
			}
			try {
				if (null != qznamesDb && qznamesDb.size() > 0) {
					for (Map<String, Object> map : qznamesDb) {
						if ("true".equals(map.get("isqserver"))) {
							String schename = StrUtil.obj2str(map.get("schename"));
							String threadCount = StrUtil.obj2str(map.get("iscount"));
							if(!StrUtil.isnull(schename)){
								initQz(schename,threadCount);
							}
						}
					}
				}
				// 启动系统自动同步定时任务
				// sysQzStart();
			} catch (Exception e) {
				e.printStackTrace();
				if (log.isErrorEnabled()) {
					log.error("启动时任务异常:" + e.getMessage());
				}
				throw new RuntimeException("启动时任务异常:" + e.getMessage());
			}
		}
	}
	private void sysQzStart() {
		try {
			ISysScheduler sched = SysSchedulerManager.getISysScheduler(SYS_QZ_NAME,"1");
			sched.addJob(SYS_QZ_JOBNAME, SYS_QZ_JOBNAME, SYS_QZ_JOBNAME, SYS_QZ_JOBNAME, SysQzFreshJob.class, "0 0/1 * * * ?");
			sched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	private void initQz(String qzname, String threadCount) throws SchedulerException{
		//获取任务
		List<Quartz> qzList = quratzMapper.findAllQzByName(qzname);
		ISysScheduler sched = SysSchedulerManager.getISysScheduler(qzname, threadCount);
		boolean isStart = false;
		for (Quartz quartz : qzList) {
			//是否是本服务器任务
			boolean isAdd = getparren(quartz.getQserver());
			int utype = quartz.getUtype();
			//是否在调度器内
			if (isAdd&&utype==1) {
				isStart = true;
				//是否初始化添加任务
				//if(quartz.getQtype()==0){
				sched.addJob2(quartz.getJobname(), quartz.getJobparams(), quartz.getJobgroup(), quartz.getTiggername(),quartz.getTiggergroup(), quartz.getExecuteclass(), quartz.getExecutetime());					
				//}
			}
		}
		if(isStart){
			sched.start();
		}
	}
	public static boolean getparren(String qserver) {
		String qs = DataConfig.getConfig("QUARTZ.KEY");
		if(null==qserver||"null".equals(qserver.trim())||"".equals(qserver.trim())){
			return true;
		}
		if(null!=qs&&qs.length()>0){
			if(qs.equals(qserver.trim())){
				return true;
			}
		}
		return false;
	}
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
			boolean isOpen = "true".equals(DataConfig.getConfig("QUARTZ.START"));
			if (isOpen) {
				init();
			}
	      }
	}
}
