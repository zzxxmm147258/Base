package com.hibo.bas.util;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能：SQL组织工具类
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年10月22日 下午3:03:35
 * </p>
 * <p>
 * 类全名：com.hibo.bas.util.SqlUtil
 * </p>
 * 作者：cgh 初审： 复审： 1 SQLserver、 2 Oracle 、3 MySQL 5 DB2
 */
public class SqlUtil {

	static public String sqlModFunction(int databaseType, String column, int x) {
		if (databaseType == 2 || databaseType == 3 || databaseType == 5) // Oracle,MySql
			return "mod(" + column + ',' + x + ')';

		return "(" + column + "%" + x + ")";
	}

	static public String sqlModFunction(int databaseType, String column, String x) {
		if (databaseType == 2 || databaseType == 3 || databaseType == 5) // Oracle,MySql
			return "mod(" + column + ',' + x + ')';
		return "(" + column + " % " + x + ")";
	}

	/*
	 * 取证 floor(2.8) = 2
	 */
	static public String sqlDivFunction(int databaseType, String column, int x) {
		if (databaseType == 2 || databaseType == 3 || databaseType == 5) // MySQL
			return "floor(" + column + '/' + x + ")";
		if (databaseType == 7)
			return "(" + column + '\\' + x + ")";
		return "(" + column + '/' + x + ")";
	}

	static public String sqlDivFunction(int databaseType, String column, String x) {
		if (databaseType == 2 || databaseType == 3 || databaseType == 5) // MySQL
			return "floor(" + column + '/' + x + ")";
		if (databaseType == 7)
			return "(" + column + '\\' + x + ")";
		return "(" + column + '/' + x + ")";
	}

	// n = 0,1,2,3,4,....
	static public String sqlBitFunction(int databaseType, String column, int n) {
		if (databaseType == 1 || databaseType == 3)
			return column + "&" + (1 << n);
		if (databaseType == 2)
			return "bitand(" + column + "," + (1 << n) + ")";
		String m = sqlModFunction(databaseType, column, 1 << (n + 1));
		return sqlDivFunction(databaseType, m, 1 << n);
		/*
		 * floor(mode(col,8)/4)
		 */
	}

	// n : 1,2,4,8,16,.....
	static public String sqlBitAndFunction(int databaseType, String column, String n) {
		if (databaseType == 2 || databaseType == 5) // Oracle
			return "bitand(" + column + "," + n + ")";
		return column + "&" + n;
	}

	static public String sqlBitAndFunction(int databaseType, String column, int n) {
		return sqlBitAndFunction(databaseType, column, Integer.toString(n));
	}

	// n2 = n1 * 2
	static public String sqlBitAndFunction3(int databaseType, String column, String n1, String n2) {
		if (databaseType == 5) {
			String m = sqlModFunction(databaseType, column, n2); // m = 1 ,..
																	// n2-1
			return sqlDivFunction(databaseType, m, n1);
		}
		return sqlBitAndFunction(databaseType, column, n1);
	}

	static public String sql01Function(int databaseType, String column, int x) {
		if (databaseType == 2 || databaseType == 3 || databaseType == 5) // MySQL
			return "floor(" + column + '/' + x + ")";
		if (databaseType == 7) // Access
			return "(" + column + '\\' + x + ")";
		return "(" + column + '/' + x + ")";
	}

	static public String sql01Function(int databaseType, int x, String column) {
		if (databaseType == 2 || databaseType == 3 || databaseType == 5) // MySQL
			return "floor(" + x + '/' + column + ")";
		if (databaseType == 7) // Access
			return "(" + x + '\\' + column + ")";
		return "(" + x + '/' + column + ")";
	}

	static public String sqlSignFunction(int databaseType, String expr) {
		return "sign(" + expr + ")";
	}

	static public String sqlRoundFunction(int databaseType, String expr, int decimals) {
		return "round(" + expr + "," + decimals + ")";
	}

	static public String sqlDateConstant(int databaseType, int year, int month, int day) {
		if (databaseType == 2) // oracel
			return "TO_DATE('" + toStr2(month) + '/' + toStr2(day) + '/' + year + "','mm/dd/yyyy')";
		else if (databaseType == 3 || databaseType == 5)
			return "'" + year + '-' + toStr2(month) + '-' + toStr2(day) + " 00:00:00'";
		String d = "'";
		if (databaseType == 7) // access
			d = "#";
		return d + year + '-' + toStr2(month) + '-' + toStr2(day) + d;
	}

