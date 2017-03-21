package com.hibo.bas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hibo.bas.classFile.ClassFileUtil;
import com.hibo.bas.spring.SpringWebUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月4日 下午6:59:12</p>
 * <p>类全名：com.hibo.bas.util.DataConfig</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
final public class DataConfig {
	public static java.io.File getConfigFile(String name) {
		if (Config.getWebContextPath() == null){
			try{
				String contextPath = ClassFileUtil.getContextPath();
				Config.setWebContextPath(contextPath);
			}catch (Exception ex ){
			}
		}
		
		if (Config.getWebContextPath() != null) {
			java.io.File file = new java.io.File(Config.ConfigPath + Config.getWebContextPath() + "." + name);
			if (file.isFile())
				return file;
		}
		return new java.io.File(Config.ConfigPath + "/" + name);
	}
	
	public static java.io.File getProjFile(String name) {
		String path = SpringWebUtil.getRealPath();
		if (path != null) {
			java.io.File file = new java.io.File(path + name);
			if (file.isFile())
				return file;
		}
		return null;
	}

	/*
	 * com.hibo.bas.util.DataConfig.getConfigFiles("Spring-ApplicationContext-*.xml"
	 * )
	 */
	public static java.io.File[] getConfigFiles(String namePattern) {
		final java.util.List<java.io.File> files1 = new ArrayList<>();
		final java.util.List<java.io.File> files2 = new ArrayList<>();
		final java.io.File configPath = new java.io.File(Config.ConfigPath);
		// fs[i] = new File(ss[i], this);
		final String allFiles[] = configPath.list();
		if (allFiles == null)
			return null;
		String pattern1 = Config.getWebContextPath() == null ? null : Config
				.getWebContextPath().substring(1) + "." + namePattern;
		for (int i = 0; i < allFiles.length; i++) {
			if (pattern1 != null
					&& com.hibo.bas.file.FileSystem.like(allFiles[i], pattern1))
				files1.add(new java.io.File(configPath, allFiles[i])
						.getAbsoluteFile());
			else if (com.hibo.bas.file.FileSystem
					.like(allFiles[i], namePattern))
				files2.add(new java.io.File(configPath, allFiles[i])
						.getAbsoluteFile());
		}
		if (files1.size() > 0)
			return files1.toArray(new java.io.File[files1.size()]);
		return files2.toArray(new java.io.File[files2.size()]);
	}
	
	/**
     * 取 Config.property 中的配置
     * @param name String
     * @return String
     */
    public static String getConfig(String  name)
    {
        return (String)getServerConfig().get(name);
    }
    
    public static String getConfig(String  name,String dftValue)
    {
        String s = (String)getServerConfig().get(name);
        return s==null ? dftValue : s;
    }
    
    public static int getIntConfig(String  name)
    {
        return StrUtil.obj2int((String)getServerConfig().get(name));
    }
    
    public static int getIntConfig(String  name,int dftValue)
    {
        return StrUtil.obj2int((String)getServerConfig().get(name),dftValue);
    }
    
    private static java.util.Map<String,String> serverConfig;
    
    public static java.util.Map<String,String> getServerConfig()
    {
        return getServerConfig( false );
    }
    
    /**
     * 清理缓存
     */
    public static void SetServerConfigNull()
    {
       serverConfig=null;
    }
    
    
    public static java.util.Map<String,String> getServerConfig(boolean ignoreOpenFail)
    {
        if( serverConfig==null )
        {
            serverConfig = new ConcurrentHashMap();//有清这个Map的程序,这里需要线程安全!
            java.io.Reader fileIn = null;
            try
            {
            	final String cfgFilePath = DataConfig.getConfigFile("Config.properties").getAbsolutePath();
            	Diagnostic.trace(1,"load Config.properties:"+cfgFilePath);
            	fileIn = getConfigReader("Config.properties");
            	if( fileIn!=null )
            	{
            		java.util.Map macro = new HashMap();
            		macro.put("HBCONFIGDIR",new java.io.File(Config.ConfigPath).getCanonicalPath().replace('\\','/'));
            		macro.put("WORKDIR",new java.io.File(System.getProperty("user.dir")).getCanonicalPath().replace('\\','/'));
            		MapUtil.loadToMap(serverConfig, new BufferedReader(new RemoveCommentReader(fileIn)),7,macro);
            	} else 
            	{
            		System.out.println("未找到文件："+cfgFilePath);
            	}
                 
            } catch (IOException ex)
            {
            	if( !ignoreOpenFail && Diagnostic.getTraceLevel()>=Diagnostic.LVINFO )
            		ex.printStackTrace();
            } finally
            {
                if( fileIn!=null ) try { fileIn.close();}
                catch( Throwable ex )
                {
                    ex.printStackTrace();
                }
            }
            
            Diagnostic.trace(1,"load Config.properties:"+serverConfig);

            Object o = serverConfig.get("Diag.TraceLevel");
            if( o!=null ) {
                int l = StrUtil.obj2int(o);
                if(l>Diagnostic.getTraceLevel())
                	Diagnostic.setTraceLevel( l );
            }
            serverConfig.remove("Diag.TraceLevel");
        } 
        return serverConfig;
    }
    
