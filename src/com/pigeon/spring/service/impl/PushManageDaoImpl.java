package com.pigeon.spring.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_PUSH;
import com.pigeon.spring.service.PushManageDao;

/**
 * 推送接口实现类
 * @author liufeng
 * @date 2017-4-20
 */
@Repository("pushManageDao")
public class PushManageDaoImpl extends SuperDao implements PushManageDao {

	/**
	 * 新增
	 */
	@Transactional("transactionManager")
	public void addPush(T_PUSH t_push) throws Exception {
		getSession().save(t_push);
		getSession().flush();
	}

	/**
	 * 修改
	 */
	@Transactional("transactionManager")
	public void updatePush(T_PUSH t_push) throws Exception {
		getSession().update(t_push);
		getSession().flush();
	}

	/**
	 * 查询未推送消息
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<T_PUSH> queryUnPush(String receiveUserId) throws Exception {
		Query query = getSession().createQuery("FROM T_PUSH p WHERE p.RECEIVE_USER_ID = '"+receiveUserId+"' ORDER BY p.CREATE_TIME ASC ");
		List<T_PUSH> list = query.list();
		getSession().flush();
		return list;
	}

}
