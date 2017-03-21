package com.hibo.bas.util;
/**
 * <p>标题：数组排序 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午5:16:58</p>
 * <p>类全名：com.hibo.bas.util.ObjectArrayComparator</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 * Object a[][] = ....
 * ObjectArrayComparator c = new ObjectArrayComparator(new int[]{0,1});//根据 第 0，1列排
 * java.util.Arrays.sort(a,c);
 * ObjectArrayComparator c = new ObjectArrayComparator(new int[]{0,1},new boolean{true,false});
 *      //根据 第 0(倒排)，1列排
 * java.util.Arrays.sort(a,c);
 *
 * int j = java.util.Arrays.binarySearch( a, new Object[]{val0,val1},c );
 */
public class ObjectArrayComparator implements java.util.Comparator<Object[]> {
	final public int sortIdx[];
	final public boolean desc[];

	public ObjectArrayComparator() {
		this.sortIdx = null;
		this.desc = null;
	}

	public ObjectArrayComparator(int sortIdx[], boolean desc[]) {
		if (sortIdx != null) {
			if (desc == null)
				desc = new boolean[sortIdx.length];
			else if (desc.length != sortIdx.length)
				throw new java.lang.IllegalArgumentException();
		}
		this.sortIdx = sortIdx;
		this.desc = desc;
	}

	public ObjectArrayComparator(int sortCnt) {
		sortIdx = new int[sortCnt];
		desc = new boolean[sortCnt];
		for (int i = 0; i < sortCnt; i++)
			sortIdx[i] = i;
	}

	public ObjectArrayComparator(int sortIdx[]) {
		if (sortIdx != null) {
			desc = new boolean[sortIdx.length];
			this.sortIdx = (int[]) sortIdx.clone();
			for (int i = 0; i < this.sortIdx.length; i++) {
				if (this.sortIdx[i] < 0) {
					this.sortIdx[i] = -this.sortIdx[i] - 1;
					desc[i] = true;
				}
			}
		} else {
			this.desc = null;
			this.sortIdx = null;
		}
	}

	@Override
	public int compare(Object[] oa1, Object[] oa2) {
		int n = oa2.length;
		if (n > oa1.length)
			n = oa1.length;
		if (sortIdx != null && n > sortIdx.length)
			n = sortIdx.length;
		// Integer
		try {
			for (int i = 0; i < n; i++) {
				int j = sortIdx != null ? sortIdx[i] : i;
				boolean desc = this.desc != null ? this.desc[i] : false;

				final int c = Data.compare(oa1[j], oa2[j]);
				if (c != 0) {
					return desc ? -c : c;
				}

			}
			return 0;
		} catch (Throwable ex) {
			if (Diagnostic.isLevelEnableOutput(Diagnostic.LVWARN)) {
				System.err.println("ObjectArrayComparator.compare:");// ERROR
				for (int j = 0; j < oa1.length || j < oa2.length; j++) {
					char cmp = sortIdx == null ? '*' : ' ';
					if (sortIdx != null)
						for (int k = 0; k < sortIdx.length; k++)
							if (sortIdx[k] == j) {
								cmp = '*';
								break;
							}
					Object x1 = j < oa1.length ? oa1[j] : null;
					Object x2 = j < oa2.length ? oa2[j] : null;
					System.err.println("  " + cmp + j + ":"
							+ Data.valueTypeToString(x1) + ","
							+ Data.valueTypeToString(x2)); // ERROR
				}
			}
			throw com.hibo.bas.exception.HookedException.toRuntimeException(ex);
		}
	}

	static final public ObjectArrayComparator cmp1 = new ObjectArrayComparator(
			new int[] { 0 });
	static final public ObjectArrayComparator cmp2 = new ObjectArrayComparator(
			new int[] { 0, 1 });
	static final public ObjectArrayComparator cmp3 = new ObjectArrayComparator(
			new int[] { 0, 1, 2 });

	static public ObjectArrayComparator newComparator(int cmpCount) {
		if (cmpCount == 1)
			return cmp1;
		if (cmpCount == 2)
			return cmp2;
		if (cmpCount == 3)
			return cmp3;
		return new ObjectArrayComparator(cmpCount);
	}

	static public ObjectArrayComparator newComparator(int sortIdx[]) {
		if (sortIdx.length == 1 && sortIdx[0] == 0)
			return cmp1;
		if (sortIdx.length == 2 && sortIdx[0] == 0 && sortIdx[1] == 1)
			return cmp2;
		if (sortIdx.length == 3 && sortIdx[0] == 0 && sortIdx[1] == 1
				&& sortIdx[1] == 2)
			return cmp3;
		return new ObjectArrayComparator(sortIdx);
	}
}
