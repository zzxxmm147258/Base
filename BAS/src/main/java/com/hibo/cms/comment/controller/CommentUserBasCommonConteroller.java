package com.hibo.cms.comment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.comment.model.Comment;
import com.hibo.cms.comment.model.PraiseUserBas;
import com.hibo.cms.comment.service.ICommentBasService;
import com.hibo.cms.comment.service.IPraiseUserBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/**
 * <p>
 * 标题：评论
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
 * 创建日期：2016年5月31日 下午1:45:53
 * </p>
 * <p>
 * 类全名：com.hibo.cms.comment.conteroller.CommentUserBasCommonConteroller
 * </p>
 * 作者：曾小明 初审： 复审：
 */
@Controller
@RequestMapping("/common/bas/comment")
public class CommentUserBasCommonConteroller {

	@Autowired
	private ICommentBasService commentService;

	@Autowired
	private IPraiseUserBasService praiseUserBasService;

	/**
	 * <p>
	 * 功能：查询某商品的所有评论
	 * <p>
	 * <p>
	 * 创建日期：2016年6月12日 上午11:10:28
	 * <p>
	 * <p>
	 * 作者：曾小明
	 * <p>
	 * 
	 * @param mId
	 * @return
	 */
	@RequestMapping(value = "/selectComment.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String selectComment(String mId, int page, int limit,String type) {
		Message message = new Message();
		Map<String,Object> map=new HashMap<String,Object>();
		int num = praiseUserBasService.selectCount(mId);//商品点赞数
		List<Comment> list = commentService.selectBymId(mId,type, StrUtil.getPages(page, limit));
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				int now = praiseUserBasService.selectCount(list.get(i).getId());
				list.get(i).setPraiseNum(now + "");// 点赞数
				if(StrUtil.isnull(list.get(i).getNum())){
					list.get(i).setNum("0");
				}
				
			}
			String userid = Envparam.getUserId();// 当前登录用户ID
			if(!StrUtil.isnull(userid)){
			for(int i=0; i<list.size();i++){
				PraiseUserBas   pr=praiseUserBasService.selectBymId(list.get(i).getId(), userid);
				if(pr!=null){
					list.get(i).setIszan(true);//已点赞
				   }else{
					   list.get(i).setIszan(false);//未点赞
				 }
			}
			}
			map.put("list", list);
			map.put("num", num);
			message.setDatas(map);
			message.setSuccess(true);
			message.setMessage("评论列表！");
		} else {
			message.setSuccess(false);
			message.setMessage("暂无评论！");
		}
		System.out.println(JsonUtil.toJsonString(message));
		return JsonUtil.toJsonString(message);
	}

}
