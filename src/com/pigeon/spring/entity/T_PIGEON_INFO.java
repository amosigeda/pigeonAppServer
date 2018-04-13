package com.pigeon.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_PIGEON_INFO")
public class T_PIGEON_INFO {
	
	private String OBJ_ID;//主键
	private String FOOT_RING_CODE;//足环编号
	private String PIGEON_LINEAGE;//信鸽血统
	private String PLUMAGE_COLOR;//羽色
	private Timestamp PIGEON_BIRTHDAY;//信鸽出生时间
	private String PIGEN_SEX;//性别
	private String EYE_SAND;//眼砂
	private String PIGEON_STATUS;//信鸽状态：是否匹配，是否飞行
	private String PIGEON_SCORE;//赛绩
	
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
	public String getFOOT_RING_CODE() {
		return FOOT_RING_CODE;
	}
	public void setFOOT_RING_CODE(String foot_ring_code) {
		FOOT_RING_CODE = foot_ring_code;
	}
	public String getPIGEON_LINEAGE() {
		return PIGEON_LINEAGE;
	}
	public void setPIGEON_LINEAGE(String pigeon_lineage) {
		PIGEON_LINEAGE = pigeon_lineage;
	}
	public String getPLUMAGE_COLOR() {
		return PLUMAGE_COLOR;
	}
	public void setPLUMAGE_COLOR(String plumage_color) {
		PLUMAGE_COLOR = plumage_color;
	}
	public Timestamp getPIGEON_BIRTHDAY() {
		return PIGEON_BIRTHDAY;
	}
	public void setPIGEON_BIRTHDAY(Timestamp pigeon_birthday) {
		PIGEON_BIRTHDAY = pigeon_birthday;
	}
	public String getPIGEN_SEX() {
		return PIGEN_SEX;
	}
	public void setPIGEN_SEX(String pigen_sex) {
		PIGEN_SEX = pigen_sex;
	}
	public String getEYE_SAND() {
		return EYE_SAND;
	}
	public void setEYE_SAND(String eye_sand) {
		EYE_SAND = eye_sand;
	}
	public String getPIGEON_STATUS() {
		return PIGEON_STATUS;
	}
	public void setPIGEON_STATUS(String pigeon_status) {
		PIGEON_STATUS = pigeon_status;
	}
	public String getPIGEON_SCORE() {
		return PIGEON_SCORE;
	}
	public void setPIGEON_SCORE(String pigeon_score) {
		PIGEON_SCORE = pigeon_score;
	}

}
