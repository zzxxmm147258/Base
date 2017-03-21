package com.hibo.cms.component.service.dictinfo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.component.dao.DictinfoMapper;
import com.hibo.cms.component.model.Dictinfo;
import com.hibo.cms.component.service.dictinfo.IDictinfoService;

@Service
public class DictinfoServiceImpl implements IDictinfoService {

	@Autowired
	private DictinfoMapper dictinfoMapper;
	
	@Override
	public List<Dictinfo> selectAll() {
		return dictinfoMapper.selectAll();
	}

	@Override
	public List<Dictinfo> selectByDictid(int dictid,String currentSalesStatus) {
		return dictinfoMapper.selectByDictid(dictid,currentSalesStatus);
	}

	@Override
	public int insert(Dictinfo dictinfo) {
		return dictinfoMapper.insert(dictinfo);
	}

	@Override
	public int delectByDictid(int dictid) {
		return dictinfoMapper.delectByDictid(dictid);
	}

	@Override
	public int updateDictidByDictid(int newDictid, int oldDictid) {
		return dictinfoMapper.updateDictidByDictid(newDictid, oldDictid);
	}

	@Override
	public int updateByOldKey(String oldCode, Dictinfo dictinfo) {
		return dictinfoMapper.updateByOldKey(oldCode, dictinfo);
	}

	@Override
	public int delete(String dictid, String code) {
		return dictinfoMapper.delete(dictid, code);
	}

	@Override
	public List<Dictinfo> selectByDictid(int dictid) {
		return dictinfoMapper.selectAllByDictid(dictid);
	}

	@Override
	public Dictinfo selectByDictidCode(String dictid, String code) {
		return dictinfoMapper.selectByDictidCode(dictid, code);
	}

}
