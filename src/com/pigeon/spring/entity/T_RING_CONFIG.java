package com.pigeon.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_RING_CONFIG")
public class T_RING_CONFIG {
	
	private String obj_id;//主键
	private String ring_code;//鸽环编号（IMEI）
	private String power_warn;//预警类型
	private String sim_status;
	private String start_status;//开机状态
	private String position_mode;//定位模式
	private String reported_freq;//报点频率
	private String schedule_power;//定时开关机
	private String fly_mode;//飞行模式
	private Timestamp conf_time;//配置时间
	
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
	public String getPower_warn() {
		return power_warn;
	}
	public void setPower_warn(String power_warn) {
		this.power_warn = power_warn;
	}
	public String getSim_status() {
		return sim_status;
	}
	public void setSim_status(String sim_status) {
		this.sim_status = sim_status;
	}
	public String getStart_status() {
		return start_status;
	}
	public void setStart_status(String start_status) {
		this.start_status = start_status;
	}
	public String getPosition_mode() {
		return position_mode;
	}
	public void setPosition_mode(String position_mode) {
		this.position_mode = position_mode;
	}
	public String getReported_freq() {
		return reported_freq;
	}
	public void setReported_freq(String reported_freq) {
		this.reported_freq = reported_freq;
	}
	public String getSchedule_power() {
		return schedule_power;
	}
	public void setSchedule_power(String schedule_power) {
		this.schedule_power = schedule_power;
	}
	public String getFly_mode() {
		return fly_mode;
	}
	public void setFly_mode(String fly_mode) {
		this.fly_mode = fly_mode;
	}
	public Timestamp getConf_time() {
		return conf_time;
	}
	public void setConf_time(Timestamp conf_time) {
		this.conf_time = conf_time;
	}
	
}
