package com.hibo.cms.feedback.service;

import java.util.List;

import com.hibo.cms.feedback.model.FeedbackUserBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月21日 下午1:40:36</p>
 * <p>类全名：com.hibo.cms.feedback.service.IFeedbackUserBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IFeedbackUserBasService {

	int deleteByPrimaryKey(String id);

    int insert(FeedbackUserBas record);

    int insertSelective(FeedbackUserBas record);

    FeedbackUserBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FeedbackUserBas record);

    int updateByPrimaryKey(FeedbackUserBas record);
    
    List<FeedbackUserBas> selectByUserId(String userid);
}
