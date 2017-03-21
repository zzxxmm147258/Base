package com.hibo.bas.exception;

import java.util.Map;

/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午3:28:52</p>
 * <p>类全名：com.hibo.bas.exception.HookedException</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class  HookedException extends BaseException
{
	private static final long serialVersionUID = -9037201172084513059L;
	public HookedException()
	{
		super();
	}
	
	public HookedException(String s)
	{
		super(s);
	}
	
	public HookedException(java.lang.Throwable ex)
	{
        this(null,ex);
	}
	
	public HookedException(java.lang.Throwable ex,String extMessage)
	{
		this(extMessage,ex);
	}
	
	public HookedException(String extMessage,java.lang.Throwable ex)
	{
		super(ex==null?extMessage:ex.getMessage(),ex);
        this.extMessage = extMessage;
		
	}
	
	public HookedException(String errorCode,String extMessage,java.lang.Throwable ex)
	{
		super(errorCode,ex==null?extMessage:ex.getMessage(),ex);
        this.extMessage = extMessage;
		
	}
	
    private String extMessage;
    public void setExtMessage(String extMessage)
    {
        this.extMessage = extMessage;
    }
    
    public String getExtMessage()
    {
        return extMessage;
    }
    
    public void addExtMessage(String extMessage)
    {
        if( this.extMessage==null )
            this.extMessage = extMessage;
        else
            this.extMessage += " "+extMessage;
    }
    
    public boolean extMsgOnly;
    @Override
    public String getMessage(final int flags)
    {
        if( extMsgOnly && extMessage!=null )
        {
            return this.errorCode!=null && (flags&1)==0 ? "["+this.errorCode+"]"+extMessage : extMessage;
        }
        String msg = super.getMessage0();
        if( msg==null || msg.equals(extMessage) )
            return errorCode==null || (flags&1)!=0 ? extMessage : "["+errorCode+"]"+extMessage;
        if( extMessage!=null )
        {
            msg += " " + extMessage;
        }
        return errorCode==null || (flags&1)!=0 ? msg : "["+errorCode+"]"+msg;//msg;
    }
    @Override
    public String toString()
    {
        String s = getClass().getName();
        Throwable ex = getCause();
        if( ex!=null )
            s += "("+ex.getClass().getName()+")";
        String message = getMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
    @Override 
    public void printStackTrace(java.io.PrintStream s)
    {
        synchronized (s) {
        	Throwable ex = getCause();
        	if( ex!=null )
        	{
        		
        		if(this.extMessage!=null) 
        			s.println(extMessage);
        		ex.printStackTrace(s);
        	} else
        		super.printStackTrace(s);
        }
    }
    @Override
    public void printStackTrace(java.io.PrintWriter s)
    {
        synchronized (s) {
        	Throwable ex = getCause();
        if( ex!=null )
        {
            if(this.extMessage!=null) 
            	s.println(extMessage);
            ex.printStackTrace(s);
        } else
            super.printStackTrace(s);
        }
    }
    
    public static RuntimeException toRuntimeException(Throwable ex)
    {
        return toRuntimeException(ex,null);
    }
    
    public static RuntimeException toRuntimeException(Throwable ex,String moreMessage)
    {
        if( moreMessage==null ) 
        	ex = ExceptionUtil.getThrowable(ex);
        if( ex instanceof java.lang.reflect.InvocationTargetException
           && ((java.lang.reflect.InvocationTargetException)ex).getTargetException()!=null )
           ex =  ((java.lang.reflect.InvocationTargetException)ex).getTargetException();
        if( ex instanceof RuntimeException && moreMessage==null )
            return (RuntimeException)ex;
        else
            return new HookedException(ex,moreMessage);
    }
    
    public static HookedException newExceptionByErrCode(Map<Object,Object> envP,String errorCode,Object...args)
    {
    	return new HookedException(errorCode,getErrTextByCode(envP,errorCode,args),null);
    }
    
    public static HookedException newExceptionByErrCode(Map<Object,Object> envP,String errorCode,Throwable ex,Object...args)
    {
    	return new HookedException(errorCode,getErrTextByCode(envP,errorCode,args),ex);
    }
    
    public static HookedException newNetException(Map<Object,Object> envP,java.io.IOException ex,java.net.URL url)
    {
    	return newNetException(envP,ex,url==null?null:url.toString());
    }
    
    public static HookedException newNetException(Map<Object,Object> envP,java.io.IOException ex,String url)
    {
    	String errorCode = "000030";
    	if( ex instanceof java.net.ConnectException )
    		errorCode = "000031";
    	return newExceptionByErrCode(envP,errorCode,ex,url);
    }
}