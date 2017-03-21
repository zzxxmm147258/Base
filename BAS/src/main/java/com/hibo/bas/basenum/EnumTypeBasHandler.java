package com.hibo.bas.basenum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * <p>标题：EnumTypeBasHandler</p>
 * <p>功能：This type handler should be specified in MyBatis map file at Enum field.</p>
 * 		  <p>
 * 			e.g result column="orderStatus" property="orderStatus" jdbcType="INTEGER" typeHandler="com.hibo.bas.model.EnumTypeBasHandler"
 * 		  </p>
 * 		  <p>
 * 		  Compared with EnumOrdinalTypeHandler in MyBatis,our EnumTypeBasHandler is more advanced in many aspects.
 * 		  </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：Jul 24, 2015 4:16:18 PM</p>
 * <p>类全名：com.hibo.bas.model.EnumTypeBasHandler</p>
 * 作者：巩宁
 * 初审：
 * 复审：
 * @author <a href="mailto:backtoning@sina.com?subject='HTBase Bugs'">Gino</a>
 * @version 2015-08-14
 */

public class EnumTypeBasHandler<E extends Enum<E> & BasEnum<E>> extends BaseTypeHandler<E>{

	private Class<E> type;
	
	//real integer map to Enum.
	private Map<Integer,E> map;
	
	public EnumTypeBasHandler(Class<E> type){
		if(type == null){
			throw new IllegalArgumentException("type must be specified to use EnumTypeBasHandler.");
		}
		this.type = type;
		this.map = new HashMap<Integer,E>();
		E[] targetConst = this.type.getEnumConstants();
		if(targetConst == null){
			throw new IllegalArgumentException("E is not a type of Enum.");
		}
		for(E curConst : targetConst){
			this.map.put(((BasEnum<E>)curConst).getEnumValue(),curConst);
		}
	}
	
	
	
	@Override
	public E getNullableResult(ResultSet arg0, String arg1) throws SQLException {
		Integer value = arg0.getInt(arg1);
		return arg0.wasNull() ? null : this.map.get(value);
	}

	@Override
	public E getNullableResult(ResultSet arg0, int arg1) throws SQLException {
		Integer value = arg0.getInt(arg1);
		return arg0.wasNull() ? null : this.map.get(value);
	}

	@Override
	public E getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
		Integer value = arg0.getInt(arg1);
		return arg0.wasNull() ? null : this.map.get(value);
	}

	@Override
	public void setNonNullParameter(PreparedStatement arg0, int arg1, E arg2, JdbcType arg3) throws SQLException {
		arg0.setInt(arg1,arg2.getEnumValue());
	}

}
