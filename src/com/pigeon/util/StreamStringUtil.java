package com.pigeon.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;




public class StreamStringUtil {
	/** 日志实例 */
	private static final Logger logger = Logger
			.getLogger(StreamStringUtil.class);
	
	final static int BUFFER_SIZE = 4096;
	private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final String ENCODING = "utf-8";
	
	public static String InputStreamTOString(HttpServletRequest request) throws IOException{
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while((online = reader.readLine()) != null){
			sb.append(online);
		}
		return sb.toString();
	}
	
	public static String InputStreamTOStringIn(InputStream in) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), ENCODING);
	}
	/*
	 * 时间戳转时间
	 * */
	public static String stampToDate(long l) {
		String res="";
		long lt = new Long(l);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}
	
	public static String sendGetToGaoDe(String url, HashMap<String,String> map){		
		String urlNameString = url + paramsTransToUrl(map);
		logger.info("urlNameString="+urlNameString);
		String returnParams = urlReturnParams(urlNameString);
		return returnParams;
	}
	
	public static String paramsTransToUrl(HashMap<String,String> map){
        StringBuffer params = new StringBuffer("?");
		
		for(String key : map.keySet()){
			if(!params.toString().equals("?")){
				params.append("&");
			}
			params.append(key).append("=").append(map.get(key));
		}
		return params.toString();
	}
	
	@SuppressWarnings("finally")
	public static String urlReturnParams(String urlNameString){
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			HttpURLConnection httpConnect = (HttpURLConnection)connection;
			httpConnect.setRequestProperty("accept", "*/*");
			httpConnect.setRequestProperty("connection", "Keep-Alive");
			httpConnect.setRequestProperty("user-agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConnect.connect();
			int code = httpConnect.getResponseCode();
			if(code == 200){
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = in.readLine()) != null){
					sb.append(line);
				}
				in.close();
			}else{
				sb.append("-1");
			}
			
		}catch (Exception e) {
			sb.append("-1");
		}finally{
			return sb.toString();
		}	
	}
	
}
