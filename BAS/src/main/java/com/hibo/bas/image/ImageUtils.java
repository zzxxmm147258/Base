package com.hibo.bas.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.springframework.util.Assert;

import com.hibo.bas.image.model.ImageModel;
import com.hibo.bas.util.DateUtil;
import com.hibo.bas.util.StrUtil;
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
 * 创建日期：2015年8月13日 下午4:22:18
 * </p>
 * <p>
 * 类全名：com.hibo.ebusi.utils.ImageUtils
 * </p>
 * 作者：马骏达 初审： 复审：
 */
public final class ImageUtils {

	/**
	 * 处理类型
	 */
	private enum Type {

		/** 自动 */
		auto,

		/** jdk */
		jdk,

		/**
		 * GraphicsMagick
		 */
		graphicsMagick,

		/**
		 * ImageMagick
		 */
		imageMagick
	}

	/** 处理类型 */
	private static Type type = Type.auto;

	/** GraphicsMagick程序路径 */
	private static String graphicsMagickPath;

	/** ImageMagick程序路径 */
	private static String imageMagickPath;

	/** 背景颜色 */
	private static final Color BACKGROUND_COLOR = Color.white;

	/** 目标图片品质(取值范围: 0 - 100) */
	private static final int DEST_QUALITY = 88;

	static {
		if (graphicsMagickPath == null) {
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.indexOf("windows") >= 0) {
				String pathVariable = System.getenv("Path");
				if (pathVariable != null) {
					String[] paths = pathVariable.split(";");
					for (String path : paths) {
						File gmFile = new File(path.trim() + "/gm.exe");
						File gmdisplayFile = new File(path.trim() + "/gmdisplay.exe");
						if (gmFile.exists() && gmdisplayFile.exists()) {
							graphicsMagickPath = path.trim();
							break;
						}
					}
				}
			}
		}

