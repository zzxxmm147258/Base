package com.hibo.cms.config.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hibo.bas.dbutil.DataSource;
import com.hibo.bas.dbutil.DataSourceUtil;
import com.hibo.cms.config.dao.SysoptionsMapper;
import com.hibo.cms.config.model.Sysoptions;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月13日 下午3:03:43</p>
 * <p>类全名：com.hibo.cms.config.util.SysConfigUtil</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
@Component
public class SysConfigUtil {
	
	private static Map<String, Sysoptions> SysConfigMap = new HashMap<String, Sysoptions>();
	
	private static SysoptionsMapper sysoptionsMapper = null;
	
	@Resource
	public void setSysoptionsMapper(SysoptionsMapper sysoptionsMapper) {
		SysConfigUtil.sysoptionsMapper = sysoptionsMapper;
	}

	/**
	 * @param conno 分类编号
	 * @param sysid 系统号
	 * @return
	 */
	/*
	public static Sysoptions getSysConfig(String conno, String sysid) {
		if(null==sysid){
			throw new RuntimeException("系统号不能为空");
		}
		String key = sysid+":"+conno;
		if(!SysConfigMap.containsKey(key)){
			DataSource.setDataSource(DataSourceUtil.getDataSource());
			Sysoptions config = sysoptionsMapper.selectByconno(conno);
			if(null!=config){
				SysConfigMap.put(key, config);
			}
		}
		return SysConfigMap.get(key);
	}
	*/
	public static Sysoptions getSysOtions(String code) {
		return getSysOtions(null, code);
	}
	
	public static Sysoptions getSysOtions(String dataSource, String code) {
		if(null==code){
			throw new RuntimeException("编号不能为空");
		}
		if (dataSource == null)
			dataSource = DataSourceUtil.getDataSource();
		Sysoptions options = SysConfigMap.get(dataSource+"_"+code);
		if(options == null){
			DataSource.setDataSource(dataSource);
			options = sysoptionsMapper.selectByconno(code);
			if (options == null)
				options = new Sysoptions();
			SysConfigMap.put(dataSource+"_"+code, options);
		}
		return options;
	}
	
	public static void setSysConfigNull(){
		SysConfigMap.clear();
	}
	
	public static String getOption1(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption1();
	}
	
	public static String getOption2(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption2();
	}
	
	public static String getOption3(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption3();
	}
	
	public static String getOption4(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption4();
	}
	
	public static String getOption5(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption5();
	}
	
	public static String getOption6(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption6();
	}
	
	public static String getOption7(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption7();
	}
	
	public static String getOption8(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption8();
	}
	
	public static String getOption9(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption9();
	}
	
	public static String getOption10(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption10();
	}
	
	public static String getOption11(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption11();
	}
	
	public static String getOption12(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption12();
	}
	
	public static String getOption13(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption13();
	}
	
	public static String getOption14(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption14();
	}
	
	public static String getOption15(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption15();
	}
	
	public static String getOption16(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption16();
	}
	
	public static String getOption17(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption17();
	}
	
	public static String getOption18(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption18();
	}
	
	public static String getOption19(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getOption19();
	}
	
	public static int getIntOption1(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getI_option1();
	}
	
	public static int getIntOption2(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getI_option2();
	}
	
	public static java.math.BigDecimal getBigOption1(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getN_option1();
	}
	
	public static java.math.BigDecimal getBigOption2(String dataSource, String code){
		Sysoptions sysOptions = getSysOtions(dataSource, code);
		return sysOptions.getN_option2();
	}
}
