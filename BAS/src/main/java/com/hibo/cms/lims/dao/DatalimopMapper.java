package com.hibo.cms.lims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.lims.model.Datalimop;
import com.hibo.cms.lims.model.DatalimopKey;



/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午10:49:23</p>
 * <p>类全名：com.hibo.cms.lims.dao.DatalimopMapper</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface DatalimopMapper {


    int insert(Datalimop datalimop);
	
	List<Datalimop> selectAll();
	
	List<Datalimop> selectDatalimop(String datalimmid);
	
	int deleteByDatalimopId(String datalimopid);
	
	int updateByDatalimopId(@Param("olddatalimopid") String olddatalimopid,@Param("datalimop") Datalimop datalimop);

	int delete(String limid);
	
	int deleteByPrimaryKey(DatalimopKey key);


    int insertSelective(Datalimop record);

    Datalimop selectByPrimaryKey(DatalimopKey key);

    int updateByPrimaryKeySelective(@Param("key") DatalimopKey key,@Param("record") Datalimop record);

    int updateByPrimaryKey(Datalimop record);
}
