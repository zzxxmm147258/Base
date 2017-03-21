package com.hibo.cms.lims;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import com.hibo.bas.dbutil.DataSource;
import com.hibo.bas.util.DateUtil;
import com.hibo.bas.util.MapUtil;
import com.hibo.bas.util.SqlParser;
import com.hibo.bas.util.SqlUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.bas.util.WorkSpace;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能： 查询条件构造
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年10月22日 下午2:44:50
 * </p>
 * <p>
 * 类全名：com.hibo.bas.lims.QueryFilterBuilder
 * </p>
 * 作者：cgh 初审： 复审：
 */
public class QueryFilterBuilder {

	static private String toTrimString(Object value) {
		if (value instanceof java.util.Date)
			return DateUtil.dateToString((java.util.Date) value);
		return value == null ? null : value.toString().trim();
	}

	public static boolean hasFilterForParamPrifix(Map params, String paramPrifix) {
		if (paramPrifix == null)
			paramPrifix = "";
		Object a[][] = MapUtil.getAttValues(params, paramPrifix + "*");
		return a != null && a.length > 0;
	}

	public static int getSqlType(String sqlTypeStr){
		int sqlType = 0;
		if (StrUtil.isStrIn("V,v",sqlTypeStr)){    //文本、字符串
			sqlType = 12;
		}else if (StrUtil.isStrIn("D,d",sqlTypeStr)){ //日期
			sqlType = 91;
		}else if (StrUtil.isStrIn("N,n",sqlTypeStr)){  //数值、数字
			sqlType = 2;
		}else if (StrUtil.isStrIn("I,i",sqlTypeStr)){   //整数
			sqlType = 4;
		}else if (StrUtil.isStrIn("B,b",sqlTypeStr)){   //布尔
			sqlType = -7;
		}else{
			sqlType = StrUtil.obj2int(sqlTypeStr,0);
		}
		return sqlType;
	}

	/**
	 * 构造 SQL 的过滤条件
	 * 
	 * @param db
	 *            数据库
	 * @param defaultTable
	 *            缺省表名
	 * @param params
	 *            查询参数 paramPrifix + name : value (name对应的条件值) paramPrifix +
	 *            name +".[from]" : 用于 between 方式的构造 paramPrifix + name +".[to]"
	 *            : 用于 between 方式的构造 paramPrifix + name +".[sqltype]" : 指定 name
	 *            对应的SQL类型 paramPrifix + name +".[%]" : 1:+%,2:%+,3:%+%
	 *            paramPrifix + name +".[delim]" : 分隔 字符 , 缺省为 逗号 paramPrifix +
	 *            "[filterexpr]" : 直接的 Filter， 其中可以含参数宏， 例如 ： rmb between
	 *            %rmb-0.01 and %rmb+0.01 , %rmb 从 params.get("rmb") , 取 (rmb 不
	 *            含前缀 paramPrifix ) paramPrifix + "[colfilter]" :
	 *            <columnname>:val1,val2,....; paramPrifix + name + "."+
	 *            <value>+".[filter]" : name 的值为<value>时的替换值 ( paramPrifix 一般为
	 *            "filter." ) name + ".[alter] : 参数的替换值， 其中 可以使用 宏， 例如 rmb 为一参数
	 *            rmb.[alter] = %rmb + 100.0 params 中 rmb 输入 17.00 时， 构造的 filter
	 *            为 rmb = 17.00 + 100.0 name +".[altercn]" 列名的替换值 例如 有 参数 acode
	 *            acode.[altercn] = %SQLLEFT$2(acode,3) 参数 acode 值为 1%,2% 时 ，构造的
	 *            filter 为 left(acode,3) like '1%' or left(acode,3) like '2%'
	 *
	 * @param paramPrifix
	 *            查询参数的前缀: 用于筛选params中的参数名
	 */
	
	/**
	 * 构造 SQL 的过滤条件
	 * 1、Params 中的KEY规则：以 paramPrifix 值为开头，为空时，以 filter 开头。
	 * 2、第二段为类型、连接符定义以[v_=_0_,],以_分隔，[0]为类型 v 字符、d日期、n数值、i为整数；[1]为连接符 =、like、>=等；
	 * 		[2]为字符是否需求为 %，1右加、2左加、3前后加；[3]为分隔符，就是值中有多个值，分开组织条件
	 * 3、第三段为字段，可以支持别名;
	 * 4、第四段为[from]、[to],就是区间条件， >= 或 <= 或  between
	 * 例：filter.[v_like_1].name:张    结果  name like '张%'
	 * @param params 
	 * @return
	 */
	public static String buildQueryFilter(Map params) {
		String wsp = DataSource.getDataSource();
		int dbType = WorkSpace.getWorkspace(wsp).dbType;
		return buildQueryFilter(dbType, null, params, null, 0);
	}
	
