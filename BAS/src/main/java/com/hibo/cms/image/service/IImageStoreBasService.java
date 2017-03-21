package com.hibo.cms.image.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hibo.cms.image.model.ImageStoreBas;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月12日 上午10:39:15</p>
 * <p>类全名：com.hibo.cms.image.service.IImageStoreBasService</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
public interface IImageStoreBasService {

	int deleteByPrimaryKey(String id);

    int insert(ImageStoreBas record);

    int insertSelective(ImageStoreBas record);

    ImageStoreBas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImageStoreBas record);

    int updateByPrimaryKey(ImageStoreBas record);
    
    /**
     * <p>功能：根据主表系统号类型查询图片地址<p>
     * <p>创建日期：2016年9月12日 上午10:27:31<p>
     * <p>作者：曾小明<p>
     * @param mId
     * @param sys
     * @param type
     * @return
     */
    List<ImageStoreBas> selectBymIdOrSysOrType(@Param("mId")String mId,@Param("sys")String sys,@Param("type")String type);

    /**
     * <p>功能：<p>
     * <p>创建日期：2016年9月12日 上午10:46:52<p>
     * <p>作者：曾小明<p>
     * @param mId 关联表id
     * @param sys 系统号
     * @param type 类型
     * @param url 图片 url[0]==原图  url[1]==大图  url[2]==中图  url[3]==小图
     * @return
     */
    int insertImage(String mId,String sys,String type,String imgUrl[]);
    
    
    int deleteBymId(String mid);
}
