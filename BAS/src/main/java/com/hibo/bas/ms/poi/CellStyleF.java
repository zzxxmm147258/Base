package com.hibo.bas.ms.poi;

import java.util.*;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import com.hibo.bas.util.StrUtil;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 上午11:19:12</p>
 * <p>类全名：com.hibo.bas.ms.poi.CellStyleF</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */

public class CellStyleF {
	final org.apache.poi.ss.usermodel.Workbook workBook;

	public CellStyleF(org.apache.poi.ss.usermodel.Workbook workBook) {
		this.workBook = workBook;
		int n = workBook.getNumCellStyles();
		for (int i = 0; i < n; i++) {
			final org.apache.poi.ss.usermodel.CellStyle cellStyle = workBook.getCellStyleAt((short) i);
			if (cellStyle == null)
				continue;
			Style s = new Style(cellStyle);
			if (cellStyles.get(s) == null)
				cellStyles.put(s, cellStyle);
		}
		// System.err.println("NumCellStyles="+n);
		n = workBook.getNumberOfFonts();
		for (int i = 0; i < n; i++) {
			org.apache.poi.ss.usermodel.Font font = workBook.getFontAt((short) i);
			if (font == null)
				continue;
			// System.err.println("Font "+i+"="+new Font(font));
			Font f = new Font(font);
			if (cellFonts.get(f) == null)
				cellFonts.put(f, font);
		}
		// System.err.println("NumberOfFonts="+n);
		// workBook.getCellStyleAt((short)0);
	}

	org.apache.poi.ss.usermodel.DataFormat hssfDataFormat;

	/**
	 *
	 * @param format
	 *            String 形如 "#,##0", "#,##0.00" 等形式
	 * @return short
	 */
	public short getDataFormatIndex(String format) {
		if (hssfDataFormat == null)
			hssfDataFormat = workBook.createDataFormat();
		return hssfDataFormat.getFormat(format);
	}

	private final java.util.Map<Style, org.apache.poi.ss.usermodel.CellStyle> cellStyles = new HashMap<Style, org.apache.poi.ss.usermodel.CellStyle>();
	private final java.util.Map<Font, org.apache.poi.ss.usermodel.Font> cellFonts = new HashMap<Font, org.apache.poi.ss.usermodel.Font>();

	private HSSFColor[] allColors;

	private HSSFColor[] getAllColors() {
		if (allColors == null) {
			java.util.Map<String, HSSFColor> m = HSSFColor.getTripletHash();
			Collection<HSSFColor> colors = m.values();
			allColors = colors.toArray(new HSSFColor[colors.size()]);
		}
		return allColors;
	}

