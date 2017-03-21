package com.hibo.cms.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hibo.bas.http.HttpRequester;
import com.hibo.bas.http.HttpRequester.HttpRespons;

/**
 * <p> * 标题：生成图形码 * </p>
 * <p> * 功能： * </p>
 * <p> * 版权： Copyright © 2015 HIBO * </p>
 * <p> * 公司: 北京瀚铂科技有限公司 * </p>
 * <p> * 创建日期：2015年11月30日 下午8:03:06 * </p>
 * <p> * 类全名：com.hibo.cms.util.ImageCode * </p>
 * 作者：周雷 
 * 初审：周雷 
 * 复审：周雷
 */
public class ImageCode {
	// 背景色
	public int back = 0x000000;
	// 前景色
	public int front = 0xFFFFFF;
	// 色彩数组
	public int[] COLORS = { 0x982020, 0x872da8, 0x2d76a8, 0xa8532d, 0x2da88f, 0x332da8, 0x54585e };
	// 内容
	public String content = "";
	// 默认图形类型
	private BarcodeFormat codetype = BarcodeFormat.QR_CODE;
	// 宽
	public int width = 400;
	// 高
	public int height = 400;
	// 内图占整幅图的倍数
	public int inMultiple = -1;
	// 内部图片的宽
	public int inWidth = 0;
	// 内部图片的高
	public int inHeight = 0;
	// 内容编码
	public String characset = "UTF-8";
	// 字体
	public Font font = null;
	// 内置图片
	public String innerImgPath = null;
	// 内置图片流
	public InputStream innerImgStream = null;
	// 图片格式
	public String imgFormat = "png";
	// 是否彩色
	public boolean isSetColor = false;
	// 容错率
	public ErrorCorrectionLevel errorLevel = ErrorCorrectionLevel.H;

	public ImageCode(int w_h, String content) {
		this.content = content;
		this.width = w_h;
		this.height = w_h;
	}

	public ImageCode(int w_h, String content, String innerImgPath) {
		this.content = content;
		this.width = w_h;
		this.height = w_h;
		this.innerImgPath = innerImgPath;
	}

	public ImageCode(int w_h, String content, InputStream innerImgStream) {
		this.content = content;
		this.width = w_h;
		this.height = w_h;
		this.innerImgStream = innerImgStream;
	}

	public ImageCode(int width, int height, String content) {
		this.content = content;
		this.width = width;
		this.height = height;
	}

	public ImageCode(int width, int height, String content, boolean isSetColor) {
		this.content = content;
		this.width = width;
		this.height = height;
		this.isSetColor = isSetColor;
	}

