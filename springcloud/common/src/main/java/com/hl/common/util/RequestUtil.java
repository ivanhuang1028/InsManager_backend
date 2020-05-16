package com.hl.common.util;

import org.apache.commons.lang.StringEscapeUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**request处理类
 * Created by ivan.huang
 */
public class RequestUtil {

    public static String SEPARATOR = "~";// 多个属性的分隔符

    /**
     * 获得参数值
     */
    public static String get(HttpServletRequest request, String name,
                             String defaultValue) {
        String text =  request.getParameter(name) != null ? request.getParameter(name)
                : defaultValue;
        text = text == null ? null : org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(text);
//        try {
//			text = text == null ? null : new String (text.getBytes("ISO-8859-1"),"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			text = text == null ? null : org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(text);
//		}
        //text = text == null ? null : StringEscapeUtils.escapeSql(text);
        return text;
    }

    public static String get(HttpServletRequest request, String name) {
        return get(request, name, null);
    }

    /**
     * 获得整数值
     */
    public static int getInt(HttpServletRequest request, String name,
                             int defaultValue) {
        return StringUtil.getInt(request.getParameter(name), defaultValue);
    }

    public static int getInt(HttpServletRequest request, String name) {
        return getInt(request, name, 0);
    }

    /**
     * 设置request值
     */
    public static void setRequest(HttpServletRequest request,
                                  String requsetName, Object requsetObj) {
        request.setAttribute(requsetName, requsetObj);
    }

    /**
     * 获得request值
     */
    public static Object getReqestAttribute(HttpServletRequest request,
                                   String requsetName) {
        return request.getAttribute(requsetName);
    }

    /**
     * 得到传递参数的浮点数形式，如果没有这个参数，返回normal
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static float getFloat(HttpServletRequest request, String name,
                                 float defaultValue) {
        try {
            return Float.parseFloat(get(request, name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float getFloat(HttpServletRequest request, String name) {
        try {
            return Float.parseFloat(get(request, name));
        } catch (Exception e) {
            return (float) 0;
        }
    }

    /**
     * @param paramName
     * @return
     */
    public static String[] getValues(HttpServletRequest request,
                                     String paramName) {
        String[] array = request.getParameterValues(paramName);
        if(null != request && array != null){
            for(int i = 0; i < array.length; i++){
                array[i] = org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(array[i]);
                //array[i] = StringEscapeUtils.escapeSql(array[i]);
            }
        }
        return array;
    }

    /**
     * GBK编码的值
     *
     * @param request
     * @param name
     * @return
     */
    public static String getEncode(HttpServletRequest request, String name) {
        try {
            return new String(get(request, name).getBytes("GBK"), "GBK");
        } catch (Exception e) {
            return name;
        }
    }

    /**
     * 取得表单里面所有的参数/值,并保存在Row里面返回(不上传图片) 多个值的默认以逗号隔开保存
     *
     * @param request
     * @return row
     */
    public static Row getParam(HttpServletRequest request, String notmath) {
        return getParam(request, notmath, SEPARATOR);
    }

    /**
     * 取得表单里面所有的参数/值,并保存在Row里面返回(不上传图片),多个值的以符号隔开保存
     *
     * @param request
     * @param notmath
     * @param seperator
     * @return
     */
    public static Row getParam(HttpServletRequest request, String notmath, String seperator) {
        Row row = new Row();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement().toString();
            if (!paramName.startsWith(notmath)) {
                String[] paramValues = request.getParameterValues(paramName);
                String paramValue = "";
                //System.out.println("名字"+paramName+"=数据长度"+paramValues.length);
                if (paramValues.length == 1) {
                    paramValue = paramValues[0];
                    if (paramValue.length() < 1) {
                        paramValue = "";
                    }
                } else {
                    for (int i = 0; i < paramValues.length; i++) {
                        paramValue += (String) paramValues[i];
                        if ((i + 1) < paramValues.length) {
                            paramValue += seperator;
                        }
                    }
                }
                // System.out.println("名字"+paramName+"=val=="+paramValue);
                row.put(paramName, paramValue);
            }
        }
        return row;
    }

    /**
     * 获取请求的所有数据
     *
     * @param request
     * @param notmath
     * @return
     */
    public static Row getAttribute(HttpServletRequest request, String notmath) {
        Row row = new Row();
        Enumeration attrNames = request.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = attrNames.nextElement().toString();
            if (!attrName.startsWith(notmath)) {
                Object o = (Object) request.getAttribute(attrName);
                if (o != null) {
                    row.put(attrName, o);
                }
            }
        }
        return row;
    }

    /**
     * 获得请求的绝对路径(不带参数),如：http://riji.163.com/weblog/abc.do
     */
    public static String getUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    /**
     * 获得请求的相对路径(不带参数),如：/weblog/abc.do
     */
    public static String getPath(HttpServletRequest request) {
        StringBuffer str = new StringBuffer(request.getContextPath())
                .append(request.getServletPath());
        return str.toString();
    }

    /**
     * 获得请求的参数
     */
    public static String getQuery(HttpServletRequest request) {
        String str = request.getQueryString();
        str = org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(str);
       // str = StringEscapeUtils.escapeSql(str);
        return str;
    }

    /**
     * 获取客户端的真实IP
     *
     * @param request
     * @return ip
     */
    public static String getOriginallyAddr(HttpServletRequest request) {
        if (request == null)
            return "";
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null)
            return request.getRemoteAddr();
        if (ip.indexOf(",") != -1) {
            String newip = ip.substring(0, ip.indexOf(","));
            if (newip.substring(0, 3).equals("192")) {
                newip = ip.substring(0, ip.lastIndexOf(",") + 1);
            }
            return newip;
        }
        if (!isLicitIp(ip))
            return request.getRemoteAddr();
        return ip;
    }

    /**
     * 验证是否正确IP
     */
    public static boolean isLicitIp(String ip) {
        if (ip == null || ip.length() == 0) {
            return false;
        }
        String regex = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ip);
        if (!m.find()) {
            return false;
        }
        return true;
    }

    /**
     * 生成cookie
     */
    public static void setCookie(HttpServletResponse response, String domain,
                                 String name, String value, int time, boolean secure) {
        if (value == null)
            value = "";
        try {
            value = java.net.URLEncoder.encode(value, "utf8");
        } catch (UnsupportedEncodingException e) {
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure); // 表示是否Cookie只能通过加密的连接（即SSL）发送。
        cookie.setPath("/"); // 设置Cookie适用的路径
        cookie.setDomain(domain); // 设置Cookie适用的域
        cookie.setMaxAge(time); // 设置Cookie有效时间
        response.addCookie(cookie);
    }


    /**
     * 获取Cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name)) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    /**
     * 获取Cookie的值
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie == null)
            return null;
        String value = cookie.getValue();
        if ("null".equals(value))
            return null;
        try {
            return java.net.URLDecoder.decode(value, "utf8");
        } catch (UnsupportedEncodingException e) {
        }
        return value;
    }

    /**
     * 删除cookie
     */
    public static HttpServletResponse deleteCookie(
            HttpServletResponse response, Cookie cookie) {
        if (cookie == null) {
            return response;
        }
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return response;
    }


    public static void main(String[] args){
        String sql = "select * from event_order where PROCESS_DETAIL like '%''''%'";
        for(int i = 0 ; i < 10;i++){
            sql = StringEscapeUtils.escapeSql(sql);
            System.out.println(sql);
        }
    }

}
