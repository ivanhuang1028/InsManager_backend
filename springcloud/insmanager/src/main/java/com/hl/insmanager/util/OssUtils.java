package com.hl.insmanager.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import java.io.*;
import java.net.URL;
import java.util.Date;

public class OssUtils {
	
	private static final Logger logger = Logger.getLogger(OssUtils.class);
	
	/**
	 * 上传, 参数不能为空
	 * @param endpoint  终端地址
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @param bucketName
	 * @param targetPath 目标路径+文件名
	 * @return
	 */
	public static boolean OssUpload(String endpoint, String accessKeyId,
			String accessKeySecret, String bucketName, String targetPath, InputStream input, long contentLength){
		logger.info("OssUpload start");
		OSS ossClient = null;
		try {
			if(StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret) || 
					StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(targetPath)){
				return false;
			}
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			if (!ossClient.doesBucketExist(bucketName)) {
				return false;
			}

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(contentLength);
			PutObjectResult result = ossClient.putObject(bucketName, targetPath, input, meta);
			if(result != null && !StringUtils.isEmpty(result.getETag())){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}finally{
			ossClient.shutdown();
			logger.info("OssUpload end");
		}
		
	}
	
	/**
	 * 下载
	 * @param endpoint
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @param bucketName
	 * @param targetPath
	 * @return
	 */
	public static InputStream OssDownload(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String targetPath){
		logger.info("OssDownload start");
		try {
			if(StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret) || 
					StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(targetPath)){
				return null;
			}
			OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			if (!ossClient.doesBucketExist(bucketName)) {
				return null;
			}
			OSSObject object = ossClient.getObject(bucketName, targetPath);
			if(object != null ){
				return object.getObjectContent();
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}finally{
			logger.info("OssDownload end");
		}
	}
	
	
	public static boolean OssDel(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String targetPath){
		logger.info("OssDel start");
		try {
			if(StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret) || 
					StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(targetPath)){
				return false;
			}
			OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			if (!ossClient.doesBucketExist(bucketName)) {
				return false;
			}
			ossClient.deleteObject(bucketName, targetPath);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}finally{
			logger.info("OssDel end");
		}
	}
	
	public static void main(String[] args)  {

		String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
		String accessKeyId = "LTAI4FxJ7pVx1QwWyUP77k3A";
		String accessKeySecret = "v1dPkbzlnJYiAVzbvywkjbqQgbj85R";
		String bucketName = "instranger";
		String originalPath = "D:\\logo.png";
		String targetPath = "user/c9c4756847584a999cfc91b8e2a78c89/topic/081f94a3fac74b61a2272ed2b2d15f3a/logo.png";
		try {
			String url = uploadOssReturnUrl(endpoint, accessKeyId, accessKeySecret,bucketName, originalPath, targetPath);
			System.out.println("url : " + url );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String uploadOssReturnUrl(String endpoint, String accessKeyId, String accessKeySecret,
											String bucketName, String originalPath, String targetPath) throws Exception{
		File imageFile = new File(originalPath);
		InputStream image1Input = new FileInputStream(imageFile);
		long contentLength = imageFile.length();
		boolean is_upload = OssUtils.OssUpload(endpoint, accessKeyId, accessKeySecret,
				bucketName, targetPath, image1Input, contentLength);
		if(is_upload == true){
			String url = OssUtils.getUrl(targetPath, endpoint, accessKeyId, accessKeySecret, bucketName);
			return url;
		}else{
			return "";
		}
	}

	private static File createSampleFile() {
        File file = null;
		try {
			file = File.createTempFile("oss-java-sdk-", ".txt");
			file.deleteOnExit();

			Writer writer = new OutputStreamWriter(new FileOutputStream(file));
			writer.write("abcdefghijklmnopqrstuvwxyz\n");
			writer.write("0123456789011234567890abcdefg\n");
			writer.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 

        return file;
    }

    /**
     * 获得url链接
     * @return
     */
    public static String getUrl(String fullName, String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		URL url = ossClient.generatePresignedUrl(bucketName, fullName, expiration);
        if (url != null) {
			ossClient.shutdown();
            return url.toString();
        }else{
			ossClient.shutdown();
			return null;
		}

    }
	
}
