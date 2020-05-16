package com.hl.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 注意：HttpPost请求不能处理重定向转发事件。需要手动的去做。
 * 如果要模拟登陆后的操作，则在请求中加入JSESSIONID就可以了
 * httpGet.setHeader("Cookie" , "JSESSIONID=432423432907427987EHLJ42"); 就可以了
 * Created by ivan.huang on 2016/7/28.
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {

    private static final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(3000).build();//设置请求和传输超时时间


    /**
     * 发送GET请求，携带参数 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpEntity sendGetRequest(String url, Map<String, String> params) throws Exception {
        final StringBuffer tmp = new StringBuffer(url);
        if (params != null) {
            Set<Entry<String, String>> paramset = params.entrySet();
            if (paramset.size() > 0) {
                tmp.append("?");
                int totalLen = paramset.size(), index = 0;
                for (Entry<String, String> entry : paramset) {
                    tmp.append(entry.getKey() + "=" + entry.getValue());
                    if (++index < totalLen) {
                        tmp.append("&");
                    }
                }
            }
        }
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(tmp.toString());
        HttpResponse response = httpClient.execute(httpGet);
        return response.getEntity();
    }
    
    public static HttpEntity sendGetRequestWithHeader(String url,
			Map<String, String> params, String token)  throws Exception{
    	final StringBuffer tmp = new StringBuffer(url);
        if (params != null) {
            Set<Entry<String, String>> paramset = params.entrySet();
            if (paramset.size() > 0) {
                tmp.append("?");
                int totalLen = paramset.size(), index = 0;
                for (Entry<String, String> entry : paramset) {
                    tmp.append(entry.getKey() + "=" + entry.getValue());
                    if (++index < totalLen) {
                        tmp.append("&");
                    }
                }
            }
        }
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(tmp.toString());
        httpGet.setHeader("Authorization", token);
        httpGet.setConfig(requestConfig);
        HttpResponse response = httpClient.execute(httpGet);
        return response.getEntity();
	}


    public static HttpEntity sendGetRequestWithHeader_UrlEncode(String url,
                                                      Map<String, String> params, String token)  throws Exception{
        final StringBuffer tmp = new StringBuffer(url);
        if (params != null) {
            Set<Entry<String, String>> paramset = params.entrySet();
            if (paramset.size() > 0) {
                tmp.append("?");
                int totalLen = paramset.size(), index = 0;
                for (Entry<String, String> entry : paramset) {
                    if(entry.getValue()==null){
                        continue;
                    }
                    tmp.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(),"UTF-8"));
                    if (++index < totalLen) {
                        tmp.append("&");
                    }
                }
            }
        }
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(tmp.toString());
        httpGet.setHeader("Authorization", token);
        httpGet.setConfig(requestConfig);
        HttpResponse response = httpClient.execute(httpGet);
        return response.getEntity();
    }


    /**
     * 发送POST请求 
     * @param url
     * @throws Exception
     */
    public static HttpEntity sendPostRequest(String url, Map<String, String> params) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> listParams = new ArrayList<NameValuePair>();
        if (params != null) {
            Set<Entry<String, String>> set = params.entrySet();
            for (Entry<String, String> entry : set) {
                listParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new UrlEncodedFormEntity(listParams, "utf-8"));
        HttpResponse reponse = httpClient.execute(httpPost);

        return reponse.getEntity();
    }
    
    /**
     * 发送POST请求, 参数为json数据
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpEntity sendJsonPostRequest(String url, Map<String, Object> params) throws Exception {
    	JSONObject jsonObj = new JSONObject();
    	if (params != null) {
            Set<Entry<String, Object>> set = params.entrySet();
            for (Entry<String, Object> entry : set) {
                jsonObj.put(entry.getKey(), entry.getValue());
            }
        }
    	
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(jsonObj.toString(), "utf-8"));
        HttpResponse reponse = httpClient.execute(httpPost);

        return reponse.getEntity();
    }

    /**
     * 下载文件 
     * @param path  文件URL 
     * @param savePath  保存路径 
     * @param fileName  文件名 
     */
    public static void downloadFile(String path, String savePath, String fileName) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(path);
        HttpResponse response = httpClient.execute(httpGet);
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();
        File saveFile = new File(dir, fileName);
        BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveFile));
        byte tmp[] = new byte[1024];
        int len = 0;
        while ((len = bis.read(tmp)) != -1) {
            bos.write(tmp, 0, len);
        }
        bis.close();
        bos.flush();
        bos.close();
    }


    /**
     * 利用jdk提供的HttpUrlConnect发起Post请求 
     * @return
     */
    public static InputStream sendPostRequestByUrlConnect(String path, Map<String, String> params) throws Exception {
        URL url = new URL(path);
        HttpURLConnection hul = (HttpURLConnection) url.openConnection();
        hul.setRequestMethod("POST");
        hul.setDoOutput(true);   //post 方式     
        hul.setDoInput(true);
        if (params != null) {
            Set<Entry<String, String>> paramSet = params.entrySet();
            //加入参数  
            StringBuilder paramsStr = new StringBuilder();
            String paramss = null;
            for (Entry<String, String> entry : paramSet) {
                paramsStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            if (paramsStr.length() > 0)
                paramss = paramsStr.substring(0, paramsStr.length() - 1);
            hul.getOutputStream().write(paramss.getBytes());
        }
        hul.getOutputStream().flush();
        hul.getOutputStream().close();
        return hul.getInputStream();
    }

    /**
     * 将输入流转换为字符串 
     * @param is
     * @return
     * @throws Exception
     */
    public static String inputToString(InputStream is) throws Exception {
        StringBuffer sb = new StringBuffer("");
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        return sb.toString();
    }

    /**
     * 将HttpEntity对象转换为字符串 
     * @param entity
     * @return
     * @throws Exception
     */
    public static String entityToString(HttpEntity entity) throws Exception {
        return entity == null ?
                null : EntityUtils.toString(entity, "utf-8");
    }
    
    /**
     * 
     * @param request
     * @return
     */
    public static String getUri(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        return uri.replaceFirst(ctxPath, "");
    }



}    
