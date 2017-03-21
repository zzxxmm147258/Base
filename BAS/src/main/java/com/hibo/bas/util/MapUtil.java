package com.hibo.bas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
/**
 * <p>标题： Map工具类</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午5:53:22</p>
 * <p>类全名：com.hibo.bas.util.MapUtil</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */

public class MapUtil {
	/**
	 * 复制Map，将from中的键值复制到to中，并将to返回；
	 * 算法：如果from为空，则直接返回to。非空情况下，则首先确保to非null，然后将from中所有键值复制添加到to中，并将to返回； 例子：
	 * Map m1 = new HashMap(); m1.put("1", 1); m1.put("2", 2); Map m2 = new
	 * HashMap(); m2.put("2", 3); m2.put("4", 4); m2.put("5", 5);
	 * out.println(MapUtil.dupMap(m1, m2)); Console：{2=3, 1=1, 5=5, 4=4}
	 * 
	 * @param from待拷贝的Map
	 * @param to数据拷贝到的Map
	 * @return 拷贝后的to
	 */
	public static final Map dupMap(Map to, Map from) {
		if (from == null)
			return to;
		if (to == null)
			to = new HashMap();
		to.putAll(from);
		return to;
	}

	public static final Map dupMaps(Map to, Map... froms) {
		if (froms != null)
			for (int i = 0; i < froms.length; i++)
				to = dupMap(to, froms[i]);
		return to;
	}

	/**
	 * 将from中的在keys数组中所指定的键的键值复制到to中并返回； 算法：from和keys若为null，则直接返回to。
	 * 按照keys数组中的键在from中找到对应的值，并将键值对塞入to中并将to返回； 例子： Map m1 = new HashMap();
	 * m1.put("1", 1); m1.put("2", 2); Map m2 = new HashMap(); m2.put("2", 3);
	 * m2.put("4", 4); m2.put("5", 5); m2.put("6", 6);
	 * out.println(MapUtil.copy(m1, m2, new Object[] { "2", "5", "6" }));
	 * Console：{2=3, 1=1, 6=6, 5=5}
	 * 
	 * @param to欲拷贝到的目标Map
	 * @param from取数的源Map
	 * @param keys用于指定取值的key的数组
	 * @return 返回值为拷贝后的to
	 */
	public static final Map copy(Map to, Map from, Object keys[]) {
		if (from == null || keys == null)
			return to;
		if (to == null)
			to = new HashMap();
		for (int i = 0; i < keys.length; i++)
			to.put(keys[i], from.get(keys[i]));
		return to;
		// for(int i=0;)
	}

	/**
	 * 复制Map，将from中的键值复制到to中，如果to中包含from的键，则该值不会复制，即不会覆盖to中原来的值，复制后将to返回；
	 * 算法：如果from为空，则直接返回to。
	 * 非空情况下，则首先确保to非null，然后将from中与to不重复键的键值复制添加到to中，并将to返回； 例子： Map m1 = new
	 * HashMap(); m1.put("1", 1); m1.put("2", 2); Map m2 = new HashMap();
	 * m2.put("2", 3); m2.put("4", 4); m2.put("5", 5);
	 * out.println(MapUtil.dupMapIfNull(m1, m2)); Console：{2=2, 1=1, 5=5, 4=4}
	 * 
	 * @param from待拷贝Map
	 * @param to数据拷贝到的Map
	 * @return 拷贝后的to
	 */
	public static final Map dupMapIfNull(Map to, Map from) {
		if (from == null)
			return to;
		if (to == null)
			to = new HashMap();
		for (final Object key : from.keySet()) {
			if (!to.containsKey(key))
				to.put(key, from.get(key));
		}
		return to;
	}

