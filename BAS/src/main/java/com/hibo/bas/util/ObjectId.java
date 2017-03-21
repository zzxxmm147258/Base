package com.hibo.bas.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.sf.ehcache.config.generator.ConfigurationUtil;

/**
 * <p>标题：分配唯一码 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月3日 下午3:59:30</p>
 * <p>类全名：com.hibo.util.ObjectId</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class ObjectId implements Serializable {
	private static final long serialVersionUID = 837806216L;
	private static final Random rnd = new Random();

	/** Create a new object id. */
	public ObjectId() {
		this(new Date());
	}

	public ObjectId(String s) {
		if (!isValid(s))
			throw new IllegalArgumentException("invalid ObjectId [" + s + "]");

		byte b[] = new byte[12];
		for (int i = 0; i < b.length; i++) {
			b[b.length - (i + 1)] = (byte) Integer.parseInt(
					s.substring(i * 2, i * 2 + 2), 16);
		}
		_inc = readInt(b, 0);
		_machine = readInt(b, 4);
		_time = readInt(b, 8);
	}

	public ObjectId(Date time) {
		this(time, rnd.nextInt());
	}

	public ObjectId(Date time, int inc) {
		this(time, rnd.nextInt(), inc);
	}

	public ObjectId(Date time, int machine, int inc) {
		_time = _flip((int) (time.getTime() / 1000));
		_machine = machine;
		_inc = inc;
	}

	/**
	 * Added this to simplify the serialization
	 * 
	 * @param time
	 * @param machine
	 * @param inc
	 */
	ObjectId(int time, int machine, int inc) {
		_time = time;
		_machine = machine;
		_inc = inc;
	}

	/**
	 * Checks if a string could be an <code>ObjectId</code>.
	 * 
	 * @return whether the string could be an object id
	 */
	public static boolean isValid(String s) {
		if (s == null)
			return false;

		final int len = s.length();
		if (len != 24)
			return false;

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9')
				continue;
			if (c >= 'a' && c <= 'f')
				continue;
			if (c >= 'A' && c <= 'F')
				continue;

			return false;
		}

		return true;
	}

	public int hashCode() {
		int x = _time;
		x += (_machine * 111);
		x += (_inc * 17);
		return x;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;

		ObjectId other = massageToObjectId(o);
		if (other == null)
			return false;

		return _time == other._time && _machine == other._machine
				&& _inc == other._inc;
	}

	public String toString() {
		byte b[] = toByteArray();

		StringBuilder buf = new StringBuilder(24);

		for (int i = 0; i < b.length; i++) {
			int x = b[i] & 0xFF;
			String s = Integer.toHexString(x);
			if (s.length() == 1)
				buf.append("0");
			buf.append(s);
		}

		return buf.toString();
	}

	/**
	 * Turn an object into an <code>ObjectId</code>, if possible. Strings will
	 * be converted into <code>ObjectId</code>s, if possible, and
	 * <code>ObjectId</code>s will be cast and returned. Passing in
	 * <code>null</code> returns <code>null</code>.
	 * 
	 * @param o
	 *            the object to convert
	 * @return an <code>ObjectId</code> if it can be massaged, null otherwise
	 */
	public static ObjectId massageToObjectId(Object o) {
		if (o == null)
			return null;

		if (o instanceof ObjectId)
			return (ObjectId) o;

		if (o instanceof String) {
			String s = o.toString();
			if (isValid(s))
				return new ObjectId(s);
		}

		return null;
	}

	public byte[] toByteArray() {
		byte b[] = new byte[12];
		putInt(_inc, b, 0);
		putInt(_machine, b, 4);
		putInt(_time, b, 8);
		reverse(b);
		return b;
	}

	static void reverse(byte[] b) {
		for (int i = 0; i < b.length / 2; i++) {
			byte t = b[i];
			b[i] = b[b.length - (i + 1)];
			b[b.length - (i + 1)] = t;
		}
	}

	public static int _flip(int x) {
		int z = 0;
		z |= ((x << 24) & 0xFF000000);
		z |= ((x << 8) & 0x00FF0000);
		z |= ((x >> 8) & 0x0000FF00);
		z |= ((x >> 24) & 0x000000FF);
		return z;
	}

	public int getMachine() {
		return _machine;
	}

	public long getTime() {
		long z = _flip(_time);
		return z * 1000;
	}

	public int getInc() {
		return _inc;
	}

	/**
	 * Masking to only use the least significant byte of an integer
	 */
	private static final int BYTE_MASK = 0x000000FF;

	/**
	 * Read 4 bytes from the given array starting at the given index and turn
	 * them into an integer
	 * 
	 * @param bytes
	 *            the byte array to read from
	 * @param startIndex
	 *            the index to start att
	 * @return the integer represented by the four bytes
	 */
	private static final int readInt(byte[] bytes, int startIndex) {
		int b0 = BYTE_MASK & (int) bytes[0 + startIndex];
		int b1 = BYTE_MASK & (int) bytes[1 + startIndex];
		int b2 = BYTE_MASK & (int) bytes[2 + startIndex];
		int b3 = BYTE_MASK & (int) bytes[3 + startIndex];
		int val = b0 << 24 | b1 << 16 | b2 << 8 | b3;

		return val;
	}

	/**
	 * Write the integer 'val' as a 4 byte value to the given array starting at
	 * the given index
	 * 
	 * @param val
	 *            the integer value to write
	 * @param bytes
	 *            the byte array to write to
	 * @param startIndex
	 *            the index in the array to start writing at
	 */
	public static void putInt(int val, byte[] bytes, int startIndex) {
		for (int i = 0; i < 4; i++) {
			bytes[i + startIndex] = (byte) (val & BYTE_MASK);
			val = val >> 8;
		}
	}

	int _time;
	int _machine;
	int _inc;

	/**
	 * 获取唯一码
	 * 
	 * @return
	 */
	static public String getId() {
		return new ObjectId().toString();
	}
	
	/**
	 * UUID 32位
	 * @return
	 */
	static public String getUUId(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	private static boolean bNowServerNo=false;
	private static String nowServerNo="";
	private static java.util.Hashtable<String,Integer> idxList= new java.util.Hashtable<String,Integer>();
	public static String getNowServerNo(){
		if (!bNowServerNo){
			nowServerNo=com.hibo.bas.util.DataConfig.getConfig("sys.nowServerNo", "");
			bNowServerNo = true;
		}
		return nowServerNo;
	}
	
	/**
	 * 获取订单号，  num 为服务器代码，解决不同服务器之间分码问题
	 * @param type   编码来源，如电商、APP、POS
	 * @param preStr 编码前缀 ，如订单1，退货2
	 * @param bDateSeconds 日期是否转秒
	 * @param bIdx  日期时间顺序是否要打乱
	 * @return
	 */
	public static String getOrderNum(String type, String preStr){
		return getOrderNum(type, preStr, true, false);
	}
	
	public static String getOrderNum(String type, String preStr, boolean bDateSeconds, boolean bIdx){
		synchronized(idxList){
			if (preStr==null)
				preStr = "";
			String num = getNowServerNo();
			num += type+preStr;
			Date date = new Date();
			String dates = "";
			if (bDateSeconds){
				dates = com.hibo.bas.util.DateUtil.dateToSeconds(date);
			}else{
				dates = com.hibo.bas.util.DateUtil.dateToString2(date,true);
			}
			String times = com.hibo.bas.util.DateUtil.timeToSeconds(date);
			times = "00000"+times;
			times = times.substring(times.length()-5);
			String nonce = "00"+new java.util.Random().nextInt(100);
			nonce = nonce.substring(nonce.length()-2);
			Integer no = idxList.get(type+"-"+preStr);
			if (no == null || no > 100)
				no = 0;
			no++;
			idxList.put(type+"-"+preStr,no);
			String noStr = "00"+no;
			noStr = noStr.substring(noStr.length()-2);
			if (bDateSeconds && bIdx){
				String dates1 = dates.substring(0, 2);
				String dates2 = dates.substring(2);
				String times1 = times.substring(0, 2);
				String times2 = times.substring(2);
				num += dates1+times1+dates2+times2+noStr+nonce;
			}else{
				num += dates+times+noStr+nonce;
			}
			return num;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
//			System.out.println(getOrderNum("", "", true, false));
		}

	}

}
