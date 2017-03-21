package com.hibo.cms.feedback.controller;

import java.util.Date;
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
import com.hibo.cms.feedback.model.FeedbackUserBas;
import com.hibo.cms.feedback.service.IFeedbackUserBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;
import com.hibo.cms.util.Envparam;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月21日 下午1:43:21</p>
 * <p>类全名：com.hibo.cms.feedback.controller.FeedbackUserBas</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping(value = "/member/bas/feedback")
public class FeedbackUserBasController {

	@Autowired
	private IFeedbackUserBasService feedbackUserBasService;

	@Resource
	private FileService fileServie;
	/**
	 * <p>功能：用户反馈<p>
	 * <p>创建日期：2016年9月21日 下午1:53:53<p>
	 * <p>作者：曾小明<p>
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/add.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String newAdd(FeedbackUserBas bean) {
		Message message = new Message();
		String memberId = Envparam.getUserId();// 当前登录用户ID
		if (memberId != null) {
			bean.setMemId(memberId);
			bean.setFeedbackDate(new Date());
			List<MultipartFile> myPhoto = bean.getMyPhoto();
			if (null != myPhoto) {// 多张图片,分割
				StringBuffer imgUrl = new StringBuffer();
				for (MultipartFile multipartFile : myPhoto) {
					if (!multipartFile.isEmpty()) {// 图片文件不为空
						 String url = fileServie.upload(multipartFile);
						 imgUrl.append(url + ",");
					}
				}
				if (imgUrl.length() > 0) {// 图片路径不为空
					bean.setImgUrl(imgUrl.toString());
				}
			}
			feedbackUserBasService.insertSelective(bean);
			message.setSuccess(true);
			message.setMessage("提交成功，多谢您的反馈！");
		} else {
			message.setSuccess(false);
			message.setMessage("提交失败！");
		}
		return JsonUtil.toJsonString(message);
	}
	
	
	
	@RequestMapping(value = "/list.ajax", method = RequestMethod.POST)
	@ResponseBody
	public String list() {
		Message message = new Message();
		String memberId = Envparam.getUserId();// 当前登录用户ID
		if (memberId != null) {
			List<FeedbackUserBas> list=feedbackUserBasService.selectByUserId(memberId);
			if(list!=null && list.size()>0){
				message.setSuccess(true);
				message.setMessage("当前登录用户反馈信息！");
				message.setDatas(list);
			}else{
				message.setSuccess(false);
				message.setMessage("暂无反馈信息！");
			}
		} else {
			message.setSuccess(false);
			message.setMessage("查询失败！");
		}
		return JsonUtil.toJsonString(message);
	}
}
