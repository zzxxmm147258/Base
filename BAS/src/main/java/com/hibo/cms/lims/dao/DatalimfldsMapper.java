package com.hibo.cms.lims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.lims.model.Datalimflds;
import com.hibo.cms.lims.model.DatalimfldsKey;


/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午10:08:02</p>
 * <p>类全名：com.hibo.cms.lims.dao.DatalimfldsMapper</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface DatalimfldsMapper {


    int insert(Datalimflds datalimflds);
	
	List<Datalimflds> selectAll();
	
	List<Datalimflds> selectDatalimflds(String datalimmid);
	
	int deleteByDatalimfldsId(String datalimfldsid);
	
	int updateByDatalimfldsId(@Param("olddatalimfldsid") String olddatalimfldsid,@Param("datalimflds") Datalimflds datalimflds);

	int delete(String limid);
	
	int deleteByPrimaryKey(DatalimfldsKey key);


    int insertSelective(Datalimflds record);

    Datalimflds selectByPrimaryKey(DatalimfldsKey key);

    int updateByPrimaryKeySelective(@Param("key") DatalimfldsKey key,@Param("record") Datalimflds record);

    int updateByPrimaryKey(Datalimflds record);

}
