package com.pigeon.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_DATA_DICT")
public class T_DATA_DICT {
	
	private String OBJ_ID;//主键
	private String GROUP_CODE;//分组编码
	private String GROUP_CNNAME;//分组中文名称
	private String DICT_CODE;//字典数据编码
	private String DICT_CNNAME;//字典数据中文名
	
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
	public String getGROUP_CODE() {
		return GROUP_CODE;
	}
	public void setGROUP_CODE(String group_code) {
		GROUP_CODE = group_code;
	}
	public String getGROUP_CNNAME() {
		return GROUP_CNNAME;
	}
	public void setGROUP_CNNAME(String group_cnname) {
		GROUP_CNNAME = group_cnname;
	}
	public String getDICT_CODE() {
		return DICT_CODE;
	}
	public void setDICT_CODE(String dict_code) {
		DICT_CODE = dict_code;
	}
	public String getDICT_CNNAME() {
		return DICT_CNNAME;
	}
	public void setDICT_CNNAME(String dict_cnname) {
		DICT_CNNAME = dict_cnname;
	}

}
