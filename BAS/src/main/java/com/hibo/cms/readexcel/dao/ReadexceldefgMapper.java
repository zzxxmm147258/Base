package com.hibo.cms.readexcel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.readexcel.model.Readexceldefg;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月21日 下午3:08:29</p>
 * <p>类全名：com.hibo.cms.readexcel.dao.ReadexceldefgMapper</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface ReadexceldefgMapper {
    int delete(@Param("fldname")String fldname,@Param("rxcode")String rxcode);

    int insert(Readexceldefg readexceldefg);

    Readexceldefg select(@Param("fldname")String fldname,@Param("rxcode")String rxcode);
    
    List<Readexceldefg> selectAll();

    List<Readexceldefg> selectByRxcode(String rxcode);

    int update(Readexceldefg readexceldefg);
    
    int updateRxcode(@Param("rxcode")String rxcode,@Param("oldrxcode")String oldrxcode);
    
    int updateByOldKey(@Param("rdexdefg")Readexceldefg readexceldefg,@Param("oldfldname")String oldfldname,@Param("oldrxcode")String oldrxcode);
    
    int deleteByRxcode(String rxcode);
    

}