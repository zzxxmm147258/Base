package com.hibo.cms.readexcel.service.readexceldefg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.readexcel.model.Readexceldefg;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午5:49:54</p>
 * <p>类全名：com.hibo.cms.importexcel.service.readexceldefg.IReadexceldefgService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IReadexceldefgService {

	/**
	 * 根据fldname和rxcode删除此条数据
	 * @param fldname
	 * @param rxcode
	 * @return
	 */
	int delete(String fldname,String rxcode);

    /**
     * 插入一条数据
     * @param record
     * @return
     */
    int insert(Readexceldefg readexceldefg);

    /**
     * 根据fldname和rxcode查出此条数据
     * @param fldname
     * @param rxcode
     * @return
     */
    Readexceldefg select(String fldname,String rxcode);

    /**
     * 更新此条数据
     * @param record
     * @return
     */
    int update(Readexceldefg readexceldefg);
    
    /**
     * 查出所有
     * @return
     */
    List<Readexceldefg> selectAll();
    
    /**
     * 根据rxcode查出对应数据
     * @param rxcode
     * @return
     */
    List<Readexceldefg> selectByRxcode(String rxcode);
    
    /**
     * 修改rxcode
     * @param rxcode
     * @param oldrxcode
     * @return
     */
    int updateRxcode(String rxcode,String oldrxcode);
	
    /**
     * 根据rxcode删除对应子数据
     * @param rxcode
     * @return
     */
    int deleteByRxcode(String rxcode);
    
    /**
     * 根据oldKey修改此条数据
     * @param readexceldefg
     * @param oldfldname
     * @param oldrxcode
     * @return
     */
    int updateByOldKey(Readexceldefg readexceldefg,String oldfldname,String oldrxcode);
    
    
}
