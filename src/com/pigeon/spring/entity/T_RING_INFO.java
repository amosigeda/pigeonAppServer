package com.pigeon.spring.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="T_RING_INFO")
public class T_RING_INFO {

	private String OBJ_ID;//主键
	private String RING_CODE;//鸽环编号：按照一定规则自动生成(YYMMDD+随机生成6位数)
	private Date MANUFACTURE_DATE;//出厂日期
	private String OUTPUT_ADDRESS;//生产地点
	private String SPECIFICATION;//规格：100:初级训练版;200:中级训练版;300:高级训练版
	private Timestamp START_DATE;//开始使用时间
	private Timestamp RENEW_DATE;//续费操作时间
	private String SERVICE_TERM;//服务期限
	private String RING_STATUS;//鸽环状态：1：已激活；2：未激活；3: 已匹配；4：未匹配；

	
	@Id
	@Column(name="OBJ_ID",nullable=false,length = 32)
	@GeneratedValue(generator="hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	public String getOBJ_ID() {
		return OBJ_ID;
	}

	public String getRING_CODE() {
		return RING_CODE;
	}

	public void setRING_CODE(String ring_code) {
		RING_CODE = ring_code;
	}

	public Date getMANUFACTURE_DATE() {
		return MANUFACTURE_DATE;
	}

	public void setMANUFACTURE_DATE(Date manufacture_date) {
		MANUFACTURE_DATE = manufacture_date;
	}

	public String getOUTPUT_ADDRESS() {
		return OUTPUT_ADDRESS;
	}

	public void setOUTPUT_ADDRESS(String output_address) {
		OUTPUT_ADDRESS = output_address;
	}

	public String getSPECIFICATION() {
		return SPECIFICATION;
	}

	public void setSPECIFICATION(String specification) {
		SPECIFICATION = specification;
	}

	public Timestamp getSTART_DATE() {
		return START_DATE;
	}

	public void setSTART_DATE(Timestamp start_date) {
		START_DATE = start_date;
	}

	public Timestamp getRENEW_DATE() {
		return RENEW_DATE;
	}

	public void setRENEW_DATE(Timestamp renew_date) {
		RENEW_DATE = renew_date;
	}

	public String getSERVICE_TERM() {
		return SERVICE_TERM;
	}

	public void setSERVICE_TERM(String service_term) {
		SERVICE_TERM = service_term;
	}

	public String getRING_STATUS() {
		return RING_STATUS;
	}

	public void setRING_STATUS(String ring_status) {
		RING_STATUS = ring_status;
	}

	public void setOBJ_ID(String obj_id) {
		OBJ_ID = obj_id;
	}
}
