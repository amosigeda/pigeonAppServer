package com.pigeon.spring.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_MESSAGE_CONTENT;
import com.pigeon.spring.entity.T_MESSAGE_LIST;
import com.pigeon.spring.service.MessageManageDao;

/**
 * 私信管理接口实现类
 * @author liufeng
 * @date 2017-4-18
 */
@Repository("messageManageDao")
public class MessageManageDaoImpl extends SuperDao implements MessageManageDao {

	/**
	 * 查询私信列表
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public T_MESSAGE_LIST queryMessageList(String userId, String friendId)
			throws Exception {
		Query query = getSession().createQuery("FROM T_MESSAGE_LIST l WHERE (l.USER_OBJ_ID = '"+userId+"' AND l.PRIVATE_USER_ID = '"+friendId+"') OR (l.PRIVATE_USER_ID = '"+friendId+"' AND l.USER_OBJ_ID = '"+userId+"') ");
		List<T_MESSAGE_LIST> list = query.list();
		T_MESSAGE_LIST message = null;
		if(list != null && list.size() > 0){
			message = list.get(0);
		}
		getSession().flush();
		return message;
	}

	/**
	 * 查询私信列表
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<T_MESSAGE_LIST> queryMessageList(String userId)
			throws Exception {
		Query query = getSession().createQuery("FROM T_MESSAGE_LIST l WHERE l.USER_OBJ_ID = '"+userId+"' OR l.PRIVATE_USER_ID = '"+userId+"'");
		List<T_MESSAGE_LIST> list = query.list();
		getSession().flush();
		return list;
	}

	/**
	 * 新增私信列表
	 */
	@Transactional("transactionManager")
	public String addMessageList(T_MESSAGE_LIST list) throws Exception {
		getSession().save(list);
		getSession().flush();
		return list.getOBJ_ID();
	}

	/**
	 * 发送私信
	 */
	@Transactional("transactionManager")
	public void addMessageContent(T_MESSAGE_CONTENT content) throws Exception {
		getSession().save(content);
		getSession().flush();
	}

	/**
	 * 获取私信用户列表
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<Map<String, Object>> queryMessageByObjId(String userId)
			throws Exception {
		String sql = "SELECT l.*,u.USER_CODE,i.USER_CODE PRIVATE_USER_CODE FROM T_MESSAGE_LIST l LEFT JOIN T_USER_INFO u ON l.USER_OBJ_ID = u.OBJ_ID LEFT JOIN T_USER_INFO i ON l.PRIVATE_USER_ID = i.OBJ_ID WHERE l.USER_OBJ_ID = '"+userId+"' OR l.PRIVATE_USER_ID = '"+userId+"'";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		getSession().flush();
		return list;
	}

	/**
	 * 获取私信内容
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<Map<String, Object>> queryMessageContent(String messageObjId)
			throws Exception {
		String sql = "SELECT m.*,u.USER_CODE MESSAGE_USER_CODE,i.USER_CODE SEND_USER_CODE FROM T_MESSAGE_CONTENT m LEFT JOIN T_USER_INFO u ON m.MESSAGE_OBJ_ID = u.OBJ_ID LEFT JOIN T_USER_INFO i ON m.SEND_USER_ID = i.OBJ_ID WHERE MESSAGE_OBJ_ID = '"+messageObjId+"' ORDER BY m.CREATE_TIME ASC";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
//		Query query = getSession().createQuery("FROM T_MESSAGE_CONTENT WHERE MESSAGE_OBJ_ID = '"+messageObjId+"' ORDER BY CREATE_TIME ASC");
//		List<T_MESSAGE_CONTENT> list = query.list();
		getSession().flush();
		return list;
	}
	
}
