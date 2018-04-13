package com.pigeon.spring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 消息推送表
 * @author liufeng
 * @date 2017-4-20
 */
@Entity(name = "T_PUSH")
public class T_PUSH implements Serializable {

	private static final long serialVersionUID = 1L;

	private String OBJ_ID;
	private String PUSH_USER_ID;
	private String RECEIVE_USER_ID;
	private String PUSH_CONTENT;
	private String PUSH_STATUS;
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
	public String getPUSH_USER_ID() {
		return PUSH_USER_ID;
	}
	public void setPUSH_USER_ID(String pUSH_USER_ID) {
		PUSH_USER_ID = pUSH_USER_ID;
	}
	public String getRECEIVE_USER_ID() {
		return RECEIVE_USER_ID;
	}
	public void setRECEIVE_USER_ID(String rECEIVE_USER_ID) {
		RECEIVE_USER_ID = rECEIVE_USER_ID;
	}
	public String getPUSH_CONTENT() {
		return PUSH_CONTENT;
	}
	public void setPUSH_CONTENT(String pUSH_CONTENT) {
		PUSH_CONTENT = pUSH_CONTENT;
	}
	public String getPUSH_STATUS() {
		return PUSH_STATUS;
	}
	public void setPUSH_STATUS(String pUSH_STATUS) {
		PUSH_STATUS = pUSH_STATUS;
	}
	public Timestamp getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Timestamp cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
}
