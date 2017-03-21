package com.hibo.cms.component.service.dictdef;

import java.util.List;

import com.hibo.cms.component.model.Dictdef;

public interface IDictdefService {
	List<Dictdef> selectAll();
	
	int insert(Dictdef dictdef);
	
	int deleteByDictid(int dictid);
	
	int updateByDictid(int oldDictid,Dictdef dictdef);
	
	Dictdef getById(int dictid);
}
