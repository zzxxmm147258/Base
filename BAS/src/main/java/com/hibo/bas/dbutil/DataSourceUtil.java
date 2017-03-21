package com.hibo.bas.dbutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.WorkSpace;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.util.Envparam;

/**
 * <p>标题：数据源 </p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015-7-4 下午4:40:59</p>
 * <p>类全名：com.hibo.base.dbutil.DataSource</p>
 * 作者：Victor
 * 初审：
 * 复审：
 * @version 1.0
 */
public class DataSourceUtil {
	private static final Logger log = LoggerFactory.getLogger(DataSourceUtil.class);
	
	public static Map<Object, Object> getTargetDataSources(){
		Map<Object, Object> targetDataSources = null;
		try {
			targetDataSources = new HashMap<Object, Object>();
			WorkSpace[] workSpace = DataConfig.listWorkSpaces(0);
			if(null!=workSpace&&workSpace.length>0){
				for (WorkSpace work : workSpace) {
					BasicDataSource dataSource = new BasicDataSource();
					dataSource.setDriverClassName(work.driver);
					dataSource.setUrl(work.driverurl);
					dataSource.setUsername(work.username);
					dataSource.setPassword(work.password);
					//初始化连接数
					dataSource.setInitialSize(work.minIdle);
					//最小空闲连接数
					dataSource.setMinIdle(work.minIdle);
					//最大空闲连接数
					dataSource.setMaxIdle(work.maxIdle);
					//最大连接数
					dataSource.setMaxActive(work.maxActive);
					//最大等待时间:当没有可用连接时,连接池等待连接被归还的最大时间(以毫秒计数),超过时间则抛出异常,如果设置为-1表示无限等待
					dataSource.setMaxWait(work.maxWait);
					
					//空闲时，是否要进行有效性检测
					dataSource.setTestWhileIdle(true);
					//进行检测一个连接是有效的SQL语句，比如oracle是select 1 from dual 而 mysql是 select 1
					if (work.dbType == 2){
						dataSource.setValidationQuery("select 1 from dual");
					}else{
						dataSource.setValidationQuery("select 1");
					}
					//是否在数据库连接请求量大的时候，如总数50，当前已请求了49个，所剩不多了，检测那些执行时间久的连接，将其从池中移除掉(移除之后怎么作，由实现决定，如直接断开，或者任其继续执行等)
					dataSource.setRemoveAbandoned(work.removeAbandon);
					//一次数据库操作执行时间超过多少秒的连接被认为是需要移除的(以秒数为单位)
					dataSource.setRemoveAbandonedTimeout(work.abandonTimeOut);
					//回收超时连接是否输出日志
					dataSource.setLogAbandoned(work.logAbandon);
					//每隔多少时间检测一次，比如每半小时检测一次，总不能总是检测，这会对性能产生影响
					dataSource.setTimeBetweenEvictionRunsMillis(1800);
					//一个数据库连接连接多少时间之外，我们认为其应该不再适用了(可能下一次就会失效了)，应该移除并重新建立连接了
					dataSource.setMinEvictableIdleTimeMillis(1800);
					//每次检测时，需要检测多少个数据连接，一般设置为与最大连接数一样，这样就可以检测完所有的连接
					dataSource.setNumTestsPerEvictionRun(work.maxActive);
					
					LazyConnectionDataSourceProxy sourceProxy = new LazyConnectionDataSourceProxy(dataSource);
					targetDataSources.put(work.id, sourceProxy);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return targetDataSources;
	}
	
//	public static Object getDefaultTargetDataSource(){
//		return DataSourceUtil.getTargetDataSources().get(DataSourceUtil.getDefaultDataSource());
//	}
	
	public static String getDataSource(){
		LoginInfo loginInfo = null;
		try{
			loginInfo = Envparam.getLoginInfo();
		}catch (Exception e){
			if(log.isDebugEnabled()){
				log.debug(e.getMessage());
			}
		}
		String dataSource = getDefaultDataSource();
		//判断用户是否登陆，如果登陆用户信息为空则重新登陆
		if (null != loginInfo && loginInfo.getWork() != null) {
			dataSource = loginInfo.getWork();
		}
		return dataSource;
	}
	
	public static String getDefaultDataSource(){
		String DEFAULTDB = DataConfig.getConfig("DEFAULTDB");
		if(null==DEFAULTDB){
			throw new RuntimeException("config文件内<DEFAULTD>属性的默认账套不能为空!");
		}
		return DEFAULTDB;
	}
	
	public static int getDbType(){
		String dataSource = getDataSource();
		WorkSpace work = WorkSpace.getWorkspace(dataSource);
		if (work != null)
			return work.dbType;
		return 3;
	}

}
