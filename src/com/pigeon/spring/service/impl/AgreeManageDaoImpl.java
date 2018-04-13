package com.pigeon.spring.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_AGREE;
import com.pigeon.spring.service.AgreeManageDao;
import com.pigeon.spring.service.DynamicManageDao;

/**
 * 点赞接口实现类
 * @author liufeng
 * @date 2017-4-13
 */
@Repository("agreeManageDao")
public class AgreeManageDaoImpl extends SuperDao implements AgreeManageDao {

	@Autowired
	private DynamicManageDao dynamicManageDao;
	
	/**
	 * 查询我点过的赞
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<Integer> queryMyAgree(String userId) throws Exception {
		Query query = getSession().createQuery("SELECT a.DYNAMIC_ID FROM T_AGREE a WHERE a.USER_OBJ_ID = '"+userId+"'");
		List<Integer> list = query.list();
		return list;
	}
	
	/**
	 * 根据用户id查询点赞数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public T_AGREE queryAgreeByUserId(String user_obj_id, String dynamic_id)
			throws Exception {
		Query query = getSession().createQuery("FROM T_AGREE a WHERE a.USER_OBJ_ID = '"+user_obj_id+"' AND a.DYNAMIC_ID = '"+dynamic_id+"'");
		List<T_AGREE> list = query.list();
		getSession().flush();
		T_AGREE agree = null;
		if(list != null && list.size() > 0){
			agree = list.get(0);
		}
		getSession().flush();
		return agree;
	}

	/**
	 * 删除用户点赞
	 */
	@Transactional("transactionManager")
	public void deleteAgreeById(T_AGREE t_agree) throws Exception {
		getSession().delete(t_agree);
		getSession().flush();
	}

	/**
	 * 新增点赞
	 */
	@Transactional("transactionManager")
	public void addAgree(T_AGREE t_agree) throws Exception {
		getSession().save(t_agree);
		getSession().flush();
	}

	/**
	 * 点赞功能
	 */
	@Transactional("transactionManager")
	public void addAgreeAndUpdateDynamic(T_AGREE t_agree,String hql) throws Exception {
		addAgree(t_agree);
		dynamicManageDao.updateDynamicByCondition(hql);
	}
	
}
