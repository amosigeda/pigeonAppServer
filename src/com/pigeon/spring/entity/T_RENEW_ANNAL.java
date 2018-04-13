package com.pigeon.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_RENEW_ANNAL")
public class T_RENEW_ANNAL {
	
	private String OBJ_ID;//主键
	private String RING_OBJ_ID;//鸽环基本信息表主键
	private String RENEW_TIME;//续费时长
	private Timestamp DATE;//续费时间
	private String RENEW_USER;//续费人
	
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
	public String getRING_OBJ_ID() {
		return RING_OBJ_ID;
	}
	public void setRING_OBJ_ID(String ring_obj_id) {
		RING_OBJ_ID = ring_obj_id;
	}
	public String getRENEW_TIME() {
		return RENEW_TIME;
	}
	public void setRENEW_TIME(String renew_time) {
		RENEW_TIME = renew_time;
	}
	public Timestamp getDATE() {
		return DATE;
	}
	public void setDATE(Timestamp date) {
		DATE = date;
	}
	public String getRENEW_USER() {
		return RENEW_USER;
	}
	public void setRENEW_USER(String renew_user) {
		RENEW_USER = renew_user;
	}

}