	/**
	 * 将m中所有键可以匹配的上keyPattern（多个key用,隔开）中的任意的key的键值对以二维数组的形式返回；
	 * 算法：m为null时直接返回null，如果为CodeData的Map，则调用CodeData的getValues()。
	 * 对m的entrySet进行遍历，非空并且可以匹配keyPattern中key的键值对，则将其键和值作为两个元素添加到一个一维数组，
	 * 并将该数组添加到一个链表，最后将链表转换为数组并返回； 例子： Map m1 = new HashMap(); m1.put("1", 1);
	 * m1.put(2, 2); m1.put("3", 3); for (Object[] objs : MapUtil.getValues(m1,
	 * null)) out.println(objs[0] + "=" + objs[1]); Console： 3=3 2=2 1=1
	 * 
	 * @param m数据源Map
	 * @param keyPattern用于匹配的字符串
	 *            ，多个匹配格式用’,’分隔
	 * @return 转换后的数组
	 */
	public static final Object[][] getValues(final Map map,
			final String keyPattern) {
		if (map == null)
			return null;
		final java.util.List<Object[]> v = new java.util.ArrayList<Object[]>();
		String akeyPatterns[] = StrUtil.splitString(keyPattern, ',');
		for (final Object key : map.keySet()) {
			if (keyPattern == null
					|| (key instanceof String && StrUtil.likeOneOf(
							(String) key, akeyPatterns)))
				v.add(new Object[] { key, map.get(key) });
		}
		return v.toArray(new Object[v.size()][]);
	}
	
	public static final Object[][] getAttValues(final Map map, final String keyPattern) {
		if (map == null)
			return null;
		final java.util.List<Object[]> v = new java.util.ArrayList<Object[]>();
		String akeyPatterns[] = StrUtil.splitString(keyPattern, ',');
		for (final Object key : map.keySet()) {
			if (keyPattern == null || (key instanceof String && StrUtil.likeOneOf((String) key, akeyPatterns))){
				Object vel = map.get(key);
				if (vel instanceof String[] && ((String[])vel).length >= 1){
					String[] vArr = (String[])vel;
					String valueStr = "";
					for (int i = 0; i < vArr.length; i++) {
						valueStr = (i == vArr.length - 1) ? valueStr + vArr[i] : valueStr + vArr[i] + ",";
					}
					v.add(new Object[] { key, valueStr });
				}else{
					v.add(new Object[] { key, vel });
				}
			}
		}
		return v.toArray(new Object[v.size()][]);
	}
	
	/**
	 * 查询参数Map处理，用于回传参数
	 * @param map
	 * @return
	 */
	public static final Map getMapValues(final Map map) {
		return getMapValues(map,"filter.*");
	}
	
	/**
	 * 查询参数Map处理，用于回传参数
	 * @param map
	 * @param keyPattern  需要保留的参数类型，后面要加 * ，  "filter.*,gfilter.*"
	 * @return
	 */
	public static final Map getMapValues(final Map map, final String keyPattern) {
		Map h = new java.util.HashMap();
		if (map == null)
			return h;
		String akeyPatterns[] = StrUtil.splitString(keyPattern, ',');
		for (final Object key : map.keySet()) {
			if (keyPattern == null || (key instanceof String && StrUtil.likeOneOf((String) key, akeyPatterns))){
				Object vel = map.get(key);
				String key1 = StrUtil.obj2str(key);
				int p = key1.indexOf('.');
				String paramPrifix = key1.substring(0, p);
				String paramName = key1.substring(p+1);
				if (paramName.charAt(0) == '['){
					p = paramName.indexOf("].");
					if (p >= 0){
						paramName = paramName.substring(p+2);
					}
				}
				if (!"filter".equals(paramPrifix))
					paramName = paramPrifix+"_"+paramName;
				
				paramName = StrUtil.replace(paramName, ".", "_");
				paramName = StrUtil.replace(paramName, "[", "");
				paramName = StrUtil.replace(paramName, "]", "");
				
				if (vel instanceof String[] && ((String[])vel).length >= 1){
					String[] vArr = (String[])vel;
					String valueStr = "";
					for (int i = 0; i < vArr.length; i++) {
						valueStr = (i == vArr.length - 1) ? valueStr + vArr[i] : valueStr + vArr[i] + ",";
					}
					h.put(paramName, valueStr);
				}else{
					h.put(paramName, vel);
				}
			}
		}
		return h;
	}

