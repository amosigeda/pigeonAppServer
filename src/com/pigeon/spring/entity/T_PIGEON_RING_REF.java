package com.pigeon.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_PIGEON_RING_REF")
public class T_PIGEON_RING_REF {
	
	private String OBJ_ID;//主键
	private String PIGEON_OBJ_ID;//信鸽基本信息表-主键
	private String RING_OBJ_ID;//鸽环基本信息表-主键
	
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
	public String getPIGEON_OBJ_ID() {
		return PIGEON_OBJ_ID;
	}
	public void setPIGEON_OBJ_ID(String pigeon_obj_id) {
		PIGEON_OBJ_ID = pigeon_obj_id;
	}
	public String getRING_OBJ_ID() {
		return RING_OBJ_ID;
	}
	public void setRING_OBJ_ID(String ring_obj_id) {
		RING_OBJ_ID = ring_obj_id;
	}

}
