package com.pigeon.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_LOGIN_LOG")
public class T_LOGIN_LOG {
	
	private String OBJ_ID;//主键',
	private String USERS_OBJ_ID;//用户基本信息表-主键
	private String LOGIN_TIME;//登陆时间
	private String LOGIN_MODE;//登陆模式
	private String LOGIN_NETWORK;//登陆时网络状态
	private String LOGIN_MARK;//登陆设备的地址
	
	@Id
	@Column(name="OBJ_ID",nullable=false,length = 32)
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public String getOBJ_ID() {
		return OBJ_ID;
	}
	public void setOBJ_ID(String obj_id) {
		OBJ_ID = obj_id;
	}
	public String getUSERS_OBJ_ID() {
		return USERS_OBJ_ID;
	}
	public void setUSERS_OBJ_ID(String users_obj_id) {
		USERS_OBJ_ID = users_obj_id;
	}
	public String getLOGIN_TIME() {
		return LOGIN_TIME;
	}
	public void setLOGIN_TIME(String login_time) {
		LOGIN_TIME = login_time;
	}
	public String getLOGIN_MODE() {
		return LOGIN_MODE;
	}
	public void setLOGIN_MODE(String login_mode) {
		LOGIN_MODE = login_mode;
	}
	public String getLOGIN_NETWORK() {
		return LOGIN_NETWORK;
	}
	public void setLOGIN_NETWORK(String login_network) {
		LOGIN_NETWORK = login_network;
	}
	public String getLOGIN_MARK() {
		return LOGIN_MARK;
	}
	public void setLOGIN_MARK(String login_mark) {
		LOGIN_MARK = login_mark;
	}

}
