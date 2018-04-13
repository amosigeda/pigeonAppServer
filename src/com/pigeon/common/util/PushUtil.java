package com.pigeon.common.util;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushMessageToAndroidRequest;
import com.aliyuncs.push.model.v20160801.PushMessageToAndroidResponse;
import com.aliyuncs.push.model.v20160801.PushMessageToiOSRequest;
import com.aliyuncs.push.model.v20160801.PushMessageToiOSResponse;
import com.aliyuncs.push.model.v20160801.PushNoticeToAndroidRequest;
import com.aliyuncs.push.model.v20160801.PushNoticeToAndroidResponse;
import com.aliyuncs.push.model.v20160801.PushNoticeToiOSRequest;
import com.aliyuncs.push.model.v20160801.PushNoticeToiOSResponse;

/**
 * 推送方法集合
 * @author liufeng
 * @date 2017-4-19
 */
public class PushUtil {

	private final static Logger LOG = Logger.getLogger(PushUtil.class);
	
	public static DefaultAcsClient getClient(String regionId, String accessKeyId, String accessKeySecret){
		IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
		DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
	}
	
	/**
	 * 推送消息到Android
	 */
	public static void pushMessageToAndroid(String account,String title,String message) throws Exception{
		PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
		androidRequest.setProtocol(ProtocolType.HTTPS);
		androidRequest.setMethod(MethodType.POST);
		
		Properties properties = PropertiesFileUtils.getPropertiesPush();
		long appKey = Long.valueOf(properties.getProperty("appKey"));
		String regionId = properties.getProperty("regionId"); 
		String accessKeyId = properties.getProperty("accessKeyId");
		String accessKeySecret = properties.getProperty("accessKeySecret");
		
		androidRequest.setAppKey(appKey);
		androidRequest.setTarget("ACCOUNT");//ACCOUNT	DEVICE
		androidRequest.setTargetValue(account);
		androidRequest.setTitle(title);//标题
		androidRequest.setBody(message);//内容
		DefaultAcsClient client = getClient(regionId, accessKeyId, accessKeySecret);
		PushMessageToAndroidResponse response = client.getAcsResponse(androidRequest);
		System.out.printf("Android RequestId:%s,Android MessageId:%s", response.getRequestId(), response.getMessageId());
		LOG.info("Android RequestId:"+response.getRequestId()+",Android MessageId:"+response.getMessageId());
	}
	
	/**
	 * 推送通知到Android
	 */
	public static void pushNoticeToAndroid(String account,String title,String message) throws Exception{
		LOG.info("进入Android推送："+account+","+title+","+message);
		PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
		androidRequest.setProtocol(ProtocolType.HTTPS);
		androidRequest.setMethod(MethodType.POST);
		
		Properties properties = PropertiesFileUtils.getPropertiesPush();
		long appKey = Long.valueOf(properties.getProperty("appKey"));
		String regionId = properties.getProperty("regionId"); 
		String accessKeyId = properties.getProperty("accessKeyId");
		String accessKeySecret = properties.getProperty("accessKeySecret");
		
		androidRequest.setAppKey(appKey);
		androidRequest.setTarget("ACCOUNT");//ACCOUNT	DEVICE
		androidRequest.setTargetValue(account);
		androidRequest.setTitle(title);//标题
		androidRequest.setBody(message);//内容
		
		DefaultAcsClient client =  getClient(regionId, accessKeyId, accessKeySecret);
		PushNoticeToAndroidResponse response = client.getAcsResponse(androidRequest);
		System.out.printf("Android RequestId:%s,Android MessageId:%s", response.getRequestId(), response.getMessageId());
		LOG.info("Android RequestId:"+response.getRequestId()+",Android MessageId:"+response.getMessageId());
	}
	
	/**
	 * 推送消息到IOS
	 */
	public static void pushMessageToIOS(String account,String title, String message) throws Exception{
		PushMessageToiOSRequest iOSRequest = new PushMessageToiOSRequest();
		iOSRequest.setProtocol(ProtocolType.HTTPS);
		iOSRequest.setMethod(MethodType.POST);
		
		Properties properties = PropertiesFileUtils.getPropertiesPush();
		long appKey = Long.valueOf(properties.getProperty("appKey"));
		String regionId = properties.getProperty("regionId"); 
		String accessKeyId = properties.getProperty("accessKeyId");
		String accessKeySecret = properties.getProperty("accessKeySecret");
		String deviceId = properties.getProperty("deviceId");
		iOSRequest.setAppKey(appKey);
		iOSRequest.setTarget("ACCOUNT");//ACCOUNT	DEVICE
		iOSRequest.setTargetValue(account);//先指定deviceId进行测试，正式环境使用account
		iOSRequest.setTitle(title);
		iOSRequest.setBody(message);
		
		DefaultAcsClient client = getClient(regionId, accessKeyId, accessKeySecret);
		PushMessageToiOSResponse iOSResponse = client.getAcsResponse(iOSRequest);
		System.out.printf("IOS RequestId:%s,IOS MessageId:%s", iOSResponse.getRequestId(), iOSResponse.getMessageId());
		LOG.info("IOS RequestId:"+iOSResponse.getRequestId()+",IOS MessageId:"+iOSResponse.getMessageId());
	}
	
	/**
	 * 推送通知到IOS
	 */
	public static void pushNoticeToIOS(String account,String message) throws Exception{
		PushNoticeToiOSRequest iOSRequest = new PushNoticeToiOSRequest();
		iOSRequest.setProtocol(ProtocolType.HTTPS);
		iOSRequest.setMethod(MethodType.POST);
		
		Properties properties = PropertiesFileUtils.getPropertiesPush();
		long appKey = Long.valueOf(properties.getProperty("appKey"));
		String regionId = properties.getProperty("regionId"); 
		String accessKeyId = properties.getProperty("accessKeyId");
		String accessKeySecret = properties.getProperty("accessKeySecret");
		String deviceId = properties.getProperty("deviceId");
		iOSRequest.setAppKey(appKey);
		// iOS的通知是通过APNS中心来发送的，需要填写对应的环境信息. DEV :表示开发环境, PRODUCT: 表示生产环境
		iOSRequest.setApnsEnv("DEV");
		iOSRequest.setTarget("ACCOUNT");//ACCOUNT	DEVICE
		iOSRequest.setTargetValue(account);//先指定deviceId进行测试，正式环境使用account
		iOSRequest.setBody(message);
		
		DefaultAcsClient client = getClient(regionId, accessKeyId, accessKeySecret);
		PushNoticeToiOSResponse iOSResponse = client.getAcsResponse(iOSRequest);
		System.out.printf("IOS RequestId:%s,IOS MessageId:%s", iOSResponse.getRequestId(), iOSResponse.getMessageId());
		LOG.info("IOS RequestId:"+iOSResponse.getRequestId()+",IOS MessageId:"+iOSResponse.getMessageId());
	}
	
}
