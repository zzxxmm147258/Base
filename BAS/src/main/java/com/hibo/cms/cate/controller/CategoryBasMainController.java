package com.hibo.cms.cate.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hibo.bas.constant.Message;
import com.hibo.bas.fileplugin.service.FileService;
import com.hibo.bas.image.ImageUtils;
import com.hibo.bas.image.model.ImageModel;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.cate.model.CategoryBas;
import com.hibo.cms.cate.service.ICategoryBasService;
import com.hibo.cms.sys.utils.json.JsonUtil;

/**   
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年9月7日 下午5:59:26</p>
 * <p>类全名：com.hibo.cms.cate.controller.CategoryBasMainController</p>
 * 作者：曾小明
 * 初审：
 * 复审：
 */
@Controller
@RequestMapping("/main/bas/categroy")
public class CategoryBasMainController {

	@Resource
	private ICategoryBasService categoryService;
	
	@Resource
	private FileService fileServie;
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(CategoryBas bean){
		Message message = new Message(true);
		int now=0;
		try {
			if(!StrUtil.isnull(bean.getImg())){
				ImageModel im = ImageUtils.GetImageInputStream(bean.getImg());
				InputStream ins = im.getInputStream();
				String[] imgUrls = fileServie.upload2(ins, "300*300", im.getSuffix());
				bean.setImg(imgUrls[1]);
				if (ins != null) {
					try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				bean.setImg(null);
			}
			if(!StrUtil.isnull(bean.getEimg())){
				ImageModel im = ImageUtils.GetImageInputStream(bean.getEimg());
				InputStream ins = im.getInputStream();
				String[] imgUrls = fileServie.upload2(ins, "300*300", im.getSuffix());
				bean.setEimg(imgUrls[1]);
				if (ins != null) {
					try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				bean.setEimg(null);
			}
			CategoryBas ca=categoryService.selectByPrimaryKey(bean.getCode());
			if(ca==null){
				now=categoryService.insertSelective(bean);
			}else{
				now=categoryService.updateByPrimaryKeySelective(bean);
			}
			if(now>0){
				message.setMessage("提交成功");
				message.setSuccess(true);
			}else{
				message.setMessage("提交失败");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage("提交失败");
			message.setSuccess(false);
		}
		return JsonUtil.toJsonString(message);
	}
	
	@RequestMapping(value="/del.ajax",method=RequestMethod.POST)
	@ResponseBody
	public String del(String code){
		Message message = new Message(true);
		int row = categoryService.deleteByPrimaryKey(code);
		if(row>0){
			
			message.setMessage("提交成功");
			message.setSuccess(true);
		}else{
			message.setMessage("删除失败");
			message.setSuccess(false);
		}
		return JsonUtil.toJsonString(message);
	}
	
}