	static public String sqlDateConstant(int databaseType, int year, int month, int day, int hours, int min, int sec) {
		if (databaseType == 2) // oracel
			return "TO_DATE('" + toStr2(month) + '/' + toStr2(day) + '/' + year + ' ' + toStr2(hours) + ':'
					+ toStr2(min) + ':' + toStr2(sec) + "','mm/dd/yyyy hh24:mi:ss')";
		else if (databaseType == 3)
			return "'" + year + '-' + toStr2(month) + '-' + toStr2(day) + " " + toStr2(hours) + ':' + toStr2(min) + ':'
					+ toStr2(sec) + "'";
		String d = "'";
		if (databaseType == 7) // access
			d = "#";
		return d + year + '-' + toStr2(month) + '-' + toStr2(day) + ' ' + toStr2(hours) + ':' + toStr2(min) + ':'
				+ toStr2(sec) + d;
	}

	static public String sqlDateConstant(int databaseType, String year, String month, String day) {
		if (databaseType == 2) // oracel
			return "TO_DATE('" + month + '/' + day + '/' + year + "','mm/dd/yyyy')";
		else if (databaseType == 3 || databaseType == 5)
			return "'" + year + '-' + month + '-' + day + " 00:00:00'";
		String d = "'";
		if (databaseType == 7)
			d = "#";
		return d + year + '-' + month + '-' + day + d;
	}

	static public String sqlDateConstant(int databaseType, String date) {
		return sqlDateConstant(databaseType, DateUtil.toDate(date));
	}

	static public String sqlDateConstant(int databaseType, String date, boolean hms) {
		return sqlDateConstant(databaseType, DateUtil.toDate(date), hms);
	}

	static public String sqlDateConstant(int databaseType, java.util.Date date) {
		if (date == null)
			return null;
		return sqlDateConstant(databaseType, DateUtil.getDateYear(date), DateUtil.getDateMonth(date),
				DateUtil.getDateDay(date));
	}

	static public String sqlDateConstant(int databaseType, java.util.Date date, boolean hms) {
		if (date == null)
			return null;
		final int dateE[] = DateUtil.getDateElements(date);
		if (hms && (dateE[3] != 0 || dateE[4] != 0 || dateE[5] != 0)) {
			return sqlDateConstant(databaseType, dateE[0], dateE[1], dateE[2], dateE[3], dateE[4], dateE[5]);
		}
		return sqlDateConstant(databaseType, dateE[0], dateE[1], dateE[2]);
	}

	static public String sqlDateDiffConstant(int databaseType, java.util.Date date, String expr) {
		return sqlDateDiffConstant(databaseType, sqlDateConstant(databaseType, date), expr);
	}

	static public String sqlDateDiffConstant(int databaseType, String expr, java.util.Date date) {
		return sqlDateDiffConstant(databaseType, expr, sqlDateConstant(databaseType, date));
	}

	static public String sqlDateDiffConstant(int databaseType, String expr1, String expr2) {
		if (databaseType == 2 || databaseType == 5) // oracle
			return expr2 + "-" + expr1;
		return "datediff(day," + expr1 + "," + expr2 + ")";
	}

	static public String sqlCurrDateConstant(int databaseType) {
		if (databaseType == 5)
			return "current date";
		if (databaseType == 2)
			return "sysdate"; // ??
		return "getdate()";
	}

	static public String sqlLeftFunction(int databaseType, String expr, int length) {
		return sqlLeftFunction(databaseType, expr, "" + length);
	}

	static public String sqlLeftFunction(int databaseType, String expr, String length) {
		if (databaseType == 2 || databaseType == 5) // oracle
			return "substr(" + expr + ",1," + length + ")";
		if (databaseType == 3) // sybase
			return "substring(" + expr + ",1," + length + ")";
		return "left(" + expr + "," + length + ")";
	}

