package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_account_third")
class AccountThirdBind {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger account_id;
	
	String part_id;
	
	String part_union_id;
	
	String belong_code;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	@JCForeign
	BigInteger pid;
	
}