	/**
	 * 
	 * @param params
	 * @param shopCol  追加商铺权限
	 * @return
	 */
	public static String buildQueryFilter(Map params, String shopCol) {
		String wsp = DataSource.getDataSource();
		int dbType = WorkSpace.getWorkspace(wsp).dbType;
		return buildQueryFilter(dbType, null, params, null, 0, shopCol, null);
	}
	
	/**
	 * 
	 * @param params
	 * @param shopCol
	 * @param resourceId  资源号
	 * @return
	 */
	public static String buildQueryFilter(Map params, String shopCol, String resourceId) {
		String wsp = DataSource.getDataSource();
		int dbType = WorkSpace.getWorkspace(wsp).dbType;
		return buildQueryFilter(dbType, null, params, null, 0, shopCol, resourceId);
	}
	
	public static String buildQueryFilter(String defaultTable, Map params, String paramPrifix) {
		int dbType = WorkSpace.getWorkspace(DataSource.getDataSource()).dbType;
		return buildQueryFilter(dbType, defaultTable, params, paramPrifix, 0);
	}
	
	public static String buildQueryFilter(String defaultTable, Map params, String paramPrifix, String shopCol, String resourceId) {
		int dbType = WorkSpace.getWorkspace(DataSource.getDataSource()).dbType;
		return buildQueryFilter(dbType, defaultTable, params, paramPrifix, 0, shopCol, resourceId);
	}
	
	public static String buildQueryFilter(int dbType, String defaultTable, Map params, String paramPrifix, int maxLength) {
		return buildQueryFilter(dbType, defaultTable, params, paramPrifix, maxLength, "", null);
	}
	
