package cc.black.common.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class OSSPhotoDownload {

    /*** 记录日志对象  */
    private static Logger logger = LoggerFactory.getLogger(OSSPhotoDownload.class);

    public void download(String localhost,String ossUrl){
        long startTime = System.currentTimeMillis();

        logger.info("");
        long endTime = System.currentTimeMillis();
        logger.info("生成分享图片总共用时： " + ((endTime - startTime) / 1000) + " 秒");
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
}
