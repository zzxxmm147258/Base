package com.hibo.bas.util;

import java.util.Arrays;
import java.util.Comparator;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午5:01:48</p>
 * <p>类全名：com.hibo.bas.util.ArrayUtil</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class ArrayUtil
{
    protected ArrayUtil(){   }
   public static Object addElement(Class elementClass,Object oldArray,Object value)
   {
       return addElement(elementClass,oldArray,value,true);
   }
   
   public static Object addElement(Object oldArray,Object value)
   {
       return addElement(null,oldArray,value,true);
   }
   
   public static<T> T[] addElement(T oldArray[],T value)
   {
       return (T[])addElement((Class<T>)null,oldArray,value,true);
   }
   
   public static<T> T[] add(Class<T> elementClass,T oldArray[],T value)
   {
       return (T[])addElement((Class<T>)elementClass,oldArray,value,true);
   }
   
   public static<T> T[] remove(Class<T> elementClass,T oldArray[],T value)
   {
       return (T[])removeElement((Class<T>)elementClass,oldArray,value,true);
   }
   
   public static<T> T[] add(Class<T> elementClass,T oldArray[],T value,boolean nonDupable)
   {
       return (T[])addElement((Class<T>)elementClass,oldArray,value,nonDupable);
   }
   
    public static Object addElement(Class elementClass,Object oldArray,Object value,boolean nonDupable)
    {
		int n = oldArray==null ? 0 : java.lang.reflect.Array.getLength(oldArray);
		if( nonDupable )for(int i=0;i<n;i++)
        {
            Object x = java.lang.reflect.Array.get(oldArray,i);
            if( x==value || (x!=null && x.equals(value)) )
                return oldArray;
        }
        if( elementClass==null && value!=null ) 
        	elementClass = value.getClass();
		Object newArray  = java.lang.reflect.Array.newInstance(elementClass,n+1);//new ?[n+1];
		if( n>0 )
			System.arraycopy(oldArray,0,newArray,0,n);
        java.lang.reflect.Array.set(newArray,n,value);
        return newArray;
    }
    
    public static Object insertElement(Class elementClass,Object oldArray,Object value,boolean nonDupable,int before)
    {
		int n = oldArray==null ? 0 : java.lang.reflect.Array.getLength(oldArray);
		if( nonDupable )for(int i=0;i<n;i++)
        {
            Object x = java.lang.reflect.Array.get(oldArray,i);
            if( x==value || (x!=null && x.equals(value)) )
                return oldArray;
        }
		Object newArray  = java.lang.reflect.Array.newInstance(elementClass,n+1);//new ?[n+1];
        if( before==-1 || before>=n )
        {
            if( n>0 )
                System.arraycopy(oldArray,0,newArray,0,n);
            java.lang.reflect.Array.set(newArray,n,value);
        } else
        {
            System.arraycopy(oldArray,0,newArray,0,before);
            java.lang.reflect.Array.set(newArray,before,value);
            System.arraycopy(oldArray,before,newArray,before+1,n-before);
        }
        return newArray;
    }
    
    static public Object[] insertElement(Object a[],int before,Object x)
    {
        if( a==null || a.length==0 )
            return new Object[]{x};
        Object newa[] = new Object[a.length+1];
        if( before<0 )
            before = 0;
        if( before>=a.length )
        {
            System.arraycopy(a,0,newa,0,a.length);
            newa[a.length] = x;
            return newa;
        }
        System.arraycopy(a,0,newa,0,before);
        newa[before] = x;
        System.arraycopy(a,before,newa,before+1,a.length-before);
        return newa;
    }
    
    /**
     *  将数组大小扩展到至少size
    */
    public static Object expandArray(Class elementClass,Object oldArray,int size,int dsize)
    {
        int n = oldArray==null ? 0 : java.lang.reflect.Array.getLength(oldArray);
        if( n>=size )
            return oldArray;
        Object newArray  = java.lang.reflect.Array.newInstance(elementClass,size+(dsize>0?dsize:0));
		if( n>0 )
			System.arraycopy(oldArray,0,newArray,0,n);
        return newArray;
    }
    
	public static<T> T[] add(T a1[],T a2[])
    {
        if(a2==null||a2.length==0) return a1;
        if(a1==null||a1.length==0) return a2;
        Class<?> elementClass = a1.getClass().getComponentType();
        T a[] = (T[])java.lang.reflect.Array.newInstance(elementClass,a1.length+a2.length);
        System.arraycopy(a1,0,a,0,a1.length);
        System.arraycopy(a2,0,a,a1.length,a2.length);
        return a;
    	
    }
	
    public static int[] expandArray(int oldArray[],int size,int dsize,int defaultValue)
    {
        final int n = oldArray==null ? 0 : oldArray.length;// java.lang.reflect.Array.getLength(oldArray);
        if( n>=size )
            return oldArray;
        int newArray[] =  new int[size+(dsize>0?dsize:0)];// java.lang.reflect.Array.newInstance(elementClass,size+(dsize>0?dsize:0));
		if( n>0 )
			System.arraycopy(oldArray,0,newArray,0,n);
		for(int i=n;i<newArray.length;i++)
			newArray[i] = defaultValue;
        return newArray;
    }
    
    public static Object copyArray(Class elementClass,Object oldArray,int indexFrom,int size)
    {
        int n = oldArray==null ? 0 : java.lang.reflect.Array.getLength(oldArray);
        Object newArray  = java.lang.reflect.Array.newInstance(elementClass,size);
        int ncopy = size;
        if( ncopy>n-indexFrom )
            ncopy = n-indexFrom;
        System.arraycopy(oldArray,indexFrom,newArray,0,ncopy);
        return newArray;
    }
    
    public static Object removeElement(Class elementClass,Object oldArray,Object value)
    {
    	return removeElement(elementClass,oldArray,value,false);
    }
    
    public static Object removeElement(Class elementClass,Object oldArray,Object value,boolean caseEquals)
    {
        final int n = oldArray==null ? 0 : java.lang.reflect.Array.getLength(oldArray);
        for(int i=0;i<n;i++)
        {
            if( caseEquals ? Data.objEquals(java.lang.reflect.Array.get(oldArray,i),value)
            		: java.lang.reflect.Array.get(oldArray,i)==value )
            {
            	if( elementClass==null )
            		elementClass = oldArray.getClass().getComponentType();
            	Object newArray  = java.lang.reflect.Array.newInstance(elementClass,n-1);
            	System.arraycopy(oldArray,0,newArray,0,i);
            	System.arraycopy(oldArray,i+1,newArray,i,n-1-i);
            	return newArray;
            }
        }
        return oldArray;
    }
    
    public static String[] removeElement(String[] oldArray,String value,boolean ignoreCase)
    {
        final int n = oldArray==null ? 0 : oldArray.length;
        for(int i=0;i<n;i++)
        {
        	String oldV = oldArray[i];
            if( oldV==value || (value!=null && (ignoreCase?value.equalsIgnoreCase(oldV):value.equals(oldV))) )
            {
            	String[] newArray  = new String[n-1];//java.lang.reflect.Array.newInstance(elementClass,n-1);
            	System.arraycopy(oldArray,0,newArray,0,i);
            	System.arraycopy(oldArray,i+1,newArray,i,n-1-i);
            	return newArray;
            }
        }
        return oldArray;
    }
    
    public static Object removeElement(Object oldArray,Object value)
    {
    	return removeElement(null,oldArray,value);
    }
    
    public static <T>T[] removeElement(T[] oldArray,T value)
    {
    	return (T[])removeElement(null,oldArray,value);
    }
    
    public static Object removeElement(Class elementClass,Object oldArray,int index)
    {
        if( oldArray==null )
            return null;
        int n =  java.lang.reflect.Array.getLength(oldArray);
        if( index>=0 && index<n )
        {
        	if( elementClass==null )
        		elementClass = oldArray.getClass().getComponentType();
            Object newArray  = java.lang.reflect.Array.newInstance(elementClass,n-1);
            System.arraycopy(oldArray,0,newArray,0,index);
            System.arraycopy(oldArray,index+1,newArray,index,n-1-index);
            return newArray;
        }
        return oldArray;
    }
    
    static public Object cloneArray(Class elementClass,Object oldArray)
    {
        if( oldArray==null )
            return null;
        int n = java.lang.reflect.Array.getLength(oldArray);
    	if( elementClass==null )
    		elementClass = oldArray.getClass().getComponentType();
        Object newArray  = java.lang.reflect.Array.newInstance(elementClass,n);
        System.arraycopy(oldArray,0,newArray,0,n);
        return newArray;
    }
    
    public static String[] addElement(String oldArray[],String s,boolean noneDup)
    {
        if( oldArray==null )
          return new String[]{s};
        if( noneDup && StrUtil.indexOf(oldArray,s)>=0 )
          return oldArray;
        int n = oldArray.length;
        String[] newArray  =  new String[n+1];
        if( n>0 )
                System.arraycopy(oldArray,0,newArray,0,n);
        newArray[n] = s;
        return newArray;
    }
    
    public static <T> T[] getElements(Object a[],Class<T> clazz)
    {
        if( a==null || clazz==null )
            return null;
        final java.util.ArrayList<T> v = new java.util.ArrayList<T>();
        for(int i=0;i<a.length;i++) {
            if( clazz.isAssignableFrom(a[i].getClass()) )
               v.add((T)a[i]);
        }
        T[] e = (T[])java.lang.reflect.Array.newInstance(clazz,v.size());
        v.toArray(e);
        return e;
    }
    
    public static <T> T[] toTypeArray(Object a[],Class<T> clazz)
    {
    	if( a==null)
    		return null;
    	T[] e = (T[])java.lang.reflect.Array.newInstance(clazz,a.length);
    	System.arraycopy(a,0,e,0,a.length);
    	return e;
    }
    
    public static int[] toIntArray(Object a[])
    {
    	if( a==null)
    		return null;
    	int inta[] = new int[a.length];
    	for(int i=0;i<inta.length;i++)
    		inta[i] = StrUtil.obj2int(a[i]);
    	return inta;
    }
    
    public static byte[] toByteArray(Object a[])
    {
    	if( a==null)
    		return null;
    	byte inta[] = new byte[a.length];
    	for(int i=0;i<inta.length;i++)
    		inta[i] = (byte)StrUtil.obj2int(a[i]);
    	return inta;
    }
    
	static public void format(java.io.Writer out,Object a[][],String format,String link) throws java.io.IOException
	{
		if( a==null ) return;
		for(int i=0;i<a.length;i++)
		{
			if( i>0 && link!=null )
				out.write(link);
			out.write(StrUtil.format(format,a[i]));
		}
	}
	
    static public String[] cloneArray(String a[])
    {
        if( a==null )
            return null;
        return a.clone();
    }
    
	static private final int intcmp(int x,int y)
	{
		return x<y?-1:(x==y?0:1);
	}
	
	/**
	 *  find index, key<=a[index], key>[index-1]
	 *  @param  ncmp  0:"<", 1:"<=", 2:"=", 3:">=", 4:">"
	 */
	public static int binarySearch(int ncmp,int[] a, int low,int high,int key)
	{
		while (low < high-1 )
		{
			int mid =(low + high)>>1;
			int kcmp = intcmp(key,a[mid]); //key < midVal ? -1 : ( key==midVal?0:1 );
			if( kcmp<0 || (kcmp==0 && (ncmp==2 || ncmp==3 || ncmp==0)) )
			{
				/*if(ncmp>=3) break; */ high = mid;
			}
			else
			{
				/*if(ncmp<=1) break;*/  low = mid;
			}
		}
	// low = hi-1 or low = hi
	if(low>high) return -1;
	//           <       <=      =       >=   >
	//  lo	w				    =0       >=0  >0
	//  hi       <0      <=0    =0
	//int lcmp = cmp(pKeyArray,m_pIndex+(m_nIndexFields+1)*lo);
	//int hcmp = hi==lo ? lcmp : cmp(pKeyArray,m_pIndex+(m_nIndexFields+1)*hi);
	if( ncmp>=2 )
	{
		// if(ncmp>2 && hi<m_nRecordCount-1) hi++;
		for(int i = low;i<=high;i++)
		{
			int k = intcmp(key,a[i]);//cmp(pKeyArray,m_pIndex+(m_nIndexFields+1)*i);
			if((k==0 && ncmp<=3) || (k<0 && ncmp>=3) )
				return i;
		}
	} else
	{
		//if(low) lo--;
		for(int i = high;i>=low;i-- )
		{
			int k = intcmp(key,a[i]);//cmp(pKeyArray,m_pIndex+(m_nIndexFields+1)*i);
			if((k==0 && ncmp>=1) || (k>0 && ncmp<=1))
				return i;
		}
	}
	return -1;
	}
	
    // binarySearch(a,0,a.length-1,key);
	public static int binarySearch(int[] a, int low,int high,int key)
	{
		//Arrays.binarySearch(a, fromIndex, high+1, key)
	while (low <= high) {
		int mid = (low + high)>>>1;
		int midVal = a[mid];

		if (midVal < key)
		low = mid + 1;
		else if (midVal > key)
		high = mid - 1;
		else
		return mid; // key found
	}
	return -(low + 1);  // key not found.
	}
	
//    public static int binarySearch(Vector v,int low,int high,
    public static int binarySearch(Object a[][],int low,int high,String key)
    {
        while (low <= high) {
            int mid = (low + high)>>>1;
            String midVal = (String)a[mid][0];
            int c = midVal.compareTo(key);
            if (c<0) //midVal < key)
                low = mid + 1;
            else if (c>0) //(midVal > key)
            high = mid - 1;
            else
            return mid; // key found
        }
        return -(low + 1);  // key not found.
    }
    
    public static int binarySearchFirst(Object a[][],String key)
    {
    	int p = binarySearch(a,key);
    	for(;p>0 && key.equals(a[p-1][0]);p--)
    		;
    	return p;
    }
    
    public static int binarySearch(Object a[][],String key)
    {
        return  a==null ? -1 : binarySearch(a,0,a.length-1,key,0);
    }
    
    public static int binarySearch(Object a[][],String key,int keyIndex)
    {
        return  a==null ? -1 : binarySearch(a,0,a.length-1,key,keyIndex);
    }
    
    
    static int binarySearch(Object a[][],int low,int high,Object key,int keyIndex)
    {
        while (low <= high) {
        	final int mid =(low + high)>>>1;
           // String midVal = (String)a[mid][0];
        	final int c = Data.compare(a[mid][keyIndex],key);
            if (c<0) //midVal < key)
                low = mid + 1;
            else if (c>0) //(midVal > key)
            	high = mid - 1;
            else
            	return mid; // key found
        }
        return -(low + 1);  // key not found.
    }
     
    public static int binarySearch(Object a[][],Object key)
    {
        return  a==null ? -1 : binarySearch(a,0,a.length-1,key,0);
    }
    
    public static<T1,T2> int binarySearch(T1 a[],int low,int high,T2 key,
    		Comparator2<? super T1,? super T2> cmp)
    {
        while (low <= high) {
        	final int mid =(low + high)>>>1;
          //  T1 midVal = a[mid];
        	final int c = cmp.compare2(a[mid], key);//midVal.compareTo(key);
            if (c<0) //midVal < key)
                low = mid + 1;
            else if (c>0) //(midVal > key)
            	high = mid - 1;
            else
            	return mid; // key found
        }
        return -(low + 1);  // key not found.
    }
    
    public static<T> int binarySearch(T a[],int low,int high,T key,Comparator<? super T> cmp)
    {
        while (low <= high) {
        	final int mid =(low + high)>>>1;
          //  T1 midVal = a[mid];
        	final int c = cmp.compare(a[mid], key);//midVal.compareTo(key);
            if (c<0) //midVal < key)
                low = mid + 1;
            else if (c>0) //(midVal > key)
            	high = mid - 1;
            else
            	return mid; // key found
        }
        return -(low + 1);  // key not found.
    }
    
  //此方法没有
  	public static <T1, T2> T1[] filterData(T1 a[], T2 keyValues, Comparator2<? super T1,? super T2> cmp)
  	{
  		if( a==null || a.length==0 || keyValues==null)
              return null;
          int j = ArrayUtil.binarySearch(a,keyValues,cmp);
          if( j<0 ) return null;
          int j1,j2;
          for(j1=j;j1>0 && cmp.compare2(a[j1-1],keyValues)==0;j1--)
              ;
          for(j2=j;j2<a.length-1 && cmp.compare2(a[j2+1],keyValues)==0;j2++)
              ;
          Class<?> elementClass = a.getClass().getComponentType();
  		  T1[] newA = (T1[])java.lang.reflect.Array.newInstance(elementClass,j2-j1+1);
          for(j=j1;j<=j2;j++) {
              newA[j-j1] = a[j];
          }
          return newA;
  	}
  	

	private static <T1,T2> int binarySearch(java.util.List<T1> a, int low, int high,
			T2 key, Comparator2<? super T1,? super T2> cmp) 
  {
		// Collections.
		while (low <= high) {
			final int mid = (low + high) >>>1;
			// T1 midVal = a[mid];
			final int c = cmp.compare2(a.get(mid), key);// midVal.compareTo(key);
			if (c < 0) // midVal < key)
				low = mid + 1;
			else if (c > 0) // (midVal > key)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}
	
    public static <T1,T2> int binarySearch(java.util.List<T1> a, T2 key, Comparator2<? super T1,? super T2> cmp) 
   {
  	  return binarySearch(a,0,a.size()-1,key,cmp);
    }
    
    public static <T1,T2> int binarySearchFirst(T1 a[], T2 key, Comparator2<? super T1,? super T2> cmp) 
    {
    	int p = binarySearch(a,0,a.length-1,key,cmp);
    	for(;p>0 && cmp.compare2(a[p-1],key)==0;p--)
    		;
    	return p;
    }
    
    public static<T1,T2> int binarySearch(T1 a[],T2 key,Comparator2<? super T1,? super T2> cmp)
    {
    	return a==null?-1:binarySearch(a,0,a.length-1,key,cmp);
    }
    
    public static<T1,T2> T1 binarySearchV(T1 a[],T2 key,Comparator2<? super T1,? super T2> cmp)
    {
    	int p = a==null?-1:binarySearch(a,0,a.length-1,key,cmp);
    	return p<0 ? null : a[p];
    }
    
    /*
     * a : 必须 是排序的
     * keyValues : 必须对应数值的 前面 的列
     */
    static public Object[][] filterData(Object a[][],Object[] keyValues)//,Object[][] keyValues)
    {
        if( a==null || a.length==0 || keyValues==null || keyValues.length==0 )
            return keyValues==null ? a : Const.ZObjectArray2;// new Object[0][];
        ObjectArrayComparator c = ObjectArrayComparator.newComparator(keyValues.length);
        int j = java.util.Arrays.binarySearch(a,keyValues,c);
        if( j<0 ) return Const.ZObjectArray2;// return new Object[0][];
        int j1,j2;
        for(j1=j;j1>0 && c.compare(keyValues,a[j1-1])==0;j1--)
            ;
        for(j2=j;j2<a.length-1 && c.compare(keyValues,a[j2+1])==0;j2++)
            ;
        Object newA[][] = new Object[j2-j1+1][];
        for(j=j1;j<=j2;j++)
            newA[j-j1] = a[j];
        return newA;
    }
    
    static public Object[][] filterData(Object a[][],Object[][] akeyValues)//,Object[][] keyValues)
    {
        if( a==null || a.length==0 || akeyValues==null)
            return  a ;
       SortedIntArray v = new SortedIntArray();
        for(int k=0;k<akeyValues.length;k++)
        {
            Object keyValues[] = akeyValues[k];
            int sortIdx[] = new int[keyValues.length];
            for (int i = 0; i < sortIdx.length; i++)
                sortIdx[i] = i;
            ObjectArrayComparator c = new ObjectArrayComparator(sortIdx);
            int j = java.util.Arrays.binarySearch(a, akeyValues[k], c);
            if (j < 0) continue;
            int j1, j2;
            for (j1 = j; j1 > 0 && c.compare(keyValues, a[j1 - 1]) == 0; j1--)
                ;
            for (j2 = j; j2 < a.length - 1 && c.compare(keyValues, a[j2 + 1]) == 0; j2++)
                ;
            for (j = j1; j <= j2; j++)
            {
            	v.add(j);
            }
        }
        Object va[][] = new Object[v.size][];
        for(int i=0;i<va.length;i++)
            va[i] = a[v.at(i)];
        return va;
    }
    
    static public Object[][] filterDataCaseMidLevel(Object a[][],Object[] keyValues,boolean middleLevl)//,Object[][] keyValues)
    {
    	Object key[] = new Object[1];
    	SortedIntArray v = new SortedIntArray(); 
    	for(int i=0;i<keyValues.length;i++)
    	{
    		key[0] = keyValues[i];
    		int p = Arrays.binarySearch(a,key,ObjectArrayComparator.cmp1);
    		if( p>=0 )
    			v.add(p);
    		if( !middleLevl )
    			continue;
    		String code = (String)keyValues[i];
    		for(int l=code.length()-1;l>0;l--)
    		{
    			key[0] = code.substring(0,l);
    			p = Arrays.binarySearch(a,key,ObjectArrayComparator.cmp1);
        		if( p>=0 )
        			v.add(p);
    		}
    	}
    	Object va[][] = new Object[v.size()][];
    	for(int i=0;i<v.size();i++)
    		va[i] = a[v.at(i)];
    	return va;
    }
    
    static public Object copyArray(Object oldArray,int index[])
    {
    	if( oldArray==null || index==null )
    		return null;
    	Class elementClass = oldArray.getClass().getComponentType();
    	Object a = java.lang.reflect.Array.newInstance(elementClass,index.length);//new ?[n+1];
    	for(int i=0;i<index.length;i++)
    	{
    		java.lang.reflect.Array.set(a,i,java.lang.reflect.Array.get(oldArray, index[i]));
    	}
    	return a;
    }
    
    static public int[] addElement(int[] old,int value,boolean nonDupable)
    {
        int n = old==null ? 0 : old.length;
        if( nonDupable )for(int i=0;i<n;i++)
            if( old[i]==value )
                return old;
        int a[] = new int[n+1];
        if( n>0 )
            System.arraycopy(old,0,a,0,n);
        a[n] = value;
        return a;
    }
    
    static public int indexOf(Object a[],Object o)
    {
        if(a!=null) for(int i=0;i<a.length;i++)
        {
            if( o==a[i] ||( o!=null && o.equals(a[i])) )
                return i;
        }
        return -1;
    }
    
    static public int indexOf(int a[],int o)
    {
       // if( a==null ) return -1;
        if(a!=null) for(int i=0;i<a.length;i++)
        {
            if( o==a[i] ) return i;
            //if( o!=null && o.equals(a[i]) )
             //   return i;
        }
        return -1;
    }
    
    public static <T> T[] toType(Object [] a,Class<T> elementClass)
    {
    	//java.lang.reflect.Array.newInstance(elementClass,
    	if( a==null )
    		return null;
    	if( elementClass.isAssignableFrom(a.getClass().getComponentType()) )
    	{
    		return (T[])a;
    	}
    	T[] newA = (T[])java.lang.reflect.Array.newInstance(elementClass,a.length);
    	for(int i=0;i<newA.length;i++)
    	{
    		newA[i] = (T)a[i];
    	}
    	return newA;
    }
    
    static public<T> T[] toArray(java.util.List<T> v,T []to,int jFrom)
    {
        for(int i=0;i<v.size() && i+jFrom<to.length;i++)
            to[i+jFrom] = v.get(i);// v.elementAt(i);
        return to;
    }
    
    public static <T> T[] toArray(java.util.Collection<T> list,Class<T> elementClass)
    {
    	return list==null?null:list.toArray((T[])java.lang.reflect.Array.newInstance(elementClass,list.size()));
    }
    
    public static <T> void addTo(java.util.Collection<T> list,T a[],int start,int end)
    {
    	for(int i=start;i<end;i++)
    		list.add(a[i]);
    }
    
    public static byte[] subBytes(byte a[],int from)
    {
        if( from==0 ) return a;
        byte newa[] = new byte[a.length-from];
        System.arraycopy(a,from,newa,0,newa.length);
        return newa;
    }
    
    public static Object[] subArray(Object a[],int from)
    {
        if( from==0 ) return a;
        Object newa[] = new Object[a.length-from];
        System.arraycopy(a,from,newa,0,newa.length);
        return newa;
    }
    
    /*
     *  to : 不含
     */
    public static Object[] subArray(Object a[],int from,int to)
    {
        if( from==0 && to==a.length ) return a;
        Object newa[] = new Object[to-from];
        System.arraycopy(a,from,newa,0,newa.length);
        return newa;
    }
    
    public static int[] subArray(int a[],int from)
    {
        if( from==0 ) return a;
        int newa[] = new int[a.length-from];
        System.arraycopy(a,from,newa,0,newa.length);
        return newa;
    }
    
    public static byte[] subBytes(byte a[],int from,int length)
    {
        if( length>a.length-from )
            length = a.length - from;
        byte newa[] = new byte[length];
        System.arraycopy(a,from,newa,0,newa.length);
        return newa;
    }
    
    public static Object[][] add(Object a1[][],Object a2[][])
    {
        if(a2==null||a2.length==0) return a1;
        if(a1==null||a1.length==0) return a2;
        Object a[][] = new Object[a1.length+a2.length][];
        System.arraycopy(a1,0,a,0,a1.length);
        System.arraycopy(a2,0,a,a1.length,a2.length);
        return a;
    }
    
    public final static int[] add(int a1[],int a2[])
    {
        if(a2==null||a2.length==0) return a1;
        if(a1==null||a1.length==0) return a2;
        int a[] = new int[a1.length+a2.length];
        System.arraycopy(a1,0,a,0,a1.length);
        System.arraycopy(a2,0,a,a1.length,a2.length);
        return a;
    }
    
    public static Object[] getArray2E(Object a[][],int index)
    {
    	if( a==null )
    		return null;
    	Object v[] = new Object[a.length];
    	for(int i=0;i<v.length;i++)
    		v[i] = a[i][index];
    	return v;
    }
    
    public static void append(java.lang.Appendable sb,Object values[],char delim)
    {
    	try {
    	if( values==null )
    		return ;
    	for(int i=0;i<values.length;i++)
    	{
    		if( i>0 && delim>0 )
    			sb.append(delim);
    		sb.append(values[i]==null?"null":values[i].toString()); //String.valueOf(obj
    	}
    	} catch( java.io.IOException ex)
    	{
    		throw com.hibo.bas.exception.HookedException.toRuntimeException(ex);
    	}
    	//return ;
    }
    
    /*
     *  fromIndex : 数值起始位置
     *  toIndex-1 :  结束位置
     */
    static public int sortE(Object a[],int fromIndex,int toIndex, int index,
    		java.util.Comparator cmp)
    {
    	final Object v = a[index];
    	if( index>fromIndex && cmp.compare(a[index-1],v)>0 )
    	{
    		int p = Arrays.binarySearch(a, fromIndex, index,v );
    		if( p<0 )
    			p = -(p+1);
    		System.arraycopy(a, p, a, p+1, index - p);
    		a[p] = v;
    		return p;
    	}
    	if( index<toIndex-1 && cmp.compare(v,a[index+1])>0 )
    	{
    		int p = Arrays.binarySearch(a, index+1, toIndex,v );
    		if( p<0 )
    			p = -(p+1);
    		//if( p>toIndex-1 )
    		//	p = toIndex-1;
    		System.arraycopy(a, index+1, a, index, p - index -1);
    		a[p-1] = v;
    		return p-1;
    	}
    	return index;
    }
    
    static public int compare(java.lang.Comparable a1[],java.lang.Comparable a2[])
    {
    	if( a1==a2 )
    		return 0;
    	if( a1==null )
    		return -1;
    	if( a2==null )
    		return 1;
    	final int n1 = a1.length;
		final int n2 = a2.length;
		final int n = n1<n2 ? n1 : n2;
		for(int i=0;i<n;i++)
		{
			int c = a1[i].compareTo(a2[i]);
			if( c!=0 )
				return c;
		}
		return n1 - n2;
    }
    
    static public int sortE(Object a[], int index,java.util.Comparator cmp)
    {
    	return sortE(a,0,a.length,index,cmp);
    }
    
    static public <T extends java.lang.Comparable<T>> T[] cloneSorted(T a[])
    {
    	if( a==null )
    		return a;
    	for(int i=1;i<a.length;i++)
    	{
    		if( a[i].compareTo(a[i-1])<0 )
    		{
    			T newA[] = a.clone();
    			Arrays.sort(newA);
    			return newA;
    		}
    	}
    	return a;
    }
    
    static public int[] cloneSorted(int a[])
    {
    	if( a==null )
    		return a;
    	for(int i=1;i<a.length;i++)
    	{
    		if( a[i]<a[i-1] )
    		{
    			int newA[] = a.clone();
    			Arrays.sort(newA);
    			return newA;
    		}
    	}
    	return a;
    }
    
	public static boolean moveRows(Object data[],int rowCount,int fromRow,int toRow,int nRowsToMove)
	{
        if( toRow>rowCount-nRowsToMove )
        	toRow = rowCount-nRowsToMove ;
        if( toRow>=fromRow && toRow<fromRow+nRowsToMove ) 
        {
        	System.err.println("Error");
        	return false;
        }
        Object a[] = new Object[nRowsToMove];
        System.arraycopy(data,fromRow, a, 0, nRowsToMove);
        if( toRow>fromRow )
        {
            System.arraycopy(data,fromRow+nRowsToMove,data,fromRow,toRow-fromRow);
        } else // row>toRow
        {
            System.arraycopy(data,toRow,data,toRow+nRowsToMove,fromRow-toRow);
        }
        System.arraycopy(a, 0, data,toRow, nRowsToMove);
        return true;
	}
	
	static public int[] toIntArray(Object v)
	{
		if( v==null )
			return null;
		if( v instanceof int[] )
			return (int[])v;
		if( v instanceof Object[] )
			return StrUtil.obja2inta((Object[])v, 0);
		throw new java.lang.UnsupportedOperationException("Can not cast "+v.getClass().getName()+" to int[]");
	}
	
	public static Object[][] mergSorted(Object[][] a1,java.util.List<Object[]> a2,
			java.util.Comparator<Object[]> cmp)
	{
		final int n1 = a1==null ? 0 : a1.length;
		final int n2 = a2==null ? 0 : a2.size();
		final int n = n1+n2;
		final Object[][] to = new Object[n][];
		int p0 = 0;
		int p1 = 0;//, p2 = 0;
		for(int p2=0;p2<n2;p2++)
		{
			Object[] v = a2.get(p2);
			int p = ArrayUtil.binarySearch(a1, p1, n1-1, v,cmp);
			if( p>=0 )
				throw new RuntimeException();
			p = -(p+1);
			if( p>p1 ) {
				System.arraycopy(a1, p1, to, p0, p-p1);
				p0 += p-p1;
				p1 = p;
			}
			to[p0++] = v; 
		}
		assert( n-p0==n1-p1 );
		if( p0<n )
			System.arraycopy(a1, p1, to, p0, n1-p1);
		return to;
	}

	public static<T> void assertSorted(T[] a,java.util.Comparator<T> cmp)
	{
		for(int i=1;i<a.length;i++)
		{
			if( cmp.compare(a[i-1],a[i])>0 )
				throw new java.lang.AssertionError("i="+i);
		}
	}
	
	public static void assertSorted(java.lang.Comparable[] a,boolean uniq)
	{
		for(int i=1;i<a.length;i++)
		{
			final int c = a[i-1].compareTo(a[i]);
			if( uniq ? c>=0 : c>0 )
				throw new java.lang.AssertionError("i="+i);
		}
	}
}
