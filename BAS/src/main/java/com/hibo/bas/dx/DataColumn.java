package com.hibo.bas.dx;

import java.util.*;
import com.hibo.bas.exception.HookedException;
import com.hibo.bas.util.StrUtil;

/**
 * <p>标题：字段列对象</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 上午11:11:42</p>
 * <p>类全名：com.hibo.bas.dx.DataColumn</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */

public class DataColumn implements java.io.Serializable, java.lang.Cloneable {
	/**
	 * 字段名
	 */
	public String columnName;
	/**
	 * 表名
	 */
	public String tableName;
	/**
	 * 类型 (java.sql.Types中定义的值)
	 */
	public int sqlType;
	/**
	 * 长度, 主要对 VARCHAR 类型
	 */
	public int size;
	/**
	 * 小数位, 主要对 数值 类型
	 */
	public int decimals;

	public DataColumn() {
	}

	public DataColumn(String columnName) {
		this.columnName = columnName;
	}

	public DataColumn(String columnName, String displayName, int sqlType) {
		this.columnName = columnName;
		this.displayCaption = displayName;
		this.sqlType = sqlType;
	}

	public DataColumn(String columnName, String displayName, int sqlType, int size, int decimals) {
		this.columnName = columnName;
		this.displayCaption = displayName;
		this.sqlType = sqlType;
		this.size = size;
		this.decimals = decimals;
	}

	public int flags;
	public int displayFlags;
	/*
	 * 多选分隔
	 */
	public char mutiValDelim;
	/**
	 * 显示名
	 */
	public String displayCaption;

	public String getDisplayCaption() {
		return displayCaption == null ? columnName : displayCaption;
	}

	public Object defaultValue;

	static public int columnAt(DataColumn dataColumns[], String columnName) {
		if (dataColumns != null && columnName != null)
			for (int i = 0; i < dataColumns.length; i++)
				if (columnName.equalsIgnoreCase(dataColumns[i].columnName))
					return i;
		return -1;
	}

	static public int indexOfColumn(DataColumn dataColumns[], String columnName) {
		if (dataColumns != null && columnName != null)
			for (int i = 0; i < dataColumns.length; i++)
				if (columnName.equalsIgnoreCase(dataColumns[i].columnName))
					return i;
		return -1;
	}

	static public <T extends DataColumn> T searchColumn(T dataColumns[], String columnName) {
		if (dataColumns != null)
			for (int i = 0; i < dataColumns.length; i++)
				if (dataColumns[i].columnName.equalsIgnoreCase(columnName))
					return dataColumns[i];
		return null;
	}

	static public int columnAtForDisplayName(DataColumn dataColumns[], String displayCaption) {
		if (displayCaption == null || dataColumns == null)
			return -1;
		if (dataColumns != null)
			for (int i = 0; i < dataColumns.length; i++)
				if (displayCaption.equalsIgnoreCase(dataColumns[i].displayCaption))
					return i;
		return -1;
	}

	public DataColumn cloneColumn() // throws CloneNotSupportedException
	{
		try {
			return (DataColumn) super.clone();
		} catch (java.lang.CloneNotSupportedException ex) {
			throw HookedException.toRuntimeException(ex);
		}
	}

	static public DataColumn[] clone(DataColumn dataColumns[]) {
		if (dataColumns == null)
			return null;
		try {
			DataColumn columns[] = new DataColumn[dataColumns.length];
			for (int i = 0; i < dataColumns.length; i++) {
				columns[i] = (DataColumn) dataColumns[i].clone();
			}
			return columns;
		} catch (java.lang.CloneNotSupportedException ex) {
			throw HookedException.toRuntimeException(ex);
		}
	}

	static public DataColumn[] selectForTableName(DataColumn dataColumns[], String tableName) {
		final List<DataColumn> v = new ArrayList<DataColumn>();
		if (tableName == null)
			tableName = "";
		for (int i = 0; i < dataColumns.length; i++) {
			if (tableName.equalsIgnoreCase(dataColumns[i].tableName == null ? "" : dataColumns[i].tableName))
				v.add(dataColumns[i]);
		}
		return v.toArray(new DataColumn[v.size()]);
	}

	static public String[] getColumnNames(DataColumn dataColumns[]) {
		if (dataColumns == null)
			return null;
		final int n = dataColumns.length;
		String a[] = new String[n];
		for (int i = 0; i < n; i++)
			a[i] = dataColumns[i].columnName;
		return a;
	}

