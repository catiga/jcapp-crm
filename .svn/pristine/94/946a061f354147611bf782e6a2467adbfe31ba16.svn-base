package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_tp_quesitem")
class QuestionItem {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pack;
	
	String itemno;
	
	String question;
	
	String subhead;
	
	/**
	 * 10:文本
	 * 21:单选
	 * 22:多选
	 */
	String qt;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
