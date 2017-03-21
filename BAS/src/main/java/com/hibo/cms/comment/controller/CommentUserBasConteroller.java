package com.hibo.cms.comment.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.constant.Message;
import com.hibo.bas.fileplugin.service.FileService;
import com.hibo.bas.image.ImageUtils;
import com.hibo.bas.image.model.ImageModel;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.comment.model.Comment;
import com.hibo.cms.comment.model.CommentUserBas;
import com.hibo.cms.comment.model.PraiseUserBas;
import com.hibo.cms.comment.model.ReplyUserBas;
import com.hibo.cms.comment.service.ICommentBasService;
import com.hibo.cms.comment.service.ICommentUserBasService;
import com.hibo.cms.comment.service.IPraiseUserBasService;
import com.hibo.cms.comment.service.IReplyUserBasService;
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
@RequestMapping("/member/bas/comment")
public class CommentUserBasConteroller {

	@Autowired
	private ICommentUserBasService commentUserBasService;

	@Resource
	private FileService fileServie;

	@Autowired
	private ICommentBasService commentBasService;

	@Autowired
	private IPraiseUserBasService praiseUserBasService;

	@Autowired
	private ICommentBasService commentService;
	
	@Autowired
	private IReplyUserBasService replyUserBasService;
	/**
	 * <p>
	 * 功能：新增评论
	 * <p>
	 * <p>
	 * 创建日期：2016年5月31日 下午2:05:00
	 * <p>
	 * <p>
	 * 作者：曾小明
	 * <p>
	 * 
	 * @param mId
	 * @param commentContent
	 * @return
	 */
	@RequestMapping(value = "/addComment.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String addComment(CommentUserBas bean) {
		Message message = new Message();
		String userid = Envparam.getUserId();// 当前登录用户ID
		try {

			if (userid != null) {
				List<MultipartFile> myPhoto = bean.getMyPhoto();
				String[] imgUrl = { "", "", "" };
				if (null != myPhoto && myPhoto.size() > 0) {// 多张图片,分割
					for (MultipartFile multipartFile : myPhoto) {
						if (!multipartFile.isEmpty()) {// 图片文件不为空
							String url[] = fileServie.upload(multipartFile, "300*300,800*600");
							imgUrl[0] += url[0] + ",";
							imgUrl[1] += url[1] + ",";
							imgUrl[2] += url[2] + ",";
						}
					}

				} else if (bean.getMyImg() != null && bean.getMyImg().size() > 0) {
					List<String> myImg = bean.getMyImg();
					for (int i = 0; i < myImg.size(); i++)
						if (myImg.get(i) != null) {
							ImageModel im = ImageUtils.GetImageInputStream(myImg.get(i));
							InputStream ins = im.getInputStream();
							String[] url = fileServie.upload2(ins, "300*300,800*600", im.getSuffix());
							imgUrl[0] += url[0] + ",";
							imgUrl[1] += url[1] + ",";
							imgUrl[2] += url[2] + ",";
							if (ins != null) {
								try {
									ins.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
				}
				if (!StrUtil.isnull(imgUrl[0])) {
					bean.setCoimgUrl(imgUrl[0]);// 原图
					bean.setCoimgSmallurl(imgUrl[1]);// 300*300
					bean.setCoimgSmaother(imgUrl[2]);// 800*600
				}
				bean.setCommentUserid(userid);// 评论人
				bean.setPraiseNum(0);// 点赞数
				bean.setReplyNum(0);// 回复数
				int now = commentUserBasService.insertSelective(bean);
				if (now > 0) {
					message.setSuccess(true);
					message.setMessage("评论成功！");
					Comment comment = commentBasService.selectByName(bean.getId());
					message.setDatas(comment);
				} else {
					message.setSuccess(false);
					message.setMessage("评论失败！");
				}
			} else {
				message.setSuccess(false);
				message.setMessage("尚未登录，评论失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("评论失败！");
		}
		return JsonUtil.toJsonString(message);
	}

	/**
	 * <p>功能：回复信息列表<p>
	 * <p>创建日期：2016年10月11日 上午10:46:19<p>
	 * <p>作者：曾小明<p>
	 * @param page
	 * @param limit
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/selectByUserid.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String selectComment(int page, int limit, String type) {
		Message message = new Message();
		String userid = Envparam.getUserId();// 当前登录用户ID
		try {
			if (!StrUtil.isnull(userid)) {
				List<Comment> list = commentService.selectByUserId(userid, StrUtil.getPages(page, limit));
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						PraiseUserBas pr = praiseUserBasService.selectBymId(list.get(i).getId(), userid);
						if (pr != null) {
							list.get(i).setIszan(true);// 已点赞
						} else {
							list.get(i).setIszan(false);// 未点赞
						}
					}
					message.setDatas(list);
					message.setSuccess(true);
					message.setMessage("消息列表！");
				} else {
					message.setSuccess(false);
					message.setMessage("暂无消息！");
				}

			} else {
				message.setSuccess(false);
				message.setMessage("未登录！");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("后台错误！");
		}
		return JsonUtil.toJsonString(message);
	}
	
	/**
	 * <p>功能：消息详情<p>
	 * <p>创建日期：2016年10月11日 上午10:55:49<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/selectDetails.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String selectDetails(String id) {
		Message message = new Message();
		String userid = Envparam.getUserId();// 当前登录用户ID
		try {
			if (!StrUtil.isnull(userid)) {
				Comment bean = commentService.selectByName(id);
				if (bean != null) {
						PraiseUserBas pr = praiseUserBasService.selectBymId(bean.getId(), userid);
						if (pr != null) {
							bean.setIszan(true);// 已点赞
						} else {
							bean.setIszan(false);// 未点赞
						}
						int now = praiseUserBasService.selectCount(bean.getId());
						bean.setPraiseNum(now + "");// 点赞数
						if(StrUtil.isnull(bean.getNum())){
							bean.setNum("0");
						}
					message.setDatas(bean);
					message.setSuccess(true);
					message.setMessage("消息详情！");
				} else {
					message.setSuccess(false);
					message.setMessage("参数错误！");
				}

			} else {
				message.setSuccess(false);
				message.setMessage("未登录！");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("后台错误！");
		}
		return JsonUtil.toJsonString(message);
	}
	/**
	 * <p>功能：删除隐藏回复我的消息<p>
	 * <p>创建日期：2016年10月11日 下午4:26:25<p>
	 * <p>作者：曾小明<p>
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/del.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String del(String ids) {
		Message message = new Message();
		String userid = Envparam.getUserId();// 当前登录用户ID
		String[] id=ids.split(",");
		int now=0;
		try {
			if (!StrUtil.isnull(userid)) {
				for(int i=0;i<id.length;i++){
					ReplyUserBas bean = replyUserBasService.selectByPrimaryKey(id[i]);
					bean.setAttr2("0");//删除隐藏
					now+=replyUserBasService.updateByPrimaryKeySelective(bean);
				}
				if (now>0) {
					message.setSuccess(true);
					message.setMessage("删除成功！");
				} else {
					message.setSuccess(false);
					message.setMessage("删除失败！");
				}
			} else {
				message.setSuccess(false);
				message.setMessage("未登录！");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("后台错误！");
		}
		return JsonUtil.toJsonString(message);
	}
}