	/**
	 * 将m中可以匹配keyPatterns中key的数据装载到to中并返回； 算法：如果匹配参数为空，不执行任何操作。
	 * 循环遍历keyPatterns中的匹配格式
	 * ，调用getValues()方法（详情参见getValues()）过滤取数，然后将过滤后的数全部装入to中，并返回to；
	 * 
	 * @param m参数源Map
	 * @param to装载到的Map
	 * @param keyPatterns过滤源数据用的过滤格式字符串数组
	 */
	static public void loadParamTo(java.util.Map map, java.util.Map to,
			String keyPatterns[]) {
		if (keyPatterns == null)
			return;
		for (int i = 0; i < keyPatterns.length; i++) {
			Object v[][] = getValues(map, keyPatterns[i]);
			if (v != null)
				for (int j = 0; j < v.length; j++) {
					if (v[j][0] != null && v[j][1] != null)
						to.put(v[j][0], v[j][1]);
				}
		}
	}

	/**
	 * 将一行形如key=value形式的文本解析，并将key和value构造出数组返回，可以根据需要将引号去除；
	 * 算法：文本需非空，对文本中’=’进行定位，根据需要如果要去除引号，则会对key和value分别去除引号，否则保持原始值，作为数组返回；
	 * 2014-12-17: 加 oldValues
	 * 
	 * @param text属性文件中的一行
	 *            ，文本格式
	 * @param removeQuotes是否需去除两端引号
	 * @return key和value组成的一个数组
	 * @param options
	 *            #1：是否去除引号; #2：+= #4: =<空> 被删除 ( since 2015-01-20)
	 */
	static private String[] parseProperty(String text, int options,
			java.util.Map<String, String> oldValues) {
		if (text == null)
			return null;
		int p = (text = text.trim()).indexOf('=');
		if (p <= 0)
			return null;
		String name;// = text.substring(0, p).trim();
		boolean add = false;
		if ((options & 2) != 0 && oldValues != null
				&& text.charAt(p - 1) == '+') {
			name = text.substring(0, p - 1).trim();
			add = true;
			// join =
		} else {
			name = text.substring(0, p).trim();
			if (p == text.length() - 1 && (options & 4) != 0
					&& oldValues != null) {
				oldValues.remove(name);
				return null;
			}
		}
		if ((options & 1) != 0 && text.charAt(0) == '"') {
			final int q = text.indexOf('"', 1);
			if (q < 0)
				return null;
			name = text.substring(1, q);
			p = text.indexOf('=', q);
			if (p <= 0)
				return null;
			if (oldValues != null && text.charAt(p - 1) == '+') {
				add = true;
			}
		}
		if (name.length() == 0)
			return null;
		String value = text.substring(p + 1).trim();
		char join;
		if (add) {
			join = value.charAt(0);
			value = value.substring(1).trim();
			if (Character.isJavaIdentifierPart(join))
				throw new java.lang.IllegalArgumentException(
						"Invalid join char " + join);
		} else {
			join = 0;
		}
		if ((options & 1) != 0 && value.length() >= 2 && value.charAt(0) == '"'
				&& value.charAt(value.length() - 1) == '"')
			value = value.substring(1, value.length() - 1);
		if (join != 0) {
			String old = oldValues.get(name);
			value = StrUtil.strcat(old, join, value);
		}
		// props.put(name,value);
		return new String[] { name, value };
	}

