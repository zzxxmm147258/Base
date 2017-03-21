package com.hibo.bas.dx;

import com.hibo.bas.util.Data;
import com.hibo.bas.util.StrUtil;

/**
 * <p>标题：表标题</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 上午11:17:23</p>
 * <p>类全名：com.hibo.bas.dx.TableHeaderMerge</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public class TableHeaderMerge {
	public TableHeaderMerge(String captions[][]) {
		init(captions);
	}

	public TableHeaderMerge(String captions[]) {
		String a[][] = new String[captions.length][];
		for (int i = 0; i < a.length; i++) {
			a[i] = StrUtil.splitString(captions[i], '|');
		}
		init(a);
	}

	void init(String captions[][]) {
		int nc = captions.length;
		int nHeadRows = 0;
		for (int i = 0; i < nc; i++) {
			if (nHeadRows < captions[i].length)
				nHeadRows = captions[i].length;
		}
		cells = new Cell[nc][nHeadRows];
		for (int j = 0; j < nc; j++) {
			for (int i = 0; i < nHeadRows; i++) {
				final Cell cell = cells[j][i] = new Cell();
				cell.text = i < captions[j].length ? captions[j][i] : null;
				if (i == captions[j].length - 1)
					cell.rowSpan = nHeadRows - i;
				if (cell.text == null)
					cell.beMerged = true;
			}
		}
		for (int c = 0; c < nc; c++) {
			for (int r = 0; r < nHeadRows; r++) {
				final Cell cell = cells[c][r];
				cell.colSpan = fontColSpan(c, r);
				for (int i = 0; i < cell.rowSpan; i++)
					for (int j = 0; j < cell.colSpan; j++) {
						if (i > 0 || j > 0)
							cells[c + j][r + i].beMerged = true;
					}
			}
		}
	}

	public int getColumnCount() {
		return cells == null ? 0 : cells.length;
	}

	public int getRowCount() {
		return cells == null || cells.length == 0 ? 0 : cells[0].length;
	}

	private int fontColSpan(final int c, final int r) {
		int nc = cells.length;
		if (cells[c][r].text == null)
			return 1;
		int j = c + 1;
		nextColumn: for (; j < nc; j++) {
			if (cells[j][r].rowSpan != cells[c][r].rowSpan)
				break;
			for (int i = 0; i <= r; i++) {
				if (!Data.objEquals(cells[j][i].text, cells[c][i].text))
					break nextColumn;
			}
		}
		return j - c;
	}

	public Cell cells[][];

	static public class Cell {
		public String text;
		public boolean beMerged;
		public int rowSpan = 1, colSpan = 1;

		@Override
		public String toString() {
			if (beMerged)
				return "";
			return text + "[" + rowSpan + "x" + colSpan + "]";
		}
	}

	void list(java.io.PrintStream out) {
		if (cells == null || cells.length == 0)
			return;
		for (int r = 0; r < cells[0].length; r++) {
			for (int j = 0; j < cells.length; j++) {
				if (j > 0)
					out.print(',');
				out.print(cells[j][r]);
			}
			out.println();
		}
	}
}
