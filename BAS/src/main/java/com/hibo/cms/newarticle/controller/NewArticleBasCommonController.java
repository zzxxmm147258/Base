package com.hibo.cms.newarticle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.newarticle.model.NewArticleBas;
import com.hibo.cms.newarticle.service.INewArticleBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年10月8日 下午2:07:39</p>
 * <p>类全名：com.hibo.cms.newarticle.controller.NewArticleBasCommonController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/common/newarticle")
public class NewArticleBasCommonController {

	@Autowired
	private INewArticleBasService newArticleBasService;
	
	@RequestMapping(value="/selectByCategorygId.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByGroupId(String code,int page, int limit){
		Message message = new Message();
		try {
			List<NewArticleBas> list=newArticleBasService.selectByCategorygId(code, StrUtil.getPages(page, limit));
			if(list!=null && list.size()>0){
				message.setDatas(list);
				message.setSuccess(true);
				message.setMessage("查询列表");
			}else{
				message.setSuccess(false);
				message.setMessage("暂无数据");
			}
			
		} catch (Exception e) {
            e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("后台出错");
		}
		return JsonUtil.toJsonString(message);
	}
	/**
	 * <p>功能：详情<p>
	 * <p>创建日期：2016年10月9日 下午3:26:16<p>
	 * <p>作者：曾小明<p>
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/selectDetails.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String selectByGroupId(String id){
		Message message = new Message();
		try {
			NewArticleBas bean=newArticleBasService.selectByPrimaryKey(id);
			if(bean!=null){
				if(!StrUtil.isnull(bean.getAttr6())){
					String[] imgs=bean.getAttr6().split(",");
					bean.setImgs(imgs);
				}
				message.setDatas(bean);
				message.setSuccess(true);
				message.setMessage("查询列表");
			}else{
				message.setSuccess(false);
				message.setMessage("参数错误");
			}
			
		} catch (Exception e) {
            e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("后台出错");
		}
		return JsonUtil.toJsonString(message);
	}
	
	@RequestMapping(value = "/content.ajax")
	@ResponseBody
	public String content(String id) {
		Message message=new Message();
		NewArticleBas bean=newArticleBasService.selectByPrimaryKey(id);
		if(bean!=null){
			message.setDatas(bean.getContent());
			message.setSuccess(true);
			message.setMessage("话题内容");
		}else{
			message.setSuccess(false);
			message.setMessage("获取失败");
		}
		return JsonUtil.toJsonString(message);
	}
}
