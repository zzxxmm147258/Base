package com.hibo.cms.readexcel.service.readexceldef.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibo.cms.readexcel.dao.ReadexceldefMapper;
import com.hibo.cms.readexcel.dao.ReadexceldefgMapper;
import com.hibo.cms.readexcel.model.Readexceldef;
import com.hibo.cms.readexcel.service.readexceldef.IReadexceldefService;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午5:48:56</p>
 * <p>类全名：com.hibo.cms.importexcel.service.readexceldef.ReadexceldefServiceImpl</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
@Service
public class ReadexceldefServiceImpl implements IReadexceldefService {
	
	@Autowired
	private ReadexceldefMapper readexceldefMapper;
	@Autowired
	private ReadexceldefgMapper readexceldefgMapper;
	
	@Transactional
	@Override
	public int delete(String rxcode) {
		readexceldefgMapper.deleteByRxcode(rxcode);
		return readexceldefMapper.delete(rxcode);
	}

	@Override
	public int insert(Readexceldef readexceldef) {
		return readexceldefMapper.insert(readexceldef);
	}

	@Override
	public Readexceldef select(String rxcode) {
		return readexceldefMapper.select(rxcode);
	}

	@Override
	public int update(Readexceldef readexceldef) {
		return readexceldefMapper.update(readexceldef);
	}

	@Override
	public List<Readexceldef> selectAll() {
		return readexceldefMapper.selectAll();
	}

	@Transactional
	@Override
	public int updateByOldrxcode(Readexceldef readexceldef, String oldrxcode) {
		if(!oldrxcode.equals(readexceldef.getRxcode())){
			readexceldefgMapper.updateRxcode(readexceldef.getRxcode(), oldrxcode);
		}
		return readexceldefMapper.updateByOldrxcode(readexceldef, oldrxcode);
	}

}
