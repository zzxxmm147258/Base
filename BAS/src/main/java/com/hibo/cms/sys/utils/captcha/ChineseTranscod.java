package com.hibo.cms.sys.utils.captcha;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月19日 上午11:32:50</p>
 * <p>类全名：com.hibo.cms.sys.utils.captcha.ChineseTranscod</p>
 * 作者：lei zhou
 * 初审：
 * 复审：
 */
public class ChineseTranscod {
    private static Random random = new Random();
    public static String randomChinese(){

        String str = null;

        int hightPos, lowPos; // 定义高低位


        hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值

        lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值

        byte[] b = new byte[2];

        b[0] = (new Integer(hightPos).byteValue());

        b[1] = (new Integer(lowPos).byteValue());
        //转成中文
        try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return str;
     }
}