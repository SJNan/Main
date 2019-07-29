package cc.black.common.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoSynthesisDynamicUtil {

    /*** 记录日志对象  */
    private static Logger logger = LoggerFactory.getLogger(PhotoSynthesisDynamicUtil.class);

    private static BufferedImage image;

    /**
     * 合成图片
     * @param imgurl 活动的宣传片
     * @param codeUrl 活动二维码
     * @param activityName 活动名称
     * @param activityInfoId 活动id
     * @param localhostUrl 本地路径
     * @return 返回本地合成图片路径
     */
    public static String graphicsGeneration(String imgurl, String codeUrl, String activityName, String activityInfoId, String localhostUrl) {
        File file = new File(imgurl);
        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(file);
        } catch (Exception e) {}
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int padding = 0;
        int canvasWidth = 0;
        int canvasHeight = 0;
        if (width > height) {
            if (width / 2 <= 400) {
                padding = 10;
            } else if (width < 1050) {
                padding = 15;
            } else {
                padding = 40;
            }
            canvasWidth = width + padding * 2;
            canvasHeight = height + padding * 6;
        } else {
            padding = 20;
            canvasWidth = width + padding * 3;
            canvasHeight = height + padding * 10;
        }
        image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
        // 设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, canvasWidth, canvasHeight);
        // ***********************用户照片**************
        Graphics mainPic = image.getGraphics();
        if (srcImage != null) {
            // 显示图片及图片定位
            int y = 0;
            if (width > height) {
                y = (canvasHeight - srcImage.getHeight()) / 2 - padding * 2;
            } else {
                y = (canvasHeight - srcImage.getHeight()) / 2 - padding * 3;
            }
            mainPic.drawImage(srcImage, (canvasWidth - srcImage.getWidth()) / 2, y, srcImage.getWidth(),
                    srcImage.getHeight(), null);
            mainPic.dispose();
        }
        // ***********************设置最下方的用户编号和姓名****************
        Graphics2D tip = image.createGraphics();
        // 设置区域颜色
        tip.setColor(Color.white);
        // 填充区域并确定区域大小位置
        // 设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.black);
        // 获取图片的高度
        int _height = srcImage.getHeight();
        // 获取下边距高度、
        int s = 0;
        if (width > height) {
            s = canvasHeight - _height - ((canvasHeight - srcImage.getHeight()) / 2 - padding * 2);
        } else {
            s = canvasHeight - _height - ((canvasHeight - srcImage.getHeight()) / 2 - padding * 3);
        }
        Font tipFont = null;
        String text=activityName;
        String test=null;
        if (width > height) {
            if (width / 2 <= 400) {
                s = 15;
            } else if (width < 1050) {
                s = 23;
            } else {
                s = s / 3;
            }
            if (text.length()>=20){
                test=text.substring(0,20)+"...";
            }else{
                test=text;
            }
            tipFont = new Font("宋体", 0, s);
            tip.setFont(tipFont);
            tip.drawString("【照片直播】"+test, 10, canvasHeight - padding * 2);
//            System.out.println("横图片的字体大小" + tipFont.getSize());
        } else {
            if (text.length()>=15){
                test=text.substring(0,15)+"...";
            }else{
                test=text;
            }
            tipFont = new Font("宋体", 0, s / 4);
            tip.setFont(tipFont);
            tip.drawString("【照片直播】"+test, 10, canvasHeight - padding * 3);
//            System.out.println("竖图片的字体大小" + tipFont.getSize());
        }
        // ***********************在设置文字旁边添加二维码图片(绘图4)****************
        // 在绘图1创建一个绘图
        Graphics2D qrcode = image.createGraphics();
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(new File(codeUrl));
        } catch (Exception e) {
        }
        if (bimg != null) {
            int a = 0;
            int y = 0;
            if (width > height) {
                a = canvasHeight - _height - ((canvasHeight - srcImage.getHeight()) / 2 - padding * 2);
                y = (canvasHeight - srcImage.getHeight()) / 2 - padding * 2;
            } else {
                a = canvasHeight - _height - ((canvasHeight - srcImage.getHeight()) / 2 - padding * 3);
                y = (canvasHeight - srcImage.getHeight()) / 2 - padding * 3;
            }
            // 显示图片及图片定位
//            System.out.println(a+"/"+y);
//            System.out.println(padding / 2 + srcImage.getWidth() - a + padding);
//            System.out.println(y + srcImage.getHeight());
            qrcode.drawImage(bimg, padding / 2 + srcImage.getWidth() - a + padding, y + srcImage.getHeight(), a, a,
                    null);
            qrcode.dispose();
        }
        localhostUrl=localhostUrl+ File.separator+activityInfoId+".jpg";
        createImage(localhostUrl);
//        createImage("D:\\Downloads\\TEST3JPG - 副本\\person.jpg");
        logger.info("生成分享图片成功");
        return localhostUrl;
    }

    /**
     * 生成图片文件
     * @param fileLocation 保存路径
     */
    public static void createImage(String fileLocation) {
        BufferedOutputStream bos = null;
        if (image != null) {
            try {
                FileOutputStream fos = new FileOutputStream(fileLocation);
                bos = new BufferedOutputStream(fos);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
                encoder.encode(image);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {// 关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        PhotoSynthesisDynamicUtil cg = new PhotoSynthesisDynamicUtil();
        String path = "D:\\Downloads\\TEST3JPG - 副本\\973A1657.JPG";
        String codeUrl = "D:\\Downloads\\TEST3JPG - 副本\\微信图片_20190710171257.jpg";
        PhotoSynthesisDynamicUtil.graphicsGeneration(path, codeUrl,"111111",null,null);
    }

}
