package com.hibo.bas.util.page.taglib.utils;

import javax.servlet.http.HttpServletRequest;


public class PageUtils {
	
	/**
	 * 获得当前页
	 * @param request
	 * @return
	 */
	public static int getPageSize(HttpServletRequest request){
		String currentPageSize = request.getParameter("pageSize");
		int pageSize = 10 ;
		try {
			pageSize = Integer.parseInt(currentPageSize);
		} catch (Exception e) {
			pageSize = 10;
		}
		return pageSize;
	}
	
	/**
	 * 获得当前页
	 * @param request
	 * @return
	 */
	public static int getCurrentPage(HttpServletRequest request){
		String currentPageStr = request.getParameter("currentPage");
		int currentPage = 1 ;
		try {
			currentPage = Integer.parseInt(currentPageStr);
			String totalPagesNumber = request.getParameter("totalPagesNumber");
			if(totalPagesNumber != null){
				int totals = Integer.parseInt(totalPagesNumber);
				if(currentPage > totals){
					currentPage = totals;
				}
			}
		} catch (Exception e) {
			currentPage = 1;
		}
		return currentPage;
	}
}	
