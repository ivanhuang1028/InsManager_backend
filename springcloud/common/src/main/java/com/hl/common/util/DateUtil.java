package com.hl.common.util;

import com.hl.common.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import java.text.*;
import java.util.*;

public class DateUtil {
	/**
	 * 获取中国的时区
	 */
	private static TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");

	/**
	 * 解析指定的日期
	 * 
	 * @param strDate
	 * @return Date
	 */
	public static Date parseDate(String strDate) {
		return parseDate(strDate, null);
	}

	/**
	 * parseDate
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String strDate, String pattern) {
		Date date = null;
		try {
			if (pattern == null) {
				pattern = "YYYYMMDD";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(strDate);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * format date
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, null);
	}

	/**
	 * format date
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		String strDate = null;
		try {
			if (pattern == null) {
				pattern = "yyyy-MM-dd";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(date);
		} catch (Exception e) {
		}
		return strDate;
	}

	/**
	 * 获取当天时间
	 * 
	 * @param dateformat
	 * @return
	 */
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	/**
	 * 获取当前日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getCurrentTime(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date_ = null;
		try {
			date_ = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date_;
	}

	/**
	 * getSimpleDateFormat
	 * 
	 * @param formatStr
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormat(String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		sdf.setTimeZone(timeZoneChina);
		return sdf;
	}

	// 获得当前日期与本周一相差的天数
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	// 获得当前周- 周一的日期
	public static String getCurrentMonday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得当前周- 周日 的日期
	public static String getPreviousSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	// 获得当前月--开始日期
	public static String getMinMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			return dateFormat.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获得当前月--结束日期
	public static String getMaxMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return dateFormat.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前月有多少周
	 * 
	 * @param year
	 * @param month
	 * @return int 周数
	 */
	public static int getWeeks(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		return c.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * convertStringToDate
	 * 
	 * @param strDate
	 * @return Date
	 */
	public static final Date convertStringToDate(String strDate) throws  ParseException{
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat("yyyy-MM-dd");
		date = df.parse(strDate);
		return (date);
	}

	/**
	 * getCalendar
	 * 
	 * @param month
	 * @param week
	 * @return
	 */
	public static Calendar getCalendar(String month, int week) throws  ParseException {
		Date newDate = DateUtil.convertStringToDate(month + "-01");
		Calendar caleNew = Calendar.getInstance();
		caleNew.setTime(newDate);
		caleNew.add(Calendar.WEEK_OF_MONTH, week - 1);
		return caleNew;
	}

	/**
	 * 获取某月的第n星期的第一天
	 * 
	 * @param month
	 *            : 2011-06
	 * @param week
	 *            : 2
	 * @return
	 */
	public static Date getFirstOfWeek(String month, int week) throws  Exception{
		Calendar myCale = Calendar.getInstance();
		GregorianCalendar gc = (GregorianCalendar) getCalendar(month, week);
		gc.setFirstDayOfWeek(Calendar.MONDAY);
		myCale.setTime(gc.getTime());
		myCale.set(Calendar.DATE,
		gc.get(Calendar.DATE) - gc.get(Calendar.DAY_OF_WEEK) + 2);
		return myCale.getTime();
	}

	/**
	 * 获取某月的第n星期的最后一天
	 * 
	 * @param month
	 *            : 2011-06
	 * @param week
	 *            : 2
	 * @return
	 */
	public static Date getLastOfWeek(String month, int week) throws  Exception {
		Calendar myCale = Calendar.getInstance();
		GregorianCalendar gc = (GregorianCalendar) getCalendar(month, week); // (GregorianCalendar)Calendar.getInstance();
		gc.setFirstDayOfWeek(Calendar.MONDAY);
		myCale.setTime(gc.getTime());
		myCale.set(Calendar.DATE,
		gc.get(Calendar.DATE) + 8 - gc.get(Calendar.DAY_OF_WEEK));
		return myCale.getTime();
	}

	public static String addOneday(String today) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = new Date(f.parse(today).getTime() + 24 * 3600 * 1000);
			return f.format(d);
		} catch (Exception ex) {
			return "输入格式错误";
		}
	}
	
	public static String addHours(String date,Double hour) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(date));
			//+3小时
			cal.add(Calendar.MINUTE,(int) (hour*60));
			return sdf.format(cal.getTime());
		} catch (Exception ex) {
			return "输入格式错误";
		}
	}

	public static String toChinese(String day) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date d = new Date(f.parse(day).getTime());
			return f.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
			// return "输入格式错误";
		}
		return null;
	}

	public static String getRowWeekMonth() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int week = c.get(Calendar.WEEK_OF_MONTH);
		return String.valueOf(week - 1);
	}

	public static void printfWeeks(String date) throws Exception {
		// String date = "2013-09";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date date1 = dateFormat.parse(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date1);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("days:" + days);
		int count = 0;
		for (int i = 1; i <= days; i++) {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = dateFormat1.parse(date + "-" + i);
			calendar.clear();
			calendar.setTime(date2);
			int k = new Integer(calendar.get(Calendar.DAY_OF_WEEK));
			if (k == 1) {// 若当天是周日
				count++;
				System.out.println("-----------------------------------");
				System.out.println("第" + count + "周");
				if (i - 6 <= 1) {
					System.out.println("本周开始日期:" + date + "-" + 1);
				} else {
					System.out.println("本周开始日期:" + date + "-" + (i - 6));
				}
				System.out.println("本周结束日期:" + date + "-" + i);
				System.out.println("-----------------------------------");
			}
			if (k != 1 && i == days) {// 若是本月最好一天，且不是周日
				count++;
				System.out.println("-----------------------------------");
				System.out.println("第" + count + "周");
				System.out.println("本周开始日期:" + date + "-" + (i - k + 2));
				System.out.println("本周结束日期:" + date + "-" + i);
				System.out.println("-----------------------------------");
			}
		}
	}

	/**
	 * 得到时间是一个月的第几周
	 * 
	 * @return
	 */

	public static int weekOfMonth(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int weekOfMonth = 0;
		Date date;
		try {
			date = sdf.parse(dateString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return weekOfMonth;
	}

	// 比较时间大小
	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	// 比较时间大小 精确到天
		public static int compare_date_day(String DATE1, String DATE2) {

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date dt1 = df.parse(DATE1);
				Date dt2 = df.parse(DATE2);
				if (dt1.getTime() >= dt2.getTime()) {
					return 1;
				} else if (dt1.getTime() < dt2.getTime()) {
					return -1;
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return 0;
		}
	
	
	/**
	 * 获取当年的第一天
	 * 
	 * @param
	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}
	
	/** 
     * 获取当年的最后一天 
     */
    public static Date getCurrYearLast(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
    }
    
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR, 23);  
        calendar.set(Calendar.MINUTE, 59);  
        calendar.set(Calendar.SECOND, 59);  
        calendar.set(Calendar.MILLISECOND, 999); 
        Date currYearLast = calendar.getTime();  
        return currYearLast;
    }
    
    /**
     * long型的日期转String
     * @param time
     * @return
     */
    public static String formatDateFromLong(long time, String pattern){
    	return formatDate(new Date(time), pattern);
    }
    
    /**
     * yyyy-MM-dd HH:mm 转成 yyyy-MM-dd HH:mm:ss
     * @param dateStr
     * @return
     */
    public static String validDateString(String dateStr){
    	if(StringUtils.isEmpty(dateStr)) return "";
    	Date dd = parseDate(dateStr, "yyyy-MM-dd HH:mm");
    	String sss = formatDate(dd, "yyyy-MM-dd HH:mm:ss");
    	return sss;
    }
    
    /**
     * yyyy-MM-dd HH:mm:ss 转成 yyyyMMdd
     */
    public static String getDate(String dateStr) {
    	if(StringUtils.isEmpty(dateStr)) return "";
    	Date dd = parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    	String sss = formatDate(dd, "yyyyMMdd");
    	return sss;
	}
    
    /**
     * 获取某个月的天数
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }
    
    public static int getHoursOffMonth(String dateStr){
    	Date date = parseDate(dateStr, Constants.DATE_PATTERN1);
    	int daysOfMonth = getDaysOfMonth(date);
    	int day = date.getDate();
    	int hour = date.getHours();
    	if(hour < 12){
    		return (daysOfMonth - day + 1) * 8;
    	}else{
    		return (daysOfMonth - day ) * 8 + 4;
    	}
    }
    
    public static int getHoursLeftMonth(String dateStr) {
    	Date date = parseDate(dateStr, Constants.DATE_PATTERN1);
    	int day = date.getDate();
    	int hour = date.getHours();
    	if(hour < 14){
    		return (day-1) * 8 + 4;
    	}else{
    		return day * 8;
    	}
	}

	public static String division(float a, int b) {
		String result = "";
		float num = (float) a / b;

		DecimalFormat df = new DecimalFormat("0.0");

		result = df.format(num);

		return getSplit(result);

	}

	public static String getSplit(String str) {
		String arr[] = str.split("[.]"); 
		String zhengshu = arr[0]; 
		String xiaoshu = arr[1]; 
		Double d = Double.valueOf("0." + xiaoshu);
		if(d<0.3){
			return zhengshu;
		}else if(0.3 <=d || d <= 0.7){
			return zhengshu + ".5";
		}else{
			return String.valueOf(Integer.valueOf(zhengshu)+1);
		}
	}
	/**
	 * 取一个时间段内除了周六日的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getDutyDays(Date startDate, Date endDate) {
		int result = 0;
		while (startDate.compareTo(endDate) <= 0) {
			if (startDate.getDay() != 6 && startDate.getDay() != 0)
				result++;
			startDate.setDate(startDate.getDate() + 1);
		}
		return result;
	}
	/**
	 * 获取某个月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date date = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 获取某个月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	public static boolean isFullOneYear(Date entranceDate) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)-1);
		if(calendar.getTime().getTime() >= entranceDate.getTime()){
			return true;
		}else{
			return false;
		}
	}

	public static int ageCal(Date birthDate, String yyyy) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(birthDate);
		int birthYear = calendar.get(Calendar.YEAR);
		int nowYear = 0;
		if(!StringUtils.isEmpty(yyyy)){
			nowYear = Integer.valueOf(yyyy);
		}else{
			calendar.setTime(new Date());
			nowYear = calendar.get(Calendar.YEAR);
		}
		return nowYear - birthYear;
	}
	/**
	 * 计算日期之间相差的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate,String bdate) {  
        long between_days = 0;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			Calendar cal = Calendar.getInstance();    
			cal.setTime(sdf.parse(smdate));    
			long time1 = cal.getTimeInMillis();                 
			cal.setTime(sdf.parse(bdate));    
			long time2 = cal.getTimeInMillis();         
			between_days = (time2-time1)/(1000*3600*24);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
       return Integer.parseInt(String.valueOf(between_days));     
    }
	
	//取当月某天前的非周六日的天数
	public static int getDutyDates(String starttime, String endtime) {
		Date endDate = parseDate(endtime.substring(0, 10), Constants.DATE_PATTERN3);
		Date startDate = parseDate(starttime.substring(0, 10), Constants.DATE_PATTERN3);
		int result = 0;
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd");
		while (startDate.compareTo(endDate) <= 0) {
			if (startDate.getDay() != 6 && startDate.getDay() != 0)
				result++;
			startDate.setDate(startDate.getDate() + 1);
		}
		return result;
	}
	
	//某天是否周末
	public static boolean isZhouMo(String targetDate){
		Date theDate = parseDate(targetDate.substring(0, 10), Constants.DATE_PATTERN3);
		if (theDate.getDay() == 6 || theDate.getDay() == 0)
			return true;
		return false;
	}
	
//	精确整一天
	public static String calDates(String leave_start_time, String leave_end_time, int speDates) {
		Calendar cal = Calendar.getInstance();    
		cal.setTime(parseDate(leave_start_time, Constants.DATE_PATTERN1));
		int startHour = cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(parseDate(leave_end_time, Constants.DATE_PATTERN1));
		int endHour = cal.get(Calendar.HOUR_OF_DAY);
		double dates = getDutyDates(leave_start_time, leave_end_time) + speDates;
		if(startHour > 18 && !checkWeekend(leave_start_time.substring(0, 10))){
			dates = dates - 1;
		}
		if(startHour >= 13 && !checkWeekend(leave_start_time.substring(0, 10))){
			dates = dates - 0.5;
		}
		if(endHour < 10 && !checkWeekend(leave_end_time.substring(0, 10))){
			dates = dates - 1;
		}
		if(endHour <= 13 && !checkWeekend(leave_end_time.substring(0, 10))){
			dates = dates - 0.5;
		}
		return String.valueOf(dates);
	}
//	精确到半天
	public static String calHalfDates(String leave_start_time, String leave_end_time) {
		String start_time_base = leave_start_time.substring(0, 10) + " 12:30:00";
		String end_time_base = leave_end_time.substring(0, 10) + " 13:30:01";
		
		double dates = getDutyDates(leave_start_time, leave_end_time);
		
		if(compare_date(leave_start_time, start_time_base) == 1 && !checkWeekend(leave_start_time.substring(0, 10))){
			dates = dates - 0.5;
		}
		if(compare_date(end_time_base, leave_end_time) == 1 && !checkWeekend(leave_end_time.substring(0, 10))){
			dates = dates - 0.5;
		}
		return String.valueOf(dates);
	}

	public static boolean checkWeekend(String workday) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(workday, Constants.DATE_PATTERN3));
		int i = cal.get(Calendar.DAY_OF_WEEK);
		if(i==7 || i==1) {
			return true;
		}
		return false;
	}
	
//	返回   几小时几分
	public static String calMins(String ondutyBaseTime, String ondutyTime) {
		long ondutyBaseTimeLong = parseDate(ondutyBaseTime, Constants.DATE_PATTERN1).getTime();
		long ondutyTimeLong = parseDate(ondutyTime, Constants.DATE_PATTERN1).getTime();
		if(ondutyTimeLong < ondutyBaseTimeLong){
			return null;
		}
		int minutes = (int) ((ondutyTimeLong - ondutyBaseTimeLong)/(1000 * 60));
		int hour = minutes/60;
		int min = minutes%60;
		return hour == 0 ? min + "分钟" : hour + "小时" + min + "分钟";
	}
	
//	返回   2.3 
	public static double calHourMins(String ondutyBaseTime, String ondutyTime) {
		long ondutyBaseTimeLong = parseDate(ondutyBaseTime, Constants.DATE_PATTERN1).getTime();
		long ondutyTimeLong = parseDate(ondutyTime, Constants.DATE_PATTERN1).getTime();
		if(ondutyTimeLong < ondutyBaseTimeLong){
			return 0;
		}
		int minutes = (int) ((ondutyTimeLong - ondutyBaseTimeLong)/(1000 * 60));
		
		return changeDouble((double)minutes/60);
	}

	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static String daysBetweenForDay(String smdate,String bdate) 
    {    
    	Date smdateD=parseDate(smdate,Constants.DATE_PATTERN3);
    	Date bdateD=parseDate(bdate,Constants.DATE_PATTERN3);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        long between_days=0;
        try {
			smdateD=sdf.parse(sdf.format(smdateD));
			bdateD=sdf.parse(sdf.format(bdateD));  
			Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdateD);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdateD);    
	        long time2 = cal.getTimeInMillis();         
	        between_days=(time2-time1)/(1000*3600*24);  
		} catch (ParseException e) {
			e.printStackTrace();
		}  
            
       return String.valueOf(between_days+1);           
    }   
	
	public static int calDatesInMonth(String startTime, String endTime, int year, int month) {
		int start = 0;
		int end = 0;
		String startMonth = getFirstDayOfMonth(year, month) + " 00:00:01";
		if(compare_date(startMonth, startTime) == 1){
			start = 1;
		}else{
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(parseDate(startTime, Constants.DATE_PATTERN1));
			start = cal1.get(Calendar.DATE);
		}
		
		
		String endMonth = getLastDayOfMonth(year, month) + " 23:59:59";
		if(compare_date(endTime, endMonth) == 1){
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(parseDate(endMonth, Constants.DATE_PATTERN1));
			end = cal2.get(Calendar.DATE);
		}else{
			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(parseDate(endTime, Constants.DATE_PATTERN1));
			end = cal3.get(Calendar.DATE);
		}
		return end - start + 1;
	}
	
	public static double calActuateHours(String leave_start_time,
			String leave_end_time) {
		double hours = 0;
		Calendar start_cal = Calendar.getInstance();
		start_cal.setTime(parseDate(leave_start_time, Constants.DATE_PATTERN1));
		int start_hour = start_cal.get(Calendar.HOUR_OF_DAY);
		int start_min = start_cal.get(Calendar.MINUTE);
		double start_hours = start_hour + (start_min > 0 ? 0.5d : 0.0d);
				
		Calendar end_cal = Calendar.getInstance();
		end_cal.setTime(parseDate(leave_end_time, Constants.DATE_PATTERN1));
		int end_hour = end_cal.get(Calendar.HOUR_OF_DAY);
		int end_min = end_cal.get(Calendar.MINUTE);
		double end_hours = end_hour + (end_min > 0 ? 0.5d : 0.0d);
		
		double dates = getDutyDates(leave_start_time, leave_end_time);
		
		hours = dates * 8;
		if( start_hours > 9.5 && start_hours <= 12.5){
			hours = hours - (start_hours - 9.5);
		}
		if(start_hours > 12.5 && start_hours <= 13.5){
			hours = hours - 3;
		}
		if( start_hour > 13.5 && start_hour < 18.5){
			hours = hours - 3 - (start_hours - 13.5);
		}
		if(start_hours >= 18.5){
			hours = hours - 8;
		}
		
		if(end_hours < 18.5 && end_hours > 13.5){
			hours = hours - (18.5 - end_hours);
		}
		if(end_hours <= 13.5 && end_hours >= 12.5){
			hours = hours - 5;
		}
		if(end_hours > 9.5 && end_hours < 12.5){
			hours = hours - 5 - (12.5 - end_hours);
		}
		if(end_hours <= 9.5 ){
			hours = hours -8;
		}
		
		return hours;
	}
	
//	double 保留一位小数
	public static double changeDouble(Double dou) {
		NumberFormat nf = new DecimalFormat("#.0");
		dou = Double.parseDouble(nf.format(dou));
		return dou;
	}
	
//	double 取整
	public static double changeInt(Double dou) {
		NumberFormat nf = new DecimalFormat("0");
		dou = Double.parseDouble(nf.format(dou));
		return dou;
	}
	
//	int 取整
	public static int changeInteter(Double dou) {
		NumberFormat nf = new DecimalFormat("0");
		int i = Integer.parseInt(nf.format(dou));
		return i;
	}
	
	public static boolean lateThanTen(String endTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(endTime, Constants.DATE_PATTERN1));
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if(hour >= 22){
			return true;
		}else{
			return false;
		}
		
	}

	public static String getWeekIndex(String date_start) {
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(parseDate(date_start, Constants.DATE_PATTERN3));  
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
        if(week_index<0){  
            week_index = 0;  
        }   
        return weeks[week_index]; 
	}

	public static double yearCal(String entrance_date, String yyyy) {
		double totalLeft = 0;
		Date entranceDate = DateUtil.parseDate(entrance_date, "yyyy-MM-dd");
		Calendar calendarEntrance = Calendar.getInstance();
		calendarEntrance.setTime(entranceDate);
		int yearEntrance = calendarEntrance.get(Calendar.YEAR);
		if(!StringUtils.isEmpty(yyyy)){
			totalLeft = Integer.valueOf(yyyy) - yearEntrance 
					+ (double)DateUtil.daysBetween(entrance_date, DateUtil.getLastDayOfMonth(yearEntrance, 12))/365;
		}else{
			totalLeft = (double)DateUtil.daysBetween(entrance_date, DateUtil.formatDate(new Date(), Constants.DATE_PATTERN3))/365;
		}
		return DateUtil.changeDouble(totalLeft);
	}
	
	public static Date getDateByString(String dateStr){
		Date date = new Date();
		System.out.println(dateStr);
	    SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		try {
			  date=sim.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return date;
	}
	
	public static void main(String[] args) throws ParseException {
//		System.out.println(changeInteter(0.11d));
//		getCurrentWeek(-1);
//		getWeek(1);
//		System.out.println(getFirstDayOfMonth(2018,9));
//		System.out.println(getLastDayOfMonth(2018,9));
//		System.out.println(new BigDecimal(0.2).setScale(2, BigDecimal.ROUND_HALF_UP));// 0.2
//		System.out.println(new BigDecimal(34.42).setScale(2, BigDecimal.ROUND_HALF_UP));// 0.2
//		System.out.println(new BigDecimal(0.2444).setScale(2, BigDecimal.ROUND_HALF_UP));// 0.2
		System.out.print(getCurrentTime("2018-01-01","yyyy-MM-dd"));
	}
}
