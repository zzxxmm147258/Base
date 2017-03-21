package com.hibo.bas.sn.dao;

import com.hibo.bas.sn.model.Sn;

public interface SnMapper {
	// int deleteByPrimaryKey(String id);
	//
	// int insert(Sn record);
	//
	// int insertSelective(Sn record);
	//
	// Sn selectByPrimaryKey(String id);
	//
	int updateByPrimaryKeySelective(Sn record);
	//
	// int updateByPrimaryKey(Sn record);

	Sn selectByType(int type);
}