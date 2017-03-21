package com.hibo.bas.ms.util;
import com.hibo.bas.util.*;

/**
 * <p>标题：Excel工具类</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 上午10:33:30</p>
 * <p>类全名：com.hibo.bas.ms.util.ExcelUtil</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public class ExcelUtil
{
    /**
     * @param col: 列号(从0开始)
     * @return: 根据列号得到Excel的列名
     *  0: 'A'
     *  1: 'B'
     *  26  'AA'
     */
    public static String getExcelCellColName(int col)
    {
        col++;
        String s = "";
        while( col > 0 )
        {
            int m = (col-1)%26;
            col = (col-1)/26;
            s = (char)('A'+m) + s;
        }
        return s;
    }
    /**
     * @return   A : 0,
     *           Z : 25
     *          AA : 26
     */
    public static int parseExcelColName(String colName)
    {
        int col = 0;
        for(int pos=0; pos<colName.length(); pos++)
        {
          col *= 26;
       //   char ch = sCol.charAt(pos);
          col += 1+(colName.charAt(pos) - 'A');
        }
        return col-1;
    }
    
    /*
     *  A1 == 0x10001  A1 == 0x2001
     *  B1 == 0x10002
     */
    static public int parseExcelRowCol(String s)
    {
    	final int n = s.length();
    	int i;
    	char c;
    	for(i=0;i<n && (c=s.charAt(i))>='A' && c<='Z' ;i++)
    		;
    	int col = 0;
    	if( i>0 )//i<=0 || i>=n  )
    		col = parseExcelColName(s.substring(0,i))+1;
    	final int i1 = i;
    	for(;i<n ;i++)
    	{
    		if( (c=s.charAt(i))<'0' || c>'9' )
    			return 0;
    	}
    	//if( i1<i )
    	int row = i1<i ? Integer.parseInt(s.substring(i1)) : 0;  
    	return (row<<16) | col;
    }
    
/*
    +constants XlFileFormat
    +{
    +        const long xlAddIn = 18; //(&H12)
    +        const long xlCSV = 6;
    +        const long xlCSVMac = 22; //(&H16)
    +        const long xlCSVMSDOS = 24; //(&H18)
    +        const long xlCSVWindows = 23; //(&H17)
    +        const long xlCurrentPlatformText = -4158; //(&HFFFFEFC2)
    +        const long xlDBF2 = 7;
    +        const long xlDBF3 = 8;
    +        const long xlDBF4 = 11;
    +        const long xlDIF = 9;
    +        const long xlExcel2 = 16; //(&H10)
    +        const long xlExcel2FarEast = 27; //(&H1B)
    +        const long xlExcel3 = 29; //(&H1D)
    +        const long xlExcel4 = 33; //(&H21)
    +        const long xlExcel4Wordbook = 35; //(&H23)
    +        const long xlExcel5 = 39; //(&H27)
    +        const long xlExcel7 = 39; //(&H27)
    +        const long xlExcel9795 = 43; //(&H2B)
    +        const long xlHtml = 44; //(&H2C)
    +        const long xlIntlAddIn = 26; //(&H1A)
    +        const long xlIntlMacro = 25; //(&H19)
    +        const long xlSYLK = 2;
    +        const long xlTemplate = 17; //(&H11)
    +        const long xlTextMac = 19; //(&H13)
    +        const long xlTextMSDOS = 21; //(&H15)
    +        const long xlTextPrinter = 36; //(&H24)
    +        const long xlTextWindows = 20; //(&H14)
    +        const long xlUnicodeText = 42; //(&H2A)
    +        const long xlWebArchive = 45; //(&H2D)
    +        const long xlWJ2WD1 = 14;
    +        const long xlWJ3 = 40; //(&H28)
    +        const long xlWJ3FJ3 = 41; //(&H29)
    +        const long xlWK1 = 5;
    +        const long xlWK1ALL = 31; //(&H1F)
    +        const long xlWK1FMT = 30; //(&H1E)
    +        const long xlWK3 = 15;
    +        const long xlWK3FM3 = 32; //(&H20)
    +        const long xlWK4 = 38; //(&H26)
    +        const long xlWKS = 4;
    +        const long xlWordbookNormal = -4143; //(&HFFFFEFD1)
    +        const long xlWords2FarEast = 28; //(&H1C)
    +        const long xlWQ1 = 34; //(&H22)
    +        const long xlXMLSpredsheet = 46
 */
    final static public class PictureInfo
    {
        public String sheetName;
        public byte data[];
        /*
         * options&1 ==0:
         *    图片 相对于  页面的 位置 ( POI 模式 未使用)
         *  options&1 !=0:
         *     图片 相对于 单元 的 位置   
         */ 
        public float  x,y;
        public float  width,height;
        /*
         * 如果  c<0,r<0 : 相当于 页面 
         */
        public int r = -1,c = -1,w ,h;  // cell w:单元宽度
        //public boolean sizeToFit = true;
        /*
         * #1 : sizeToFit
         * #2 :  x,y 为  图片 相对于 单元 的 位置   
         */
        public int options = 1;
        public boolean isSizeToFit() // 2015-03-31
        {
        	return (options&1)!=0;
        }
        public void setSizeToFit(boolean sizeToFit) 
        {
        	if( sizeToFit )
        		options |= 1;
        	else
        		options &= ~1;
        }
        /*
         * #1 -- LEFT ; #2 -- RIGHT; #4 -- TOP ; #8 -- BOTTOM
         */
        public int align;
    };
    
    final public static class ExecuteMethod
    {
        public PictureInfo pictures[];
        public String callName ;
        public Object callArgs[];
    };
 
   
