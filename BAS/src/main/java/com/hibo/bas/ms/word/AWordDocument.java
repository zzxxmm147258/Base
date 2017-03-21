package com.hibo.bas.ms.word;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月12日 上午11:44:06</p>
 * <p>类全名：com.hibo.bas.ms.word.AWordDocument</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public abstract class AWordDocument {
	
	public <T> T replaceInDoc(T doc, Map<String, Object> params) {
		// 替换段落里面的变量
		replaceInPara(doc, params);
		// 替换表格里面的变量
		replaceInTable(doc, params);
		return doc;
	};

	public void replaceInDoc(String sourceFile, String newFile, Map<String, Object> params) throws Exception {
	}

	/**
	 * @功能:替换内容返回输出流
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月12日 下午2:12:26
	 * @param sourceFile
	 * @param out
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public OutputStream replaceOutDoc(String sourceFile, OutputStream out, Map<String, Object> params) throws Exception {
		return out;
	}
	
	public ByteArrayOutputStream replaceOutDoc(String sourceFile, Map<String, Object> params) throws Exception {
		return new ByteArrayOutputStream();
	}
	/**
	 * @功能:替换内容返回字节数组
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月13日 下午2:36:49
	 * @param sourceFile
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public byte[] replaceByteDoc(String sourceFile, Map<String, Object> params) throws Exception {
		return new byte[0];
	}
	
	/**
	 * @功能:替换内容返回输入流
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月12日 下午6:21:50
	 * @param sourceFile
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public InputStream replaceInDoc(String sourceFile, Map<String, Object> params) throws Exception{
		return null;
	}
	
	/**
	 * @功能:设置单元
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月12日 下午2:12:08
	 * @param run
	 */
	public <T> void setRun(T run) {

	}
	
	/**
	 * @功能:关闭输入流
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月12日 下午2:11:35
	 * @param in
	 */
	public void close(InputStream in) {
		try {
			if (null != in) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @功能:关闭输出流
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月12日 下午2:11:35
	 * @param in
	 */
	public void close(OutputStream out) {
		try {
			if (null != out) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param <T>
	 * 
	 * @param doc 要替换的文档
	 * @param params  参数
	 */
	public <T> void replaceInPara(T doc, Map<String, Object> params) {
	}

	/**
	 * 替换表格里面的变量
	 * 
	 * @param <T>
	 * 
	 * @param doc 要替换的文档
	 * @param params  参数
	 */
	public <T> void replaceInTable(T doc, Map<String, Object> params) {
	}

}
