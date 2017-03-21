package com.hibo.bas.util;

import java.io.PrintStream;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午3:54:21</p>
 * <p>类全名：com.hibo.bas.util.Diagnostic</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
final public class Diagnostic {
	public static PrintStream out = System.out;

	final public static int LVOFF = 0;
	final public static int LVERROR = 3;
	final public static int LVWARN = 4;
	final public static int LVINFO = 6;
	final public static int LVDEBUG = 7;
	final public static int LVALL = 9;
	private static int traceLevl;

	static {
		initForSystemProperty();
	};

	static public int getLogLevl() {
		return traceLevl;
	}

	static public void initForSystemProperty() {
		try {
			String s = System.getProperty("Diagnostic.TraceLevel");
			if (s != null)
				traceLevl = Integer.parseInt(s);
		} catch (Throwable ex) {
		}
	}

	public static boolean isLevelEnableOutput(int level) {
		return level <= traceLevl;
	}

	public static boolean isEnableLvInfoOutput() {
		return traceLevl >= LVINFO;
	}

	public static boolean isEnableLvDebugOutput() {
		return traceLevl >= LVDEBUG;
	}

	public static boolean isEnableOutput() {
		return traceLevl >= LVINFO;
	}

	public static boolean isOutputEnabled() {
		return traceLevl >= LVINFO;
	}

	/**
	 * Set the minimum threshold for trace and warning output. 0 is highest
	 * level +maxint is lowest level
	 * 
	 * Setting this level to -1 will effectively turn off traces and warnings.
	 * NOTE: this does not effect direct access to 'out', nor does it effect
	 * output in the checking functions. Use enableOutput() for that.
	 */
	public static void setTraceLevel(int level) {
		if (traceLevl == level)
			return;
		traceLevl = level;
	}

	public static int getTraceLevel() {
		return traceLevl;
	}

	/**
	 * Output a trace if the threshold level is high enough and general output
	 * is enabled
	 */
	public static void trace(int level, String description) {
		if (level <= traceLevl)
			println(description);
	}

	/**
	 * Print a diagnostic stack trace of the current thread to the diagnostic
	 * out stream
	 */
	public static void printStackTrace() {
		if (traceLevl > 0) // outputEnabled)
		{
			printStackTrace(new Exception("Diagnostic Stack Trace"));
		}
	}

	public static void printStackTrace(Throwable ex) {
		if (traceLevl > 0 && ex != null) {
			synchronized (out) {
				com.hibo.bas.exception.ExceptionUtil.getThrowable(ex)
						.printStackTrace(out);
			}
		}
	}

	public static void printStackTrace(int level, Throwable ex) {
		if (level <= traceLevl && ex != null) // outputEnabled
		{
			synchronized (out) {
				com.hibo.bas.exception.ExceptionUtil.getThrowable(ex)
						.printStackTrace(out);
			}
		}
	}

	/**
	 * print a message to the diagnostic out stream
	 */
	public static void println(String message) {
		if (traceLevl > 0)
			out.println(message);
	}

	public static void println(Object message) {
		if (traceLevl > 0)
			out.println(message);
	}

	public static void println() {
		if (traceLevl > 0)
			out.println();
	}

	static int logToConsole;

	static public void logToConsole(boolean log) {
		if (log)
			logToConsole++;
		else
			logToConsole--;
	}

	/**
	 * Flushes the diagnostic out stream buffer
	 */
	public static void flush() {
		if (traceLevl > 0)
			out.flush();
	}

}
