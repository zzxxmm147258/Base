package com.hibo.cms.sys.utils.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * <p>标题：验证码</p>
 * <p>功能：验证码工具类 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月18日 下午6:11:14</p>
 * <p>类全名：com.hibo.cms.sys.utils.captcha.CaptchaUtil</p>
 * 作者：lei zhou
 * 初审：
 * 复审：
 */
public class CaptchaUtil {

	// 随机产生的字符串
	private static final String RANDOM_STRS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final String[] RANDOM_MATH = {"壹","贰","叁","五","陆","柒","捌","玖","零"};
	private static final String[] RANDOM_UNIT = {"","拾","佰","仟"};
	
	private static final String FONT_NAME = "微软雅黑";
	private static final int FONT_SIZE = 18;

	private Random random = new Random();

	private int width = 84;// 图片宽
	private int height = 30;// 图片高
	private int lineNum = 0;// 干扰线数量
	private int strNum = 4;// 随机产生字符数量

	/**
	 * 生成随机图片
	 */
	public BufferedImage genRandomCodeImage(StringBuffer randomCode) {
		// BufferedImage类是具有缓冲区的Image类
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		// 获取Graphics对象,便于对图像进行各种绘制操作
		Graphics g = image.getGraphics();
		// 设置背景色
		g.setColor(getRandColor(255, 255));
		g.fillRect(0, 0, width, height);

		// 设置干扰线的颜色
		g.setColor(getRandColor(110, 120));

		// 绘制干扰线
		for (int i = 0; i < lineNum; i++) {
			drowLine(g);
		}
		//String rand = getRandomchinese();
		//String rand = String.valueOf(getRandomString(random.nextInt(RANDOM_STRS.length())));
		Object[] s = getCal();
		String[] sl = (String[]) s[1];
 		// 绘制随机字符
		g.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
		for (int i = 0; i < strNum; i++) {
			drowString(g,i,sl[i]);
		}
		randomCode.append(s[0]);
		g.dispose();
		return image;
	}

	/**
	 * 给定范围获得随机颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int rad = bc-fc>0?random.nextInt(bc - fc):0;
		int r = fc + rad;
		int g = fc + rad;
		int b = fc + rad;
		return new Color(r, g, b);
	}

	/**
	 * 绘制字符串
	 */
	private void drowString(Graphics g,int i,String rand) {
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		int x = random.nextInt(3);
		int y = random.nextInt(3)-1;
		g.translate(x,y);
		int k = 3;
		if(i%2==0){
			k = 0;
		}
		g.drawString(rand, 20 * i + k, width/strNum);
	}

	protected String getRandomchinese() {
		return ChineseTranscod.randomChinese();
	}
	
	
	protected Object[] getCal() {
		int l = random.nextInt(20);
		int f = random.nextInt(20-l)+l;
		int c = random.nextInt(2);
		Object[] s = new Object[2]; 
		String[] sl = new String[strNum];
		if(c>0){
			s[0] = (f-l)+"";
			sl[0] = f+"";
			sl[1] = "-";
			sl[2] = l+"";
			sl[3] = "";
			s[1] =sl;
		}else{
			s[0] = (f+l)+"";
			sl[0] = f+"";
			sl[1] = "+";
			sl[2] = l+"";
			sl[3] = "";
			s[1] =sl;
		}
		return s;
	}
	/**
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int x0 = random.nextInt(16);
		int y0 = random.nextInt(16);
		g.drawLine(x, y, x + x0, y + y0);
	}

	/**
	 * 获取随机的字符
	 */
	private String getRandomString(int num) {
		return String.valueOf(RANDOM_STRS.charAt(num));
	}
	
}