package com.pigeon.spring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 私信内容
 * @author liufeng
 * @date 2017-4-18
 */
@Entity(name = "T_MESSAGE_CONTENT")
public class T_MESSAGE_CONTENT implements Serializable {

	private static final long serialVersionUID = 1L;
	private String OBJ_ID;
	private String MESSAGE_OBJ_ID;
	private String SEND_USER_ID;
	private String CONTENT;
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
	public String getMESSAGE_OBJ_ID() {
		return MESSAGE_OBJ_ID;
	}
	public void setMESSAGE_OBJ_ID(String mESSAGE_OBJ_ID) {
		MESSAGE_OBJ_ID = mESSAGE_OBJ_ID;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public Timestamp getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Timestamp cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public String getSEND_USER_ID() {
		return SEND_USER_ID;
	}
	public void setSEND_USER_ID(String sEND_USER_ID) {
		SEND_USER_ID = sEND_USER_ID;
	}
	
}
