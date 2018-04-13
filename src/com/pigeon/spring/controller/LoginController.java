package com.pigeon.spring.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pigeon.common.util.PhoneUtil;
//import com.pigeon.common.util.RSAUtil;
import com.pigeon.common.util.ResMessageUtil;
import com.pigeon.common.util.StringUtil;
import com.pigeon.common.util.json.BeanUtils;
import com.pigeon.common.util.json.JsonDateValueProcessor;
import com.pigeon.spring.entity.T_USER_INFO;
import com.pigeon.spring.service.UserAccess;
import com.pigeon.spring.service.UserLoginService;
import com.pigeon.spring.service.UserManageDao;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserManageDao userManageDao;
	
	@Autowired
	private UserLoginService loginService;
	
	/** 日志实例 */
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	/**
	 * 用户登陆
	 * @param LOGIN_TYPE String
	 * @param USER_PHONE String
	 * @param USER_PWD String
	 * @return String
	 */
	@UserAccess
	@RequestMapping(value="/loginin",produces = "application/json;charset=UTF-8")
	public @ResponseBody String login(@RequestParam("params") String params){
		logger.info("开始调用用户登陆接口,LOGIN_TYPE:" + params);
		String result = null;
		Map<String, Object> loginMap = new HashMap<String, Object>();
		try{
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String LOGIN_TYPE = paramsJSON.getString("LOGIN_TYPE");
			String USER_PHONE = paramsJSON.getString("USER_PHONE");
			String USER_PWD = paramsJSON.getString("USER_PWD");
			String LOGIN_NW = paramsJSON.getString("LOGIN_NW");
			String LOGIN_MAC = paramsJSON.getString("LOGIN_MAC");
			String APP_TYPE = paramsJSON.getString("APP_TYPE");
			if(StringUtil.verificationIsNull(LOGIN_TYPE)) {
				logger.info("调用用户登陆接口，参数LOGIN_TYPE不能为NULL！");
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(StringUtil.verificationIsNull(USER_PHONE)) {
				logger.info("调用用户登陆接口，参数USER_PHONE不能为NULL！");
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(StringUtil.verificationIsNull(USER_PWD)) {
				logger.info("调用用户登陆接口，参数USER_PWD不能为NULL！");
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(StringUtil.verificationIsNull(LOGIN_MAC)) {
				logger.info("调用用户登陆接口，参数LOGIN_MAC不能为NULL！");
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(StringUtil.verificationIsNull(APP_TYPE)) {
				logger.info("调用用户登陆接口，参数LOGIN_MAC不能为NULL！");
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(!PhoneUtil.isMobile(USER_PHONE)) {
				logger.info("调用用户登陆接口，手机号格式错误！");
				return ResMessageUtil.responseMessage("PhoneFormatError");
			}
			T_USER_INFO t_user_info = userManageDao.getUserInfoByPhone(USER_PHONE);
			if(t_user_info == null){
				logger.info("调用用户登陆接口，当前登录用户未注册！");
				return ResMessageUtil.responseMessage("UserIsNull");
			}
			
//			String params_pwd = RSAUtil.decrypt(USER_PWD) == null ? "" : RSAUtil.decrypt(USER_PWD);
//			String user_pwd = RSAUtil.decrypt(t_user_info.getUSER_PWD()) == null ? "" : RSAUtil.decrypt(t_user_info.getUSER_PWD());
			String params_pwd = USER_PWD == null ? "" : USER_PWD;
			String user_pwd = t_user_info.getUSER_PWD() == null ? "" : t_user_info.getUSER_PWD();
			
			logger.info("登录接口参数params_pwd解密：" + params_pwd);
			logger.info("登录原始密码params_pwd解密：" + user_pwd);
			if(!params_pwd.equals(user_pwd)) {
				logger.info("调用用户登陆接口，用户登录密码错误！");
				return ResMessageUtil.responseMessage("UserPwdIsError");
			}
			//修改用户登录设备
			t_user_info.setAPP_TYPE(APP_TYPE);
			userManageDao.updateUserInfo(t_user_info);
			String token = UserLoginService.tokenMap.get(USER_PHONE);
			if(token != null && token.length() > 0) {//判断是否已登录
				logger.info("调用用户登陆接口，用户当前已登录，重新登录！");
				String token_new = loginService.userLogin(t_user_info);
				UserLoginService.loginUserMap.remove(token);//删除原登录TOKEN
				loginMap.put("TOKEN", token_new);
				loginMap.putAll(ResMessageUtil.responseMessageWithMap("succes"));
			} else {
				if("1".equals(LOGIN_TYPE)) {//当前用户未登录，密码登录
					logger.info("调用用户登陆接口，当前用户未登录，用户使用密码登录");
					token = loginService.userLogin(t_user_info);
					loginMap.put("TOKEN", token);
					loginMap.putAll(ResMessageUtil.responseMessageWithMap("succes"));
				} else {//当前用户未登录，短信验证登陆
					logger.info("调用用户登陆接口，用户当前未登录，用户使用短信验证登录");
					loginMap.putAll(ResMessageUtil.responseMessageWithMap("SMSIsError"));
				}
			}
			//保存用户登录日志
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			T_LOGIN_LOG t_login_log = new T_LOGIN_LOG();
//			t_login_log.setUSERS_OBJ_ID(t_user_info.getOBJ_ID());
//			t_login_log.setLOGIN_TIME(df.format(new Date()));
//			t_login_log.setLOGIN_MODE(LOGIN_TYPE);
//			t_login_log.setLOGIN_NETWORK(LOGIN_NW);
//			t_login_log.setLOGIN_MARK(LOGIN_MAC);
//			userManageDao.addUserLoginLog(t_login_log);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map = BeanUtils.beanToMap(t_user_info);
			loginMap.putAll(map);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
			JSONObject jsonObj = JSONObject.fromObject(loginMap, jsonConfig);
			logger.info("用户登录成功！" + jsonObj.toString());
			result = jsonObj.toString();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		return result;
	}
	
	/*if(!StringUtil.verificationIsNull(LOGIN_TYPE) && !StringUtil.verificationIsNull(USER_PHONE) && 
	!StringUtil.verificationIsNull(USER_PWD) && !StringUtil.verificationIsNull(LOGIN_MAC)) {
if(!PhoneUtil.isMobile(USER_PHONE)) {
	logger.info("手机号格式错误！" + ResMessageUtil.responseMessage(ResMessageUtil.PhoneFormatError));
	return ResMessageUtil.responseMessage(ResMessageUtil.PhoneFormatError);
}
//T_USER_INFO t_user_info = userManageDao.getUserInfoByPhone(USER_PHONE);
if(t_user_info != null) {
	String token = UserLoginService.tokenMap.get(USER_PHONE);
	if(token != null && token.length() > 0) {//判断是否已登录
		logger.info("调用用户登陆接口，用户当前已登录");
		loginMap.put("TOKEN", token);
		loginMap.putAll(ResMessageUtil.responseMessageWithMap(ResMessageUtil.succes));
	} else {//用户未登录，重新进行登陆
		if("1".equals(LOGIN_TYPE)) {//密码登录
			logger.info("调用用户登陆接口，用户当前未登录，用户使用密码登录");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			token = loginService.userLogin(t_user_info);
			loginMap.put("TOKEN", token);
			T_LOGIN_LOG t_login_log = new T_LOGIN_LOG();
			t_login_log.setUSERS_OBJ_ID(t_user_info.getOBJ_ID());
			t_login_log.setLOGIN_TIME(df.format(new Date()));
			t_login_log.setLOGIN_MODE(LOGIN_TYPE);
			t_login_log.setLOGIN_NETWORK(LOGIN_NW);
			t_login_log.setLOGIN_MARK(LOGIN_MAC);
			userManageDao.addUserLoginLog(t_login_log);
			loginMap.putAll(ResMessageUtil.responseMessageWithMap(ResMessageUtil.succes));
		} else {//短信验证登陆
			logger.info("调用用户登陆接口，用户当前未登录，用户使用短信验证登录");
			loginMap.putAll(ResMessageUtil.responseMessageWithMap(ResMessageUtil.SMSIsError));
		}
	}
	Map<String, Object> map = new HashMap<String, Object>();
	map = BeanUtils.beanToMap(t_user_info);
	loginMap.putAll(map);
} else {
	loginMap.putAll(ResMessageUtil.responseMessageWithMap(ResMessageUtil.UserIsNull));
}
} else {
loginMap.putAll(ResMessageUtil.responseMessageWithMap("paramIsNullError"));
}*/
	
	/**
	 * 退出登录
	 * @param params String
	 * @return String
	 */
	@RequestMapping(value="/exitLogin",produces = "application/json;charset=UTF-8")
	public @ResponseBody String exitLogin(@RequestParam("params") String params) {
		logger.info("开始调用退出登陆接口,LOGIN_TYPE:" + params);
		if(params == null || params.length() <= 0) {
			logger.info("params参数不能为NULL:" + params);
			return ResMessageUtil.responseMessage("paramIsNullError");
		}
		JSONObject json = JSONObject.fromObject(params);
		String userPhone = json.getString("USER_PHONE");
		String token = json.getString("TOKEN");
		if(StringUtil.verificationIsNull(token)) {
			logger.info("TOKEN不能为NULL:" + token);
			return ResMessageUtil.responseMessage("UserTokenIsNull");
		}
		if(StringUtil.verificationIsNull(userPhone)) {
			logger.info("参数USER_PHONE不能为NULL:" + token);
			return ResMessageUtil.responseMessage("paramIsNullError");
		}
		UserLoginService.tokenMap.remove(userPhone);
		UserLoginService.loginUserMap.remove(token);
		return ResMessageUtil.responseMessage("succes");
	}
	
}
