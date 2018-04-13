package com.pigeon.spring.service;

import java.util.List;
import java.util.Map;

import com.pigeon.spring.entity.T_MESSAGE_CONTENT;
import com.pigeon.spring.entity.T_MESSAGE_LIST;

/**
 * 私信管理接口
 * @author liufeng
 * @date 2017-4-18
 */
public interface MessageManageDao {

	/**
	 * 查询是否存在私信列表
	 */
	T_MESSAGE_LIST queryMessageList(String userId,String friendId) throws Exception;
	
	/**
	 * 根据userId查询私信列表
	 */
	List<T_MESSAGE_LIST> queryMessageList(String userId) throws Exception;
	
	/**
	 * 新增私信列表
	 */
	String addMessageList(T_MESSAGE_LIST list) throws Exception;
	
	/**
	 * 发送私信
	 */
	void addMessageContent(T_MESSAGE_CONTENT content) throws Exception;
	
	/**
	 * 获取私信用户列表
	 */
	List<Map<String, Object>> queryMessageByObjId(String userId) throws Exception;
	
	/**
	 * 获取私信内容
	 */
	List<Map<String, Object>> queryMessageContent(String messageObjId) throws Exception;
	
}