	/**
	 * 
	 * @param dbType        数据库类型，1 SQL  2 oracle  3 MySql
	 * @param defaultTable  表名，目前没用到
	 * @param params        参数Map
	 * @param paramPrifix   参数名前缀，默认filter
	 * @param maxLength     防止SQL注册，查询条件值默认最大长度为 32，超过不后参数丢掉，若有超长要求，调用时传入
	 * @param shopCol       "" 表示不追加商户权限，null 或 有值，则追加
	 * @return
	 */
	public static String buildQueryFilter(int dbType, String defaultTable, Map params, String paramPrifix, int maxLength, String shopCol, String resourceId) {
		String limWhere = null;
		if (!"".equals(shopCol)){
			limWhere = ShopLimit.getShopLim(shopCol);
		}
		
		if (StrUtil.isnull(resourceId)){
			String lim = " 1=1 ";
			if (limWhere == null)
				limWhere = lim;
			else
				limWhere += " and "+lim;
		}
		if (params == null)
			return limWhere;
		
		if (maxLength <= 0)
			maxLength = 32;
		
		if (paramPrifix == null)
			paramPrifix = "filter.";
		if (dbType <= 0) {
			dbType = WorkSpace.getWorkspace(DataSource.getDataSource()).dbType;
		}
		Object a[][] = MapUtil.getAttValues(params, paramPrifix + "*");
		if (a == null || a.length == 0)
			return limWhere;
		// int opt_90_10 = 0;
		Map paramsAsMacro = null;// new Hashtable();

		String filter = null;
		for (int i = 0; i < a.length; i++) {
			String paramName = (String) a[i][0];
			if (paramPrifix.length() > 0)
				paramName = paramName.substring(paramPrifix.length());
			if (paramName.length() == 0)
				continue;
			Object value;
			if ((value = a[i][1]) == null)
				continue;
			String suffix = null;
			String paramName0 = paramName;
			int sqlType = 0;   
			String op = null;  //连接符  =  like 
			int likep=0;  //1 右加%， 2 左加%  3 左右加%
			String delimS = null;
			int p;
			
			if (paramName.charAt(paramName.length() - 1) == ']') {
				p = paramName.lastIndexOf(".[");
				if (p >= 0) {
					suffix = paramName.substring(p + 1);
					paramName = paramName.substring(0, p);
				}
			}
			
			String paramName1 = paramName;
			if (paramName.charAt(0) == '['){
				p = paramName.indexOf("].");
				if (p >= 0){
					String sqlTypeStr = paramName.substring(1, p);
					String[] typeArr = StrUtil.splitString(sqlTypeStr, '_');
					sqlType = getSqlType(typeArr[0]);
					if (typeArr.length > 1 && !"".equals(typeArr[1]) && typeArr[1] != null)
						op = " "+typeArr[1]+" ";
					else
						op = "=";
					if (typeArr.length > 2)
						likep = StrUtil.obj2int(typeArr[2]);
					if (typeArr.length > 3)
						delimS = typeArr[3];
					paramName = paramName.substring(p + 2);
				}else{
					sqlType = 12;
					op="=";
				}
			}
			
			if ("[to]".equals(suffix) && params.get(paramPrifix + paramName1 + ".[from]") == null) {
				suffix = "[from]";
				value = null;
			}
			if ("[filter]".equals(suffix) || "[sqltype]".equals(suffix) || "[to]".equals(suffix) || "[%]".equals(suffix)
					|| "[alter]".equals(suffix) || "[altercn]".equals(suffix) || "[cnmsvjoin]".equals(suffix)
					|| "[op]".equals(suffix) || "[..]".equals(suffix))
				continue;
			
			boolean bFrom = "[from]".equals(suffix);
			String paramValue = toTrimString(value); 
			paramValue = StrUtil.replace(paramValue, "'", "");
			
			if (sqlType == 0)
				sqlType = StrUtil.obj2int(params.get(paramPrifix + paramName + ".[sqltype]"));
			if (sqlType == 0)
				sqlType = StrUtil.obj2int(params.get(paramPrifix + paramName0 + ".[sqltype]"));
			
			Object val = bFrom ? params.get(paramPrifix + paramName1 + ".[to]") : null;
			String paramValue2 = getStrValue(val);
			paramValue2 = StrUtil.replace(paramValue2, "'", "");
			
			if ((paramValue == null || paramValue.length() == 0 || "%".equals(paramValue) || "*".equals(paramValue))
				&& (paramValue2 == null || paramValue2.length() == 0 || "%".equals(paramValue2) || "*".equals(paramValue2))){
				continue;
			}
			/*   暂不提供，有SQL注入风险
			{
			String f = (String) params.get(paramPrifix + paramName + "." + paramValue + ".[filter]");
				if (f != null) {
					filter = linkFilter(filter, f);
					continue;
				}
			}
			
			boolean bFilterexpr = paramName.startsWith("[filterexpr]");
			boolean bColfilter = paramName.startsWith("[colfilter]");

			*/
			
			String columnNames[] = null;
			final String joinColumnNames;// = " or ";
			{
				String columnName = paramName;
				columnNames = SqlParser.splitSqlExpr(columnName);
				String join = (String) params.get(paramName + ".[cnmsvjoin]");
				joinColumnNames = join == null ? " or " : " " + join + " ";
			}
			
			//final String delimS = (String) params.get(paramPrifix + paramName0 + ".[delim]");
			final char delim = delimS == null ? ',' : (delimS.length() == 1 ? delimS.charAt(0) : 0);
			final boolean isNullValue = "null".equalsIgnoreCase(paramValue);
			
			if (sqlType == 12 && paramValue != null && !isNullValue) {
				//int likep = StrUtil.obj2int(params.get(paramPrifix + paramName0 + ".[%]"));
				if ((likep & 3) != 0 && delim != 0 && paramValue.indexOf(delim) >= 0) {
					String av[] = StrUtil.splitString(paramValue, delim);
					for (int j = 0; j < av.length; j++) {
						if ((likep & 1) != 0 && !av[j].endsWith("%")){
							av[j] += "%";
						}
						if ((likep & 2) != 0 && !av[j].startsWith("%")){
							av[j] = "%" + av[j];
						}
					}
					paramValue = StrUtil.strcat(av, "" + delim, null);
				} else {
					if ((likep & 1) != 0 && !paramValue.endsWith("%")){
						paramValue += "%"; // 右加 % 号
					}
					if ((likep & 2) != 0 && !paramValue.startsWith("%")){
						paramValue = "%" + paramValue;
					}
				}
			}
			String orFilter = null;
			int nOr = 0;
			for (final String columnName : columnNames) {
				int flagSqlType = SqlUtil.flagSqlType(sqlType);
				String f = null;
				if (bFrom) {
					paramValue = sqlInjection(flagSqlType, paramValue, maxLength);
					paramValue2 = sqlInjection(flagSqlType, paramValue2, maxLength);
					f = SqlUtil.addRangeFilter(dbType, sqlType, null, null, columnName, paramValue, paramValue2, 1 );   //+ (sqlType == 93 && (opt_90_10 & 0x10000) != 0 ? 4 : 0)
				} else {
					/*
					if (bFilterexpr) 
					{
	                    f = paramValue;
					} else if (bColfilter)
					{
	                    String  vlist[] = StrUtil.splitString(paramValue,';');
	                    Hashtable h = new Hashtable();
	                    if( vlist!=null )
	                    {
	                        for(int j=0;j<vlist.length;j++)
	                        {
	                            String v = vlist[j].trim();
	                            p = v.indexOf(':');
	                            if( p>0 ) h.put(v.substring(0,p),v.substring(p+1));
	                        }
	                        f = buildQueryFilter( dbType, defaultTable, h, "", 0);
	                    } else {
	                    	f = null;
	                    }
					} else 
					*/
					if (sqlType == java.sql.Types.VARCHAR && (paramValue.indexOf('%') >= 0 || paramValue.indexOf('_') >= 0)) {
						String paramsList[] = delim > 0 ? StrUtil.splitString(paramValue, delim) : new String[] { paramValue };
						f = SqlUtil.buildLikeFilter(columnName, paramsList, 1);
					} else {
						String paramsList[] = delim > 0 ? StrUtil.splitString(paramValue, delim) : new String[] { paramValue };
						
						setF: if (isNullValue) {
							f = "(" + columnName + " is null or " + columnName + "=' ')";
						} else {
							//String op = (String) params.get(paramPrifix + paramName0 + ".[op]");
							if (op == null)
								op = "=";
							else if ("isnull".equals(op)) {
								f = columnName + " is null";
								break setF;
							} else if ("isnotnull".equals(op)) {
								f = columnName + " is not null";
								break setF;
							}
							// Types
							if (sqlType >= 91 && sqlType <= 93) {
								f = SqlUtil.buildDateFilter(dbType, columnName, paramsList, op, sqlType > 91);
							} else {
								String betweenJoin = (String) params.get(paramPrifix + paramName0 + ".[..]");
								StringBuffer sb = null;
								if (betweenJoin != null) {
									sb = new StringBuffer();
									if (paramsList.length > 1)
										sb.append('(');
								}
								for (int j = 0; j < paramsList.length; j++) {
									String s = paramsList[j];
									if (betweenJoin != null) {
										if (j > 0)
											sb.append(" or ");
										int q = s.indexOf(betweenJoin);
										if (q >= 0) {
											String s1 = s.substring(0, q).trim();
											String v1 = s1.length() == 0 ? null : SqlUtil.sqlConstant(dbType, sqlType, s1);

											String s2 = s.substring(q + betweenJoin.length()).trim();
											String v2 = s2.length() == 0 ? null : SqlUtil.sqlConstant(dbType, sqlType, s2);

											if (v2 == null) {
												if (v1 == null)
													continue;
												sb.append(columnName).append(">=").append(v1);
											} else if (v1 == null) {
												sb.append(columnName).append("<=").append(v2);
											} else {
												sb.append(columnName).append(" between ").append(v1).append(" and ").append(v2);
											}
										} else {
											sb.append(columnName).append('=').append(SqlUtil.sqlConstant(dbType, sqlType, s));
										}
									} else {
										s = sqlInjection(flagSqlType, s, maxLength);
										paramsList[j] = SqlUtil.sqlConstant(dbType, sqlType, s);
									}
								}

								if (sb != null) {
									if (sb.length() == 0)
										continue;
									if (paramsList.length > 1)
										sb.append(')');
									f = sb.toString();
								} else if (sqlType == 4 && columnName.indexOf('#') > 0) {
									final int q = columnName.indexOf('#');
									final int x = StrUtil.obj2int(paramsList[0]);
									if (x != 0) {
										int b = StrUtil.obj2int(columnName.substring(q + 1));
										f = SqlUtil.sqlBitAndFunction(dbType, columnName.substring(0, q), b) + "=" + x;
									} else {
										f = null;
									}
								} else {
									f = StrUtil.filterFormat("or", columnName + op + "%0",
											(flagSqlType & 1) != 0 ? columnName + " between %0 and %1" : null,
											paramsList, true);
								}
							}
						}
					}
				}
				if (f != null && f.length() > 0) {
					orFilter = orFilter == null ? f : orFilter + joinColumnNames + f; // "
													// "
					nOr++;
				}
			} // for(final String columnName : columnNames)
			if (orFilter != null && orFilter.length() > 0) {
				if (nOr > 1)
					orFilter = "(" + orFilter + ")";
				filter = linkFilter(filter, orFilter);
			}
		}
		if (limWhere != null && !StrUtil.isnull(filter))
			filter = limWhere+" and "+filter;
		
		return filter;
	}
	
