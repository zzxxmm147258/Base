package com.hibo.bas.util.page.taglib;

import java.util.ArrayList;
import java.util.List;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月30日 下午2:20:36</p>
 * <p>类全名：com.hibo.cms.sys.dao.page.taglib.PageNums</p>
 * 作者：cuizhimin
 * 初审：
 * 复审：
 */
public class PageNums {
	
	public static List<Long> generateLinkPageNumbers(int currentPageNumber, long lastPageNumber, int count) {
		
		int avg = count / 2;

		long startPageNumber = currentPageNumber - avg;
		if (startPageNumber <= 0) {
			startPageNumber = 1;
		}

		long endPageNumber = startPageNumber + count - 1;
		if (endPageNumber > lastPageNumber) {
			endPageNumber = lastPageNumber;
		}

		if (endPageNumber - startPageNumber < count) {
			startPageNumber = endPageNumber - count;
			if (startPageNumber <= 0) {
				startPageNumber = 1;
			}
		}

		java.util.List<Long> result = new ArrayList<Long>();
		for (long i = startPageNumber; i <= endPageNumber; i++) {
			result.add(new Long(i));
		}
		return result;
	}
}
