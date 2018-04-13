package com.pigeon.spring.service;

import java.util.List;
import java.util.Map;

import com.pigeon.spring.entity.T_DYNAMIC;

public interface DynamicManageDao {

	/**
	 * 新增动态、转发
	 * @param td
	 */
	void addDynamic(T_DYNAMIC td) throws Exception;
	
	/**
	 * 新增动态，插入图片
	 */
	void addDynamicAndImage(T_DYNAMIC td,List<String> list) throws Exception;
	
	/**
	 * 分页查询所有动态信息
	 * @param pageStart
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryAllDyanamic(int pageStart,int pageSize) throws Exception;
	
	/**
	 * 查询好友鸽圈动态
	 * @param user_obj_id
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryFriendsDynamics(String user_obj_id,int pageNum,int pageSize) throws Exception;
	
	/**
	 * 查询我的鸽圈动态
	 * @param user_obj_id
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryMyMapDynamic(String user_obj_id,int pageNum,int pageSize) throws Exception;
	
	/**
	 * 查询指定用户的好友列表
	 * @param user_obj_id
	 * @return
	 * @throws Exception
	 */
	List<String> queryFriends(String user_obj_id) throws Exception;
	
	/**
	 * 查询总记录数
	 * @param hql
	 * @param values
	 * @return
	 * @throws Exception
	 */
	Long queryTotalCount(final String hql,final Map<String, Object> values) throws Exception;
	
	/**
	 * 修改转发、评论、点赞数
	 * @param hql
	 * @throws Exception
	 */
	void updateDynamicByCondition(final String hql) throws Exception;
	
	/**
	 * 根据动态id查询动态信息
	 * @param dynamic_id
	 * @return
	 * @throws Exception
	 */
	T_DYNAMIC queryDynamicById(String dynamic_id) throws Exception;
}
