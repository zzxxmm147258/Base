package com.hibo.bas.exception;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午3:33:49</p>
 * <p>类全名：com.hibo.bas.exception.ExceptionUtil</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class ExceptionUtil
{
    public static void printStackTrace(Throwable ex,java.io.PrintWriter out,String msgDesc)
    {
        printStackTrace(ex,out,msgDesc,true) ;
    }
    
    public static void printStackTrace(Throwable ex,java.io.PrintWriter out,String msgDesc,boolean trace)
    {
        if( ex instanceof UnReportableException )  return;
        if( ex instanceof java.lang.reflect.InvocationTargetException && ((java.lang.reflect.InvocationTargetException)ex).getTargetException()!=null )
        {
            ex = ((java.lang.reflect.InvocationTargetException)ex).getTargetException();//.printStackTrace(out);
        }
        if( msgDesc!=null && ex.getMessage()!=null )
        {
            out.println(msgDesc+":"+ex.getMessage());
        }
        if(trace) ex.printStackTrace(out);
    }
    
    static public Throwable getThrowable(Throwable ex)
    {
        return getThrowable(ex,false);
    }
    
    static public Throwable getThrowable(Throwable ex,boolean forceGetSourceThrowable)
    {
        for(;ex!=null;)
        {
          if( ex instanceof java.lang.reflect.InvocationTargetException 
        		  && ((java.lang.reflect.InvocationTargetException)ex).getTargetException()!=null
          )
          {
              ex = ((java.lang.reflect.InvocationTargetException)ex).getTargetException();//.printStackTrace(out);
              continue;
          }
          if( ex instanceof HookedException  )
           {
        	  if( ((HookedException)ex).getCause()!=null &&
        			  (forceGetSourceThrowable || ((HookedException)ex).getExtMessage()==null)
        		)
        	  {
        		  ex = ex.getCause();
               	  continue;
        	  }
        	  break;
           }
          if( ex.getCause()!=null )
          {
        	  ex = ex.getCause();
        	  continue;
          }
           break;
        }
        return ex;
    }
    
    public static void printStackTrace(Throwable ex,java.io.PrintStream  out,String msgDesc)
    {
        printStackTrace(ex,out,msgDesc,true);
    }
    
    public static void printStackTrace(Throwable ex,java.io.PrintStream  out,String msgDesc,boolean trace)
    {
        if( ex instanceof UnReportableException ) 
        	return;
        String msg = ex.getMessage();
        if( msg==null ) msg = ""+ex;
        {
            out.println((msgDesc==null?"":msgDesc+":")+msg);
        }
        if( trace ) 
        	getThrowable(ex).printStackTrace(out);
    }
    
    static public String  getMessage(Throwable ex)
    {
    	if( !(ex instanceof HookedException) || ( ((HookedException)ex).getExtMessage()==null && ((HookedException)ex).errorCode==null ) ) 
    		ex = getThrowable(ex);
        String message = ex.getMessage();
        if( message==null || message.length()==0 )
            message = ex.getClass().getName();
        return message;
    }
    
    static public String  traceToString(Throwable ex)
    {
    	return traceToString(null,ex);
    }
    
    static public String  traceToString(Object message,Throwable ex)
    {
        java.io.StringWriter w = new java.io.StringWriter();
        java.io.PrintWriter out = new java.io.PrintWriter(w);
        if( message!=null )
        	out.println(message);
        if( ex!=null )
        	ex.printStackTrace(out);
        return w.toString();
    }
    
    static public void appendTrace(java.lang.Appendable out,Throwable ex)
    {
    	if( out instanceof java.io.PrintStream )
    		ex.printStackTrace((java.io.PrintStream)out);
    	else if( out instanceof java.io.PrintWriter )
    		ex.printStackTrace((java.io.PrintWriter)out);
    	else try {
    		out.append(traceToString(null,ex));
    	} catch( java.io.IOException x ) {

    	}
    }
    
    public static void printStackTraceToFile(Throwable ex,String fileName) throws java.io.IOException
    {
        java.io.PrintStream out = new java.io.PrintStream(new java.io.FileOutputStream(fileName));
        try {
            ex.printStackTrace(out);
        } finally
        {
            out.close();
        }
    }

	static public java.lang.RuntimeException getException(String sysid,String code,Object... args)
	{
		return getException(sysid,code,(Throwable)null,args);
	}    
	
	static public void printStackTraceCaseLevl(Throwable ex)
	{
		final int errlevl = ex instanceof BaseException ? ((BaseException)ex).getErrLevel() : 3;
		final int tracLevl = com.hibo.bas.util.Diagnostic.getTraceLevel();
		if( tracLevl<7 && (ex instanceof UnReportableException || errlevl>=6) )
		{
			// 非调试 级别 下  不打印  UnReportableException, InfoException
			return;
		}
		if( tracLevl>0 )
			ex.printStackTrace();
	}
}
