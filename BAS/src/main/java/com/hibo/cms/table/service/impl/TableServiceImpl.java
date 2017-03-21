package com.hibo.cms.table.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import com.hibo.bas.classFile.ClassFileUtil;
import com.hibo.bas.dbutil.DataSourceUtil;
import com.hibo.bas.util.Config;
import com.hibo.bas.util.StrUtil;
import com.hibo.bas.util.WorkSpace;
import com.hibo.bas.xml.XmlElement;
import com.hibo.cms.sys.model.login.LoginInfo;
import com.hibo.cms.table.dao.TableMapper;
import com.hibo.cms.table.service.ITableService;
import com.hibo.cms.util.Envparam;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月14日 下午2:41:53</p>
 * <p>类全名：com.hibo.cms.table.service.impl.TableServiceImpl</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */

@Service
public class TableServiceImpl implements ITableService{
	private static final Logger log = LoggerFactory.getLogger(TableServiceImpl.class);
	private int dbType;
	private TableMapper tableMapper;
	@Autowired
	public void setTableMapper(TableMapper tableMapper){
		this.tableMapper = tableMapper;
	}
	
	@Override
	public String createTable(String sysidList) {
		String msg = "更新结果：<br>";
		java.io.InputStream is = null;
		try {
			dbType = DataSourceUtil.getDbType();
			// WEB-INF   
			//TODO  目录处理
			String[] sysidArr = StrUtil.splitString(sysidList, ',');
			for (int i = 0; i < sysidArr.length; i++) {
				String name = "tabledef_" + sysidArr[i]+".xml";
				File file = null;
				try{
					file = ClassFileUtil.getFile("hibo/sql/"+name);
				}catch(Exception e){
					e.printStackTrace();
				}
				if (null == file || !file.exists()) {
					if(log.isInfoEnabled()){
						log.info("config查找文件"+name);
					}
					file = new java.io.File(Config.ConfigPath + "/" + name);
				}
				if (file.isFile()) {
					is = new java.io.FileInputStream(file);
				}
				if (is == null) {
					continue;
				}
				final XmlElement rootT = com.hibo.bas.xml.XmlHandler.parseXML(is);
				for (final XmlElement tbl : rootT.subElements) {
					if ("tables".equals(tbl.qName)) {
						msg += checkTable(tbl);
					} else if ("refdefs".equals(tbl.qName)) {
						//TODO  表关联处理   暂时不提供
					}
				}
				
			}
		} catch( Throwable ex)
		{
			msg = msg + "<br>" + ex.getMessage();
			ex.printStackTrace();
//			throw com.hibo.bas.exception.HookedExceptionception.toRuntimeException(ex);
		} finally
		{
			if( is!=null ) try { is.close();} catch( Throwable ex){}
		}
		return msg+"<br>执行完成!";
	}

	private String checkTable(XmlElement tbls){
		String msg = "";
		for(final XmlElement tbl:tbls.subElements)
		{
			String tableName = tbl.getAttribute("name");
			String tblspace = tbl.getAttribute("tblspace");
			if (tblspace == null)
				tblspace = "HB_SYSDATA";
			String indspace = tbl.getAttribute("indspace");
			if (indspace == null)
				indspace = "HB_SYSINDEX";
			boolean isCreate = false;
			if (dbType == 1)
				isCreate = tableMapper.selectTable1(tableName.toUpperCase()) == null;
			else if (dbType == 2)
				isCreate = tableMapper.selectTable2(tableName.toUpperCase()) == null;
			else
				isCreate = tableMapper.selectTable(getDataBase(), tableName) == null;
			
			if (isCreate){
				String msg1 = createTable(tbl, tableName, tblspace, indspace);
				if (!"".equals(msg1))
					msg += "<br>"+msg1;
			}else{
				String msg1 = alterTable(tbl, tableName);
				if (!"".equals(msg1))
					msg += "<br>"+msg1;
			}
		}
		return msg;
	}
	
