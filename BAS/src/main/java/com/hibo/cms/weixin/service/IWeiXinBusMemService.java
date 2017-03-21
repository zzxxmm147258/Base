package com.hibo.cms.weixin.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.weixin.model.WeiXinBusMem;


/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月25日 下午3:55:15</p>
 * <p>类全名：com.hibo.mem.weixin.service.IWeiXinMemService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IWeiXinBusMemService {

	    int deleteByPrimaryKey(String openid);

	    int insert(WeiXinBusMem record);

	    int insertSelective(WeiXinBusMem record);

	    WeiXinBusMem selectByPrimaryKey(String openid);

	    int updateByPrimaryKeySelective(WeiXinBusMem record);

	    int updateByPrimaryKey(WeiXinBusMem record);
	    
	    PageList<WeiXinBusMem> selectByCondition(Map map,PageBounds pageBounds);
	    
	    List<WeiXinBusMem> select3All(String mId);
	    
}
