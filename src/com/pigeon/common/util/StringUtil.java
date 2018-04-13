package com.pigeon.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

public class StringUtil {

	/**
	 * 验证字符串是否为空，若为空则返回true，否则返回false
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean verificationIsNull(String str) {
		if (str == null || str.length() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 验证是否为正整数，若为正整数则返回true,否则返回false
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean verificationIsInteger(String str) {
		String rex = "^\\d*[1-9]\\d*$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 验证日期格式是否合法
	 * @param date String
	 * @return boolean
	 */
	public static boolean isValidDate(String date) {
		int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		try {
			int year = Integer.parseInt(date.substring(0, 4));
			if (year <= 0)
				return false;
			int month = Integer.parseInt(date.substring(5, 7));
			if (month <= 0 || month > 12)
				return false;
			int day = Integer.parseInt(date.substring(8, 10));
			if (day <= 0 || day > DAYS[month])
				return false;
			if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {
				return false;
			}
			int hour = Integer.parseInt(date.substring(11, 13));
			if (hour < 0 || hour > 23)
				return false;
			int minute = Integer.parseInt(date.substring(14, 16));
			if (minute < 0 || minute > 59)
				return false;
			int second = Integer.parseInt(date.substring(17, 19));
			if (second < 0 || second > 59)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static final boolean isGregorianLeapYear(int year) {  
	    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);  
	}  

	public static String getStringFromJson(JSONObject json,String str){
    	return json.containsKey(str)?json.getString(str):"";
    }
	
	public static int getIntegerFromJson(JSONObject json,String str){
		return json.containsKey(str)?json.getInt(str):0;
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtil.verificationIsInteger("1500"));
		System.out.println(StringUtil.verificationIsNull(""));
		System.out.println(isValidDate("2020-02-29 24:00:01")); 
	}

}
