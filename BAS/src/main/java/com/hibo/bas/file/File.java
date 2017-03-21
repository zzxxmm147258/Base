package com.hibo.bas.file;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午6:05:05</p>
 * <p>类全名：com.hibo.bas.file.File</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
final public class File {
	public final String path; // separatorChar = '/'
	public final boolean isdir;
	public final long time;
	public final long length;
	public final boolean rdonly, hidden;

	public File(String path, boolean isdir, long time, long length) {
		this.path = path;
		this.isdir = isdir;
		this.time = time;
		this.length = length;
		rdonly = false;
		hidden = false;
	}

	public File(String path, boolean isdir, long time, long length,
			boolean rdonly, boolean hidden) {
		this.path = path;
		this.isdir = isdir;
		this.time = time;
		this.length = length;
		this.rdonly = rdonly;
		this.hidden = hidden;
	}

	final public String getPath() {
		return path;
	}

	final public String getName() {
		int p = path.lastIndexOf('/');
		return p >= 0 ? path.substring(p + 1) : path;
	}

	static final public String getName(String path) {
		int p = path.lastIndexOf('/');
		return p >= 0 ? path.substring(p + 1) : path;
	}

	final public String getParentPath() {
		int p = path.lastIndexOf('/');
		return p >= 0 ? path.substring(0, p) : null;
	}

	@Override
	final public String toString() {
		return path;
	}

	final public boolean isDir() {
		return isdir;
	}

	final public long getTime() {
		return time;
	}

	final public long getLength() {
		return length;
	}

	final public boolean isReadonly() {
		return this.rdonly;
	}

	final public boolean isHidden() {
		return this.hidden;
	}

	final public boolean isFile() {
		return !this.isdir;
	}

	final public boolean isDirectory() {
		return this.isdir;
	}

	final public long length() {
		return length;
	}

	@Override
	public boolean equals(Object file) {
		return file instanceof com.hibo.bas.file.File
				&& path.equals(((com.hibo.bas.file.File) file).path)
				&& isdir == ((com.hibo.bas.file.File) file).isdir;
	}
}
