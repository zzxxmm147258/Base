package com.hibo.cms.weixin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hibo.cms.lims.QueryFilterBuilder;
import com.hibo.cms.sys.shiro.util.PasswordHelper;
import com.hibo.cms.user.model.User;
import com.hibo.cms.util.Envparam;
import com.hibo.cms.weixin.dao.WeiXinSerMemMapper;
import com.hibo.cms.weixin.model.WeiXinSerMem;
import com.hibo.cms.weixin.service.IWeiXinSerMemService;
import com.hibo.mem.member.dao.MemberMemBasMapper;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月25日 下午6:58:06</p>
 * <p>类全名：com.hibo.cms.weixin.service.impl.WeiXinSerMemServiceImpl</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Service
public class WeiXinSerMemServiceImpl implements IWeiXinSerMemService{

	@Autowired
	private WeiXinSerMemMapper weiXinSerMemMapper;
	@Autowired
	private MemberMemBasMapper memberMemBasMapper;
	
	@Override
	public int deleteByPrimaryKey(String openid) {
		return weiXinSerMemMapper.deleteByPrimaryKey(openid);
	}

	@Override
	public int insert(WeiXinSerMem record) {
		return weiXinSerMemMapper.insert(record);
	}

	@Override
	public int insertSelective(WeiXinSerMem record) {
		return weiXinSerMemMapper.insertSelective(record);
	}

	@Override
	public WeiXinSerMem selectByPrimaryKey(String openid) {
		return weiXinSerMemMapper.selectByPrimaryKey(openid);
	}

	@Override
	public int updateByPrimaryKeySelective(WeiXinSerMem record) {
		return weiXinSerMemMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int bind(User user) {
		String openId = (String) Envparam.getVFormSession("openId");
		String appKey = (String) Envparam.getVFormSession("appKey");
		User u = memberMemBasMapper.selectByUsername(user.getUsername(),null);
		if(null!=u){
			//验证密码
			String p = PasswordHelper.getPasswordByPS(user.getPassword(), u.getUsername(), u.getSalt());
			if(!p.equals(u.getPassword())){
				return -2; 			//用户名或密码错误
			}
			WeiXinSerMem weiXinSer = weiXinSerMemMapper.selectByPrimaryKey(openId);
			int num = 0;
			if(null!=weiXinSer){
				weiXinSer.setUserid(u.getUserid());
				num = weiXinSerMemMapper.updateByPrimaryKeySelective(weiXinSer);
			}else{
				weiXinSer = new WeiXinSerMem();
				weiXinSer.setOpenid(openId);
				weiXinSer.setAppkey(appKey);
				weiXinSer.setUserid(u.getUserid());
				weiXinSer.setSubscribe((short) 1);
				num = weiXinSerMemMapper.insertSelective(weiXinSer);
			}
			return num;
		}else{
			return -1;				//该手机号未注册
		}
	}

	@Override
	public int updateByPrimaryKey(WeiXinSerMem record) {
		return weiXinSerMemMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageList<WeiXinSerMem> selectByCondition(Map map, PageBounds pageBounds) {
		String wStr = QueryFilterBuilder.buildQueryFilter(map);
		return weiXinSerMemMapper.selectByCondition(wStr, pageBounds);
	}

	@Override
	public List<WeiXinSerMem> select2All(String mId) {
		return weiXinSerMemMapper.select2All(mId);
	}

	@Override
	public WeiXinSerMem selectByNameKey(String openid) {
		return weiXinSerMemMapper.selectByNameKey(openid);
	}

	@Override
	public WeiXinSerMem selectByAppKey(String openid, String appkey) {
		return weiXinSerMemMapper.selectByAppKey(openid, appkey);
	}

}
