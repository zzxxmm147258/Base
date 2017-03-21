package com.hibo.bas.mybaits.dialect;

import com.github.miemiedev.mybatis.paginator.dialect.Dialect;
import com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect;
import com.github.miemiedev.mybatis.paginator.dialect.OracleDialect;
import com.github.miemiedev.mybatis.paginator.dialect.SQLServer2005Dialect;
import com.hibo.bas.dbutil.DataSourceUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年8月19日 下午7:47:09</p>
 * <p>类全名：com.hibo.bas.mybaits.dialect.AutoDialectBas</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class AutoDialectBas extends Dialect{
	

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		int dbType = DataSourceUtil.getDbType();
		if(dbType==1){//sqlserver
			sql = new SQLServer2005Dialect().getLimitString(sql, offset, offsetPlaceholder, limit, limitPlaceholder);
		}else if(dbType==2){//oracle
			sql = new OracleDialect().getLimitString(sql, offset, offsetPlaceholder, limit, limitPlaceholder);
		}else if(dbType==3){//mysql
			sql = new MySQLDialect().getLimitString(sql, offset, offsetPlaceholder, limit, limitPlaceholder);
		}
		return sql;
	}

}
