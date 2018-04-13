package com.pigeon.spring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 添加好友申请记录表
 * @author liufeng
 * @date 2017-4-17
 */
@Entity(name = "T_ADD_FRIEND_RECORD")
public class T_ADD_FRIEND_RECORD implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String OBJ_ID;//主键
	private String USER_OBJ_ID;//发起请求用户主键
	private String FRIEND_OBJ_ID;//接受用户请求主键
	private Integer ADD_STATUS;//接受状态
	private Timestamp ADD_TIME;//添加时间
	
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
	public String getFRIEND_OBJ_ID() {
		return FRIEND_OBJ_ID;
	}
	public void setFRIEND_OBJ_ID(String fRIEND_OBJ_ID) {
		FRIEND_OBJ_ID = fRIEND_OBJ_ID;
	}
	public Timestamp getADD_TIME() {
		return ADD_TIME;
	}
	public void setADD_TIME(Timestamp aDD_TIME) {
		ADD_TIME = aDD_TIME;
	}
	public Integer getADD_STATUS() {
		return ADD_STATUS;
	}
	public void setADD_STATUS(Integer aDD_STATUS) {
		ADD_STATUS = aDD_STATUS;
	}
	
	
}
