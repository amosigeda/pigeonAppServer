package com.pigeon.spring.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_COMMENT;
import com.pigeon.spring.service.CommentManageDao;

/**
 * 评论、点赞、转发实现类
 * @author liufeng
 * @date 2017-4-14
 */
@Repository("commentManageDao")
public class CommentManageDaoImpl extends SuperDao implements CommentManageDao {

	/**
	 * 新增评论
	 */
	@Transactional("transactionManager")
	public void addComment(T_COMMENT t_comment) {
		getSession().save(t_comment);
		getSession().flush();
	}

	/**
	 * 删除评论
	 */
	@Transactional("transactionManager")
	public void deleteComment(T_COMMENT t_comment) {
		
	}

	/**
	 * 查询评论
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<Map<String, Object>> queryCommentList(String dynamic_id) {
		String sql = "SELECT c.*,u.USER_CODE,i.USER_CODE RE_USER_CODE FROM T_COMMENT c LEFT JOIN T_USER_INFO u ON c.USER_OBJ_ID = u.OBJ_ID LEFT JOIN T_USER_INFO i ON c.REVIEWER = i.OBJ_ID WHERE c.DYNAMIC_ID = '"+dynamic_id+"' ORDER BY c.CREATE_TIME DESC";
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		getSession().flush();
		return list;
	}

}