/*
  "111    =>   "111
  333"    =>   333"
  "222"   =>  "222"
  
    
 */
    static String[] parseLineCellText(String text)
    {
    	if( text==null )
    		return null;
    	if( text.length()==0 )
    		return new String[0];
    	String textA[] = StrUtil.splitString(text,'\t');
    	for(int i=0;i<textA.length;i++)
    	{
    		String s = textA[i];
			int ls = s.length();
            if( ls>=2 && s.charAt(0)=='"' && s.charAt(ls-1)=='"' && s.indexOf('\n')>=0 )
            {
                char a[] = new char[ls]; int na = 0;
                for(int j=1;j<ls-1;j++)
                {
                    char x = s.charAt(j);
                    if( x=='"' && j<ls-2 && s.charAt(j+1)=='"' )
                        j++;
                    a[na++] = x;
                }
                textA[i] = new String(a,0,na);
            }
    	}
    	return textA;
    }
    static public String[][] parseCellText(String text)
    {
        text = StrUtil.trimRight(text);
     
        final  int n = text.length();
        final java.util.List<String[]> lines = new java.util.ArrayList<>();
        final java.util.List<String> cells = new java.util.ArrayList<>();
        int countStrDelim = 0; //  双引号个数
        int countTabOrNewLn = 0;
        int pos0 = 0;
        for(int i=0;i<=n;i++)
        {
            final char c = i==n?'\n' : text.charAt(i);
            if( c=='"' )
            	countStrDelim++;
            else if( (c=='\t' || c=='\n')   )
            {
            	if( (countStrDelim&1)==0 || text.charAt(pos0)!='"' || i==n || c=='\t' )// || text.charAt(i-1)!='"'  )
            	//if( (countStrDelim&1)==0 || text.charAt(pos0)!='"'  || text.charAt(i-1)!='"'  )
            	{
            		String s = text.substring(pos0,i).trim();
            		pos0 = i+1;
            		if( countTabOrNewLn>0 ) {
            			int ls = s.length();
                        if( ls>=2 && s.charAt(0)=='"' && s.charAt(ls-1)=='"' )
                        {
                            char a[] = new char[ls]; int na = 0;
                            for(int j=1;j<ls-1;j++)
                            {
                                char x = s.charAt(j);
                                if( x=='"' && j<ls-2 && s.charAt(j+1)=='"' )
                                    j++;
                                a[na++] = x;
                            }
                            s = new String(a,0,na);
                        }
            		}
            		countStrDelim = 0;
            		countTabOrNewLn = 0;
            		cells.add(s);
            		if( c=='\n' )
            		{
            			lines.add(cells.toArray(new String[cells.size()]));
            			cells.clear();
            		}
            	} else // (countStrDelim&1)!=0
            	{
            		countTabOrNewLn++;
            	}
            }
            	
        }        
        return lines.toArray(new String[lines.size()][]);
       
    }    
   
}
