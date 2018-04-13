package com.pigeon.spring.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pigeon.common.util.AppTypeEnum;
import com.pigeon.common.util.DateUtils;
import com.pigeon.common.util.PushUtil;
import com.pigeon.common.util.ResMessageUtil;
import com.pigeon.common.util.StringUtil;
import com.pigeon.common.util.json.JsonDateValueProcessor;
import com.pigeon.spring.entity.T_ADD_FRIEND_RECORD;
import com.pigeon.spring.entity.T_FRIENDS_LIST;
import com.pigeon.spring.entity.T_MESSAGE_CONTENT;
import com.pigeon.spring.entity.T_MESSAGE_LIST;
import com.pigeon.spring.entity.T_USER_INFO;
import com.pigeon.spring.service.FriendManageDao;
import com.pigeon.spring.service.MessageManageDao;
import com.pigeon.spring.service.PushManageDao;
import com.pigeon.spring.service.UserLoginService;
import com.pigeon.spring.service.UserManageDao;

/**
 * 好友管理
 * @author liufeng
 * @date 2017-4-17
 */
@Controller
@RequestMapping(value = "/friend")
public class FriendsController {

	private static final Logger logger = Logger.getLogger(FriendsController.class);
	
	@Autowired
	private FriendManageDao friendManageDao;
	@Autowired
	private MessageManageDao messageManageDao;
	@Autowired
	private UserManageDao userManageDao;
	@Autowired
	private PushManageDao pushManageDao;
	