	static public String sqlSubStrFunction(int databaseType, String expr, int start, int length) {
		if (databaseType == 2 || databaseType == 5) // oracle
			return "substr(" + expr + "," + start + "," + length + ")";
		if (databaseType == 7) // access
			return "mid(" + expr + "," + start + "," + length + ")";
		return "substring(" + expr + "," + start + "," + length + ")";
	}

	static public String sqRightFunction(int databaseType, String expr, int length) {
		if (databaseType == 2 || databaseType == 5) // oracle
			return "substr(" + expr + ",-" + length + ")";
		return "right(" + expr + "," + length + ")";
	}

	static public String sqRightFunction(int databaseType, String expr, String length) {
		if (databaseType == 2 || databaseType == 5) // oracle
			return "substr(" + expr + ",-" + length + ")";
		return "right(" + expr + "," + length + ")";
	}

	static public String sqlSubStrFunction(int databaseType) {
		if (databaseType == 2 || databaseType == 5) // oracle
			return "substr";
		if (databaseType == 7) // oracle
			return "mid";
		return "substring";
	}

	static public String sqlSubStrFunction(int databaseType, String expr, String start, String length) {
		if (databaseType == 2 || databaseType == 5) // oracle
			return "substr(" + expr + "," + start + "," + length + ")";
		if (databaseType == 7) // access
			return "mid(" + expr + "," + start + "," + length + ")";
		
		return "substring(" + expr + "," + start + "," + length + ")";
	}

	static public String sqlToStrFunction(int databaseType, String expr) {
		return sqlToStrFunction(databaseType, expr, 0);
	}

	static public String sqlToStrFunction(int databaseType, String expr, int maxSize) {
		if (databaseType == 2) // oracle
			return "to_char(" + expr + ")";
		if (databaseType == 5)
			return "char(" + expr + ")";
		if (maxSize > 0 && databaseType == 1)
			return "convert(varchar(" + maxSize + ")," + expr + ")";

		if (databaseType == 3)
			return "cast(" + expr + " as char)";
		return "str(" + expr + ")"; // convert(varchar(3000), g.mixmake)
	}

	static public String sqlToStrFunction(int databaseType, String expr, String maxSize) {
		if (databaseType == 1)
			return "convert(varchar(" + maxSize + ")," + expr + ")";
		return sqlToStrFunction(databaseType, expr, 0);// "str("+expr+")";
														// //convert(varchar(3000),
														// g.mixmake)
	}

	static public String sqlToIntFunction(int databaseType, String expr) {
		switch (databaseType) {
		case 1:
			return "convert(integer," + expr + ")"; // sql server
		case 2:
		case 3:
			return "cast(" + expr + " as signed integer)";
		case 5:
			return "to_number(" + expr + ")";
		}
		return expr;
	}

	static public String sqlToNumericFunction(int databaseType, String expr) {
		switch (databaseType) {
		case 1:
			return "convert(numeric," + expr + ")"; // sql server
		case 2:
			return "to_number(" + expr + ")";
		case 3:
		case 5:
			return "cast(" + expr + " as  decimal(20,4))";
		}
		return expr;
	}

	static public String sqlNameLengthFunction(int databaseType) {
		return (databaseType == 2 || databaseType == 3 || databaseType == 5) ? "length" : "len";
	}

	// ymd==1,2,3 for yaer,month,day
	static public String sqlYMDFunction(int databaseType, String expr, int ymd) {
		switch (ymd) {
		case 1:
			if (databaseType == 2) // oracle
				return "to_number(to_char(" + expr + ",'yyyy'))";
			return "year(" + expr + ")";
		case 2:
			if (databaseType == 2) // oracle
				return "to_number(to_char(" + expr + ",'mm'))";
			return "month(" + expr + ")";
		case 3:
			if (databaseType == 2) // oracle
				return "to_number(to_char(" + expr + ",'dd'))";
			return "day(" + expr + ")";
		}
		throw new java.lang.IllegalArgumentException();
	}

	static public String sqlStrConcatFunction(int databaseType, String... exprs) {
		if (databaseType == 2 || databaseType == 5)
			return StrUtil.strcat(exprs, "||", null);
		if (databaseType == 3)
			return "concat(" + StrUtil.strcat(exprs, ",", null) + ")";
		return StrUtil.strcat(exprs, "+", null);
	}

