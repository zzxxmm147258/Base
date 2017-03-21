package com.hibo.cms.version.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.version.model.VersionControlBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月10日 上午9:45:14</p>
 * <p>类全名：com.hibo.cms.version.service.IVersionControlBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IVersionControlBasService {

	int deleteByPrimaryKey(String id);

    int insert(VersionControlBas record);

    int insertSelective(VersionControlBas record);

    VersionControlBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VersionControlBas record);

    int updateByPrimaryKey(VersionControlBas record);
    
    /**
     * <p>功能：查询最新更新<p>
     * <p>创建日期：2016年5月9日 下午5:01:26<p>
     * <p>作者：曾小明<p>
     * @param type
     * @return
     */
    VersionControlBas selectByType(String type);
    
    PageList<VersionControlBas> selectByCondition(Map map,PageBounds pageBounds);
    
}
