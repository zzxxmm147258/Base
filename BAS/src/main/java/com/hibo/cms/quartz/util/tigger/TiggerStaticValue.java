package com.hibo.cms.quartz.util.tigger;

import java.util.HashMap;
import java.util.Map;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月7日 下午1:41:36</p>
 * <p>类全名：com.hibo.cms.quartz.util.tigger.TiggerStaticValue</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class TiggerStaticValue {
	public static Map<Integer,String> JOB_STATUS = new HashMap<Integer, String>();
	static{
		//-1:None：Trigger已经完成，且不会在执行，或者找不到该触发器，或者Trigger已经被删除
		JOB_STATUS.put(-1, "未执行");
		//0:NORMAL:正常状态
		JOB_STATUS.put(0, "正常运行");
		//1:PAUSED：暂停状态
		JOB_STATUS.put(1, "暂停运行");
		//2:COMPLETE：触发器完成，但是任务可能还正在执行中
		JOB_STATUS.put(2, "完成,仍运行中");
		//4:BLOCKED：线程阻塞状态
		JOB_STATUS.put(4, "阻塞状态");
		//5:ERROR：出现错误
		JOB_STATUS.put(5, "错误状态");
		//8:调度器关闭
		JOB_STATUS.put(8, "调度器关闭");
	}
	
	public static Map<String,Object> JOB_INFO = new HashMap<String,Object>();
}