	/*
	 * select
	 * modipwddate,convert(datetime,convert(char(4),DATEPART(yyyy,modipwddate))
	 * +'-'+convert(char(2),DATEPART(mm,modipwddate))
	 * +'-'+convert(char(2),DATEPART(dd,modipwddate))) from wcode
	 */
	static public String toDateFunction(int databaseType, String expr) {
		if (databaseType == 2 || databaseType == 5) // oracle
			return "to_date(to_char(" + expr + ",'yyyy-mm-dd'),'yyyy-mm-dd')";
		return "convert(datetime,convert(char(10)," + expr + ",20))";
	}

	static public String sqlIsNullFunction(int databaseType, String expr, String nullExpr) {
		if (databaseType == 2 || databaseType == 5)
			return "nvl(" + expr + "," + nullExpr + ")";
		if (databaseType == 3)
			return "ifnull(" + expr + "," + nullExpr + ")";
		return "isnull(" + expr + "," + nullExpr + ")";
	}

	static public String sqlUpperFunction(int databaseType, String expr) {
		if (databaseType == 7 || databaseType == 9)
			return "upper(" + expr + ")";
		return "upper(" + expr + ")";
	}

	static public String sqlLowerFunction(int databaseType, String expr) {
		return "lower(" + expr + ")";
	}

	static public String ymBetweenFilter(String yearColumn, String monthColumn, int fromYear, int fromMonth, int toYear,
			int toMonth) {
		if (fromYear == toYear) {
			if (fromMonth == toMonth)
				return yearColumn + "=" + fromYear + " and " + monthColumn + "=" + fromMonth;
			if (fromMonth > toMonth)
				return "1=2";
			return yearColumn + "=" + fromYear + " and " + monthColumn + " between " + fromMonth + " and " + toMonth;
		}
		if (fromYear > toYear)
			return "1=2";
		if (fromYear == 0) {
			return yearColumn + "<" + toYear + " or (" + yearColumn + "=" + toYear + " and " + monthColumn + "<="
					+ toMonth + ")";
		}
		// fromYear < toYear :
		String f = "((" + yearColumn + "=" + fromYear + " and " + monthColumn + ">=" + fromMonth + ")";
		if (toYear > fromYear + 1) {
			if (fromYear + 1 == toYear - 1)
				f += " or " + yearColumn + "=" + (fromYear + 1);
			else
				f += " or " + yearColumn + " between " + (fromYear + 1) + " and " + (toYear - 1);
		}
		return f + " or (" + yearColumn + "=" + toYear + " and " + monthColumn + "<=" + toMonth + "))";
	}

	static public String addDateRangeFilter(int databaseType, String filter1, String link, String column, String date1,
			String date2, int dateOptions) {
		return addRangeFilter(databaseType, Types.DATE, filter1, link, column, date1, date2, dateOptions);
	}

