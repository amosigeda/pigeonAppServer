package com.pigeon.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 图片表
 * @author liufeng
 * @date 2017-4-27
 */
@Entity(name = "T_IMAGE_URL")
public class T_IMAGE_URL implements Serializable {

	private static final long serialVersionUID = 1L;

	private String OBJ_ID;
	private String DYNAMIC_ID;
	private String IMAGE_NAME;
	private String IMAGE_URL;
	
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
	public String getIMAGE_NAME() {
		return IMAGE_NAME;
	}
	public void setIMAGE_NAME(String iMAGE_NAME) {
		IMAGE_NAME = iMAGE_NAME;
	}
	public String getIMAGE_URL() {
		return IMAGE_URL;
	}
	public void setIMAGE_URL(String iMAGE_URL) {
		IMAGE_URL = iMAGE_URL;
	}
	public String getDYNAMIC_ID() {
		return DYNAMIC_ID;
	}
	public void setDYNAMIC_ID(String dYNAMIC_ID) {
		DYNAMIC_ID = dYNAMIC_ID;
	}
	
}