	/**
	 * 根据用户编号搜索好友
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/queryUserInfo", produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryUserInfo(@RequestParam("params") String params){
		logger.info("调用搜索用户接口：" + params);
		JSONObject json = new JSONObject();
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String user_code = StringUtil.getStringFromJson(paramsJSON, "USER_CODE");
			String userObjId = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(StringUtil.verificationIsNull(user_code)||StringUtil.verificationIsNull(userObjId)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			T_USER_INFO user_info = friendManageDao.queryUserInfoByUserCode(user_code);
			if(user_info != null){//自己
				if(user_info.getOBJ_ID().equals(userObjId)){
					json.put("RELATION", 3);
				}else{
					T_FRIENDS_LIST friend = friendManageDao.queryFriendRelation(userObjId, user_info.getOBJ_ID());
					if(friend != null){
						json.put("RELATION", 1);
					}else{
						json.put("RELATION", 0);
					}
				}
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONObject jsonObj = JSONObject.fromObject(user_info, jsonConfig);
			json.put("userInfo", jsonObj);
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("FriendsController-->queryUserInfo:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 发送私信
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/sendMessage",produces = "application/json;charset=UTF-8")
	public @ResponseBody String sendMessage(@RequestParam("params") String params){
		logger.info("调用发送私信接口：" + params);
		JSONObject json = new JSONObject();
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String userId = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			String friendId = StringUtil.getStringFromJson(paramsJSON, "FRIEND_OBJ_ID");
			String content = StringUtil.getStringFromJson(paramsJSON, "CONTENT");
			
			if(StringUtil.verificationIsNull(userId)||StringUtil.verificationIsNull(friendId)||StringUtil.verificationIsNull(content)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			
			T_MESSAGE_LIST message = messageManageDao.queryMessageList(userId, friendId);
			T_MESSAGE_CONTENT mesContent = new T_MESSAGE_CONTENT();
			if(message == null){
				//新增私信列表，然后发送私信
				message = new T_MESSAGE_LIST();
				message.setUSER_OBJ_ID(userId);
				message.setPRIVATE_USER_ID(friendId);
				message.setCREATE_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
				String messageObjId = messageManageDao.addMessageList(message);
				mesContent.setMESSAGE_OBJ_ID(messageObjId);
			}
			//直接发送私信
			if(mesContent.getMESSAGE_OBJ_ID() == null){
				mesContent.setMESSAGE_OBJ_ID(message.getOBJ_ID());
			}
			mesContent.setCONTENT(content);
			mesContent.setCREATE_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
			messageManageDao.addMessageContent(mesContent);
			//推送信息给friendId
			T_USER_INFO friendInfo = userManageDao.getUserInfoByObjID(friendId);
			if(friendInfo == null){
				return ResMessageUtil.responseMessage("UserIsNull");
			}else{
				if(friendInfo.getAPP_TYPE().equals(AppTypeEnum.IOS.name())){
					PushUtil.pushMessageToIOS(friendInfo.getUSER_PHONE(), "私信", content);
				}else if(friendInfo.getAPP_TYPE().equals(AppTypeEnum.ANDROID.name())){
					PushUtil.pushMessageToAndroid(friendInfo.getUSER_PHONE(), "私信", content);
				}
			}
			
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("FriendsController-->sendMessage:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 获取私信用户列表
	 */
	@RequestMapping(value = "/queryMessageList",produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryMessageList(@RequestParam("params")String params){
		logger.info("开始调用获取私信列表接口："+params);
		JSONObject json = new JSONObject();
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用获取私信列表接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String userId = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			if(StringUtil.verificationIsNull(userId)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			List<Map<String, Object>> list = messageManageDao.queryMessageByObjId(userId);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);
			
			json.put("messageList", jsonArr);
			json.put("amount", list.size());
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("FriendsController-->queryMessageList:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 获取私信内容
	 */
	@RequestMapping(value = "/queryMessageContent",produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryMessageContent(@RequestParam("params")String params){
		JSONObject json = new JSONObject();
		logger.info("开始调用获取私信列表接口："+params);
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用获取私信列表接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String userId = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			String messageObjId = StringUtil.getStringFromJson(paramsJSON, "MESSAGE_OBJ_ID");
			if(StringUtil.verificationIsNull(userId)||StringUtil.verificationIsNull(messageObjId)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
//			List<T_MESSAGE_CONTENT> list = messageManageDao.queryMessageContent(messageObjId);
			List<Map<String, Object>> list = messageManageDao.queryMessageContent(messageObjId);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);
			
			json.put("amount", list.size());
			json.put("messageContent", jsonArr);
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("FriendsController-->queryMessageContent:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 请求添加好友
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/addFriendReq",produces = "application/json;charset=UTF-8")
	public @ResponseBody String addFriends(@RequestParam("params") String params){
		logger.info("调用添加好友接口：" + params);
		JSONObject json = new JSONObject();
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String userId = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			String friendId = StringUtil.getStringFromJson(paramsJSON, "FRIEND_OBJ_ID");
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(StringUtil.verificationIsNull(userId)||StringUtil.verificationIsNull(friendId)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			T_FRIENDS_LIST friend = friendManageDao.queryFriendRelation(userId, friendId);
			if(friend != null){
				//已是好友
				json.putAll(ResMessageUtil.responseMessageWithMap("haveFriend"));
			}else{
				//添加好友
				//是否有未处理请求状态，如果有则修改时间，否则新增一条加好友请求
				T_ADD_FRIEND_RECORD friendRecord = friendManageDao.queryFriendReq(userId, friendId);
				if(friendRecord == null){
					T_ADD_FRIEND_RECORD record = new T_ADD_FRIEND_RECORD();
					record.setUSER_OBJ_ID(userId);
					record.setFRIEND_OBJ_ID(friendId);
					record.setADD_STATUS(0);
					record.setADD_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
					friendManageDao.addFriendRequest(record);
				}else{
					friendRecord.setADD_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
					friendManageDao.updateFriendRecord(friendRecord);
				}
				//推送通知给friendId(通知不用考虑是否在线)
				T_USER_INFO friendInfo = userManageDao.getUserInfoByObjID(friendId);
				if(friendInfo == null){
					return ResMessageUtil.responseMessage("UserIsNull");
				}else{
					if(friendInfo.getAPP_TYPE().equals(AppTypeEnum.IOS.name())){
						PushUtil.pushNoticeToIOS(friendInfo.getUSER_PHONE(), userInfo.getUSER_CODE()+"请求添加我为好友");
					}else if(friendInfo.getAPP_TYPE().equals(AppTypeEnum.ANDROID.name())){
						PushUtil.pushNoticeToAndroid(friendInfo.getUSER_PHONE(), "新朋友", userInfo.getUSER_CODE()+"请求添加我为好友");
					}
				}
				json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
			}
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("FriendsController-->sendMessage:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 获取用户加好友请求列表
	 */
	@RequestMapping(value = "/queryAddFriendList",produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryAddFriendList(@RequestParam("params")String params){
		JSONObject json = new JSONObject();
		logger.info("开始调用获取用户添加好友请求列表接口："+params);
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String userId = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			if(StringUtil.verificationIsNull(userId)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			List<Map<String, Object>> list = friendManageDao.queryAddFriendList(userId);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);
			
			json.put("amount", list.size());
			json.put("requestList", jsonArr);
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("FriendsController-->queryAddFriendList:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 处理添加好友请求
	 */
	@RequestMapping(value = "/dispose",produces = "application/json;charset=UTF-8")
	public @ResponseBody String disposeFriend(@RequestParam("params")String params){
		logger.info("开始调用处理添加好友请求接口："+params);
		JSONObject json = new JSONObject();
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String objId = StringUtil.getStringFromJson(paramsJSON, "OBJ_ID");//添加好友表主键
//			String userId = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");//用户id
//			String friendId = StringUtil.getStringFromJson(paramsJSON, "FRIEND_OBJ_ID");
			int addStatus = StringUtil.getIntegerFromJson(paramsJSON, "ADD_STATUS");
			if(StringUtil.verificationIsNull(objId)){//||StringUtil.verificationIsNull(userId)||StringUtil.verificationIsNull(friendId)
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			//查询添加好友请求记录
			T_ADD_FRIEND_RECORD record = friendManageDao.queryRecordByObjId(objId);
			if(record != null){
				//1、先查询是否是好友
				T_FRIENDS_LIST friendRelation = friendManageDao.queryFriendRelation(record.getUSER_OBJ_ID(), record.getFRIEND_OBJ_ID());
				//2、是，则提示，否则处理请求
				if(friendRelation != null){
					return ResMessageUtil.responseMessage("haveFriend");
				}
				record.setADD_STATUS(addStatus);
				friendManageDao.updateFriendRecord(record);
				if(addStatus == 1){//同意
					T_FRIENDS_LIST friend = new T_FRIENDS_LIST();
					friend.setUSER_OBJ_ID(record.getUSER_OBJ_ID());
					friend.setFRIENDS_OBJ_ID(record.getFRIEND_OBJ_ID());
					friend.setADD_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
					friendManageDao.addFriendRelation(friend);
					//推送通知给friendId
					T_USER_INFO friendInfo = userManageDao.getUserInfoByObjID(record.getUSER_OBJ_ID());
					logger.info("是否存在推送用户aaaaa");
					if(friendInfo == null){
						logger.info("不存在推送用户bbbbbb");
						return ResMessageUtil.responseMessage("UserIsNull");
					}else{
						logger.info("存在推送用户cccccc");
						if(friendInfo.getAPP_TYPE().equals(AppTypeEnum.IOS.name())){
							logger.info("存在iOS推送用户ddddd");
							PushUtil.pushNoticeToIOS(friendInfo.getUSER_PHONE(), userInfo.getUSER_CODE()+"请求添加我为好友");
						}else if(friendInfo.getAPP_TYPE().equals(AppTypeEnum.ANDROID.name())){
							logger.info("存在Android推送用户eeeeee");
							PushUtil.pushNoticeToAndroid(friendInfo.getUSER_PHONE(), "鸽环", userInfo.getUSER_CODE()+"请求添加我为好友");
						}
					}
				}
			}
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("FriendsController-->disposeFriend:"+e);
		}
		return json.toString();
	}
	
}
