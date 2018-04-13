package com.pigeon.spring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 动态表
 * @author liufeng
 * @date 2017-4-14
 */
@Entity(name = "T_DYNAMIC")
public class T_DYNAMIC implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "DYNAMIC_ID",nullable=false,length=32)
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String DYNAMIC_ID;//动态主键
	private String USER_OBJ_ID;//用户表主键
	private String CONTENT;//内容
	private String IMAGE_URL;//图片链接
	private Integer TRANSPOND_NUM;//转发数
	private Integer COMMENT_NUM;//评论数
	private Integer PRAISE_NUM;//点赞数
	private Timestamp CREATE_TIME;//发表时间
	private String TRAN_OBJ_ID;//原动态作者
	private String REASON;//转发理由
	private String IMAGE_URL1;//图片链接
	private String IMAGE_URL2;//图片链接
	private String IMAGE_URL3;//图片链接
	@Transient
	private Integer RELATION;//关系
	@Transient
	private String DOAGREE;//是否点赞
	@Transient
	private String USER_CODE;//用户名
	
	public String getDYNAMIC_ID() {
		return DYNAMIC_ID;
	}
	public void setDYNAMIC_ID(String dYNAMIC_ID) {
		DYNAMIC_ID = dYNAMIC_ID;
	}
	public String getUSER_CODE() {
		return USER_CODE;
	}
	public void setUSER_CODE(String uSER_CODE) {
		USER_CODE = uSER_CODE;
	}
	public String getIMAGE_URL1() {
		return IMAGE_URL1;
	}
	public void setIMAGE_URL1(String iMAGE_URL1) {
		IMAGE_URL1 = iMAGE_URL1;
	}
	public String getIMAGE_URL2() {
		return IMAGE_URL2;
	}
	public void setIMAGE_URL2(String iMAGE_URL2) {
		IMAGE_URL2 = iMAGE_URL2;
	}
	public String getIMAGE_URL3() {
		return IMAGE_URL3;
	}
	public void setIMAGE_URL3(String iMAGE_URL3) {
		IMAGE_URL3 = iMAGE_URL3;
	}
	public String getUSER_OBJ_ID() {
		return USER_OBJ_ID;
	}
	public void setUSER_OBJ_ID(String uSER_OBJ_ID) {
		USER_OBJ_ID = uSER_OBJ_ID;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getIMAGE_URL() {
		return IMAGE_URL;
	}
	public void setIMAGE_URL(String iMAGE_URL) {
		IMAGE_URL = iMAGE_URL;
	}
	public Integer getTRANSPOND_NUM() {
		return TRANSPOND_NUM!=null?TRANSPOND_NUM:0;
	}
	public void setTRANSPOND_NUM(Integer tRANSPOND_NUM) {
		TRANSPOND_NUM = tRANSPOND_NUM;
	}
	public Integer getCOMMENT_NUM() {
		return COMMENT_NUM!=null?COMMENT_NUM:0;
	}
	public void setCOMMENT_NUM(Integer cOMMENT_NUM) {
		COMMENT_NUM = cOMMENT_NUM;
	}
	public Integer getPRAISE_NUM() {
		return PRAISE_NUM!=null?PRAISE_NUM:0;
	}
	public void setPRAISE_NUM(Integer pRAISE_NUM) {
		PRAISE_NUM = pRAISE_NUM;
	}
	public Timestamp getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Timestamp cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public Integer getRELATION() {
		return RELATION;
	}
	public void setRELATION(Integer rELATION) {
		RELATION = rELATION;
	}
	public String getTRAN_OBJ_ID() {
		return TRAN_OBJ_ID;
	}
	public void setTRAN_OBJ_ID(String tRAN_OBJ_ID) {
		TRAN_OBJ_ID = tRAN_OBJ_ID;
	}
	public String getREASON() {
		return REASON;
	}
	public void setREASON(String rEASON) {
		REASON = rEASON;
	}
	public String getDOAGREE() {
		return DOAGREE;
	}
	public void setDOAGREE(String dOAGREE) {
		DOAGREE = dOAGREE;
	}
	
}