	/**
	 * 把数组传为字符串
	 * @param vel
	 * @return
	 */
	private static String getStrValue(Object val){
		if (val instanceof String[] && ((String[])val).length >= 1){
			String[] vArr = (String[])val;
			String valueStr = "";
			for (int j = 0; j < vArr.length; j++) {
				valueStr = (j == vArr.length - 1) ? valueStr + vArr[j] : valueStr + vArr[j] + ",";
			}
			return valueStr;
		}else{
			return StrUtil.obj2str(val);
		}
	}
	
	/**
	 * 防SQL注入
	 * @param flagSqlType
	 * @param val
	 * @param maxLength
	 * @return
	 */
	private static String sqlInjection(int flagSqlType, String val, int maxLength){
		if (val == null)
			return val;
		
		if ((flagSqlType & 1) != 0){
			//数值
			try{
				StrUtil.obj2big(val);
			}catch(Exception ex){
				val = "0";
			}
		}else if ((flagSqlType & 8) != 0){
			//日期
			int r[] = new int[6];
			if (!DateUtil.parseDateTime(val,r))
				val = DateUtil.dateToString(new Date());
		}else{   // if ((flagSqlType & 4) != 0)
			//字符， 控制长度来防止注入
			if (val.length() > maxLength)
				val = val.substring(0,maxLength);
		}
		return val;
	}

