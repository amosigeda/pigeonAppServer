package com.pigeon.spring.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_USER_INFO")
public class T_USER_INFO {
	
	private String OBJ_ID;//主键
	private Integer USER_CODE;//用户编号：用户注册时自动生成9位数字
	private String USER_PHONE;//手机号
	private String USER_PWD;//用户密码
	private String USER_EMAIL;//邮箱
	private String DOVECOTE_NAME;//鸽舍名称
	private String CLUB;//俱乐部
	private String PIGENO_UNION;//协会
	private Date USER_AGE;//出生年月
	private String USER_SEX;//性别:1：男；2：女；3：其他
	private Integer FEED_PIGEON_YEAR;//养鸽年限
	private Timestamp ENROLLMENT_TIME;//注册时间
	private String secret_key;//密码找回秘钥
	private Timestamp expired_time;//密码找回过期时间
	private String APP_TYPE;//APP类别：IOS/Android
	
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
	public Integer getUSER_CODE() {
		return USER_CODE;
	}
	public void setUSER_CODE(Integer user_code) {
		USER_CODE = user_code;
	}
	public String getUSER_PHONE() {
		return USER_PHONE;
	}
	public void setUSER_PHONE(String user_phone) {
		USER_PHONE = user_phone;
	}
	public String getUSER_PWD() {
		return USER_PWD;
	}
	public void setUSER_PWD(String user_pwd) {
		USER_PWD = user_pwd;
	}
	public String getUSER_EMAIL() {
		return USER_EMAIL;
	}
	public void setUSER_EMAIL(String user_email) {
		USER_EMAIL = user_email;
	}
	public String getDOVECOTE_NAME() {
		return DOVECOTE_NAME;
	}
	public void setDOVECOTE_NAME(String dovecote_name) {
		DOVECOTE_NAME = dovecote_name;
	}
	public String getCLUB() {
		return CLUB;
	}
	public void setCLUB(String club) {
		CLUB = club;
	}
	public String getPIGENO_UNION() {
		return PIGENO_UNION;
	}
	public void setPIGENO_UNION(String pigeno_union) {
		PIGENO_UNION = pigeno_union;
	}
	public Date getUSER_AGE() {
		return USER_AGE;
	}
	public void setUSER_AGE(Date user_age) {
		USER_AGE = user_age;
	}
	public String getUSER_SEX() {
		return USER_SEX;
	}
	public void setUSER_SEX(String user_sex) {
		USER_SEX = user_sex;
	}
	public Integer getFEED_PIGEON_YEAR() {
		return FEED_PIGEON_YEAR;
	}
	public void setFEED_PIGEON_YEAR(Integer feed_pigeon_year) {
		FEED_PIGEON_YEAR = feed_pigeon_year;
	}
	public Timestamp getENROLLMENT_TIME() {
		return ENROLLMENT_TIME;
	}
	public void setENROLLMENT_TIME(Timestamp enrollment_time) {
		ENROLLMENT_TIME = enrollment_time;
	}
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public Timestamp getExpired_time() {
		return expired_time;
	}
	public void setExpired_time(Timestamp expired_time) {
		this.expired_time = expired_time;
	}
	public String getAPP_TYPE() {
		return APP_TYPE;
	}
	public void setAPP_TYPE(String aPP_TYPE) {
		APP_TYPE = aPP_TYPE;
	}

}
