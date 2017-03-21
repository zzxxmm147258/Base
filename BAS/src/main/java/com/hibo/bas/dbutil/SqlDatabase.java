package com.hibo.bas.dbutil;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.SqlParser;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.table.dao.TableMapper;

/** <p>标题：SQL工具类</p>
 * <p>功能： 通过SQL取值，返回二维组织、List<Map>、Map;此工具在工具类中使用，service类中，取业务数据禁用</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月17日 下午3:37:40</p>
 * <p>类全名：com.hibo.bas.dbutil.SqlDatabase</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */

@Component
public class SqlDatabase {
	
	private static TableMapper tableMapper;
	
	@Resource
	public void setTableMapper(TableMapper tableMapper){
		this.tableMapper = tableMapper;
	}
	
	public static List<Map> sqlSelectList(String sql){
		return sqlSelectList(null, sql);
	}
	
	/**
	 * 通过SQL语句，获得List<Map>
	 * @param dataSource  套帐号
	 * @param sql         SQL语句
	 * @return
	 */
	public static List<Map> sqlSelectList(String dataSource,String sql){
		List<Map> list = null;
		String oldDataSource = null;
		try{
			if (dataSource != null){
				oldDataSource = DataSource.getDataSource();
				if (!dataSource.equals(oldDataSource))
					DataSource.setDataSource(dataSource);
			}
			list = tableMapper.selectSql(sql);
		}finally{
			if (dataSource != null && !dataSource.equals(oldDataSource))
				DataSource.setDataSource(oldDataSource);
		}
		return list;
	}
	
	public static Map sqlSelectMap(String sql){
		return sqlSelectMap(null, sql);
	}
	
	/**
	 * 通过SQL语句，获得一个Map
	 * @param dataSource
	 * @param sql
	 * @return
	 */
	public static Map sqlSelectMap(String dataSource,String sql){
		String oldDataSource = null;
		try{
			if (dataSource != null){
				oldDataSource = DataSource.getDataSource();
				if (!dataSource.equals(oldDataSource))
					DataSource.setDataSource(dataSource);
			}
			List<Map> list = tableMapper.selectSql(sql);
			
			return (list != null && list.size()>0) ? list.get(0) : null;
		}finally{
			if (dataSource != null && !dataSource.equals(oldDataSource))
				DataSource.setDataSource(oldDataSource);
		}
	}
	
	public static Map[] sqlSelectMaps(String sql){
		return sqlSelectMaps(null, sql);
	}
	/**
	 * 通过SQL语句，获得一个Map
	 * @param dataSource
	 * @param sql
	 * @return
	 */
	public static Map[] sqlSelectMaps(String dataSource,String sql){
		String oldDataSource = null;
		try{
			if (dataSource != null){
				oldDataSource = DataSource.getDataSource();
				if (!dataSource.equals(oldDataSource))
					DataSource.setDataSource(dataSource);
			}
			List<Map> list = tableMapper.selectSql(sql);
			return (list != null && list.size()>0) ? (Map[])list.toArray() : null;
		}finally{
			if (dataSource != null && !dataSource.equals(oldDataSource))
				DataSource.setDataSource(oldDataSource);
		}
	}
	
	public static Object sqlSelect1(String sql, String cols){
		return sqlSelect1(null, sql, cols);
	}
	
	/**
	 * 通过SQL语句，获得一个Object
	 * @param dataSource
	 * @param sql
	 * @param cols  字段
	 * @return
	 */
	public static Object sqlSelect1(String dataSource,String sql, String cols){
		String oldDataSource = null;
		try{
			if (dataSource != null){
				oldDataSource = DataSource.getDataSource();
				if (!dataSource.equals(oldDataSource))
					DataSource.setDataSource(dataSource);
			}
			List<Map> list = tableMapper.selectSql(sql);
			Object o = null;
			if (list != null && list.size()>0){
				if (StrUtil.isnull(cols)){
					String colnums = SqlParser.getSqlColumn(sql);
					cols = StrUtil.splitString(colnums, ',')[0].trim();
				}
				o = list.get(0).get(cols);
			}
			return o;
		}finally{
			if (dataSource != null && !dataSource.equals(oldDataSource))
				DataSource.setDataSource(oldDataSource);
		}
	}
	
	
	public static Object[] sqlSelect2(String sql, String cols){
		return sqlSelect2(null, sql, cols);
	}
	
	/**
	 * 通过SQL语句，获得一个 一维数组
	 * @param dataSource
	 * @param sql
	 * @param cols
	 * @return
	 */
	public static Object[] sqlSelect2(String dataSource, String sql, String cols){
		Object[][] o = sqlSelect3(null, sql, cols);
		if (o == null || o.length < 1 || o[0].length < 1)
			return null;
		if (o.length > 0 && o[0].length > 0){
			return o[0];
		}else{
			Object[] o1 = new Object[o.length];
			for(int i = 0; i < o.length; i++)
				o1[i] = o[i][0];
			return o1;
		}
	}
	
	public static Object[][] sqlSelect3(String sql, String cols){
		return sqlSelect3(null, sql, cols);
	}
	
	/**
	 * 通过SQL语句，获得一个M 二维数组
	 * @param dataSource
	 * @param sql
	 * @return
	 */
	public static Object[][] sqlSelect3(String dataSource,String sql, String cols){
		String oldDataSource = null;
		try{
			if (dataSource != null){
				oldDataSource = DataSource.getDataSource();
				if (!dataSource.equals(oldDataSource))
					DataSource.setDataSource(dataSource);
			}
			List<Map> list = tableMapper.selectSql(sql);
			Object[][] o = null;
			if (list != null && list.size()>0){
				if (StrUtil.isnull(cols)){
					cols = SqlParser.getSqlColumn(sql);
					cols = StrUtil.replace(cols, " ", "");
					cols = cols.toLowerCase();
				}
				String[] colArr = StrUtil.splitString(cols, ',');
				Map[] map = (Map[])list.toArray();
				o = MapUtil.toArrays(map, colArr);
			}
			return o;
		}finally{
			if (dataSource != null && !dataSource.equals(oldDataSource))
				DataSource.setDataSource(oldDataSource);
		}
	}
}
