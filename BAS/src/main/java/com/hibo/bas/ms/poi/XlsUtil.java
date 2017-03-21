package com.hibo.bas.ms.poi;

import org.apache.poi.ss.usermodel.*;
import java.io.*;

import com.hibo.bas.ms.util.ExcelUtil.PictureInfo;
import com.hibo.bas.util.*;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 上午11:19:33</p>
 * <p>类全名：com.hibo.bas.ms.poi.XlsUtil</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */

public class XlsUtil {

	static public final int PX_ROW = 15;
	/**
	 * width of 1px in columns with default width in units of 1/256 of a
	 * character width
	 */
	public static final int PX_DEFAULT = 32; // = 256 / 8
	/**
	 * width of 1px in columns with overridden width in units of 1/256 of a
	 * character width
	 */
	static final public boolean enableXSSF;

	static {
		boolean _enableXSSF = false;
		try {
			Class.forName("org.apache.poi.ss.usermodel.WorkbookFactory");
			_enableXSSF = true;
		} catch (Throwable ex) {
		}
		enableXSSF = _enableXSSF;
		System.out.println("XlsUtil.enableXSSF=" + XlsUtil.enableXSSF);
	}

	static public org.apache.poi.ss.usermodel.Workbook newWorkbook(java.io.InputStream is) throws java.io.IOException {
		// poi-ooxml WorkbookFactory
		// poi-ooxml-schemas x-bean.jar
		/*
		 * http://mirrors.ibiblio.org/pub/mirrors/maven2/org/apache/poi/poi-
		 * ooxml/3.7/ if( "xssf".equalsIgnoreCase(model) ) { return new
		 * org.apache.poi.xssf.usermodel.XSSFWorkbook(is); }
		 */
		if (enableXSSF) {
			try {
				return org.apache.poi.ss.usermodel.WorkbookFactory.create(is);
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		}
		try {
			return new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
		} catch (java.io.IOException ex) {
			String msg = ex.getMessage();
			if (msg != null && msg.toLowerCase().indexOf("header") > 0)
				throw new java.lang.RuntimeException("无效的Excel文件");
			throw ex;
		}
	}

	static public org.apache.poi.ss.usermodel.Workbook newWorkbook(java.io.File xlsFile) throws java.io.IOException {
		try (java.io.InputStream is = new java.io.FileInputStream(xlsFile)) {
			return newWorkbook(is);
		}
	}

	static public org.apache.poi.ss.usermodel.Workbook newWorkbook(String model) throws java.io.IOException {

		if (enableXSSF && "xssf".equalsIgnoreCase(model)) {
			return new org.apache.poi.xssf.usermodel.XSSFWorkbook();
		}
		return new org.apache.poi.hssf.usermodel.HSSFWorkbook();
	}

	// @SuppressWarnings("unused")
	public static final float PX_MODIFIED = 36.56f; // = 256 / 7

	static public int indexOfSheet(org.apache.poi.ss.usermodel.Workbook workBook, String sheetName) {
		int n = workBook.getNumberOfSheets();
		// boolean removed = false;
		for (int i = 0; i < n; i++) {
			if (sheetName.equalsIgnoreCase(workBook.getSheetName(i))) {
				return i;
				// workBook.removeSheetAt(i);
			}
		}
		return -1;
	}

	static public String[] getSheetNames(java.io.InputStream is, boolean closeIS) throws java.io.IOException {
		if (is == null)
			return null;
		try {
			// POIFSFileSystem fs = new POIFSFileSystem(is);
			org.apache.poi.ss.usermodel.Workbook wb = XlsUtil.newWorkbook(is);
			// new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
			int n = wb.getNumberOfSheets();
			String sheetNames[] = new String[n];
			for (int i = 0; i < n; i++) {
				sheetNames[i] = wb.getSheetName(i);
			}
			return sheetNames;
		} finally {
			if (closeIS)
				try {
					is.close();
				} catch (Throwable ex) {
				}
			;
		}
	}

	static public boolean removeSheet(String fileName, String sheetName, boolean closeIS) throws IOException {
		org.apache.poi.ss.usermodel.Workbook wb;// = new HSSFWorkbook(fs);
		FileInputStream is = new FileInputStream(fileName);
		try {
			wb = XlsUtil.newWorkbook(is);
			// new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);//new
			// POIFSFileSystem(is));
		} finally {
			is.close();
		}
		// if( is==null ) return false;
		int n = wb.getNumberOfSheets();
		boolean removed = false;
		for (int i = 0; i < n; i++) {
			if (sheetName.equals(wb.getSheetName(i))) {
				wb.removeSheetAt(i);
				removed = true;
				break;
			}
		}
		if (!removed)
			return false;
		FileOutputStream os = new FileOutputStream(fileName);
		try {
			wb.write(os);
		} finally {
			os.close();
		}
		return true;
	}

	static public String[] getSheetNames(final java.util.Map envParams, final java.util.Map params)
			throws java.io.IOException {
		byte srcStream[] = (byte[]) params.get("[object].SrcStream");
		if (srcStream == null)
			return null;
		return getSheetNames(new java.io.ByteArrayInputStream(srcStream), true);
	}

	public static Object getCellNumberValue(org.apache.poi.ss.usermodel.Cell cell, boolean caseStrType) {
		return getCellNumberValue(cell, caseStrType, 8);
	}

	public static Object getCellNumberValue(org.apache.poi.ss.usermodel.Cell cell, boolean caseStrType,
			int maxDecimals) {
		/*
		 * 如果Cell的Type为CELL_TYPE_NUMERIC时，还需要进一步判断该Cell的数据格式，因为它有可能是Date类型，
		 * 在Excel中的Date类型也是以Double类型的数字存储的。 Excel中的Date表示当前时间与1900年1月1日相隔的天数
		 * 2015-01-13 : 将判断 dataFormat>=0xe && dataFormat<=0x16 改为
		 * HSSFDateUtil.isCellDateFormatted(cell) FixBUG 例子
		 * D:\测试\Excel\日期格式例子1.xls 中的 “生效日期” 解析错误！
		 */
		if (org.apache.poi.hssf.usermodel.HSSFDateUtil.isCellDateFormatted(cell)) {
			return cell.getDateCellValue();
		}
		final int dataFormat = cell.getCellStyle().getDataFormat();
		/*
		 * if( dataFormat>=0xe && dataFormat<=0x16 ) return
		 * cell.getDateCellValue();
		 */
		if (dataFormat == 0x31) {
			double v = cell.getNumericCellValue();
			if (caseStrType && v == Double.NaN) {
				return cell.getStringCellValue();
			}
			// text format
			return new java.math.BigDecimal(v).toString();
		}
		/// System.err.println("dataFormat=0x"+Integer.toHexString(dataFormat)+","+cell.getNumericCellValue());
		// cell.getDateCellValue()
		// return new Double(cell.getNumericCellValue());
		double v = cell.getNumericCellValue();
		if (caseStrType && Double.isNaN(v) || Double.isInfinite(v)) {
			return cell.getStringCellValue();
		}
		java.math.BigDecimal x = new java.math.BigDecimal(v);
		if (maxDecimals > 0 && x.scale() > maxDecimals)
			x = x.setScale(maxDecimals, java.math.BigDecimal.ROUND_HALF_UP);
		// if( x.scale()>12 )
		// x = x.setScale(12,java.math.BigDecimal.ROUND_HALF_UP);
		return x;
	}

	// private static int
	// getDefaultColumnWidth(org.apache.poi.ss.usermodel.Sheet sheet)
	// {
	// // return 8:
	// return sheet.getDefaultColumnWidth(); // since poi 3.7
	// }
	public static float getColumnWidthInPixels(org.apache.poi.ss.usermodel.Workbook workBook,
			org.apache.poi.ss.usermodel.Sheet sheet, int column) {
		// int cw = sheet.getColumnWidth((short)column) * 256 ; // the width in
		// units of 1/256th of a character width
		// if( cw==getDefaultColumnWidth(sheet) ) // ==8
		// return 72;
		final int def = sheet.getDefaultColumnWidth() * 256;
		final int cw = sheet.getColumnWidth(column);
		org.apache.poi.ss.usermodel.Font defFont = workBook.getFontAt((short) 0);
		int s = defFont.getFontHeightInPoints();
		float h;
		if (cw == def)
			h = 72;
		else {
			// HSSFFont defFont = workbook.getFontAt((short)0);
			// sheet.getc
			/*
			 * ?????? Column 宽度 转换到 Pxcel宽度 于 缺省字体相关 ??? 如何 根据 字体大小 求 字符的 字体 宽度
			 * ？？？
			 */
			// return s <= 10 ? PX_MODIFIED : PX_DEFAULT;
			float px = s <= 10 ? PX_MODIFIED : PX_DEFAULT;// getPixelWidth(workBook,sheet,column);
															// //PX_DEFAULT=32
			h = cw / px; // cw / 32
		}
		return h * s * columnWidthAdjust(s) / 12f;
	}

	public static float columnWidthFromPixcel(org.apache.poi.ss.usermodel.Workbook workBook,
			org.apache.poi.ss.usermodel.Sheet sheet, int pixcelWidth) {
		org.apache.poi.ss.usermodel.Font defFont = workBook.getFontAt((short) 0);
		int s = defFont.getFontHeightInPoints();
		float px = s <= 10 ? PX_MODIFIED : PX_DEFAULT;
		return pixcelWidth * 12f * px / (s * columnWidthAdjust(s));
	}

	// final static int columnWidthAdjSize[] = {10 , 9, 8 ,7 , 6 ,
	// 11,13,14,15,16,17,18
	// };
	final static float columnWidthAdjVal[] = { 181f / 158.4245f, // 6
			227f / 185.43489f, // 7
			271f / 210.8315f, // 8
			271f / 237.18544f, // 9 : ,
			316f / 263.40262f, // 10
			360f / 330.0f, // 11
			1f, // 12
			406f / 390.9479f, // 13
			451 / 420.91147f, // 14
			451f / 450.97656f, // 15
			495f / 480.0f, // 16
			540f / 510.0f, // 17
			540f / 540.0f, // 18
	};

	static public float columnWidthAdjust(int fontSize) {
		// if( true )
		// return 1;
		// for(int j=0;j<columnWidthAdjSize.length;j++)
		// {
		// if( columnWidthAdjSize[j]==fontSize )
		// return columnWidthAdjVal[j];
		// }
		if (fontSize >= 6 && fontSize <= 18)
			return columnWidthAdjVal[fontSize - 6];
		return 1;
	}

	public static float getRowHeightInPixels(Sheet sheet, Row sheetRow) {
		return (float) (sheetRow == null ? sheet.getDefaultRowHeight() : sheetRow.getHeight()) / (float) XlsUtil.PX_ROW;
	}

	public static float getRowHeightInPixels(Sheet sheet, int row) {
		return getRowHeightInPixels(sheet, sheet.getRow(row));
	}

	private static java.awt.Point getPicturePoint(org.apache.poi.ss.usermodel.Workbook workBook,
			org.apache.poi.ss.usermodel.Sheet sheet, float x, float y, int row, int col) {
		// sheet.getco
		for (; x > 0; col++) {
			float w = getColumnWidthInPixels(workBook, sheet, col);
			if (x <= w)
				break;
			// leftX>=w:
			x -= w;
		}
		for (; y > 0;) {
			org.apache.poi.ss.usermodel.Row sheetRow = sheet.getRow(row);
			// if( sheetRow==null ) break;
			// ((org.apache.poi.hssf.usermodel.HSSFRow)sheetRow).getHeightInPoints()
			float h = getRowHeightInPixels(sheet, sheetRow);
			if (y <= h)
				break;
			y -= h;
			row++;
		}
		float w = getColumnWidthInPixels(workBook, sheet, col);
		float h = getRowHeightInPixels(sheet, row);
		int px = (int) (x / w * 1024);
		if (px >= 1024)
			px = 1023;
		if (px < 0)
			px = 0;
		int py = (int) (y / h * 256);
		if (py >= 256)
			py = 255;
		if (py < 0)
			py = 0;
		return new java.awt.Point((col << 16) | px, (row << 16) | py);
	}

	public static void addPicture(org.apache.poi.ss.usermodel.Workbook workBook,
			org.apache.poi.ss.usermodel.Sheet sheet, final org.apache.poi.ss.usermodel.Drawing patriarch,
			final PictureInfo picts[]) {
		// org.apache.poi.ss.usermodel.Font defFont =
		// workBook.getFontAt((short)0);
		// defFont.setFontHeightInPoints((short)12);
		// workBook.se
		// System.out.println(defFont.getFontHeightInPoints());
		// defFont.
		// for(int i=0;i<sheetPicture.size();i++)
		// java.util.List<? extends PictureData> allPcits =
		// workBook==null?null:workBook.getAllPictures();

		for (final PictureInfo pict : picts) {
			int pictIndex = workBook.addPicture(pict.data, org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_JPEG);
			// x: 0-1023, y:0-255
			// int pictWidth = imx.getWidth();
			// int pictHeight = imx.getHeight();
			int pictCR = pict.r, pictCC = pict.c, pictCW = pict.w, pictCH = pict.h;
			float totalH = 0, totalW = 0;
			// if( pictCR<0 && pictCC<0 )
			if (pictCH < 0) {
				pictCH = (int) sheet.getLastRowNum();
				// 相当于 页面 布局：
			}

			for (int r = pictCR; r < pictCR + pictCH; r++) {
				// heightPixel = (int)
				// (sheet.getRow(cellRowNum).getHeightInPoints() / 0.75 + 0.5);
				totalH += getRowHeightInPixels(sheet, r);
				// (double)sheet.getRow(r).getHeight()/(double)ExcelUtil.PX_ROW;
				// sheet.getRow(r).getHeightInPoints();
				// sheet.getRow(r).getHeight()/(double)PX_ROW;
				// totalH += sheet.getRow(r).getHeightInPoints();
				// totalH += sheet.getRow(r).getHeight();
			}
			// System.err.println(sheet.getDefaultColumnWidth()+";"+sheet.getDefaultColumnWidth()*256);
			for (int c = pictCC; c < pictCC + pictCW; c++) {
				totalW += XlsUtil.getColumnWidthInPixels(workBook, sheet, c); // ???
				// totalW += columnWidths[c];
				// sheet.getDefaultColumnWidth()
				// sheet.
				// System.err.println("col:"+c+":"+columnWidths[c]+":"+columnWidths[c]/32+":"+sheet.getColumnWidth((short)c)+":"+getColumnWidthInPixels(sheet,c));
				// 5536 => 173 ; 2048:72
			}
			float pictW = pict.width, pictH = pict.height;

			/*
			 * totalH = 0, totalW : 是实际 宽度 3/4 ???
			 */

			if ((pictW > totalW || pictH > totalH) && pict.isSizeToFit()) {
				// 需要重新调整图片大小:
				float fX = totalW / pictW;
				float fY = totalH / pictH;
				float f = fX < fY ? fX : fY;
				pictW *= f;
				pictH *= f;
			}

			if (pictW <= 0 || pictH <= 0)
				continue;
			float leftX = (pict.align & 1) == 1 ? 0 : ((pict.align & 2) == 2 ? totalW - pictW : (totalW - pictW) / 2);
			float topY = (pict.align & 4) == 4 ? 0 : ((pict.align & 8) == 8 ? totalH - pictH : (totalH - pictH) / 2);
			if ((pict.options & 2) != 0) {
				if (pict.x != 0) {
					if ((pict.align & 2) == 2)
						leftX -= pict.x;
					else if ((pict.align & 1) == 1)
						leftX += pict.x;
				}
				if (pict.y != 0) {
					if ((pict.align & 8) == 8)
						topY -= pict.y;
					else if ((pict.align & 4) == 4)
						topY += pict.y;
				}
			}
			java.awt.Point xy1 = XlsUtil.getPicturePoint(workBook, sheet, leftX, topY, pictCR, pictCC);
			java.awt.Point xy2 = XlsUtil.getPicturePoint(workBook, sheet, leftX + pictW, topY + pictH, pictCR, pictCC);
			// System.err.println();
			// System.err.println("totalH="+totalH/15+";totalW="+totalW/32);
			// public HSSFClientAnchor( int dx1, int dy1, int dx2, int dy2,
			// short col1, int row1, short col2, int row2 )

			org.apache.poi.ss.usermodel.ClientAnchor anchor = new org.apache.poi.hssf.usermodel.HSSFClientAnchor(
					xy1.x & 0xffff, xy1.y & 0xffff, xy2.x & 0xffff, xy2.y & 0xffff, (short) (xy1.x >> 16), xy1.y >> 16,
					(short) (xy2.x >> 16), xy2.y >> 16);
			patriarch.createPicture(anchor, pictIndex);
		}

	}

	final static public String PageSizeNames[] = { null, "Latter", // 1
			"Latter Small", // 2
			"Tabloid", // 3
			"Ledger", // 4
			"Legal", // 5
			"Statement", // 6
			"Executive", // 7
			"A3", // 8
			"A4", // 9
			"A4 Small", // 10
			"A5", // 11
			"B4", // 12
			"B5", // 13
			"Folio", // 14
			// "", //15
			// "", //16
	};

	public static int indexOfPageSize(String pageSizeName) {
		if (pageSizeName == null)
			return -1;
		for (int i = 0; i < PageSizeNames.length; i++) {
			if (pageSizeName.equals(PageSizeNames[i]))
				return i;
		}
		return -1;
	}
	/*
	 * private static float getPixelWidth(org.apache.poi.ss.usermodel.Workbook
	 * workBook,org.apache.poi.ss.usermodel.Sheet sheet,int column) { final int
	 * def = sheet.getDefaultColumnWidth()*256; final int cw =
	 * sheet.getColumnWidth(column); if( cw==def ) return PX_DEFAULT; //return
	 * cw == def ? PX_DEFAULT : PX_MODIFIED; org.apache.poi.ss.usermodel.Font
	 * defFont = workBook.getFontAt((short)0);
	 * //System.out.println(defFont.getFontHeight()+":"+defFont.
	 * getFontHeightInPoints()); // 12 => 8 //return 256 / (int)(
	 * defFont.getFontHeightInPoints()*8/12 ); //if( defFont.getFontHeight() )
	 * //return PX_DEFAULT; }
	 */

	// static public String[] selectXlsColumn(java.awt.Component parent)
	// {
	// }
}
