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
import com.hibo.cms.comment.model.ReplyUserBas;
import com.hibo.cms.comment.service.ICommentBasService;
import com.hibo.cms.comment.service.ICommentUserBasService;
import com.hibo.cms.comment.service.IReplyUserBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/**
 * <p>
 * 标题：
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
 * 创建日期：2016年5月31日 下午2:26:10
 * </p>
 * <p>
 * 类全名：com.hibo.cms.comment.conteroller.ReplyUserBasCommonConteroller
 * </p>
 * 作者：曾小明 初审： 复审：
 */
@Controller
@RequestMapping("/member/bas/reply")
public class ReplyUserBasConteroller {

	@Autowired
	private IReplyUserBasService replyUserBasService;

	@Autowired
	private ICommentUserBasService commentUserBasService;

	@Autowired
	private ICommentBasService commentBasService;

	@Resource
	private FileService fileServie;

	/**
	 * <p>
	 * 功能：回复评论
	 * <p>
	 * <p>
	 * 创建日期：2016年6月12日 下午3:59:58
	 * <p>
	 * <p>
	 * 作者：曾小明
	 * <p>
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/addReply.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String addReply(ReplyUserBas bean) {
		Message message = new Message();
		String userid = Envparam.getUserId();// 当前登录用户ID
		try {

			if (userid != null) {
				CommentUserBas com = commentUserBasService.selectByPrimaryKey(bean.getCommentId());
				if (com != null) {// 评论回复数
					com.setReplyNum(com.getReplyNum() + 1);// 回复顺加1
					bean.setCommentUserid(com.getCommentUserid());// 被回复人ID
				}
				ReplyUserBas re = replyUserBasService.selectByPrimaryKey(bean.getCommentId());
				if (re != null) {// 回复的回复数
					String attr1 = re.getAttr1() != null ? re.getAttr1() : "0";
					int num = Integer.parseInt(attr1) + 1;
					re.setAttr1(num + "");
					bean.setCommentUserid(re.getReplyUserid());// 被回复人ID
				}
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
					bean.setReimgUrl(imgUrl[0]);// 原图
					bean.setReimgSmallurl(imgUrl[1]);// 300*300
					bean.setReimgSmaother(imgUrl[2]);// 800*600
				}
				bean.setReplyUserid(userid);// 回复人
				int now = replyUserBasService.insert(bean, com, re);
				if (now > 0) {
					message.setSuccess(true);
					message.setMessage("回复成功！");
					Comment comment = commentBasService.selectByName(bean.getId());
					message.setDatas(comment);
				} else {
					message.setSuccess(false);
					message.setMessage("回复失败！");
				}
			} else {
				message.setSuccess(false);
				message.setMessage("尚未登录，回复失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("回复失败！");
		}
		return JsonUtil.toJsonString(message);
	}

}