	/**
	 * 生成二维码
	 * 
	 * @param matrix
	 * @return
	 */
	private BufferedImage toBufferedImage(BitMatrix matrix) {
		// 边缘偏移
		int pw = 0;
		int ph = 0;
		if (codetype.equals(BarcodeFormat.QR_CODE)) {
			pw = ph = (width + 400) / 20;
			if (width < 200) {
				ph = pw = width / 8;
			}
			ph = pw = ph + (int) Math.pow(content.length(), 1.0 / 3);
		}
		int cy = 0;
		int ct = 0;
		int mWidth = matrix.getWidth() - pw;
		int mHeight = matrix.getHeight() - ph;
		if (codetype.equals(BarcodeFormat.CODE_128) || codetype.equals(BarcodeFormat.CODE_39)) {
			cy = mWidth / 15;
			if (mHeight * 3 > mWidth) {
				cy = mWidth / (21 - 3 * mWidth / mHeight);
			}
			ct = cy / 3;
		}

		Random random = new Random();
		int ss = back;
		int isCol = 1;
		boolean isC = true;
		BufferedImage bufImg = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < mWidth; x++) {
			// 设置条码颜色
			if (isSetColor) {
				if (matrix.get(x + pw / 2, 1)) {
					isCol = 1;
				} else {
					isCol++;
					isC = true;
				}
				if (isCol % 2 == 0 && isC) {
					isC = false;
					int k = random.nextInt(7);
					ss = COLORS[k];
				}
			}
			for (int y = 0; y < mHeight; y++) {
				if (y < ct || y > height - cy) {
					bufImg.setRGB(x, y, front);
				} else {
					bufImg.setRGB(x, y, matrix.get(x + pw / 2, y + ph / 2) ? ss : front);
				}
			}
		}
		isSetColor = false;
		return bufImg;
	}

	/**
	 * 在二维码中间加入图片
	 * 
	 * @param bugImg
	 * @return
	 */
	private BufferedImage createPhotoAtCenter(BufferedImage bufImg) throws Exception {
		Image im = null;
		if (null != innerImgPath) {
			int p = innerImgPath.indexOf(':');
			String img = null;
			if(p>0){
				img = innerImgPath.substring(0, p);
			}
			if ("http".equalsIgnoreCase(img)||"https".equalsIgnoreCase(img)) {
				HttpRequester httpRequester = new HttpRequester();
				HttpRespons rs = httpRequester.sendGet(innerImgPath);
				InputStream inputStream = new ByteArrayInputStream(rs.getBytes());
				im = ImageIO.read(inputStream);
			} else {
				im = ImageIO.read(new File(innerImgPath));
			}
		} else if (null != innerImgStream) {
			im = ImageIO.read(innerImgStream);
		}
		Graphics2D g = bufImg.createGraphics();
		int w = bufImg.getWidth() / 2;
		int h = bufImg.getHeight() / 2;
		if (inMultiple <= 0) {
			inMultiple = 4;
			int k = (int) Math.pow(content.length(), 1.0 / 2.5);
			inMultiple = Math.min(k, inMultiple);
			if (k < 4) {
				inMultiple = 8 - k;
			}
		}
		inWidth = width / inMultiple;
		inHeight = height / inMultiple;
		// 获取bufImg的中间位置
		int centerX = bufImg.getMinX() + w - inWidth / 2;
		int centerY = bufImg.getMinY() + h - inHeight / 2;
		g.drawImage(im, centerX, centerY, inWidth, inHeight, null);
		g.dispose();
		bufImg.flush();
		return bufImg;
	}

	/**
	 * @功能:处理条形码下面的明文内容
	 * @作者:周雷
	 * @时间:2015年11月27日 下午5:54:40
	 * @param bufImg
	 * @param content
	 * @param font
	 * @return
	 * @throws Exception
	 */
	private BufferedImage drawContext(BufferedImage bufImg) throws Exception {
		Graphics2D g = bufImg.createGraphics();
		Color color = new Color(back);
		g.setColor(color);
		int gh = bufImg.getHeight();
		int gw = bufImg.getWidth();
		int fontSize = gw / 21;
		if (gh * 3 > gw) {
			fontSize = gw / (24 - gw / gh * gw / gh);
		}
		int fs = fontSize;
		if (null == font) {
			font = new Font("微软雅黑", Font.PLAIN, fs);
		} else {
			fs = font.getSize();
		}
		g.setFont(font);
		String newContext = "";
		int len = content.length();
		int n = len / 4;
		if (len % 4 != 0) {
			n = n - 1;
		}
		int k = 0;
		for (int i = 0; i < content.length(); i++) {
			if (k < n && i != 0 && i % 4 == 0) {
				newContext = newContext + "  ";
				k++;
			}
			newContext = newContext + content.charAt(i);
		}
		int x = (bufImg.getWidth() - newContext.length() * (fs / 2) - fs / 2) / 2;
		int add = 3 * gw / gh - gw / gh * gw / gh;
		if (gw / gh > 3) {
			add = -add * gh / gw;
		}
		g.drawString(newContext, x, gh - fontSize / 2 + add);
		g.dispose();
		bufImg.flush();
		return bufImg;
	}

	/**
	 * @功能:创建码图
	 * @作者:周雷
	 * @时间:2015年11月30日 下午8:47:17
	 * @return
	 * @throws Exception
	 */
	private BufferedImage createImageCode() throws Exception {
		// 条码类型
		if (null == codetype) {
			codetype = BarcodeFormat.QR_CODE;
		}
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, characset);
		// 设置容错率
		hints.put(EncodeHintType.ERROR_CORRECTION, errorLevel);
		if (null == content || content.isEmpty()) {
			content = "null";
		}
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, codetype, width, height, hints);
		BufferedImage bufImg = toBufferedImage(bitMatrix);
		// 在二维码中间加入图片
		if (null != innerImgPath || null != innerImgStream) {
			createPhotoAtCenter(bufImg);
		}
		if (codetype.equals(BarcodeFormat.CODE_128) || codetype.equals(BarcodeFormat.CODE_39)
				|| codetype.equals(BarcodeFormat.CODABAR)) {
			drawContext(bufImg);
		}
		return bufImg;
	}

	/**
	 * @功能:创建条码
	 * @作者:周雷
	 * @时间:2015年11月30日 下午8:58:22
	 * @return
	 * @throws Exception
	 */
	private void createImage(OutputStream outputStream, String imgOutPath) throws Exception {
		BufferedImage bufImg = createImageCode();
		if (codetype.equals(BarcodeFormat.CODE_128) || codetype.equals(BarcodeFormat.CODE_39)) {
			drawContext(bufImg);
		}
		// 生成二维码
		if (null == imgFormat) {
			imgFormat = "png";
		}
		if (null != imgOutPath) {
			File outputFile = new File(imgOutPath);
			boolean isWrite = ImageIO.write(bufImg, imgFormat, outputFile);
			if (!isWrite) {
				throw new IOException("Could not write an image of format " + outputFile + " to " + outputFile);
			}
		}
		if (null != outputStream) {
			boolean isWrite = ImageIO.write(bufImg, imgFormat, outputStream);
			if (!isWrite) {
				throw new IOException("Could not write an image of format OutputStream");
			}
		}
	}

	private byte[] createCode2Bytes() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		createImage(byteArrayOutputStream, null);
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * @功能:解码
	 * @作者:周雷
	 * @时间:2015年11月27日 下午6:22:30
	 * @param path
	 *            图片地址
	 * @return
	 */
	public static String decode(String path) {
		File file = new File(path);
		return decode(file);
	}

	/**
	 * @功能:解码
	 * @作者:周雷
	 * @时间:2015年11月27日 下午6:20:49
	 * @param file
	 *            图片文件
	 * @return
	 */
	public static String decode(File file) {
		BufferedImage image;
		try {
			if (file == null || file.exists() == false) {
				throw new Exception(" File not found:" + file.getPath());
			}
			image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result result;
			// 解码设置编码方式为：utf-8，
			Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] ImgCodeTobytes(BarcodeFormat codetype) throws Exception {
		this.codetype = codetype;
		return createCode2Bytes();
	}

	public void ImgCodeToFile(BarcodeFormat codetype, String imgOutPath) throws Exception {
		this.codetype = codetype;
		createImage(null, imgOutPath);
	}

	public void ImgCodeToStream(BarcodeFormat codetype, OutputStream outputStream) throws Exception {
		this.codetype = codetype;
		createImage(outputStream, null);
	}

	public void d2CodeToFile(String imgOutPath) throws Exception {
		createImage(null, imgOutPath);
	}

	public void d2CodeToStream(OutputStream outputStream) throws Exception {
		createImage(outputStream, null);
	}

	public byte[] d2CodeTobytes() throws Exception {
		return createCode2Bytes();
	}

	public void barCodeToFile_128(String imgOutPath) throws Exception {
		ImgCodeToFile(BarcodeFormat.CODE_128, imgOutPath);
	}

	public void barCodeToFile_39(String imgOutPath) throws Exception {
		ImgCodeToFile(BarcodeFormat.CODE_39, imgOutPath);
	}

	public void barCodeToFile_CODABAR(String imgOutPath) throws Exception {
		ImgCodeToFile(BarcodeFormat.CODABAR, imgOutPath);
	}

	public void barCodeToStream_128(OutputStream outputStream) throws Exception {
		ImgCodeToStream(BarcodeFormat.CODE_128, outputStream);
	}

	public void barCodeToStream_39(OutputStream outputStream) throws Exception {
		ImgCodeToStream(BarcodeFormat.CODE_39, outputStream);
	}

	public void barCodeToStream_CODABAR(OutputStream outputStream) throws Exception {
		ImgCodeToStream(BarcodeFormat.CODABAR, outputStream);
	}

	public byte[] barCodeTobytes_128() throws Exception {
		return ImgCodeTobytes(BarcodeFormat.CODE_128);
	}

	public byte[] barCodeTobytes_39() throws Exception {
		return ImgCodeTobytes(BarcodeFormat.CODE_39);
	}

	public byte[] barCodeTobytes_CODABAR() throws Exception {
		return ImgCodeTobytes(BarcodeFormat.CODABAR);
	}

	public static void main(String[] args) throws Exception {
		String content = "";
		content = "http://www.vienstar.com/fileserv/apk/vienstar.apk";
		// java.util.Random random = new java.util.Random();
		// for (int i = 0; i < 101; i++) {
		// content = content + random.nextInt(10);
		// }
		// 二维码的图片格式
		String outputFile = "d:" + File.separator + "vienstar.png";
		String innerImgPath = "http://lovemanyan.com/hibo/resources/image/common.gif";
		byte[] b = new ImageCode(400, content, innerImgPath).d2CodeTobytes();
//		 byte[] b = new ImageCode(400, 400, content,true).d2CodeTobytes();
//		byte[] b = new ImageCode(690, 186, content, true).barCodeTobytes_128();
		File file = new File(outputFile);
		java.io.FileOutputStream fop = new java.io.FileOutputStream(file);
		fop.write(b);
		fop.flush();
		fop.close();
		// System.out.println((int)Math.pow(3000, 1.0/4));
	}

}
