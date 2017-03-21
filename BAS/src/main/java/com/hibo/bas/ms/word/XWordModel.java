package com.hibo.bas.ms.word;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import com.hibo.bas.classFile.ClassFileUtil;
import com.hibo.bas.util.StrUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月11日 下午6:20:35</p>
 * <p>类全名：com.hibo.bas.ms.word.XWordModel</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class XWordModel extends AWordDocument {

	@Override
	public void replaceInDoc(String sourceFile, String newFile, Map<String, Object> params) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = ClassFileUtil.getInputStream01(sourceFile);
			XWPFDocument xwpf = new XWPFDocument(in);
			replaceInDoc(xwpf, params);
			out = new FileOutputStream(newFile);
			xwpf.write(out);
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
			XWPFDocument xwpf = new XWPFDocument(in);
			replaceInDoc(xwpf, params);
			xwpf.write(out);
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
			XWPFDocument xwpf = new XWPFDocument(in);
			replaceInDoc(xwpf, params);
			ByteArrayOutputStream out= new ByteArrayOutputStream();
			xwpf.write(out);
			return out;
		} finally {
			close(in);
		}
	}
	
	@Override
	public byte[] replaceByteDoc(String sourceFile, Map<String, Object> params) throws Exception {
		InputStream in = null;
		try {
			in = ClassFileUtil.getInputStream01(sourceFile);
			XWPFDocument xwpf = new XWPFDocument(in);
			replaceInDoc(xwpf, params);
			ByteArrayOutputStream out= new ByteArrayOutputStream();
			xwpf.write(out);
			return out.toByteArray();
		} finally {
			close(in);
		}
	}

	@Override
	public InputStream replaceInDoc(String sourceFile, Map<String, Object> params) throws Exception {
		ByteArrayOutputStream out = null;
		try {
			InputStream in = ClassFileUtil.getInputStream01(sourceFile);
			XWPFDocument xwpf = new XWPFDocument(in);
			replaceInDoc(xwpf, params);
			out = new ByteArrayOutputStream();
			xwpf.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} finally {
			close(out);
		}
	}

	/**
	 * 替换段落里面的变量
	 * @param <T>
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	@Override
	public <T> void replaceInPara(T doc, Map<String, Object> params) {
		Iterator<XWPFParagraph> iterator = ((XWPFDocument)doc).getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			replaceInPara(para, params);
		}
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            参数
	 */
	private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
		List<XWPFRun> runs;
		runs = para.getRuns();
		String text = "";
		ArrayList<Integer> list = new ArrayList<Integer>();
		boolean isOk = false;
		boolean isUp = false;
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
		for (int i = runs.size() - 1; i >= 0; i--) {
			XWPFRun run = runs.get(i);
			String runText = run.toString();
		    Matcher matcher = pattern.matcher(runText);
		    if (matcher.find()) {  
                while (matcher.find()) {  
                   runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                }
                run.setText(runText);  
            }else if(runText.endsWith("}")){
				isOk = true;
			}else if(runText.startsWith("${")||runText.startsWith("$")){
				isUp = true;
			}
			if(isOk&&!isUp){
				list.add(i);
				text = runText+text;
			}
			if(isOk&&isUp){
				isOk = false;
				isUp = false;
				text = runText+text;
				String p = text.substring(2, text.length()-1);
				if(params.containsKey(p)){
					for (int j = 0; j < list.size(); j++) {
						para.removeRun(list.get(j));
					}
					String value = StrUtil.obj2str(params.get(p));
					if(StrUtil.isnull(value)){
						value = "";
					}
					run.setText(value, 0);
					setRun(run);
					list.clear();
					text = "";
				}
			}
			
		}
	}
	
	/**
	 * 替换表格里面的变量
	 * @param <T>
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	public <T> void replaceInTable(T doc, Map<String, Object> params) {
		Iterator<XWPFTable> iterator = ((XWPFDocument)doc).getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while (iterator.hasNext()) {
			table = iterator.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {
						this.replaceInPara(para, params);
					}
				}
			}
		}
	}

	
}
