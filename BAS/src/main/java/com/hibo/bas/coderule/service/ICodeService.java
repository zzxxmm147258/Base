package com.hibo.bas.coderule.service;

import java.util.List;
import java.util.Map;

import com.hibo.bas.coderule.model.CodeModel;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月21日 上午10:06:25</p>
 * <p>类全名：com.hibo.bas.coderule.service.ICodeService</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public interface ICodeService {
	List<Map<String,Object>> selectCodeList(String sql);
	public String getServiceCode(CodeModel codeModel,int num) throws Exception;
}
