package com.pigeon.spring.service;

import java.util.List;
import java.util.Map;

import com.pigeon.spring.entity.T_ADD_FRIEND_RECORD;
import com.pigeon.spring.entity.T_FRIENDS_LIST;
import com.pigeon.spring.entity.T_USER_INFO;

/**
 * 好友管理
 * @author liufeng
 * @date 2017-4-17
 */
public interface FriendManageDao {

	/**
	 * 根据userCode查询用户信息
	 */
	public List<T_USER_INFO> getUserInfoByUserCode(String userCode) throws Exception;
	
	public T_USER_INFO queryUserInfoByUserCode(String userCode) throws Exception;
	
	/**
	 * 查询是否是好友关系
	 */
	public T_FRIENDS_LIST queryFriendRelation(String userId,String friendId) throws Exception;
	
	/**
	 * 发送添加好友请求
	 */
	public void addFriendRequest(T_ADD_FRIEND_RECORD record) throws Exception;
	
	/**
	 * 查询好友申请记录
	 */
	public T_ADD_FRIEND_RECORD queryRecordByObjId(String objId) throws Exception;
	
	/**
	 * 添加好友
	 */
	public void addFriendRelation(T_FRIENDS_LIST friend) throws Exception;
	
	/**
	 * 获取添加好友请求列表
	 */
	List<Map<String, Object>> queryAddFriendList(String userId) throws Exception;
	
	/**
	 * 根据userId和friendId查询添加好友请求记录
	 */
	T_ADD_FRIEND_RECORD queryFriendReq(String userId,String friendId) throws Exception;
	
	/**
	 * 修改添加好友时间
	 */
	void updateFriendRecord(T_ADD_FRIEND_RECORD record) throws Exception;
}
