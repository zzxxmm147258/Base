package com.hibo.bas.util;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月4日 下午7:03:24</p>
 * <p>类全名：com.hibo.bas.util.Config</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public final class Config {
	/*
	 * X.X.XX.yyyymmdd since 1.0.0 : 1.0 版本
	 */
	static final private int version = 0x1_0_00_0000 | (15 << 9) | (7 << 5) | 1;

	// 6 4 5
	@SuppressWarnings("unused")
	static final public String getVersion() {
		final int month = (version >> 5) & 0xf; // 4
		final int day = version & 0x1f; // 5
		return (version >> 28) + "." + ((version >> 24) & 0xf) + "."
				+ ((version >> 16) & 0xff) + "."
				+ ((version >> 9) & 0x3f) // year
				+ ((month >= 10 ? "" : "0") + month)
				+ ((day >= 10 ? "" : "0") + day);
	}

	static final public int version() {
		return version;
	}

	private static long libsReleaseTime;

	static public void setLibsReleaseTime(long time) {
		if (time > Config.startTime)
			time = Config.startTime;
		Config.libsReleaseTime = time;
	}

	static final public long getReleaseTime(boolean caseLibs) {
		final int year = (version >> 9) & 0x3f;
		final int month = (version >> 5) & 0xf;
		final int day = version & 0x1f;
		final long time = DateUtil.toLongTime(2000 + year, month, day);
		return caseLibs && time < libsReleaseTime ? libsReleaseTime : time;
	}

	static final public long startTime = System.currentTimeMillis(); // 启动时间
	static final public String localHostAddress;

	public static final int javaVersion; // 0x130 for 1.3, 0x122 for 1.22
	/*
	 * 1: Windows 2： Mac OS
	 */
	static public final int osType;
	static final public int msgsysVersion = 0x102;

	private static String webContextPath;

	public static void setWebContextPath(String path) {
		Config.webContextPath = path;
	}

	public static String getWebContextPath()// String webContextPath)
	{
		return webContextPath;
	}

	public static String webContextRealPath; //
	public final static String ConfigPath;

	static final public boolean test; // hb.test
	static {
		String jvs = System.getProperty("java.version");
		int jv = 0;
		if (jvs != null) {
			int p = -1;
			for (int i = 2; i >= 0; i--) {
				int p0 = p + 1;
				p = jvs.indexOf('.', p0);
				String subStr;
				if (p < 0)
					subStr = jvs.substring(p0);
				else
					subStr = jvs.substring(p0, p);
				int q = subStr.indexOf('_');
				if (q >= 0)
					subStr = subStr.substring(0, q);
				try {
					jv |= Integer.parseInt(subStr) << (i * 4);
				} catch (Exception ex) {
				}
				if (p < 0)
					break;
			}
		}
		javaVersion = jv;
		int _osType = 0;
		try {
			String osName = System.getProperty("os.name");// .toLowerCase();
			if (osName != null) {
				osName = osName.toLowerCase();
				/*
				 * Windows 7 Mac OS X
				 */
				// System.out.println("os.name = "+osName); // Windows 7
				if (osName.indexOf("windows") >= 0) {
					_osType = 1;
				} else if (osName.indexOf("mac os") >= 0) {
					_osType = 2;
				}
			}
		} catch (Throwable ex) {
		}

		osType = _osType;
		String addr = null;
		try {
			addr = java.net.InetAddress.getLocalHost().getHostAddress();
		} catch (Throwable ex) {
		}
		localHostAddress = addr;

		String configPath = "../hbconfig";
		try {
			String path = System.getProperty("hb.ConfigPath");
			if (path != null) {
				configPath = path.length() > 0 ? path : ".";
			} else {
				String testPaths[] = { "../hbconfig", ".", "./hbconfig", "..",
						"../../bhconfig" };
				for (int i = 0; i < testPaths.length; i++) {
					if (new java.io.File(testPaths[i] + "/WorkSpace.xml").isFile()) {
						configPath = testPaths[i];
						break;
					}
				}
			}
		} catch (Throwable ex) {
		}
		ConfigPath = configPath;
		boolean _test = false;
		try {
			_test = "true".equalsIgnoreCase(System.getProperty("hb.test"));
		} catch (Throwable ex) {
		}
		test = _test;

		java.util.TimeZone.setDefault(null);
	}
}
