package com.hibo.cms.articlenew.service;

import java.util.List;

import com.hibo.cms.articlenew.model.BasArticleButton;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年6月1日 下午4:03:47</p>
 * <p>类全名：com.hibo.cms.articlenew.service.IBasArticleButtonService</p>
 * 作者：lsw
 * 初审：
 * 复审：
 */
public interface IBasArticleButtonService {
	List<BasArticleButton> selectByArticleId(String articleId);
}
