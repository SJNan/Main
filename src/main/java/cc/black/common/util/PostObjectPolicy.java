package cc.black.common.util;

import cc.deepblack.common.oss.Configure;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PolicyConditions;

import java.io.InputStream;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostObjectPolicy {
	/**
	 * 获取token
	 * @param ossUrl
	 * @return
	 */
	public static Map<String, String> getToken(String ossUrl){
		Map<String, String> respMap = new LinkedHashMap<>();
        String bucket = Configure.BUCKET;
        String dir = ossUrl;
        String host = "http://" + bucket + "." + Configure.ENDPOINT;
        OSSClient client = new OSSClient(Configure.ENDPOINT, Configure.ACCESS_KEY_ID, Configure.ACCESS_KEY_SECRET);
        try { 	
        	long expireTime = 3600;
        	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            respMap.put("accessid", Configure.ACCESS_KEY_ID);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            
        } catch (Exception e) {
        	System.out.println(ossUrl + " 获取令牌失败");
        }
		return respMap;
	}

	/**
	 * 获取token
	 * @param activityInfo
	 * @return
	 */
	public static Map<String, String> getPLXToken(String ossUrl){
		Map<String, String> respMap = new LinkedHashMap<String, String>();
		String bucket = Configure.BUCKET_CUT;
		String dir = ossUrl;
		String host = "http://" + bucket + "." + Configure.ENDPOINT;
		OSSClient client = new OSSClient(Configure.ENDPOINT, Configure.BUCKET_ACCESS_KEY_ID, Configure.BUCKET_ACCESS_KEY_SECRET);
		try {
			long expireTime = 3600;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

			String postPolicy = client.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = client.calculatePostSignature(postPolicy);
			respMap.put("accessid", Configure.BUCKET_ACCESS_KEY_ID);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			respMap.put("dir", dir);
			respMap.put("host", host);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));

		} catch (Exception e) {
			System.out.println(ossUrl + " 获取令牌失败");
		}
		return respMap;
	}
	/**
	 * 获取token
	 * @param
	 * @return
	 */
	public static Map<String, String> getBalanceToken(String ossUrl){
		Map<String, String> respMap = new LinkedHashMap<String, String>();
		String bucket = Configure.BALANCE_BUCKET;
		String dir = ossUrl;
		String host = "http://" + bucket + "." + Configure.ENDPOINT;
		OSSClient client = new OSSClient(Configure.ENDPOINT, Configure.BALANCE_ACCESS_KEY_ID, Configure.BALANCE_ACCESS_KEY_SECRET);
		try {
			long expireTime = 3600;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

			String postPolicy = client.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = client.calculatePostSignature(postPolicy);
			respMap.put("accessid", Configure.BALANCE_ACCESS_KEY_ID);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			respMap.put("dir", dir);
			respMap.put("host", host);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));

		} catch (Exception e) {
			System.out.println(ossUrl + " 获取令牌失败");
		}
		return respMap;
	}
	
	/**
	 * 裁剪图片
	 * @param imgUrl
	 * @param style
	 * @param fileName
	 * @return
	 */
	@Deprecated
	public static String cutImg(String imgUrl, String style, String fileName){
		OSSClient ossClient = new OSSClient(Configure.ENDPOINT, Configure.ACCESS_KEY_ID, Configure.ACCESS_KEY_SECRET);
		try {
			GetObjectRequest request = new GetObjectRequest(Configure.BUCKET_CUT, imgUrl);
			request.setProcess(style);
			OSSObject ossObject = ossClient.getObject(request);
			InputStream inputStream =ossObject.getObjectContent();
			ossClient.putObject(Configure.BUCKET_CUT,Configure.CUT_URL+imgUrl,inputStream);
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			ossClient.shutdown();
		}
		return Configure.CUT_URL+imgUrl;
		
	}
}
