package com.hibo.bas.util;

import java.util.Iterator;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午4:56:33</p>
 * <p>类全名：com.hibo.bas.util.SortedObjectArray</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class SortedObjectArray<E> implements java.lang.Iterable<E> {
	final public java.util.Comparator cmp;
	final boolean dupable;

	public SortedObjectArray(java.util.Comparator<E> cmp) {
		this(cmp, 8, false);
	}

	public SortedObjectArray() {
		this(null, 8, false);
	}

	public SortedObjectArray(java.util.Comparator<E> cmp, boolean dupable) {
		this(cmp, 8, dupable);
	}

	public SortedObjectArray(java.util.Comparator<E> cmp, int initSize,
			boolean dupable) {
		this.cmp = cmp;
		this.dupable = dupable;
		data = new Object[initSize];
	}

	public SortedObjectArray(java.util.Comparator cmp, Object data[]) {
		this.cmp = cmp;
		dupable = false;
		this.data = data;
		this.size = data.length;
	}

	protected Object data[];// = new Object[8];
	protected int size;

	public int add(E x) {
		int j = binarySearch(data, 0, size - 1, x, cmp);
		if (j >= 0 && !dupable) {
			return j;
		}
		if (size == data.length) {
			Object old[] = data;
			data = new Object[data.length + 8];
			System.arraycopy(old, 0, data, 0, size);
		}
		if (j < 0)
			j = -(j + 1);
		else if (dupable) {
			// 重复时确保 加在最后！CreateMgdataProgress3 有次需要
			for (; j < size && this.compare(j, x) == 0; j++)
				;
		}
		if (j < size) {
			System.arraycopy(data, j, data, j + 1, size - j);
		}
		data[j] = x;
		size++;
		return j;
	}

	public void addAll(E[] a) {
		if (a != null)
			for (E e : a)
				this.add(e);
	}

	public void addAll(java.lang.Iterable<E> a) {
		if (a != null)
			for (E e : a)
				this.add(e);
	}

	public int insert(E x, int p) {
		if (!dupable)
			return add(x);
		if ((p > 0 && this.compare(p - 1, x) > 0)
				|| (p < size - 1 && this.compare(p + 1, x) < 0)) {
			throw new IllegalArgumentException();
		}
		if (size == data.length) {
			Object old[] = data;
			data = new Object[data.length + 8];
			System.arraycopy(old, 0, data, 0, size);
		}
		{
			// 重复时确保 加在最后！CreateMgdataProgress3 有次需要
			// for(;j<size && this.compare(j, x)==0;j++ )
			// ;
		}
		if (p < 0 || p > size)
			throw new IllegalArgumentException();
		if (p < size) {
			System.arraycopy(data, p, data, p + 1, size - p);
		}
		data[p] = x;
		size++;
		return p;
	}

	public E addObject(E x) {
		final int j = add(x);
		return (E) data[j];
	}

	public void clear() {
		size = 0;
		if (data.length > 8)
			data = new Object[8];
		else
			for (int i = 0; i < data.length; i++) {
				data[i] = null;
			}
	}

	public E at(int index) {
		if (index < 0 || index >= size)
			throw new java.lang.ArrayIndexOutOfBoundsException(index);
		return (E) data[index];
	}

	public E get(int index) {
		if (index < 0 || index >= size)
			throw new java.lang.ArrayIndexOutOfBoundsException(index);
		return (E) data[index];
	}

	public int indexOf(Object x) {
		return binarySearch(data, 0, size - 1, x, cmp);
	}

	public <K> int indexOf(K key, Comparator2<E, K> cmp2) {
		return ArrayUtil.binarySearch((E[]) this.data, 0, size - 1, key, cmp2);
		// return 0;
	}

	public <K> E get(K key, Comparator2<E, K> cmp2) {
		final int i = ArrayUtil.binarySearch((E[]) this.data, 0, size - 1, key,
				cmp2);
		return i >= 0 ? (E) data[i] : null;
	}

	// public int indexOfObject(Object x)
	// {
	// return binarySearch(data,0,size-1,x,cmp);
	// }
	public E getObject(Object x) {
		int i = binarySearch(data, 0, size - 1, x, cmp);
		return i >= 0 ? (E) data[i] : null;
	}

	public int remove(E x) {
		final int j = binarySearch(data, 0, size - 1, x, cmp);
		if (j < 0)
			return j;
		System.arraycopy(data, j + 1, data, j, size - 1 - j);
		size--;
		return j;
	}

	public int remove(int i) {
		if (i >= 0 && i < size) {
			System.arraycopy(data, i + 1, data, i, size - 1 - i);
			size--;
			return i;
		}
		return -1;
	}

	public int getSize() {
		return size;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Object[] toArray() {
		Object a[] = new Object[size];
		System.arraycopy(data, 0, a, 0, size);
		return a;
	}

	// public Object[] toArray(Object a[])
	public synchronized <T> T[] toArray(T[] a) {
		System.arraycopy(data, 0, a, 0, size);
		return a;
	}

	public synchronized <T> T[] toArray(Class<T> type) {
		T[] a = (T[]) java.lang.reflect.Array.newInstance(type, size);
		System.arraycopy(data, 0, a, 0, size);
		return a;
	}

	public int firstIndexOf(E x) {
		return firstIndexOf(x, this.cmp);
	}

	public int firstIndexOf(E x, java.util.Comparator cmp) {
		int i = binarySearch(data, 0, size - 1, x, cmp);
		if (i < 0)
			return i;
		for (; i > 0; i--) {
			int c = cmp == null ? ((java.lang.Comparable) data[i - 1])
					.compareTo(x) : cmp.compare(data[i - 1], x);
			if (c != 0)
				break;
		}
		return i;
	}

	public int compare(int i, E x) {
		return cmp == null ? ((java.lang.Comparable) data[i]).compareTo(x)
				: cmp.compare(data[i], x);
	}

	public int compare(int i, E x, java.util.Comparator cmp) {
		return cmp == null ? ((java.lang.Comparable) data[i]).compareTo(x)
				: cmp.compare(data[i], x);
	}

	public static int binarySearch(Object[] data, int low, int high,
			Object key, java.util.Comparator c) {
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int cmp = c == null ? ((java.lang.Comparable) data[mid])
					.compareTo(key) : c.compare(data[mid], key);
			// System.err.println(data[mid]+","+key+":"+cmp);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++)
			sb.append(i == 0 ? '[' : ',').append(data[i]);
		sb.append(']');
		return sb.toString();
	}

	public String join(String join) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i > 0)
				sb.append(join);
			sb.append(data[i]);
		}
		return sb.toString();
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<E> {
		int cursor = 0;

		@Override
		public boolean hasNext() {
			return cursor < size();
		}

		@Override
		public E next() {
			return at(cursor++);
			// E next = at(cursor);
			// lastRet = cursor++;
			// return next;
			// return null;
		}

		@Override
		public void remove() {

		}
	}

}
