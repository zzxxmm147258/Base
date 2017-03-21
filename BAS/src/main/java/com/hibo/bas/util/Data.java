package com.hibo.bas.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;
import java.util.Map;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午5:07:00</p>
 * <p>类全名：com.hibo.bas.util.Data</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class Data {
	static public class DataErrorException extends RuntimeException // Exception
	{
		public DataErrorException() {
		}

		public DataErrorException(String s) {
			super(s);
		}
	}

	protected Data() {
	}

	final public static Class numberClasses[] = { null, Byte.class,
			Short.class, Integer.class, Long.class, Float.class, Double.class,
			BigInteger.class, BigDecimal.class };

	private static int searchNumberType(Class cls) /* throws DataErrorException */
	{
		for (int i = 0; i < numberClasses.length; i++)
			if (cls == numberClasses[i])
				return i;
		throw new DataErrorException("类型错(需要数值型):"
				+ (cls == null ? null : cls.getName()));
	}

	public static Class matchNumberType(Class cls1, Class cls2) /*
																 * throws
																 * DataErrorException
																 */
	{
		if (cls1 == cls2)
			return cls1;
		int t1 = searchNumberType(cls1);
		int t2 = searchNumberType(cls2);
		int t = t1 > t2 ? t1 : t2;
		if (t == 5)
			return Double.class;
		if (t == 7 && (t1 == 6 || t2 == 6 || t1 == 5 || t2 == 5))
			return BigDecimal.class;
		return numberClasses[t];
	}

	public static Number nullNumber(Class cls) {
		if (cls == Short.class)
			return Short.valueOf((short) 0);
		else if (cls == Long.class || cls == Long.TYPE)
			return Long.valueOf(0);
		else if (cls == Byte.class || cls == Byte.TYPE)
			return Byte.valueOf((byte) 0);
		else if (cls == Float.class || cls == Float.TYPE)
			return Float.valueOf(0.0f);
		else if (cls == Double.class || cls == Double.TYPE)
			return new Double(0.0);
		else if (cls == BigInteger.class)
			return BigInteger.ZERO;// new BigInteger(new byte[]{0});
		else if (cls == BigDecimal.class)
			return BigDecimal.ZERO;// new BigDecimal((double)0.0);
		return Integer.valueOf(0);
	}

	public static Number toNumber(Number value, Class cls) {
		if (value == null)
			return nullNumber(cls);
		Class valueClass = value.getClass();
		if (cls == valueClass)
			return value;
		if (cls == Integer.class || cls == Integer.TYPE)
			return Integer.valueOf(value.intValue());
		else if (cls == Short.class || cls == Short.TYPE)
			return Short.valueOf(value.shortValue());
		else if (cls == Long.class || cls == Long.TYPE)
			return Long.valueOf(value.longValue());
		else if (cls == Byte.class || cls == Byte.TYPE)
			return Byte.valueOf(value.byteValue());
		else if (cls == Float.class || cls == Float.TYPE)
			return Float.valueOf(value.floatValue());
		else if (cls == Double.class || cls == Double.TYPE)
			return Double.valueOf(value.doubleValue());
		else if (cls == BigInteger.class) {
			// if( valueClass==
			return BigInteger.valueOf(value.longValue());
		} else if (cls == BigDecimal.class) {
			if (value instanceof Integer || value instanceof Long
					|| value instanceof Short || value instanceof Byte)
				return BigDecimal.valueOf(value.longValue());
			if (valueClass == BigInteger.class)
				return new BigDecimal((BigInteger) value);
			return new BigDecimal(value.doubleValue());
			// return BigDecimal.valueOf(value.doubleValue());
		}
		return value;
	}

	public static BigDecimal toBigDecimal(Number value, int maxScale) {
		BigDecimal x = toBigDecimal(value);
		if (x != null && maxScale > 0 && x.scale() > maxScale)
			x = x.setScale(maxScale, BigDecimal.ROUND_HALF_UP);
		return x;
	}

	public static BigDecimal toBigDecimal(Number value) {
		if (value instanceof BigDecimal)
			return (BigDecimal) value;
		if (value instanceof BigInteger)
			return new BigDecimal((BigInteger) value);
		if (value instanceof Integer || value instanceof Long
				|| value instanceof Short || value instanceof Byte)
			return BigDecimal.valueOf(value.longValue());
		return value == null ? null : new BigDecimal(value.doubleValue());
	}

	static public java.util.Date toSqlDate(Object value) {
		return toDate(value, java.sql.Date.class);
	}

	static public java.util.Date toSqlTimestamp(Object value) {
		return toDate(value, java.sql.Timestamp.class);
	}

	static public java.util.Date toDate(Object value, Class cls) {
		if (value == null)
			return null;
		Class valueClass = value.getClass();
		if (cls == valueClass)
			return (java.util.Date) value;
		boolean valueIsDate = value instanceof java.util.Date;
		if (cls == java.util.Date.class) {
			if (valueClass == String.class)
				try {
					return java.text.DateFormat.getDateInstance().parse(
							(String) value);
				} catch (java.text.ParseException ex) {
					return null;
				}
			else if (valueIsDate)
				return new java.util.Date(((java.util.Date) value).getTime());
		} else if (cls == java.sql.Date.class) {
			if (valueClass == String.class)
				try {
					return new java.sql.Date(java.text.DateFormat
							.getDateInstance().parse((String) value).getTime());
				} catch (java.text.ParseException ex) {
					return null;
				}
			else if (valueIsDate)
				return new java.sql.Date(((java.util.Date) value).getTime());
		} else if (cls == java.sql.Timestamp.class) {
			if (valueClass == String.class)
				try {
					return new java.sql.Timestamp(java.text.DateFormat
							.getDateInstance().parse((String) value).getTime());
				} catch (java.text.ParseException ex) {
					return null;
				}
			else if (valueIsDate)
				return new java.sql.Timestamp(
						((java.util.Date) value).getTime());
		}
		return valueIsDate ? (java.util.Date) value : null;
	}

	/**
	 * 从年月日得到一个Date对象
	 */
	public static java.util.Date toDate(int year, int month, int day) {
		return DateUtil.toDate(year, month, day);
	}

	public static final Object toCompatibleObject(Object obj, Class cls) {
		if (obj == null || cls == obj.getClass())
			return obj;
		if (cls == String.class) {
			return StrUtil.longText(obj);
		}
		if (cls == Boolean.class || cls == Boolean.TYPE) {
			if (obj instanceof Boolean)
				return (Boolean) obj;
			if (obj instanceof Number)
				return ((Number) obj).intValue() != 0;// ? Boolean.TRUE :
														// Boolean.FALSE ;
			if (obj instanceof String)
				return Boolean.valueOf(((String) obj));
			return null;
		}
		if (Number.class.isAssignableFrom(cls)) {
			if (obj instanceof Number)
				return toNumber((Number) obj, cls);
			if (obj instanceof String)
				return toNumber(new BigDecimal((String) obj), cls);
			return obj;
		}
		if (java.util.Date.class.isAssignableFrom(cls)) {
			return toDate(obj, cls);
		}
		if (cls.isPrimitive()) {
			if (cls == java.lang.Character.TYPE) {
				if (obj instanceof Number)
					return (char) ((Number) obj).intValue();
				if (obj instanceof String) {
					String s = (String) obj;
					return s.length() == 0 ? (char) 0 : s.charAt(0);
				}
			}
			if (obj instanceof Number)
				return toNumber((Number) obj, cls);
			if (obj instanceof String)
				return toNumber(new BigDecimal((String) obj), cls);
		}
		return obj;
	}

	static public int valueSignnum(Number value) {
		if (value == null)
			return 0;
		if (value instanceof BigInteger)
			return ((BigInteger) value).signum();
		if (value instanceof BigDecimal)
			return ((BigDecimal) value).signum();
		if (value instanceof Long) {
			long x = value.longValue();
			return x > 0 ? 1 : (x < 0 ? -1 : 0);
		}
		if (value instanceof Double || value instanceof Float) {
			double x = value.doubleValue();
			return x > 0.0 ? 1 : (x < 0.0 ? -1 : 0);
		}
		int x = value.intValue();
		return x > 0 ? 1 : (x < 0 ? -1 : 0);
	}

	static public boolean isZero(Number value) {
		if (value == null)
			return true;
		if (value instanceof BigInteger)
			return ((BigInteger) value).signum() == 0;
		if (value instanceof BigDecimal)
			return ((BigDecimal) value).signum() == 0;
		if (value instanceof Long) {
			return value.longValue() == 0;
		}
		if (value instanceof Double || value instanceof Float) {
			return value.doubleValue() == 0;
		}
		return value.intValue() == 0;
	}

	static DataErrorException UnsupportedOperation(java.util.Map envParams) {
		return UnsupportedOperation(envParams, null);
	}

	static DataErrorException UnsupportedOperation(java.util.Map envParams,
			String msg) {
		String s = "unsupportedOperation";
		if (msg != null)
			s += msg;
		return new DataErrorException(s);
	}

	static public Number valueNegative(Number value) {
		if (value == null)
			return null;
		if (value instanceof BigInteger)
			return ((BigInteger) value).negate();
		if (value instanceof BigDecimal)
			return ((BigDecimal) value).negate();
		// if( value instanceof Short )
		// return new Short(-value.shortValue());
		if (value instanceof Long)
			return Long.valueOf(-value.longValue());
		// if( value instanceof Byte )
		// return new Byte(-value.byteValue());
		if (value instanceof Double)
			return Double.valueOf(-value.doubleValue());
		if (value instanceof Float)
			return Float.valueOf(-value.floatValue());
		return Integer.valueOf(-value.intValue());
	}

	static public Number valueAdd(Number value1, Number value2) {
		if (value1 == null)
			return value2;
		if (value2 == null)
			return value1;
		return valueAdd(value1, value2,
				Data.matchNumberType(value1.getClass(), value2.getClass()));
	}

	static public Number valueAdd(Number value1, Number value2, Class type) {
		value1 = Data.toNumber(value1, type);
		value2 = Data.toNumber(value2, type);
		if (type == Integer.class || type == Byte.class || type == Short.class)
			return Integer.valueOf(value1.intValue() + value2.intValue());
		// else if( type==Short.class )return new
		// Short((short)(value1.shortValue()+value2.shortValue()));
		else if (type == Long.class)
			return Long.valueOf(value1.longValue() + value2.longValue());
		else if (type == Double.class || type == Float.class)
			return new Double(value1.doubleValue() + value2.doubleValue());
		else if (type == BigInteger.class)
			return ((BigInteger) value1).add((BigInteger) value2);
		else if (type == BigDecimal.class)
			return ((BigDecimal) value1).add((BigDecimal) value2);
		return null;
	}

	static public Number valueSub(Number value1, Number value2) {
		if (value1 == null)
			return valueNegative(value2);
		if (value2 == null)
			return value1;
		return valueSub(value1, value2,
				Data.matchNumberType(value1.getClass(), value2.getClass()));
	}

	static public Number valueSub(Number value1, Number value2, Class type) {
		value1 = Data.toNumber(value1, type);
		value2 = Data.toNumber(value2, type);
		if (type == Integer.class || type == Byte.class || type == Short.class)
			return Integer.valueOf(value1.intValue() - value2.intValue());
		// else if( type==Short.class )return new
		// Short((short)(value1.shortValue()+value2.shortValue()));
		else if (type == Long.class)
			return Long.valueOf(value1.longValue() - value2.longValue());
		else if (type == Double.class || type == Float.class)
			return new Double(value1.doubleValue() - value2.doubleValue());
		else if (type == BigInteger.class)
			return ((BigInteger) value1).subtract((BigInteger) value2);
		else if (type == BigDecimal.class)
			return ((BigDecimal) value1).subtract((BigDecimal) value2);
		return null;
	}

	static public Number valueMul(Number value1, Number value2, Class type) {
		if (value1 == null || value2 == null)
			return null;
		value1 = Data.toNumber(value1, type);
		value2 = Data.toNumber(value2, type);
		if (type == Integer.class || type == Byte.class || type == Short.class)
			return Integer.valueOf(value1.intValue() * value2.intValue());
		// else if( type==Short.class )return new
		// Short((short)(value1.shortValue()+value2.shortValue()));
		else if (type == Long.class)
			return Long.valueOf(value1.longValue() * value2.longValue());
		else if (type == Double.class || type == Float.class)
			return new Double(value1.doubleValue() * value2.doubleValue());
		else if (type == BigInteger.class)
			return ((BigInteger) value1).multiply((BigInteger) value2);
		else if (type == BigDecimal.class)
			return ((BigDecimal) value1).multiply((BigDecimal) value2);
		return null;
	}

	static public Number valueDiv(Number value1, Number value2) {
		if (value1 == null || value2 == null)
			return null;
		return valueDiv(value1, value2,
				Data.matchNumberType(value1.getClass(), value2.getClass()));
	}

	static public Number valueDiv(Number value1, Number value2, Class type) {
		if (valueSignnum(value2) == 0) // divide by 0
			return null;
		value1 = Data.toNumber(value1, type);
		value2 = Data.toNumber(value2, type);
		if (type == Integer.class || type == Byte.class || type == Short.class)
			return Integer.valueOf(value1.intValue() / value2.intValue());
		// else if( type==Short.class )return new
		// Short((short)(value1.shortValue()+value2.shortValue()));
		else if (type == Long.class)
			return Long.valueOf(value1.longValue() / value2.longValue());
		else if (type == Double.class || type == Float.class)
			return Double.valueOf(value1.doubleValue() / value2.doubleValue());
		else if (type == BigInteger.class)
			return ((BigInteger) value1).divide((BigInteger) value2);
		else if (type == BigDecimal.class) {
			int scale = 8;
			return ((BigDecimal) value1).divide((BigDecimal) value2, scale,
					BigDecimal.ROUND_HALF_UP);
		}
		return null;
	}

	static public Number valueDiv(Number value1, int n) {
		if (n == 0 || value1 == null)
			return null;
		Class type = value1.getClass();
		if (type == Integer.class || type == Byte.class || type == Short.class)
			return Integer.valueOf(value1.intValue() / n);
		else if (type == Long.class)
			return Long.valueOf(value1.longValue() / n);
		else if (type == Double.class || type == Float.class)
			return Double.valueOf(value1.doubleValue() / n);
		else if (type == BigInteger.class)
			return ((BigInteger) value1).divide(new BigInteger("" + n));
		else if (type == BigDecimal.class) {
			int scale = ((BigDecimal) value1).scale();
			// ((BigDecimal)value1).scale()
			// + (((BigDecimal)value2).unscaledValue().bitLength()*3+10)/10;

			return ((BigDecimal) value1).divide(new BigDecimal((double) n),
					scale, BigDecimal.ROUND_HALF_UP);
		}
		return null;
	}

	static final public String valueTypeToString(Object value) {
		if (value == null)
			return null;
		return value.getClass().getName() + " " + value;
	}

	/*
	 * @return 0 : value1==value2; 1 : value1 > value2 -1 : value1 < value2
	 */
	static public int compare(Object value1, Object value2) /*
															 * throws
															 * DataErrorException
															 */
	{
		final int c = valueCompare(0x29, value1, value2);
		if ((c & 2) != 0)
			return 1;
		if ((c & 4) != 0)
			return -1;
		return 0;
	}

	static public boolean valueEquals(Object value1, Object value2) {
		return (valueCompare(0x29, value1, value2) & 1) != 0;
	}

	static public boolean valueNotEQ(Object value1, Object value2) {
		return (valueCompare(0x29, value1, value2) & 1) == 0;
	}

	/*
	 * value1 > value2
	 */
	static public boolean valueGT(Object value1, Object value2) {
		return (valueCompare(0x29, value1, value2) & 2) != 0;
	}

	/*
	 * value1 >= value2
	 */
	static public boolean valueGE(Object value1, Object value2) {
		return (valueCompare(0x29, value1, value2) & 3) != 0;
	}

	/*
	 * value1 < value2
	 */
	static public boolean valueLT(Object value1, Object value2) {
		return (valueCompare(0x29, value1, value2) & 4) != 0;
	}

	/*
	 * value1 <= value2
	 */
	static public boolean valueLE(Object value1, Object value2) {
		return (valueCompare(0x29, value1, value2) & 5) != 0;
	}

	/*
	 * // op : OPCODE_EQ = 0x29, OPCODE_NE !=
	 */
	private static int valueCompare(int op, Object value1, Object value2) /*
																		 * throws
																		 * DataErrorException
																		 */
	{
		if (value1 == value2)
			return 1;
		int signnum;
		if (value1 == null && value2 != null) {
			if (value2 instanceof Number) {
				signnum = valueSignnum((Number) value2);
				return signnum == 0 ? 1 : (signnum > 0 ? 4 : 2);
			}
			if (value2 instanceof Boolean
					&& !(((Boolean) value2).booleanValue()))
				return 1;
			return 4; // bit |= 4; // null < ?
		} else if (value1 != null && value2 == null) {
			if (value1 instanceof Number) {
				signnum = valueSignnum((Number) value1);
				return signnum == 0 ? 1 : (signnum > 0 ? 2 : 4);
			}
			if (value1 instanceof Boolean && !((Boolean) value1).booleanValue())
				return 1;
			return 2;// null > ?
		} else // value1!=null && value2!=null
		{
			if (value1 instanceof java.util.Date)// && value2 instanceof String
													// )
			{
				value2 = DateUtil.toDate(value2);
			} else if (value2 instanceof java.util.Date)// && value2 instanceof
														// String )
			{
				value1 = DateUtil.toDate(value1);
			}
			if (value1 instanceof String && value2 instanceof String) {
				signnum = ((String) value1).compareTo((String) value2);
			} else if (value1 instanceof java.util.Date
					&& value2 instanceof java.util.Date) {
				long time1 = ((java.util.Date) value1).getTime();
				long time2 = ((java.util.Date) value2).getTime();
				return (time1 < time2 ? 4 : (time1 == time2 ? 1 : 2));
			} else if (value1 instanceof Boolean && value2 instanceof Boolean) {
				return ((Boolean) value1).booleanValue() == ((Boolean) value2)
						.booleanValue() ? 1 : (((Boolean) value1)
						.booleanValue() ? 2 : 4);
			} else {
				if (value1 instanceof Character)
					value1 = Integer.valueOf(((Character) value1).charValue());
				if (value2 instanceof Character)
					value2 = Integer.valueOf(((Character) value2).charValue());
				if (value1 instanceof Number && value2 instanceof Number) {
					if (value1 instanceof BigDecimal
							|| value2 instanceof BigDecimal) {
						value1 = Data.toNumber((Number) value1,
								BigDecimal.class);
						value2 = Data.toNumber((Number) value2,
								BigDecimal.class);
						signnum = ((BigDecimal) value1)
								.compareTo((BigDecimal) value2);
					} else if (value1 instanceof BigInteger
							|| value2 instanceof BigInteger) {
						value1 = Data.toNumber((Number) value1,
								BigInteger.class);
						value2 = Data.toNumber((Number) value2,
								BigInteger.class);
						signnum = ((BigInteger) value1)
								.compareTo((BigInteger) value2);
					} else if (value1 instanceof Double
							|| value2 instanceof Double
							|| value1 instanceof Float
							|| value2 instanceof Float) {
						double v1 = ((Number) value1).doubleValue();
						double v2 = ((Number) value2).doubleValue();
						return v1 < v2 ? 4 : (v1 == v2 ? 1 : 2);
					} else {
						long v1 = ((Number) value1).longValue();
						long v2 = ((Number) value2).longValue();
						return v1 < v2 ? 4 : (v1 == v2 ? 1 : 2);
					}
				} else if (op == 0x29 || op == 0x2E) {
					if (value1 == null)
						return value2 == null ? 1 : 0;
					return value1.equals(value2) ? 1 : 0;
				} else {
					if (value1 == value2)
						return 1;
					if (value1 == null)
						return 4;
					else if (value2 == null)
						return 2;
					if (value1 instanceof java.lang.Comparable) {
						signnum = ((java.lang.Comparable) value1)
								.compareTo(value2);
					} else
						throw UnsupportedOperation(null, "("
								+ valueTypeToString(value1) + ","
								+ valueTypeToString(value2) + ")");
				}
			}
			return signnum == 0 ? 1 : (signnum > 0 ? 2 : 4);
		}
	}

	static public boolean valueBetween(Object value, Object value1,
			Object value2) {
		int k = valueCompare(0x29, value, value1);
		if ((k & 4) != 0)
			return false; // value<value1
		k = valueCompare(0x29, value, value2);
		if ((k & 2) != 0)
			return false; // value>value2
		return true;
	}

	static public void valueAddTo(Map<String, Number> to, String key,
			Number value) {
		if (value != null) {
			to.put(key, valueAdd((Number) to.get(key), value));
		}
	}

	public static Object parseStringFromSqlType(String text, int sqlType) {
		return parseStringFromSqlType(null, text, sqlType);
	}

	public static Object parseStringFromSqlType(java.util.Map envParams,
			String text, int sqlType) {
		if (text == null)
			return null;
		String oldText = text;
		try {
			text = text.trim();
			if (text.length() == 0)
				return null;
			switch (sqlType) {
			case Types.TINYINT: {
				int x = Integer.parseInt(text);
				if (x < Byte.MIN_VALUE || x > Byte.MAX_VALUE)
					throw new NumberFormatException("范围只能从 " + Byte.MIN_VALUE
							+ " 到 " + Byte.MAX_VALUE);
				return Byte.valueOf((byte) x);
			}
			case Types.SMALLINT: {
				int x = Integer.parseInt(text);
				if (x < Short.MIN_VALUE || x > Short.MAX_VALUE)
					throw new NumberFormatException("范围只能从 " + Short.MIN_VALUE
							+ " 到 " + Short.MAX_VALUE);
				return Short.valueOf((short) x);
			}
			case Types.INTEGER:
				return Integer.valueOf(text);
				// case Types.LLONG: return Long.valueOf(text);
			case Types.FLOAT:
				return Float.valueOf(text);
			case Types.REAL:
			case Types.DOUBLE:
				return Double.valueOf(text);
				// case -10:
			case Types.DECIMAL:
			case Types.NUMERIC:
				return new BigDecimal(text);
			case Types.BIT:
				return Boolean.valueOf(text);
				// case Variant.INPUTSTREAM: return Object.class;
			case Types.DATE:// 91 //return java.sql.Date.valueOf(text);
			{
				int r[] = new int[6];
				if (DateUtil.parseDate(text, r))
					return DateUtil.toDate(r[0], r[1], r[2]);
				else if (DateUtil.parseDateTime(text, r))
					return new java.sql.Timestamp(DateUtil.toLongTime(r[0],
							r[1], r[2], r[3], r[4], r[5]));
				throw new java.lang.IllegalArgumentException("日期(" + text
						+ ")格式错");
			}
			case Types.TIMESTAMP: {
				int r[] = new int[6];
				if (!DateUtil.parseDateTime(text, r))
					throw new IllegalArgumentException("日期(" + text + ")格式错");
				return new java.sql.Timestamp(DateUtil.toLongTime(r[0], r[1],
						r[2], r[3], r[4], r[5]));
			}
			}
		} catch (NumberFormatException numberFormatException) {

			com.hibo.bas.exception.HookedException ex = new com.hibo.bas.exception.HookedException(
					numberFormatException, "(数值型(" + oldText + ")格式错)");
			ex.extMsgOnly = true;
			throw ex;
		}
		return StrUtil.trimRight(oldText);
	}

	static public String removeNumThouDelim(String s) {
		if (s == null)
			return s;
		StringBuilder sb = new StringBuilder();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			if (c != ',' && c != '，')
				sb.append(c);
		}
		return n == sb.length() ? s : sb.toString();
	}

	/**
	 * 得到与 java.sql.Types 中定义类型相容的Java类.
	 */
	static public Class getJavaClassFromSqlType(int sqlType) {
		switch (sqlType) {
		case Types.CHAR:
		case Types.VARCHAR:
		case -9:
		case Types.LONGVARCHAR:
			return String.class;
		case Types.BIT:
			return Boolean.class;
		case Types.DATE:
			return java.sql.Date.class;
		case Types.TIME:
			return java.sql.Time.class;
		case Types.TIMESTAMP:
			return java.sql.Timestamp.class;

		case Types.TINYINT:
			return Byte.class;
		case Types.SMALLINT:
			return Short.class;
		case Types.INTEGER:
			return Integer.class;
		case Types.BIGINT:
			return Long.class;

		case Types.FLOAT:
			return Float.class;
		case Types.REAL:
		case Types.DOUBLE:
			return Double.class;
		case Types.NUMERIC:
		case Types.DECIMAL:
			return BigDecimal.class;
		case Types.LONGVARBINARY:
			;// return (byte[]).class;
		case Types.VARBINARY:
		}
		return Object.class;
	}

	static public int getSqlTypeFromJavaClass(Class javaClass) {
		final Class objectDataTypes[] = { Boolean.class, Byte.class,
				Short.class, Integer.class, Long.class, BigInteger.class,
				String.class, Float.class, Double.class, BigDecimal.class,
				java.util.Date.class, java.sql.Date.class,
				java.sql.Timestamp.class, };
		final int sqlType[] = { Types.BIT, Types.TINYINT, Types.SMALLINT,
				Types.INTEGER, Types.BIGINT, Types.NUMERIC, Types.VARCHAR,
				Types.FLOAT, Types.DOUBLE, Types.NUMERIC, Types.DATE,
				Types.DATE, Types.TIMESTAMP };
		for (int i = 0; i < objectDataTypes.length; i++) {
			if (javaClass == objectDataTypes[i])
				return sqlType[i];
		}
		return 0;
	}

	public static final Object defaultSqlObject(int sqlType) {
		switch (sqlType) {
		case Types.CHAR:
		case Types.VARCHAR:
		case -9:
		case Types.LONGVARCHAR:
			return "";
		case Types.BIT:
			return Boolean.FALSE;
		case Types.DATE:
			return new java.sql.Date(0);// System.currentTimeMillis());
		case Types.TIME:
			return new java.sql.Time(0);// System.currentTimeMillis());
		case Types.TIMESTAMP:
			return new java.sql.Timestamp(0);// System.currentTimeMillis());

		case Types.TINYINT:
			return Byte.valueOf((byte) 0);
		case Types.SMALLINT:
			return Short.valueOf((short) 0);
		case Types.INTEGER:
			return Integer.valueOf(0);
		case Types.BIGINT:
			return Long.valueOf(0);

		case Types.FLOAT:
			return Float.valueOf(0);
		case Types.REAL:
		case Types.DOUBLE:
			return Double.valueOf(0);
		case Types.NUMERIC:
		case Types.DECIMAL:
			return BigDecimal.ZERO;// ((double)0);
		}
		return null;
	}

	public static final Object toCompatibleSqlObject(Object obj, int sqlType,
			int scale) {
		if (obj == null)
			return null;
		// Class targeClass = getJavaClassFromSqlType(sqlType);
		if (sqlType == -4 || sqlType == 2004) // Types.LONGVARBINARY or BLOB )
		{
			if (obj instanceof byte[])
				return obj;
			else
				try {
					return ResUtil.CharSet == null ? obj.toString().getBytes()
							: obj.toString().getBytes(ResUtil.CharSet);
				} catch (Throwable ex) {
					ex.printStackTrace();
					return obj.toString().getBytes();
				}
			/*
			 * if( !(obj instanceof byte[]) ) obj = obj.toString().getBytes();
			 * return new java.io.ByteArrayInputStream((byte[])obj);
			 */
		}
		Object o;
		if (obj instanceof String)
			o = sqlType != 12 && sqlType != -9 ? Data.parseStringFromSqlType(
					(String) obj, sqlType) : obj;
		else
			o = sqlType == 0 ? obj : toCompatibleObject(obj,
					getJavaClassFromSqlType(sqlType));
		if (o != null && o.getClass() == BigDecimal.class) {
			if (scale <= 0)
				scale = 4;
			if (scale < ((BigDecimal) o).scale())
				o = ((BigDecimal) o).setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		return o;
	}

	/*
	 * Objects.equals(o1,o2)
	 */
	static final public boolean objEquals(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
		// return o1==o2 || (o1!=null && o1.equals(o2));
	}

	// static final boolean arrayEquals(Object a1[],Object a2[])
	// {
	// return Arrays.equals(a1, a2);
	// /*
	// if( a1==a2 )
	// return true;
	// int n;
	// if( a1==null || a2==null || (n=a1.length)!=a2.length )
	// return false;
	// for(int i=0;i<n;i++)
	// {
	// if( !objEquals(a1[i],a2[i]) )
	// return false;
	// }
	// return true;
	// */
	// }
	static final public boolean valueEquals(Object a1[], Object a2[]) {
		if (a1 == a2)
			return true;
		int n;
		if (a1 == null || a2 == null || (n = a1.length) != a2.length)
			return false;
		for (int i = 0; i < n; i++) {
			if ((valueCompare(0x29, a1[i], a2[i]) & 1) == 0)
				return false;
			// if( !objEquals(a1[i],a2[i]) )
			// return false;
		}
		return true;
	}

	static public final int obj2int(Object o, int defaultValue) {
		if (o instanceof Number)
			return ((Number) o).intValue();
		return defaultValue;
	}

	static public final int obj2int(Object o) {
		return obj2int(o, 0);
	}

	/*
	 * static public void main(String args[]) throws Exception {
	 * java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	 * byte a[] = new byte[100] ; for(int i=0;i<100;i++) a[i]=(byte)i;
	 * writeDataObject(new DataOutputStream(baos),a); baos.close(); byte
	 * buffer[] = baos.toByteArray(); byte b[] = (byte[])readDataObject(new
	 * DataInputStream(new ByteArrayInputStream(buffer))); for(int
	 * i=0;i<b.length;i++) System.out.println(b[i]);
	 * System.out.println(a.getClass()==b.getClass()); }
	 */
	/****************************************************************************************
	 * 表达式运算 等
	 ******************************************************************************/

	static public Object tacOp2(int op,/* String name, */Object value1,
			Object value2) // throws
							// TacErrorException,Data.DataErrorException,InvocationTargetException,IllegalAccessException,CloneNotSupportedException
	{
		// if( op==0x29 || op==0x2E )
		// return ==1 ? Boolean.TRUE : Boolean.FALSE;
		if (op >= 0x29 && op <= 0x2E) {
			int v = Data.valueCompare(op, value1, value2);
			if (op == 0x29) // OPCODE_EQ = 0x29
				return (v & 1) != 0 ? Boolean.TRUE : Boolean.FALSE;
			if (op == 0x2E) // OPCODE_NE
				return (v & 1) == 0 ? Boolean.TRUE : Boolean.FALSE;
			return (v & op) != 0 ? Boolean.TRUE : Boolean.FALSE;
		}
		// if( value1==Const.NULL ) value1 = null;
		// if( value2==Const.NULL ) value2 = null;
		if (value1 == null && value2 == null)
			return null;
		if (value1 instanceof String || value2 instanceof String) {
			if (op == OPCODE_ADD)
				return (value1 == null ? "" : toString(value1))
						+ (value2 == null ? "" : toString(value2));
			if (op == OPCODE_LIKE)
				return StrUtil.like(toString(value1), toString(value2)) ? Boolean.TRUE
						: Boolean.FALSE;
			throw UnsupportedOperation(null);
		} // if( value1 instanceof String || value2 instanceof String )
		if (value1 instanceof java.util.Date
				|| value2 instanceof java.util.Date) {
			if (value1 instanceof java.util.Date
					&& value2 instanceof java.util.Date) {
				if (op != OPCODE_SUB)
					UnsupportedOperation(null);// throw new
												// DataErrorException(Data.unsupportedOperation);
				return Integer
						.valueOf((int) ((((java.util.Date) value1).getTime() - ((java.util.Date) value2)
								.getTime()) / (24 * 60 * 60 * 1000)));
			}
			if (op == OPCODE_SUB) {
				if (value2 == null)
					return value1;
				if (value1 == null || !(value2 instanceof Number))
					throw UnsupportedOperation(null, value1 + " - " + value2);// new
																				// DataErrorException(Data.unsupportedOperation);
				// Date date = (Date)value1; date = (Date)(date.clone());
				// messageOut.println("date.clone");
				java.util.Date date = DateUtil
						.cloneDate((java.util.Date) value1); // (java.util.Date)(((java.util.Date)value1).clone());
				// messageOut.println("date-integer="+date);
				date.setTime(((java.util.Date) value1).getTime()
						- ((Number) value2).longValue() * 24 * 60 * 60 * 1000);
				return date; // new
								// Date(((Date)value1).getTime()-((Number)value2).longValue()*24*60*60*1000);
			} else if (op == OPCODE_ADD) {
				if (value2 instanceof Number) {
					// Date date = (Date)value1; date = (Date)(date.clone());
					java.util.Date date = DateUtil
							.cloneDate((java.util.Date) value1);
					// java.util.Date date =
					// (java.util.Date)(((java.util.Date)value1).clone());
					date.setTime(((java.util.Date) value1).getTime()
							+ ((Number) value2).longValue() * 24 * 60 * 60
							* 1000);
					return date;
					// return new
					// Date(((Date)value1).getTime()+((Number)value2).longValue()*24*60*60*1000);
				} else if (value1 instanceof Number) {
					// Date date = (Date)value2; date = (Date)(date.clone());
					java.util.Date date = DateUtil
							.cloneDate((java.util.Date) value2);
					// java.util.Date date =
					// (java.util.Date)(((java.util.Date)value2).clone());
					date.setTime(((java.util.Date) value2).getTime()
							+ ((Number) value1).longValue() * 24 * 60 * 60
							* 1000);
					return date;
					// return new
					// Date(((Date)value2).getTime()+((Number)value1).longValue()*24*60*60*1000);
				}
			}
			throw UnsupportedOperation(null);// new
												// DataErrorException(Data.unsupportedOperation);
		} // if(value1 instanceof Date || value2 instanceof Date)
		/*
		 * if( ( value1!=null && !(value1 instanceof Number) ) && ( value2!=null
		 * && !(value2 instanceof Number) ) throw new
		 * TacErrorException(unsupportedOperation);
		 */
		Class type = Data.matchNumberType(
				value1 == null ? null : value1.getClass(),
				value2 == null ? null : value2.getClass());
		if (op == OPCODE_ADD)
			return Data.valueAdd((Number) value1, (Number) value2, type);
		if (op == OPCODE_SUB)
			return Data.valueSub((Number) value1, (Number) value2, type);
		if (op == OPCODE_MUL)
			return Data.valueMul((Number) value1, (Number) value2, type);
		if (op == OPCODE_DIV)
			return Data.valueDiv((Number) value1, (Number) value2, type);
		if (op == OPCODE_BITAND) {
			if (value1 == null || value2 == null)
				return null;
			if (value1 instanceof Long || value2 instanceof Long)
				return Long.valueOf(((Number) value1).longValue()
						& ((Number) value2).longValue());
			return Integer.valueOf(((Number) value1).intValue()
					& ((Number) value2).intValue());
		}
		if (op == OPCODE_BITOR) {
			if (value1 == null)
				return value2;
			if (value2 == null)
				return value1;
			if (value1 instanceof Long || value2 instanceof Long)
				return Long.valueOf(((Number) value1).longValue()
						| ((Number) value2).longValue());
			return Integer.valueOf(((Number) value1).intValue()
					| ((Number) value2).intValue());
		}
		if (op == OPCODE_BITXOR) {
			if (value1 == null)
				return value2;
			if (value2 == null)
				return value1;
			return Integer.valueOf(((Number) value1).intValue()
					^ ((Number) value2).intValue());
		}
		// value1 = toNumber(value1,type);
		// value2 = toNumber(value2,type);
		/*
		 * loadTacopMethod(); int nMethod = tacopMethod.length; for(int
		 * i=0;i<nMethod;i++) { if(
		 * !tacopMethod[i].getName().equals("$tacop$"+name) ) continue; Class
		 * type1 = tacopMethodTypes[i*2]; Class type2 = tacopMethodTypes[i*2+1];
		 * return tacopMethod[i].invoke(this,new Object[]{value1,value2}); }
		 */
		throw UnsupportedOperation(null);// new
	}

	/*
	 * 不要定义为 final, 建发 的RatifyTac 改写了该方法
	 */
	static public String toString(Object x) {
		if (x instanceof java.util.Date)
			return DateUtil.dateToString((java.util.Date) x);
		// if( x instanceof java.util.Calendar )
		// return DateUtil.dateToString();
		return x == null ? null : x.toString();
	}

	static public Object tacOp1(int op, Object value) // throws
														// TacErrorException,InvocationTargetException,IllegalAccessException
	{
		if (op == OPCODE_NOT) {
			if (value == null || !((Boolean) value).booleanValue())
				return Boolean.TRUE;
			return Boolean.FALSE;
		}
		if (op == OPCODE_NEG) {
			if (value == null)
				return null;
			if (value instanceof Number)
				return Data.valueNegative((Number) value);
			// return tacOp2(OPCODE_SUB,"Sub",null,value);
		}
		return null;
	}

	static final public byte OPCODE_LOAD1 = 0x02;
	static final public byte OPCODE_STORE1 = 0x04;
	static final public byte OPCODE_GET = 0x07;
	static final public byte OPCODE_NEG = 0x21;
	static final public byte OPCODE_NOT = 0x22;
	static final public byte OPCODE_BITNOT = 0x23;
	static final public byte OPCODE_INVOCKSTATIC = 0x26;
	static final public byte OPCODE_EQ = 0x29;
	static final public byte OPCODE_GT = 0x2A;
	static final public byte OPCODE_GE = 0x2B;
	static final public byte OPCODE_LT = 0x2C;
	static final public byte OPCODE_LE = 0x2D;
	static final public byte OPCODE_NE = 0x2E;

	static final public byte OPCODE_ADD = 0x30;
	static final public byte OPCODE_SUB = 0x31;
	static final public byte OPCODE_MUL = 0x32;
	static final public byte OPCODE_DIV = 0x33;
	static final public byte OPCODE_AND = 0x34;
	static final public byte OPCODE_OR = 0x35;
	static final public byte OPCODE_BITAND = 0x36;
	static final public byte OPCODE_BITOR = 0x37;
	static final public byte OPCODE_BITXOR = 0x38;

	static final public byte OPCODE_IFF = 0x3A;
	static final public byte OPCODE_LIKE = 0x3B;

	static final public byte OPCODE_TOTYPE = 0x46;

	static public class ValueComparator implements java.util.Comparator<Object> {
		final java.util.Map valueMap;

		// final
		public ValueComparator(java.util.Map valueMap) {
			this.valueMap = valueMap;
		}

		@Override
		public int compare(Object o1, Object o2) {
			if (valueMap != null) {
				o1 = valueMap.get(o1);
				o2 = valueMap.get(o2);
			}
			int c = Data.valueCompare(0x29, o1, o2);
			if ((c & 4) != 0)
				return -1;
			else if ((c & 2) != 0)
				return 1;
			return 0;
		}
	}

	static public class ValuesComparator implements
			java.util.Comparator<java.util.Map> {
		public final String cmpNames[];

		public ValuesComparator(String cmpNames[]) {
			this.cmpNames = cmpNames;
		}

		@Override
		public int compare(java.util.Map o1, java.util.Map o2) {
			for (int i = 0; i < cmpNames.length; i++) {
				int c = Data.valueCompare(0x29,
						o1 == null ? null : o1.get(cmpNames[i]),
						o2 == null ? null : o2.get(cmpNames[i]));
				if ((c & 4) != 0)
					return -1;
				else if ((c & 2) != 0)
					return 1;
			}
			return 0;
		}

	}
}
