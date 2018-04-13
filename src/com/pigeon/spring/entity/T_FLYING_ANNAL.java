package com.pigeon.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_FLYING_ANNAL")
public class T_FLYING_ANNAL {

	private String OBJ_ID;// 主键
	private String PIGEON_OBJ_ID;// 信鸽基本信息表-主键
	private Timestamp FLYING_START_TIME;// 飞行开始时间
	private String FLYING_START_ADDRESS;// 飞行开始地点
	private Timestamp FLYING_END_TIME;// 飞行结束时间
	private String FLYING_END_ADDRESS;// 飞行结束地点
	private Timestamp GENERATE_TIME;// 记录时间
	private String FLYING_SPEED;// 速度
	private String FLYING_HEIGHT;// 高度
	private String SPEED;// 分速
	private String FLYING_DIRECTION;// 方向
	private String LONGITUDE;// 经度
	private String LATITUDE;// 纬度
	private String SURPLUS_POWER;// 剩余电量
	private String FLYING_NATION;// 国家
	private String FLYING_PROVINCE;// 省份
	private String FLYING_CITY;// 地市
	private String FLYING_COUNTY;// 县/区
	private String FLYING_ADDRESS;// 详细地址
	private String FLYING_MODE;// 训放；竞赛；

	@Id
	@Column(name = "OBJ_ID", nullable = false, length = 32)
	@GeneratedValue(generator = "hibernate-uuid")
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

	public Timestamp getFLYING_START_TIME() {
		return FLYING_START_TIME;
	}

	public void setFLYING_START_TIME(Timestamp flying_start_time) {
		FLYING_START_TIME = flying_start_time;
	}

	public String getFLYING_START_ADDRESS() {
		return FLYING_START_ADDRESS;
	}

	public void setFLYING_START_ADDRESS(String flying_start_address) {
		FLYING_START_ADDRESS = flying_start_address;
	}

	public Timestamp getFLYING_END_TIME() {
		return FLYING_END_TIME;
	}

	public void setFLYING_END_TIME(Timestamp flying_end_time) {
		FLYING_END_TIME = flying_end_time;
	}

	public String getFLYING_END_ADDRESS() {
		return FLYING_END_ADDRESS;
	}

	public void setFLYING_END_ADDRESS(String flying_end_address) {
		FLYING_END_ADDRESS = flying_end_address;
	}

	public Timestamp getGENERATE_TIME() {
		return GENERATE_TIME;
	}

	public void setGENERATE_TIME(Timestamp generate_time) {
		GENERATE_TIME = generate_time;
	}

	public String getFLYING_SPEED() {
		return FLYING_SPEED;
	}

	public void setFLYING_SPEED(String flying_speed) {
		FLYING_SPEED = flying_speed;
	}

	public String getFLYING_HEIGHT() {
		return FLYING_HEIGHT;
	}

	public void setFLYING_HEIGHT(String flying_height) {
		FLYING_HEIGHT = flying_height;
	}

	public String getSPEED() {
		return SPEED;
	}

	public void setSPEED(String speed) {
		SPEED = speed;
	}

	public String getFLYING_DIRECTION() {
		return FLYING_DIRECTION;
	}

	public void setFLYING_DIRECTION(String flying_direction) {
		FLYING_DIRECTION = flying_direction;
	}

	public String getLONGITUDE() {
		return LONGITUDE;
	}

	public void setLONGITUDE(String longitude) {
		LONGITUDE = longitude;
	}

	public String getLATITUDE() {
		return LATITUDE;
	}

	public void setLATITUDE(String latitude) {
		LATITUDE = latitude;
	}

	public String getSURPLUS_POWER() {
		return SURPLUS_POWER;
	}

	public void setSURPLUS_POWER(String surplus_power) {
		SURPLUS_POWER = surplus_power;
	}

	public String getFLYING_NATION() {
		return FLYING_NATION;
	}

	public void setFLYING_NATION(String flying_nation) {
		FLYING_NATION = flying_nation;
	}

	public String getFLYING_PROVINCE() {
		return FLYING_PROVINCE;
	}

	public void setFLYING_PROVINCE(String flying_province) {
		FLYING_PROVINCE = flying_province;
	}

	public String getFLYING_CITY() {
		return FLYING_CITY;
	}

	public void setFLYING_CITY(String flying_city) {
		FLYING_CITY = flying_city;
	}

	public String getFLYING_COUNTY() {
		return FLYING_COUNTY;
	}

	public void setFLYING_COUNTY(String flying_county) {
		FLYING_COUNTY = flying_county;
	}

	public String getFLYING_ADDRESS() {
		return FLYING_ADDRESS;
	}

	public void setFLYING_ADDRESS(String flying_address) {
		FLYING_ADDRESS = flying_address;
	}

	public String getFLYING_MODE() {
		return FLYING_MODE;
	}

	public void setFLYING_MODE(String flying_mode) {
		FLYING_MODE = flying_mode;
	}

}
