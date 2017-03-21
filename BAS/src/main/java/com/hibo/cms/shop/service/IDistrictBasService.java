package com.hibo.cms.shop.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.shop.model.DistrictBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 下午2:34:09</p>
 * <p>类全名：com.hibo.cms.shop.service.IDistrictBasServices</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IDistrictBasService {

	    /**
	     * <p>功能：根据商圈主键district删除数据<p>
	     * <p>创建日期：2015年11月19日 下午2:53:45<p>
	     * <p>作者：曾小明<p>
	     * @param district
	     * @return
	     */
	    int deleteByPrimaryKey(String district);

	    /**
	     * <p>功能：不判空新增商圈数据<p>
	     * <p>创建日期：2015年11月19日 下午2:54:37<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int insert(DistrictBas record);

	    /**
	     * <p>功能：判空新增商圈数据<p>
	     * <p>创建日期：2015年11月19日 下午2:55:09<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int insertSelective(DistrictBas record);

	    /**
	     * <p>功能：根据商圈主键district查询商圈数据<p>
	     * <p>创建日期：2015年11月19日 下午2:55:44<p>
	     * <p>作者：曾小明<p>
	     * @param district
	     * @return
	     */
	    DistrictBas selectByPrimaryKey(String district);

	    /**
	     * <p>功能：判空修改商圈数据<p>
	     * <p>创建日期：2015年11月19日 下午2:56:21<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int updateByPrimaryKeySelective(DistrictBas record);

	    /**
	     * <p>功能：不判空修改商圈信息<p>
	     * <p>创建日期：2015年11月19日 下午2:56:44<p>
	     * <p>作者：曾小明<p>
	     * @param record
	     * @return
	     */
	    int updateByPrimaryKey(DistrictBas record);
	    
	    /**
	     * <p>功能：分页查询<p>
	     * <p>创建日期：2015年11月19日 下午2:58:01<p>
	     * <p>作者：曾小明<p>
	     * @param map
	     * @param pageBounds
	     * @return
	     */
	    PageList<DistrictBas> selectByCondition(Map map,PageBounds pageBounds);
	    
	    
	    /**
	     * <p>功能：查询所有商圈<p>
	     * <p>创建日期：2015年11月19日 下午3:57:24<p>
	     * <p>作者：曾小明<p>
	     * @return
	     */
	    List<DistrictBas> selectAll();
	    
	    /**
	     * <p>功能：查出所有可用的商圈信息 </p>
	     * <p>作者：吕康</p>
	     * <p>创建日期：2016年2月18日 下午5:52:56</p>
	     * @return
	     */
	    List<DistrictBas> selectAllAvailable();
	    
	    /**
	     * <p>功能：根据编号，名称，城市查询<p>
	     * <p>创建日期：2016年5月18日 上午10:11:43<p>
	     * <p>作者：曾小明<p>
	     * @param district
	     * @param disname
	     * @param city
	     * @return
	     */
	    List<DistrictBas> selectBy(String district,String disname,String city);
}
