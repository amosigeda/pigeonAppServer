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

import com.pigeon.common.util.DateUtils;
import com.pigeon.common.util.ResMessageUtil;
import com.pigeon.common.util.StringUtil;
import com.pigeon.common.util.json.JsonDateValueProcessor;
import com.pigeon.spring.entity.T_AGREE;
import com.pigeon.spring.entity.T_COMMENT;
import com.pigeon.spring.entity.T_DYNAMIC;
import com.pigeon.spring.entity.T_USER_INFO;
import com.pigeon.spring.service.AgreeManageDao;
import com.pigeon.spring.service.CommentManageDao;
import com.pigeon.spring.service.DynamicManageDao;
import com.pigeon.spring.service.UserLoginService;

/**
 * 点赞、转发、评论
 * @author liufeng
 * @date 2017-4-14
 */
@Controller
@RequestMapping(value = "/dynamic")
public class DynamicController {

	private static final Logger logger = Logger.getLogger(DynamicController.class);
	
	@Autowired
	private AgreeManageDao agreeManageDao;
	@Autowired
	private static DynamicManageDao dynamicManageDao;
	@Autowired
	private CommentManageDao commentManageDao;
	
	/**
	 * 点赞
	 */
	@RequestMapping(value = "/doAgree",produces = "application/json;charset=UTF-8")
	public @ResponseBody String doAgree(@RequestParam("params") String params){
		JSONObject json = new JSONObject();
		logger.info("开始调用点赞接口：" + params);
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String obj_user_id = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			String dynamic_id = StringUtil.getStringFromJson(paramsJSON, "DYNAMIC_ID");
			
			if(StringUtil.verificationIsNull(token)){
				return ResMessageUtil.responseMessage("UserTokenIsNull");
			}
			if(StringUtil.verificationIsNull(obj_user_id)||StringUtil.verificationIsNull(dynamic_id)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			T_AGREE t_agree = agreeManageDao.queryAgreeByUserId(obj_user_id, dynamic_id);
			if(t_agree != null){//提示已点赞
				json.putAll(ResMessageUtil.responseMessageWithMap("haveThumbUp"));
			}else{
				t_agree = new T_AGREE();
				t_agree.setDYNAMIC_ID(dynamic_id);
				t_agree.setUSER_OBJ_ID(obj_user_id);
//				agreeManageDao.addAgree(t_agree);
				
				//维护动态表
				String hql = "UPDATE T_DYNAMIC SET PRAISE_NUM = PRAISE_NUM + 1 WHERE DYNAMIC_ID = '"+dynamic_id+"'";
//				dynamicManageDao.updateDynamicByCondition(hql);
				agreeManageDao.addAgreeAndUpdateDynamic(t_agree, hql);
				json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
			}
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("PublishController-->doAgree:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 转发
	 */
	@RequestMapping(value = "/transpond",produces = "application/json;charset=UTF-8")
	public @ResponseBody String doTranspond(@RequestParam("params") String params){
		JSONObject json = new JSONObject();
		logger.info("开始调用转发接口："+params);
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String obj_user_id = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			String dynamic_id = StringUtil.getStringFromJson(paramsJSON, "DYNAMIC_ID");
			String tran_obj_id = StringUtil.getStringFromJson(paramsJSON, "TRAN_OBJ_ID");
			String reason = StringUtil.getStringFromJson(paramsJSON, "REASON");
			if(StringUtil.verificationIsNull(token)){
				return ResMessageUtil.responseMessage("UserTokenIsNull");
			}
			if(StringUtil.verificationIsNull(obj_user_id)||StringUtil.verificationIsNull(tran_obj_id)||
					StringUtil.verificationIsNull(reason)||StringUtil.verificationIsNull(dynamic_id)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			
			T_DYNAMIC t_dynamic = dynamicManageDao.queryDynamicById(dynamic_id);
			T_DYNAMIC dm = new T_DYNAMIC();
			dm.setUSER_OBJ_ID(obj_user_id);
			dm.setCONTENT(t_dynamic.getCONTENT());
			dm.setIMAGE_URL(t_dynamic.getIMAGE_URL());
			dm.setTRANSPOND_NUM(0);
			dm.setPRAISE_NUM(0);
			dm.setCOMMENT_NUM(0);
			dm.setTRAN_OBJ_ID(tran_obj_id);
			dm.setREASON(reason);
			dm.setCREATE_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
			dynamicManageDao.addDynamic(dm);
			String hql = "UPDATE T_DYNAMIC SET TRANSPOND_NUM = TRANSPOND_NUM + 1 WHERE DYNAMIC_ID = '"+dynamic_id+"'";
			dynamicManageDao.updateDynamicByCondition(hql);
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("DynamicController-->doAgree:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 查询评论
	 */
	@RequestMapping(value = "/queryComment",produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryComment(@RequestParam("params")String params){
		JSONObject json = new JSONObject();
		logger.info("开始调用查询评论接口："+params);
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String obj_user_id = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			String dynamic_id = StringUtil.getStringFromJson(paramsJSON, "DYNAMIC_ID");
			
			if(StringUtil.verificationIsNull(obj_user_id)||StringUtil.verificationIsNull(dynamic_id)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			List<Map<String, Object>> list = commentManageDao.queryCommentList(dynamic_id);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);
			json.put("commentList", jsonArr);
			json.put("amount", list.size());
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("DynamicController-->queryComment:"+e);
		}
		return json.toString();
	}
	
	/**
	 * 新增评论
	 */
	@RequestMapping(value = "/addComment",produces = "application/json;charset=UTF-8")
	public @ResponseBody String addComment(@RequestParam("params") String params){
		JSONObject json = new JSONObject();
		logger.info("开始调用新增评论接口："+params);
		try {
			if(StringUtil.verificationIsNull(params)){
				logger.info("调用搜索用户接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String token = StringUtil.getStringFromJson(paramsJSON, "TOKEN");
			String obj_user_id = StringUtil.getStringFromJson(paramsJSON, "USER_OBJ_ID");
			String reviewer = StringUtil.getStringFromJson(paramsJSON, "REVIEWER");
			String dynamic_id = StringUtil.getStringFromJson(paramsJSON, "DYNAMIC_ID");
			String comment = StringUtil.getStringFromJson(paramsJSON, "COMMENT");
			String reply_comment_id = StringUtil.getStringFromJson(paramsJSON, "REPLY_COMMENT_ID");
			
			if(StringUtil.verificationIsNull(token)){
				return ResMessageUtil.responseMessage("UserTokenIsNull");
			}
			if(StringUtil.verificationIsNull(obj_user_id)||StringUtil.verificationIsNull(comment)){
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
			if(userInfo == null){
				return ResMessageUtil.responseMessage("UserTokenIsNull"); 
			}
			T_COMMENT t_comment = new T_COMMENT();
			t_comment.setDYNAMIC_ID(dynamic_id);
			t_comment.setUSER_OBJ_ID(obj_user_id);
			t_comment.setREVIEWER(reviewer);
			t_comment.setCONTENT(comment);
			t_comment.setREPLY_COMMENT_ID(reply_comment_id);
			t_comment.setCREATE_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
			commentManageDao.addComment(t_comment);
			if(reply_comment_id != null && reply_comment_id != ""){
				String hql = "UPDATE T_DYNAMIC SET COMMENT_NUM = COMMENT_NUM + 1 WHERE DYNAMIC_ID = '"+dynamic_id+"'";
				dynamicManageDao.updateDynamicByCondition(hql);
			}
			json.putAll(ResMessageUtil.responseMessageWithMap("succes"));
		} catch (Exception e) {
			json.putAll(ResMessageUtil.responseMessageWithMap("systemError"));
			logger.error("DynamicController-->addComment:"+e);
		}
		return json.toString();
	}
	public static void main(String[] args) throws Exception {
		T_DYNAMIC dm = new T_DYNAMIC();
		dm.setUSER_OBJ_ID("11");
		dm.setCONTENT("1");
		dm.setIMAGE_URL("2");
		dm.setTRANSPOND_NUM(0);
		dm.setPRAISE_NUM(0);
		dm.setCOMMENT_NUM(0);
		dm.setTRAN_OBJ_ID("3");
		dm.setREASON("aa");
		dm.setCREATE_TIME(Timestamp.valueOf(DateUtils.df.format(new Date())));
		dynamicManageDao.addDynamic(dm);
	}
}
