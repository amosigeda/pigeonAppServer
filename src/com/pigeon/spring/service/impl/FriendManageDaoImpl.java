package com.pigeon.spring.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_ADD_FRIEND_RECORD;
import com.pigeon.spring.entity.T_FRIENDS_LIST;
import com.pigeon.spring.entity.T_RING_INFO;
import com.pigeon.spring.entity.T_USER_INFO;
import com.pigeon.spring.service.FriendManageDao;

/**
 * 好友管理实现类
 * @author liufeng
 * @date 2017-4-17
 */
@Repository("friendManageDao")
public class FriendManageDaoImpl extends SuperDao implements FriendManageDao {

	/**
	 * 根据用户编号搜索好友
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<T_USER_INFO> getUserInfoByUserCode(String userCode) throws Exception {
		Query query = getSession().createQuery("FROM T_USER_INFO u where u.USER_CODE  LIKE '%"+userCode+"%'");
		List<T_USER_INFO> list = query.list();
		getSession().flush();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public T_USER_INFO queryUserInfoByUserCode(String userCode)
			throws Exception {
		Query query = getSession().createQuery("FROM T_USER_INFO u where u.USER_CODE  LIKE '%"+userCode+"%'");
		List<T_USER_INFO> list = query.list();
		T_USER_INFO userInfo = null;
		if(list != null && list.size() > 0){
			userInfo = list.get(0);
		}
		return userInfo;
	}
	
	/**
	 * 根据用户主键查询鸽环信息
	 * @param user_obj_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<T_RING_INFO> getRingsWithList(String user_obj_id, Class clz) throws Exception {
		SQLQuery query = getSession().createSQLQuery("SELECT I.* FROM T_USER_RING_REF R LEFT JOIN T_RING_INFO I ON I.OBJ_ID = R.PIGEON_OBJ_ID WHERE R.USER_OBJ_ID = '"+user_obj_id+"'").addEntity(clz);
		List<T_RING_INFO> list = query.list();
		getSession().flush();
		return list;
	}
	
	/**
	 * 根据用户主键查询鸽环及绑定的信鸽足环编号信息
	 * @param user_obj_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRingAndPigeonCodeWithList(String user_obj_id) throws Exception {
		String sql = "SELECT I.OBJ_ID, I.RING_CODE, I.SPECIFICATION, I.START_DATE, I.RING_STATUS, C.START_STATUS, C.SIM_STATUS, C.POSITION_MODE, C.REPORTED_FREQ, C.SCHEDULE_POWER, C.POWER_WARN, P.FOOT_RING_CODE, C.SF_VERSION FROM (((T_RING_INFO I LEFT JOIN T_RING_CONFIG C ON I.RING_CODE = C.RING_CODE) LEFT JOIN T_PIGEON_RING_REF F ON I.OBJ_ID = F.RING_OBJ_ID) LEFT JOIN T_PIGEON_INFO P ON F.PIGEON_OBJ_ID = P.OBJ_ID) LEFT JOIN T_USER_RING_REF R ON I.OBJ_ID = R.PIGEON_OBJ_ID WHERE R.USER_OBJ_ID = '"+user_obj_id+"'";
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		getSession().flush();
		return list;
	}
	

	/**
	 * 查询是否是好友
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public T_FRIENDS_LIST queryFriendRelation(String userId, String friendId)
			throws Exception {
		Query query = getSession().createQuery("FROM T_FRIENDS_LIST f WHERE (f.USER_OBJ_ID = '"+userId+"' AND f.FRIENDS_OBJ_ID = '"+friendId+"') OR (f.USER_OBJ_ID = '"+friendId+"' AND f.FRIENDS_OBJ_ID = '"+userId+"')");
		List<T_FRIENDS_LIST> list = query.list();
		T_FRIENDS_LIST friend = null;
		if(list != null && list.size() > 0){
			friend = list.get(0);
		}
		getSession().flush();
		return friend;
	}

	/**
	 * 添加好友请求
	 */
	@Transactional("transactionManager")
	public void addFriendRequest(T_ADD_FRIEND_RECORD record) throws Exception {
		getSession().save(record);
		getSession().flush();
	}

	/**
	 * 查询好友申请记录
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public T_ADD_FRIEND_RECORD queryRecordByObjId(String objId) throws Exception {
		Query query = getSession().createQuery("FROM T_ADD_FRIEND_RECORD r WHERE r.OBJ_ID = '"+objId+"' AND r.ADD_STATUS = 0");
		List<T_ADD_FRIEND_RECORD> list = query.list();
		T_ADD_FRIEND_RECORD record = null;
		if(list != null && list.size() > 0){
			record = list.get(0);
		}
		getSession().flush();
		return record;
	}

	/**
	 * 加好友
	 */
	@Transactional("transactionManager")
	public void addFriendRelation(T_FRIENDS_LIST friend) throws Exception {
		getSession().save(friend);
		getSession().flush();
	}

	/**
	 * 获取用户添加好友请求
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<Map<String, Object>> queryAddFriendList(String userId)
			throws Exception {
		String sql = "SELECT r.*,u.USER_CODE,i.USER_CODE FRIEND_USER_CODE FROM T_ADD_FRIEND_RECORD r LEFT JOIN T_USER_INFO u ON r.USER_OBJ_ID = u.OBJ_ID LEFT JOIN T_USER_INFO i ON r.FRIEND_OBJ_ID = i.OBJ_ID WHERE r.USER_OBJ_ID = '"+userId+"' OR r.FRIEND_OBJ_ID = '"+userId+"' ORDER BY r.ADD_TIME DESC";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		getSession().flush();
		return list;
	}

	/**
	 * 根据userId和friendId查询是否有未处理好友添加请求记录
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public T_ADD_FRIEND_RECORD queryFriendReq(String userId, String friendId)
			throws Exception {
		Query query = getSession().createQuery("FROM T_ADD_FRIEND_RECORD r WHERE r.USER_OBJ_ID = '"+userId+"' AND r.FRIEND_OBJ_ID = '"+friendId+"' AND r.ADD_STATUS = 0 ORDER BY r.ADD_TIME DESC");
		List<T_ADD_FRIEND_RECORD> list = query.list();
		T_ADD_FRIEND_RECORD friendRecord = null;
		if(list != null && list.size() > 0){
			friendRecord = list.get(0);
		}
		getSession().flush();
		return friendRecord;
	}

	/**
	 * 修改添加好友时间
	 */
	@Transactional("transactionManager")
	public void updateFriendRecord(T_ADD_FRIEND_RECORD record) throws Exception {
		getSession().update(record);
		getSession().flush();
	}

}
