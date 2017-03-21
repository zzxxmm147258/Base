package com.hibo.bas.coderule.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月19日 下午2:38:12</p>
 * <p>类全名：com.hibo.bas.coderule.dao.CodeMapper</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public interface CodeMapper {
	List<Map<String,Object>> selectCodeList(@Param("sql")String sql);
	int updateCode(@Param("id")String id,@Param("lock")boolean lock);
	int updateCodeTrue(@Param("id")String id,@Param("lock")boolean lock);
	int insertCode(@Param("id")String id,@Param("updateName")String updateName,@Param("lock")boolean lock);
	Map<String,Object> selectLock(@Param("id")String id);
	int selectCount(@Param("id")String id);
	
}