	public HSSFColor getColorByRGB(int rgb) {
		return findSimilarColor(getAllColors(), (rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
		// HSSFColor[] allColors =
		// HSSFPalette p = workBook.getcu
		// HSSFPalette palette = workBook.get.getCustomPalette();
		// java.util.Map<String,HSSFColor> m = HSSFColor.getTripletHash();
		// for(String k : m.keySet() )
		// {
		// HSSFColor c = m.get(k);
		// System.out.println(k+" : index = "+c.getIndex()+",
		// "+c.getHexString()+" , Triplet = "+Arrays.toString(c.getTriplet()));
		// }
		// return 0;
	}

	static public HSSFColor findSimilarColor(HSSFColor[] colors, int red, int green, int blue) {
		int minColorDistance = Integer.MAX_VALUE;
		HSSFColor result = null;
		for (final HSSFColor c : colors) {
			short[] b = c.getTriplet();
			// int colorDistance = Math.abs(red - b[0]) +
			// Math.abs(green - b[1]) +
			// Math.abs(blue - b[2]);
			int colorDistance = (red - b[0]) * (red - b[0]) + (green - b[1]) * (green - b[1])
					+ (blue - b[2]) * (blue - b[2]);
			if (colorDistance < minColorDistance) {
				minColorDistance = colorDistance;
				result = c;// getColor(i);
				if (colorDistance == 0)
					break;
			}
		}
		return result;
		/*
		 * HSSFColor result = null; int minColorDistance = Integer.MAX_VALUE;
		 * byte[] b = _palette.getColor(PaletteRecord.FIRST_COLOR_INDEX); for
		 * (short i = PaletteRecord.FIRST_COLOR_INDEX; b != null; b =
		 * _palette.getColor(++i)) { int colorDistance = Math.abs(red -
		 * unsignedInt(b[0])) + Math.abs(green - unsignedInt(b[1])) +
		 * Math.abs(blue - unsignedInt(b[2])); if (colorDistance <
		 * minColorDistance) { minColorDistance = colorDistance; result =
		 * getColor(i); } }
		 */
	}

	final public class Style implements java.lang.Cloneable {
		public short alignment, valignment; // VERTICAL_CENTER ,VERTICAL_TOP
		public int border; // TOP:1, BOTTOM:2,LEFT:4, RIGHT:8
		public boolean wrapText;
		/*
		 * 0, "General"<br> 1, "0"<br> 2, "0.00"<br> 3, "#,##0"<br> 4,
		 * "#,##0.00"<br> 5, "($#,##0_);($#,##0)"<br> 6,
		 * "($#,##0_);[Red]($#,##0)"<br> 7, "($#,##0.00);($#,##0.00)"<br> 8,
		 * "($#,##0.00_);[Red]($#,##0.00)"<br> 9, "0%"<br> 0xa, "0.00%"<br> 0xb,
		 * "0.00E+00"<br> 0xc, "# ?/?"<br> 0xd, "# ??/??"<br> 0xe, "m/d/yy"<br>
		 * 0xf, "d-mmm-yy"<br> 0x10, "d-mmm"<br> 0x11, "mmm-yy"<br> 0x12,
		 * "h:mm AM/PM"<br> 0x13, "h:mm:ss AM/PM"<br> 0x14, "h:mm"<br> 0x15,
		 * "h:mm:ss"<br> 0x16, "m/d/yy h:mm"<br>
		 * HSSFDataFormat.getBuiltinFormat("0.00") 参见：
		 * http://blog.sina.com.cn/s/blog_6793ffde0101l121.html
		 */
		public short dataFormat;

		/*
		 * 文本格式
		 */
		// public String dataFormatT;
		/*
		 * HSSFColor.LIGHT_BLUE.index HSSFColor.AUTOMATIC
		 */
		public short bgColor;
		public Font font = new Font();// public int fontIndex;
		// String fontName

		@Override
		public String toString() {
			return "[alignment=" + alignment + ",valignment=" + valignment + ",border=" + border + ",wrapText="
					+ wrapText + ",dataFormat=" + dataFormat + ",bgColor=" + bgColor + ",font=" + font + "]";
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Style))
				return false;
			Style a = (Style) o;
			return alignment == a.alignment && valignment == a.valignment && border == a.border
					&& wrapText == a.wrapText && dataFormat == a.dataFormat && font.equals(a.font)
					&& this.bgColor == a.bgColor
					;

			// [alignment=3,valignment=0,border=3,wrapText=false,dataFormat=3,fomt=[fontName=宋体,fontHeight=130,italic=false,color=32767,boldweight=400,underline=0,strikeout=false]]
			// : hashCode=0xb1bcc
		}

		@Override
		public int hashCode() {
			return (alignment + valignment + border + (wrapText ? 1 : 0) + dataFormat) ^ font.hashCode() ^ bgColor;
		}

		// Style()
		// {
		// }
		Style(org.apache.poi.ss.usermodel.CellStyle cellStyle) {
			if (cellStyle != null) {
				alignment = cellStyle.getAlignment();
				valignment = cellStyle.getVerticalAlignment();
				wrapText = cellStyle.getWrapText();
				dataFormat = cellStyle.getDataFormat();
				bgColor = cellStyle.getFillForegroundColor();
				if (bgColor == HSSFColor.AUTOMATIC.index) // 缺省颜色 ： 2014-6-10 :
					bgColor = 0;
				// getFillBackgroundColor();
				if (cellStyle.getBorderTop() > 0)
					border |= 1;
				if (cellStyle.getBorderBottom() > 0)
					border |= 2;
				if (cellStyle.getBorderLeft() > 0)
					border |= 4;
				if (cellStyle.getBorderRight() > 0)
					border |= 8;
				font = new Font(workBook.getFontAt(cellStyle.getFontIndex()));
				// System.err.println("fontIndex="+fontIndex);
				// cellStyle.getFontIndex()
			}
		}

		public org.apache.poi.ss.usermodel.CellStyle createCellStyle() {
			/*
			 * StringBuffer key = new StringBuffer();
			 * key.append(sample.alignment); key.append(',');
			 * key.append(sample.getVerticalAlignment()); key.append(',');
			 * key.append(sample.getBorderTop()); key.append(',');
			 * key.append(sample.getBorderBottom()); key.append(',');
			 * key.append(sample.getBorderLeft()); key.append(',');
			 * key.append(sample.getBorderRight()); key.append(',');
			 * key.append(sample.getWrapText()); key.append(',');
			 * key.append(sample.getDataFormat()); // key.append(','); String k
			 * = key.toString();
			 */
			final Style key = this.cloneStyle();
			org.apache.poi.ss.usermodel.CellStyle styles = cellStyles.get(key);
			if (styles != null) {
				// System.err.println("Reuse "+styles);
				return styles;
			}
			styles = workBook.createCellStyle();
			styles.setAlignment(alignment);
			styles.setVerticalAlignment(valignment);
			styles.setWrapText(wrapText);
			styles.setDataFormat(dataFormat);
			// styles
			// HSSFColor.BLUE_GREY , HSSFColor.GREY_25_PERCENT
			// HSSFColor.GREY_25_PERCENT.
			if (this.bgColor > 0) {
				// HSSFColor.LIGHT_BLUE.index
				styles.setFillForegroundColor(this.bgColor);
				// styles.setFillBackgroundColor(bgColor);
				styles.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			}
			// workBook.get.getFormat("",true);
			// HSSFDataFormat f = createDataFormat();
			// f.getFormat()
			// if( (border&1)!=0 ) styles.setBorderTop( (short)1 );//
			// HSSFCellStyle.BORDER_THIN );
			// if( (border&2)!=0 ) styles.setBorderBottom( (short)1 );//
			// if( (border&4)!=0 ) styles.setBorderLeft( (short)1 );//
			// if( (border&8)!=0 ) styles.setBorderRight( (short)1 );//
			styles.setBorderTop((border & 1) != 0 ? (short) 1 : (short) 0);
			styles.setBorderBottom((border & 2) != 0 ? (short) 1 : (short) 0);//
			styles.setBorderLeft((border & 4) != 0 ? (short) 1 : (short) 0);//
			styles.setBorderRight((border & 8) != 0 ? (short) 1 : (short) 0);//
			styles.setFont(font.createHSSFFont());
			cellStyles.put(key, styles);
			// System.err.println(cellStyles.size()+"--Create new Style
			// "+cellStyles.hashCode()+":"+this+" :
			// hashCode=0x"+Integer.toHexString(hashCode()));
			// [alignment=3,valignment=0,border=3,wrapText=false,dataFormat=3,fomt=[fontName=宋体,fontHeight=130,italic=false,color=32767,boldweight=400,underline=0,strikeout=false]]
			// : hashCode=0xb1bcc
			return styles;
		}

		public Style cloneStyle() {
			try {
				Style s = (Style) super.clone();
				if (this.font != null)
					s.font = this.font.cloneFont();
				return s;
			} catch (java.lang.CloneNotSupportedException ex) {
				return null;
			}
		}
	}; // Style

