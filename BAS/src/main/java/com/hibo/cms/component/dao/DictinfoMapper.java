package com.hibo.cms.component.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.component.model.Dictinfo;

public interface DictinfoMapper {
	List<Dictinfo> selectAll();
	
	List<Dictinfo> selectByDictid(@Param("dictid")int dictid,@Param("currentSalesStatus")String currentSalesStatus);
	
	List<Dictinfo> selectAllByDictid(int dictid);

	int delete(@Param("dictid")String dictid,@Param("code")String code);
	
	int insert(Dictinfo dictinfo);
	
	int delectByDictid(int dictid);
	
	
	int updateByOldKey(@Param("oldCode") String oldCode,@Param("dictinfo") Dictinfo dictinfo);
	
	int updateDictidByDictid(@Param("newDictid") int newDictid,@Param("oldDictid") int oldDictid);
	
	Dictinfo selectByDictidCode(@Param("dictid")String dictid,@Param("code")String code);
}
