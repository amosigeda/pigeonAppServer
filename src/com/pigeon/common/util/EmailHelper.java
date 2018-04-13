package com.pigeon.common.util;

/**
 * Email工具类
 * 
 * @author fengmb 2016-12-29
 */
public class EmailHelper {

	public static boolean checkEmailFormat(String email) {
		if (StringUtil.verificationIsNull(email)) {
			return false;
		}
		String format = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z][@][a-z0-9A-Z]{3,}[.]\\p{Lower}{2,}";
		if (email.matches(format)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(EmailHelper.checkEmailFormat("feng.mingbao@foxmail.com"));
	}
}
