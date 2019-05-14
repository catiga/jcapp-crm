package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'mm_pre_mc_order_item')
class McPreOrderItem {
	
	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger order_id;
	
	String card_num;
	
	String pub_ss;
	
	BigInteger pub_order_mc;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	String cards_identifier;
}
