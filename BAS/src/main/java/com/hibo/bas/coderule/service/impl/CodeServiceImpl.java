package com.hibo.bas.coderule.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hibo.bas.coderule.dao.CodeMapper;
import com.hibo.bas.coderule.model.CodeModel;
import com.hibo.bas.coderule.service.ICodeService;
import com.hibo.bas.coderule.util.CodeUtil;
import com.hibo.bas.dbutil.DataSource;
import com.hibo.bas.util.DataConfig;
import com.hibo.bas.util.StrUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月21日 上午11:35:39</p>
 * <p>类全名：com.hibo.bas.coderule.service.impl.CodeServiceImpl</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Service
public class CodeServiceImpl implements ICodeService {
	@Resource
	private CodeMapper codeMapper;
	Random random = new Random();
	String db = DataConfig.getConfig("BAS.CODE.DATABASE");
	@Override
	public List<Map<String, Object>> selectCodeList(String sql) {
		return codeMapper.selectCodeList(sql);
	}
	@Override
	public String getServiceCode(CodeModel codeModel, int num) throws Exception {
		if(!StrUtil.isnull(db)){
			DataSource.setDataSource(db);
		}
		String newCode = null;
		String key = codeModel.getTable() + "_" + codeModel.getFild();
		try {
			int k = codeMapper.selectCount(key);
			if (k > 0) {
				k = codeMapper.updateCodeTrue(key, true);
			} else {
				k = codeMapper.insertCode(key, key+"编码锁", true);
			}
			if (k > 0) {
				newCode = CodeUtil.getNewCode(codeModel);
			} else {
				if (num < 9) {
					num++;
					Thread.sleep(random.nextInt(10));
					newCode = this.getServiceCode(codeModel, num);
				}
			}
		} finally {
			codeMapper.updateCode(key, false);
		}
		return newCode;
	}

}
