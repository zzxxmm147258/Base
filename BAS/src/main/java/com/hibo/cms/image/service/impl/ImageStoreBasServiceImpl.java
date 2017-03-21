package com.hibo.cms.image.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibo.cms.image.dao.ImageStoreBasMapper;
import com.hibo.cms.image.model.ImageStoreBas;
import com.hibo.cms.image.service.IImageStoreBasService;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月12日 上午10:39:33</p>
 * <p>类全名：com.hibo.cms.image.service.ImageStoreBasServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class ImageStoreBasServiceImpl implements IImageStoreBasService{

	@Autowired 
	private ImageStoreBasMapper imageStoreBasMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return imageStoreBasMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ImageStoreBas record) {
		return imageStoreBasMapper.insert(record);
	}

	@Override
	public int insertSelective(ImageStoreBas record) {
		return imageStoreBasMapper.insertSelective(record);
	}

	@Override
	public ImageStoreBas selectByPrimaryKey(String id) {
		return imageStoreBasMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ImageStoreBas record) {
		return imageStoreBasMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ImageStoreBas record) {
		return imageStoreBasMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ImageStoreBas> selectBymIdOrSysOrType(String mId, String sys, String type) {
		return imageStoreBasMapper.selectBymIdOrSysOrType(mId, sys, type);
	}

	@Override
	public int insertImage(String mId, String sys, String type, String[] imgUrl) {
		ImageStoreBas img=new ImageStoreBas();
		img.setmId(mId);
		img.setSys(sys);
		img.setType(type);
		if(imgUrl.length==0){
			
		}else if(imgUrl.length==1){
			img.setImgUrl(imgUrl[0]);
		}else if(imgUrl.length==2){
			img.setImgUrl(imgUrl[0]);
			img.setImgBigurl(imgUrl[1]);
		}else if(imgUrl.length==3){
			img.setImgUrl(imgUrl[0]);
			img.setImgBigurl(imgUrl[1]);
			img.setImgInurl(imgUrl[2]);
		}else if(imgUrl.length==4){
			img.setImgUrl(imgUrl[0]);
			img.setImgBigurl(imgUrl[1]);
			img.setImgInurl(imgUrl[2]);
			img.setImgSmallurl(imgUrl[3]);
		}
		return imageStoreBasMapper.insertSelective(img);
	}

	@Override
	public int deleteBymId(String mid) {
		return imageStoreBasMapper.deleteBymId(mid);
	}

}
