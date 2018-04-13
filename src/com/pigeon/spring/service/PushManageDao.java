package com.pigeon.spring.service;

import java.util.List;

import com.pigeon.spring.entity.T_PUSH;

/**
 * 推送接口
 * @author liufeng
 * @date 2017-4-20
 */
public interface PushManageDao {

	/**
	 * 新增
	 */
	void addPush(T_PUSH t_push) throws Exception;
	
	/**
	 * 修改
	 */
	void updatePush(T_PUSH t_push) throws Exception;
	
	/**
	 * 查询未推送消息
	 */
	List<T_PUSH> queryUnPush(String receiveUserId) throws Exception;
	
}
