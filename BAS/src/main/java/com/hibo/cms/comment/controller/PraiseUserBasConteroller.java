package com.hibo.cms.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.comment.model.CommentUserBas;
import com.hibo.cms.comment.model.PraiseUserBas;
import com.hibo.cms.comment.service.ICommentUserBasService;
import com.hibo.cms.comment.service.IPraiseUserBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/**
 * <p>
 * 标题：点赞
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2016年5月31日 下午1:46:33
 * </p>
 * <p>
 * 类全名：com.hibo.cms.comment.conteroller.PraiseUserBasCommonConteroller
 * </p>
 * 作者：曾小明 初审： 复审：
 */
@Controller
@RequestMapping("/member/bas/praise")
public class PraiseUserBasConteroller {

	@Autowired
	private IPraiseUserBasService praiseUserBasService;

	@Autowired
	private ICommentUserBasService commentUserBasService;

	/**
	 * <p>
	 * 功能：点赞商品或者评论
	 * <p>
	 * <p>
	 * 创建日期：2016年6月12日 下午4:10:13
	 * <p>
	 * <p>
	 * 作者：曾小明
	 * <p>
	 * 
	 * @param mId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/addPraise.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String addPraise(String mId, String type) {
		Message message = new Message();
		String userid = Envparam.getUserId();// 当前登录用户ID
		if (userid != null) {
			PraiseUserBas bas = praiseUserBasService.selectBymId(mId, userid);
			if (bas == null) {
				PraiseUserBas bean = new PraiseUserBas();
				bean.setCommentId(mId);
				bean.setUserid(userid);
				bean.setType(type);
				int now = praiseUserBasService.insertSelective(bean);
				if (now > 0) {
					if(!StrUtil.isnull(type) && type.equals("20")){
						CommentUserBas com=commentUserBasService.selectByPrimaryKey(mId);
						if(com!=null){
						   com.setPraiseNum(com.getPraiseNum()+1);//点赞数
						   commentUserBasService.updateByPrimaryKeySelective(com);
						}
					}
					message.setSuccess(true);
					message.setMessage("点赞成功！");
				} else {
					message.setSuccess(false);
					message.setMessage("点赞失败！");
				}
			} else {
				message.setSuccess(false);
				message.setMessage("已点赞！");
			}
		} else {
			message.setSuccess(false);
			message.setMessage("尚未登录，点赞失败！");
		}
		return JsonUtil.toJsonString(message);
	}
	
	
	
	/**
	 * <p>功能：判断是否已底赞<p>
	 * <p>创建日期：2016年6月12日 下午4:51:20<p>
	 * <p>作者：曾小明<p>
	 * @param mId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/isPraise.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String isPraise(String mId, String type) {
		Message message = new Message();
		String userid = Envparam.getUserId();// 当前登录用户ID
		if (userid != null) {
			PraiseUserBas bas = praiseUserBasService.selectBymId(mId, userid);
			if (bas == null) {
				message.setSuccess(false);
				message.setMessage("未点赞！");
			} else {
				message.setSuccess(true);
				message.setMessage("已点赞！");
			}
		} else {
			message.setSuccess(false);
			message.setMessage("尚未登录，点赞失败！");
		}
		return JsonUtil.toJsonString(message);
	}
}
