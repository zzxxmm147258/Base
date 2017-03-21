package com.hibo.cms.component.service.country;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.component.model.CountryBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月11日 下午3:28:01</p>
 * <p>类全名：com.hibo.cms.component.service.country.ICountryBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface ICountryBasService {

	    int deleteByPrimaryKey(String id);

	    int insert(CountryBas record);

	    int insertSelective(CountryBas record);

	    CountryBas selectByPrimaryKey(String id);

	    int updateByPrimaryKeySelective(CountryBas record);

	    int updateByPrimaryKey(CountryBas record);
	    
	    PageList<CountryBas> selectByCondition(Map map,PageBounds pageBounds);
	    
	    List<String> selectAll();
	    
	    /**
	     * <p>功能：查询所有的国家<p>
	     * <p>创建日期：2015年11月23日 下午7:31:33<p>
	     * <p>作者：曾小明<p>
	     * @return
	     */
	    List<CountryBas> selectList(String title);
}