	public static String addQueryFilter(String sql, int dbType, String defaultTable, Map params, String paramPrifix) {
		return addQueryFilter(sql, dbType, defaultTable, params, paramPrifix, null);
	}

	public static String addQueryFilter(String sql, int dbType, String defaultTable, final Map params, String paramPrifix, String listParamPrifix[]) {
		if (sql == null)
			return null;
		String sqlParts[][] = null;
		if (defaultTable == null) {
			SqlParser.sqlNormalize(sql, sqlParts = new String[7][]);
			if (sqlParts[1] != null) {
				int p = sqlParts[1][0].indexOf(',');
				defaultTable = p >= 0 ? sqlParts[1][0].substring(0, p) : sqlParts[1][0];
			}
		}
		String filterList[] = null, dftFilter = null;
		dftFilter = paramPrifix == null ? null : buildQueryFilter(dbType, defaultTable, params, paramPrifix, 0);
		if (listParamPrifix != null) {
			filterList = new String[listParamPrifix.length];
			for (int i = 0; i < filterList.length; i++) {
				filterList[i] = listParamPrifix[i] == null || listParamPrifix[i].length() == 0 ? dftFilter
						: buildQueryFilter(dbType, defaultTable, params, listParamPrifix[i], 0);
			}
		}
		if (dftFilter != null || !isNullStringArray(filterList)) {
			if (sqlParts == null)
				SqlParser.sqlNormalize(sql, sqlParts = new String[7][]);
			// sql = null;
			StringBuffer sqlBuffer = null;
			for (int i = 0; i < sqlParts[0].length; i++) {
				// String s = "select "+sqlParts[0][i]+" from "+sqlParts[1][i];
				StringBuffer sb = new StringBuffer("select ").append(sqlParts[0][i]).append(" from ")
						.append(sqlParts[1][i]);
				String filter = null;
				if (filterList != null && i < filterList.length)
					filter = filterList[i];
				else
					filter = dftFilter;
				if (filter == null) {
					if (sqlParts[2][i] != null)
						// s += " where "+sqlParts[2][i];
						sb.append(" where ").append(sqlParts[2][i]);
				} else {
					if (sqlParts[2][i] == null)
						// s += " where "+filter;
						sb.append(" where ").append(filter);
					else
						// s += " where ("+sqlParts[2][i]+") and ("+filter+")";
						sb.append(" where (").append(sqlParts[2][i]).append(") and (").append(filter).append(')');
				}
				if (sqlParts[3][i] != null)
					// s += " group by "+sqlParts[3][i];
					sb.append(" group by ").append(sqlParts[3][i]);
				if (sqlParts[4][i] != null)
					// s += " having "+sqlParts[4][i];
					sb.append(" having ").append(sqlParts[4][i]);
				if (sqlBuffer == null)
					sqlBuffer = sb;// .toString();
				else
					// sqlBuffer.append("\r\n union all\r\n").append(sb);
					sqlBuffer.append("\r\n").append(sqlParts[6][i]).append("\r\n").append(sb);
				// sql += "\r\n union all\r\n"+sb;
			}
			if (sqlParts[5] != null && sqlParts[5][0] != null)
				sqlBuffer.append(" order by ").append(sqlParts[5][0]);
			sql = sqlBuffer == null ? null : sqlBuffer.toString();
		}
		return updateQueryFilter(sql, dbType, params);
	}

