package com.pigeon.common.util;

import org.apache.commons.lang3.ArrayUtils;

public class ArrayUtil {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] ns = new int[2];
		ns[0] = 0;
		ns[1] = 1;
		int[] ns1 = ns;
		ns1[0] = 1;

		String str = "abcd";
		byte[] bs1 = str.getBytes();
	
		byte[] bs3 = ArrayUtil.subArray(bs1, 6, bs1.length);
		System.out.println(new String(bs3));

	}
	public static byte[] subArray(byte[] bts, int start){
		bts=subArray(bts,start,bts.length);
		return bts;
	}
	/**
	 * 按起始截止序号截取数组
	 * 
	 * @param bts
	 * @param start
	 * @param end
	 * @return
	 */
	public static byte[] subArray(byte[] bts, int start, int end) {
		byte[] newos = ArrayUtils.subarray(bts, start, end);
		return newos;
	}

	/**
	 * 按照长度截取数组
	 * 
	 * @param bts
	 * @param start
	 * @param len
	 * @return
	 */
	public static byte[] subArrayWithLen(byte[] bts, int start, int len) {
		byte[] newos = ArrayUtils.subarray(bts, start, start + len);

		return newos;
	}

	public static byte[] addAll(byte[] bts, byte[] btsDest) {
		bts = ArrayUtils.addAll(bts, btsDest);
		return bts;
	}

	/**
	 * 反转数组顺序
	 * 
	 * @param bts
	 * @return
	 */
	public static byte[] reverse(byte[] bts) {
		ArrayUtils.reverse(bts);
		return bts;
	}

}
