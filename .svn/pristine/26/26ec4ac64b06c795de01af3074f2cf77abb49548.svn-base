package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = "data_account_session")
class AccountSession {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger proj_id;
	
	@JCForeign
	BigInteger basic_id;
	
	@JCForeign
	BigInteger ap_id;
	
	Timestamp a_time;
	
	Long rushed;
	
	String token;
	
	Timestamp c_time;
	
	Integer flag;
	
	Integer lograns;
	
	String logtype;
	
	@JCNotColumn
	String part_id;
	
	@JCNotColumn
	String mobile;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getProj_id() {
		return proj_id;
	}

	public void setProj_id(BigInteger proj_id) {
		this.proj_id = proj_id;
	}

	public BigInteger getBasic_id() {
		return basic_id;
	}

	public void setBasic_id(BigInteger basic_id) {
		this.basic_id = basic_id;
	}

	public Timestamp getA_time() {
		return a_time;
	}

	public void setA_time(Timestamp a_time) {
		this.a_time = a_time;
	}

	public Long getRushed() {
		return rushed;
	}

	public void setRushed(Long rushed) {
		this.rushed = rushed;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getC_time() {
		return c_time;
	}

	public void setC_time(Timestamp c_time) {
		this.c_time = c_time;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getLograns() {
		return lograns;
	}

	public void setLograns(Integer lograns) {
		this.lograns = lograns;
	}

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	
}