	/*
	 * dateOptions : #4 ---
	 */
	static public String addRangeFilter(int databaseType, int sqlType, String filter1, String link, String column,
			String value1, String value2, int dateOptions) {
		if ((dateOptions & 1) != 0) {
			try {
				value1 = sqlConstant(databaseType, sqlType, value1);
			} catch (Exception ex) {
			}
			try {
				if (sqlType >= 91 && sqlType <= 93) // Types.DATE &&
													// sqlType==Types. )
				{
					java.util.Date sqldate2 = DateUtil.toDate(value2);
					if (sqldate2 == null)
						value2 = null;
					else {
						if ((dateOptions & 2) != 0)
							sqldate2 = DateUtil.incDate(sqldate2, 1);
						if ((dateOptions & 4) != 0)
							sqldate2 = DateUtil.setDayEndTime(sqldate2);
						value2 = sqlDateConstant(databaseType, sqldate2, sqlType != Types.DATE);
					}
				} else {
					value2 = sqlConstant(databaseType, sqlType, value2);
				}
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
		}
		String filter2 = null;
		{
			if (value1 != null && value2 != null)
				filter2 = column + " between " + value1 + " and " + value2;
			else if (value1 != null)
				filter2 = column + ">=" + value1;
			else if (value2 != null)
				filter2 = column + "<=" + value2;
		}
		if (filter2 == null)
			return filter1;
		return filter1 == null || filter1.trim().length() == 0 ? filter2 : filter1 + ' ' + link + ' ' + filter2;
	}

	static public String sqlConstant(int databaseType, int sqlType, String value) {
		if (value == null)
			return null;
		switch (sqlType) {
		case Types.DATE:
		case Types.TIMESTAMP:
		case Types.TIME:
			return sqlDateConstant(databaseType, value, sqlType != Types.DATE); // 2006-01-19
																				// 加
																				// sqlType!=Types.DATE
		case Types.VARCHAR:
		case Types.CHAR:
		case -9: {
			return sqlStringConstant(value);
		}
		}
		return value;
	}

	static public String sqlConstant(int databaseType, Object value) {
		if (value == null)
			return null;
		if (value instanceof java.util.Date)
			return sqlDateConstant(databaseType, (java.util.Date) value, true);
		if (value instanceof String)
			return sqlStringConstant((String) value);
		return value.toString();
	}

	static public String sqlStringConstant(String value) {
		return sqlStringConstantTo(value, null).toString();// return
															// "'"+sb.toString()
															// + value+"'";
	}

	static private StringBuffer sqlStringConstantTo(String value, StringBuffer sb) {
		if (sb == null)
			sb = new StringBuffer();
		sb.append('\'');
		for (;;) {
			int p = value.indexOf('\'');
			if (p < 0)
				break;
			sb.append(value.substring(0, p + 1));
			sb.append('\'');
			value = value.substring(p + 1);
		}
		sb.append(value);
		sb.append('\'');
		return sb;
	}

	static public String toString(java.sql.Clob clobValue) throws SQLException, IOException {
		java.io.Reader is = clobValue.getCharacterStream(); // blobValue.getBinaryStream();
		try {
			return StrUtil.toString(is);
		} finally {
			try {
				is.close();
			} catch (Throwable ex) {
			}
		}
	}

	static public String buildInFilter(String column, String inValues[]) {
		return buildInFilter(column, inValues, false);
	}

	static public String buildInFilter(String column, String inValues[], boolean not) {
		if (inValues == null || inValues.length == 0)
			return null;
		final StringBuffer sb = new StringBuffer();
		sb.append(column);
		if (inValues.length == 1) {
			sb.append(not ? "<>" : "=");
			sqlStringConstantTo(inValues[0], sb);
		} else {
			sb.append(not ? " not in " : " in ");
			for (int i = 0; i < inValues.length; i++) {
				sb.append(i == 0 ? '(' : ',');
				sqlStringConstantTo(inValues[i], sb);
			}
			sb.append(')');
		}
		return sb.toString();
	}

	static public String buildInFilter(String column, String inValues[], int maxInCount) {
		if (inValues == null || inValues.length == 0)
			return null;
		if (maxInCount <= 0)
			throw new java.lang.IllegalArgumentException();
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inValues.length; i++) {
			if (i % maxInCount == 0) {
				if (i > 0)
					sb.append(") or ");
				sb.append(column).append(" in (");
			} else {
				sb.append(',');
			}
			sqlStringConstantTo(inValues[i], sb);
		}
		sb.append(')');
		return sb.toString();
	}

	static public String buildInFilter(String column, String inValues[], java.util.Map sqlParams) {
		if (inValues == null || inValues.length == 0)
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append(column);
		if (inValues.length == 1) {
			sb.append("=:").append(column);// .append("0");
			sqlParams.put(column, inValues[0]);
		} else {
			sb.append(" in ");
			for (int i = 0; i < inValues.length; i++) {
				sb.append(i == 0 ? '(' : ',').append(':').append(column).append('_').append(i);
				sqlParams.put(column + "_" + i, inValues[i]);
			}
			sb.append(')');
		}
		return sb.toString();
	}

	static public String buildDateFilter(int dbType, String column, String dateValues[], boolean caseTime) {
		return buildDateFilter(dbType, column, dateValues, null, caseTime);
	}

