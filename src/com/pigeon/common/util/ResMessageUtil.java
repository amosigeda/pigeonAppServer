package com.pigeon.common.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class ResMessageUtil {
	
	public static String responseMessage(String message) {
		Map<String, String> messageMap = new HashMap<String, String>();
		String messageValue = PropertiesFileUtils.getPropertiesByCodes().getProperty(message);
		System.out.println(messageValue);
		messageMap.put("CODES", messageValue);
		JSONObject jsonObj = JSONObject.fromObject(messageMap);
		return jsonObj.toString();
	}
	
	public static Map<String, String> responseMessageWithMap(String message) {
		Map<String, String> messageMap = new HashMap<String, String>();
		String messageValue = PropertiesFileUtils.getPropertiesByCodes().getProperty(message);
		messageMap.put("CODES", messageValue);
		return messageMap;
	}
	
	public static String responseValue(String message) {
		String messageValue = PropertiesFileUtils.getPropertiesByCodes().getProperty(message);
		return messageValue;
	}
	
	public static void main(String[] args) {
		System.out.println(ResMessageUtil.responseMessage("dealResult"));;
	}

}
