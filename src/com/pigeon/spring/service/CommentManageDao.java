package com.pigeon.spring.service;

import java.util.List;
import java.util.Map;

import com.pigeon.spring.entity.T_COMMENT;

/**
 * 转发、评论、点赞接口
 * @author liufeng
 * @date 2017-4-14
 */
public interface CommentManageDao {

	/**
	 * 新增评论
	 * @param t_comment
	 */
	void addComment(T_COMMENT t_comment);
	
	/**
	 * 删除评论
	 * @param t_comment
	 */
	void deleteComment(T_COMMENT t_comment);
	
	/**
	 * 查询评论内容
	 * @param dynamic_id
	 * @return
	 */
	List<Map<String, Object>> queryCommentList(String dynamic_id);
	
}
