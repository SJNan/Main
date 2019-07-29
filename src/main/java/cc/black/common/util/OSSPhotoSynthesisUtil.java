package cc.black.common.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class OSSPhotoSynthesisUtil {

    /*** 记录日志对象  */
    private static Logger logger = LoggerFactory.getLogger(OSSPhotoSynthesisUtil.class);

    /**
     * 图片合成
     * @param localhost 服务器路径
     * @param fileUrl OSS路径
     * @param activityInfoId 活动id
     * @param activityName 活动名称
     * @param activityPublicityMapUrl OSS活动宣传片路径
     * @param activityQeCodeUrl OSS活动二维码路径
     * @return
     */
    public static String photoSynthesis(String localhost, String fileUrl, String activityInfoId, String activityName, String activityPublicityMapUrl, String activityQeCodeUrl, String fkCustomerInfoId){
        long startTime = System.currentTimeMillis();
        logger.info("生成活动分享图片流程开始");
        logger.info("获取需要合成图片信息:(活动id:"+activityInfoId+",活动名称:"+activityName+",活动宣传片路径:"+activityPublicityMapUrl+",活动二维码:"+activityQeCodeUrl+",商户Id:"+fkCustomerInfoId+")");
        String path=localhost+ File.separator+activityInfoId;
        File file=new File(path);
        logger.info("第一步:");
        createFolder(file);
        logger.info("第二步:");
        String localhostPublicityMapUrl=downloadPublicityMapUrl(file,activityInfoId,activityPublicityMapUrl);
        logger.info("第三步:");
        String localhostQeCodeUrl=downloadQeCodeUrl(file,activityInfoId,activityQeCodeUrl);
        logger.info("第四步:");
        logger.info("开始图片合成");
        String loclahostPhotoSynthesis= PhotoSynthesisStaticUtil.graphicsGeneration(localhostPublicityMapUrl,localhostQeCodeUrl,activityName,activityInfoId,path);
        logger.info("结束图片合成路径为:"+loclahostPhotoSynthesis);
        logger.info("第五步:");
        String OSSUrl=fileUrl+fkCustomerInfoId+"/"+activityInfoId;
        String OSSupLoadUrl=AliyunOSSUtil.upLoad(new File(loclahostPhotoSynthesis),OSSUrl);
        logger.info("第六步:");
        logger.info("开始删除服务器临时文件");
        deleteFolder(file);
        logger.info("结束删除服务器临时文件路径为:"+file.getPath());
        logger.info("生成活动分享图片流程结束");
        long endTime = System.currentTimeMillis();
        logger.info("生成分享图片总共用时： " + ((endTime - startTime) / 1000) + " 秒");
        return OSSupLoadUrl;
    }

    /**
     * 创建文件
     * @param file
     */
    private static void createFolder(File file){
        try{
            boolean flag=file.mkdirs();
            if (flag==false){
                logger.info("文件夹已存在- - ->跳过创建文件夹步骤");
            }else{
                logger.info("创建文件夹路径:"+file.getPath());
            }
        }catch (Exception e){
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 删除文件及目录
     * @param folder
     */
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    /**
     * OSS下载活动宣传片到服务器
     * @param file 服务器路径
     * @param activityInfoId 活动id
     * @param activityPublicityMapUrl OSS活动宣传片路径
     * @return 返回服务器活动宣传片路径
     */
    private static String downloadPublicityMapUrl(File file, String activityInfoId, String activityPublicityMapUrl){
        String publicitymap=null;
        try {
            logger.info("开始OSS下载活动宣传图片到服务器");
            String suffx = activityPublicityMapUrl.split("\\.")[1].toLowerCase();
            publicitymap=file.getPath()+ File.separator+activityInfoId+"(1)."+suffx;
            AliyunOSSUtil.downloadFile(activityPublicityMapUrl,publicitymap);
            logger.info("结束OSS下载活动宣传片到服务器路径为:"+publicitymap);
        }catch (Exception e){
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return publicitymap;
    }

    /**
     * OSS下载活动二维码到服务器
     * @param file 服务器路径
     * @param activityInfoId 活动id
     * @param activityQeCodeUrl OSS活动二维码路径
     * @return 返回服务器活动二维码路径
     */
    private static String downloadQeCodeUrl(File file, String activityInfoId, String activityQeCodeUrl){
        String qrcodepath=null;
        try{
            logger.info("开始OSS下载二维码到服务器");
            String suffx=activityQeCodeUrl.split("\\.")[1].toLowerCase();
            qrcodepath=file.getPath()+ File.separator+activityInfoId+"(2)."+suffx;
            AliyunOSSUtil.downloadFile(activityQeCodeUrl,qrcodepath);
            logger.info("结束OSS下载二维码到服务器路径为:"+qrcodepath);
        }catch (Exception e){
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return qrcodepath;
    }

    /**
     * 获取图片大小转化 B KB MB GB显示
     * @param size
     * @return
     */
    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    public static void main(String[] args) {
        String OSSupLoadUrl= photoSynthesis("G:\\OSSPhoto","SharePictures/","1989",
                "金地集团30周年司庆","SharePictures/微信图片_20190710085544.jpg","SharePictures/微信图片_20190710171257.jpg","2");
        logger.info("OSS路径为:"+OSSupLoadUrl);
    }
}
