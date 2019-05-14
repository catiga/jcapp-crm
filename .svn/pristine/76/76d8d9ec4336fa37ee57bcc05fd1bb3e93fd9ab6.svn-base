package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_doc_reader")
class DocReader {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger user_id;
	
	@JCForeign
	BigInteger ap_id;

	String user_key;
	
	@JCForeign
	BigInteger doc_id;
	
	String ip_addr;
	
	String latitude;
	
	String longitude;
	
	Timestamp a_time;
	
	Integer flag = 0;
	
	String app_id;
}
