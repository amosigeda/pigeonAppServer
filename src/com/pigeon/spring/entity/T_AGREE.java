package com.pigeon.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 点赞表
 * @author liufeng
 * @date 2017-4-14
 */
@Entity(name = "T_AGREE")
public class T_AGREE implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "AGREE_ID",nullable=false,length=32)
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String AGREE_ID;
	private String DYNAMIC_ID;
	private String USER_OBJ_ID;
	
	public String getAGREE_ID() {
		return AGREE_ID;
	}
	public void setAGREE_ID(String aGREE_ID) {
		AGREE_ID = aGREE_ID;
	}
	public String getDYNAMIC_ID() {
		return DYNAMIC_ID;
	}
	public void setDYNAMIC_ID(String dYNAMIC_ID) {
		DYNAMIC_ID = dYNAMIC_ID;
	}
	public String getUSER_OBJ_ID() {
		return USER_OBJ_ID;
	}
	public void setUSER_OBJ_ID(String uSER_OBJ_ID) {
		USER_OBJ_ID = uSER_OBJ_ID;
	}
	
}
