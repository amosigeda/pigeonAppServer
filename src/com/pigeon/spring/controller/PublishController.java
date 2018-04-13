package com.pigeon.spring.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pigeon.common.util.DateUtils;
import com.pigeon.common.util.ImageUploadUtil;
import com.pigeon.common.util.ResMessageUtil;
import com.pigeon.common.util.StringUtil;
import com.pigeon.common.util.json.JsonDateValueProcessor;
import com.pigeon.spring.entity.T_DYNAMIC;
import com.pigeon.spring.entity.T_USER_INFO;
import com.pigeon.spring.service.AgreeManageDao;
import com.pigeon.spring.service.DynamicManageDao;
import com.pigeon.spring.service.ImageUrlManageDao;
import com.pigeon.spring.service.UserLoginService;

/**
 * 动态管理
 * @author liufeng
 * @date 2017-3-31
 */
@Controller
@RequestMapping(value = "/publish")
public class PublishController {

	private static final Logger logger = Logger.getLogger(PublishController.class);
	
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private DynamicManageDao dynamicManageDao;
	@Autowired
	private AgreeManageDao agreeManageDao;
	@Autowired
	private ImageUrlManageDao imageUrlManageDao;
	
	/**
	 * 发表动态，附带图片
	 * @return
	 */
	@RequestMapping(value = "/addDynamicFile",produces = "application/json;charset=UTF-8")
	public @ResponseBody String addDynamicFile(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		try {
			String token = request.getParameter("TOKEN");
			String user_obj_id = request.getParameter("USER_OBJ_ID");
			String content = request.getParameter("CONTENT");
			
			if(StringUtil.verificationIsNull(user_obj_id)||StringUtil.verificationIsNull(content)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			List<String> list = ImageUploadUtil.uploadFiles(request, "upload");
			
			T_DYNAMIC t_dynamic = new T_DYNAMIC();
			t_dynamic.setUSER_OBJ_ID(user_obj_id);
			t_dynamic.setCONTENT(content);
			t_dynamic.setCOMMENT_NUM(0);
			t_dynamic.setTRANSPOND_NUM(0);
			t_dynamic.setPRAISE_NUM(0);
			t_dynamic.setCREATE_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
			dynamicManageDao.addDynamicAndImage(t_dynamic, list);
			
			result = ResMessageUtil.responseMessage("succes");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("PublishController-->addDynamicFile:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 新增动态
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/addDynamic" ,produces = "application/json;charset=UTF-8")
	public @ResponseBody String addDynamic(@RequestParam("params") String params,HttpServletRequest request){
		logger.info("开始调用发表动态接口：" + params);
		String result = null;
		try {
			String fileName = ImageUploadUtil.upload(request, "upload");
			System.out.println("fileName:"+fileName);
			
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String user_obj_id = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			String content = StringUtil.getStringFromJson(paramsJSON, "CONTENT");
			if(StringUtil.verificationIsNull(user_obj_id)||StringUtil.verificationIsNull(content)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			
			T_DYNAMIC t_dynamic = new T_DYNAMIC();
			t_dynamic.setUSER_OBJ_ID(user_obj_id);
			t_dynamic.setCONTENT(content);
			t_dynamic.setCOMMENT_NUM(0);
			t_dynamic.setTRANSPOND_NUM(0);
			t_dynamic.setPRAISE_NUM(0);
			t_dynamic.setCREATE_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
			
			dynamicManageDao.addDynamic(t_dynamic);
			result = ResMessageUtil.responseMessage("succes");
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			logger.error("PublishController-->addDynamic:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 分页查询所有鸽圈动态
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/queryAll",produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryAll(@RequestParam("params") String params){
		logger.info("开始调用查询所有鸽圈动态接口：" + params);
		JSONObject json = new JSONObject();
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用查询所有鸽圈动态接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String obj_user_id = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			int pageNum = StringUtil.getIntegerFromJson(paramsJSON, "PAGENUM");
			int pageSize = StringUtil.getIntegerFromJson(paramsJSON, "PAGESIZE");
			if(pageNum < 1){
				pageNum = 0;
			}
			if(pageSize < 1){
				pageSize = 10;
			}
			if(StringUtil.verificationIsNull(obj_user_id)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			List<Map<String, Object>> list = dynamicManageDao.queryAllDyanamic(pageNum, pageSize);
			//查询好友id
			List<String> friends = dynamicManageDao.queryFriends(obj_user_id);
			for(int i=0;i<list.size();i++){
				Map<String, Object> map = list.get(i);
				String USER_OBJ_ID = map.get("USER_OBJ_ID").toString();
				for(int j=0;j<friends.size();j++){
					String f = friends.get(j);
					if(USER_OBJ_ID.equals(f)){
						map.put("RELATION", 1);
					}
				}
			}
			//获取总数
			String hql = "SELECT COUNT(*) FROM T_DYNAMIC";
			Long count = dynamicManageDao.queryTotalCount(hql, null);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);
			
			json.put("allDyanmic", jsonArr);
			json.put("amount", list.size());
			json.put("count", count);
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("PublishController-->queryAll:"+e.getMessage());
		}
		return json.toString();
	}
	
	/**
	 * 分页查询好友鸽圈动态
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/queryFriendsDynamic",produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryFriendsDynamic(@RequestParam("params") String params){
		JSONObject json = new JSONObject();
		logger.info("开始调用查询好友鸽圈动态接口：" + params);
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String obj_user_id = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			int pageNum = StringUtil.getIntegerFromJson(paramsJSON, "PAGENUM");
			int pageSize = StringUtil.getIntegerFromJson(paramsJSON, "PAGESIZE");
			if(pageNum < 1){
				pageNum = 0;
			}
			if(pageSize < 1){
				pageSize = 10;
			}
			if(StringUtil.verificationIsNull(token)){
				return ResMessageUtil.responseMessage("UserTokenIsNull");
			}
			if(StringUtil.verificationIsNull(obj_user_id)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			String friends = queryFriendList(obj_user_id);
			List<Map<String, Object>> friendsList = dynamicManageDao.queryFriendsDynamics(friends, pageNum, pageSize);
			//查询好友总数
			String hql = "SELECT COUNT(*) FROM T_DYNAMIC d WHERE d.USER_OBJ_ID IN ("+friends+") ";
			Long count = dynamicManageDao.queryTotalCount(hql, null);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(friendsList, jsonConfig);
			
			json.put("friendDynamic", jsonArr);
			json.put("amount", friendsList.size());
			json.put("count", count);
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("PublishController-->queryFriendsDynamic:"+e.getMessage());
		}
		return json.toString();
	}
	
	/**
	 * 查询好友列表
	 * @param user_obj_id
	 * @return
	 * @throws Exception
	 */
	public String queryFriendList(String user_obj_id) throws Exception{
		List<String> list = dynamicManageDao.queryFriends(user_obj_id);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<list.size();i++){
			if((list.size() == 1)||(i == list.size() - 1)){
				sb.append("'"+list.get(i)+"'");
				continue;
			}
			sb.append("'"+list.get(i)+"',");
		}
		return sb.toString();
	}
	
	/**
	 * 查询我的鸽圈动态
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/queryMyDynamic",produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryMyDynamic(@RequestParam("params") String params){
		JSONObject json = new JSONObject();
		logger.info("开始调用查询我的鸽圈动态接口：" + params);
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String obj_user_id = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			int pageNum = StringUtil.getIntegerFromJson(paramsJSON, "PAGENUM");
			int pageSize = StringUtil.getIntegerFromJson(paramsJSON, "PAGESIZE");
			if(pageNum < 1){
				pageNum = 0;
			}
			if(pageSize < 1){
				pageSize = 10;
			}
			if(StringUtil.verificationIsNull(obj_user_id)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			List<Map<String, Object>> list = dynamicManageDao.queryMyMapDynamic(obj_user_id, pageNum, pageSize);
			String hql = "SELECT COUNT(*) FROM T_DYNAMIC d WHERE d.USER_OBJ_ID = '"+obj_user_id+"' ";
			Long count = dynamicManageDao.queryTotalCount(hql, null);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);
			json.put("myDynamic", jsonArr);
			json.put("count", count);
			json.put("amount", list.size());
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("PublishController-->queryMyDynamic:"+e.getMessage());
		}
		return json.toString();
	}
	
}
