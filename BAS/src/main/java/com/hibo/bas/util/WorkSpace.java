package com.hibo.bas.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.hibo.bas.xml.XmlElement;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月10日 下午7:01:07</p>
 * <p>类全名：com.hibo.bas.util.WorkSpace</p>
 * 作者：陈国红
 * 初审：
 * 复审：
 * @version 1.0
 */
/*
  <?xml version="1.0" encoding="UTF-8"?>
 <workspace-list>
  
   <workspace id="00" 
       title="帐套1"
       startTimerTask="true" 
     >
  <datasource id="Default" url="jdbc:inetdae:127.0.0.1:1433?database=snadkbas&charset=GB18030&user=sa&amp;password="/>
  <datasource id="READ"   host = "127.0.0.1"    port="1433"    database="snadkbas"    user="sa"    password=""   type="1"/>
  
  // MySql:
  <datasource id="MYSQL"   host = "127.0.0.1"    port="3306"    database="snsoft80_demo"    user="root"    password=""   type="4"/>
  
  </workspace>
  
   <workspace id="00" title="帐套1">
   </workspace>
   
 </workspace-list>
 
  type : sqlserver
         tds:sqlserver
         ms:sqlserver
         oracle
         mysql
         
  
   V7.opt    &1 : 7.0 : 定时任务
    *        &2 : 7.0 : 消息任务

Config.properties :
   StartInitialize+=;snsoft.appserv.AppTimerTask.loadWSTask
   
 static String dataSource = WorkSpace.getWorkspace("00_snsoft80_demo").getDataSource();       
 */
final public class WorkSpace 
{
	final public String id, title,url,driver,driverurl,database,username,password;
	//               最小空闲数   最大空闲数     最大连接数              放弃连接超时 s   最大等待连接ms
	final public int minIdle,maxIdle,maxActive,abandonTimeOut,maxWait;
	//                    记关闭超时日志        关闭超时连接
	final public boolean logAbandon,removeAbandon;
	/*
	 *   1 :-- 自动启动定时任务
	 *   2-- 消息任务
	 * 0x10 --  禁止启动定时任务
	 * 
	 * 0x100 -- 隐藏 (登录时不可见)
	 * 
	 */
	final public int dbType;
	final public int options;
	final public String dbCharset;
	/*
	 * dsidList : 数据源类型：
	 *   TIMERTASK  -- 定时任务使用的 数据源
	 *   READ
	 *   CONFIG
	 *   WRITE
	 *   
	 */
	public final String dsidList[], dsurlList[];
	
	final private java.util.Map<String,String> xprops;
	
