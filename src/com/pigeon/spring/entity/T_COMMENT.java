package com.pigeon.spring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 评论表
 * @author liufeng
 * @date 2017-4-14
 */
@Entity(name = "T_COMMENT")
public class T_COMMENT implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COMMENT_ID",nullable=false,length=32)
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String COMMENT_ID;
	private String DYNAMIC_ID;
	private String USER_OBJ_ID;
	private String REVIEWER;
	private String CONTENT;
	private String REPLY_COMMENT_ID;
	private Timestamp CREATE_TIME;
	
	public String getCOMMENT_ID() {
		return COMMENT_ID;
	}
	public void setCOMMENT_ID(String cOMMENT_ID) {
		COMMENT_ID = cOMMENT_ID;
	}
	public String getDYNAMIC_ID() {
		return DYNAMIC_ID;
	}
	public void setDYNAMIC_ID(String dYNAMIC_ID) {
		DYNAMIC_ID = dYNAMIC_ID;
	}
	public String getREPLY_COMMENT_ID() {
		return REPLY_COMMENT_ID;
	}
	public void setREPLY_COMMENT_ID(String rEPLY_COMMENT_ID) {
		REPLY_COMMENT_ID = rEPLY_COMMENT_ID;
	}
	public String getUSER_OBJ_ID() {
		return USER_OBJ_ID;
	}
	public void setUSER_OBJ_ID(String uSER_OBJ_ID) {
		USER_OBJ_ID = uSER_OBJ_ID;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getREVIEWER() {
		return REVIEWER;
	}
	public void setREVIEWER(String rEVIEWER) {
		REVIEWER = rEVIEWER;
	}
	public Timestamp getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Timestamp cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	
}
