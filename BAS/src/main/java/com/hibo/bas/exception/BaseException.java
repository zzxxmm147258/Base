package com.hibo.bas.exception;

import java.util.Map;

/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月9日 下午3:26:47</p>
 * <p>类全名：com.hibo.bas.exception.BaseException</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
public class BaseException extends java.lang.RuntimeException
{
	private static final long serialVersionUID = -1931250797876242081L;
	public BaseException()
	{
		super();
	}
	public BaseException(String s)
	{
		super(s);
	}
	public BaseException(String errorCode,String s)
	{
		super(s);
		this.errorCode = errorCode;
	}
	public BaseException(java.lang.Throwable ex)
	{
	    this(null,null,ex);
	}
	/*
	 * 000000-000019 -- 系统或程序错误（NullPointer,Array）
	 * 000020-000029 -- 文件系统，FTP 等错误
	 * 000030-000039 --- 网络错误
	 *     000031 —— java.net.ConnectException 
	 * 0001XX -- 数据库操作错误（SQL等）
	 * 0002XX -- 登录错误
	 * 0010XX -- 流程错误； 参见 RatifyDataChangedException
	 * 0020XX -- TAC 错误 , Yacc 错误
	 *    002001 -- 编译错误
	 *    002002 -- 执行错误
	 *    002050 -- Yacc
	 */
	protected String errorCode;
	public String getErrorCode()
	{
		return errorCode;
	}
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}
	public BaseException(String message,java.lang.Throwable ex)
	{
		super( message,ex);
	}
	public BaseException(String code,String message,java.lang.Throwable ex)
	{
		super( message,ex);
		this.errorCode = code;
		
	}
	
	protected String getMessage0()
	{
		return super.getMessage();
	}
	
	@Override
	final public String getMessage()
	{
		return getMessage(0);
	}
	
	protected int errLevel = 3;
	public int getErrLevel()
	{
		return errLevel; //LVERROR
	}
	
	/*
	 * flags#1 : 不含 errorCode
	 */
	public String getMessage(final int flags)
	{
		return errorCode==null || (flags&1)!=0 ? super.getMessage() : "["+errorCode+"]"+super.getMessage();
	}
	
	static protected String getResSysIdOfErrcode(String errcode)
	{
		int p = errcode.indexOf('.');
		if( p<0 )
			p = 2;
		return "ERR"+errcode.substring(0,p);
		//return p>=0 ? "ERR"+errcode.substring(0,p) : "ERR00";
	}
	
	public static String getErrTextByCode(Map<Object,Object> envP,String errcode,Object...args)
	{
		int p = errcode.indexOf('.');
		if( p>0 ) {
			return "ERR"+errcode;
		} else
		{
			return "ERR"+errcode;
		}
	}
}