	private WorkSpace(String id,String title, String url,int options,String dbCharset,String dsidList[], String dsurlList[],java.util.Map<String,String> xprops,String driver,Map<String,String> map)
	{
		this.id = id;
		this.title = title;
		this.url = url;
		this.options = options;
		this.dbCharset = dbCharset;
		this.dsidList = dsidList;
		this.dsurlList = dsurlList;		
		this.xprops = xprops;
		this.driver = driver;
		this.driverurl = map.get("url");
		this.username = map.get("username");
		this.password = map.get("password");
		this.database = map.get("database");
		this.minIdle = StrUtil.obj2int(map.get("minIdle"), 1);
		this.maxIdle = StrUtil.obj2int(map.get("maxIdle"), 10);
		this.maxActive = StrUtil.obj2int(map.get("maxActive"), 50);
		this.maxWait = StrUtil.obj2int(map.get("maxWait"), 1000);
		this.abandonTimeOut = StrUtil.obj2int(map.get("abandonTimeOut"), 600);
		this.logAbandon = !"false".equals(map.get("logAbandon"));
		this.removeAbandon = !"false".equals(map.get("removeAbandon"));
		this.dbType = StrUtil.obj2int(map.get("dbType"));
	}
	public String getXProperty(String name)
	{
		return xprops==null ? null : xprops.get(name);
	}
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder(id).append(':')
				 .append(title).append("\r\n DEFAULT:").append(url);//+"\r\"
		if( dsidList!=null ) for(int i=0;i<dsidList.length;i++)
		{
			s.append("\r\n  ").append(dsidList[i]).append(':').append(dsurlList[i]);
		}
		return s.toString();
	}
	static private WorkSpace listWorkSpace[];
	static private DataSourceCluster listDataSourceCluster[];
	/*
	 * lastLoadTime==0 : listWorkSpace 从资源文件装载
	 *             >0  : listWorkSpace 从 config 文件装载     
	 */
	static private long lastLoadTime ;
	static public synchronized WorkSpace[] load() 
	{
		return load(false);
	}
	
	//javax.annotation.
	static public synchronized WorkSpace[] load(boolean  forecReload) 
	{
		if( listWorkSpace!=null && !forecReload )
		{
			return listWorkSpace;
		}
		java.io.InputStream is = null;
		try {
			java.io.File file = DataConfig.getConfigFile("WorkSpace.xml");
			if( listWorkSpace!=null && lastLoadTime>0 && lastLoadTime==file.lastModified() )
			{
				// 文件未修改
				return listWorkSpace;
			}
			if( file.isFile() )
			{
				is = new java.io.FileInputStream(file);
			}
			if( is==null )
			{
				//System.out.println("Can not found file "+file.getCanonicalPath());
				String s = "未定义账套文件:"+file.getCanonicalPath();
				System.out.println(s);
				throw new com.hibo.bas.exception.HookedException(s);
			}
			final XmlElement rootE = com.hibo.bas.xml.XmlHandler.parseXML(is);
			java.util.List<WorkSpace> list = new ArrayList();
			java.util.Map<String,String[]> clusters = new HashMap();
			for(final XmlElement wsE:rootE.subElements)
			{
				list.add(parseWorkspace(wsE,clusters));
			}
			listWorkSpace = list.toArray( new WorkSpace[list.size()] );
			listDataSourceCluster = new DataSourceCluster[clusters.size()];
			{
				int i=0;
				for(final String cluseterID : clusters.keySet() )
				{
					listDataSourceCluster[i++] = new DataSourceCluster(cluseterID,clusters.get(cluseterID));
				}
				if( i>1 )
					Arrays.sort(listDataSourceCluster);
			}
			if( file.isFile() )
			{
				lastLoadTime = file.lastModified();
			} else
			{
				lastLoadTime = 0;
			}
				//throw new java.lang.IllegalArgumentException("未定义");
		} catch( Throwable ex)
		{
			throw com.hibo.bas.exception.HookedException.toRuntimeException(ex);
		} finally
		{
			if( is!=null ) try { is.close();} catch( Throwable ex){}
		}
		return listWorkSpace;
	}
	
	public static String getWorkspcDSIDByURL(String url)//,char join)
	{
		WorkSpace[] a = load(false);
		if( a==null )
			return null;
		String matched = null;
		for(int i=0;i<a.length;i++)
		{
			final WorkSpace ws = a[i];
			if( url.equals(ws.url) )
				return ws.id;
			if( matched==null && ws.dsurlList!=null ) {
				int j = StrUtil.indexOf(ws.dsurlList,url);
				if( j>=0 )
					matched = ws.dsidList[j]+"@"+ws.id;
			}
		}
		return matched;
	}
	
	static java.lang.RuntimeException wspError(Object s)
	{
		return new java.lang.IllegalArgumentException("帐套文件错误：\r\n"+(s==null?"":s));
	}
	
	private static final String stdWSAttrNames[] = {"id","title","options","charset","visible","startTimerTask"};
	/*
	 * 其他属性：
	 *   forceTableSpc=""  // 强制表 空间：
	 */
	static {
		Arrays.sort(stdWSAttrNames);
	}
	
	private static WorkSpace parseWorkspace(final XmlElement wsE,java.util.Map<String,String[]> clusters)
	{
		String id = wsE.getAttribute("id");  // 帐套 ID
		if( id==null )
			throw wspError(wsE);
		id = id.toUpperCase();
		for(int i=0;i<id.length();i++)
		{
			char c = id.charAt(i);
			if( c>=0x80||c<=0x20||!(Character.isJavaIdentifierPart(c)||c=='.') )
			{
				throw new java.lang.IllegalArgumentException("帐套ID 只能为 字母，数字，下画线:"+id);
			}
		}
		String title = wsE.getAttribute("title");
		int options = StrUtil.obj2int(wsE.getAttribute("options"));
		String charSet = wsE.getAttribute("charset");
		if( "false".equalsIgnoreCase(wsE.getAttribute("visible")) )
		{
			options |= 0x100;
		}
		if( "true".equalsIgnoreCase(wsE.getAttribute("startTimerTask")))
		{
			options |= 1;
		} else if( "false".equalsIgnoreCase(wsE.getAttribute("startTimerTask")))
		{
			options |= 0x10;
		}
		String defaultURL = null;
		String driverClass = null;
		Map<String ,String> mapTepm = new HashMap<String,String>();
		java.util.List<String> dsidList = new ArrayList();
		java.util.List<String> urlList = new ArrayList();
		String urlTepm = null;
		String usernameTepm = null;
		String passwordTepm = null;
		for(final XmlElement e : wsE.subElements )
		{
			String url = e.getAttribute("url");
			String dsID = e.getAttribute("id");
			String dsClusterID = e.getAttribute("clusterid"); // 同一个集群：
			if( url==null )
			{
//				String typeAttr = e.getAttribute("type");
//				if( typeAttr!=null ) {
//				}
				final String port = e.getAttribute("port");
				final String type = e.getAttribute("type");
				final String host = e.getAttribute("host");
				final String database = e.getAttribute("database");
				final String user = e.getAttribute("user");
				usernameTepm  = user;
				final String password = e.getAttribute("password");
				passwordTepm = password;
				String charset = e.getAttribute("charset");
				String drivertype = e.getAttribute("drivertype");
				
				mapTepm.put("minIdle", e.getAttribute("minidle"));
				mapTepm.put("maxIdle", e.getAttribute("maxidle"));
				mapTepm.put("maxActive", e.getAttribute("maxactive"));
				mapTepm.put("maxWait", e.getAttribute("maxwait"));
				mapTepm.put("abandonTimeOut", e.getAttribute("abtimeout"));
				mapTepm.put("logAbandon",e.getAttribute("logab"));
				mapTepm.put("removeAbandon",e.getAttribute("removeab"));
				
				Properties props = new Properties();
				if ("1".equals(type) || "sqlserver".equals(type)){
					mapTepm.put("dbType", "1");
					if (charset==null)
						charset = "GB18030";
					driverClass = "com.inet.tds.TdsDriver";
					urlTepm = "jdbc:inetdae:"+host+":"+port+"?database="+database+"&charset="+charset;
					url = urlTepm+"&user="+user+"&password="+password;
				}else if ("2".equals(type) || "oracle".equals(type)){
					mapTepm.put("dbType", "2");
					if (drivertype==null)
						drivertype = "thin";
					driverClass = "oracle.jdbc.driver.OracleDriver";
					urlTepm = "jdbc:oracle:"+drivertype+":@//"+host+":"+port+"/"+database;
					url = urlTepm +"?user="+user+"&password="+password;
				}else if ("3".equals(type) || "mysql".equals(type)){
				//useUnicode=true&characterEncoding=UTF8
					mapTepm.put("dbType", "3");
					if (charset==null)
						charset = "UTF8";
					driverClass = "com.mysql.jdbc.Driver";
					urlTepm = "jdbc:mysql://"+host+":"+port+"/"+database+"?useUnicode=true&characterEncoding="+charset;
					url = urlTepm +"&user="+user+"&password="+password;
				}
				
				mapTepm.put("database",database);
			}
			if( url==null )
				throw wspError(wsE);
			if( dsID==null || dsID.length()==0 ||  "DEFAULT".equals(dsID=dsID.toUpperCase()) )
			{
				defaultURL = url;
				mapTepm.put("url", urlTepm);
				mapTepm.put("username", usernameTepm);
				mapTepm.put("password", passwordTepm);
				
			} else
			{
				dsidList.add(dsID);
				urlList.add(url);
			}
			
			if( defaultURL==null )
				defaultURL = url;
			if( dsClusterID!=null && clusters!=null )
			{
				dsClusterID = dsClusterID.toUpperCase();
				String[] c = clusters.get(dsClusterID);
				c = ArrayUtil.addElement(c, url, true);
				clusters.put(dsClusterID, c);
				//	;//clusters.put(dsClusterID, c)
			}
		}
		
		if( !wsE.qName.equals("workspace") || wsE.subElements.size()==0 || defaultURL==null )
			throw wspError(wsE);
		java.util.Map<String,String> xprops = null;
		for(String name : wsE.getAttributeNames() )
		{
			if( Arrays.binarySearch(stdWSAttrNames, name)<0 ) 
			{
				if( xprops==null )
					xprops = new HashMap();
				xprops.put(name, wsE.getAttribute(name));
			}
		}
		/* 
		
		*/
		return new WorkSpace(id,title,defaultURL,options,charSet,
				dsidList.size()==0?null:dsidList.toArray(new String[dsidList.size()]),
				urlList.size()==0?null:urlList.toArray(new String[urlList.size()]),xprops,driverClass,mapTepm);
		//WorkSpace
	}
	
	static public final WorkSpace getWorkspace(String id)
	{
		final WorkSpace[] workspc = listWorkSpace!=null ? listWorkSpace : load();
		if( workspc==null || id==null )
			return null;
		for(int i=0;i<workspc.length;i++)
		{
			if( id.equalsIgnoreCase(workspc[i].id) )
				return workspc[i];
		}
		return null;
	}
	
	public String getDataSource()
	{
		return this.url;
	}
	
	public String getDataSource(String dsID)
	{
		if( this.dsidList==null || dsID==null || dsID.length()==0 )
			return this.url;
		for(int i=0;i<dsidList.length;i++)
		{
			if( dsID.equalsIgnoreCase(dsidList[i]) )
				return this.dsurlList[i];
		}
		return url;
	}
	
	public String getDataSourceByDSID(String  dsidA[] )//,String defaultDSID)
	{
		if( this.dsidList==null || dsidA==null || dsidA.length==0 )
			return this.url;
		for(final String dsID : dsidA )
		{
			for(int i=0;i<dsidList.length;i++)
			{
				if( dsID.equalsIgnoreCase(dsidList[i]) )
					return this.dsurlList[i];
			}
		}
		return  url;
	}
	
	private String[] listDataSource;
	/**
	 * 列举该帐套定义的所有数据源
	 * @return
	 */
	public String[] listDataSource()
	{
		if( listDataSource==null) {
			final java.util.List<String> list = new ArrayList<>();
			list.add(url);
			if( dsurlList!=null ) for(int i=0;i<dsurlList.length;i++)
			{
				if( list.indexOf(dsurlList[i])<0 )
					list.add(dsurlList[i]);
			}
			listDataSource = list.toArray(new String[list.size()]);
		}
		return listDataSource;
		//java
	}
	
	static public String getDataSource(String idWorkspc,String dsID)
	{
		final WorkSpace s = getWorkspace(idWorkspc);
		return s==null ? null : s.getDataSource(dsID);
	}

	static public String getDataSourceByIdWorkspc(String idWorkspc)
	{
		final WorkSpace s = getWorkspace(idWorkspc);
		return s==null ? null : s.url;
	}
	
	static public String getDataSourceByDSID(String idWorkspc,String dsID[])
	{
		final WorkSpace s = getWorkspace(idWorkspc);
		return s==null ? null : s.getDataSourceByDSID(dsID);
	}
	
	static public String[] listDataSource(String wspID)
	{
		final WorkSpace s = getWorkspace(wspID);
		return s==null ? null : s.listDataSource();
	}
	
	static public WorkSpace getWorkSpaceByDataSource(String dataSource,boolean allDsid)
	{
		if( dataSource==null )
			return null;
		WorkSpace[] workspc = load();
		if( workspc==null )
			return null;
		for(int i=0;i<workspc.length;i++)
		{
			if( dataSource.equals(workspc[i].url) )
				return workspc[i];
			if( allDsid && workspc[i].dsurlList!=null ) for(String s : workspc[i].dsurlList)
			{
				if( dataSource.equals(s) )
					return workspc[i];
			}
		}
//		if( this)
		return null;
	}
	
	static public WorkSpace getWorkSpaceByDataSource(String dataSource)
	{
		return getWorkSpaceByDataSource(dataSource,false);
	}
	
	/*
	  getWorkSpaceByDataSource(dataSource,new String[]{"TIMERTASK"})
	 */
	static public WorkSpace getWorkSpaceByDataSource(String dataSource,String dsidA[])
	{
		if( dataSource==null )
			return null;
		if( dsidA==null || dsidA.length==0 )
			return getWorkSpaceByDataSource(dataSource);
		WorkSpace[] workspc = load();
		if( workspc==null )
			return null;
		for(int i=0;i<workspc.length;i++)
		{
			for(String dsid:dsidA )
			{
				if(workspc[i].dsidList!=null) for(int j=0;j<workspc[i].dsidList.length;j++)
				{
					if( dsid.equals(workspc[i].dsidList[j]) && dataSource.equals(workspc[i].dsurlList[j]) )
					{
						return workspc[i];
					}
				}
			}
		}
		return getWorkSpaceByDataSource(dataSource);
	}
	
	static public String[] getSameDataSource(String url)
	{
		// 
		return null;
	}
	
	final static class DataSourceCluster implements java.util.Comparator<DataSourceCluster>
	{
		final String clusterID;
		final String dataSource[];
		public DataSourceCluster(String clusterID,String dataSource[])
		{
			this.clusterID = clusterID;
			Arrays.sort(dataSource);
			this.dataSource = dataSource;
		}
		@Override
		public int compare(DataSourceCluster o1, DataSourceCluster o2) 
		{
			return o1.clusterID.compareTo(o2.clusterID);
		}
	}
	
	static public String getCluseterID(String dataSource)
	{
		if( listDataSourceCluster==null )
			load();
		if(listDataSourceCluster!=null && dataSource!=null )for(int i=0;i<listDataSourceCluster.length;i++)
		{
			String a[] = listDataSourceCluster[i].dataSource;
			for(int j=0;j<a.length;j++)
				if( dataSource.equals(a[j]) )
					return listDataSourceCluster[i].clusterID;
			//if( Arrays.binarySearch(a, key))
		}
		return null;
	}
	
	static public String getConfigDataSource(String idWorkspc)
	{
		WorkSpace wsp = WorkSpace.getWorkspace(idWorkspc);
		if( wsp==null )
			return null;
		if( wsp.dsidList==null || wsp.dsidList.length==0 )
			return wsp.url;
		return wsp.getDataSource("CONFIG");
	}
	
	public String getConfigDataSource()
	{
		return this.getDataSource("CONFIG");
	}
	
	/*
	  同一类(集群)的 
	 */
	static public String[] getCluseterDataSourcesByClusterID(String cluseterID)
	{
		if( listDataSourceCluster==null )
			load();
		if(listDataSourceCluster!=null && cluseterID!=null) 
			for(int i=0;i<listDataSourceCluster.length;i++)
		{
			if( cluseterID.equals(listDataSourceCluster[i].clusterID) )
				return listDataSourceCluster[i].dataSource;
		}
		return null;
	}
	static public String[] getCluseterDataSourcesByURL(String url,boolean useDefault)
	{
		if( listDataSourceCluster==null )
			load();
		if(listDataSourceCluster!=null && url!=null) 
			for(int i=0;i<listDataSourceCluster.length;i++)
		{
				String a[] = listDataSourceCluster[i].dataSource;
				for(int j=0;j<a.length;j++)
				{
					if( url.equals(a[j]) )
						return a;
				}
				//return listDataSourceCluster[i].dataSource;
		}
		return useDefault ? new String[]{url} : null ;
	}
	
    public static void main(String args[])
    {
    	
    }
}