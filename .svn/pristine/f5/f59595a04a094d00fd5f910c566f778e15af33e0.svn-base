package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tp_result')
class QuestionResult {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger user_id;
	
	String user_key;
	
	@JCForeign
	BigInteger pack_id;
	
	@JCForeign
	BigInteger item_id;
	
	String item_name;
	
	@JCForeign
	BigInteger choise_id;
	
	String choise_value;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	String ip_addr;
	
	Integer flag = 0;
	
	String latitude;
	
	String longitude;
}
