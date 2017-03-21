package com.hibo.bas.ms.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.hibo.bas.ms.word.AWordDocument;
import com.hibo.bas.ms.word.HWordModel;
import com.hibo.bas.ms.word.XWordModel;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月13日 下午2:48:48</p>
 * <p>类全名：com.hibo.bas.ms.util.WordUtil</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class WordUtil {
	public static void poiWordTableReplace(String sourceFile, String newFile, Map<String, Object> replaces)
			throws Exception {
		AWordDocument document = null;
		if (sourceFile.endsWith(".docx")) {
			document = new XWordModel();
		} else if (sourceFile.endsWith(".doc")) {
			document = new HWordModel();
		}
		document.replaceInDoc(sourceFile, newFile, replaces);
	}

	public static InputStream poiWordTableReplace(String sourceFile, Map<String, Object> replaces) throws Exception {
		AWordDocument document = null;
		if (sourceFile.endsWith(".docx")) {
			document = new XWordModel();
		} else if (sourceFile.endsWith(".doc")) {
			document = new HWordModel();
		}
		return document.replaceInDoc(sourceFile, replaces);
	}

	public static OutputStream poiWordReplace(String sourceFile, OutputStream out, Map<String, Object> replaces) throws Exception {
		AWordDocument document = null;
		if (sourceFile.endsWith(".docx")) {
			document = new XWordModel();
		} else if (sourceFile.endsWith(".doc")) {
			document = new HWordModel();
		}
		return document.replaceOutDoc(sourceFile, out, replaces);
	}

	public static ByteArrayOutputStream poiWordReplace(String sourceFile, Map<String, Object> replaces) throws Exception {
		AWordDocument document = null;
		if (sourceFile.endsWith(".docx")) {
			document = new XWordModel();
		} else if (sourceFile.endsWith(".doc")) {
			document = new HWordModel();
		}
		return document.replaceOutDoc(sourceFile, replaces);
	}

	public static byte[] poiWordByteReplace(String sourceFile, Map<String, Object> replaces) throws Exception {
		AWordDocument document = null;
		if (sourceFile.endsWith(".docx")) {
			document = new XWordModel();
		} else if (sourceFile.endsWith(".doc")) {
			document = new HWordModel();
		}
		return document.replaceByteDoc(sourceFile, replaces);
	}
}
