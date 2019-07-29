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

public class PhotoSynthesisStaticUtil {

    /**
     * 记录日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(PhotoSynthesisStaticUtil.class);

    /**
     * 定义全局图片对象
     */
    private static BufferedImage image;

    /**
     * 图片得宽
     */
    private static final Integer maxWidth = 1604;

    /**
     * 图片得高
     */
    private static final Integer maxHeigth = 1070;

    /**
     * 合成之后得宽
     */
    private static final Integer canvasWidth = 1680;

    /**
     * 合成之后得高
     */
    private static final Integer canvasHeight = 1328;

    public static String graphicsGeneration(String imgurl, String codeUrl, String activityName, String activityInfoId, String localhostUrl) {
        File file = new File(imgurl);
        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(file);
        } catch (Exception e) {
        }
        int canvasHeights=canvasHeight+100;

        image = new BufferedImage(canvasWidth, canvasHeights, BufferedImage.TYPE_INT_RGB);

        // 设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, canvasWidth, canvasHeights);

        // ***********************用户照片**************
        Graphics mainPic = image.getGraphics();
        int padding = canvasHeights - maxHeigth;
        if (srcImage != null) {

            mainPic.drawImage(srcImage, 36, 22, maxWidth, maxHeigth, null);
            mainPic.dispose();
        }
        // ***********************设置最下方的用户编号和姓名****************
        Graphics2D tip = image.createGraphics();
        // 设置区域颜色
        tip.setColor(Color.white);
        // 填充区域并确定区域大小位置

        // 设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.black);

//        System.out.println(padding);
        // 获取下边框的高度
        int _height = canvasHeights - 28 - maxHeigth;
        Font tipFont = null;
        tipFont = new Font("宋体", 0, _height / 4);
        tip.setFont(tipFont);
        // 标题长度小于等于15时
//        String str = "【照片直播】金地集团30周年之爷傲视群雄安徽立刻回答说的撒旦撒的撒的撒的撒打算的撒娇";
        String str="【照片直播】"+activityName;
        if (str.trim().length() <= 15) {
            tip.drawString(str, 10, canvasHeights);
        }
        // 标题长度大于15时
        if (str.trim().length() > 15) {
            String item = null;
            item = str.substring(0, 15);
            tip.drawString(item, 11, maxHeigth + 25 + padding / 3);
            String temp = null;
            if (str.trim().length() > 25) {
                temp = str.substring(item.length(), (item.length() + 5)) + "...";
            } else {
                temp = str.substring(item.length());
            }
            tip.drawString(temp, 36, canvasHeights - 70);
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
            int a = canvasHeights - _height;

            // 显示图片及图片定位
            int x = (canvasWidth - maxWidth) / 2 + maxWidth - (_height + (canvasWidth - maxWidth) / 2);
            int y = maxHeigth + 30;
            qrcode.drawImage(bimg, x+36+10, y, _height, _height, null);
            qrcode.dispose();
        }

//        System.out.println("横图片的字体大小" + tipFont.getSize());

//        String path="G:\\OSSPhoto\\person.jpg";
        String path=localhostUrl+ File.separator+activityInfoId+".jpg";
        createImage(path);
        logger.info("生成分享图片成功");
        return path;
    }

    // 生成图片文件
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
        PhotoSynthesisStaticUtil cg = new PhotoSynthesisStaticUtil();
        String path = "G:\\OSSPhoto\\TIM图片20190711114340.png";
        String codeUrl = "G:\\OSSPhoto\\微信图片_20190710171257.jpg";
        cg.graphicsGeneration(path, codeUrl,null,null,null);
    }

}
