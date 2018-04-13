package com.pigeon.spring.service;

import com.pigeon.spring.entity.T_LOGIN_LOG;
import com.pigeon.spring.entity.T_USER_INFO;

public interface UserManageDao {
	
	/**
	 * 用户注册
	 * @param t_user_info T_USER_INFO
	 */
	public void addUserRegedit(T_USER_INFO t_user_info) throws Exception;
	
	/**
	 * 更新用户信息
	 * @param updatedUserInfo
	 * @throws Exception
	 */
	public void updateUserInfo(T_USER_INFO updatedUserInfo) throws Exception;
	
	/**
	 * 根据手机号查询用户信息
	 * @param phone String
	 * @return T_USER_INFO
	 */
	public T_USER_INFO getUserInfoByPhone(String phone) throws Exception;
	
	/**
	 * 根据用户主键信息查询到对应的用户信息
	 * @param userObjID String : User Object ID 用户主键
	 * @return T_USER_INFO     : 用户信息
	 * @throws Exception
	 */
	public T_USER_INFO getUserInfoByObjID(String userObjID) throws Exception;
	
	/**
	 * 保存用户登录日志
	 * @param t_login_log T_LOGIN_LOG
	 */
	public void addUserLoginLog(T_LOGIN_LOG t_login_log) throws Exception;

}
