package com.pigeon.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };
	/**
	 * 年
	 */
	public static final int YEAR = Calendar.YEAR;
	/**
	 * 月
	 */
	public static final int MONTH = Calendar.MONTH;
	/**
	 * 日
	 */
	public static final int DATE = Calendar.DATE;
	/**
	 * 时
	 */
	public static final int HOUR = Calendar.HOUR;
	/**
	 * 分钟
	 */
	public static final int MINUTE = Calendar.MINUTE;

	/**
	 * 秒
	 */
	public static final int SECOND = Calendar.SECOND;
	
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 将UTC时间戳转换为本地时间（yyyy-MM-dd HH:mm:ss）
	 * @param utcTime String UTC时间戳
	 * @return String
	 * @throws ParseException
	 */
	public static String utcToLocalTime(String utcTime) throws ParseException {
		Long longUtcTime = Long.valueOf(utcTime) * 1000;
		String localTime = format.format(new Date(longUtcTime));
		return localTime;
	}
	
	/**
	 * 获取当前时间戳
	 * @return String
	 */
	public static String getCurrentUtcTime() {
		long currentTime = System.currentTimeMillis() / 1000;
		return String.valueOf(currentTime);
	}
	
	public static long getLocalTime(Date date) {
		long l = date.getTime();
		l = l / 1000;
		return l;
	}

	public static Date getLocalDate(long l) {
		l = l * 1000;
		Date date = new Date(l);
		return date;
	}

	/**
	 * 获得指定日期的字符串形式
	 * 
	 * @param date
	 *            指定日期
	 * @param formate
	 *            日期格式
	 * @return
	 */
	public static String getDateStr(Date date, String formate) {
		String result = null;
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateformate = new SimpleDateFormat(formate);
		result = dateformate.format(date);
		return result;
	}

	/**
	 * 将指定格式的日期字符串转换为新的日期格式字符串
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param srcFormat
	 *            源日期格式
	 * @param destFormat
	 *            目标日期格式
	 * @return
	 * @throws ParseException
	 */
	public static String getDateStr(String dateStr, String srcFormat, String destFormat) throws ParseException {
		SimpleDateFormat dateformate = new SimpleDateFormat(srcFormat);
		String str = null;
		Date date = dateformate.parse(dateStr);
		dateformate = new SimpleDateFormat(destFormat);
		str = dateformate.format(date);
		return str;
	}

	/**
	 * 将日期字符串转换日期类型
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateStr, String format) throws ParseException {
		Date date = null;
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		SimpleDateFormat dateformate = new SimpleDateFormat(format);

		date = dateformate.parse(dateStr);
		return date;
	}

	/**
	 * 取得服务器的日期字符串，日期格式为“yyyy-MM-dd”
	 * 
	 * @return 日期字符串
	 */
	public static String getSysDateStr() {
		return DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 取得服务器的日期时间字符串，日期格式为“yyyy-MM-dd HH:mm:ss”
	 * 
	 * @return 日期字符串
	 */
	public static String getSysDateTimeStr() {
		return DateUtils.getDateStr(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 时间加法（分钟）
	 * 
	 * @param date
	 * @param minTimeLen
	 * @return
	 */
	public static Date addTime(Date date, int timeLen, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, timeLen);
		return calendar.getTime();
	}

	public static Date parseDates(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
/*
	public static void main(String[] arps) throws ParseException {
		String fileName = "2010-03-29-1.sql";
		String[] temp = fileName.split("\\.");
		temp[0].substring(11);
		String str = String.format("%04d", 121);
		System.out.println(str);
	}*/

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println(System.currentTimeMillis() / 1000);
			System.out.println(utcToLocalTime("1474388012"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
