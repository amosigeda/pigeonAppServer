package com.pigeon.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 设备定位信息表
 * @author weitechao
 * @date 2017-8-21
 */
@Entity(name = "T_DEVICE_LOCATION")
public class T_DEVICE_LOCATION implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String OBJ_ID;
	private String TRANSACTION_CODE;
	private String DEVICE_SERIAL_NUMBER;
	private String LAT;
	private String LON;
	private String SPEED;
	private String ELEVATION;//海拔
	private String DIRECTION;//方向
	private Integer BAT_STATUS;//电池使用状态
	private Integer ELECTRIC_QUANTITY;//电量
	private Integer FLY;//是否飞行
	private String TIMESTAMP;
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
	public String getTRANSACTION_CODE() {
		return TRANSACTION_CODE;
	}
	public void setTRANSACTION_CODE(String tRANSACTION_CODE) {
		TRANSACTION_CODE = tRANSACTION_CODE;
	}
	public String getDEVICE_SERIAL_NUMBER() {
		return DEVICE_SERIAL_NUMBER;
	}
	public void setDEVICE_SERIAL_NUMBER(String dEVICE_SERIAL_NUMBER) {
		DEVICE_SERIAL_NUMBER = dEVICE_SERIAL_NUMBER;
	}
	public String getLAT() {
		return LAT;
	}
	public void setLAT(String lAT) {
		LAT = lAT;
	}
	public String getLON() {
		return LON;
	}
	public void setLON(String lON) {
		LON = lON;
	}
	public String getSPEED() {
		return SPEED;
	}
	public void setSPEED(String sPEED) {
		SPEED = sPEED;
	}
	public String getELEVATION() {
		return ELEVATION;
	}
	public void setELEVATION(String eLEVATION) {
		ELEVATION = eLEVATION;
	}
	public String getDIRECTION() {
		return DIRECTION;
	}
	public void setDIRECTION(String dIRECTION) {
		DIRECTION = dIRECTION;
	}
	public Integer getBAT_STATUS() {
		return BAT_STATUS;
	}
	public void setBAT_STATUS(Integer bAT_STATUS) {
		BAT_STATUS = bAT_STATUS;
	}
	public Integer getELECTRIC_QUANTITY() {
		return ELECTRIC_QUANTITY;
	}
	public void setELECTRIC_QUANTITY(Integer eLECTRIC_QUANTITY) {
		ELECTRIC_QUANTITY = eLECTRIC_QUANTITY;
	}
	public Integer getFLY() {
		return FLY;
	}
	public void setFLY(Integer fLY) {
		FLY = fLY;
	}
	public String getTIMESTAMP() {
		return TIMESTAMP;
	}
	public void setTIMESTAMP(String tIMESTAMP) {
		TIMESTAMP = tIMESTAMP;
	}
	public Date getINSERT_TIME() {
		return INSERT_TIME;
	}
	public void setINSERT_TIME(Date iNSERT_TIME) {
		INSERT_TIME = iNSERT_TIME;
	}

}
