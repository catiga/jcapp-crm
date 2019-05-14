package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'mm_pre_mc_order_info')
class McPreOrderInfo {
	
	@JCID
	BigInteger id;
	
	String name;
	
	String info;
	
	String content;
	
	String ss;

	@JCForeign
	BigInteger pid;
	
	String order_num;
	
	Integer total_num;
	
	@JCForeign
	BigInteger mcr_id;
	
	String mcr_name;
	
	@JCForeign
	BigInteger mch_id;
	
	String mch_name;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
