package com.pigeon.spring.service;

import java.util.List;

import com.pigeon.spring.entity.T_AGREE;

/**
 * 点赞
 * @author liufeng
 * @date 2017-4-13
 */
public interface AgreeManageDao {

	/**
	 * 查询我点过的赞
	 */
	List<Integer> queryMyAgree(String userId) throws Exception;
	
	/**
	 * 根据用户id查询点赞数据
	 * @param user_obj_id
	 * @param dynamic_id
	 * @return
	 */
	T_AGREE queryAgreeByUserId(String user_obj_id,String dynamic_id) throws Exception;
	
	/**
	 * 删除用户点赞
	 * @param T_AGREE
	 * @throws Exception
	 */
	void deleteAgreeById(T_AGREE t_agree) throws Exception;
	
	/**
	 * 新增点赞
	 * @param t_agree
	 * @throws Exception
	 */
	void addAgree(T_AGREE t_agree) throws Exception;
	
	/**
	 * 点赞
	 */
	void addAgreeAndUpdateDynamic(T_AGREE t_agree,String hql) throws Exception;
}
