package com.hibo.cms.util.video;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.hibo.bas.classFile.ClassFileUtil;

/**
 * <p>标题：视频格式转换</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月18日 下午5:14:57</p>
 * <p>类全名：com.hibo.cms.util.video.VideoFormatChange</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class VideoFormatChange {

	public static boolean process(String inPath, String outPath) {
		String fileName = checkfile(inPath);
		if(null==fileName){
			System.out.println(inPath + " is not file");
			return false;
		}
		int type = checkContentType(inPath);
		boolean status = false;
		if (type == 0) {
			status = processM3U8(inPath, outPath);
		} 
		return status;
	}

	/**
	 * @功能:视频检查格式
	 * @描述:ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等)
	 * @作者:周雷
	 * @时间:2016年3月18日 下午1:50:58
	 * @return
	 */
	public static int checkContentType(String inPath) {
		String type = inPath.substring(inPath.lastIndexOf(".") + 1, inPath.length()).toLowerCase();
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		}
		return 9;
	}

	public static String checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return null;
		}
		return file.getName();
	}
	
	private static boolean processM3U8(String inPath, String outPath) {
		 List<String> commend = new ArrayList<String>();
//		 String ffmpegPath = ClassFileUtil.getFileRealPath("hibo/ffmpeg/ffmpeg.exe");
		 commend.add("E:/ffmpeg/ffmp/ffmpeg.exe");
		 commend.add("-i");
		 commend.add(inPath);
		 commend.add("-strict");
		 commend.add("-2");
		 commend.add("-c:v");
		 commend.add("libx264");
		 commend.add("-c:a");
		 commend.add("aac");
		 commend.add("-f");
		 commend.add("hls");
		 commend.add("m3u8");
		 commend.add(outPath + ".m3u8");
		 try {
			 ProcessBuilder builder = new ProcessBuilder(commend);
			 builder.command(commend);
			 builder.start();
		 return true;
		 } catch (Exception e) {
		 e.printStackTrace();
		 return false;
		 }
	}
	public static String getFileName(String path, String name) {
		Random random = new Random();
		String ra = "";
		for (int i = 0; i < 4; i++) {
			ra = ra + random.nextInt(10);
		}
		String newName = name + "-" + (new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss").format(new Date()) + ra);
		return path + newName;
	}
	
	
	
	/**
	 * 例：ffmpeg -y -i "test.avi" -title "Test" -vcodec xvid -s 368x208 -r 29.97 - b 1500 -acodec aac -ac 2 -ar 24000 -ab 128 -vol 200 -f psp -muxvb 768 "test.***" 
	 * # 参数解释： 
	 * -y（覆盖输出文件，即如果1.***文件已经存在的话，不经提示就覆盖掉了） 
	 * -i "1.avi"（输入文件是和ffmpeg在同一目录下的1.avi文件，可以自己加路径，改名字） 
	 * -title "Test"（在PSP中显示的影片的标题） 
	 * -vcodec xvid（使用XVID编码压缩视频，不能改的） 
	 * -s 368x208（输出的分辨率为368x208，注意片源一定要是16:9的不然会变形） 
	 * -r 29.97（帧数，一般就用这个吧） 
	 * -b 1500（视频数据流量，用-b xxxx的指令则使用固定码率，数字随便改，1500以上没效果；还可以用动态码率如：-qscale 4和-qscale 6，4的质量比6高） 
	 * -acodec aac（音频编码用AAC） 
	 * -ac 2（声道数1或2） 
	 * -ar 24000（声音的采样频率，好像PSP只能支持24000Hz） 
	 * -ab 128（音频数据流量，一般选择32、64、96、128） 
	 * -vol 200（200%的音量，自己改） 
	 * -f psp（输出psp专用格式） 
	 * -muxvb 768（好像是给PSP机器识别的码率，一般选择384、512和768，我改成1500，PSP就说文件损坏了） 
	 * "test.***"（输出文件名，也可以加路径改文件名）
	 * */
	public static void main(String[] args) {
		VideoFormatChange.processM3U8("E:/ffmpeg/qpdpublicity.mp4", "E:/ffmpeg/output2/qpdpublicity");
		System.out.println("OK");
	}
}