	public static String updateQueryFilter(String sql, int dbType, Map params) {
		int p0 = 0;
		if (sql != null)
			for (;;) {
				int p = sql.indexOf("${filter.", p0);
				if (p < 0)
					break;
				int pe = sql.indexOf('}', p + 9);
				if (pe < 0)
					break;
				String id = sql.substring(p + 9, pe).trim();
				String f = buildQueryFilter(dbType, null, params, id + ".", 0);
				if (f == null)
					f = "1=1";
				String psql;
				sql = (psql = sql.substring(0, p) + f) + sql.substring(pe + 1);
				p0 = psql.length();
			}
		return sql;
	}

	static private String linkFilter(String filter, String f) {
		if (f == null || f.trim().length() == 0)
			return filter;
		if (filter == null || filter.trim().length() == 0)
			return f;
		return filter + " and " + f;
	}

	static private Hashtable toSqlFilterMacro(Map params, String paramPrifix) {
		Hashtable h = new Hashtable();
		Object a[][] = MapUtil.getAttValues(params, null);
		for (int i = 0; i < a.length; i++) {
			String paramName = (String) a[i][0];
			if (paramPrifix != null && paramName.startsWith(paramPrifix))
				paramName = paramName.substring(paramPrifix.length());
			if (a[i][1] == null)
				continue;
			for (int j = 0; j < paramName.length(); j++) {
				char c = paramName.charAt(j);
				if (!Character.isJavaIdentifierPart(c) && c != '.')
					paramName = paramName.substring(0, j) + '_' + paramName.substring(j + 1);
			}
			h.put(paramName, a[i][1]);
		}
		// System.err.println(h);
		return h;
	}

	private static boolean isNullStringArray(String a[]) {
		if (a == null)
			return true;
		for (int i = 0; i < a.length; i++)
			if (a[i] != null)
				return false;
		return true;
	}
	
	public static void main(String[] args){
		Map h = new Hashtable();
		h.put("filter.[v_like_1_,].a1", "a1"); 	//like 'a1%'
		h.put("filter.[v_like_2_,].a2", "a2");	//like '%a2'
		h.put("filter.[v_like_3_,].a3", "a3");	//like '%a3%'
		h.put("filter.[v_=_0_,].a4", "s4,s3");	//(a4 = 's4' or a4 = 's3')
		h.put("filter.[d__1_,].t.bbb.[from]", "2015-10-27");	//t.bbb>='2015-10-27 00:00:00'
		//h.put("filter.[d__1_,].t.bbb.[to]", "2015-10-28");
		h.put("filter.[i__1_,].ccc.[from]", "100");	
		h.put("filter.[i__1_,].ccc.[to]", "200");	//ccc between 100 and 200
		h.put("filter.[n_=_1_,].ddd", "100");		// = 100
		h.put("gfilter.[n_>=_1_,].eee.[to]", "200");		// >= 200
		System.err.println(QueryFilterBuilder.buildQueryFilter(3, null, h, null, 0));
		
		Map h1 = MapUtil.getMapValues(h,"filter*,gfilter**");
		System.err.println(h1);
	}
}