	/**
	 * 将文件中的属性解析装载到一个Map中，该方法可能会抛出IOException；
	 * 算法：文件流不能为空，将文件中内容按行读取，并通过首字符的判断去掉注释，调用parseProperty()（参见2.1.19）得到一个一维数组，
	 * 将数组内第一个元素作key，第二个元素作value的形式装入props中，并返回；
	 * 
	 * @param props属性装载到的Map
	 * @param in带缓存的文件读取器
	 * @param options
	 *            #1：是否去除引号; #2：+= #4: 空格
	 */
	static public void loadToMap(java.util.Map<String, String> props,
			BufferedReader in, int options, java.util.Map macro)
			throws IOException // 1.1
	{
		if (in == null)
			return;
		for (;;) {
			String lineText = in.readLine();
			if (lineText == null)
				break;
			lineText = lineText.trim();
			if (lineText.length() == 0 || lineText.charAt(0) == '#'
					|| lineText.charAt(0) == '!' || lineText.startsWith("//"))
				continue;
			if (macro != null && lineText.indexOf("${") >= 0) {
				lineText = StrUtil.macroReplace(lineText, macro, "${", "}");
			}
			// putProperty(props,lineText,removeQuotes);
			String[] a = parseProperty(lineText, options, props);// removeQuotes,enableAdd?props:null);
			if (a != null)
				props.put(a[0], a[1]);
		}
	}

	static public void loadToMap(java.util.Map<String, String> props,
			BufferedReader in, boolean removeQuotes)// ,java.util.Map macro)
			throws IOException // 1.1
	{
		loadToMap(props, in, removeQuotes ? 1 : 0, null);
	}

	/**
	 * 将property文件中的内容装载到一个二维数组，并将该数组返回，给方法可能会抛出IOException；
	 * 算法：文件流不能为空，将文件内容按行读取
	 * ，并通过首字符的判断去掉注释，调用parseProperty()（参见parseProperty()）得到一个一维数组，
	 * 将数组添加到链表中，全部解析完毕后，将链表转换为数组并返回；
	 * 
	 * @param in带缓存的文件读取器
	 * @param removeQutotes是否去除引号
	 * @return 解析得到的属性数组
	 */
	static public String[][] loadProperties(BufferedReader in,
			boolean removeQuotes) throws IOException // 1.1
	{
		if (in == null)
			return null;
		// String text = null;
		final java.util.List<String[]> v = new ArrayList<String[]>();
		for (;;) {
			String lineText = in.readLine();
			// System.out.println("lineText="+lineText);
			if (lineText == null)
				break;
			lineText = lineText.trim();
			if (lineText.length() == 0 || lineText.charAt(0) == '#'
					|| lineText.charAt(0) == '!' || lineText.startsWith("//"))
				continue;
			String[] a = parseProperty(lineText, removeQuotes ? 1 : 0, null);
			if (a != null)
				v.add(a);
			// putProperty(props,lineText,removeQuotes);
		}
		return v.toArray(new String[v.size()][]);
	}

	static public String[][] loadProperties(String srcText, boolean removeQuotes)
			throws IOException // 1.1
	{
		if (srcText == null)
			return null;
		return loadProperties(new BufferedReader(new StringReader(srcText)),
				removeQuotes);
	}

	static public java.util.Map loadPropertiesAsMap(String text,
			boolean removeQuotes) throws IOException {
		if (text == null)
			return null;
		java.util.Map props = new HashMap();
		loadToMap(props, new BufferedReader(new StringReader(text)),
				removeQuotes ? 1 : 0, null);
		return props;
	}

	static public void loadToMap(java.util.Map<String, String> props,
			String text, boolean removeQuotes) throws IOException // 1.1
	{
		loadToMap(props, new BufferedReader(new StringReader(text)),
				removeQuotes ? 1 : 0, null);
	}

	/**
	 * 将参数字符串解析并将解析结果装载到一个Map中；
	 * 算法：如果参数不为空，则将参数以’&’切割为数组并遍历，如果切割后的小字符串可以满足key=value的形式
	 * ，并将key和value作为键值对装入to中，将to返回；
	 * 
	 * @param paramText参数文本
	 * @param to解析后参数装载到的Map
	 * @return 装载后的to
	 */
	static public java.util.Map<String, String> parseParameterTo(
			String paramText, java.util.Map<String, String> to) {
		if (paramText == null)
			return to;
		String a[] = StrUtil.splitString(paramText, '&');
		for (int i = 0; i < a.length; i++) {
			final String si = a[i].trim();
			int p = si.indexOf('=');
			if (p < 0)
				continue;
			if (to == null)
				to = new HashMap<String, String>();
			to.put(si.substring(0, p).trim(), si.substring(p + 1));
		}
		return to;
	}

