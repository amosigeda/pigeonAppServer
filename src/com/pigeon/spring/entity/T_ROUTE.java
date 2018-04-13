package com.pigeon.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_ROUTE")
public class T_ROUTE {
	
	private String obj_id;//主键
	private String user_obj_id;//用户信息表主键
	private String route_name;//行程名称（示例：20161106-天津至北京）
	private String route_start_address;//开始位置
	private Timestamp route_start_time;//开始时间
	private String route_end_address;//结束位置
	private Timestamp route_end_time;//结束时间
	
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
	public String getUser_obj_id() {
		return user_obj_id;
	}
	public void setUser_obj_id(String user_obj_id) {
		this.user_obj_id = user_obj_id;
	}
	public String getRoute_name() {
		return route_name;
	}
	public void setRoute_name(String route_name) {
		this.route_name = route_name;
	}
	public String getRoute_start_address() {
		return route_start_address;
	}
	public void setRoute_start_address(String route_start_address) {
		this.route_start_address = route_start_address;
	}
	public Timestamp getRoute_start_time() {
		return route_start_time;
	}
	public void setRoute_start_time(Timestamp route_start_time) {
		this.route_start_time = route_start_time;
	}
	public String getRoute_end_address() {
		return route_end_address;
	}
	public void setRoute_end_address(String route_end_address) {
		this.route_end_address = route_end_address;
	}
	public Timestamp getRoute_end_time() {
		return route_end_time;
	}
	public void setRoute_end_time(Timestamp route_end_time) {
		this.route_end_time = route_end_time;
	}

}
