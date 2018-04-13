package com.pigeon.common.util;

import net.sf.json.JSONObject;

public class BaiDuUtil {
	
	public static String getProvince(String lat, String lng) {
		JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
		.getJSONObject("addressComponent");
		return obj.getString("province");
	}
	
	public static String getCountry(String lat, String lng) {
		JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
		.getJSONObject("addressComponent");
		return obj.getString("country");
	}
	
	public static String getCity(String lat, String lng) {
		JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
				.getJSONObject("addressComponent");
		return obj.getString("city");
	}

	/**
	 * 根据经纬度获取地址
	 * 示例数据：{"distance":"","direction":"","street":"青山湖隧道","province":"江西省","adcode":"360111","street_number":"","district":"青山湖区","country_code":0,"city":"南昌市","country":"中国"}
	 * @param lat String
	 * @param lng String
	 * @return
	 */
	public static JSONObject getLocationInfo(String lat, String lng) {
		String url = "http://api.map.baidu.com/geocoder/v2/?location=" + lat + ","
				+ lng + "&output=json&ak=" + PropertiesFileUtils.getProperties().getProperty("BAIDU_GEOCONV_KEY")+"&pois=0";
		JSONObject obj = JSONObject.fromObject(HttpUtil.getRequest(url));
		return obj;
	}

	public static void main(String[] args) {
		System.out.println(BaiDuUtil.getCity("28.694439", "115.939728"));
	}

}
