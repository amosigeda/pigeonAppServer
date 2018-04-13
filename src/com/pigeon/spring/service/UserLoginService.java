package com.pigeon.spring.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pigeon.common.util.MD5Util;
import com.pigeon.spring.entity.T_USER_INFO;

@Service
public class UserLoginService {
	
	/**
	 * 存放“手机号：token”键值对
	 */
	public static Map<String, String> tokenMap = new HashMap<String, String>();
	
	/**
	 * 存放“token:T_USER_INFO”键值对
	 */
	public static Map<String, T_USER_INFO> loginUserMap = new HashMap<String, T_USER_INFO>();
	
	/** 日志实例 */
	private static final Logger logger = Logger.getLogger(UserLoginService.class);
	
	/**
	 * 用户登陆
	 * @param name String
	 * @param pwd String
	 * @return String
	 */
	public String userLogin(T_USER_INFO t_user_info) {
		String phone = t_user_info.getUSER_PHONE();
		String pwd = t_user_info.getUSER_PWD();
		logger.info("User login phone:[" + phone + "]password:[" + pwd + "]");
		/*String token = tokenMap.get(phone);
		if(token == null) {
			t_user_info = new T_USER_INFO();
			t_user_info.setUSER_PHONE(phone);
			t_user_info.setUSER_PWD(pwd);
		} else {
			logger.info("新用户登陆!");
			t_user_info = loginUserMap.get(token);
			loginUserMap.remove(token);
		}*/
		String token = MD5Util.MD5(phone + pwd + new Date().getTime());
		logger.info("用户登陆Token：" + token);
		loginUserMap.put(token, t_user_info);
		tokenMap.put(phone, token);
		return token;
	}
}