		if (imageMagickPath == null) {
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.indexOf("windows") >= 0) {
				String pathVariable = System.getenv("Path");
				if (pathVariable != null) {
					String[] paths = pathVariable.split(";");
					for (String path : paths) {
						File convertFile = new File(path.trim() + "/convert.exe");
						File compositeFile = new File(path.trim() + "/composite.exe");
						if (convertFile.exists() && compositeFile.exists()) {
							imageMagickPath = path.trim();
							break;
						}
					}
				}
			}
		}

		if (type == Type.auto) {
			try {
				IMOperation operation = new IMOperation();
				operation.version();
				IdentifyCmd identifyCmd = new IdentifyCmd(true);
				if (graphicsMagickPath != null) {
					identifyCmd.setSearchPath(graphicsMagickPath);
				}
				identifyCmd.run(operation);
				type = Type.graphicsMagick;
			} catch (Throwable e1) {
				try {
					IMOperation operation = new IMOperation();
					operation.version();
					IdentifyCmd identifyCmd = new IdentifyCmd(false);
					identifyCmd.run(operation);
					if (imageMagickPath != null) {
						identifyCmd.setSearchPath(imageMagickPath);
					}
					type = Type.imageMagick;
				} catch (Throwable e2) {
					type = Type.jdk;
				}
			}
		}
	}

	/**
	 * 不可实例化
	 */
	private ImageUtils() {
	}

	/**
	 * 等比例图片缩放
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @param destWidth
	 *            目标宽度
	 * @param destHeight
	 *            目标高度
	 * @param model
	 *            模式， 0，比例不一致是截图， 1，长宽不变，比例不一致时，有白边， 2 与原图长宽等比例
	 */
	public static void zoom(File srcFile, File destFile, int destWidth, int destHeight) {
		zoom(srcFile, destFile, destWidth, destHeight, 0, null);
	}

	public static void zoom(File srcFile, File destFile, int destWidth, int destHeight, int model){
		zoom(srcFile, destFile, destWidth, destHeight, 0, null);
	}
	
	public static void zoom(File srcFile, File destFile, int destWidth, int destHeight, int model, String logoText) {
		Assert.notNull(srcFile);
		Assert.notNull(destFile);
		Assert.state(destWidth > 0);
		Assert.state(destHeight > 0);
		if (type == Type.jdk) {
			Graphics2D graphics2D = null;
			ImageOutputStream imageOutputStream = null;
			ImageWriter imageWriter = null;
			try {
				BufferedImage srcBufferedImage = ImageIO.read(srcFile);
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();
				int width = destWidth;
				int height = destHeight;
				if (model == 1) {
					if (srcHeight >= srcWidth) {
						width = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
					} else {
						height = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
					}
				} else if (model == 2) {
					if ((srcWidth * 1.0) / destWidth < (srcHeight * 1.0) / destHeight) {
						if (srcWidth > destWidth) {
							destHeight = Integer.parseInt(
									new java.text.DecimalFormat("0").format(srcHeight * destWidth / (srcWidth * 1.0)));
						}
					} else {
						if (srcHeight > destHeight) {
							destWidth = Integer.parseInt(
									new java.text.DecimalFormat("0").format(srcWidth * destHeight / (srcHeight * 1.0)));
						}
					}
				} else {
					width = srcWidth;
					height = srcHeight;
					int x = 0, y = 0;
					if (destWidth * 1.0 / srcWidth > destHeight * 1.0 / srcHeight) {
						// 截去高度
						height = (int) Math.round(destHeight * 1.0 * srcWidth / destWidth);
						y = (int) Math.round((srcHeight - height) / 2.0);
					} else if (destWidth * 1.0 / srcWidth < destHeight * 1.0 / srcHeight) {
						// 截去宽度
						width = (int) Math.round(destWidth * 1.0 * srcHeight / destHeight);
						x = (int) Math.round((srcWidth - width) / 2.0);
					}
					Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
					ImageReader reader = (ImageReader) readers.next();
					ImageInputStream iis = ImageIO.createImageInputStream(srcFile);
					// ImageInputStream iis = new
					reader.setInput(iis, true);
					ImageReadParam param = reader.getDefaultReadParam();
					Rectangle rect = new Rectangle(x, y, width, height);
					param.setSourceRegion(rect);
					try {
						BufferedImage srcBufferedImage1 = reader.read(0, param);
						srcBufferedImage = srcBufferedImage1;
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}

				BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				graphics2D = destBufferedImage.createGraphics();
				graphics2D.setBackground(BACKGROUND_COLOR);
				graphics2D.clearRect(0, 0, destWidth, destHeight);
				
				if (!StrUtil.isnull2(logoText)){
					graphics2D.setColor(Color.red);
		            // 设置 Font
					graphics2D.setFont(new Font("宋体", Font.BOLD, 12));
					//float alpha = 85/100F;
					//graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			            // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .

		            int width2 = destWidth*2/3;
		            int height2 = destHeight*9/10;
		            String[] textArr = StrUtil.splitString(logoText, '\n');
		            if (textArr.length > 1 && StrUtil.isnull2(textArr[1]))
		            	textArr[1] = DateUtil.dateToString(new java.util.Date(), 4);
		            for(int i = 0; i<textArr.length; i++)
		            	graphics2D.drawString(textArr[i], width2, height2+(i*18));
				}
				
				if (model == 1) {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
							(destWidth / 2) - (width / 2), (destHeight / 2) - (height / 2), null);
				} else if (model == 2) {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH),
							0, 0, destWidth, destHeight, null);
				} else {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH),
							0, 0, destWidth, destHeight, null);
				}
				
				if (!StrUtil.isnull2(logoText)){
					graphics2D.setColor(Color.red);
		            // 设置 Font
					graphics2D.setFont(new Font("宋体", Font.BOLD, 12));
					//float alpha = 85/100F;
					//graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			            // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .

		            int width2 = destWidth*2/3;
		            int height2 = destHeight*9/10;
		            String[] textArr = StrUtil.splitString(logoText, '\n');
		            if (textArr.length > 1 && StrUtil.isnull2(textArr[1]))
		            	textArr[1] = DateUtil.dateToString(new java.util.Date(), 4);
		            for(int i = 0; i<textArr.length; i++)
		            	graphics2D.drawString(textArr[i], width2, height2+(i*18));
				}

				imageOutputStream = ImageIO.createImageOutputStream(destFile);
				imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
						.next();
				imageWriter.setOutput(imageOutputStream);
				ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
				imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
				imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
				imageOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
				if (imageWriter != null) {
					imageWriter.dispose();
				}
				if (imageOutputStream != null) {
					try {
						imageOutputStream.close();
					} catch (IOException e) {
					}
				}
			}
		} else {
			IMOperation operation = new IMOperation();
			operation.thumbnail(destWidth, destHeight);
			operation.gravity("center");
			operation.background(toHexEncoding(BACKGROUND_COLOR));
			operation.extent(destWidth, destHeight);
			operation.quality((double) DEST_QUALITY);
			operation.addImage(srcFile.getPath());
			operation.addImage(destFile.getPath());
			if (type == Type.graphicsMagick) {
				ConvertCmd convertCmd = new ConvertCmd(true);
				if (graphicsMagickPath != null) {
					convertCmd.setSearchPath(graphicsMagickPath);
				}
				try {
					convertCmd.run(operation);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IM4JavaException e) {
					e.printStackTrace();
				}
			} else {
				ConvertCmd convertCmd = new ConvertCmd(false);
				if (imageMagickPath != null) {
					convertCmd.setSearchPath(imageMagickPath);
				}
				try {
					convertCmd.run(operation);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IM4JavaException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void zoom(InputStream inputStream, File destFile, int destWidth, int destHeight, int model) {
		zoom(inputStream, destFile, destWidth, destHeight, model, null);
	}
	
	public static void zoom(InputStream inputStream, File destFile, int destWidth, int destHeight, int model, String logoText) {
		Assert.notNull(inputStream);
		Assert.notNull(destFile);
		Assert.state(destWidth > 0);
		Assert.state(destHeight > 0);
		if (type == Type.jdk) {
			Graphics2D graphics2D = null;
			ImageOutputStream imageOutputStream = null;
			ImageWriter imageWriter = null;
			try {
				inputStream.mark(0);
				BufferedImage srcBufferedImage = ImageIO.read(inputStream);
				inputStream.reset();
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();
				int width = destWidth;
				int height = destHeight;
				if (model == 1) {
					if (srcHeight >= srcWidth) {
						width = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
					} else {
						height = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
					}
				} else if (model == 2) {
					if ((srcWidth * 1.0) / destWidth < (srcHeight * 1.0) / destHeight) {
						if (srcWidth > destWidth) {
							destHeight = Integer.parseInt(
									new java.text.DecimalFormat("0").format(srcHeight * destWidth / (srcWidth * 1.0)));
						}
					} else {
						if (srcHeight > destHeight) {
							destWidth = Integer.parseInt(
									new java.text.DecimalFormat("0").format(srcWidth * destHeight / (srcHeight * 1.0)));
						}
					}
				} else {
					width = srcWidth;
					height = srcHeight;
					int x = 0, y = 0;
					if (destWidth * 1.0 / srcWidth > destHeight * 1.0 / srcHeight) {
						// 截去高度
						height = (int) Math.round(destHeight * 1.0 * srcWidth / destWidth);
						y = (int) Math.round((srcHeight - height) / 2.0);
					} else if (destWidth * 1.0 / srcWidth < destHeight * 1.0 / srcHeight) {
						// 截去宽度
						width = (int) Math.round(destWidth * 1.0 * srcHeight / destHeight);
						x = (int) Math.round((srcWidth - width) / 2.0);
					}
					Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
					ImageReader reader = (ImageReader) readers.next();
					reader.setInput(inputStream, true);
					ImageReadParam param = reader.getDefaultReadParam();
					Rectangle rect = new Rectangle(x, y, width, height);
					param.setSourceRegion(rect);
					try {
						BufferedImage srcBufferedImage1 = reader.read(0, param);
						srcBufferedImage = srcBufferedImage1;
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}

				BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				graphics2D = destBufferedImage.createGraphics();
				graphics2D.setBackground(BACKGROUND_COLOR);
				graphics2D.clearRect(0, 0, destWidth, destHeight);
				if (model == 1) {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
							(destWidth / 2) - (width / 2), (destHeight / 2) - (height / 2), null);
				} else if (model == 2) {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH),
							0, 0, destWidth, destHeight, null);
				} else {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH),
							0, 0, destWidth, destHeight, null);
				}
				
				if (!StrUtil.isnull2(logoText)){
					graphics2D.setColor(Color.red);
		            // 设置 Font
					graphics2D.setFont(new Font("宋体", Font.BOLD, 12));
					//float alpha = 85/100F;
					//graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			            // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .

		            int width2 = destWidth*2/3;
		            int height2 = destHeight*9/10;
		            String[] textArr = StrUtil.splitString(logoText, '\n');
		            if (textArr.length > 1 && StrUtil.isnull2(textArr[1]))
		            	textArr[1] = DateUtil.dateToString(new java.util.Date(), 4);
		            for(int i = 0; i<textArr.length; i++)
		            	graphics2D.drawString(textArr[i], width2, height2+(i*18));
				}

				imageOutputStream = ImageIO.createImageOutputStream(destFile);
				imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
						.next();
				imageWriter.setOutput(imageOutputStream);
				ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
				imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
				imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
				imageOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
				if (imageWriter != null) {
					imageWriter.dispose();
				}
				if (imageOutputStream != null) {
					try {
						imageOutputStream.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
	/*
	public static void zoom(InputStream inputStream, String logoText) {
		ImageUtils.zoom(inputStream, -1, -1, 2, logoText);
	}
	
	public static void zoom(InputStream inputStream, int destWidth, int destHeight, int model, String logoText) {
		Assert.notNull(inputStream);
		if (type == Type.jdk) {
			Graphics2D graphics2D = null;
			try {
				inputStream.mark(0);
				BufferedImage srcBufferedImage = ImageIO.read(inputStream);
				inputStream.reset();
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();
				int width = destWidth = destWidth>0?destWidth:srcWidth;
				int height = destHeight= destHeight>0?destHeight:srcHeight;
				if (model == 1) {
					if (srcHeight >= srcWidth) {
						width = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
					} else {
						height = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
					}
				} else if (model == 2) {
					if ((srcWidth * 1.0) / destWidth < (srcHeight * 1.0) / destHeight) {
						if (srcWidth > destWidth) {
							destHeight = Integer.parseInt(
									new java.text.DecimalFormat("0").format(srcHeight * destWidth / (srcWidth * 1.0)));
						}
					} else {
						if (srcHeight > destHeight) {
							destWidth = Integer.parseInt(
									new java.text.DecimalFormat("0").format(srcWidth * destHeight / (srcHeight * 1.0)));
						}
					}
				} else {
					width = srcWidth;
					height = srcHeight;
					int x = 0, y = 0;
					if (destWidth * 1.0 / srcWidth > destHeight * 1.0 / srcHeight) {
						// 截去高度
						height = (int) Math.round(destHeight * 1.0 * srcWidth / destWidth);
						y = (int) Math.round((srcHeight - height) / 2.0);
					} else if (destWidth * 1.0 / srcWidth < destHeight * 1.0 / srcHeight) {
						// 截去宽度
						width = (int) Math.round(destWidth * 1.0 * srcHeight / destHeight);
						x = (int) Math.round((srcWidth - width) / 2.0);
					}
					Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
					ImageReader reader = (ImageReader) readers.next();
					reader.setInput(inputStream, true);
					ImageReadParam param = reader.getDefaultReadParam();
					Rectangle rect = new Rectangle(x, y, width, height);
					param.setSourceRegion(rect);
					try {
						BufferedImage srcBufferedImage1 = reader.read(0, param);
						srcBufferedImage = srcBufferedImage1;
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}

				BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				graphics2D = destBufferedImage.createGraphics();
				graphics2D.setBackground(BACKGROUND_COLOR);
				graphics2D.clearRect(0, 0, destWidth, destHeight);
				if (model == 1) {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
							(destWidth / 2) - (width / 2), (destHeight / 2) - (height / 2), null);
				} else if (model == 2) {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH),
							0, 0, destWidth, destHeight, null);
				} else {
					graphics2D.drawImage(srcBufferedImage.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH),
							0, 0, destWidth, destHeight, null);
				}
				
				if (!StrUtil.isnull2(logoText)){
					graphics2D.setColor(Color.red);
		            // 设置 Font
					graphics2D.setFont(new Font("宋体", Font.BOLD, 12));
		            int width2 = destWidth*2/3;
		            int height2 = destHeight*9/10;
		            String[] textArr = StrUtil.splitString(logoText, '\n');
		            if (textArr.length > 1 && StrUtil.isnull2(textArr[1]))
		            	textArr[1] = DateUtil.dateToString(new java.util.Date(), 4);
		            for(int i = 0; i<textArr.length; i++)
		            	graphics2D.drawString(textArr[i], width2, height2+(i*18));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
			}
		}
	}
	*/
	
	 /**
     * 给图片添加水印、可设置水印图片旋转角度
     * 
     * @param iconPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            水印图片旋转角度
     * @param width
     *            宽度(与左相比)
     * @param height
     *            高度(与顶相比)
     * @param clarity
     *            透明度(小于1的数)越接近0越透明
     */
    public static void waterMarkImageByIcon(String iconPath, String srcImgPath,
            String targerPath, int degree, int width, int height,
            int clarity) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            if (degree != 0 ) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 得到Image对象。
            Image img = imgIcon.getImage();
            float alpha = clarity/100F; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            // 表示水印图片的位置
            if (width < 0)
            	width += srcImg.getWidth(null);
            if (height < 0)
            	height += srcImg.getHeight(null);
            
            g.drawImage(img, width, height, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(targerPath);
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
           // System.out.println("添加水印图片完成!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 给图片添加水印、可设置水印图片旋转角度
     * 
     * @param logoText
     *            水印文字
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            水印图片旋转角度
     * @param width
     *            宽度(与左相比)
     * @param height
     *            高度(与顶相比)
     * @param clarity
     *            透明度(小于1的数)越接近0越透明
     */
    
    public static void waterMarkByText(InputStream inputStream, File targerFile, String logoText){
    	waterMarkByText(inputStream, targerFile, logoText,0, 0, 0 ,0, Color.red, null);
    }
    
    public static void waterMarkByText(InputStream inputStream, File targerFile, String logoText,int degree, int width, int height,int clarity, Color color, Font font){
		Assert.notNull(inputStream);
		Graphics2D graphics2D = null;
		OutputStream os = null;
		try {
			inputStream.mark(0);
			BufferedImage srcBufferedImage = ImageIO.read(inputStream);
			inputStream.reset();
			int srcWidth = srcBufferedImage.getWidth();
			int srcHeight = srcBufferedImage.getHeight();
			graphics2D = srcBufferedImage.createGraphics();
			
			if (!StrUtil.isnull2(logoText)){
				if (color == null)
					color = Color.red;
				graphics2D.setColor(color);
	            // 设置 Font
				if (font == null)
					font = new Font("宋体", Font.BOLD, 12);
				if (clarity>0){
					float alpha = clarity/100F;
					graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
				}
	            
				graphics2D.setFont(font);
				
				if (width ==0)
					width = srcWidth*2/3;
				if (height == 0)
					height = srcHeight*9/10;
	            
	            if (width < 0)
	            	width += srcWidth;
	            if (height < 0)
	            	height += srcHeight;
				
	            String[] textArr = StrUtil.splitString(logoText, '\n');
	            if (textArr.length > 1 && StrUtil.isnull2(textArr[1]))
	            	textArr[1] = DateUtil.dateToString(new java.util.Date(), 4);
	            for(int i = 0; i<textArr.length; i++)
	            	graphics2D.drawString(textArr[i], width, height+(i*18));
			}
			os = new FileOutputStream(targerFile);
	            // 生成图片
	        ImageIO.write(srcBufferedImage, "JPG", os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (graphics2D != null) {
				graphics2D.dispose();
			}
		}
    }
    
    public static void waterMarkByText(File srcImgFile,File targerFile,String logoText) {
    	waterMarkByText(srcImgFile,targerFile, logoText, 0, 0, 0, 0, Color.red);
    }
    public static void waterMarkByText(File srcImgFile, File targerFile, String logoText, int degree, int width, int height,
            int clarity, Color color) {
    	// 主图片的路径
        InputStream is = null;
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(srcImgFile);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            
            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            if (degree != 0) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 设置颜色
            if (color == null)
            	color = Color.red;
            g.setColor(color);
            // 设置 Font
            g.setFont(new Font("宋体", Font.BOLD, 24));
            if (clarity > 0){
            	float alpha = clarity/100F;
            	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            }
            // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
            
			if (width ==0)
				width = srcImg.getWidth(null)*2/3;
			if (height == 0)
				height = srcImg.getHeight(null)*9/10;
			
            if (width < 0)
            	width += srcImg.getWidth(null);
            if (height < 0)
            	height += srcImg.getHeight(null);
            
            String[] textArr = StrUtil.splitString(logoText, '\n');
            if (textArr.length > 1 && StrUtil.isnull2(textArr[1]))
            	textArr[1] = DateUtil.dateToString(new java.util.Date(), 4);
            for(int i = 0; i<textArr.length; i++)
            	g.drawString(textArr[i], width, height+(i*24));
            
            g.dispose();
            os = new FileOutputStream(targerFile);
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
            //System.out.println("添加水印文字完成!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void waterMarkByText(String logoText, String srcImgPath,
            String targerPath, int degree, int width, int height,
            int clarity) {
    	waterMarkByText(logoText, srcImgPath,targerPath, degree, width, height, clarity, Color.red);
    }
    public static void waterMarkByText(String logoText, String srcImgPath,
            String targerPath, int degree, int width, int height,
            int clarity, Color color) {
        // 主图片的路径
        InputStream is = null;
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            if (degree != 0) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 设置颜色
            if (color == null)
            	color = Color.red;
            g.setColor(color);
            // 设置 Font
            g.setFont(new Font("宋体", Font.BOLD, 24));
            float alpha = clarity/100F;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
            if (width < 0)
            	width += srcImg.getWidth(null);
            if (height < 0)
            	height += srcImg.getHeight(null);
            String[] textArr = StrUtil.splitString(logoText, '\n');
            if (textArr.length > 1 && StrUtil.isnull2(textArr[1]))
            	textArr[1] = DateUtil.dateToString(new java.util.Date(), 4);
            for(int i = 0; i<textArr.length; i++)
            	g.drawString(textArr[i], width, height+(i*24));
            
            g.dispose();
            os = new FileOutputStream(targerPath);
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
            //System.out.println("添加水印文字完成!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


	/**
	 * 添加水印
	 *
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @param watermarkFile
	 *            水印文件
	 * @param watermarkPosition
	 *            水印位置
	 * @param alpha
	 *            水印透明度
	 */
	public static void addWatermark(File srcFile, File destFile, File watermarkFile,
			WatermarkPosition watermarkPosition, int alpha) {
		Assert.notNull(srcFile);
		Assert.notNull(destFile);
		Assert.state(alpha >= 0);
		Assert.state(alpha <= 100);
		if (watermarkFile == null || !watermarkFile.exists() || watermarkPosition == null
				|| watermarkPosition == WatermarkPosition.no) {
			try {
				FileUtils.copyFile(srcFile, destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if (type == Type.jdk) {
			Graphics2D graphics2D = null;
			ImageOutputStream imageOutputStream = null;
			ImageWriter imageWriter = null;
			try {
				BufferedImage srcBufferedImage = ImageIO.read(srcFile);
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();
				BufferedImage destBufferedImage = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);
				graphics2D = destBufferedImage.createGraphics();
				graphics2D.setBackground(BACKGROUND_COLOR);
				graphics2D.clearRect(0, 0, srcWidth, srcHeight);
				graphics2D.drawImage(srcBufferedImage, 0, 0, null);
				graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha / 100F));

				BufferedImage watermarkBufferedImage = ImageIO.read(watermarkFile);
				int watermarkImageWidth = watermarkBufferedImage.getWidth();
				int watermarkImageHeight = watermarkBufferedImage.getHeight();
				int x = srcWidth - watermarkImageWidth;
				int y = srcHeight - watermarkImageHeight;
				if (watermarkPosition == WatermarkPosition.topLeft) {
					x = 0;
					y = 0;
				} else if (watermarkPosition == WatermarkPosition.topRight) {
					x = srcWidth - watermarkImageWidth;
					y = 0;
				} else if (watermarkPosition == WatermarkPosition.center) {
					x = (srcWidth - watermarkImageWidth) / 2;
					y = (srcHeight - watermarkImageHeight) / 2;
				} else if (watermarkPosition == WatermarkPosition.bottomLeft) {
					x = 0;
					y = srcHeight - watermarkImageHeight;
				} else if (watermarkPosition == WatermarkPosition.bottomRight) {
					x = srcWidth - watermarkImageWidth;
					y = srcHeight - watermarkImageHeight;
				}
				graphics2D.drawImage(watermarkBufferedImage, x, y, watermarkImageWidth, watermarkImageHeight, null);

				imageOutputStream = ImageIO.createImageOutputStream(destFile);
				imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName()))
						.next();
				imageWriter.setOutput(imageOutputStream);
				ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
				imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				imageWriteParam.setCompressionQuality(DEST_QUALITY / 100F);
				imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
				imageOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
				if (imageWriter != null) {
					imageWriter.dispose();
				}
				if (imageOutputStream != null) {
					try {
						imageOutputStream.close();
					} catch (IOException e) {
					}
				}
			}
		} else {
			String gravity = "SouthEast";
			if (watermarkPosition == WatermarkPosition.topLeft) {
				gravity = "NorthWest";
			} else if (watermarkPosition == WatermarkPosition.topRight) {
				gravity = "NorthEast";
			} else if (watermarkPosition == WatermarkPosition.center) {
				gravity = "Center";
			} else if (watermarkPosition == WatermarkPosition.bottomLeft) {
				gravity = "SouthWest";
			} else if (watermarkPosition == WatermarkPosition.bottomRight) {
				gravity = "SouthEast";
			}
			IMOperation operation = new IMOperation();
			operation.gravity(gravity);
			operation.dissolve(alpha);
			operation.quality((double) DEST_QUALITY);
			operation.addImage(watermarkFile.getPath());
			operation.addImage(srcFile.getPath());
			operation.addImage(destFile.getPath());
			if (type == Type.graphicsMagick) {
				CompositeCmd compositeCmd = new CompositeCmd(true);
				if (graphicsMagickPath != null) {
					compositeCmd.setSearchPath(graphicsMagickPath);
				}
				try {
					compositeCmd.run(operation);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IM4JavaException e) {
					e.printStackTrace();
				}
			} else {
				CompositeCmd compositeCmd = new CompositeCmd(false);
				if (imageMagickPath != null) {
					compositeCmd.setSearchPath(imageMagickPath);
				}
				try {
					compositeCmd.run(operation);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IM4JavaException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * <p>
	 * Title: cutImage
	 * </p>
	 * <p>
	 * Description: 根据原图与裁切size截取局部图片
	 * </p>
	 * 
	 * @param srcImg
	 *            源图片
	 * @param output
	 *            图片输出流
	 * @param rect
	 *            需要截取部分的坐标和大小
	 */
	public static void cutImage(File srcImg, File destFile, java.awt.Rectangle rect) {
		if (srcImg.exists()) {
			java.io.FileInputStream fis = null;
			ImageInputStream iis = null;
			try {
				fis = new FileInputStream(srcImg);
				// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
				// JPEG, WBMP, GIF, gif]
				String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
				String suffix = null;
				// 获取图片后缀
				if (srcImg.getName().indexOf(".") > -1) {
					suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
				} // 类型和图片后缀全部小写，然后判断后缀是否合法
				if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase() + ",") < 0) {
					return;
				}
				// 将FileInputStream 转换为ImageInputStream
				iis = ImageIO.createImageInputStream(fis);
				// 根据图片类型获取该种类型的ImageReader
				ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
				reader.setInput(iis, true);
				ImageReadParam param = reader.getDefaultReadParam();
				param.setSourceRegion(rect);
				BufferedImage bi = reader.read(0, param);
				OutputStream output = new java.io.FileOutputStream(destFile);
				ImageIO.write(bi, suffix, output);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (iis != null)
						iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
		}
	}

	// public static void cutImage(File srcImg, OutputStream output, int x, int
	// y, int width, int height){
	// cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height));
	// }
	//
	// public static void cutImage(File srcImg, String destImgPath,
	// java.awt.Rectangle rect){
	// File destImg = new File(destImgPath);
	// if(destImg.exists()){
	// String p = destImg.getPath();
	// try {
	// if(!destImg.isDirectory()) p = destImg.getParent();
	// if(!p.endsWith(File.separator)) p = p + File.separator;
	// cutImage(srcImg, new java.io.FileOutputStream(p + "cut_" + new
	// java.util.Date().getTime() + "_" + srcImg.getName()), rect);
	// } catch (FileNotFoundException e) {
	// }
	// }
	// }
	//
	// public static void cutImage(File srcImg, String destImg, int x, int y,
	// int width, int height){
	// cutImage(srcImg, destImg, new java.awt.Rectangle(x, y, width, height));
	// }
	//
	// public static void cutImage(String srcImg, String destImg, int x, int y,
	// int width, int height){
	// cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width,
	// height));
	// }

	/**
	 * 初始化
	 */
	public static void initialize() {
	}

	/**
	 * 转换颜色为十六进制代码
	 * 
	 * @param color
	 *            颜色
	 * @return 十六进制代码
	 */
	private static String toHexEncoding(Color color) {
		String R, G, B;
		StringBuffer stringBuffer = new StringBuffer();
		R = Integer.toHexString(color.getRed());
		G = Integer.toHexString(color.getGreen());
		B = Integer.toHexString(color.getBlue());
		R = R.length() == 1 ? "0" + R : R;
		G = G.length() == 1 ? "0" + G : G;
		B = B.length() == 1 ? "0" + B : B;
		stringBuffer.append("#");
		stringBuffer.append(R);
		stringBuffer.append(G);
		stringBuffer.append(B);
		return stringBuffer.toString();
	}

	/**
	 * 水印位置
	 */
	public enum WatermarkPosition {

		/** 无 */
		no,

		/** 左上 */
		topLeft,

		/** 右上 */
		topRight,

		/** 居中 */
		center,

		/** 左下 */
		bottomLeft,

		/** 右下 */
		bottomRight
	}
	//base64字符串转化成图片
	public static ImageModel GetImageInputStream(String bsae64) throws Exception {
		ImageModel model = new ImageModel();
		try {
			int i = bsae64.indexOf(',');
			if(i>0){
				String bf = bsae64.substring(0, i);
				bsae64 = bsae64.substring(i+1);
				int p = bf.indexOf('/');
				int z = bf.indexOf(';');
				model.setSuffix(bf.substring(p+1,z));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		model.setInputStream(new ByteArrayInputStream(Base64.decodeBase64(bsae64)));
		return model;
	}
}
