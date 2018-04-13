package com.pigeon.spring.service;

import java.util.List;
import java.util.Map;



import com.pigeon.spring.entity.T_DEVICE_CONFIMBIND;
import com.pigeon.spring.entity.T_DEVICE_LOCATION;
import com.pigeon.spring.entity.T_DEVICE_LOGIN;
import com.pigeon.spring.entity.T_DEVICE_UPMSG;
import com.pigeon.spring.entity.T_DEVICE_WARN;



/**
 * 设备
 * @author weitechao
 * @date 2017-08-21
 */
public interface DeviceManageDao {
	/*
	 * 新增登录信息
	 * */
	void addDeviceLogin(T_DEVICE_LOGIN t_device_login) throws Exception;
	
	/*
	 * 新增设备定位信息
	 * */
	void addDeviceLocation(T_DEVICE_LOCATION t_device_location) throws Exception;
	
	/*
	 * 新增当设备被强制关机、SIM卡状态、电量过低的时候，发送此通知
	 * */
	void addDeviceWarn(T_DEVICE_WARN t_device_warn) throws Exception;
	
	/*
	 * 当设备升级完成之后发送此消息
	 * */
	void addDeviceUpMsg(T_DEVICE_UPMSG t_device_upmsg) throws Exception;
	
	/*
	 * 添加设备确认绑定消息日志
	 * */
	void addDeviceConfirmBind(T_DEVICE_CONFIMBIND t_device_confimBind) throws Exception;

	/*
	 * 查询APP对设备的设置
	 * */
	List<Map<String, Object>> queryDeviceMyselfSet(String user_obj_id,int pageNum,int pageSize) throws Exception;
	/*
	 * 当设备获取到最新的APP设置的时候更新获取时间
	 * */
	void updateMyselefByCondition(final String hql) throws Exception;
}
