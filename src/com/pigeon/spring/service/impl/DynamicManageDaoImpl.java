package com.pigeon.spring.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_DYNAMIC;
import com.pigeon.spring.service.DynamicManageDao;

/**
 * 动态信息
 * @author liufeng
 * @date 2017-4-15
 */
@Repository("dynamicManageDao")
public class DynamicManageDaoImpl extends SuperDao implements DynamicManageDao {

	/**
	 * 添加动态
	 */
	@Transactional("transactionManager")
	public void addDynamic(T_DYNAMIC td) throws Exception {
		getSession().save(td);
		getSession().flush();
	}

	/**
	 * 新增动态，插入图片
	 */
	@Transactional("transactionManager")
	public void addDynamicAndImage(T_DYNAMIC td,List<String> list)
			throws Exception {
		if(list != null && list.size() > 0){
			if(list.size() == 1){
				td.setIMAGE_URL(list.get(0));
			}else if(list.size() == 2){
				td.setIMAGE_URL(list.get(0));
				td.setIMAGE_URL1(list.get(1));
			}else if(list.size() == 3){
				td.setIMAGE_URL(list.get(0));
				td.setIMAGE_URL1(list.get(1));
				td.setIMAGE_URL2(list.get(2));
			}
		}
		getSession().save(td);
		getSession().flush();
	}
	
	/**
	 * 分页查询所有动态信息
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<Map<String, Object>> queryAllDyanamic(int pageNum, int pageSize)
			throws Exception {
//		Query query = getSession().createQuery("FROM T_DYNAMIC d ORDER BY d.CREATE_TIME DESC");
//		query.setFirstResult(pageNum);
//		query.setMaxResults(pageSize);
//		List<T_DYNAMIC> list = query.list();
		String sql = "SELECT d.*,u.USER_CODE,a.AGREE_ID DOAGREE FROM T_DYNAMIC d LEFT JOIN T_USER_INFO u ON d.USER_OBJ_ID = u.OBJ_ID LEFT JOIN T_AGREE a ON d.DYNAMIC_ID = a.DYNAMIC_ID ORDER BY d.CREATE_TIME DESC";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setFirstResult(pageNum).setMaxResults(pageSize);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
//		List<Map<String, Object>> list = query.list();
		List<Map<String, Object>> list = query.list();
		getSession().flush();
		return list;
	}

	/**
	 * 查询好友鸽圈动态
	 * @param user_obj_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryFriendsDynamics(String user_obj_id,int pageNum,int pageSize) throws Exception {
		String sql = "SELECT d.*,u.USER_CODE,a.AGREE_ID DOAGREE FROM T_DYNAMIC d LEFT JOIN T_USER_INFO u ON d.USER_OBJ_ID = u.OBJ_ID LEFT JOIN T_AGREE a ON d.DYNAMIC_ID = a.DYNAMIC_ID WHERE d.USER_OBJ_ID IN ("+user_obj_id+") ORDER BY d.CREATE_TIME DESC";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setFirstResult(pageNum).setMaxResults(pageSize);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		getSession().flush();
		return list;
	}
	
	
	/**
	 * 查询我的鸽圈动态
	 * @param user_obj_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryMyMapDynamic(String user_obj_id,
			int pageNum, int pageSize) throws Exception {
		String sql = "SELECT d.*,u.USER_CODE,a.AGREE_ID DOAGREE FROM T_DYNAMIC d LEFT JOIN T_USER_INFO u ON d.USER_OBJ_ID = u.OBJ_ID LEFT JOIN T_AGREE a ON d.DYNAMIC_ID = a.DYNAMIC_ID AND d.USER_OBJ_ID = a.USER_OBJ_ID WHERE d.USER_OBJ_ID = '"+user_obj_id+"' ORDER BY d.CREATE_TIME DESC";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setFirstResult(pageNum).setMaxResults(pageSize);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}
	
	/**
	 * 查询指定用户的好友列表
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<String> queryFriends(String user_obj_id)
			throws Exception {
		Query query = getSession().createQuery("SELECT f.FRIENDS_OBJ_ID AS user_id FROM T_FRIENDS_LIST f WHERE f.USER_OBJ_ID = '"+user_obj_id+"'");
		List<String> list = query.list();
		Query query1 = getSession().createQuery("SELECT l.USER_OBJ_ID AS user_id FROM T_FRIENDS_LIST l WHERE l.FRIENDS_OBJ_ID = '"+user_obj_id+"'");
		List<String> list1 = query1.list();
		list.addAll(list1);
		list.add(user_obj_id);
		getSession().flush();
		return list;
	}

	/**
	 * 查询总记录数
	 */
	@SuppressWarnings("rawtypes")
	@Transactional("transactionManager")
	public Long queryTotalCount(String hql, Map<String, Object> values)
			throws Exception {
		Query query = getSession().createQuery(hql);
		if(values != null){
			Set keys = values.keySet();
			for(Object s:keys){
				query.setParameter((String)s, values.get(s));
			}
		}
		Long result = (Long) query.uniqueResult();
		getSession().flush();
		return result;
	}

	/**
	 * 修改转发、评论、点赞数
	 */
	@Override
	@Transactional("transactionManager")
	public void updateDynamicByCondition(String hql)
			throws Exception {
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
		getSession().flush();
	}

	/**
	 * 根据动态id查询动态信息
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public T_DYNAMIC queryDynamicById(String dynamic_id) throws Exception {
		Query query = getSession().createQuery("FROM T_DYNAMIC d WHERE d.DYNAMIC_ID = '"+dynamic_id+"'");
		List<T_DYNAMIC> list = query.list();
		T_DYNAMIC t_dynamic = null;
		if(list != null && list.size() > 0){
			t_dynamic = list.get(0);
		}
		getSession().flush();
		return t_dynamic;
	}

}
