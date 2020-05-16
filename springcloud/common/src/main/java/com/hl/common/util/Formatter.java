package com.hl.common.util;

import org.apache.commons.lang.StringUtils;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ivan.huang
 * @date 2016年8月2日
 */
public class Formatter {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 字符串转日期
     *
     * @param dateValue
     * @return
     */
    public static Date str2Date(String dateValue) {
        return str2Date(dateValue, null);
    }

    /**
     * 字符串转日期
     *
     * @param dateValue
     * @param strFormat
     * @return
     */
    public static Date str2Date(String dateValue, String strFormat) {
        if (dateValue == null)
            return null;
        if (strFormat == null) {
            strFormat = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        Date newDate = null;

        try {
            newDate = dateFormat.parse(dateValue);
        } catch (ParseException pe) {
            newDate = null;
        }

        return newDate;
    }

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseDate2Str(Date date) {
        if (null == date) {
            return "0000-00-00 00:00:00";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0000-00-00 00:00:00";
    }

    public static String parseDate2Str(Date date, String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        try {
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param srcStr     左边补 holderChar
     * @param len
     * @param holderChar
     * @return
     */
    public static String formatLen(String srcStr, int len, Character holderChar) {
        if (null == holderChar) {
            holderChar = ' ';
        }
        String res = "";
        if (StringUtils.isBlank(srcStr)) {
            for (int i = 0; i < len; i++) {
                res += holderChar;
            }
        } else {
            int holdNum = len - srcStr.length();
            for (int i = 0; i < holdNum; i++) {
                srcStr = holderChar + srcStr;
            }
            return srcStr;
        }

        return res;
    }

    /**
     * @param obj
     * @param len 不包含省略号长度
     * @return
     */
    public static String formatLengthEllipsis(Object obj, int len) {

        if (obj == null) {
            //logger.info("新值为空!请注意");
            return "";
        }
        String srcStr = obj.toString();

        int strlen = srcStr.length();
        if (strlen <= len) {
            return srcStr;
        } else {
            return srcStr.substring(0, len) + "……";
        }
    }

    public static boolean isNullOrBlank(String str) {
        if (str == null) {
            return true;
        } else {
            return str.trim().equals("");
        }

    }

    public static String getStr(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof Date || obj instanceof Timestamp) {
            return parseDate2Str((Date) obj);
        }
        return obj.toString();

    }

    public static String getValueFromMap(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Date || obj instanceof Timestamp) {
            return parseDate2Str((Date) obj);
        }
        return obj.toString();

    }


    public static String columnTofiled(String columnName) {
        String[] words = columnName.split("_");
        String fieldName = words[0].toLowerCase();

        for (int i = 1; i < words.length; i++) {
            fieldName += firstCharToUp(words[i]);
        }
        return fieldName;
    }

    public static String firstCharToUp(String word) {

        return word.substring(0, 1).toUpperCase()
                + word.substring(1).toLowerCase();

    }

    public static Float format2Float(Float price) {
        return (float) (Math.round(price * 100)) / 100;
    }

    public static Float formatFloatByPrecision(Float price, int precision) {
        int scale = 1;
        for (int i = 0; i < precision; i++) {
            scale = 10 * scale;
        }
        return (float) (Math.round(price * scale)) / scale;
    }

    public static String FormatMoney(Float price) {
        if (null == price) {
            return "-";
        }
        DecimalFormat decimalFormat = new DecimalFormat("##,###.00");
        return "$" + decimalFormat.format(price);

    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmpty(Object o) {
        return !(o != null && (!StringUtils.isBlank(o.toString())));
    }

    public static String FormatMoneyNoDallor(Float price) {
        if (null == price) {
            return "-";
        }
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(price);

    }

    public static String fieldToCulumn(String field) {
        if (field == null) {
            return null;
        }
        String culumnStr = "";
        for (int i = 0; i < field.length(); i++) {
            if (Character.isUpperCase(field.charAt(i))) {
                culumnStr += "_" + field.charAt(i);
            } else {
                culumnStr += field.charAt(i);
            }
        }
        return culumnStr.toUpperCase();
    }


    public static Float DiffTimeMin(String startTime, String endTime) {
        Date start = TimeUtil.parseByPatterMatch(startTime);
        Date end = TimeUtil.parseByPatterMatch(endTime);
        long cha = end.getTime() - start.getTime();
        Float result = (float) (cha * 1.0 / (1000 * 60));// 精确到分
        return result;
    }
    
    public static boolean compareStringTime(String time1, String time2){
    	Date time1Date = TimeUtil.parseByPatterMatch(time1);
        Date time2Date = TimeUtil.parseByPatterMatch(time2);
        if(time2Date.getTime() > time1Date.getTime()){
        	return true;
        }else{
        	return false;
        }
    }

    public static Float DiffTimeMin(Date startTime, Date endTime) {
        try {
            long cha = endTime.getTime() - startTime.getTime();
//        Float result = (float) (cha * 1.0 / (1000 * 60 * 60 * 60));
//        Float result = (float)Math.round( ((cha * 1.0 / (1000 * 60))*0.01)*100);// 精确到分
            BigDecimal b = new BigDecimal((float) (cha * 1.0 / (1000 * 60)));
            float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
//        Float result = (float)Math.round(((cha * 1.0 / (1000 * 60))/100)*100);
//        Float result = (float)(cha * 1.0 / (1000 * 60));
            return f1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Float(0);
    }

    public static String addNDay2Date(long N, Date date) {
        return addNDay2Date(N, date, null);
    }

    /**
     * @param N    在给定日期加N天
     * @param date
     * @return
     */
    public static String addNDay2Date(long N, Date date, String dateFormat) {
        Date d = new Date();

        SimpleDateFormat objFormat = dateFormat == null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") : new SimpleDateFormat(dateFormat);
        d.setTime(date.getTime() + new Long(N * 24 * 60 * 60 * 1000));
        return objFormat.format(d);
    }

    // 在给定日期加N天
    public static Date addNDayOnDate(long N, Date date) {
        Date d = new Date();
        d.setTime(date.getTime() + N * 24 * 60 * 60 * 1000);
        return d;
    }

    /**
     * @param Str      获取标签的innerHTML
     * @param startTag
     * @param endTag
     * @return
     */
    public static String getTagInnerHTML(String Str, String startTag, String endTag) {
        int start = Str.indexOf(startTag) + startTag.length();
        int end = Str.indexOf(endTag, start);
        String innerText;
        if (end < 0) {
//            logger.info("没有找到" + startTag + endTag + "的内容");
            return "";
        }
        try {
            innerText = Str.substring(start, end);

            return innerText;
        } catch (Exception e) {
        }
        return "";
    }


    // 日期类
    public static String removeSameRecipients(String recipient, String ccRecipient) {
        String res = "";
        for (String cc : ccRecipient.split(",")) {
            if (!("," + recipient + ",").contains("," + cc + ",")) {
                res += cc + ",";
            }
        }
        if (!"".equals(res)) {
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }

    public static String enCodeStr(String str) {
        if (isEmpty(str)) return null;
        try {
            return new String(str.getBytes("iso-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTimeDiff(Float minute) {
        if (minute == null) {
            return "-";
        }
        //        Long mins = Long.valueOf(minute.toString());
        Float mins = minute;
        Integer day = (int) (mins / (60 * 24));
        mins = mins % (60 * 24);
        Integer hour = (int) (mins / 60);
        Integer min = (int) (mins % 60);
        String timeDiff = new String();
        if (day > 0) {
            timeDiff = day + "天";
        }
        if (hour > 0) {
            timeDiff += hour + "时";
        }

        if (min > 0) {
            timeDiff += min + "分";
        }
        return timeDiff;
    }

    public static String[] getFieldNamesByColumn(String columnNameStr) {
        String fieldStr = "";
        for (String columnName : columnNameStr.split(",")) {
            fieldStr += "," + columnTofiled(columnName.trim());
        }
        if (fieldStr.contains(",")) {
            return fieldStr.substring(1).split(",");
        } else {
            return null;
        }
    }

    /**
     * 取上周一的日期
     * @return
     */
    public static Date getLastMonday(){
    	Calendar calendar = Calendar.getInstance();
    	int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1;
    	int offset1=1-dayOfWeek;
    	calendar.add(Calendar.DATE, offset1-7);
    	return calendar.getTime();
    }
    
    /**
     * 取上周日的日期
     * @return
     */
    public static Date getLastSunday(){
    	Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK)-1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1-7);
		calendar2.add(Calendar.DATE, offset2-7);
		return calendar2.getTime();
    }
    
    /**
     * float四舍五入保留2位小数
     * @return
     */
    public static float get2Float(float f){
    	return new BigDecimal(f).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    
    public static String getHouMinSec(){
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("HHmmss");
    	String time=format.format(date); 
    	return time;
    }

    public static String changeSpecialCharacter(String value){
    	StringBuffer sb = new StringBuffer();
    	if(StringUtils.isEmpty(value) || !StringUtils.contains(value, "'")) return value;
    	String[] strArr = value.split("'");
    	for(int i = 0 ; i < strArr.length ; i++){
    		if(i==strArr.length-1){
    			sb.append(strArr[i]);
    		}else{
    			sb.append(strArr[i] + "\\'");
    		}
    	}
    	return sb.toString();
    }
    /**
     * trim Map 的 value
     * @param listMap
     * @return
     */
    public static List<Map> trimListMap(List<Map> listMap) {
		for(Map map : listMap){
			for(Object key : map.keySet()){
				if(map.get(key) instanceof String){
					map.put(key, ((String)map.get(key)).trim());
				}
			}
		}
		return listMap;
	}
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    	
    }
    
}
