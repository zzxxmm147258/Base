package com.hibo.bas.ms.poi;

import java.io.OutputStream;

import com.hibo.bas.ms.util.ExcelUtil;
import com.hibo.bas.util.StrUtil;
import com.hibo.bas.dx.*;

/**
 * <p>标题：EXCEL文件处理对象</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 上午11:18:03</p>
 * <p>类全名：com.hibo.bas.ms.poi.XlsReadDataSet</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */

public class XlsReadDataSet {
	org.apache.poi.ss.usermodel.Sheet sheet;
	public int headRows;
	public final int titleRows;
	public final String sheetName;
	final org.apache.poi.ss.usermodel.Workbook workBook;
	final CellStyleF cellStyleF;
	final int options;

	public XlsReadDataSet(String xlsModel, java.io.InputStream is, boolean closeIS, String sheetName, boolean hasHead,
			DataColumn dataColumns[]) throws java.io.IOException {
		this(xlsModel, is, closeIS, null, sheetName, dataColumns, hasHead ? 1 : 0, hasHead ? 1 : 0);
	}

	public XlsReadDataSet(String xlsModel, java.io.InputStream is, boolean closeIS, String sheetName,
			DataColumn dataColumns[], int options) throws java.io.IOException {
		this(xlsModel, is, closeIS, null, sheetName, dataColumns, (options & 1) != 0 ? 1 : 0, options);
	}

	final String xlsModel;// "xssf" or "hssf"

	/*
	 * &1 : HasHead options &2 : dataColumns[?].size 作为 显示宽度 &4 : | 组合行 (单行标题)
	 * &8 : : headRows>1 时， 使用 最后一行做 列名 0x10 : header 中 使用 Column 的显示名 0x20 :
	 * 自动构造 Column 的列名为 "A","B",.... 0x100 : Excel 1007
	 */
	public XlsReadDataSet(String xlsModel, java.io.InputStream is, boolean closeIS, String title, String sheetName,
			DataColumn dataColumns[], int headRows, int options) throws java.io.IOException {
		this.options = options;
		titleRows = title != null ? 1 : 0;
		if (headRows > 0)
			options |= 1;
		this.xlsModel = xlsModel;
		try {
			this.sheetName = sheetName;
			{
				if (is == null)
					workBook = XlsUtil.newWorkbook(xlsModel);
				else
					workBook = XlsUtil.newWorkbook(is);
			}
			cellStyleF = new CellStyleF(workBook);
			if (dataColumns == null || (options & 0x40) != 0) {
				if (is == null) {
					sheet = workBook.createSheet(sheetName);
				} else {
					if (sheetName == null)
						sheet = workBook.getSheetAt(0);
					else if (sheetName.startsWith("@[") && sheetName.charAt(sheetName.length() - 1) == ']')
						sheet = workBook.getSheetAt(Integer.parseInt(sheetName.substring(2, sheetName.length() - 1)));
					else
						sheet = workBook.getSheet(sheetName);
					if (sheet == null)
						throw new java.lang.IllegalArgumentException("No sheet '" + sheetName + "'");
				}
				rowAt = -1;
				this.headRows = headRows;
				if ((options & 0x40) != 0 && dataColumns != null)
					this.dataColumns = dataColumns;
				else
					buildColumn(headRows > 1 && (options & 8) != 0 ? headRows - 1 : 0);
			} else {
				createSheet(sheetName, title, dataColumns, headRows, options);
			}
		} finally {
			if (closeIS && is != null)
				try {
					is.close();
				} catch (Throwable ex) {
				}
		}
	}

	public void createSheet(String sheetName, String title, DataColumn dataColumns[], int headRows, int options) {
		int iSheet = XlsUtil.indexOfSheet(workBook, sheetName);
		if (iSheet >= 0)
			workBook.removeSheetAt(iSheet);
		iSheet = workBook.getNumberOfSheets();
		sheet = workBook.createSheet(sheetName);
		workBook.setSheetName(iSheet, sheetName);// ,(short)1);
		final int nc = dataColumns.length;
		if (title != null) {
			CellStyleF.Style style = cellStyleF.newStyle();
			style.alignment = org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;// cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.valignment = org.apache.poi.ss.usermodel.CellStyle.VERTICAL_CENTER;
			style.font.fontHeight = 400;
			final org.apache.poi.ss.util.CellRangeAddress r = new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0,
					nc - 1);
			org.apache.poi.ss.usermodel.Row sheetRow = sheet.createRow(0);
			org.apache.poi.ss.usermodel.Cell cell = sheetRow.createCell((short) 0);
			cell.setCellValue(title);// dataColumns[i].columnName);
			cell.setCellStyle(style.createCellStyle());
			sheet.addMergedRegion(r);
		}

