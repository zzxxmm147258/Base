package com.hibo.bas.util;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午5:24:19</p>
 * <p>类全名：com.hibo.bas.util.SortedIntArray</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class SortedIntArray implements java.lang.Cloneable {
	final protected int capacityIncrement;

	public SortedIntArray() {
		data = new int[capacityIncrement = 8];
	}

	public SortedIntArray(int capacityIncrement) {
		if (capacityIncrement <= 0)
			throw new java.lang.IllegalArgumentException();
		data = new int[this.capacityIncrement = capacityIncrement];
	}

	int data[];// = new int[8];
	int size;

	public int add(int x) {
		int j = binarySearch(data, 0, size - 1, x);
		if (j >= 0) {
			return j;
		}
		if (size == data.length) {
			int old[] = data;
			data = new int[data.length + 8];
			System.arraycopy(old, 0, data, 0, size);
		}
		j = -(j + 1);
		if (j < size) {
			System.arraycopy(data, j, data, j + 1, size - j);
		}
		data[j] = x;
		size++;
		return j;
	}

	public void add(int a[]) {
		if (a != null)
			for (int x : a)
				this.add(x);
	}

	public void clear() {
		size = 0;
		if (data.length > 8)
			data = new int[8];
	}

	public int at(int index) {
		if (index < 0 || index >= size)
			throw new java.lang.ArrayIndexOutOfBoundsException(index);
		return data[index];
	}

	public int get(int index) {
		if (index < 0 || index >= size)
			throw new java.lang.ArrayIndexOutOfBoundsException(index);
		return data[index];
	}

	public int indexOf(int x) {
		return binarySearch(data, 0, size - 1, x);
	}

	public int[] _getData() {
		return data;
	}

	public int remove(int x) {
		int j = binarySearch(data, 0, size - 1, x);
		if (j < 0)
			return j;
		System.arraycopy(data, j + 1, data, j, size - 1 - j);
		size--;
		return j;
	}

	public void removeByIndex(int index) {
		if (index >= 0 && index < size) {
			System.arraycopy(data, index + 1, data, index, size - 1 - index);
			size--;
		}
	}

	public int getSize() {
		return size;
	}

	public int size() {
		return size;
	}

	public int[] toArray() {
		int a[] = new int[size];
		System.arraycopy(data, 0, a, 0, size);
		return a;
	}

	public static int binarySearch(int[] data, int low, int high, int key) {
		while (low <= high) {
			int mid = (low + high) / 2;
			int midVal = data[mid];

			if (midVal < key)
				low = mid + 1;
			else if (midVal > key)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; i < size; i++) {
			(i > 0 ? sb.append(',') : sb).append(data[i]);
		}
		sb.append(']');
		return sb.toString();
	}

	public String cat(char cat) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i > 0)
				sb.append(cat);
			sb.append(data[i]);
		}
		return sb.toString();
	}

	public boolean equals(int a[], int size) {
		if (size != this.size)
			return false;
		for (int i = 0; i < size; i++) {
			if (a[i] != this.data[i])
				return false;
		}
		return true;
	}

	@Override
	public SortedIntArray clone() {
		try {
			SortedIntArray v = (SortedIntArray) super.clone();
			v.data = this.data.clone();
			return v;
		} catch (CloneNotSupportedException e) {
			throw com.hibo.bas.exception.HookedException.toRuntimeException(e);
		}

	}
}
