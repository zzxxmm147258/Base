package com.hibo.cms.comment.service;

import com.hibo.cms.comment.model.PraiseUserBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年5月31日 下午1:47:32</p>
 * <p>类全名：com.hibo.cms.comment.service.IPraiseUserBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IPraiseUserBasService {

	int deleteByPrimaryKey(String id);

    int insert(PraiseUserBas record);

    int insertSelective(PraiseUserBas record);

    PraiseUserBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PraiseUserBas record);

    int updateByPrimaryKey(PraiseUserBas record);
    
    /**
     * <p>功能：判断是否已点赞<p>
     * <p>创建日期：2016年6月12日 下午4:21:15<p>
     * <p>作者：曾小明<p>
     * @param id
     * @param userid
     * @return
     */
    PraiseUserBas selectBymId(String mId,String userid);
    
    /**
     * <p>功能：查询点赞数量<p>
     * <p>创建日期：2016年6月13日 下午3:49:17<p>
     * <p>作者：曾小明<p>
     * @param commentId
     * @return
     */
    int selectCount(String commentId);
}
