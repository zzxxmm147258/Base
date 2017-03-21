package com.hibo.cms.lims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.lims.model.Busheetlim;
import com.hibo.cms.lims.model.BusheetlimKey;



/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月20日 上午9:31:12</p>
 * <p>类全名：com.hibo.cms.lims.dao.BusheetlimMapper</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface BusheetlimMapper {
	    
	    int insert(Busheetlim busheetlim);
		
		List<Busheetlim> selectAll();
		
		List<Busheetlim> selectBusheetlim(String datalimmid);
		
		int deleteByBusheetlimId(String busheetlimid);
		
		int updateByBusheetlimId(@Param("oldbusheetlimid") String oldbusheetlimid,@Param("busheetlim") Busheetlim busheetlim);
      
		int delete(String limid);
		
		int deleteByPrimaryKey(BusheetlimKey key);


	    int insertSelective(Busheetlim record);

	    Busheetlim selectByPrimaryKey(BusheetlimKey key);

	    int updateByPrimaryKeySelective(@Param("key") BusheetlimKey key,@Param("record") Busheetlim record);

	    int updateByPrimaryKey(Busheetlim record);
}
