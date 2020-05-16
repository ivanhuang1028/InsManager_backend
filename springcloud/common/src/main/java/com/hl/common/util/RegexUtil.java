package com.hl.common.util;

import org.apache.commons.lang3.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	// 手机号正则表达式
	public final static String REGEX_MOBILE = "^1\\d{10}$";

    // 邮箱地址正则表达式
    public final static String REGEX_EMAIL = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
		
    /**
     * 
     * @param str
     * @param regex
     * @return
     */
    public static boolean isMatch(String str, String regex) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    /**
     * 手机号校验
     * @param unknown
     * @return
     */
    public static boolean isMobile(String unknown) {
    	if (StringUtils.isEmpty(unknown)) {
    		return false;
    	}
    	Pattern p = Pattern.compile(REGEX_MOBILE);
    	Matcher m = p.matcher(unknown);
    	return m.matches();
    }

    /**
     * 邮箱校验
     * @param unknown
     * @return
     */
    public static boolean isEmail(String unknown) {
        if (StringUtils.isEmpty(unknown)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_EMAIL);
        Matcher m = p.matcher(unknown);
        return m.matches();
    }
}