	public Style newStyle() {
		return new Style(null);
	}

	public Font newFont() {
		return new Font();
	}

	public Font newFont(com.hibo.bas.print.FontDescriptor fontDesc) {
		Font font = new Font();
		if (fontDesc != null) {
			font.fontHeight = (short) (fontDesc.size * 20);
			font.fontName = fontDesc.name;
			if ("dialog".equalsIgnoreCase(fontDesc.name) || "dialoginput".equalsIgnoreCase(fontDesc.name)) {
				font.fontName = "宋体";
			}
			// fontDesc.name
			font.italic = (fontDesc.style & 2) != 0;
			if ((fontDesc.style & 1) != 0) {
				font.boldweight = 700;
			}
		}
		return font;
	}

	final public class Font implements java.lang.Cloneable {
		public String fontName = "Arial";
		public short fontHeight = 200;
		// height in unit's of 1/20th of a point --
		public short color = 32767; // COLOR_NORMAL = 0x7fff
		public boolean italic, strikeout;
		public short boldweight = 400; // 黑体＝700
		public byte underline;

		public Font() {
		}

		public Font cloneFont() {
			try {
				return (Font) super.clone();
			} catch (java.lang.CloneNotSupportedException ex) {
				return null;
			}
		}

		public Font(org.apache.poi.ss.usermodel.Font f) {
			fontName = f.getFontName();
			fontHeight = f.getFontHeight();
			italic = f.getItalic();
			color = f.getColor();
			boldweight = f.getBoldweight();
			strikeout = f.getStrikeout();
			underline = f.getUnderline();
		}