		if (headRows > 0) {
			final String captions[][] = new String[nc][];
			for (int i = 0; i < nc; i++) {
				String s = (options & 16) != 0 ? dataColumns[i].displayCaption : null;
				if (s == null)
					s = dataColumns[i].columnName;
				captions[i] = (options & 4) != 0 ? StrUtil.splitString(s, '|') : new String[] { s };
			}
			TableHeaderMerge tableHeaderMerge = new TableHeaderMerge(captions);

			final java.util.List<org.apache.poi.ss.util.CellRangeAddress> vMergeRegion = new java.util.ArrayList<org.apache.poi.ss.util.CellRangeAddress>();
			CellStyleF.Style style = cellStyleF.newStyle();// new
															// CellStyleF.Style();
			style.border = 1 + 2 + 4 + 8;
			style.alignment = org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;// cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.valignment = org.apache.poi.ss.usermodel.CellStyle.VERTICAL_CENTER;
			style.bgColor = 0x16; // HSSFColor.GREY_25_PERCENT.index

			headRows = tableHeaderMerge.getRowCount();
			for (int r = 0; r < headRows; r++) {
				org.apache.poi.ss.usermodel.Row sheetRow = sheet.createRow(titleRows + r);// 0);
				for (int i = 0; i < nc; i++) {
					String caption = tableHeaderMerge.cells[i][r].text;
					org.apache.poi.ss.usermodel.Cell cell = sheetRow.createCell((short) i);
					cell.setCellValue(caption == null ? "" : caption);// dataColumns[i].columnName);
					cell.setCellStyle(style.createCellStyle());// cellStyleF.getHSSFCellStyle(style));
					if (caption != null) {
						int mr = tableHeaderMerge.cells[i][r].rowSpan, mc = tableHeaderMerge.cells[i][r].colSpan;
						if (mr > 1 || mc > 1) {
							org.apache.poi.ss.util.CellRangeAddress region = new org.apache.poi.ss.util.CellRangeAddress(
									titleRows + r, titleRows + r + mr - 1, i, i + mc - 1);
							vMergeRegion.add(region);
						}
					}
				}
			}
			for (int i = 0; i < vMergeRegion.size(); i++) {
				final org.apache.poi.ss.util.CellRangeAddress r = vMergeRegion.get(i);
				sheet.addMergedRegion(r);
			}
		}
		this.headRows = headRows;
		this.dataColumns = dataColumns;
		if ((options & 2) != 0) {
			for (int j = 0; j < dataColumns.length; j++) {
				if (dataColumns[j].size > 0)
					sheet.setColumnWidth((short) j, (short) (dataColumns[j].size * 36));
			}
		}
	}

	/**
	 * 设置纸张大小
	 * 
	 * @param name="A4",....
	 * @since 2015-01-20
	 */
	public void setSheetPaperSize(String name) {
		// final PrintSetup printSetup = sheet.getPrintSetup();
		int i = XlsUtil.indexOfPageSize(name);
		if (i > 0 && this.sheet != null)
			sheet.getPrintSetup().setPaperSize((short) i);
	}

	public String[] getSheetNames() {
		int n = workBook.getNumberOfSheets();
		String sheetNames[] = new String[n];
		for (int i = 0; i < n; i++) {
			sheetNames[i] = workBook.getSheetName(i);
		}
		return sheetNames;
	}

	static String getCaption(String captions[][], int r, int j) {
		String a[] = captions[j];
		return r < a.length ? a[r] : null;
	}

	DataColumn dataColumns[];
	int rowAt = -1;

	public DataColumn[] getColumns() {
		return dataColumns;
	}

	public int[] getSortColumns() {
		return null;
	}

	public Object[] getRowValues() {
		int colCount = dataColumns.length;
		Object values[] = new Object[colCount];
		for (int j = 0; j < colCount; j++)
			values[j] = getValue(j);
		return values;
	}

	public XlsReadDataSet(java.io.InputStream is, String sheetName, boolean hasHead) throws java.io.IOException {
		this(null, is, true, null, sheetName, null, hasHead ? 1 : 0, hasHead ? 1 : 0);
	}

	public XlsReadDataSet(java.io.InputStream is, String sheetName, int headRows) throws java.io.IOException {
		this(null, is, true, null, sheetName, null, headRows, 0 | 8);
	}

	void buildColumn(int columnRow) {
		this.dataColumns = XlsReadDataSet.buildColumns(sheet, headRows, columnRow, (this.options & 0x20) != 0);
	}

	static int getNumberOfColumns(org.apache.poi.ss.usermodel.Sheet sheet) {
		int nr = sheet.getLastRowNum();
		int n = 0;
		for (int r = 0; r <= nr; r++) {
			org.apache.poi.ss.usermodel.Row sheetRow = sheet.getRow(r);
			if (sheetRow == null)
				continue;
			int nc = sheetRow.getLastCellNum() + 1;
			if (n < nc)
				n = nc;
		}
		return n;
	}

	/*
	 * useXlsColnm : "A","B".///
	 */
	public static DataColumn[] buildColumns(org.apache.poi.ss.usermodel.Sheet sheet, int headRows, int columnRow,
			boolean useXlsColnm) {
		if (sheet == null)
			return null;
		org.apache.poi.ss.usermodel.Row sheetRow = sheet.getRow(headRows);
		// if( sheetRow==null )
		// return null;
		// int n2 = sheetRow.getLastCellNum();
		int nc = getNumberOfColumns(sheet);

		DataColumn[] dataColumns = new DataColumn[nc];
		/*
		 * if( !hasHead ) { return; }
		 */
		org.apache.poi.ss.usermodel.Row row = sheet == null || headRows == 0 ? null : sheet.getRow(columnRow); // 列名
		for (int j = 0; j < nc; j++) {
			int sqlType = 0;
			if (sheetRow != null) {
				org.apache.poi.ss.usermodel.Cell cell = sheetRow.getCell((short) j);
				int cellType = cell == null ? -1 : cell.getCellType();
				if (cellType == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
					sqlType = 12;
				else if (cellType == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
					sqlType = java.sql.Types.DOUBLE;
				else if (cellType == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN)
					sqlType = java.sql.Types.BIT;
			}
			String columnName = ExcelUtil.getExcelCellColName(j);// :
																	// "column-"+j;
			String displayName = null;
			int width = sheet.getColumnWidth(j);
			// cell.
			if (row != null)
				try {
					Object o = getValue(row, j, 0);
					String s = o == null ? null : trimBlanks(o.toString());
					if (s != null && s.length() > 0) {
						displayName = s;
						if (!useXlsColnm)
							columnName = s;
					}
				} catch (Throwable ex) {
				}
			dataColumns[j] = new DataColumn(columnName, displayName, sqlType);
			dataColumns[j].displayWidth = width / 32;
			dataColumns[j].decimals = -1;
			dataColumns[j].minDecimals = -1;
		}
		return dataColumns;
	}

	static String trimBlanks(String text) {
		if (text == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean changed = false;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c > ' ')
				sb.append(c);
			else
				changed = true;
		}
		return changed ? sb.toString() : text;
	}

	public void saveTo(OutputStream os, boolean closeOS) throws java.io.IOException {
		try {
			mergPendingCells();
			workBook.write(os);
		} finally {
			if (closeOS)
				os.close();
		}
	}

	org.apache.poi.ss.usermodel.Row currentRow;

	public boolean nextRow() {
		int r = titleRows + headRows + (++rowAt);
		// (hasHead?1:0)+(++rowAt);
		currentRow = sheet.getRow(r);
		return r <= sheet.getLastRowNum();// currentRow!=null;
	}

	public void gotoRow(int row) {
		gotoRow(row, 0);
	}

	public void gotoRow(int row, int userFlags) {
		org.apache.poi.ss.usermodel.Row sheetRow = sheet.getRow(titleRows + headRows + row);
		currentRow = sheetRow;
		this.rowAt = row;
	}

	public int getRowCount() {
		return sheet.getLastRowNum() + 1 - headRows - titleRows;
	}

	public int getRow() {
		return rowAt;
	}

	public void insertRow(int mode) {
		this.postRow();
		switch (mode) {
		case 0:
			insertRowBefore(rowAt);
			return;
		case 1:
			insertRowBefore(rowAt + 1);
			return;
		case 2:
			insertRowBefore(0);
			return;
		case 3:
			insertRowBefore(getRowCount());
			return;
		}
	}

	public void postRow() {
	}

	protected void insertRowBefore(int row) // boolean after
	{
		int prow = row + titleRows + headRows;// hasHead ? row+1 : row;
		int lasRowNumber = sheet.getLastRowNum();
		if (prow <= lasRowNumber)
			sheet.shiftRows(prow, lasRowNumber, 1);
		org.apache.poi.ss.usermodel.Row sheetRow = sheet.createRow(prow);
		if (sheetRow == null)
			throw new java.lang.RuntimeException();
		currentRow = sheetRow;
		this.rowAt = row;
	}

	public static Object getValue(org.apache.poi.ss.usermodel.Row row, int columnIndex) {
		return getValue(row, columnIndex, 8);
	}

	public static Object getValue(org.apache.poi.ss.usermodel.Row row, int columnIndex, int maxDecimals) {
		if (row == null)
			return null;
		org.apache.poi.ss.usermodel.Cell cell = row.getCell((short) columnIndex);
		if (cell == null)
			return null;
		int type = cell.getCellType();
		if (type == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA)
			type = cell.getCachedFormulaResultType();
		switch (type) {
		/*
		 * @see #CELL_TYPE_STRING
		 * 
		 * @see #CELL_TYPE_NUMERIC
		 * 
		 * @see #CELL_TYPE_FORMULA
		 * 
		 * @see #CELL_TYPE_BOOLEAN
		 * 
		 * @see #CELL_TYPE_ERROR
		 */
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC: {
			// Object v = getCellNumberValue(cell,false,maxDecimals);
			/*
			 * 如果Cell的Type为CELL_TYPE_NUMERIC时，还需要进一步判断该Cell的数据格式，因为它有可能是Date类型，
			 * 在Excel中的Date类型也是以Double类型的数字存储的。 Excel中的Date表示当前时间与1900年1月1日相隔的天数
			 * getCellNumberValue 中会判断 isCellDateFormatted
			 */
			/*
			 * if(
			 * org.apache.poi.hssf.usermodel.HSSFDateUtil.isCellDateFormatted(
			 * cell) ) { }
			 */
			// org.apache.poi.hssf.usermodel.HSSFDataFormat f
			return getCellNumberValue(cell, false, maxDecimals);
		}
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		// case HSSFCell.CELL_TYPE_BLANK :
		// return null;
		// case HSSFCell.CELL_TYPE_FORMULA :
		// return null;
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? Boolean.TRUE : Boolean.FALSE;
		// case HSSFCell.CELL_TYPE_ERROR :
		// cell.getStringCellValue()
		case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA: {

			// System.err.println(cell.getNumericCellValue());
			// System.err.println(cell.getStringCellValue());
			return getCellNumberValue(cell, true, maxDecimals);
		}
		}
		return null;
	}

	public static Object getCellNumberValue(org.apache.poi.ss.usermodel.Cell cell, boolean caseStrType,
			int maxDecimals) {
		return XlsUtil.getCellNumberValue(cell, caseStrType, maxDecimals);
	}

	int maxDecimals = 8;

	public void setMaxDecimals(int maxDecimals) {
		this.maxDecimals = maxDecimals;
	}

	public Object getValue(int columnIndex) {
		return getValue(currentRow, columnIndex, maxDecimals);
		// super.getValue()
	}

	public Object getValue(int row, int columnIndex) {
		return getValue(sheet.getRow(titleRows + headRows + row), columnIndex, maxDecimals);
	}

	/**
	 * @since 2005-03-25
	 */
	public Object[] getRowValues(int row) {
		int nCols = this.dataColumns == null ? 0 : dataColumns.length;
		Object a[] = new Object[nCols];
		for (int j = 0; j < nCols; j++)
			a[j] = getValue(row, j);
		return a;
	}

	static private int getSqlType(DataColumn dataColumns[], int columnIndex) {
		if (dataColumns != null && columnIndex >= 0 && columnIndex < dataColumns.length) {
			return dataColumns[columnIndex] == null ? 0 : dataColumns[columnIndex].sqlType;
		}
		return 0;
	}

	/*
	 * bit 1: Number Type, 2: Number Type(有小数位), 4: String Type 8 : Date 32 :
	 * Boolean
	 */
	final public void setValue(int columnIndex, Object value) {
		setValue(columnIndex, value, 0, 0);
	}

	final java.util.List<MergeCells> mergCells = new java.util.ArrayList<MergeCells>();

	/**
	 *
	 * @param columnIndex
	 *            int
	 * @param value
	 *            Object
	 * @param options
	 *            int Bit #1:为0不显示 Bit #2: 百分
	 * 
	 *            bgRGBColor : 与 N7 含义有 区别， N7 直接为 HSSFColor.index
	 * 
	 */
	public void setValue(int columnIndex, Object value, int options, int bgRGBColor) {
		// int n = this.dataColumns ==null ? 0 : dataColumns.length;
		DataColumn c = dataColumns != null && columnIndex < dataColumns.length ? dataColumns[columnIndex] : null;
		XlsReadDataSet.setValue(cellStyleF, currentRow, dataColumns, columnIndex, value, options, mergCells, bgRGBColor,
				c == null ? -1 : c.decimals, c == null ? -1 : c.minDecimals);
	}

	static public class MergeCells {
		final int fromRow, fromCol;
		int rowCount = 1, colCount = 1;

		public MergeCells(int fromRow, int fromCol) {
			this.fromRow = fromRow;
			this.fromCol = fromCol;
		}
	}

	void mergPendingCells() {
		for (MergeCells cells : mergCells) {
			org.apache.poi.ss.util.CellRangeAddress region = new org.apache.poi.ss.util.CellRangeAddress(cells.fromRow,
					cells.fromRow + cells.rowCount - 1, cells.fromCol, cells.fromCol + cells.colCount - 1);
			sheet.addMergedRegion(region);
		}
		mergCells.clear();
	}

	/*
	 * options #1 : 为 0 时隐藏 Bit #2: 百分 bgRGBColor : 与 N7 含义有 区别， N7 直接为
	 * HSSFColor.index
	 * 
	 */
	static void setValue(CellStyleF cellStyleF, org.apache.poi.ss.usermodel.Row currentRow, DataColumn dataColumns[],
			int columnIndex, Object value, int options, java.util.List<MergeCells> mergCells, int bgRGBColor,
			int decimal, int minDecimal) {

		if (currentRow != null) {
			final int bgColor = bgRGBColor <= 0 ? 0 : cellStyleF.getColorByRGB(bgRGBColor).getIndex();
			org.apache.poi.ss.usermodel.Cell cell;
			if (value == null || value == valueEqPrevRow) {
				cell = currentRow.createCell((short) columnIndex);

				cell.setCellStyle(cellStyleF.createSqlTypeCellStyle(0, null, bgColor, decimal, minDecimal, options));
				if (value == valueEqPrevRow && mergCells != null) {
					boolean added = false;
					int r = currentRow.getRowNum(); // row number (0 based)
					for (MergeCells cells : mergCells) {
						if (cells.fromCol == columnIndex && r == cells.fromRow + cells.rowCount) {
							cells.rowCount++;
							added = true;
							break;
						}
					}
					if (!added) {
						MergeCells m = new MergeCells(r - 1, columnIndex);
						m.rowCount = 2;
						mergCells.add(m);
					}
				}
			} else if (value instanceof Number) {
				cell = currentRow.createCell((short) columnIndex);
				if ((options & 1) == 0 || !com.hibo.bas.util.Data.isZero((Number) value))
					cell.setCellValue(((Number) value).doubleValue());
				int sqlType = getSqlType(dataColumns, columnIndex);
				if (sqlType == 0) {
					if (value instanceof java.math.BigDecimal || value instanceof Double || value instanceof Float)
						sqlType = java.sql.Types.NUMERIC;
					else
						sqlType = java.sql.Types.INTEGER;
					/*
					 * 
					 */
					decimal = -1;
				}
				cell.setCellStyle(cellStyleF.createSqlTypeCellStyle(com.hibo.bas.util.SqlUtil.flagSqlType(sqlType),
						null, bgColor, decimal, minDecimal, options));
			} else if (value instanceof java.util.Date) {
				(cell = currentRow.createCell((short) columnIndex)).setCellValue((java.util.Date) value);
				cell.setCellStyle(cellStyleF.createSqlTypeCellStyle(8, value, bgColor, -1, -1, options));
			} else if (value instanceof Boolean) {
				(cell = currentRow.createCell((short) columnIndex)).setCellValue(((Boolean) value).booleanValue());
				cell.setCellStyle(cellStyleF.createSqlTypeCellStyle(6, null, bgColor, decimal, minDecimal, options));
			} else {
				cell = currentRow.createCell((short) columnIndex);
				cell.setCellValue(value.toString());
				cell.setCellStyle(cellStyleF.createSqlTypeCellStyle(0, null, bgColor, decimal, minDecimal, options));
			}
		}
	}

	public void deleteAllRows(int flags) {
		int fromRow = titleRows + headRows;// this.hasHead ? 1 : 0;
		for (; fromRow <= sheet.getLastRowNum();) {
			org.apache.poi.ss.usermodel.Row row = sheet.getRow(fromRow);
			if (row != null)
				sheet.removeRow(row);
			else
				fromRow++;
		}
	}

	static public final Object valueEqPrevRow = new Object();
}
