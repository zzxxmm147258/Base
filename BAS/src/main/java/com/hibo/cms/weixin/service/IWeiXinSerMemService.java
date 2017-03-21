package com.hibo.cms.weixin.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.user.model.User;
import com.hibo.cms.weixin.model.WeiXinSerMem;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月25日 下午6:56:59</p>
 * <p>类全名：com.hibo.cms.weixin.service.IWeiXinSerMemService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IWeiXinSerMemService {

	 int deleteByPrimaryKey(String openid);

	    int insert(WeiXinSerMem record);

	    int insertSelective(WeiXinSerMem record);

	    WeiXinSerMem selectByPrimaryKey(String openid);

	    int updateByPrimaryKeySelective(WeiXinSerMem record);
	    
	    /**
	     * <p>功能：将openId和user绑定 </p>
	     * <p>作者：吕康</p>
	     * <p>创建日期：2015年11月27日 下午5:09:42</p>
	     * @param openId
	     * @param user
	     * @return
	     */
	    int bind(User user);

	    int updateByPrimaryKey(WeiXinSerMem record);
	    
	    PageList<WeiXinSerMem> selectByCondition(Map map,PageBounds pageBounds);

	    List<WeiXinSerMem> select2All(String mId);
	    
	    WeiXinSerMem selectByNameKey(String openid);
	    
	    /**
	     * <p>功能：根据openid和appkey查询是否关注<p>
	     * <p>创建日期：2016年4月27日 下午5:03:50<p>
	     * <p>作者：曾小明<p>
	     * @param openid
	     * @param appkey
	     * @return
	     */
	    WeiXinSerMem selectByAppKey(String openid,String appkey);
}
