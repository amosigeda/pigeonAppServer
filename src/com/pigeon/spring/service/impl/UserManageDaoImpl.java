package com.pigeon.spring.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_LOGIN_LOG;
import com.pigeon.spring.entity.T_USER_INFO;
import com.pigeon.spring.service.UserManageDao;

@Repository("userManageDao")
public class UserManageDaoImpl extends SuperDao implements UserManageDao {

	/**
	 * 用户注册
	 * @param t_user_info T_USER_INFO
	 */
	@Transactional("transactionManager")
	public void addUserRegedit(T_USER_INFO t_user_info) throws Exception {
		// TODO Auto-generated method stub
		getSession().save(t_user_info);
		getSession().flush();
		
	}
	
	/**
	 * 更新用户信息
	 * @param updatedUserInfo
	 * @throws Exception
	 */
	@Transactional("transactionManager")
	public void updateUserInfo(T_USER_INFO updatedUserInfo) throws Exception {
		getSession().update(updatedUserInfo);
		getSession().flush();
	}
	
	/**
	 * 根据手机号查询用户信息
	 * @param phone String
	 * @return T_USER_INFO
	 */
	@SuppressWarnings("unchecked")
	public T_USER_INFO getUserInfoByPhone(String phone) throws Exception {
		Query query = getSession().createQuery("FROM T_USER_INFO u where u.USER_PHONE = '"+phone+"'");
		List<T_USER_INFO> list = query.list();
		T_USER_INFO t_user_info = null;
		if(list != null && list.size() > 0) {
			t_user_info = list.get(0);
		}
		getSession().flush();
		return t_user_info;
	}
	
	/**
	 * 保存用户登录日志
	 * @param t_login_log T_LOGIN_LOG
	 */
	public void addUserLoginLog(T_LOGIN_LOG t_login_log) throws Exception {
		getSession().save(t_login_log);
		getSession().flush();
	}

	/**
	 * 根据用户主键信息查询到对应的用户信息
	 * @param userObjID String : User Object ID 用户主键
	 * @return T_USER_INFO     : 用户信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T_USER_INFO getUserInfoByObjID(String userObjID) throws Exception {
		Query query = getSession().createQuery("FROM T_USER_INFO u where u.OBJ_ID = '"+userObjID+"'");
		List<T_USER_INFO> list = query.list();
		T_USER_INFO t_user_info = null;
		if(list != null && list.size() > 0) {
			t_user_info = list.get(0);
		}
		getSession().flush();
		return t_user_info;
	
	}

}
