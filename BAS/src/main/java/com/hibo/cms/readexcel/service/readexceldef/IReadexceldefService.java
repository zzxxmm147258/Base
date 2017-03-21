package com.hibo.cms.readexcel.service.readexceldef;

import java.util.List;

import com.hibo.cms.readexcel.model.Readexceldef;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月19日 下午5:48:28</p>
 * <p>类全名：com.hibo.cms.importexcel.service.readexceldef.IReadexceldefService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IReadexceldefService {

	/**
	 * 根据rxcode删除此条记录
	 * @param rxcode
	 * @return
	 */
	int delete(String rxcode);

    /**
     * 插入一条数据
     * @param record
     * @return
     */
    int insert(Readexceldef readexceldef);

    /**
     * 根据rxcode查出此条数据
     * @param rxcode
     * @return
     */
    Readexceldef select(String rxcode);

    /**
     * 更新此条数据
     * @param record
     * @return
     */
    int update(Readexceldef readexceldef);
    
    /**
     * 查出所有
     * @return
     */
    List<Readexceldef> selectAll();
    
    /**
     * 跟据rxcode修改此条数据
     * @param readexceldef
     * @param rxcode
     * @return
     */
    int updateByOldrxcode(Readexceldef readexceldef,String oldrxcode);
	
}