	static public int getIntValue(java.util.Map m, Object key, int defaultValue) {
		return m == null ? defaultValue : StrUtil.obj2int(m.get(key),
				defaultValue);
	}

	static public String findFullname(java.util.Map valueMap, String code) {
		return findFullname(valueMap, code, null, null);
	}

	static public String findFullname(java.util.Map valueMap, String code,
			int showLevlFullnm[]) {
		return findFullname(valueMap, code, null, showLevlFullnm);
	}

	/**
	 * 根据码表和编码查找该编码的全名。 Eg1： Map<String,String> valueMap = new TreeMap();
	 * valueMap.put("01", "安徽"); valueMap.put("0101", "亳州");
	 * valueMap.put("010101", "赵桥"); valueMap.put("01010101", "冀老家");
	 * out.println(MapUtil.findFullname(valueMap, "01010101",null,new
	 * int[]{1,2,4})); Console:安徽-亳州-冀老家
	 * 
	 * @param valueMap
	 *            码表
	 * @param code
	 *            当前编码，可以是中间级
	 * @param codePrefix
	 *            TODO
	 * @param showLevlFullnm
	 *            全名级别，可以跳过若干级别
	 * @return 找到的名称，多级用“-”分隔
	 */
	static public String findFullname(java.util.Map valueMap, String code,
			String codePrefix, int showLevlFullnm[]) {
		if (code == null)
			return null;
		// String fullname = null;
		final java.util.List<String> v = new ArrayList<String>();
		// final int codeLen;
		int l = code.length();
		// if( codePrefix==null ) codePrefix = "";
		for (; l > 0; l--) {
			String scode = code.substring(0, l);
			if (codePrefix != null)
				scode = codePrefix + scode;
			Object o = valueMap.get(scode); //
			if (o instanceof String) {
				v.add((String) o);
			}
		}
		final int n = v.size();
		StringBuilder sb = null;// new StringBuffer();
		// if( n==0 ) return null;
		for (int i = 0; i < n; i++) {
			String s = v.get(n - i - 1);
			if (s != null)
				s = s.trim();
			if (showLevlFullnm != null) {
				int i1 = i + 1, i2 = -(n - i);
				boolean show = false;
				for (int j = 0; j < showLevlFullnm.length; j++) {
					if (showLevlFullnm[j] == i1 || showLevlFullnm[j] == i2) {
						show = true;
						break;
					}
				}
				if (!show)
					continue;
			}
			if (sb == null)
				sb = new StringBuilder(s);
			else {
				sb.append('-');
				sb.append(s);
			}
		}
		return sb == null ? null : sb.toString();
	}

	/**
	 * 将Map中的内容转换为一个二维数组，得到的数组外层为Map中多个键值对，内层数组第一个元素为key，第二个元素为value，将数组返回；
	 * 算法：m需非null，将m中的内容按照keyPatterns的匹配将，匹配上的键，值存到一维数组中缓存到一个链表中，最后将链表转换为数组并返回；
	 * 例子： Map m1 = new HashMap(); m1.put("1", 1); m1.put(2, 2); m1.put("3", 3);
	 * for (Object[] objs : MapUtil.mapToArray(m1, null)) out.println(objs[0] +
	 * "=" + objs[1]); Console： 3=3 2=2 1=1
	 * 
	 * @param m待转换的Map
	 * @param keyPatterns用于过滤数据的格式字符串数组
	 * @return 转换后的二维数组
	 */
	static public Object[][] mapToArray(final java.util.Map m,
			String keyPatterns[]) {
		if (m == null)
			return null;
		final java.util.List<Object[]> v = new java.util.ArrayList<Object[]>();
		// for(java.util.Enumeration e=m.keys();e.hasMoreElements();)
		for (final Object k : m.keySet()) {
			// Object k = e.nextElement();
			if (keyPatterns == null
					|| (k instanceof String && StrUtil.likeOneOf((String) k,
							keyPatterns)))
				v.add(new Object[] { k, m.get(k) });
		}
		return v.isEmpty() ? null : v.toArray(new Object[v.size()][]);
	}

