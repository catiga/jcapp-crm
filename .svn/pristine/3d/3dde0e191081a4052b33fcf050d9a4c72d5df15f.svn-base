package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_tp_quespack")
class Questionnaire {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pid;
	
	String pack_no;
	
	String pack_name;
	
	String pack_info;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	Integer useflag;
	
	Integer sgutimes;
	
	String tplname;
	
}