	private String getDataBase(){
		LoginInfo loginInfo = Envparam.getLoginInfo();
		String workId = loginInfo.getWork();
		if (workId == null)
			 throw new com.hibo.bas.exception.HookedException("套帐获取失败！");
		String database = WorkSpace.getWorkspace(workId).database;
		return database;
	}
	
	/**
	 * 新建表
	 * @param tbl
	 * @param tableName
	 * @return
	 */
	private String createTable(XmlElement tbl, String tableName, String tblspace, String indspace){
		String msg = "";
		String sql = "CREATE TABLE ";
		if (dbType == 3)
			sql += "`"+tableName+"` (";
		else
			sql += tableName+" (";
		String keyStr = "";
		List<String> listIndex = new java.util.ArrayList<String>();
		for(final XmlElement field:tbl.subElements)
		{
			if ("field".equals(field.qName)){
				String sql1 = getFieldSql(field,tableName,1);
				if (!"".equals(sql1))
					sql += sql1+", ";
				String primarykey = field.getAttribute("primarykey");
				if ("Y".equals(primarykey)){
					String fieldname = field.getAttribute("name");
					if (!"".equals(keyStr))
						keyStr += ",";
					if (dbType == 3)
						keyStr += "`"+fieldname+"`";
					else
						keyStr += fieldname;
				}
			}else if ("index".equals(field.qName)){
				String mysql = field.getAttribute("mysql");
				if (dbType != 3 || "Y".equals(mysql)){
					String index = getIndexSql(field,tableName);
					if (!"".equals(index))
						listIndex.add(index);
				}
			}
		}
		
		if ("".equals(keyStr)){
			return "表： "+tableName+" 未指定主键，不能建表！";
		}
		if (dbType == 1){
			sql += "CONSTRAINT  "+tableName+"_pk PRIMARY KEY ("+keyStr+") ";
			sql += ")";
		}else if (dbType == 2){
			sql += "CONSTRAINT  "+tableName+"_pk PRIMARY KEY ("+keyStr+") ";
			sql += "USING INDEX TABLESPACE "+indspace;
			sql += ") TABLESPACE "+tblspace;
		}else{
			sql += "PRIMARY KEY ("+keyStr+")";
			String charset = tbl.getAttribute("charset");
			if (StrUtil.isnull(charset))
				charset = "utf8";
			String collate = tbl.getAttribute("collate");
			if (StrUtil.isnull(collate)){
				//collate = "utf8_default collation";
				collate = "";
			}else{
				collate = " COLLATE "+ collate;
			}
			sql += ") ENGINE=InnoDB CHARACTER SET "+charset+" DEFAULT CHARSET="+charset+collate;
		}
		
		boolean bCreate = true;
		try{
			tableMapper.updateSql(sql);
		}catch(BadSqlGrammarException ex){
			msg += "<br>"+sql+"<br>"+ex.getMessage();
		}catch (Exception ex){
			bCreate = false;
			msg += "<br>"+sql+"<br>"+ex.toString();
		}
		//索引
		if (bCreate){
			for (int i = 0; i < listIndex.size(); i++){
				String sql1 = listIndex.get(i);
				try{
					tableMapper.updateSql(sql1);
				}catch(BadSqlGrammarException ex){
					msg += "<br>"+sql1+"<br>"+ex.getMessage();
				}catch (Exception ex){
					msg += "<br>"+sql1+"<br>"+ex.toString();
				}
			}
		}
		return msg;
	}
	
