package com.pigeon.common.util;

/**
 * 枚举类，只有(IOS)和(ANDROID)两种类型
 * @author liufeng
 * @date 2017-4-20
 */
public enum AppTypeEnum {

	IOS("IOS"),
	ANDROID("ANDROID");
	
	private AppTypeEnum(String desc){
		this.desc = desc;
	}
	
	/*** 描述*/
	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
