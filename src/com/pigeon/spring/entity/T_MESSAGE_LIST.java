package com.pigeon.spring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 * 私信列表
 * @author liufeng
 * @date 2017-4-18
 */
@Entity(name = "T_MESSAGE_LIST")
public class T_MESSAGE_LIST implements Serializable {

	private static final long serialVersionUID = 1L;
	private String OBJ_ID;
	private String USER_OBJ_ID;
	private String PRIVATE_USER_ID;
	private Timestamp CREATE_TIME;
	
	@Id
	@Column(name="OBJ_ID",nullable=false,length = 32)
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public String getOBJ_ID() {
		return OBJ_ID;
	}
	public void setOBJ_ID(String oBJ_ID) {
		OBJ_ID = oBJ_ID;
	}
	public String getUSER_OBJ_ID() {
		return USER_OBJ_ID;
	}
	public void setUSER_OBJ_ID(String uSER_OBJ_ID) {
		USER_OBJ_ID = uSER_OBJ_ID;
	}
	public String getPRIVATE_USER_ID() {
		return PRIVATE_USER_ID;
	}
	public void setPRIVATE_USER_ID(String pRIVATE_USER_ID) {
		PRIVATE_USER_ID = pRIVATE_USER_ID;
	}
	public Timestamp getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Timestamp cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	
	
}