		@Override
		public int hashCode() {
			return (fontName == null ? 0 : fontName.hashCode()) ^ color ^ fontHeight ^ (italic ? 1 : 0)
					^ (strikeout ? 1 : 0) ^ boldweight ^ underline;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Font))
				return false;
			if (this == o)
				return true;
			final Font a = (Font) o;
			return (fontName == a.fontName || (fontName != null && fontName.equals(a.fontName)))
					&& fontHeight == a.fontHeight && color == a.color && italic == a.italic
					&& boldweight == a.boldweight && strikeout == a.strikeout && underline == a.underline;
		}

		@Override
		public String toString() {
			return "[fontName=" + fontName + ",fontHeight=" + fontHeight + ",italic=" + italic + ",color=" + color
					+ ",boldweight=" + boldweight + ",underline=" + underline + ",strikeout=" + strikeout + "]";
		}

		public boolean equalsFont(HSSFFont f) {
			if (f == null)
				return false;
			return (fontName == f.getFontName() || (fontName != null && fontName.equals(f.getFontName())))
					&& fontHeight == f.getFontHeight() && color == f.getColor() && italic == f.getItalic()
					&& boldweight == f.getBoldweight() && strikeout == f.getStrikeout()
					&& underline == f.getUnderline();
		}

		public org.apache.poi.ss.usermodel.Font createHSSFFont() {
			org.apache.poi.ss.usermodel.Font f = cellFonts.get(this);
			if (f != null)
				return f;

			f = workBook.createFont();
			f.setFontName(this.fontName);
			f.setBoldweight(this.boldweight);
			f.setColor(this.color);
			f.setFontHeight(this.fontHeight);
			f.setItalic(this.italic);
			f.setStrikeout(this.strikeout);
			f.setUnderline(this.underline);
			cellFonts.put(this, f);
			// System.err.println("Create new font "+f+":"+this);
			return f;
		}
	}; // Font

	//
	// 下列方法 ： 在 XlsReadDataSet 使用
	//
	public org.apache.poi.ss.usermodel.CellStyle createSqlTypeCellStyle(int flagSqlType) {
		return createSqlTypeCellStyle(flagSqlType, null, -1, -1, -1, 0);
	}

	/*
	 * flagSqlTypebit 1: Number Type, 2: Number Type(有小数位), 4: String Type 8 :
	 * Date 16 : Number (2=NUMERIC,3=DECIMALS) 32 : Boolean int options #2 --
	 * 百分数
	 */
	public org.apache.poi.ss.usermodel.CellStyle createSqlTypeCellStyle(int flagSqlType, Object value, int bgColorIndex,
			int decimals, int minDecimals, int options) {
		// org.apache.poi.hssf.usermodel.HSSFDataFormat f;
		int index = 0;
		if ((flagSqlType & 32) != 0) // Boolean
			index = 6;
		else if ((flagSqlType & 8) != 0) // Date
		{
			index = 4;
			if (value instanceof java.util.Date) {
				java.util.Date date = (java.util.Date) value;
				if (com.hibo.bas.util.DateUtil.toDate0(date).getTime() != date.getTime())
					index = 10;
			}
		} else if ((flagSqlType & 4) != 0)
			index = 3; // String
		else if ((flagSqlType & 2) != 0)
			index = 2; // 数值，
		else if ((flagSqlType & 1) != 0)
			index = 1; // 整数

		CellStyleF.Style style = this.newStyle();
		style.border = 1 + 2 + 4 + 8;
		if (bgColorIndex > 0)
			style.bgColor = (short) bgColorIndex;
		switch (index) {
		case 1: // 整数
			style.alignment = HSSFCellStyle.ALIGN_RIGHT;
			style.dataFormat = 1;
			break;
		case 2:
			style.alignment = HSSFCellStyle.ALIGN_RIGHT;
			if (decimals >= 3) {
				int minDeci = minDecimals > 0 ? minDecimals : 2;
				String fmt = "#,##0." + StrUtil.newString('0', minDecimals);
				if (decimals > minDeci)
					fmt += StrUtil.newString('#', decimals - minDeci);
				if ((options & 2) != 0)
					fmt += "%";
				style.dataFormat = (short) newDataFormat(fmt);
			} else {
				style.dataFormat = (options & 2) != 0 ? (decimals == 0 ? (short) 9 : (short) 10)
						: (decimals == 0 ? (short) 1 : (short) 4); // cellStyle.setDataFormat((short)4);
																	// //
																	// "#,##0.00"
																	// 3: "#,##0
			}
			// Toto ..
			break;
		case 3:
			break;
		case 4:
			style.dataFormat = 0xe;
			break;
		case 10:
			style.dataFormat = 0x16;
			break;
		case 6:
			break;
		default:
		}
		return style.createCellStyle();
	}

	java.util.Map<String, Integer> mapDataFormat;

	int newDataFormat(String fmt) {
		if (mapDataFormat == null) {
			mapDataFormat = new HashMap();
		}
		Integer f = mapDataFormat.get(fmt);
		if (f != null)
			return f.intValue();
		DataFormat format = this.workBook.createDataFormat();
		f = (int) format.getFormat(fmt);
		mapDataFormat.put(fmt, f);
		return f.intValue();
	}

	static public org.apache.poi.ss.usermodel.Font getFont(org.apache.poi.ss.usermodel.Workbook workBook,
			short boldWeight, short color, short fontHeight, String name, boolean italic, boolean strikeout,
			short typeOffset, byte underline) {
		org.apache.poi.ss.usermodel.Font font = workBook.findFont(boldWeight, color, fontHeight, name, italic,
				strikeout, typeOffset, underline);
		if (font == null) {
			font = workBook.createFont();
			font.setBoldweight(boldWeight);
			font.setColor(color);
			font.setFontHeight(fontHeight);
			font.setItalic(italic);
			font.setFontName(name);
			font.setStrikeout(strikeout);
			font.setTypeOffset(typeOffset);
			font.setUnderline(underline);
		}
		return font;
	}
}
