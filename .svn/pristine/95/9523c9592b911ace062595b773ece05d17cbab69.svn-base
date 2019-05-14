package com.jeancoder.crm.ready.dto.sys

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

class SysFunction {

	BigInteger id;
	
	String func_name;
	
	String func_ss;
	
	String func_type;
	
	String func_info;
	
	BigInteger parent_id;
	
	Timestamp c_time;
	
	Timestamp a_time;
	
	Integer flag = 0;
	
	Integer level;
	
	String click_url;
	
	Boolean whole;
	
	Boolean hasson;
	
	Integer sort;
	
	String limpro;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getFunc_name() {
		return func_name;
	}

	public void setFunc_name(String func_name) {
		this.func_name = func_name;
	}

	public String getFunc_ss() {
		return func_ss;
	}

	public void setFunc_ss(String func_ss) {
		this.func_ss = func_ss;
	}

	public String getFunc_info() {
		return func_info;
	}

	public void setFunc_info(String func_info) {
		this.func_info = func_info;
	}

	public BigInteger getParent_id() {
		return parent_id;
	}

	public void setParent_id(BigInteger parent_id) {
		this.parent_id = parent_id;
	}

	public Timestamp getC_time() {
		return c_time;
	}

	public void setC_time(Timestamp c_time) {
		this.c_time = c_time;
	}

	public Timestamp getA_time() {
		return a_time;
	}

	public void setA_time(Timestamp a_time) {
		this.a_time = a_time;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getClick_url() {
		return click_url;
	}

	public void setClick_url(String click_url) {
		this.click_url = click_url;
	}

	public Boolean getWhole() {
		return whole;
	}

	public void setWhole(Boolean whole) {
		this.whole = whole;
	}

	public Boolean getHasson() {
		return hasson;
	}

	public void setHasson(Boolean hasson) {
		this.hasson = hasson;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLimpro() {
		return limpro;
	}

	public void setLimpro(String limpro) {
		this.limpro = limpro;
	}

	public String getFunc_type() {
		return func_type;
	}

	public void setFunc_type(String func_type) {
		this.func_type = func_type;
	}
	
	@Override
	public int hashCode() {
		return this.id.intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SysFunction)) {
			return false;
		}
		SysFunction aim = (SysFunction)obj;
		if(aim.getId().equals(this.id)) {
			return true;
		}
		return false;
	}
}
