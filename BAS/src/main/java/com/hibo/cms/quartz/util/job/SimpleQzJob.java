package com.hibo.cms.quartz.util.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.quartz.util.tigger.TiggerStaticValue;
/**
 * <p>标题：任务测试类</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月6日 下午6:05:56</p>
 * <p>类全名：com.hibo.cms.quartz.util.job.SimpleQzJob</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */

public class SimpleQzJob extends BasJob {
	
	@Override
	public void run(JobExecutionContext context,JobDataMap map) {
		Date time = context.getNextFireTime();
		Object type = map.get("type");
		String name = context.getJobDetail().getName();
		String key = StrUtil.obj2str(map.get("jobkey"));
		long num = StrUtil.obj2long(TiggerStaticValue.JOB_INFO.get(key+"_totle"), 0);
		System.out.println(name + "--参数:type=" + type + "--下次执行时间:" + new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(time));
		if (num == 8)
			throw new RuntimeException("执行次数："+num);
	}
}