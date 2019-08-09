package cc.black.common.oss;

import com.aliyun.oss.OSSClient;

/**
 * oss configure
 * @author tanye
 *
 */
public class Configure {
	
	/*** 访问域名 endpoint */
	public final static String BUCKET_ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
	/*** 访问密钥 key accessKeyId*/
	public final static String BUCKET_ACCESS_KEY_ID = "LTAIniKNCWLVJYZx";
	/*** 访问密钥 口令  accessKeySecret*/
	public final static String BUCKET_ACCESS_KEY_SECRET = "L6M3NrQAMOKAXEGiHxDwB0btPDtnVq";
	/***   bucket*/
	public final static String BUCKET_CUT ="plxai";


	/*** 访问域名 endpoint */
	public final static String ENDPOINT = "oss-cn-shanghai.aliyuncs.com";
	/*** 访问密钥 key accessKeyId*/
	public final static String ACCESS_KEY_ID = "LTAItPFngyYwTwAA";
	/*** 访问密钥 口令  accessKeySecret*/
	public final static String ACCESS_KEY_SECRET = "VdQLdYmQllogNZ9TUHdmbXCrC440Yc";
	/***   bucket*/
	public final static String CUT = "yyq-j";

	public static void main(String[] args) {
//		// endpoint以杭州为例，其它region请按实际情况填写
//		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//		// accessKey请登录https://ak-console.aliyun.com/#/查看
//		String accessKeyId = Configure.ACCESS_KEY_ID;
//		String accessKeySecret = Configure.ACCESS_KEY_SECRET;
//		// 创建OSSClient实例
//		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//		// 删除bucket
////		ossClient.deleteBucket("dbtt");
//		ossClient.createBucket("cloud-api");
//		// 关闭clientcloud-api/100/yu-bg-3.jpg
//		ossClient.shutdown();
	}

}
