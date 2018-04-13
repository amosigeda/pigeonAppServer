package com.pigeon.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_FRIENDS_LIST")
public class T_FRIENDS_LIST {
	
	private String OBJ_ID;//主键
	private String USER_OBJ_ID;//用户基本信息表主键
	private String FRIENDS_OBJ_ID;//好友的用户基本信息表主键
//	private String ADD_STATUS;//添加好友状态
//	private String IS_TWO_WAY;//是否双向好友
	private Timestamp ADD_TIME;//添加好友时间
	
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
	public String getFRIENDS_OBJ_ID() {
		return FRIENDS_OBJ_ID;
	}
	public void setFRIENDS_OBJ_ID(String friends_obj_id) {
		FRIENDS_OBJ_ID = friends_obj_id;
	}
//	public String getADD_STATUS() {
//		return ADD_STATUS;
//	}
//	public void setADD_STATUS(String add_status) {
//		ADD_STATUS = add_status;
//	}
//	public String getIS_TWO_WAY() {
//		return IS_TWO_WAY;
//	}
//	public void setIS_TWO_WAY(String is_two_way) {
//		IS_TWO_WAY = is_two_way;
//	}
	public Timestamp getADD_TIME() {
		return ADD_TIME;
	}
	public void setADD_TIME(Timestamp add_time) {
		ADD_TIME = add_time;
	}

}
