package com.pigeon.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_PIGEON_GROUP")
public class T_PIGEON_GROUP {
	
	private String OBJ_ID;//主键
	private String USER_OBJ_ID;//用户信息表主键
	private String GROUP_CODE;//分组编号
	private String GROUP_NAME;//分组名称
	private String PIGEON_OBJ_ID;//成员主键，即信鸽基本信息表主键
	private String PIGEON_MAX;//成员最大值，单个分组中最多有20只鸽子
	private Timestamp PIGEON_ADD_TIME;//成员添加时间
	private String GROUP_REMARK;//分组备注
	private Timestamp GROUP_CREATE_TIME;//分组创建时间
	
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
	public String getUSER_OBJ_ID() {
		return USER_OBJ_ID;
	}
	public void setUSER_OBJ_ID(String user_obj_id) {
		USER_OBJ_ID = user_obj_id;
	}
	public String getGROUP_CODE() {
		return GROUP_CODE;
	}
	public void setGROUP_CODE(String group_code) {
		GROUP_CODE = group_code;
	}
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String group_name) {
		GROUP_NAME = group_name;
	}
	public String getPIGEON_OBJ_ID() {
		return PIGEON_OBJ_ID;
	}
	public void setPIGEON_OBJ_ID(String pigeon_obj_id) {
		PIGEON_OBJ_ID = pigeon_obj_id;
	}
	public String getPIGEON_MAX() {
		return PIGEON_MAX;
	}
	public void setPIGEON_MAX(String pigeon_max) {
		PIGEON_MAX = pigeon_max;
	}
	public Timestamp getPIGEON_ADD_TIME() {
		return PIGEON_ADD_TIME;
	}
	public void setPIGEON_ADD_TIME(Timestamp pigeon_add_time) {
		PIGEON_ADD_TIME = pigeon_add_time;
	}
	public String getGROUP_REMARK() {
		return GROUP_REMARK;
	}
	public void setGROUP_REMARK(String group_remark) {
		GROUP_REMARK = group_remark;
	}
	public Timestamp getGROUP_CREATE_TIME() {
		return GROUP_CREATE_TIME;
	}
	public void setGROUP_CREATE_TIME(Timestamp group_create_time) {
		GROUP_CREATE_TIME = group_create_time;
	}

}
