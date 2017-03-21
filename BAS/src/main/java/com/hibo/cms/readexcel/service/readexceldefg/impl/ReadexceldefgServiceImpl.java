package com.hibo.cms.readexcel.service.readexceldefg.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.readexcel.dao.ReadexceldefgMapper;
import com.hibo.cms.readexcel.model.Readexceldefg;
import com.hibo.cms.readexcel.service.readexceldefg.IReadexceldefgService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午5:50:43</p>
 * <p>类全名：com.hibo.cms.importexcel.service.readexceldefg.impl.ReadexceldefgServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class ReadexceldefgServiceImpl implements IReadexceldefgService {

	@Autowired
	private ReadexceldefgMapper readexceldefgMapper;
	
	@Override
	public int delete(String fldname, String rxcode) {
		return readexceldefgMapper.delete(fldname, rxcode);
	}

	@Override
	public int insert(Readexceldefg readexceldefg) {
		return readexceldefgMapper.insert(readexceldefg);
	}

	@Override
	public Readexceldefg select(String fldname, String rxcode) {
		return readexceldefgMapper.select(fldname, rxcode);
	}

	@Override
	public int update(Readexceldefg readexceldefg) {
		return readexceldefgMapper.update(readexceldefg);
	}

	@Override
	public List<Readexceldefg> selectAll() {
		return readexceldefgMapper.selectAll();
	}

	@Override
	public List<Readexceldefg> selectByRxcode(String rxcode) {
		return readexceldefgMapper.selectByRxcode(rxcode);
	}

	@Override
	public int updateRxcode(String rxcode, String oldrxcode) {
		return readexceldefgMapper.updateRxcode(rxcode, oldrxcode);
	}

	@Override
	public int deleteByRxcode(String rxcode) {
		return readexceldefgMapper.deleteByRxcode(rxcode);
	}

	@Override
	public int updateByOldKey(Readexceldefg readexceldefg, String oldfldname, String oldrxcode) {
		return readexceldefgMapper.updateByOldKey(readexceldefg, oldfldname, oldrxcode);
	}

}