	/*
	 * op : =,>,<,>=,<=,<>,///
	 */
	static public String buildDateFilter(int dbType, String column, String dateValues[], String op, boolean caseTime) {
		if (dateValues == null || dateValues.length == 0)
			return null;
		if (op == null)
			op = "=";
		StringBuffer sb = new StringBuffer();
		int n = 0;
		for (int i = 0; i < dateValues.length; i++) {
			java.util.Date dateA[] = DateUtil.parseDateRange(dateValues[i], null, caseTime);
			if (dateA == null)
				continue;
			if (n > 0)
				sb.append(" or ");
			if (dateA[0] != null && dateA[1] != null && dateA[0].getTime() == dateA[1].getTime()) {
				sb.append(column).append(op).append(sqlDateConstant(dbType, dateA[0]));
			} else if (dateA[0] != null && dateA[1] != null) {
				sb.append(column).append(" between ").append(sqlDateConstant(dbType, dateA[0], caseTime))
						.append(" and ").append(sqlDateConstant(dbType, dateA[1], caseTime));
			} else if (dateA[0] != null) {
				sb.append(column).append(">=").append(sqlDateConstant(dbType, dateA[0], caseTime));
			} else if (dateA[1] != null) {
				sb.append(column).append("<=").append(sqlDateConstant(dbType, dateA[1], caseTime));
			} else {
				assert(false);
			}
			n++;
		}
		if (n > 1) {
			sb.insert(0, '(').append(')');
		}
		return sb.length() == 0 ? null : sb.toString();
	}

	static public String buildInFilter(String columns[], Object inValues[][]) {
		if (inValues == null || inValues.length == 0)
			return null;
		int nr = inValues.length;
		int nc = columns.length;
		StringBuffer sb = new StringBuffer();
		for (int r = 0; r < nr; r++) {
			if (r > 0)
				sb.append(" or ");
			if (nc > 1)
				sb.append('(');
			for (int j = 0; j < nc; j++) {
				if (j > 0)
					sb.append(" and ");
				sb.append(columns[j]).append('=');
				Object v = inValues[r][j];
				if (v instanceof String)
					sqlStringConstantTo((String) v, sb);
				else
					sb.append(v);
			}
			if (nc > 1)
				sb.append(')');
		}
		return sb.toString();
	}

	/*
	 * options & 1 : 多于一个时 加 括号 2 : 右加 ％ 4 ： 左加 % 测试 ：
	 * 
	 * String values[] = {"123"}; String values[] = {"123","abc"}; String
	 * values[] = {"12%3","ab_c","222"};
	 * 
	 * snsoft.sql.SqlUtil1.buildLikeFilter("field",values,0);
	 * snsoft.sql.SqlUtil1.buildLikeFilter("field",values,1);
	 * snsoft.sql.SqlUtil1.buildLikeFilter("field",values,2);
	 * snsoft.sql.SqlUtil1.buildLikeFilter("field",values,3);
	 */
	static public String buildLikeFilter(String column, String values[], int options) {
		if (values == null || values.length == 0)
			return null;
		StringBuffer sb = new StringBuffer();
		int n = 0;
		for (int i = 0; i < values.length; i++) {
			String v = values[i];
			if (v == null)
				continue;
			if ((options & 2) != 0 && !v.endsWith("%"))
				v += "%";
			if ((options & 4) != 0 && !v.startsWith("%"))
				v = "%" + v;
			if (n > 0)
				sb.append(" or ");
			n++;
			sb.append(column);
			if (v.indexOf('%') >= 0 || v.indexOf('_') >= 0)
				sb.append(" like ");
			else
				sb.append('=');
			sqlStringConstantTo(v, sb);
		}
		if (sb.length() == 0)
			return null;
		if (n > 1 && (options & 1) != 0) {
			sb.insert(0, '(');
			sb.append(')');
		}
		return sb.toString();
	}

	static public String buildLikeFilter(String column, String values, char valieDelim, int options) {
		return buildLikeFilter(column, StrUtil.splitString(values, ','), options);
	}

	/**
	 * @return bit 1: Number Type, 2: Number Type(有小数位), 4: String Type 8 : Date
	 *         16 : Number (2=NUMERIC,3=DECIMALS) 32 : Boolean
	 */
	static public int flagSqlType(int sqlType) {
		int flag = 0;
		if ((sqlType >= 2 && sqlType <= 8) || sqlType == -6 || sqlType == -5) {
			flag |= 1;
			if (sqlType == 2 || sqlType == 3 || sqlType == 6 || sqlType == 7 || sqlType == 8)
				flag |= 2;
			if (sqlType == 2 || sqlType == 3)
				flag |= 16;
		} else if (sqlType == 12 || sqlType == 1 || sqlType == -9)
			flag |= 4;
		else if (sqlType >= 91 && sqlType <= 93)
			flag |= 8;
		else if (sqlType == -7) // java.sql.Types.BIT )
			flag |= 32;
		return flag;
	}

