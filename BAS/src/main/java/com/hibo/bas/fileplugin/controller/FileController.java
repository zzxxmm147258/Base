package com.hibo.bas.fileplugin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.constant.Message;
import com.hibo.bas.fileplugin.model.FileInfo;
import com.hibo.bas.fileplugin.model.FileInfo.FileType;
import com.hibo.bas.fileplugin.model.FileInfo.OrderType;
import com.hibo.bas.fileplugin.model.FileMultipartFile;
import com.hibo.bas.fileplugin.service.FileService;
import com.hibo.cms.sys.utils.json.JsonUtil;

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
 * 创建日期：2015年9月18日 下午4:16:10
 * </p>
 * <p>
 * 类全名：com.hibo.bas.fileplugin.controller.FileController
 * </p>
 * 作者：马骏达 初审： 复审：
 */

@Controller("adminFileController")
@RequestMapping("/main/file")
public class FileController {

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	/**
	 * 上传
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public void upload(FileType fileType, MultipartFile file, HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (!fileService.isValid(fileType, file)) {
			data.put("message", "无效");
		} else {
			String url = fileService.upload(file);
			if (url == null) {
				data.put("message", "error");
			} else {
				data.put("message", "success");
				data.put("url", url);
			}
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			JsonUtil.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public void upload(FileMultipartFile files, HttpServletResponse response) {
		List<Message> urls = new ArrayList<Message>();
		if(files!=null){
			for (MultipartFile file : files.getFiles()) {
				Message message = new Message(true);
				String url = fileService.upload(file);
				if (url == null) {
					message.setMessage("error");
					message.setSuccess(false);
				} else {
					message.setMessage("成功");
					message.setDatas(url);
				}
				urls.add(message);
			}
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			JsonUtil.writeValue(response.getWriter(), urls);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 浏览
	 */
	@RequestMapping(value = "/browser", method = RequestMethod.GET)
	public @ResponseBody List<FileInfo> browser(String path, FileType fileType, OrderType orderType) {
		return fileService.browser(path, fileType, orderType);
	}
}