	/**
	 * 将m中数据装载到to中并返回，详情参见 dupMap()；
	 * 
	 * @param m参数源Map
	 * @param to装载到的Map
	 */
	static public void loadParamTo(java.util.Map map, java.util.Map to) {
		MapUtil.dupMap(to, map);
	}

	/**
	 * 定义缺省宏变量，宏变量放在macro中； 算法：将缺省的宏变量放入macro中，有些变量在生成时需用到其他工具方法，所以需环境变量参数； 例子：
	 * macro = new java.util.HashMap()
	 * MapUtil.defineDefaultMacro(getEnvParams(), macro)
	 * getProgress().addMessage(macro.toString()) Console：{CURDATEMONDAYS=31,
	 * CURDATEYEAR=2012, CURDATETIME=2012-08-06 16:45:13, NEXTDATEYEAR=2013,
	 * CURDATE=2012-08-06, CURDATEMONTH2=08, PREVDATEYEAR=2011, CURDATEMONTH=8,
	 * CURDATEDAY=6,CURDATEP30=2012-07-07}
	 * 
	 * @param envParams
	 * @param macro定义好的宏变量放在该map中
	 */
	static public void defineDefaultMacro(java.util.Map envParams,
			java.util.Map macro) {
		java.util.Date cdate = new java.util.Date();
		macro.put("CURDATE", DateUtil.dateToString(cdate, 2));
		macro.put("CURDATEP30",
				DateUtil.dateToString(DateUtil.incDate(cdate, -30), 2));
		macro.put("CURDATETIME", DateUtil.dateToString(cdate));
		final int y, m;
		macro.put("CURDATEYEAR",
				Integer.valueOf(y = DateUtil.getDateYear(cdate)));
		final int y2 = y % 100;
		macro.put("CURDATEYEAR2", y2 < 10 ? "0" + y2 : "" + y2);
		macro.put("CURDATEMONTH",
				Integer.valueOf(m = DateUtil.getDateMonth(cdate)));
		macro.put("CURDATEMONTH2", m < 10 ? "0" + m : "" + m);
		macro.put("CURDATEDAY", Integer.valueOf(DateUtil.getDateDay(cdate)));
		macro.put("CURDATEMONDAYS",
				Integer.valueOf(DateUtil.getDaysOfMonth(y, m)));
		// int y = DateUtil.getDateYear(cdate);
		macro.put("PREVDATEYEAR", y - 1);
		int m2 = DateUtil.getDateMonth(cdate) - 1;
		macro.put("PREVDATEYEARM", m2 > 0 ? y : y - 1);
		macro.put("PREVDATEMONTH", m2 > 0 ? m2 : 12);
		m2 = DateUtil.getDateMonth(cdate) + 1;
		macro.put("NEXTDATEYEAR", m2 > 12 ? 1 : m2);
	}

	/**
	 * 将Map中所有的key转换到一个数组to中，并将to返回；
	 * 算法：如果to为null，则开辟一个m.size()大小的数组，非空时to的长度必须和m
	 * .size()相等，通过对m.keySet()的遍历，将每一个键存储到数组中； 例子： Map m1 = new HashMap();
	 * m1.put("1", 1); m1.put(2, 2); m1.put("3", 3); for (Object obj :
	 * MapUtil.keysToArray(m1, null)) out.println(obj); Console： 3 2 1
	 * 
	 * @param m待转换的Map
	 * @param to数据拷贝到的数组
	 * @return 拷贝后的to
	 */
	static public <K, V> K[] keysToArray(Map<K, V> m, K to[]) {
		if (to == null)
			to = (K[]) new Object[m.size()];
		else if (to.length != m.size())
			throw new java.lang.IllegalArgumentException();
		int i = 0;
		for (final K k : m.keySet()) {
			to[i++] = k;
		}
		return to;
	}

