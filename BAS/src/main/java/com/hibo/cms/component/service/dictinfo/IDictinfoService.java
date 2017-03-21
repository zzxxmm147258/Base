package com.hibo.cms.component.service.dictinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.component.model.Dictinfo;

public interface IDictinfoService {
	
	/**
	 * 查出所有子表数据据
	 * @return
	 */
	List<Dictinfo> selectAll();
	
	/**
	 * 根据主表dictid查出对应的子表数据
	 * @param dictid
	 * @param currentSalesStatus : 从哪个开始；若想全查出则为null
	 * @return
	 */
	List<Dictinfo> selectByDictid(int dictid,String currentSalesStatus);
	
	/**
	 * <p>功能：根据主表dictid查出对应的子表数据 </p>
	 * <p>作者：吕康</p>
	 * <p>创建日期：2015年11月11日 上午9:41:51</p>
	 * @param dictid
	 * @return
	 */
	List<Dictinfo> selectByDictid(int dictid);

	/**
	 * 根据Key删除此条数据
	 * @param dictid
	 * @param code
	 * @return
	 */
	int delete(String dictid,String code);
	
	/**
	 * 根据dictid删除子表对应数据
	 * @param dictid
	 * @return
	 */
	int delectByDictid(int dictid);
	
	/**
	 * 插入数据
	 * @param dictinfo
	 * @return
	 */
	int insert(Dictinfo dictinfo);
	
	/**
	 * 根据oldkey修改对应数据
	 * @param oldCode
	 * @param dictinfo
	 * @return
	 */
	int updateByOldKey(String oldCode,Dictinfo dictinfo);
	
	/**
	 * 主表修改dictid时相应子表也跟着修改
	 * @param newDictid
	 * @param oldDictid
	 * @return
	 */
	int updateDictidByDictid(int newDictid,int oldDictid);
	
	Dictinfo selectByDictidCode(String dictid,String code);
}
