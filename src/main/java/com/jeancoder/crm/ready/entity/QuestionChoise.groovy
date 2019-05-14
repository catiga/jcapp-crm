package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_tp_queschoise")
class QuestionChoise {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger item_id;
	
	String awno;
	
	String awname;
	
	String awvise;
	
	Integer input_falg;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