	/**
	 * 将Map中的entry复制到一个数组to中，并将to返回，得到的是键值对数组；
	 * 算法：如果to为null，则开辟一个m.size()大小的数组，非空时to的长度必须和m
	 * .size()相等，通过对m.enrtySet()的遍历，将每一个键值对存储到数组中； 例子： Map m1 = new HashMap();
	 * m1.put("1", 1); m1.put(2, 2); m1.put("3", 3); Object[] objs = new
	 * Object[m1.size()]; for (Object object : MapUtil.elementsToArray(m1,
	 * objs)) out.println(object); Console： 3=3 2=2 1=1
	 * 
	 * @param m待转换的Map
	 * @param to数据拷贝到的数组
	 * @return 拷贝后的to
	 */
	static public <K, T> T[] valuesToArray(Map<K, T> m, T to[]) {
		if (to == null)
			to = (T[]) new Object[m.size()];
		else if (to.length != m.size())
			throw new java.lang.IllegalArgumentException();
		int i = 0;
		// for(java.util.Enumeration e=m.elements();e.hasMoreElements();)
		for (final T v : m.values()) {
			to[i++] = v;
		}
		return to;
	}

	static public Object[] keysToArray(Map m) {
		return keysToArray(m, null);
	}

	static public Object[] valuesToArray(Map m) {
		return valuesToArray(m, null);
	}

	/**
	 * 将Map中的各个元素转换成key=value格式delim分隔的字符串；
	 * 算法：以StringBuilder做缓存，循环遍历map，将key和value拼到StringBuilder内，并返回成字符串； 例子： Map
	 * m1 = new HashMap(); m1.put("1", 1); m1.put(2, 2); m1.put("3", 3);
	 * out.println(MapUtil.toString(m1, ";")); Console：3=3;2=2;1=1
	 * 
	 * @param m待转换的Map
	 * @param delim每一个键值对之间的分隔符
	 * @return 转换后的字符串
	 */
	public static String toString(Map m, String delim) {
		if (m == null)
			return null;
		// TODO 彭凯 edited L640-L642
		if (delim == null)
			delim = ",";
		StringBuilder sb = new StringBuilder();
		for (final Object key : m.keySet()) {
			Object v = m.get(key);
			if (sb.length() > 0)
				sb.append(delim);
			sb.append(key).append('=').append(v);
		}
		return sb.toString();
	}

	/**
	 * 比较两个map中的元素是否相等； 算法：如果两个map为同一个对象，返回true。如果其中一个map为null，返回false。
	 * 分别对两个Map的遍历，并分别比较对应键的值是否相等，出现一次不等则返回false，完全相等返回true； 例子： Map m1 = new
	 * HashMap(); m1.put("1", 1); m1.put(2, 1); m1.put("3", 3); Map m2 = new
	 * HashMap(); m2.put("1", 1); m2.put("2", 2); m2.put("3", 3);
	 * out.println(MapUtil.elementsEquals(m1, m2)); Console：false
	 * 
	 * @param o1待比较Map1
	 * @param o2待比较Map2
	 * @return 是否相等
	 */
	static public boolean elementsEquals(java.util.Map o1, java.util.Map o2) {
		if (o1 == o2)
			return true;
		if (o1 == null || o2 == null)
			return false;
		for (final Object key : o1.keySet()) {
			if (!Data.objEquals(o1.get(key), o2.get(key)))
				return false;
		}
		for (final Object key : o2.keySet()) {
			if (!Data.objEquals(o1.get(key), o2.get(key)))
				return false;
		}
		return true;
	}

	/**
	 * 将给定的两个Map合并为一个Map； 算法：如果两个Map有一个为空，则返回另外一个。
	 * 非空情况下，将m1构造为一个新Map即m，将m2中所有键值复制添加到新m中，key有重复则覆盖，将新m返回； 例子： Map m1 = new
	 * HashMap(); m1.put("1", 1); m1.put("2", 2); m1.put("3", 3); Map m2 = new
	 * HashMap(); m2.put("3", 4); m2.put("5", 5);
	 * out.println(MapUtil.buildUnionMap(m1, m2)); Console：{3=4, 2=2, 1=1, 5=5}
	 * 
	 * @param m1需合并的map1
	 * @param m2需合并的map2
	 * @return 返回值为合并后的map
	 */
	static public java.util.Map buildUnionMap(java.util.Map m1, java.util.Map m2) {
		if (m2 == null || m2.size() == 0)
			return m1;
		if (m1 == null || m1.size() == 0)
			return m2;
		java.util.Map m = new HashMap(m1);
		MapUtil.dupMap(m, m2);
		return m;
	}

