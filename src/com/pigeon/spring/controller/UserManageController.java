package com.pigeon.spring.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pigeon.common.util.EmailHelper;
import com.pigeon.common.util.MD5Util;
import com.pigeon.common.util.PhoneUtil;
import com.pigeon.common.util.ResMessageUtil;
import com.pigeon.common.util.SendMailHelper;
import com.pigeon.common.util.StringUtil;
import com.pigeon.common.util.UserCodeUtil;
import com.pigeon.spring.entity.T_LOGIN_LOG;
import com.pigeon.spring.entity.T_USER_INFO;
import com.pigeon.spring.service.UserLoginService;
import com.pigeon.spring.service.UserManageDao;

/**
 * 2016-10-17 17:01:01
 * @author fengmb
 * 
 */
@Component
@Controller
@RequestMapping("/service")
public class UserManageController {
	
	private static final String emailTile = "詹森019,APP密码找回,该邮件为系统自动发送,请勿回复";
	
	@Autowired
	private UserLoginService loginService;
	
	@Autowired
	private UserManageDao userManageDao;
	
	/** 日志实例 */
	private static final Logger logger = Logger.getLogger(UserManageController.class);
	
	@Rollback(false)
	@RequestMapping(value = "/userUpdateInfo", produces = "application/json;charset=UTF-8")
	public @ResponseBody String UserUpdateInfo(@RequestParam("params") String params) {
		logger.info("开始更新用户信息 : " + params);
		String result = null;
		try {
			JSONObject paramsJSON = JSONObject.fromObject(params);
			if (!paramsJSON.has("TOKEN")) {
				result = ResMessageUtil.responseMessage("paramTypeError");
			} else {
				String token = paramsJSON.getString("TOKEN");
				T_USER_INFO userInfo = UserLoginService.loginUserMap.get(token);
				if (null == userInfo) {
					result = ResMessageUtil.responseMessage("UserIsNull");
				} else {
					if (paramsJSON.has("DOVECOTE_NAME")) {
						String dovecoteName = paramsJSON.getString("DOVECOTE_NAME");
						userInfo.setDOVECOTE_NAME(dovecoteName);
					}
					if (paramsJSON.has("CLUB")) {
						String club = paramsJSON.getString("CLUB");
						userInfo.setCLUB(club);
					}
					if (paramsJSON.has("PIGENO_UNION")) {
						String pigenoUnion = paramsJSON.getString("PIGENO_UNION");
						userInfo.setPIGENO_UNION(pigenoUnion);
					}
					if (paramsJSON.has("USER_AGE")) {
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dateStr = paramsJSON.getString("USER_AGE");
						Date userAge = df.parse(dateStr);
						userInfo.setUSER_AGE(userAge);
					}
					if (paramsJSON.has("USER_SEX")) {
						String sexStr = paramsJSON.getString("USER_SEX");
						if ("1".equals(sexStr) || "2".equals(sexStr)) {
							userInfo.setUSER_SEX(sexStr);
						} else {
							userInfo.setUSER_SEX("3");
						}
					}
					if (paramsJSON.has("FEED_PIGEON_YEAR")) {
						String years = paramsJSON.getString("FEED_PIGEON_YEAR");
						userInfo.setFEED_PIGEON_YEAR(Integer.valueOf(years));
					}
					userManageDao.updateUserInfo(userInfo);
					result = ResMessageUtil.responseMessage("succes");
				}
			}
			
		} catch (JSONException e){
			result = ResMessageUtil.responseMessage("paramTypeError");
			e.printStackTrace();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 用户注册
	 * @param userInfo String JSON格式的用户信息
	 * @return String 
	 */
	@Rollback(false)
	@RequestMapping(value = "/userRegedit", produces = "application/json;charset=UTF-8")
	public @ResponseBody String UserRegedit(@RequestParam("params") String params) {
		logger.info("收到注册用户请求!" + params);
		String result = null;
		try{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String LOGIN_NW = paramsJSON.getString("LOGIN_NW");
			String LOGIN_MAC = paramsJSON.getString("LOGIN_MAC");
			T_USER_INFO t_user_info = (T_USER_INFO) JSONObject.toBean(paramsJSON, T_USER_INFO.class);
			if(StringUtil.verificationIsNull(LOGIN_MAC) || t_user_info.getUSER_PHONE() == null || t_user_info.getUSER_PHONE().length() < 0 || t_user_info.getUSER_PWD() == null || t_user_info.getUSER_PWD().length() < 0) {
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(StringUtil.verificationIsNull(t_user_info.getUSER_EMAIL())) {
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(!PhoneUtil.isMobile(t_user_info.getUSER_PHONE())) {
				logger.info("手机号格式错误！" + ResMessageUtil.responseMessage("PhoneFormatError"));
				return ResMessageUtil.responseMessage("PhoneFormatError");
			}
			T_USER_INFO user = userManageDao.getUserInfoByPhone(t_user_info.getUSER_PHONE());
			if(user != null) {
				logger.info("手机号已注册！" + ResMessageUtil.responseMessage("UserExisted"));
				return ResMessageUtil.responseMessage("UserExisted");
			}
			String userCode = UserCodeUtil.generateUserCode();
			t_user_info.setUSER_CODE(Integer.parseInt(userCode));
			t_user_info.setENROLLMENT_TIME(Timestamp.valueOf(df.format(new Date())));
			userManageDao.addUserRegedit(t_user_info);
			String token = null;
			token = loginService.userLogin(t_user_info);//注册成功后，调用用户登录接口
			Map<String, Object> map = new HashMap<String, Object>();//
			map.put("TOKEN", token);
			map.put("USER_CODE", userCode);
			map.put("USER_OBJ_ID", t_user_info.getOBJ_ID());
			//保存登录日志
			T_LOGIN_LOG t_login_log = new T_LOGIN_LOG();
			t_login_log.setUSERS_OBJ_ID(t_user_info.getOBJ_ID());
			t_login_log.setLOGIN_TIME(df.format(new Date()));
			t_login_log.setLOGIN_MODE("1");
			t_login_log.setLOGIN_NETWORK(LOGIN_NW);
			t_login_log.setLOGIN_MARK(LOGIN_MAC);
			userManageDao.addUserLoginLog(t_login_log);
			map.putAll(ResMessageUtil.responseMessageWithMap("succes"));
			JSONObject jsonObject = JSONObject.fromObject(map);
			result = jsonObject.toString();
			logger.info("用户注册成功，返回JSON：" + result);
		} catch (JSONException e){
			result = ResMessageUtil.responseMessage("paramTypeError");
			e.printStackTrace();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据邮箱找回密码
	 * @param params String
	 * @return String
	 */
	@RequestMapping(value = "/sendFindPwdMail", produces = "application/json;charset=UTF-8")
	public @ResponseBody String sendFindPwdMail(HttpServletRequest request, @RequestParam("params") String params) {
		logger.info("调用发送找回密码邮件接口：" + params);
		String result = null;
		try {
			if(StringUtil.verificationIsNull(params)) {
				logger.info("调用根据邮箱找回密码接口失败，参数params不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			JSONObject paramsJSON = JSONObject.fromObject(params);
			String user_phone = paramsJSON.get("USER_PHONE") == null ? "":paramsJSON.get("USER_PHONE").toString();
			if(StringUtil.verificationIsNull(user_phone)) {
				logger.info("调用根据邮箱找回密码接口失败，参数USER_PHONE不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			String user_email = paramsJSON.get("USER_EMAIL") == null ? "":paramsJSON.get("USER_EMAIL").toString();
			if(StringUtil.verificationIsNull(user_email)) {
				logger.info("调用根据邮箱找回密码接口失败，参数USER_EMAIL不能为NULL!" + ResMessageUtil.responseMessage("paramIsNullError"));
				return ResMessageUtil.responseMessage("paramIsNullError");
			}
			if(!EmailHelper.checkEmailFormat(user_email)) {
				logger.info("调用根据邮箱找回密码接口失败，邮箱格式错误!" + ResMessageUtil.responseMessage("EmailFormatError"));
				return ResMessageUtil.responseMessage("EmailFormatError");
			}
			T_USER_INFO t_user_info = userManageDao.getUserInfoByPhone(user_phone);
			if(t_user_info == null) {
				logger.info("调用根据邮箱找回密码接口失败，用户手机号不存在!" + ResMessageUtil.responseMessage("UserIsNull"));
				return ResMessageUtil.responseMessage("UserIsNull");
			}
			if(!user_email.equalsIgnoreCase(t_user_info.getUSER_EMAIL())) {
				logger.info("调用根据邮箱找回密码接口失败，找回密码邮箱与注册邮箱不匹配!" + ResMessageUtil.responseMessage("NotRegeditEmail"));
				return ResMessageUtil.responseMessage("NotRegeditEmail");
			}
			String emailContent = createEmailFindPwdLink(t_user_info, request);
			boolean falg = new SendMailHelper().sendHtmlEmail(emailTile, emailContent, user_email);
			//返回APP接口调用状态
			if(falg) {
				result = ResMessageUtil.responseMessage("succes");
				logger.info("调用根据邮箱找回密码接口成功，返回JSON：" + result);
			} else {
				result = ResMessageUtil.responseMessage("sendMailError");
				logger.info("调用根据邮箱找回密码接口失败，返回JSON：" + result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		return result;
	}
	
	private String createEmailFindPwdLink(T_USER_INFO t_user_info, HttpServletRequest request) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//生成密钥    
	    String secretKey=UUID.randomUUID().toString();
	    //设置过期时间    
	    Date outDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000);// 10分钟后过期    
	    long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql 取出时间是忽略毫秒数的
	    t_user_info.setSecret_key(secretKey);
	    t_user_info.setExpired_time(Timestamp.valueOf(df.format(outDate)));
	    userManageDao.updateUserInfo(t_user_info);
	    //将用户主键、过期时间、密钥生成链接密钥    
	    String key = t_user_info.getOBJ_ID() + "$" + date + "$" + secretKey; 
	    String digitalSignature = MD5Util.MD5(key);
	    String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String resetPassHref = basePath + "checkLink?sid=" + digitalSignature +"&key=" + t_user_info.getOBJ_ID() + "&email=" + t_user_info.getUSER_EMAIL();
        StringBuffer emailContent = new StringBuffer("<table style='margin: 25px auto;' border='0' cellspacing='0' cellpadding='0' width='648' align='center'>");
        emailContent.append("<tbody>");
        emailContent.append("<tr><td style='color:#40AA53;'><h1 style='margin-bottom:10px;'>詹森019</h1></td></tr>");
        emailContent.append("<tr><td style='border-left: 1px solid #D1FFD1; padding: 20px 20px 0px; background: none repeat scroll 0% 0% #ffffff; border-top: 5px solid #40AA53; border-right: 1px solid #D1FFD1;'><p>你好</p></td></tr>");
        emailContent.append("<tr><td style='border-left: 1px solid #D1FFD1; padding: 10px 20px; background: none repeat scroll 0% 0% #ffffff; border-right: 1px solid #D1FFD1;'>");
        emailContent.append("<p style='font-weight:bold'>请点击下面链接进行密码重置<br/><br/><a href='");
        emailContent.append(resetPassHref);
        emailContent.append("'>");
        emailContent.append(resetPassHref);
        emailContent.append("</a></p></td></tr>");
        emailContent.append("<tr><td style='border-bottom: 1px solid #D1FFD1; border-left: 1px solid #D1FF=D1; padding: 0px 20px 20px; background: none repeat scroll 0% 0% #ffffff; b=order-right: 1px solid #D1FFD1;'><hr style='color:#ccc;'/><p style='color:#060;font-size:9pt;'>想了解更多信息，请访问&nbsp;<a href='http://www.zhansen019.com' target='_blank'>http://www.zhansen019.com</a></p></td></tr>");
        emailContent.append("</tbody></table>");
	    return emailContent.toString();
	}
	
}
