package com.hibo.cms.quartz.dao;
/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月14日 下午4:42:29</p>
 * <p>类全名：com.hibo.cms.quartz.dao.QuratzMapper</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.quartz.model.Quartz;
public interface QuratzMapper {
	
	List<Map<String,Object>> findAllQzNames(String qserver);
	
	List<Quartz> selectByQserver(String qserver);
	
	List<Quartz> findAllQzByName(String qzname);
	
	Quartz selectByJobName(@Param("schename")String schename,@Param("jobname")String jobname);
	
	int updateQzById(Quartz quartz);
	
	int updateQzByIdMap(@Param("idSet")Set<String> idSet,@Param("qtypeMap")Map<String, Integer>qtypeMap);
	
	int updateStatusById(@Param("id")String id, @Param("qtype")int qtype);
	
	int deleteQzById(String id);
	
	int addQz(Quartz quartz);
	
	int selectCountByQserver(String qserver);
	
	List<Map<String,Object>> findAllQzByQserver(String qserver);
	

}
