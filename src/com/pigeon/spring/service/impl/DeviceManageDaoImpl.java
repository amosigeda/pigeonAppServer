package com.pigeon.spring.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_DEVICE_CONFIMBIND;
import com.pigeon.spring.entity.T_DEVICE_LOCATION;
import com.pigeon.spring.entity.T_DEVICE_LOGIN;
import com.pigeon.spring.entity.T_DEVICE_UPMSG;
import com.pigeon.spring.entity.T_DEVICE_WARN;
import com.pigeon.spring.service.DeviceManageDao;

@Repository("deviceManageDao")
public class DeviceManageDaoImpl extends SuperDao implements DeviceManageDao{

	@Override
	@Transactional("transactionManager")
	public void addDeviceLogin(T_DEVICE_LOGIN t_device_login) throws Exception {
		getSession().save(t_device_login);
		getSession().flush();
	}

	@Override
	@Transactional("transactionManager")
	public void addDeviceLocation(T_DEVICE_LOCATION t_device_location)
			throws Exception {
		getSession().save(t_device_location);
		getSession().flush();
	}

	@Override
	@Transactional("transactionManager")
	public void addDeviceWarn(T_DEVICE_WARN t_device_warn) throws Exception {
		getSession().save(t_device_warn);
		getSession().flush();
		
	}

	@Override
	@Transactional("transactionManager")
	public void addDeviceUpMsg(T_DEVICE_UPMSG t_device_upmsg) throws Exception {
		getSession().save(t_device_upmsg);
		getSession().flush();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional("transactionManager")
	public List<Map<String, Object>> queryDeviceMyselfSet(String user_obj_id,
			int pageNum, int pageSize) throws Exception {
		String sql = "SELECT ID,DEVICE_SERIAL_NUMBER,LOCATION_TYPE,POINT_FREQUENCY,TING_OFF_ON,FLY_MODEL,UPDATE_TIME FROM device_set WHERE DEVICE_SERIAL_NUMBER = '"+user_obj_id+"' ORDER BY ID DESC";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setFirstResult(pageNum).setMaxResults(pageSize);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@Override
	@Transactional("transactionManager")
	public void addDeviceConfirmBind(T_DEVICE_CONFIMBIND t_device_confimBind)
			throws Exception {
		getSession().save(t_device_confimBind);
		getSession().flush();
	}

	@Override
	@Transactional("transactionManager")
	public void updateMyselefByCondition(String hql) throws Exception {
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
		getSession().flush();
	}
}
