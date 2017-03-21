package com.hibo.bas.ms.word;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import com.hibo.bas.classFile.ClassFileUtil;
import com.hibo.bas.util.StrUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月12日 下午1:39:41</p>
 * <p>类全名：com.hibo.bas.ms.word.HWordModel</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class HWordModel extends AWordDocument {

	@Override
	public <T> T replaceInDoc(T doc, Map<String, Object> params) {
		Range range = ((HWPFDocument) doc).getRange();
		// 替换段落里面的变量
		replaceInPara(range, params);
		// 替换表格里面的变量
		replaceInTable(range, params);
		return doc;
	}

	@Override
	public void replaceInDoc(String sourceFile, String newFile, Map<String, Object> params) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = ClassFileUtil.getInputStream01(sourceFile);
			HWPFDocument hwpf = new HWPFDocument(in);
			this.replaceInDoc(hwpf, params);
			out = new FileOutputStream(newFile);
			hwpf.write(out);
		} finally {
			close(out);
			close(in);
		}
	}

	@Override
	public OutputStream replaceOutDoc(String sourceFile, OutputStream out, Map<String, Object> params) throws Exception {
		InputStream in = null;
		try {
			in = ClassFileUtil.getInputStream01(sourceFile);
			HWPFDocument hwpf = new HWPFDocument(in);
			replaceInDoc(hwpf, params);
			hwpf.write(out);
			return out;
		} finally {
			close(in);
		}
	}
	
	@Override
	public ByteArrayOutputStream replaceOutDoc(String sourceFile, Map<String, Object> params) throws Exception {
		InputStream in = null;
		try {
			in = ClassFileUtil.getInputStream01(sourceFile);
			HWPFDocument hwpf = new HWPFDocument(in);
			replaceInDoc(hwpf, params);
			ByteArrayOutputStream out= new ByteArrayOutputStream();
			hwpf.write(out);
			return out;
		} finally {
			close(in);
		}
	}
	
	@Override
	public byte[] replaceByteDoc(String sourceFile, Map<String, Object> params) throws Exception {
		InputStream in = ClassFileUtil.getInputStream01(sourceFile);
		HWPFDocument hwpf = new HWPFDocument(in);
		replaceInDoc(hwpf, params);
		ByteArrayOutputStream out= new ByteArrayOutputStream();
		hwpf.write(out);
		return out.toByteArray();
	}

	@Override
	public InputStream replaceInDoc(String sourceFile, Map<String, Object> params) throws Exception {
		InputStream in = ClassFileUtil.getInputStream01(sourceFile);
		HWPFDocument hwpf = new HWPFDocument(in);
		replaceInDoc(hwpf, params);
		ByteArrayOutputStream out= new ByteArrayOutputStream();
		hwpf.write(out);
		return new ByteArrayInputStream(out.toByteArray());
	}
	

	@Override
	public <T> void replaceInPara(T doc, Map<String, Object> params) {
		Range range = (Range) doc;
		Set<String> keys = params.keySet();
		for (String key : keys) {
			String value = StrUtil.obj2str(params.get(key));
			key = "${" + key + "}";
			if(StrUtil.isnull(value)){
				value = "";
			}
			range.replaceText(key, value);
		}
	}
}