	/**
	 * 更新字段
	 * @param tbl
	 * @param tableName
	 * @return
	 */
	private String alterTable(XmlElement tbl, String tableName){
		String msg = "";
		for(final XmlElement field:tbl.subElements)
		{
			if ("field".equals(field.qName)){
				String sql = getFieldSql(field,tableName,2);
				if ("".equals(sql))
					continue;
				try{
					tableMapper.updateSql(sql);
				}catch(BadSqlGrammarException ex){
					msg += "<br>"+sql+"<br>"+ex.getMessage();
				}catch (Exception ex){
					msg += "<br>"+ex.toString();
				}
			}else if ("index".equals(field.qName)){
				String mysql = field.getAttribute("mysql");
				if ("Y".equals(mysql)){
					String sql = getIndexSql(field,tableName);
					if ("".equals(sql))
						continue;
					try{
						tableMapper.updateSql(sql);
					}catch(BadSqlGrammarException ex){
						msg += "<br>"+sql+"<br>"+ex.getMessage();
					}catch (Exception ex){
						msg += "<br>"+ex.toString();
					}
				}
			}
		}
		return msg;
	}
	
	/**
	 * 获取语句
	 * @param field
	 * @param tableName
	 * @param options
	 * @return
	 */
	private String getFieldSql(XmlElement field,String tableName,int options){
		String fieldname = field.getAttribute("name");
		String type = field.getAttribute("type").toUpperCase();
		String size = field.getAttribute("size");
		String decimal = field.getAttribute("decimal");
		String unsigned = field.getAttribute("unsigned");
		String drop = field.getAttribute("drop");
		String primarykey = field.getAttribute("primarykey");
		String isnull = field.getAttribute("isnull");
		String default1 = field.getAttribute("default");
		String comment = field.getAttribute("comment");
		String collate = field.getAttribute("collate");
		if (StrUtil.isnull(collate)){
			//collate = "utf8_default collation";
			collate = "COLLATE utf8_bin";
		}else{
			collate = " COLLATE "+ collate;
		}
		
		if (unsigned != null)
			unsigned = unsigned.toUpperCase();
		
		if (drop == null)
			drop = "N";
		else
			drop = drop.toUpperCase();
		
		String sql = " "+collate+" COMMENT '"+comment+"'";
		if (dbType != 3)
			sql = "";
		
		if (default1 != null)
			sql = " DEFAULT "+default1+sql;

		if ("Y".equals(isnull) || "y".equals(isnull) || "Y".equals(primarykey)){
			isnull = "Y";
			if (dbType == 2)
				sql += " NOT NULL ";
			else
				sql = " NOT NULL "+ sql;
		}else{
			isnull = "N";
			if (dbType == 2)
				sql += " NULL ";
			else if (dbType != 3)
				sql = " NULL "+ sql;
		}
		
		if (dbType == 1){
			if (StrUtil.isStrIn("CHAR,VARCHAR", type)){
				sql = type+"("+size+")"+ sql;
			}else if (StrUtil.isStrIn("YEAR,TIMESTAMP", type)){
				sql = "VARCHAR(16)"+ sql;
			}else if (StrUtil.isStrIn("DATE,TIME,DATETIME", type)){
				sql = "DATETIME" + sql;
			}else if (StrUtil.isStrIn("CLOB,TINYTEXT,TEXT,MEDIUMTEXT,LONGTEXT", type)){
				sql = "TEXT" + sql;
			}else if (StrUtil.isStrIn("TINYBLOB,BLOB,MEDIUMBLOB,LONGBLOB", type)){
				sql = "IMAGE" + sql;
			}else if (StrUtil.isStrIn("FLOAT,SMALLINT,INT,INTEGER,TINYINT,MEDIUMINT,BIGINT", type)){
				sql = type + sql;
			}else if (StrUtil.isStrIn("DOUBLE,REAL,DECIMAL,NUMERIC", type)){
				if (decimal == null)
					decimal = "0";
				sql = "NUMERIC("+size+","+decimal+")"+ sql;
			}else{
				throw new RuntimeException("字段类型错误:"+tableName+":"+fieldname+":" + type);
			}
		}else if (dbType == 2){
			if (StrUtil.isStrIn("CHAR", type)){
				sql = type+"("+size+")"+ sql;
			}else if (StrUtil.isStrIn("VARCHAR", type)){
				sql = "VARCHAR2("+size+")"+ sql;
			}else if (StrUtil.isStrIn("YEAR,TIMESTAMP", type)){
				sql = "VARCHAR2(16)"+ sql;
			}else if (StrUtil.isStrIn("DATE,TIME,DATETIME", type)){
				sql = "DATE" + sql;
			}else if (StrUtil.isStrIn("FLOAT", type)){
				sql = type + sql;
			}else if (StrUtil.isStrIn("TINYTEXT,VARCHAR2", type)){
				sql = "VARCHAR2(4000)" + sql;
			}else if (StrUtil.isStrIn("BLOB,TINYBLOB,MEDIUMBLOB", type)){
				sql = "BLOB" + sql;
			}else if (StrUtil.isStrIn("CLOB,TEXT,MEDIUMTEXT", type)){
				sql = "CLOB" + sql;
			}else if (StrUtil.isStrIn("LONGTEXT,LONGBLOB", type)){
				sql = "LONG RAW" + sql;
			}else if (StrUtil.isStrIn("TINYINT,SMALLINT,DOUBLE,REAL,DECIMAL,NUMERIC,MEDIUMINT,INT,INTEGER,BIGINT", type)){
				if (decimal == null)
					decimal = "0";
				sql = "NUMERIC("+size+","+decimal+")"+ sql;
			}else{
				throw new RuntimeException("字段类型错误:"+tableName+":"+fieldname+":" + type);
			}
		}else{
			if (StrUtil.isStrIn("TIMESTAMP,YEAR,CHAR,VARCHAR", type)){    
				sql = type+"("+size+")"+ sql;
			}else if (StrUtil.isStrIn("DATE,TIME,DATETIME,TINYTEXT,TINYBLOB,TEXT,BLOB,MEDIUMTEXT,MEDIUMBLOB,LONGTEXT,LONGBLOB", type)){
				sql = type + sql;
			}else if (StrUtil.isStrIn("TINYINT,SMALLINT,MEDIUMINT,INT,INTEGER,BIGINT", type)){
				sql = type + "("+size+")" + ("Y".equals(unsigned)?" unsigned ":"") + sql;
			}else if (StrUtil.isStrIn("FLOAT,DOUBLE,REAL,DECIMAL,NUMERIC", type)){
				if (decimal == null)
					decimal = "0";
				sql = type+"("+size+","+decimal+")"+ sql;
			}else{
				throw new RuntimeException("字段类型错误:"+tableName+":"+fieldname+":" + type);
			}
		}
		
		if (options == 1){  //新建
			if ("N".equals(drop)){
				if (dbType == 3)
					sql = "`"+fieldname+"` "+sql;
				else
					sql = fieldname+" "+sql;
			}else
				sql = "";
		}else if (options == 2){  //增加、修改
			List<Map> list = null;
			if (dbType == 1){
				list = tableMapper.selectField1(tableName, fieldname);
			}else if (dbType == 2){
				try{
					list = tableMapper.selectField2(tableName.toUpperCase(), fieldname.toUpperCase());
				}catch (Exception ex){
					return "";
				}
			}else{ 
				list = tableMapper.selectField(getDataBase(), tableName, fieldname);
			}
			if (list != null && list.size() == 0){  //增加
				if ("Y".equals(drop))
					sql = "";
				else{
					if (dbType == 1)
						sql = "alter table "+tableName+" add " + fieldname + " " + sql;
					else if (dbType == 2)
						sql = "alter table "+tableName+" add (" + fieldname + " " + sql+")";
					else
						sql = "alter table "+tableName+" add " + fieldname + " " + sql;
				}
			}else{   //修改
				if ("Y".equals(drop)){
					if (dbType == 1)
						sql = "ALTER TABLE "+tableName+" DROP COLUMN "+fieldname;
					else if (dbType == 2)
						sql = "ALTER TABLE "+tableName+" DROP ("+fieldname+")";
					else
						sql = "ALTER TABLE "+tableName+" DROP "+fieldname;
				}else{
					Map map = list.get(0);
					boolean bModify = true;
					if (dbType == 1){
						String type2 = ""+map.get("DATA_TYPE");
						type2 = type2.toUpperCase();
						String size2 = ""+map.get("DATA_LENGTH");
						String decimal2 = ""+map.get("DATA_SCALE");
						String isnull2 = ""+map.get("NULLABLE");
						if ("0".equals(isnull2))
							isnull2 = "Y";
						else
							isnull2 = "N";
						
						if (!isnull.equals(isnull2)){
							bModify = true;
						}else if (StrUtil.isStrIn("CHAR,VARCHAR", type)){
							if (size.equals(size2))
								bModify = false;
						}else if (StrUtil.isStrIn("YEAR,TIMESTAMP", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("DATE,TIME,DATETIME", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("CLOB,TINYTEXT,TEXT,MEDIUMTEXT,LONGTEXT", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("TINYBLOB,BLOB,MEDIUMBLOB,LONGBLOB", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("FLOAT,SMALLINT,INT,INTEGER,TINYINT,MEDIUMINT,BIGINT", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("DOUBLE,REAL,DECIMAL,NUMERIC", type)){
							if (size.equals(size2) && decimal.equals(decimal2))
								bModify = false;
						}
					}else if (dbType == 2){
						//DATA_TYPE,DATA_LENGTH,DATA_SCALE,NULLABLE
						String type2 = ""+map.get("DATA_TYPE");
						type2 = type2.toUpperCase();
						String size2 = ""+map.get("DATA_LENGTH");
						String decimal2 = ""+map.get("DATA_SCALE");
						String isnull2 = ""+map.get("NULLABLE");
						if (isnull.equals(isnull2)){
							sql = "alter table "+tableName+" modify (" + fieldname + (isnull.equals("Y")?" NOT NULL":" NULL")+")";
							return sql;
						}else{
							sql = sql.replace(" NOT NULL ", " ");
							sql = sql.replace(" NULL ", " ");
						}
						
						if (StrUtil.isStrIn("CHAR", type)){
							if (type.equals(type2) && size.equals(size2))
								bModify = false;
						}else if (StrUtil.isStrIn("VARCHAR", type)){
							if ("VARCHAR2".equals(type2) && size.equals(size2))
								bModify = false;
						}else if (StrUtil.isStrIn("YEAR,TIMESTAMP", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("DATE,TIME,DATETIME", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("FLOAT", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("TINYTEXT,VARCHAR2", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("BLOB,TINYBLOB,MEDIUMBLOB", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("CLOB,TEXT,MEDIUMTEXT", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("LONGTEXT,LONGBLOB", type)){
							bModify = false;
						}else if (StrUtil.isStrIn("TINYINT,SMALLINT,DOUBLE,REAL,DECIMAL,NUMERIC,MEDIUMINT,INT,INTEGER,BIGINT", type)){
							if (size.equals(size2) && decimal.equals(decimal2))
								bModify = false;
						}
					}else if (dbType == 3){
						String type2 = ""+map.get("DATA_TYPE");
						type2 = type2.toUpperCase();
						String size1 = ""+map.get("CHARACTER_MAXIMUM_LENGTH");
						String size2 = ""+map.get("NUMERIC_PRECISION");
						String decimal2 = ""+map.get("NUMERIC_SCALE");
						String isnull2 = ""+map.get("IS_NULLABLE");
						/*
						if ("Y".equals(isnull) && "NO".equals(isnull2) ){
							//由空到非空，在数据库中个性，因为只有数据为空，就无法完成修改
						}else 
							*/
						if ("N".equals(isnull) && "YES".equals(isnull2) || "Y".equals(isnull) && "NO".equals(isnull2)){
							bModify = true;
						}else if (StrUtil.isStrIn("DATE,TIME,DATETIME,TINYTEXT,TINYBLOB,TEXT,BLOB,MEDIUMTEXT,MEDIUMBLOB,LONGTEXT,LONGBLOB,TINYINT,SMALLINT,MEDIUMINT,INT,INTEGER,BIGINT", type)){
							if (type.equals(type2))
								bModify = false;
						}else if (StrUtil.isStrIn("TIMESTAMP,YEAR,CHAR,VARCHAR", type)){
							if (type.equals(type2) && size.equals(size1))
								bModify = false;
						}else if (StrUtil.isStrIn("FLOAT,DOUBLE,REAL,DECIMAL,NUMERIC", type)){
							if (type.equals(type2) && size.equals(size2) && decimal.equals(decimal2))
								bModify = false;
						}
					}
					if (bModify){
						if (dbType == 1)
							sql = "alter table "+tableName+" ALTER COLUMN " + fieldname + " " + sql;
						else if (dbType == 2)
							sql = "alter table "+tableName+" modify (" + fieldname + " " + sql+")";
						else
							sql = "alter table "+tableName+" modify " + fieldname + " " + sql;
					}else{
						sql = "";
					}
				}
			}
		}
		return sql;
	}

	/**
	 * 获取索引语句
	 * @param field
	 * @param tableName
	 * @return
	 */
	private String getIndexSql(XmlElement field,String tableName){
		String indexname = field.getAttribute("name");
		String fields = field.getAttribute("fields");
		String only = field.getAttribute("only");
		String drop = field.getAttribute("drop");
		
		if (only!=null)
			only = only.toUpperCase();
		
		if (drop == null)
			drop = "N";
		else
			drop = drop.toUpperCase();
		
		String sql = "";
		List<Map> list = null;
		if (dbType == 1){
			list = tableMapper.selectIndex1(tableName, indexname);
		}else if (dbType == 2){
			list = tableMapper.selectIndex2(tableName.toUpperCase(), indexname.toUpperCase());
		}else{
			list = tableMapper.selectIndex(getDataBase(), tableName, indexname);
		}
		if (list.size() == 0){
			if (!"Y".equals(drop))
				sql = "CREATE "+("Y".equals(only)?"UNIQUE":"")+" INDEX "+indexname+" ON "+tableName+" ("+fields+")";
		}else if ("Y".equals(drop)){
			sql = "DROP INDEX "+indexname+" ON " + tableName;
		}
		return sql;
	}
	
	/**
	 SQL Sever
CREATE TABLE bacode2
(
	bcode VARCHAR(16) not null,
	acode VARCHAR(16) not null,
	codelevl VARCHAR(26) null,
	flags SMALLINT null,      
	property  INTEGER null,
	top MONEY null,
	a TINYINT null,
	b BIGINT null,
	c FLOAT null,
	d NUMERIC(19,6) null,
	e DECIMAL(19,6) null,
	f CHAR(8) null,
	h TEXT null,   LONGVARCHAR
	j DATETIME null,
	k IMAGE null,   BLOB
	t TEXT null,    CLOB
	r IMAGE null,   LONGVARBINARY
	CONSTRAINT  bacode2_pk PRIMARY KEY(bcode,acode)
)

oracle

CREATE TABLE bacode2
(
	bcode VARCHAR2(16) not null,
	acode VARCHAR2(16) not null,
	codelevl VARCHAR2(26) null,
	flags DECIMAL(5,0) null,        SMALLINT
	property DECIMAL(10,0) null,      INTEGER
	rownum DECIMAL(19,4) null,    MONEY
	a DECIMAL(3,0) null,     TINYINT
	b DECIMAL(19,0) null,   BIGINT
	c FLOAT null,   FLOAT
	d NUMERIC(19,6) null,   NUMERIC
	e DECIMAL(19,6) null,  DECIMAL
	f CHAR(8) null,
	h VarChar2(4000) null,    LONGVARCHAR
	j DATE null,    
	k BLOB null,  BLOB
	t CLOB null,  CLOB
	r LONG RAW null, LONGVARBINARY
	CONSTRAINT  bacode2_pk PRIMARY KEY(bcode,acode) USING INDEX TABLESPACE SYSINDX_TBS
) TABLESPACE SYSDATA_TBS

 
	 */
		
}
