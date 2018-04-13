package com.pigeon.common.util;

import java.util.Random;

public class UserCodeUtil {
	
	public static String generateUserCode() {
		/*int maxLength = 9;
		Random random = new Random();
		double pross = (1 + random.nextDouble()) * Math.pow(10, maxLength);
		String fixLenthString = String.valueOf(pross);
		return fixLenthString.substring(1, maxLength + 1);*/
		
		//生成1－999999999之间的随机数，包括26
		Random random = new Random();
		int maxLength = 999999999;
		int year = DateUtils.YEAR;
		int randNum = random.nextInt(maxLength - year) + year;
		while(String.valueOf(randNum).length() != 9) {
			randNum = random.nextInt(maxLength - year) + year;
		}
		return String.valueOf(randNum);
	}
	
	public static void main(String[] args) {
		int i = 0;
		while(i < 200) {
			System.out.println(UserCodeUtil.generateUserCode());
			i++;
		}
	}

}
