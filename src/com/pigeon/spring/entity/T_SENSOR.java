package com.pigeon.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_SENSOR")
public class T_SENSOR {
	
	private String obj_id;//主键
	private String ring_code;//鸽环IMEI
	private int ring_mode;//飞行状态:1--走路，2--爬升，3--飞行，4--下降
	private Timestamp sensor_time;//状态记录时间
	
	@Id
	@Column(name="obj_id",nullable=false,length = 32)
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getRing_code() {
		return ring_code;
	}
	public void setRing_code(String ring_code) {
		this.ring_code = ring_code;
	}
	public int getRing_mode() {
		return ring_mode;
	}
	public void setRing_mode(int ring_mode) {
		this.ring_mode = ring_mode;
	}
	public Timestamp getSensor_time() {
		return sensor_time;
	}
	public void setSensor_time(Timestamp sensor_time) {
		this.sensor_time = sensor_time;
	}
	
	
}
