package com.pigeon.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_PIGEON_ROUTE_REF")
public class T_PIGEON_ROUTE_REF {
	
	private String obj_id;//主键
	private String route_obj_id;//行程主键
	private String pigeon_obj_id;//信鸽主键
	
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
	public String getRoute_obj_id() {
		return route_obj_id;
	}
	public void setRoute_obj_id(String route_obj_id) {
		this.route_obj_id = route_obj_id;
	}
	public String getPigeon_obj_id() {
		return pigeon_obj_id;
	}
	public void setPigeon_obj_id(String pigeon_obj_id) {
		this.pigeon_obj_id = pigeon_obj_id;
	}
	
}
