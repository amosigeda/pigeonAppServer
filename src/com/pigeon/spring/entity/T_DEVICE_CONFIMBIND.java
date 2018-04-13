package com.pigeon.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 设备登录信息表
 * @author weitechao
 * @date 2017-4-14
 */
@Entity(name = "T_DEVICE_CONFIMBIND")
public class T_DEVICE_CONFIMBIND implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String OBJ_ID;
	private String DEVICE_SERIAL_NUMBER;
	private String TRANSACTION_CODE;
    private String DEVICE_TIMESTAMP;
    private Date INSERT_TIME;
    
    @Id
    @Column(name = "OBJ_ID",nullable=false,length=32)
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    public String getOBJ_ID() {
  		return OBJ_ID;
  	}
  	public void setOBJ_ID(String oBJ_ID) {
  		OBJ_ID = oBJ_ID;
  	}
	public String getDEVICE_SERIAL_NUMBER() {
		return DEVICE_SERIAL_NUMBER;
	}
	public void setDEVICE_SERIAL_NUMBER(String dEVICE_SERIAL_NUMBER) {
		DEVICE_SERIAL_NUMBER = dEVICE_SERIAL_NUMBER;
	}
	public String getTRANSACTION_CODE() {
		return TRANSACTION_CODE;
	}
	public void setTRANSACTION_CODE(String tRANSACTION_CODE) {
		TRANSACTION_CODE = tRANSACTION_CODE;
	}
	public String getDEVICE_TIMESTAMP() {
		return DEVICE_TIMESTAMP;
	}
	public void setDEVICE_TIMESTAMP(String dEVICE_TIMESTAMP) {
		DEVICE_TIMESTAMP = dEVICE_TIMESTAMP;
	}
	public Date getINSERT_TIME() {
		return INSERT_TIME;
	}
	public void setINSERT_TIME(Date iNSERT_TIME) {
		INSERT_TIME = iNSERT_TIME;
	}
    
}
