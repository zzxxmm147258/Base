package com.hibo.bas.ms;

import com.hibo.bas.util.Diagnostic;
import com.hibo.bas.util.StrUtil;

//import com.jacob.activeX.ActiveXComponent;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 上午10:20:42</p>
 * <p>类全名：com.hibo.bas.ms.MsUtil</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */

public class MsUtil
{
    /*
        useExcelMode = 0 : Poi-hssf （客户段）
                       1 : jacob
                       2 : Poi-hssf （服务器段）
    */
    public static int  useExcelMode = 0;
    static {
         try {
             String s = System.getProperty("UseExcelMode");
             if(s!=null) useExcelMode = Integer.parseInt(s.trim());
         } catch( Throwable ex )
         {
         }
    };
    
    /*
      兼容性： WindowState
      
     */
    /*
    static public ActiveXComponent newOfficeActiveXComponent(String activeID)
    {
    	final int usewpsoffice = StrUtil.obj2int(LocalDataConfig.getConfigStrValue("usewpsoffice"));
    	 int flags = 0;
    	 if( usewpsoffice!=0 ) {
    		 if( "Word.Application".equalsIgnoreCase(activeID) )
    			 flags = 1;
    		 if( "Excel.Application".equalsIgnoreCase(activeID) )
    			 flags = 2;
    		 else if( "PowerPoint.Application".equalsIgnoreCase(activeID) )
    			flags = 4;
    	 }
    	RuntimeException ex = null;
    	if( (usewpsoffice&flags)==0 )
    	{
    		try {
    			return  new ActiveXComponent(activeID);//"Word.Application"); 
    		} catch( RuntimeException _ex)
    		{
    			ex = _ex;
    		}
    	}
    	if( Diagnostic.isEnableOutput() )
    	{
    		Diagnostic.println("尝试使用WPS : "+activeID);
    	}
    	if( "Word.Application".equalsIgnoreCase(activeID) )
    	{
    		try {
    			return  new ActiveXComponent("wps.application"); 
    		} catch( RuntimeException _ex)
    		{
    			if( ex==null ) ex = _ex;
    		}
    	} else if( "Excel.Application".equalsIgnoreCase(activeID) )
    	{
    		try {
    			return  new ActiveXComponent("et.Application"); 
    		} catch( RuntimeException _ex)
    		{
    			if( ex==null ) ex = _ex;
    		}
    	}  else if( "PowerPoint.Application".equalsIgnoreCase(activeID) )
    	{
    		try {
    			return  new ActiveXComponent("wpp.Application"); 
    		} catch( RuntimeException _ex)
    		{
    			if( ex==null ) ex = _ex;
    		}
    	}
    	throw ex;
    }
    */

}
