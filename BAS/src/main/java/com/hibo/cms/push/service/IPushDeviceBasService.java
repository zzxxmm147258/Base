package com.hibo.cms.push.service;

import java.util.List;

import com.hibo.cms.push.model.PushDeviceBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月8日 下午5:22:53</p>
 * <p>类全名：com.hibo.cms.push.service.IPushDeviceBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IPushDeviceBasService {
    
   public int addPushDevice(PushDeviceBas record);
   
   public int updatePushDevice(PushDeviceBas record);
   
   /**
    * <p>功能： 查询该用户的设备ID</p>
    * <p>创建日期：2015年12月25日 上午10:41:46</p>
    * 作者：崔志敏
    * @param userId
    * @return
    */
   public String[] findByUserId(String userId);
   
   /**
    * <p>功能： 查询全部设备ID</p>
    * <p>创建日期：2015年12月25日 上午10:46:39</p>
    * 作者：崔志敏
    * @return
    */
   public List<PushDeviceBas> findAll();
   
   /**
    * <p>功能： </p>
    * <p>创建日期：2016年2月24日 下午5:12:52</p>
    * 作者：崔志敏
    * @param attrName1 android ,ios
    * @return
    */
   public List<PushDeviceBas> findIosOrAndroidAll(String attrName1);
}
