package com.hibo.cms.table.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月30日 下午4:03:49</p>
 * <p>类全名：com.hibo.cms.table.dao.TableMapper</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public interface TableMapper {
	
	String selectTable(@Param("dbname") String dbname, @Param("tablename") String tablename);
	
	String selectTable1(@Param("tablename") String tablename);
	
	String selectTable2(@Param("tablename") String tablename);
	
	List<Map> selectField(@Param("dbname") String dbname, @Param("tablename") String tablename, @Param("colname") String colname);
	
	List<Map> selectField1(@Param("tablename") String tablename, @Param("colname") String colname);
	
	List<Map> selectField2(@Param("tablename") String tablename, @Param("colname") String colname);
	
	List<Map> selectIndex(@Param("dbname") String dbname, @Param("tablename") String tablename, @Param("indexname") String indexname);
	
	List<Map> selectIndex1(@Param("tablename") String tablename, @Param("indexname") String indexname);
	
	List<Map> selectIndex2(@Param("tablename") String tablename, @Param("indexname") String indexname);
	
	List<Map> selectSql(@Param("sql") String sql);
	
	int updateSql(@Param("sql") String sql);
}
