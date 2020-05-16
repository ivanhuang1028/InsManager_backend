package com.hl.common.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.*;


@Slf4j
public class OssUtils {

	/**
	 * 上传, 参数不能为空
	 * @param endpoint  终端地址
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @param bucketName
	 * @param targetPath 目标路径+文件名
	 * @param
	 * @return
	 */
	public static boolean OssUpload(String endpoint, String accessKeyId,
			String accessKeySecret, String bucketName, String targetPath, InputStream input, long contentLength){
//		logger.info("OssUpload start");
		try {
			if(StringUtils.isEmpty(endpoint) || StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret) ||
					StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(targetPath)){
				return false;
			}
			OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			if (!ossClient.doesBucketExist(bucketName)) {
				return false;
			}
//			PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, targetPath, file));
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(contentLength);
			//响应头设置为下载
			meta.setContentDisposition("attachment;");
			PutObjectResult result = ossClient.putObject(bucketName, targetPath, input, meta);
			if(result != null && !StringUtils.isEmpty(result.getETag())){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
//			logger.error(e.getMessage());
			return false;
		}finally{
//			logger.info("OssUpload end");
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
//		logger.info("OssDownload start");
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
//			logger.error(e.getMessage());
			return null;
		}finally{
//			logger.info("OssDownload end");
		}
	}


	public static boolean OssDel(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String targetPath){
//		logger.info("OssDel start");
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
//			logger.error(e.getMessage());
			return false;
		}finally{
//			logger.info("OssDel end");
		}
	}

	public static void main(String[] args){

		String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
	    String accessKeyId = "ugjE1sM2j7z8f86v";
	    String accessKeySecret = "XjhC94p1RRxNlN953McTRiKX0HsdSw";
	    String bucketName = "side-projects";
	    String targetPath = "usercenter/usercenter1.txt";

		try {
			File file = createSampleFile();
			InputStream input = new FileInputStream(file);
			long contentLength = file.length();
			System.out.println("Uploading a new object to OSS from a file\n");
			OssUtils.OssUpload(endpoint, accessKeyId, accessKeySecret, bucketName, targetPath, input, contentLength);
		} catch (Exception e) {
//			logger.error(e.getMessage());
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
//			logger.error(e.getMessage());
		}

        return file;
    }

}
