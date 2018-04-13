package com.pigeon.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 设备APP设置信息表
 * 
 * @author weitechao
 * @date 2017-4-14
 */
@Entity(name = "DEVICE_SET")
public class DEVICE_SET implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer ID;
	private String DEVICE_SERIAL_NUMBER;
	private Integer LOCATION_TYPE;
	private Integer POINT_FREQUENCY;
	private String TING_OFF_ON;
	private Integer FLY_MODEL;
	private Date UPDATE_TIME;
	private Integer GET_STATUS;
	private Date GET_TIME;

	@Id
	@Column(name = "ID", nullable = false, length = 11)
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getDEVICE_SERIAL_NUMBER() {
		return DEVICE_SERIAL_NUMBER;
	}

	public void setDEVICE_SERIAL_NUMBER(String dEVICE_SERIAL_NUMBER) {
		DEVICE_SERIAL_NUMBER = dEVICE_SERIAL_NUMBER;
	}

	public Integer getLOCATION_TYPE() {
		return LOCATION_TYPE;
	}

	public void setLOCATION_TYPE(Integer lOCATION_TYPE) {
		LOCATION_TYPE = lOCATION_TYPE;
	}

	public Integer getPOINT_FREQUENCY() {
		return POINT_FREQUENCY;
	}

	public void setPOINT_FREQUENCY(Integer pOINT_FREQUENCY) {
		POINT_FREQUENCY = pOINT_FREQUENCY;
	}

	public String getTING_OFF_ON() {
		return TING_OFF_ON;
	}

	public void setTING_OFF_ON(String tING_OFF_ON) {
		TING_OFF_ON = tING_OFF_ON;
	}

	public Integer getFLY_MODEL() {
		return FLY_MODEL;
	}

	public void setFLY_MODEL(Integer fLY_MODEL) {
		FLY_MODEL = fLY_MODEL;
	}

	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}

	public Integer getGET_STATUS() {
		return GET_STATUS;
	}

	public void setGET_STATUS(Integer gET_STATUS) {
		GET_STATUS = gET_STATUS;
	}

	public Date getGET_TIME() {
		return GET_TIME;
	}

	public void setGET_TIME(Date gET_TIME) {
		GET_TIME = gET_TIME;
	}

}
