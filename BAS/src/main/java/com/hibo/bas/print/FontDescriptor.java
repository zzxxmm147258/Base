package com.hibo.bas.print;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年11月19日 上午9:58:10
 * </p>
 * <p>
 * 类全名：com.hibo.bas.print.FontDescriptor
 * </p>
 * 作者：cgh 初审： 复审：
 */
public class FontDescriptor implements java.io.Serializable {
	transient java.awt.Font font;
	public final String name;
	public final int style;
	public final float size;

	public FontDescriptor(String name, int style, float size) {
		if (name == null)
			throw new java.lang.NullPointerException();
		this.name = name;
		this.style = style;
		this.size = size;
	}

	public FontDescriptor(java.awt.FontMetrics fontMetrics) {
		this.font = fontMetrics.getFont();
		this.name = font.getName();
		this.style = font.getStyle();
		this.size = font.getSize();
		this.fontMetrics = fontMetrics;
	}

	final public java.awt.Font getFont() {
		if (font == null)
			font = new java.awt.Font(name, style, (int) size);
		return font;
	}

	final public java.awt.Font getFont(java.util.Map fontSet) {
		java.awt.Font f = (java.awt.Font) fontSet.get(this);
		if (f == null) {
			fontSet.put(this, f = getFont());
		}
		return f;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof FontDescriptor) {
			FontDescriptor f = (FontDescriptor) obj;
			return name.equals(f.name) && style == f.style && size == f.size;
		}
		return false;
	}

	@Override
	public String toString() {
		return "font(" + name + "," + style + "," + size + ")";
	}

	public String getName() {
		return name;
	}

	public int getStyle() {
		return style;
	}

	public float getSize() {
		return size;
	}

	@Override
	public int hashCode() {
		return name.hashCode() ^ style ^ (int) size;
	}

	public float getFontHeight() {
		return size + 2;
	}

	transient java.awt.FontMetrics fontMetrics;
	transient public java.awt.Graphics graphics;

	public java.awt.FontMetrics getFontMetrics() {
		if (fontMetrics == null) {
			if (graphics != null)
				fontMetrics = graphics.getFontMetrics(getFont());
			else
				fontMetrics = getFontMetrics(getFont());
		}
		return fontMetrics;
	}

	public float stringWidth(String s) {
		if (s == null || s.length() == 0)
			return 0;
		return getFontMetrics().stringWidth(s);
	}

	public static java.awt.FontMetrics getFontMetrics(java.awt.Font font) {
		return java.awt.Toolkit.getDefaultToolkit().getFontMetrics(font);
	}
}