	static public void listDxColumn(DataColumn dataColumns[], java.io.PrintStream out) {
		for (int i = 0; i < dataColumns.length; i++) {
			out.println("Column " + (dataColumns[i].tableName == null ? "" : dataColumns[i].tableName + ".")
					+ dataColumns[i].columnName + ",sqlType=" + dataColumns[i].sqlType + "("
					+ com.hibo.bas.util.SqlUtil.sqlType2Name(dataColumns[i].sqlType) + "),size=" + dataColumns[i].size
					+ ",decimals=" + dataColumns[i].decimals + ",flags=" + dataColumns[i].flags);
		}
	}

	public String sqlExpr;
	public int displayWidth;
	public int minDecimals; // 显示用

	final static public String AggNames[] = new String[] { null, "group", "sum", "min", "max", "avg", "count" };
	public int groupType;
	private static final long serialVersionUID = 1L;
	char valueDelim;

	/*
	 * 码名 表 数据
	 */
	public transient java.util.Map valueMap;
	public transient java.util.Map crossKeyValues; // // {<列名>:<值>,...
	public String cpMaster, cpFMaster;
	int indexDataStoreColumn;
	String crossDataColumn;
	int indexCrossDataColumn = -2;

	/*
	 * JsonableDataColumn.toJsonableDataColumns
	 * 
	 * @since adk3,
	 */
	public static DataColumn jsonDecode(java.util.Map m) {
		if (m == null)
			return null;
		DataColumn c = new DataColumn((String) m.get("name"), (String) m.get("title"),
				StrUtil.obj2int(m.get("sqlType")));
		c.tableName = (String) m.get("table");
		c.size = StrUtil.obj2int(m.get("size"));
		c.decimals = StrUtil.obj2int(m.get("decimals"));
		c.minDecimals = StrUtil.obj2int(m.get("minDecimals"));
		c.flags = StrUtil.obj2int(m.get("flags"));
		c.displayFlags = StrUtil.obj2int(m.get("displayFlags"));
		c.displayWidth = StrUtil.obj2int(m.get("displayWidth"));
		// c.v
		// displayFlags
		Object svalueDelim = m.get("valueDelim");
		if (svalueDelim instanceof Character)
			c.valueDelim = (Character) svalueDelim;
		else if (svalueDelim instanceof String && ((String) svalueDelim).length() > 0)
			c.valueDelim = ((String) svalueDelim).charAt(0);
		c.valueMap = (java.util.Map) m.get("valueMap");
		// cpmaster,
		return c;
	}

	// @Override
	public java.util.Map toMap() {
		java.util.Map<String, Object> m = new HashMap();
		m.put("name", columnName);
		if (tableName != null) {
			m.put("table", tableName);
		}
		if (sqlType != 0) {
			m.put("sqlType", sqlType);
		}
		if (size != 0)
			m.put("size", size);
		if (decimals >= 0 && (com.hibo.bas.util.SqlUtil.flagSqlType(sqlType) & 2) != 0) {
			m.put("decimals", decimals);
		}
		if (minDecimals > 0) {
			m.put("minDecimals", minDecimals);
		}
		if (flags != 0) {
			m.put("flags", flags);
		}
		if (displayFlags != 0) {
			m.put("displayFlags", displayFlags);
		}
		if (displayWidth > 0) {
			m.put("displayWidth", displayWidth);
		}
		if (displayCaption != null) {
			m.put("title", displayCaption);
		}
		if (sqlExpr != null) {
			m.put("sqlExpr", sqlExpr);
		}
		return m;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("column=").append(columnName);
		if (displayCaption != null)
			sb.append(",discap=").append(displayCaption);
		sb.append(",sqlType=").append(sqlType);
		return sb.toString();
	}

	public static DataColumn[] decodeColumns(Object a[]) {
		if (a == null)
			return null;
		DataColumn[] columns = new DataColumn[a.length];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = a[i] instanceof DataColumn ? (DataColumn) a[i] : jsonDecode((Map) a[i]);
		}
		return columns;
	}

	/*
	 * 由客户端 Table 设置
	 */
	transient java.util.Comparator<Object> valueCmp;

	public void setValueCompare(java.util.Comparator<Object> valueCmp) {
		this.valueCmp = valueCmp;
	}

	/*
	 * 由 UIDataSet 使用，
	 */
	public transient java.util.Map<String, String[]> moreNameCodes;
	/*
	 * 临时 序号
	 */
	public transient int idx;

}
