package com.hibo.cms.lims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.hibo.cms.lims.model.Datalims;
import com.hibo.cms.lims.model.DatalimsKey;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午1:40:41</p>
 * <p>类全名：com.hibo.cms.lims.dao.DatalimsMapper</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface DatalimsMapper {

	int deleteByPrimaryKey(DatalimsKey key);
	
	Datalims selectByPrimaryKey(DatalimsKey key);
	 
    int insert(Datalims datalims);
	
	List<Datalims> selectAll();
	
	List<Datalims> selectDatalims(String datalimmid);
	
	int deleteByDatalimsId(String datalimsid);
	
	int updateByDatalimsId(@Param("olddatalimsid") String olddatalimsid,@Param("datalims") Datalims datalims);
   
	int delete(String limid);

    int insertSelective(Datalims record);

    int updateByPrimaryKeySelective(@Param("key") DatalimsKey key,@Param("record") Datalims record);

    int updateByPrimaryKey(Datalims record);
}
