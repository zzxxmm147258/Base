package com.hibo.bas.exception;
/**
 * <p>标题： 抛异常，但不输出堆栈</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午3:39:29</p>
 * <p>类全名：com.hibo.bas.exception.DummyException</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class DummyException extends BaseException implements UnReportableException
{
	private static final long serialVersionUID = -5846666706781169270L;
	public DummyException()
    {
    	errLevel = 6;
    }
    public DummyException(String s)
    {
    	super(s);
    	errLevel = 6;
    }
    public DummyException(Throwable ex)
    {
    	super(ex);
    	errLevel = 6;
    }
    public static DummyException toDummyException(Throwable ex)
    {
        if( ex instanceof java.lang.reflect.InvocationTargetException
           && ((java.lang.reflect.InvocationTargetException)ex).getTargetException()!=null )
           ex =  ((java.lang.reflect.InvocationTargetException)ex).getTargetException();
        if( ex instanceof DummyException  )
            return (DummyException)ex;
        else
            return new DummyException(ex);
    }
}