	static public <K, V> Map<K, V> arrayToMap(Map<K, V> toMap, K[] key,
			V[] value) {
		return arrayToMap(toMap, key, value, null);
	}

	/**
	 * 循环将key数组中的key值，以col数组中的每一个数（即列数）在value数组中该列所对应的值为value，将键-
	 * 值对存入toMap中并返回toMap；
	 * 算法：在键值数组为null情况下，返回空Map。非空情况下，以key数组的遍历为循环条件，依次从value数组中取值，
	 * 如col数组非空，则按col数组，取到指定列值上的value，如col为null则依次取值； 例子： String[] keys = new
	 * String[] {"1","2","3","4"}; Integer[] values = new Integer[]
	 * {1,2,3,4,5,6,7,8,9}; int[] cols = new int[] {1,3,5}; Map<String,Integer>
	 * testMap = null; out.println(MapUtil.arrayToMap(testMap, keys, values,
	 * cols)); Console:{3=6, 2=4, 1=2}
	 * 
	 * @param toMap
	 *            数据结构转换后数据所存储的Map
	 * @param key
	 *            键的数组，作为所生成的Map的键
	 * @param value
	 *            值的数组，作为所生成Map的值的取值来源
	 * @param col
	 *            列值的数组，欲在value数组中指定列取数的列数存放数组
	 * @return 返回值为转换后的toMap
	 */
	static public <K, V> Map<K, V> arrayToMap(Map<K, V> toMap, K[] key,
			V[] value, int[] col) {
		if (toMap == null) {
			toMap = new HashMap<K, V>();
		}
		if (key == null || value == null) {
			return toMap;
		}
		int count = key.length;
		int colLen = col == null ? 0 : col.length;
		for (int i = 0; i < count; i++) {
			int row = col == null ? i : colLen > i ? col[i] : -1;
			// TODO 彭凯 edited L754
			if (row >= 0 && row < value.length) {
				toMap.put(key[i], value[row]);
			}
		}
		return toMap;
	}

	static public Object[][] toArrays(java.util.Map values[], Object keys[]) {
		if (values == null)
			return null;
		final int nr = values.length;
		final int nc = keys.length;
		Object a[][] = new Object[nr][nc];
		for (int r = 0; r < nr; r++) {
			for (int j = 0; j < nc; j++)
				a[r][j] = values[r].get(keys[j]);
		}
		return a;
	}

	static public <K, V> String toString(java.util.Map<K, V> map,
			String prefix, String suffix, char join) {
		try {
			return appendTo(new StringBuilder(), map, prefix, suffix, join)
					.toString();
		} catch (java.io.IOException ex) {
			throw com.hibo.bas.exception.HookedException.toRuntimeException(ex);
		}
	}

	static public <K, V> java.lang.Appendable appendTo(java.lang.Appendable sb,
			java.util.Map<K, V> map, String prefix, String suffix, char join)
			throws java.io.IOException {
		if (prefix != null)
			sb.append(prefix);
		Iterator<Entry<K, V>> i = map.entrySet().iterator();
		if (!i.hasNext())
			return suffix == null ? sb : sb.append(suffix);
		for (;;) {
			Entry<K, V> e = i.next();
			K key = e.getKey();
			V value = e.getValue();
			sb.append(key == map ? "(this Map)" : String.valueOf(key));
			sb.append('=');
			sb.append(value == map ? "(this Map)" : String.valueOf(value));
			if (!i.hasNext())
				return suffix == null ? sb : sb.append(suffix);
			sb.append(join).append(' ');
		}
	}
}