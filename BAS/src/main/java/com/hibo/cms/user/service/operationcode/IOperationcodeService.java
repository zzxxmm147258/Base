package com.hibo.cms.user.service.operationcode;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.Operationcode;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月14日 下午4:37:49</p>
 * <p>类全名：com.hibo.cms.user.service.operationcode.IOperationcodeService</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public interface IOperationcodeService {
	
	/**
	 * 查出所有(不分页)
	 * @return
	 */
	List<Operationcode> selectAll();

	/**
	 * 查出所有(分页)
	 * @param pageBounds
	 * @return
	 */
	public PageList<Operationcode> selectAll(PageBounds pageBounds);

	/**
	 * 插入一条
	 * @param operationcode
	 * @return
	 */
	public int insert(Operationcode operationcode);
	
	/**
	 * 根据id删除这一条
	 * @param operaid
	 * @return
	 */
	public int delete(String operaid);
	
	/**
	 * 更新
	 * @param operationcode
	 * @param oldoperaid
	 * @return
	 */
	public int update(Operationcode operationcode,String oldoperaid);
	
	/**
	 * 根据ID查询
	 * @param operaid
	 * @return
	 */
	public Operationcode select(String operaid);
	
	/**
	 * 模糊查询
	 * @param operationcode
	 * @return
	 */
	List<Operationcode> selectFuzzy(Operationcode operationcode);
	
}