	final static private String toStr2(int x) {
		return x < 10 ? "0" + x : "" + x;
	}

	final public static java.util.Map<String, Object> getSqlParameters(String sql, java.util.Map paramGetter) {
		if (paramGetter == null)
			return null;
		return getSqlParameters(getSqlParameterNames(sql), paramGetter);
	}

	final public static java.util.Map<String, Object> getSqlParameters(String names[], java.util.Map paramGetter) {
		if (paramGetter == null || names == null || names.length == 0)
			return null;
		java.util.Map<String, Object> h = new java.util.Hashtable<String, Object>();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			Object v = paramGetter.get(name);
			if (v != null)
				h.put(name, v);
			v = paramGetter.get(name + ".[sqltype]");
			if (v != null)
				h.put(name + ".[sqltype]", v);
		}
		return h;
	}

	final public static String[] getSqlParameterNames(String sql)// ,snsoft.util.ValueMap
																	// paramGetter)
	{
		if (sql == null)
			return null;
		int lsql = sql.length();
		char quoteChar = 0; // boolean hasParameter = false;
		java.util.List<String> paramNameVector = new java.util.ArrayList<String>();
		for (int i = 0; i < lsql;) {
			char c = sql.charAt(i);
			if (quoteChar == 0) {
				if ((c == ':' || c == '?') && i < lsql - 1 && Character.isJavaIdentifierStart(sql.charAt(i + 1))) {
					char ca[] = new char[128];
					int j = 0;
					for (i++; i < lsql; i++) {
						c = sql.charAt(i);
						if (!Character.isJavaIdentifierPart(c) && c != '.')
							break;
						ca[j++] = c;
					}
					paramNameVector.add(new String(ca, 0, j).toLowerCase());
					continue;
				}
			}
			if (c == quoteChar) {
				quoteChar = 0;
			} else if (quoteChar == '\'')
				quoteChar = '\'';
			i++;
		}
		if (paramNameVector.size() == 0)
			return null;
		return (String[]) paramNameVector.toArray(new String[paramNameVector.size()]);
	}

	static public String[] MysqlKeys = { "range", "tables" };

	static public boolean isMysqlKey(String n) {
		return StrUtil.indexOf(MysqlKeys, n) >= 0;
	}

	static public String sqlSelectConstNull(int dbType) {
		return sqlSelectConstNull(dbType, 0);
	}

	static public String sqlSelectConstNull(int dbType, int sqlType) {
		if (dbType == 5) {
			String t = "integer";
			if (sqlType == 12 || sqlType == -9)
				t = "varchar(255)";
			else if (sqlType == 91)
				t = "date";
			else if (sqlType == 93)
				t = "timestamp";
			else if (sqlType == 1)
				t = "char";
			else if (sqlType == 3 || sqlType == 2) // java.sql.Types.DECIMAL)
				t = "decimal";
			return "cast(null as " + t + ")";
		}
		return "null";
	}

	/*
	static public DataConstraint[] toConstraints(String[][] ekeys) {
		if (ekeys == null)
			return null;
		java.util.List<Object[]> v = new java.util.ArrayList<Object[]>();
		for (int i = 0; i < ekeys.length; i++) {
			for (int j = 0; j < 6; j++) {
				if (ekeys[i][j] != null)
					ekeys[i][j] = ekeys[i][j].toLowerCase();
			}
			int j = 0;
			Object a[] = null;
			for (; j < v.size(); j++) {
				a = v.get(j);// (Object[])v.elementAt(j);
				if (Data.objEquals(ekeys[i][0], a[0]) && ekeys[i][1].equals(a[1]) && ekeys[i][3].equals(a[3])
						&& ekeys[i][4].equals(a[4])) {
					break;
				}
			}
			if (j >= v.size())
				v.add(new Object[] { ekeys[i][0], ekeys[i][1], new String[] { ekeys[i][2] }, ekeys[i][3], ekeys[i][4],
						new String[] { ekeys[i][5] } });
			else {
				a[2] = ArrayUtil.addElement(String.class, a[2], ekeys[i][2], true);
				a[5] = ArrayUtil.addElement(String.class, a[5], ekeys[i][5], true);
			}
		}
		DataConstraint c[] = new DataConstraint[v.size()];
		for (int i = 0; i < c.length; i++) {
			Object[] a = v.get(i);// (Object[])v.elementAt(i);
			c[i] = new DataConstraint((String) a[0], (String) a[1], (String[]) a[2], (String) a[3], (String) a[4],
					(String[]) a[5]);
		}
		return c;
	}
	*/

	static public String buildYearMonthFilter(String ycolumnName, String mcolumnName, int fromYear, int fromMonth,
			int toYear, int toMonth) {
		if (fromYear == toYear) {
			return ycolumnName + "=" + fromYear + " and " + (fromMonth == toMonth ? mcolumnName + "=" + fromMonth
					: mcolumnName + " between " + fromMonth + " and " + toMonth);
		}
		String filter = "(" + ycolumnName + "=" + fromYear + " and " + mcolumnName + ">=" + fromMonth + ") or ("
				+ ycolumnName + "=" + toYear + " and " + mcolumnName + "<=" + toMonth + ")";
		if (toYear > fromYear + 1) {
			if (fromYear + 1 == toYear - 1)
				filter += " or " + ycolumnName + "=" + (fromYear + 1);
			else
				filter += " or " + ycolumnName + " between " + (fromYear + 1) + " and " + (toYear - 1);
		}
		return "(" + filter + ")";
	}
	
	public static final String sqlType2Name(int sqlType)
	{
		try
		{
		Class cls = java.sql.Types.class; // Class.forName("java.sql.Types"); // SecurityException
		java.lang.reflect.Field[] fields = cls.getFields();
		for(int i=0;i<fields.length;i++)
		{
			if( fields[i].getInt(null)==sqlType )
				return fields[i].getName();
		}
		} catch( Throwable ex )
		{
		}
		return null;
	}
	/**
	 * @功能:返回mybatis分页参数
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年6月22日 上午11:18:19
	 * @param page //页号 
	 * @param limit//每页数据条数 
	 * @param orders排序："age.asc,gender.desc";//如果你想排序的话逗号分隔可以排序多列 
	 * @param containsTotalCount
	 * @return
	 */
	public static PageBounds pageParams(int page, int limit, String orders, boolean containsTotalCount){
		return new PageBounds(page, limit, Order.formString(orders), containsTotalCount);
	}
	public static PageBounds pageParams(int page, int limit, String orders){
		return new PageBounds(page, limit, Order.formString(orders));
	}
	public static PageBounds pageParams(int page, int limit,boolean containsTotalCount){
		PageBounds bounds = new PageBounds(page, limit);
		bounds.setContainsTotalCount(containsTotalCount);
		return bounds;
	}
	public static PageBounds pageParams(int page, int limit){
		return new PageBounds(page, limit);
	}
}
/*
 * 
 * 序号 简述 Access语法 SqlServer语法 Oracle语法 DB2语法 解决方案 01 系统时间 Date() GETDATE()
 * SYSDATE GetSysTimeStr 02 连接字符串 & + || + GetConcatStr 03 截取字符串 mid SubStr
 * SubString SubString GetSubStr 04 小写字符串 LCase Lower Lower Lower GetLowerStr 05
 * 大写字符串 UCase Upper Upper Upper GetUpperStr 06 查找字符串 InStr InStr CharIndex
 * InStr GetFindStr 07 替换空值 IIF+IsNull Coalesce Nvl Coalesce GetNullStr 08 条件取值
 * IIF Case+When+Else DeCode或Case IIF GetCaseStr 09 字段类型转换 Str、var、….
 * Convert或cast To_Char,To_Number. GetConvertStr GetConvertStr 10 日期字符串
 * ‘2004-10-9’ #2004-10-19# ‘2004-10-9’ GetDateStr 11 最大值加1 GetNextNumStr 12
 * Like语句函数 Like ‘101* Like ‘101%’ Like ‘101%’ GetLikeStr
 */