	static java.io.Reader getConfigReader(String name)
	{
		final java.util.List<java.io.Reader> readers = new ArrayList<java.io.Reader>();
		try {
			java.io.File file = getConfigFile(name);
			if (file.isFile())
				return ResUtil.newInputStreamReader(new java.io.FileInputStream(file),"UTF-8");
			return null;
		} catch( Throwable ex)
		{
			for(final java.io.Reader reader:readers)
				try { reader.close();} catch( Throwable ex2){}
			return null;
		}
	}
	
    public static String[] getWorkSpaceList(int options)
	{
    	try{
	        String[][] ws = loadWorkSpace( options );
	        String[] ws1 = new String[ws.length];
	        for(int i = 0; i < ws.length; i++){
	        	ws1[i] = ws[i][0]+":"+ws[i][1];
	        }
	        return ws1;
    	}catch(Exception ex){
    		
    	}
    	return new String[0];
	}
    
    /**
     * <p>功能： 返回账套列表的map</p>
     * <p>作者：吕康</p>
     * <p>创建日期：2015年11月10日 上午10:03:01</p>
     * @param options
     * @return
     */
    public static Map<String, String> getWorkSpaceMap(int options)
	{
    	try{
	        String[][] ws = loadWorkSpace( options );
	        Map<String, String> map = new HashMap<String, String>();
	        for(int i = 0; i < ws.length; i++){
	        	map.put(ws[i][0], ws[i][1]);
	        }
	        return map;
    	}catch(Exception ex){
    		
    	}
    	return new HashMap<String, String>();
	}
	
    public static String[][] listWorkSpace(int options) throws IOException
	{
        return loadWorkSpace( options );
	}
    public static  WorkSpace[] listWorkSpaces(int options) throws IOException
	{
        return loadWorkSpaces( options );
	}

    /*
     * options#1 : 重装载
     *         2： visible = true 
     */
    static String[][] loadWorkSpace(int options) throws IOException
    {
    	{
    		WorkSpace wsa[] = WorkSpace.load((options&1)!=0);
    		if( wsa==null )
    			return null;
    		//String[][] a = new String[wsa.length][];//new snsoft.util.data.WorkSpace()
    		java.util.List<String[]> list = new ArrayList();
    		for(int i=0;i<wsa.length;i++)
    		{
    			WorkSpace ws = wsa[i];
    			if( (ws.options&0x100)!=0)
               	 continue;
    			list.add( new String[]{ws.id,ws.title,ws.getDataSource()} );
    		}
    		return list.toArray(new String[list.size()][]);
    	}
	}
    
    static WorkSpace[] loadWorkSpaces(int options) throws IOException
    {
    	{
    		WorkSpace wsa[] = WorkSpace.load((options&1)!=0);
    		if( wsa==null )
    			return null;
    		return wsa;
    	}
	}
    
    /**
     * 取系统列表
     * @return
     */
    public static String[] getSysIdList(){
    	String sysidList = getConfig("sysidList");
    	if (sysidList == null)
    		sysidList = "00:瀚铂科技";
    	return StrUtil.splitString(sysidList, ',');
    }
    
    /**
     * 取系统列表Map
     * @return
     */
    public static Map<String, String> getSysIdMap(){
    	Map<String, String> map = new HashMap<String, String>();
		String[] sysList = getSysIdList();
		for(int i=0;i<sysList.length;i++){
			map.put(sysList[i].substring(0, 2), sysList[i].substring(3));
		}
    	return map;
    }
    
    /**
     * 取系统title(后台管理页面)
     * @return
     */
    public static String getSysTitle(){
    	//String sysTitle = getConfig("sysTitle");
    	String sysTitle = getConfig("HB.title");
    	if (sysTitle == null)
    		sysTitle = "瀚铂科技管理系统";
    	return sysTitle;
    }
    
    /**
     * 取系统Home(后台管理页面)
     * @return
     */
    public static String getSysHome(){
    	return getConfig("HB.home");
    }
    
    /**
     * <p>功能：获取默认的昵称前缀 </p>
     * <p>作者：吕康</p>
     * <p>创建日期：2015年12月24日 下午5:17:43</p>
     * @return
     */
    public static String getDefaultNick(){
    	String defaultNick = getConfig("DEFAULTNICK");
    	if (defaultNick == null)
    		defaultNick = "hibo";
    	return defaultNick;
    }
}
